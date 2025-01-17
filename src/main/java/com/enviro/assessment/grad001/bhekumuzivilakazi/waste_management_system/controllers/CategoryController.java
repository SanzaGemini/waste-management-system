package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exceptions.BindExceptionHandler;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exceptions.NotFoundException;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.Category;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.CategoryDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.CreateGroup;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.response.ApiResponse;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service.CategoryService;

import jakarta.validation.Valid;

/**
 * This controller provides RESTful endpoints for managing Category resources in the waste management system.
 * It supports creating, retrieving, updating, and deleting categories, as well as fetching a random category.
 */
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;  // Service for handling category-related business logic

    /**
     * Creates a new category.
     * @param categoryDTO The data transfer object containing category details.
     * @param bindingResult Holds validation errors if any.
     * @return A ResponseEntity containing the result of the category creation.
     */
    @Validated(CreateGroup.class)
    @PostMapping
    public ResponseEntity<ApiResponse<Object>> addCategory(@RequestBody @Valid CategoryDTO categoryDTO, BindingResult bindingResult){
        // Call service to save the category and return the response with success status
        Category savedCategory = categoryService.saveCategory(categoryDTO); 
        return new ResponseEntity<>(ApiResponse.success(savedCategory), HttpStatus.CREATED); // Return 201 Created
    }

    /**
     * Retrieves a category by its ID.
     * @param id The ID of the category to retrieve.
     * @return A ResponseEntity containing the category details, or 404 if not found.
     */
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> getCategoryById(@PathVariable Long id){
        try {
            Category category = categoryService.getCategoryById(id).get();  
            return new ResponseEntity<>(ApiResponse.success(category), HttpStatus.OK);
        } catch (NotFoundException nE) {
            return NotFoundException.handleNotFoundException(nE);  
        }
       
    }

    /**
     * Retrieves all categories.
     * @return A ResponseEntity containing the list of all categories.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getAllCategories(){
        List<Category> categories = categoryService.getCategories();  // Fetch and return all categories
        return new ResponseEntity<>(ApiResponse.success(categories), HttpStatus.OK); 
    }

    /**
     * Updates an existing category.
     * @param id The ID of the category to update.
     * @param categoryDTO The updated category details.
     * @return A ResponseEntity containing the updated category.
     */
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
          // Update the category
        try {
            Category updatedCategory = categoryService.updateCategory(id, categoryDTO);  
            return new ResponseEntity<>(ApiResponse.success(updatedCategory), HttpStatus.OK);
        } catch (NotFoundException nE) {
            return NotFoundException.handleNotFoundException(nE);  
        } 
    }

    /**
     * Deletes a category by its ID.
     * @param id The ID of the category to delete.
     * @return A ResponseEntity containing the result of the deletion.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);  // Delete the category using the service
        return new ResponseEntity<>(ApiResponse.success("Category deleted successfully"), HttpStatus.NO_CONTENT);  // Return 204 No Content
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>>handleValidationException(MethodArgumentNotValidException ex){
        return BindExceptionHandler.handleBindException(ex);
    }
}

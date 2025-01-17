package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.Category;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.CategoryDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service.CategoryService;

/**
 * This controller handles CRUD operations for the Category entity in the waste management system.
 * It exposes RESTful API endpoints for creating, retrieving, updating, and deleting categories.
 */
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;  // Service class for handling business logic related to Category
    private List<Category> categoryList = new ArrayList<>(); // Temporary list to store categories for random selection
    private static 
    /**
     * Endpoint to add a new category.
     * @param categoryDTO The category data transfer object containing information about the category.
     * @return A ResponseEntity containing the saved Category object.
     */
    @PostMapping
    public <T> ResponseEntity<T> addCategory(@RequestBody CategoryDTO categoryDTO){
        Category savedCategory = categoryService.saveCategory(categoryDTO);  // Save the category using the service
        return ResponseEntity.ok(savedCategory);  // Return the saved category in the response with HTTP status 200 OK
    }

    /**
     * Endpoint to get a category by its ID.
     * @param id The ID of the category to be retrieved.
     * @return A ResponseEntity containing the Category if found, or 404 if not found.
     */
    @GetMapping("{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
        Optional<Category> category = categoryService.getCategoryById(id);  // Fetch the category by ID from the service
        return ResponseEntity.ok(category.get());  // Return the category in the response with HTTP status 200 OK
    }

    /**
     * Endpoint to get all categories.
     * @return A ResponseEntity containing a list of all categories.
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getCategories());  // Return a list of all categories from the service
    }

    /**
     * Endpoint to get a random category.
     * @return A ResponseEntity containing a random category, or a 404 if no categories are found.
     */
    @GetMapping("rand")
    public ResponseEntity<Category> getRandomCategory() {
        // If the list of categories is empty, load all categories from the service
        if (categoryList.isEmpty()) {
            categoryList = categoryService.getCategories(); 
        }

        // Generate a random index to pick a random category from the list
        Long id = categoryList.get(new Random().nextInt(categoryList.size())).getId();

        // Retrieve the randomly selected category by ID
        Optional<Category> category = categoryService.getCategoryById(id); 
        // Return the category if found, otherwise return a 404 Not Found response
        return category.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build()); 
    }

    /**
     * Endpoint to update an existing category.
     * @param id The ID of the category to be updated.
     * @param categoryDTO The data transfer object containing updated information for the category.
     * @return A ResponseEntity containing the updated Category object.
     */
    @PutMapping("{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));  // Call the service to update the category
    }

    /**
     * Endpoint to delete a category by its ID.
     * @param id The ID of the category to be deleted.
     * @return A ResponseEntity containing the updated list of categories after deletion.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<List<Category>> deleteCategory(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.deleteCategory(id));  // Call the service to delete the category
    }
}

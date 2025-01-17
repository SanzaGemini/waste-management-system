package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exceptions.NotFoundException;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.Category;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.CategoryDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;  // Repository for interacting with the database for Category entities

    /**
     * Saves a new category to the database.
     * 
     * @param categoryDTO The data transfer object containing category details to be saved.
     * @return The saved Category entity.
     * @throws NotFoundException if the category could not be saved.
     */
    @Transactional
    public Category saveCategory(CategoryDTO categoryDTO) {
        // Convert CategoryDTO to Category entity
        Category category = new Category(categoryDTO);

        // Save the category to the database
        Category savedCategory = categoryRepository.save(category);

        // If saving fails, throw an exception
        if (savedCategory == null) {
            throw new NotFoundException("Category could not be saved");
        }

        // Return the saved Category object
        return savedCategory;
    }

    /**
     * Retrieves a category by its ID.
     * 
     * @param id The ID of the category to retrieve.
     * @return An Optional containing the found Category, or an empty Optional if not found.
     * @throws NotFoundException if the category with the specified ID could not be found.
     */
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryById(Long id) {
        // Fetch the category from the repository
        Optional<Category> category = categoryRepository.findById(id);

        // If the category is not found, throw a NotFoundException
        if (!category.isPresent()) {
            throw new NotFoundException("Category could not be found");
        }

        // Return the found category
        return category;
    }

    /**
     * Retrieves all categories from the database.
     * 
     * @return A list of all categories.
     */
    @Transactional(readOnly = true)
    public List<Category> getCategories() {
        // Return all categories from the repository
        return categoryRepository.findAll();
    }

    /**
     * Updates an existing category.
     * 
     * @param id The ID of the category to update.
     * @param categoryDTO The new data for the category.
     * @return The updated Category entity.
     * @throws NotFoundException if the category with the specified ID could not be found.
     */
    @Transactional
    public Category updateCategory(Long id, CategoryDTO categoryDTO) {
        // Find the existing category by ID
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found for update"));

        // Update the category entity with the new data
        category.updateCategory(categoryDTO);

        // Save and return the updated category
        return categoryRepository.save(category);
    }

    /**
     * Deletes a category by its ID.
     * 
     * @param id The ID of the category to delete.
     * @return A list of all remaining categories after the deletion.
     * @throws NotFoundException if the category with the specified ID could not be found for deletion.
     */
    @Transactional
    public List<Category> deleteCategory(Long id) {
        // Find the category to delete
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found for deletion"));

        // Remove the category from the database
        categoryRepository.delete(category);

        // Return the list of remaining categories after deletion
        return categoryRepository.findAll();
    }
}
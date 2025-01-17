package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exceptions.NotFoundException;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.Category;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.CategoryDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.repository.CategoryRepository;

import jakarta.persistence.EntityManager;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;  // Repository for interacting with the database for Category entities
    
    @Autowired
    private EntityManager eManager;  // EntityManager for managing transactions and entity operations

    /**
     * Saves a new category to the database.
     * 
     * @param categoryDTO The data transfer object containing category details to be saved.
     * @return The saved Category entity.
     * @throws NotFoundException if the category could not be saved.
     */
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
    public Optional<Category> getCategoryById(Long id) {
        // Fetch the category from the repository
        Optional<Category> category = categoryRepository.findById(id);
        
        // If the category is not found, throw a NotFoundException
        if (category.get()==null) {
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
    public Category updateCategory(Long id, CategoryDTO categoryDTO) {
        // Begin transaction to update the category
        eManager.getTransaction().begin();

        // Find the existing category by ID
        Category category = eManager.find(Category.class, id);

        // If category does not exist, throw a NotFoundException
        if (category == null) {
            throw new NotFoundException("Category not found for update");
        }

        // Update the category entity with the new data
        category.updateCategory(categoryDTO);

        // Merge the updated entity into the persistence context and commit the transaction
        eManager.merge(category);
        eManager.getTransaction().commit();

        // Return the updated category
        return getCategoryById(id).get();
    }

    /**
     * Deletes a category by its ID.
     * 
     * @param id The ID of the category to delete.
     * @return A list of all remaining categories after the deletion.
     * @throws NotFoundException if the category with the specified ID could not be found for deletion.
     */
    public List<Category> deleteCategory(Long id) {
        // Begin transaction to delete the category
        eManager.getTransaction().begin();

        // Find the category to delete
        Category category = eManager.find(Category.class, id);

        // If category does not exist, throw a NotFoundException
        if (category == null) {
            throw new NotFoundException("Category not found for deletion");
        }

        // Remove the category from the database
        eManager.remove(category);
        eManager.getTransaction().commit();

        // Return the list of remaining categories after deletion
        return getCategories();
    }
}

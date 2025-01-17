package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exception.NotFoundException;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.model.Category;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.model.CategoryDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.repository.CategoryRepository;

import jakarta.persistence.EntityManager;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;  // Repository to interact with the database for Category entities
    
    @Autowired
    private EntityManager eManager;  // EntityManager for manual transaction control and interaction with the persistence context

    /**
     * Saves a new category.
     * 
     * @param categoryDTO The Data Transfer Object containing category details to be saved.
     * @return The saved Category entity.
     * @throws NotFoundException if the category cannot be saved.
     */
    public Category saveCategory(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO);
        
        Category savedCategory = categoryRepository.save(category);
        
        // If the savedCategory is null, throw an exception
        if (savedCategory == null) {
            throw new NotFoundException("Category could not be saved", null);
        }
    
        return savedCategory;  // Return the saved Category object
    }

    /**
     * Retrieves a category by its ID.
     * 
     * @param id The ID of the category to retrieve.
     * @return An Optional containing the found Category or an empty Optional if not found.
     */
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    /**
     * Retrieves all categories.
     * 
     * @return A list of all categories in the system.
     */
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Updates an existing category.
     * 
     * @param id The ID of the category to update.
     * @param categoryDTO The new data for the category.
     * @return The updated Category entity.
     */
    public Category updateCategory(Long id, CategoryDTO categoryDTO) {
        eManager.getTransaction().begin();
        Category category = eManager.find(Category.class, id);
        category.updateCategory(categoryDTO);
        eManager.merge(category);
        eManager.getTransaction().commit();
        return getCategoryById(id).get();
    }

    /**
     * Deletes a category by its ID.
     * 
     * @param id The ID of the category to delete.
     * @return A list of all categories remaining after the deletion.
     */
    public List<Category> deleteCategory(Long id) {
        eManager.getTransaction().begin();
        eManager.remove(eManager.find(Category.class, id));
        eManager.getTransaction().commit();
        return getCategories();
    }
}

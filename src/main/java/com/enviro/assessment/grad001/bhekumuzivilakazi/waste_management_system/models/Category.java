package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The Category entity represents a category in the waste management system.
 * It is mapped to the "Categories" table in the database.
 */
@Entity
@Table(name = "Categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;  // Unique identifier for the category

    @Column(name = "Title")
    private String title;  // Title of the category

    /**
     * Default constructor for the Category entity.
     */
    public Category() {
    }

    /**
     * Constructor to create a Category entity from a CategoryDTO object.
     * 
     * @param categoryDTO The CategoryDTO object containing the category's data.
     */
    public Category(CategoryDTO categoryDTO) {
        this.title = categoryDTO.getTitle();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Updates the Category entity using data from a CategoryDTO object.
     * This method only updates the fields if the new values differ from the current values.
     * 
     * @param categoryDTO The CategoryDTO object containing the updated data.
     */
    public void updateCategory(CategoryDTO categoryDTO) {
        // Update title if the new value is different from the current one and not null
        if (categoryDTO.getTitle() != null && !categoryDTO.getTitle().equals(this.title)) {
            this.title = categoryDTO.getTitle();
        }
    }
}

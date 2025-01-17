package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for the Category entity.
 * This class is used to transfer category data between layers (e.g., from controller to service).
 */
public class CategoryDTO {

    @NotNull(groups = CreateGroup.class, message = "Title cannot be null during Adding")
    @Size(min = 2, groups = CreateGroup.class, message = "Title must have at least 2 characters")
    private String title;  // The title of the category

    /**
     * Gets the title of the category.
     * 
     * @return The title of the category.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the category.
     * 
     * @param title The title of the category.
     */
    public void setTitle(String title) {
        this.title = title;
    }
}

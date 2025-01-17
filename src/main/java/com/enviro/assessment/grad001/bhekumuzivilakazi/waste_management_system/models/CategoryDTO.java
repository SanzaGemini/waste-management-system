package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models;

/**
 * Data Transfer Object (DTO) for the Category entity.
 * This class is used to transfer category data between layers (e.g., from controller to service).
 */
public class CategoryDTO {

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

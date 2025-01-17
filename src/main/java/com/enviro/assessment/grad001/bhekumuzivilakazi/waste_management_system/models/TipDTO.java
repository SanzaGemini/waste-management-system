package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for the Tip entity.
 * This class is used to transfer the tip's data between layers (e.g., from controller to service).
 */
public class TipDTO {
    
    @NotNull(groups = CreateGroup.class, message = "Title cannot be null during Adding")
    @NotBlank(groups = CreateGroup.class, message =  "Title cannot be blank during Adding")
    @Size(min = 2, groups = CreateGroup.class, message = "Title must have at least 2 characters")
    private String title;  // The title of the tip

    @NotNull(groups = CreateGroup.class, message = "Description cannot be null during Adding")
    @NotBlank(groups = CreateGroup.class, message =  "Description cannot be blank during Adding")
    @Size(min = 2, groups = CreateGroup.class, message = "Description must have at least 2 characters")
    private String information;  // The description or information about the tip

    /**
     * Gets the title of the tip.
     * 
     * @return The title of the tip.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the tip.
     * 
     * @param title The title of the tip.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the information (description) of the tip.
     * 
     * @return The information of the tip.
     */
    public String getInformation() {
        return information;
    }

    /**
     * Sets the information (description) of the tip.
     * 
     * @param information The description or information about the tip.
     */
    public void setInformation(String information) {
        this.information = information;
    }
}

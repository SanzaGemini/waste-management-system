package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.model;

/**
 * Data Transfer Object (DTO) for the Guideline entity.
 * This class is used to transfer guideline data between layers (e.g., from controller to service).
 */
public class GuidelineDTO {

    private String title;  // The title of the guideline
    private String information;  // The description or information about the guideline

    /**
     * Gets the title of the guideline.
     * 
     * @return The title of the guideline.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the guideline.
     * 
     * @param title The title of the guideline.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the information (description) of the guideline.
     * 
     * @return The information of the guideline.
     */
    public String getInformation() {
        return information;
    }

    /**
     * Sets the information (description) of the guideline.
     * 
     * @param information The description or information about the guideline.
     */
    public void setInformation(String information) {
        this.information = information;
    }
}


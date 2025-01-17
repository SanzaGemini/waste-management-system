package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.model;

/**
 * Data Transfer Object (DTO) for the Tip entity.
 * This class is used to transfer the tip's data between layers (e.g., from controller to service).
 */
public class TipDTO {

    private String title;  // The title of the tip
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

package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The Guideline entity represents a guideline in the waste management system.
 * It is mapped to the "Guidelines" table in the database.
 */
@Entity
@Table(name = "Guidelines")
public class Guideline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;  // Unique identifier for the guideline

    @Column(name = "Title")
    private String title;  // Title of the guideline

    @Column(name = "Information")
    private String information;  // Description or information about the guideline

    /**
     * Default constructor for the Guideline entity.
     */
    public Guideline() {
    }

    /**
     * Constructor to create a Guideline entity from a GuidelineDTO object.
     * 
     * @param guidelineDTO The GuidelineDTO object containing the guideline's data.
     */
    public Guideline(GuidelineDTO guidelineDTO) {
        this.title = guidelineDTO.getTitle();
        this.information = guidelineDTO.getInformation();
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    /**
     * Updates the Guideline entity using data from a GuidelineDTO object.
     * This method only updates the fields if the new values differ from the current values.
     * 
     * @param guidelineDTO The GuidelineDTO object containing the updated data.
     */
    public void updateGuideline(GuidelineDTO guidelineDTO) {
        // Update title if the new value is different from the current one
        if (this.title == null || !this.title.equals(guidelineDTO.getTitle())) {
            this.title = guidelineDTO.getTitle();
        }

        // Update information if the new value is different from the current one
        if (this.information == null || !this.information.equals(guidelineDTO.getInformation())) {
            this.information = guidelineDTO.getInformation();
        }
    }
}

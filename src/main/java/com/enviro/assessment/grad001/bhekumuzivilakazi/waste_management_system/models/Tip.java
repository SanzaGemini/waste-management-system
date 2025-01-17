package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The Tip entity represents a tip in the waste management system.
 * It is mapped to the "Tips" table in the database.
 */
@Entity
@Table(name = "Tips")
public class Tip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;  // Unique identifier for the tip

    @Column(name = "Title")
    private String title;  // Title of the tip

    @Column(name = "Description")
    private String information;  // Description or details about the tip

    /**
     * Default constructor for the Tip entity.
     */
    public Tip() {
    }

    /**
     * Constructor to create a Tip entity from a TipDTO object.
     * 
     * @param tipDTO The TipDTO object containing the tip's data.
     */
    public Tip(TipDTO tipDTO) {
        this.title = tipDTO.getTitle();
        this.information = tipDTO.getInformation();
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
     * Updates the Tip entity using data from a TipDTO object.
     * This method only updates the fields if the new values differ from the current values.
     * 
     * @param tipDTO The TipDTO object containing the updated data.
     */
    public void updateTip(TipDTO tipDTO) {
        // Update title if the new value is different from the current one
        if (this.title != tipDTO.getTitle()) {
            this.title = tipDTO.getTitle();
        }
        
        // Update information if the new value is different from the current one
        if (this.information != tipDTO.getInformation()) {
            this.information = tipDTO.getInformation();
        }
    }
}

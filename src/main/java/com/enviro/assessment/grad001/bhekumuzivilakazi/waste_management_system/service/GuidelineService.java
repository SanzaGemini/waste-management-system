package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exception.NotFoundException;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.model.Guideline;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.model.GuidelineDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.repository.GuidelineRepository;

import jakarta.persistence.EntityManager;

@Service
public class GuidelineService {

    @Autowired
    private GuidelineRepository guidelineRepository;  // Repository to interact with the database for Guideline entities
    
    @Autowired
    private EntityManager eManager;  // EntityManager for manual transaction control and interaction with the persistence context

    /**
     * Saves a new guideline.
     * 
     * @param guidelineDTO The Data Transfer Object containing guideline details to be saved.
     * @return The saved Guideline entity.
     * @throws NotFoundException if the guideline cannot be saved.
     */
    public Guideline saveGuideline(GuidelineDTO guidelineDTO) {
        Guideline guideline = new Guideline(guidelineDTO);
        
        Guideline savedGuideline = guidelineRepository.save(guideline);
        
        // If the savedGuideline is null, throw an exception
        if (savedGuideline == null) {
            throw new NotFoundException("Guideline could not be saved", null);
        }
    
        return savedGuideline;  // Return the saved Guideline object
    }

    /**
     * Retrieves a guideline by its ID.
     * 
     * @param id The ID of the guideline to retrieve.
     * @return An Optional containing the found Guideline or an empty Optional if not found.
     */
    public Optional<Guideline> getGuidelineById(Long id) {
        return guidelineRepository.findById(id);
    }

    /**
     * Retrieves all guidelines.
     * 
     * @return A list of all guidelines in the system.
     */
    public List<Guideline> getGuidelines() {
        return guidelineRepository.findAll();
    }

    /**
     * Updates an existing guideline.
     * 
     * @param id The ID of the guideline to update.
     * @param guidelineDTO The new data for the guideline.
     * @return The updated Guideline entity.
     */
    public Guideline updateGuideline(Long id, GuidelineDTO guidelineDTO) {
        eManager.getTransaction().begin();
        Guideline guideline = eManager.find(Guideline.class, id);
        guideline.updateGuideline(guidelineDTO);
        eManager.merge(guideline);
        eManager.getTransaction().commit();
        return getGuidelineById(id).get();
    }

    /**
     * Deletes a guideline by its ID.
     * 
     * @param id The ID of the guideline to delete.
     * @return A list of all guidelines remaining after the deletion.
     */
    public List<Guideline> deleteGuideline(Long id) {
        eManager.getTransaction().begin();
        eManager.remove(eManager.find(Guideline.class, id));
        eManager.getTransaction().commit();
        return getGuidelines();
    }
}

package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exceptions.NotFoundException;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.Guideline;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.GuidelineDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.repository.GuidelineRepository;

@Service
public class GuidelineService {

    @Autowired
    private GuidelineRepository guidelineRepository;  // Repository to interact with the database for Guideline entities

    /**
     * Saves a new guideline.
     * 
     * @param guidelineDTO The Data Transfer Object containing guideline details to be saved.
     * @return The saved Guideline entity.
     * @throws NotFoundException if the guideline cannot be saved.
     */
    @Transactional
    public Guideline saveGuideline(GuidelineDTO guidelineDTO) {
        Guideline guideline = new Guideline(guidelineDTO);
        
        Guideline savedGuideline = guidelineRepository.save(guideline);
        
        // If the savedGuideline is null, throw an exception
        if (savedGuideline == null) {
            throw new NotFoundException("Guideline could not be saved");
        }
    
        return savedGuideline;  // Return the saved Guideline object
    }

    /**
     * Retrieves a guideline by its ID.
     * 
     * @param id The ID of the guideline to retrieve.
     * @return An Optional containing the found Guideline or an empty Optional if not found.
     */
    @Transactional(readOnly = true)
    public Optional<Guideline> getGuidelineById(Long id) {
        // Fetch the category from the repository
        Optional<Guideline> guideline = guidelineRepository.findById(id);

        // If the category is not found, throw a NotFoundException
        if (!guideline.isPresent()) {
            throw new NotFoundException("Category could not be found");
        }

        // Return the found category
        return guideline;
    }

    /**
     * Retrieves all guidelines.
     * 
     * @return A list of all guidelines in the system.
     */
    @Transactional(readOnly = true)
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
    @Transactional
    public Guideline updateGuideline(Long id, GuidelineDTO guidelineDTO) {
        Guideline guideline = guidelineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Guideline not found for update"));

        guideline.updateGuideline(guidelineDTO);
        return guidelineRepository.save(guideline);
    }

    /**
     * Deletes a guideline by its ID.
     * 
     * @param id The ID of the guideline to delete.
     * @return A list of all guidelines remaining after the deletion.
     */
    @Transactional
    public List<Guideline> deleteGuideline(Long id) {
        Guideline guideline = guidelineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Guideline not found for deletion"));

        guidelineRepository.delete(guideline);
        return guidelineRepository.findAll();
    }
}

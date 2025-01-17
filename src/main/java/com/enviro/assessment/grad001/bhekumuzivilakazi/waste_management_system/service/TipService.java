package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exceptions.NotFoundException;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.Tip;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.TipDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.repository.TipRepository;

@Service
public class TipService {

    @Autowired
    private TipRepository tipRepository;  // Repository for interacting with the database for Tip entities

    /**
     * Saves a new tip to the database.
     * 
     * @param tipDTO The Data Transfer Object containing the tip details.
     * @return The saved Tip entity.
     * @throws NotFoundException if the tip could not be saved (e.g., if the save operation fails).
     */
    @Transactional
    public Tip saveTip(TipDTO tipDTO) {
        // Convert TipDTO to Tip entity
        Tip tip = new Tip(tipDTO);
        
        // Save the tip using the repository and handle any potential issues
        Tip savedTip = tipRepository.save(tip);
        
        // If the saved tip is null, throw a NotFoundException (this shouldn't normally happen if saving works)
        if (savedTip == null) {
            throw new NotFoundException("Tip could not be saved");
        }
        
        return savedTip;  // Return the saved Tip entity
    }

    /**
     * Retrieves a tip by its ID.
     * 
     * @param id The ID of the tip to retrieve.
     * @return An Optional containing the found Tip, or an empty Optional if not found.
     * @throws NotFoundException if the tip with the specified ID is not found.
     */
    @Transactional(readOnly = true)
    public Optional<Tip> getTipById(Long id) {
        // Fetch the tip by its ID using the repository
        Optional<Tip> tip = tipRepository.findById(id);

        // If the tip is not found, throw a NotFoundException
        if (!tip.isPresent()) {
            throw new NotFoundException("Tip not found with ID: " + id);
        }

        return tip;  // Return the found tip
    }

    /**
     * Retrieves all tips from the database.
     * 
     * @return A list of all tips in the system.
     */
    @Transactional(readOnly = true)
    public List<Tip> getTips() {
        // Return the list of all tips using the repository
        return tipRepository.findAll();
    }

    /**
     * Updates an existing tip.
     * 
     * @param id The ID of the tip to update.
     * @param tipDTO The new data for the tip.
     * @return The updated Tip entity.
     * @throws NotFoundException if the tip with the specified ID does not exist.
     */
    @Transactional
    public Tip updateTip(Long id, TipDTO tipDTO) {
        // Find the existing tip by ID
        Tip tip = tipRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tip not found for update"));

        // Update the tip with the new data from the DTO
        tip.updateTip(tipDTO);

        // Save and return the updated tip
        return tipRepository.save(tip);
    }

    /**
     * Deletes a tip by its ID.
     * 
     * @param id The ID of the tip to delete.
     * @return A list of all tips remaining after the deletion.
     * @throws NotFoundException if the tip with the specified ID does not exist.
     */
    @Transactional
    public List<Tip> deleteTip(Long id) {
        // Find the tip to delete
        Tip tip = tipRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tip not found for deletion"));

        // Remove the tip from the database
        tipRepository.delete(tip);

        // Return the updated list of tips after deletion
        return tipRepository.findAll();
    }
}

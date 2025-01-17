package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exception.NotFoundException;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.model.Tip;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.model.TipDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.repository.TipRepository;

import jakarta.persistence.EntityManager;

@Service
public class TipService {

    @Autowired
    private TipRepository tipRepository;  // Repository to interact with the database for Tip entities
    
    @Autowired
    private EntityManager eManager;  // EntityManager for manual transaction control and interaction with the persistence context

    /**
     * Saves a new tip.
     * 
     * @param tipDTO The Data Transfer Object containing tip details to be saved.
     * @return The saved Tip entity.
     * @throws NotFoundException if the tip cannot be saved.
     */
    public Tip saveTip(TipDTO tipDTO) {
        // Convert TipDTO to a Tip entity
        Tip tip = new Tip(tipDTO);
        
        // Save the tip using the repository
        Tip savedTip = tipRepository.save(tip);
        
        // If the saved tip is null, throw an exception (this shouldn't normally happen if saving works)
        if (savedTip == null) {
            throw new NotFoundException("Tip could not be saved", null);
        }
        
        return savedTip;  // Return the saved Tip object
    }

    /**
     * Retrieves a tip by its ID.
     * 
     * @param id The ID of the tip to retrieve.
     * @return An Optional containing the found Tip or an empty Optional if not found.
     */
    public Optional<Tip> getTipById(Long id) {
        // Find the tip by ID using the repository
        return tipRepository.findById(id);
    }

    /**
     * Retrieves all tips.
     * 
     * @return A list of all tips in the system.
     */
    public List<Tip> getTips() {
        // Return all tips using the repository
        return tipRepository.findAll();
    }

    /**
     * Updates an existing tip.
     * 
     * @param id The ID of the tip to update.
     * @param tipDTO The new data for the tip.
     * @return The updated Tip entity.
     */
    public Tip updateTip(Long id, TipDTO tipDTO) {
        // Begin transaction for manual entity management
        eManager.getTransaction().begin();
        
        // Find the tip by ID
        Tip tip = eManager.find(Tip.class, id);
        
        // Update the tip with the new data from the DTO
        tip.updateTip(tipDTO);
        
        // Merge the updated tip back into the persistence context
        eManager.merge(tip);
        
        // Commit the transaction to save the changes
        eManager.getTransaction().commit();
        
        // Return the updated tip
        return getTipById(id).get();
    }

    /**
     * Deletes a tip by its ID.
     * 
     * @param id The ID of the tip to delete.
     * @return A list of all tips remaining after the deletion.
     */
    public List<Tip> deleteTip(Long id) {
        // Begin transaction for manual entity management
        eManager.getTransaction().begin();
        
        // Find the tip by ID and remove it from the persistence context
        eManager.remove(eManager.find(Tip.class, id));
        
        // Commit the transaction to finalize the deletion
        eManager.getTransaction().commit();
        
        // Return the updated list of all tips
        return getTips();
    }
}

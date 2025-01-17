package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.model.Tip;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.model.TipDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service.TipService;

/**
 * This controller handles CRUD operations for the Tip entity in the waste management system.
 * It exposes RESTful API endpoints for creating, retrieving, updating, and deleting tips.
 */
@RestController
@RequestMapping("/api/v1/tips")
public class TipController {

    @Autowired
    private TipService tipService;  // Service class for handling business logic related to Tip
    private List<Tip> tipList = new ArrayList<>();  // Temporary list to store tips for random selection

    /**
     * Endpoint to add a new tip.
     * @param tipDTO The tip data transfer object containing information about the tip.
     * @return A ResponseEntity containing the saved Tip object.
     */
    @PostMapping
    public ResponseEntity<Tip> addTip(@RequestBody TipDTO tipDTO){
        Tip savedTip = tipService.saveTip(tipDTO);  // Save the tip using the service
        return ResponseEntity.ok(savedTip);  // Return the saved tip in the response with HTTP status 200 OK
    }

    /**
     * Endpoint to get a tip by its ID.
     * @param id The ID of the tip to be retrieved.
     * @return A ResponseEntity containing the Tip if found, or 404 if not found.
     */
    @GetMapping("{id}")
    public ResponseEntity<Tip> getTipById(@PathVariable Long id){
        Optional<Tip> tip = tipService.getTipById(id);  // Fetch the tip by ID from the service
        return ResponseEntity.ok(tip.get());  // Return the tip in the response with HTTP status 200 OK
    }

    /**
     * Endpoint to get all tips.
     * @return A ResponseEntity containing a list of all tips.
     */
    @GetMapping
    public ResponseEntity<List<Tip>> getAllTips(){
        return ResponseEntity.ok(tipService.getTips());  // Return a list of all tips from the service
    }

    /**
     * Endpoint to get a random tip.
     * @return A ResponseEntity containing a random tip, or a 404 if no tips are found.
     */
    @GetMapping("rand")
    public ResponseEntity<Tip> getRandomTip() {
        // If the list of tips is empty, load all tips from the service
        if (tipList.isEmpty()) {
            tipList = tipService.getTips(); 
        }

        // Generate a random index to pick a random tip from the list
        Long id = tipList.get(new Random().nextInt(tipList.size())).getId();

        // Retrieve the randomly selected tip by ID
        Optional<Tip> tip = tipService.getTipById(id); 
        // Return the tip if found, otherwise return a 404 Not Found response
        return tip.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build()); 
    }

    /**
     * Endpoint to update an existing tip.
     * @param id The ID of the tip to be updated.
     * @param tipDTO The data transfer object containing updated information for the tip.
     * @return A ResponseEntity containing the updated Tip object.
     */
    @PutMapping("{id}")
    public ResponseEntity<Tip> updateTip(@PathVariable Long id, @RequestBody TipDTO tipDTO){
        // Call the service to update the tip and return the updated tip in the response
        return ResponseEntity.ok(tipService.updateTip(id, tipDTO));
    }

    /**
     * Endpoint to delete a tip by its ID.
     * @param id The ID of the tip to be deleted.
     * @return A ResponseEntity containing the updated list of tips after deletion.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<List<Tip>> deleteTip(@PathVariable Long id){
        // Call the service to delete the tip and return the updated list of tips
        return ResponseEntity.ok(tipService.deleteTip(id));
    }
}

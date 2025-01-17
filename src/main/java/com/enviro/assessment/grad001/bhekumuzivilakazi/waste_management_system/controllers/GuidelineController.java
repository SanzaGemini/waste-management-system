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

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.model.Guideline;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.model.GuidelineDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service.GuidelineService;

/**
 * This controller handles CRUD operations for the Guideline entity in the waste management system.
 * It exposes RESTful API endpoints for creating, retrieving, updating, and deleting guidelines.
 */
@RestController
@RequestMapping("/api/v1/guidelines")
public class GuidelineController {

    @Autowired
    private GuidelineService guidelineService;  // Service class for handling business logic related to Guideline
    private List<Guideline> guidelineList = new ArrayList<>();  // Temporary list to store guidelines for random selection

    /**
     * Endpoint to add a new guideline.
     * @param guidelineDTO The guideline data transfer object containing information about the guideline.
     * @return A ResponseEntity containing the saved Guideline object.
     */
    @PostMapping
    public ResponseEntity<Guideline> addGuideline(@RequestBody GuidelineDTO guidelineDTO){
        Guideline savedGuideline = guidelineService.saveGuideline(guidelineDTO);  // Save the guideline using the service
        return ResponseEntity.ok(savedGuideline);  // Return the saved guideline in the response with HTTP status 200 OK
    }

    /**
     * Endpoint to get a guideline by its ID.
     * @param id The ID of the guideline to be retrieved.
     * @return A ResponseEntity containing the Guideline if found, or 404 if not found.
     */
    @GetMapping("{id}")
    public ResponseEntity<Guideline> getGuidelineById(@PathVariable Long id){
        Optional<Guideline> guideline = guidelineService.getGuidelineById(id);  // Fetch the guideline by ID from the service
        return ResponseEntity.ok(guideline.get());  // Return the guideline in the response with HTTP status 200 OK
    }

    /**
     * Endpoint to get all guidelines.
     * @return A ResponseEntity containing a list of all guidelines.
     */
    @GetMapping
    public ResponseEntity<List<Guideline>> getAllGuidelines(){
        return ResponseEntity.ok(guidelineService.getGuidelines());  // Return a list of all guidelines from the service
    }

    /**
     * Endpoint to get a random guideline.
     * @return A ResponseEntity containing a random guideline, or a 404 if no guidelines are found.
     */
    @GetMapping("rand")
    public ResponseEntity<Guideline> getRandomGuideline() {
        // If the list of guidelines is empty, load all guidelines from the service
        if (guidelineList.isEmpty()) {
            guidelineList = guidelineService.getGuidelines(); 
        }

        // Generate a random index to pick a random guideline from the list
        Long id = guidelineList.get(new Random().nextInt(guidelineList.size())).getId();

        // Retrieve the randomly selected guideline by ID
        Optional<Guideline> guideline = guidelineService.getGuidelineById(id); 
        // Return the guideline if found, otherwise return a 404 Not Found response
        return guideline.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build()); 
    }

    /**
     * Endpoint to update an existing guideline.
     * @param id The ID of the guideline to be updated.
     * @param guidelineDTO The data transfer object containing updated information for the guideline.
     * @return A ResponseEntity containing the updated Guideline object.
     */
    @PutMapping("{id}")
    public ResponseEntity<Guideline> updateGuideline(@PathVariable Long id, @RequestBody GuidelineDTO guidelineDTO){
        return ResponseEntity.ok(guidelineService.updateGuideline(id, guidelineDTO));  // Call the service to update the guideline
    }

    /**
     * Endpoint to delete a guideline by its ID.
     * @param id The ID of the guideline to be deleted.
     * @return A ResponseEntity containing the updated list of guidelines after deletion.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<List<Guideline>> deleteGuideline(@PathVariable Long id){
        return ResponseEntity.ok(guidelineService.deleteGuideline(id));  // Call the service to delete the guideline
    }
}

package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exceptions.BindExceptionHandler;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exceptions.NotFoundException;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.CreateGroup;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.Guideline;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.GuidelineDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.response.ApiResponse;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service.GuidelineService;

import jakarta.validation.Valid;

/**
 * This controller handles CRUD operations for the Guideline entity in the waste management system.
 * It exposes RESTful API endpoints for creating, retrieving, updating, and deleting guidelines.
 */
@RestController
@RequestMapping("/api/v1/guidelines")
public class GuidelineController {

    @Autowired
    private GuidelineService guidelineService;

    /**
     * Creates a new guideline.
     * @param guidelineDTO The data transfer object containing guideline details.
     * @param bindingResult Holds validation errors if any.
     * @return A ResponseEntity containing the result of the guideline creation.
     */
    @Validated(CreateGroup.class)
    @PostMapping
    public ResponseEntity<ApiResponse<Object>> addGuideline(@RequestBody @Valid GuidelineDTO guidelineDTO, BindingResult bindingResult) {
        Guideline savedGuideline = guidelineService.saveGuideline(guidelineDTO);  // Save the guideline using the service
        return new ResponseEntity<>(ApiResponse.success(savedGuideline), HttpStatus.CREATED);  // Return 201 Created
    }

    /**
     * Retrieves a guideline by its ID.
     * @param id The ID of the guideline to retrieve.
     * @return A ResponseEntity containing the guideline details, or 404 if not found.
     */
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> getGuidelineById(@PathVariable Long id) {
        try {
            Guideline guideline = guidelineService.getGuidelineById(id).get();
            return new ResponseEntity<>(ApiResponse.success(guideline), HttpStatus.OK);
        } catch (NotFoundException nE) {
            return NotFoundException.handleNotFoundException(nE);
        }
    }

    /**
     * Retrieves all guidelines.
     * @return A ResponseEntity containing the list of all guidelines.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getAllGuidelines() {
        List<Guideline> guidelines = guidelineService.getGuidelines();  // Fetch all guidelines from the service
        return new ResponseEntity<>(ApiResponse.success(guidelines), HttpStatus.OK);
    }

    /**
     * Updates an existing guideline.
     * @param id The ID of the guideline to update.
     * @param guidelineDTO The updated guideline details.
     * @return A ResponseEntity containing the updated guideline.
     */
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> updateGuideline(@PathVariable Long id, @RequestBody GuidelineDTO guidelineDTO) {
        try {
            Guideline updatedGuideline = guidelineService.updateGuideline(id, guidelineDTO);  // Update the guideline using the service
            return new ResponseEntity<>(ApiResponse.success(updatedGuideline), HttpStatus.OK);
        } catch (NotFoundException nE) {
            return NotFoundException.handleNotFoundException(nE);
        }
    }

    /**
     * Deletes a guideline by its ID.
     * @param id The ID of the guideline to delete.
     * @return A ResponseEntity containing the result of the deletion.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> deleteGuideline(@PathVariable Long id) {
        guidelineService.deleteGuideline(id);  // Delete the guideline using the service
        return new ResponseEntity<>(ApiResponse.success("Guideline deleted successfully"), HttpStatus.NO_CONTENT);  // Return 204 No Content
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>>handleValidationException(MethodArgumentNotValidException ex){
        return BindExceptionHandler.handleBindException(ex);
    }
}

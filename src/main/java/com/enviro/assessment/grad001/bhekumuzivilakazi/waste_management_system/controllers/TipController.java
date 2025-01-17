package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.controllers;

import java.util.List;
import java.util.Random;

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
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.Tip;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.TipDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.response.ApiResponse;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service.TipService;

import jakarta.validation.Valid;

/**
 * This controller handles CRUD operations for the Tip entity in the waste management system.
 * It exposes RESTful API endpoints for creating, retrieving, updating, deleting tips, and fetching a random tip.
 */
@RestController
@RequestMapping("/api/v1/tips")
public class TipController {

    @Autowired
    private TipService tipService;

    /**
     * Creates a new tip.
     * @param tipDTO The data transfer object containing tip details.
     * @param bindingResult Holds validation errors if any.
     * @return A ResponseEntity containing the result of the tip creation.
     */
    @Validated(CreateGroup.class)
    @PostMapping
    public ResponseEntity<ApiResponse<Object>> addTip(@RequestBody @Valid TipDTO tipDTO, BindingResult bindingResult) {
        Tip savedTip = tipService.saveTip(tipDTO);  // Save the tip using the service
        return new ResponseEntity<>(ApiResponse.success(savedTip), HttpStatus.CREATED);  // Return 201 Created
    }

    /**
     * Retrieves a tip by its ID.
     * @param id The ID of the tip to retrieve.
     * @return A ResponseEntity containing the tip details, or 404 if not found.
     */
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> getTipById(@PathVariable Long id) {
        try {
            Tip tip = tipService.getTipById(id).get();
            return new ResponseEntity<>(ApiResponse.success(tip), HttpStatus.OK);
        } catch (NotFoundException nE) {
            return NotFoundException.handleNotFoundException(nE);
        } 
    }

    /**
     * Retrieves all tips.
     * @return A ResponseEntity containing the list of all tips.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getAllTips() {
        List<Tip> tips = tipService.getTips();  // Fetch all tips from the service
        return new ResponseEntity<>(ApiResponse.success(tips), HttpStatus.OK);
    }

    /**
     * Retrieves a random tip.
     * @return A ResponseEntity containing a random tip, or a 404 if no tips are found.
     */
    @GetMapping("rand")
    public ResponseEntity<ApiResponse<Object>> getRandomTip() {
        List<Tip> tips = tipService.getTips();
        if (tips.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.failure("No tips available"), HttpStatus.NOT_FOUND);  // Return 404 if no tips exist
        }

        Tip randomTip = tips.get(new Random().nextInt(tips.size()));  // Randomly select a tip
        return new ResponseEntity<>(ApiResponse.success(randomTip), HttpStatus.OK);
    }

    /**
     * Updates an existing tip.
     * @param id The ID of the tip to update.
     * @param tipDTO The updated tip details.
     * @return A ResponseEntity containing the updated tip.
     */
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> updateTip(@PathVariable Long id, @RequestBody TipDTO tipDTO) {
        try {
            Tip updatedTip = tipService.updateTip(id, tipDTO);  // Update the tip using the service
            return new ResponseEntity<>(ApiResponse.success(updatedTip), HttpStatus.OK);
        } catch (NotFoundException nE) {
            return NotFoundException.handleNotFoundException(nE);
        }
    }

    /**
     * Deletes a tip by its ID.
     * @param id The ID of the tip to delete.
     * @return A ResponseEntity containing the result of the deletion.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> deleteTip(@PathVariable Long id) {
        tipService.deleteTip(id);  // Delete the tip using the service
        List<Tip> remainingTips = tipService.getTips();  // Get the updated list after deletion
        return new ResponseEntity<>(ApiResponse.success(remainingTips), HttpStatus.OK);  // Return the remaining tips list
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>>handleValidationException(MethodArgumentNotValidException ex){
        return BindExceptionHandler.handleBindException(ex);
    }
}

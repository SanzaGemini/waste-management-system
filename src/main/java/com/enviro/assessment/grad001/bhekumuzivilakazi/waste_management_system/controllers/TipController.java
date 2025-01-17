package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.controllers;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.CreateGroup;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.Tip;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.TipDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.response.ApiResponse;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service.TipService;

@RestController
@RequestMapping("/api/v1/tips")
public class TipController {

    @Autowired
    private TipService tipService;

    /**
     * Endpoint to add a new tip.
     * @param tipDTO The tip data transfer object containing information about the tip.
     * @return A ResponseEntity containing the saved Tip object.
     */
    @Validated(CreateGroup.class)
    @PostMapping
    public ResponseEntity<ApiResponse<Object>> addTip(@RequestBody TipDTO tipDTO, BindingResult bindingResult) {
        Tip savedTip = tipService.saveTip(tipDTO);  // Save the tip using the service
        return new ResponseEntity<>(ApiResponse.success(savedTip), HttpStatus.CREATED);  // Return 201 Created
    }

    /**
     * Endpoint to get a tip by its ID.
     * @param id The ID of the tip to be retrieved.
     * @return A ResponseEntity containing the Tip if found, or 404 if not found.
     */
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> getTipById(@PathVariable Long id) {
        Tip tip = tipService.getTipById(id).get();
        return new ResponseEntity<>(ApiResponse.success(tip), HttpStatus.OK);
    }

    /**
     * Endpoint to get all tips.
     * @return A ResponseEntity containing a list of all tips.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getAllTips() {
        List<Tip> tips = tipService.getTips();  // Fetch all tips from the service
        return new ResponseEntity<>(ApiResponse.success(tips), HttpStatus.OK);
    }

    /**
     * Endpoint to get a random tip.
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
     * Endpoint to update an existing tip.
     * @param id The ID of the tip to be updated.
     * @param tipDTO The data transfer object containing updated information for the tip.
     * @return A ResponseEntity containing the updated Tip object.
     */
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> updateTip(@PathVariable Long id, @RequestBody TipDTO tipDTO) {
        Tip updatedTip = tipService.updateTip(id, tipDTO);
        return new ResponseEntity<>(ApiResponse.success(updatedTip), HttpStatus.OK);
    }

    /**
     * Endpoint to delete a tip by its ID.
     * @param id The ID of the tip to be deleted.
     * @return A ResponseEntity containing the updated list of tips after deletion.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> deleteTip(@PathVariable Long id) {
        tipService.deleteTip(id);
        List<Tip> remainingTips = tipService.getTips();  // Get the updated list after deletion
        return new ResponseEntity<>(ApiResponse.success(remainingTips), HttpStatus.OK);  // Return the remaining tips list
    }
}

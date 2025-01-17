package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.Integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; // Ensure this is imported


import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.Tip;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.TipDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.repository.TipRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Use a specific profile for testing with an H2 database
public class TipsIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TipRepository tipRepository;

    private TipDTO tipDTO;

    @BeforeEach
    public void setUp() {
        tipRepository.deleteAll(); // Ensure a clean slate for each test

        // Initialize a sample TipDTO
        tipDTO = new TipDTO();
        tipDTO.setTitle("Recycling Tips");
        tipDTO.setInformation("These are some tips for recycling.");
    }

    @Test
    public void testAddTip() throws Exception {
        // Perform the POST request
        mockMvc.perform(post("/api/v1/tips")
                .contentType("application/json")
                .content("{\"title\":\"Recycling Tips\",\"information\":\"These are some tips for recycling.\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Request was successful"))
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"))
                .andExpect(jsonPath("$.data.information").value("These are some tips for recycling."));
    }

    @Test
    public void testGetTipById() throws Exception {
        // Save a tip to the database
        Tip tip = tipRepository.save(new Tip(tipDTO));

        // Perform the GET request
        mockMvc.perform(get("/api/v1/tips/{id}", tip.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Request was successful"))
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"))
                .andExpect(jsonPath("$.data.information").value("These are some tips for recycling."));
    }

    @Test
    public void testGetAllTips() throws Exception {
        // Save multiple tips to the database
        tipRepository.save(new Tip(tipDTO));
        TipDTO tipDTO2 = new TipDTO();
        tipDTO2.setTitle("Composting Tips");
        tipDTO2.setInformation("These are some tips for composting.");
        tipRepository.save(new Tip(tipDTO2));

        // Perform the GET request
        mockMvc.perform(get("/api/v1/tips"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Request was successful"))
                .andExpect(jsonPath("$.data[0].title").value("Recycling Tips"))
                .andExpect(jsonPath("$.data[0].information").value("These are some tips for recycling."))
                .andExpect(jsonPath("$.data[1].title").value("Composting Tips"))
                .andExpect(jsonPath("$.data[1].information").value("These are some tips for composting."));
    }

    @Test
    public void testUpdateTip() throws Exception {
        // Save a tip to the database
        Tip tip = tipRepository.save(new Tip(tipDTO));

        // Perform the PUT request
        mockMvc.perform(put("/api/v1/tips/{id}", tip.getId())
                .contentType("application/json")
                .content("{\"title\":\"Updated Tips\",\"information\":\"Updated information.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Request was successful"))
                .andExpect(jsonPath("$.data.title").value("Updated Tips"))
                .andExpect(jsonPath("$.data.information").value("Updated information."));
    }

    @Test
    public void testDeleteTip() throws Exception {
        // Save a tip to the database
        Tip tip = tipRepository.save(new Tip(tipDTO));

        // Perform the DELETE request
        mockMvc.perform(delete("/api/v1/tips/{id}", tip.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Request was successful"));

        // Ensure the tip is deleted
        assert tipRepository.findById(tip.getId()).isEmpty();
    }

    @Test
    public void testHandleNotFoundException() throws Exception {
        // Perform a GET request with a non-existent ID
        mockMvc.perform(get("/api/v1/tips/{id}", 9999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("failed"))
                .andExpect(jsonPath("$.message").value("Tip not found with ID: 9999"));
    }

    // @Test
    // public void testHandleBindException() throws Exception {
    //     // Perform a POST request with invalid data
    //     mockMvc.perform(post("/api/v1/tips")
    //             .contentType("application/json")
    //             .content("{\"title\":\"\"}")) // Invalid data: title is empty
    //             .andExpect(status().isBadRequest())
    //             .andExpect(jsonPath("$.status").value("error"))
    //             .andExpect(jsonPath("$.message").exists());
    // }
}
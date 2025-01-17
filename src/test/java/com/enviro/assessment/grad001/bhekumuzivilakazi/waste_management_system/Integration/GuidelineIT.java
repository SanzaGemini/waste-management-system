package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.Integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.Guideline;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.GuidelineDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.repository.GuidelineRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class GuidelineIT {

    @Autowired
    private MockMvc mockMvc;

    // Use @MockBean to create a mock of GuidelineService
    @Autowired
    private GuidelineRepository guidelineRepository;

    private GuidelineDTO guidelineDTO;

    @BeforeEach
    public void setUp() {
        // Set up a sample GuidelineDTO object
        guidelineDTO = new GuidelineDTO();
        guidelineDTO.setTitle("Recycling Tips");
        guidelineDTO.setInformation("These are some tips for recycling.");
    }

    @AfterEach
    public void tearDown(){
        guidelineRepository.deleteAll();
    }

    @Test
    public void testGetGuidelineById() throws Exception {
        // Create a Guideline object
        Guideline guideline = guidelineRepository.save(new Guideline(guidelineDTO));

        // Perform the GET request
        mockMvc.perform(get("/api/v1/guidelines/{id}", guideline.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"))  // Check the title in the data
                .andExpect(jsonPath("$.data.information").value("These are some tips for recycling."));  // Check the information in the data
    }

    @Test
    public void testAddGuideline() throws Exception {
   
        // Perform the POST request
        mockMvc.perform(post("/api/v1/guidelines")
                .contentType("application/json")
                .content("{\"title\":\"Recycling Tips\",\"information\":\"These are some tips for recycling.\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"))  // Check the title in the data
                .andExpect(jsonPath("$.data.information").value("These are some tips for recycling."));  // Check the information in the data
    }

    @Test 
    public void testGetAllGuidelines() throws Exception {
        // Create two distinct Guideline objects 
        guidelineRepository.save(new Guideline(guidelineDTO));
        
        GuidelineDTO guidelineDTO2 = new GuidelineDTO(); 
        guidelineDTO2.setTitle("Composting Tips"); 
        guidelineDTO2.setInformation("These are some tips for composting."); 
        guidelineRepository.save(new Guideline(guidelineDTO2));
        
        // Perform the GET request
        mockMvc.perform(get("/api/v1/guidelines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data[0].title").value("Recycling Tips"))  // Check the title in the first guideline
                .andExpect(jsonPath("$.data[0].information").value("These are some tips for recycling."))  // Check the information in the first guideline
                .andExpect(jsonPath("$.data[1].title").value("Composting Tips"))  // Check the title in the second guideline
                .andExpect(jsonPath("$.data[1].information").value("These are some tips for composting."));  // Check the information in the second guideline
    }

    @Test 
    public void testUpdateGuidelines() throws Exception {
        // Create the original and updated Guideline objects
        Guideline guideline = guidelineRepository.save(new Guideline(guidelineDTO));
        // Perform the PUT request
        mockMvc.perform(put("/api/v1/guidelines/{id}", guideline.getId())
                .contentType("application/json")
                .content("{\"title\":\"Recycling Tips\",\"information\":\"There has been Changes Made To The Guideline.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"))  // Check the title in the updated guideline
                .andExpect(jsonPath("$.data.information").value("There has been Changes Made To The Guideline."));  // Check the information in the updated guideline
    }

    @Test 
    public void testDeleteGuideline() throws Exception {
        // Create the guideline to be deleted
        Guideline guideline = guidelineRepository.save(new Guideline(guidelineDTO));
        // Perform the DELETE request
        mockMvc.perform(delete("/api/v1/guidelines/{id}", guideline.getId()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data").value("Guideline deleted successfully")); // Check the remaining guideline title
                
    }

    @Test
    public void testHandleNotFoundException() throws Exception {
        // Simulate a NotFoundException by calling an endpoint that will throw the exception
        mockMvc.perform(get("/api/v1/guidelines/{id}",99)) // Use an ID that doesn't exist
                .andExpect(status().isNotFound()) // Expect 404 Not Found
                .andExpect(jsonPath("$.status").value("failed"))
                .andExpect(jsonPath("$.message").value("Category could not be found")); // Replace with your specific message
    }

    // @Test
    // public void testHandleBindException() throws Exception {
    //     // Simulate a request that will trigger a BindException
    //     mockMvc.perform(post("/api/v1/guidelines") // Assuming this endpoint requires a body
    //             .contentType("application/json")
    //             .content("{\"title\": \"\"}")) // Assuming title is required but empty
    //             .andExpect(status().isBadRequest()) // Expect 400 Bad Request
    //             .andExpect(jsonPath("$.title").value("Title is required")); // Replace with your specific validation message
    // }

}

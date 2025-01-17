package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.Integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.Guideline;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.GuidelineDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service.GuidelineService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class GuidelineIT {

    @Autowired
    private MockMvc mockMvc;

    // Use @MockBean to create a mock of GuidelineService
    @MockBean
    private GuidelineService guidelineService;

    private GuidelineDTO guidelineDTO;

    @BeforeEach
    public void setUp() {
        // Set up a sample GuidelineDTO object
        guidelineDTO = new GuidelineDTO();
        guidelineDTO.setTitle("Recycling Tips");
        guidelineDTO.setInformation("These are some tips for recycling.");
    }

    @Test
    public void testGetGuidelineById() throws Exception {
        // Create a Guideline object
        Guideline guideline = new Guideline(guidelineDTO);
        guideline.setId(1L);

        // Mocking the service to return the guideline by ID
        when(guidelineService.getGuidelineById(1L)).thenReturn(Optional.of(guideline));

        // Perform the GET request
        mockMvc.perform(get("/api/v1/guidelines/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"))  // Check the title in the data
                .andExpect(jsonPath("$.data.information").value("These are some tips for recycling."));  // Check the information in the data
    }

    @Test
    public void testAddGuideline() throws Exception {
        // Create a Guideline object
        Guideline savedGuideline = new Guideline(guidelineDTO);
        savedGuideline.setId(1L);

        // Mocking the save operation
        when(guidelineService.saveGuideline(any(GuidelineDTO.class))).thenReturn(savedGuideline);

        // Perform the POST request
        mockMvc.perform(post("/api/v1/guidelines")
                .contentType("application/json")
                .content("{\"title\":\"Recycling Tips\",\"information\":\"These are some tips for recycling.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"))  // Check the title in the data
                .andExpect(jsonPath("$.data.information").value("These are some tips for recycling."));  // Check the information in the data
    }

    @Test 
    public void testGetAllGuidelines() throws Exception {
        // Create two distinct Guideline objects 
        Guideline guideline1 = new Guideline(guidelineDTO); guideline1.setId(1L); 
        
        GuidelineDTO guidelineDTO2 = new GuidelineDTO(); 
        guidelineDTO2.setTitle("Composting Tips"); 
        guidelineDTO2.setInformation("These are some tips for composting."); 
        Guideline guideline2 = new Guideline(guidelineDTO2); 
        guideline2.setId(2L);
        
        // Mock the service to return a list of guidelines
        when(guidelineService.getGuidelines()).thenReturn(List.of(guideline1, guideline2));

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
        Guideline guideline1 = new Guideline(guidelineDTO); guideline1.setId(1L); 
        
        GuidelineDTO guidelineDTO2 = new GuidelineDTO(); 
        guidelineDTO2.setTitle("Recycling Tips"); 
        guidelineDTO2.setInformation("There has been Changes Made To The Guideline."); 
        Guideline guideline2 = new Guideline(guidelineDTO2); 
        guideline2.setId(2L);

        // Mock the service to return the updated guideline
        when(guidelineService.updateGuideline(1L, guidelineDTO2)).thenReturn(guideline2); 
        
        // Perform the PUT request
        mockMvc.perform(put("/api/v1/guidelines/{id}", 1L)
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
        Guideline guideline1 = new Guideline(guidelineDTO); guideline1.setId(1L); 
        
        GuidelineDTO guidelineDTO2 = new GuidelineDTO(); 
        guidelineDTO2.setTitle("Composting Tips"); 
        guidelineDTO2.setInformation("These are some tips for composting."); 
        Guideline guideline2 = new Guideline(guidelineDTO2); 
        guideline2.setId(2L);

        // Mock the service to return the remaining guideline after deletion
        when(guidelineService.deleteGuideline(2L)).thenReturn(List.of(guideline1));

        // Perform the DELETE request
        mockMvc.perform(delete("/api/v1/guidelines/{id}", 2L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Guideline deleted successfully"))  // Check the message
                .andExpect(jsonPath("$.data[0].title").value("Recycling Tips"))  // Check the remaining guideline title
                .andExpect(jsonPath("$.data[0].information").value("These are some tips for recycling."))  // Check the remaining guideline information
                .andExpect(jsonPath("$.data.length()").value(1));  // Ensure only one guideline remains
    }

    @Test
    public void testHandleNotFoundException() throws Exception {
        // Simulate a NotFoundException by calling an endpoint that will throw the exception
        mockMvc.perform(get("/api/v1/guidelines/{id}",99)) // Use an ID that doesn't exist
                .andExpect(status().isNotFound()) // Expect 404 Not Found
                .andExpect(content().string("Tip not found with ID 9999")); // Replace with your specific message
    }

    @Test
    public void testHandleBindException() throws Exception {
        // Simulate a request that will trigger a BindException
        mockMvc.perform(post("/api/v1/guidelines") // Assuming this endpoint requires a body
                .contentType("application/json")
                .content("{\"title\": \"\"}")) // Assuming title is required but empty
                .andExpect(status().isBadRequest()) // Expect 400 Bad Request
                .andExpect(jsonPath("$.title").value("Title is required")); // Replace with your specific validation message
    }

    @Test
    public void testFullGuidelineFlow() throws Exception {
        // Create a Guideline object
        Guideline guideline = new Guideline(guidelineDTO);
        guideline.setId(1L);

        when(guidelineService.saveGuideline(any(GuidelineDTO.class))).thenReturn(guideline);
        when(guidelineService.getGuidelineById(any(Long.class))).thenReturn(Optional.of(guideline));

        // Create a new guideline
        mockMvc.perform(post("/api/v1/guidelines")
                .contentType("application/json")
                .content("{\"title\":\"Recycling Tips\",\"information\":\"These are some tips for recycling.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Request was successful"))
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"))
                .andExpect(jsonPath("$.data.information").value("These are some tips for recycling."));
        
        // Retrieve the newly created guideline by ID
        mockMvc.perform(get("/api/v1/guidelines/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Request was successful"))
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"))
                .andExpect(jsonPath("$.data.information").value("These are some tips for recycling."));
    }
}

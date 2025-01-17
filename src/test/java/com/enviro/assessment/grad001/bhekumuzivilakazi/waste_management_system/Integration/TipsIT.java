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

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.Tip;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.TipDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service.TipService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class TipsIT {

    @Autowired
    private MockMvc mockMvc;

    // Use @MockBean to create a mock of TipService
    @MockBean
    private TipService tipService;

    private TipDTO tipDTO;

    @BeforeEach
    public void setUp() {
        // Set up a sample TipDTO object
        tipDTO = new TipDTO();
        tipDTO.setTitle("Recycling Tips");
        tipDTO.setInformation("These are some tips for recycling.");
    }

    @Test
    public void testGetTipById() throws Exception {
        // Create a Tip object
        Tip tip = new Tip(tipDTO);
        tip.setId(1L);

        // Use Mockito to mock the service method's behavior
        when(tipService.getTipById(1L)).thenReturn(Optional.of(tip));

        // Perform the GET request
        mockMvc.perform(get("/api/v1/tips/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"))  // Check the title in the data
                .andExpect(jsonPath("$.data.information").value("These are some tips for recycling."));  // Check the information in the data
    }

    @Test
    public void testAddTip() throws Exception {
        // Create a Tip object
        Tip savedTip = new Tip(tipDTO);
        savedTip.setId(1L);

        // Mocking the save operation
        when(tipService.saveTip(any(TipDTO.class))).thenReturn(savedTip);

        // Perform the POST request
        mockMvc.perform(post("/api/v1/tips")
                .contentType("application/json")
                .content("{\"title\":\"Recycling Tips\",\"information\":\"These are some tips for recycling.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"))  // Check the title in the data
                .andExpect(jsonPath("$.data.information").value("These are some tips for recycling."));  // Check the information in the data
    }

    @Test 
    public void testGetAllTips() throws Exception {
        // Create two distinct Tip objects 
        Tip tip1 = new Tip(tipDTO); tip1.setId(1L); 
        
        TipDTO tipDTO2 = new TipDTO(); 
        tipDTO2.setTitle("Composting Tips"); 
        tipDTO2.setInformation("These are some tips for composting."); 
        Tip tip2 = new Tip(tipDTO2); 
        tip2.setId(2L);
        
        // Mock the service to return a list of tips
        when(tipService.getTips()).thenReturn(List.of(tip1, tip2));

        // Perform the GET request
        mockMvc.perform(get("/api/v1/tips"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data[0].title").value("Recycling Tips"))  // Check the title in the first tip
                .andExpect(jsonPath("$.data[0].information").value("These are some tips for recycling."))  // Check the information in the first tip
                .andExpect(jsonPath("$.data[1].title").value("Composting Tips"))  // Check the title in the second tip
                .andExpect(jsonPath("$.data[1].information").value("These are some tips for composting."));  // Check the information in the second tip
    }

    @Test 
    public void testUpdateTip() throws Exception {
        // Create the original and updated Tip objects
        Tip tip1 = new Tip(tipDTO); tip1.setId(1L); 
        
        TipDTO tipDTO2 = new TipDTO(); 
        tipDTO2.setTitle("Recycling Tips"); 
        tipDTO2.setInformation("There has been changes made to the guideline."); 
        Tip tip2 = new Tip(tipDTO2); 
        tip2.setId(2L);

        // Mock the service to return the updated tip
        when(tipService.updateTip(1L, tipDTO2)).thenReturn(tip2); 
        
        // Perform the PUT request
        mockMvc.perform(put("/api/v1/tips/{id}", 1L)
                .contentType("application/json")
                .content("{\"title\":\"Recycling Tips\",\"information\":\"There has been changes made to the guideline.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"))  // Check the updated title
                .andExpect(jsonPath("$.data.information").value("There has been changes made to the guideline."));  // Check the updated information
    }

    @Test 
    public void testDeleteTip() throws Exception {
        // Create the guideline to be deleted
        Tip tip1 = new Tip(tipDTO); tip1.setId(1L); 
        
        TipDTO tipDTO2 = new TipDTO(); 
        tipDTO2.setTitle("Composting Tips"); 
        tipDTO2.setInformation("These are some tips for composting."); 
        Tip tip2 = new Tip(tipDTO2); 
        tip2.setId(2L);

        // Mock the service to return the remaining tip after deletion
        when(tipService.deleteTip(2L)).thenReturn(List.of(tip1)); 

        // Perform the DELETE request
        mockMvc.perform(delete("/api/v1/tips/{id}", 2L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Tip deleted successfully"))  // Check the message
                .andExpect(jsonPath("$.data[0].title").value("Recycling Tips"))  // Check the remaining tip's title
                .andExpect(jsonPath("$.data[0].information").value("These are some tips for recycling."))  // Check the remaining tip's information
                .andExpect(jsonPath("$.data.length()").value(1));  // Ensure only one tip remains
    }

    @Test
    public void testHandleNotFoundException() throws Exception {
        // Simulate a NotFoundException by calling an endpoint that will throw the exception
        mockMvc.perform(get("/api/v1/tips/9999")) // Use an ID that doesn't exist
                .andExpect(status().isNotFound()) // Expect 404 Not Found
                .andExpect(content().string("Tip not found with ID 9999")); // Replace with your specific message
    }

    @Test
    public void testHandleBindException() throws Exception {
        // Simulate a request that will trigger a BindException
        mockMvc.perform(post("/api/v1/tips") // Assuming this endpoint requires a body
                .contentType("application/json")
                .content("{\"title\": \"\"}")) // Assuming title is required but empty
                .andExpect(status().isBadRequest()) // Expect 400 Bad Request
                .andExpect(jsonPath("$.title").value("Title is required")); // Replace with your specific validation message
    }

    @Test
    public void testFullTipFlow() throws Exception {
        // Create a Tip object
        Tip tip = new Tip(tipDTO);
        tip.setId(1L);

        when(tipService.saveTip(any(TipDTO.class))).thenReturn(tip);
        when(tipService.getTipById(any(Long.class))).thenReturn(Optional.of(tip));

        // Create a new tip
        mockMvc.perform(post("/api/v1/tips")
                .contentType("application/json")
                .content("{\"title\":\"Recycling Tips\",\"information\":\"These are some tips for recycling.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Request was successful"))
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"))
                .andExpect(jsonPath("$.data.information").value("These are some tips for recycling."));
        
        // Retrieve the newly created tip by ID
        mockMvc.perform(get("/api/v1/tips/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Request was successful"))
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"))
                .andExpect(jsonPath("$.data.information").value("These are some tips for recycling."));
    }
}

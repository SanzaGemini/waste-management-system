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

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.Category;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.CategoryDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.repository.CategoryRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class CategoryIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    private CategoryDTO categoryDTO;

    @BeforeEach
    public void setUp() {
        categoryDTO = new CategoryDTO();
        categoryDTO.setTitle("Recycling Tips");
    }

    @AfterEach
    public void tearDown(){
        categoryRepository.deleteAll();
    }

    @Test
    public void testGetCategoryById() throws Exception {
        // Create a Category object
        Category category = categoryRepository.save(new Category(categoryDTO));
        

        // Perform the GET request
        mockMvc.perform(get("/api/v1/categories/{id}", category.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"));  // Check the category title in the data
    }

    @Test
    public void testAddCategory() throws Exception {

        // Perform the POST request
        mockMvc.perform(post("/api/v1/categories")
                .contentType("application/json")
                .content("{\"title\":\"Recycling Tips\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"));  // Check the category title in the data
    }

    @Test
    public void testGetAllCategories() throws Exception {
        // Create two distinct Category objects
        categoryRepository.save(new Category(categoryDTO));


        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setTitle("Composting Tips");
        categoryRepository.save(new Category(categoryDTO2));

        // Perform the GET request
        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data[0].title").value("Recycling Tips"))  // Check the first category title
                .andExpect(jsonPath("$.data[1].title").value("Composting Tips"));  // Check the second category title
    }

    @Test
    public void testUpdateCategory() throws Exception {
        // Create Category and DTO for update
        Category category = categoryRepository.save(new Category(categoryDTO));

        
        // Perform the PUT request
        mockMvc.perform(put("/api/v1/categories/{id}", category.getId())
                .contentType("application/json")
                .content("{\"title\":\"Updated Recycling Tips\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data.title").value("Updated Recycling Tips"));  // Check the updated category title in the data
    }

    @Test
    public void testDeleteCategory() throws Exception {

        Category category = categoryRepository.save(new Category(categoryDTO));


        // Perform the DELETE request
        mockMvc.perform(delete("/api/v1/categories/{id}", category.getId()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data").value("Category deleted successfully"));  // Check the remaining category
    }

    @Test
    public void testHandleNotFoundException() throws Exception {
        // Simulate a NotFoundException by calling an endpoint that will throw the exception
        mockMvc.perform(get("/api/v1/tips/9999")) // Use an ID that doesn't exist
                .andExpect(status().isNotFound()) // Expect 404 Not Found
                .andExpect(jsonPath("$.message").value("Tip not found with ID: 9999")); // Replace with your specific message
    }

    // @Test
    // public void testHandleBindException() throws Exception {
    //     // Simulate a request that will trigger a BindException
    //     mockMvc.perform(post("/api/v1/tips") // Assuming this endpoint requires a body
    //             .contentType("application/json")
    //             .content("{\"title\": \"\"}")) // Assuming title is required but empty
    //             .andExpect(status().isBadRequest()) // Expect 400 Bad Request
    //             .andExpect(jsonPath("$.title").value("Title is required")); // Replace with your specific validation message
    // }

}

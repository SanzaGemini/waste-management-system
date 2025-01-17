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

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.Category;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.CategoryDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service.CategoryService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class CategoryIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    private CategoryDTO categoryDTO;

    @BeforeEach
    public void setUp() {
        categoryDTO = new CategoryDTO();
        categoryDTO.setTitle("Recycling Tips");
    }

    @Test
    public void testGetCategoryById() throws Exception {
        // Create a Category object
        Category category = new Category(categoryDTO);
        category.setId(1L);

        // Mocking the service to return the category by ID
        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(category));

        // Perform the GET request
        mockMvc.perform(get("/api/v1/categories/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"));  // Check the category title in the data
    }

    @Test
    public void testAddCategory() throws Exception {
        // Create a Category object
        Category savedCategory = new Category(categoryDTO);
        savedCategory.setId(1L);

        // Mocking the save operation
        when(categoryService.saveCategory(any(CategoryDTO.class))).thenReturn(savedCategory);

        // Perform the POST request
        mockMvc.perform(post("/api/v1/categories")
                .contentType("application/json")
                .content("{\"title\":\"Recycling Tips\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data.title").value("Recycling Tips"));  // Check the category title in the data
    }

    @Test
    public void testGetAllCategories() throws Exception {
        // Create two distinct Category objects
        Category category1 = new Category(categoryDTO);
        category1.setId(1L);

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setTitle("Composting Tips");
        Category category2 = new Category(categoryDTO2);
        category2.setId(2L);

        // Mock the service to return a list of categories
        when(categoryService.getCategories()).thenReturn(List.of(category1, category2));

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
        Category category = new Category(categoryDTO);
        category.setId(1L);

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setTitle("Updated Recycling Tips");

        // Mock the update operation
        when(categoryService.updateCategory(1L, categoryDTO2)).thenReturn(category);

        // Perform the PUT request
        mockMvc.perform(put("/api/v1/categories/{id}", 1L)
                .contentType("application/json")
                .content("{\"title\":\"Updated Recycling Tips\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Request was successful"))  // Check the message
                .andExpect(jsonPath("$.data.title").value("Updated Recycling Tips"));  // Check the updated category title in the data
    }

    @Test
    public void testDeleteCategory() throws Exception {
        // Create Category objects
        Category category1 = new Category(categoryDTO);
        category1.setId(1L);

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setTitle("Composting Tips");
        Category category2 = new Category(categoryDTO2);
        category2.setId(2L);

        // Mock the delete operation
        when(categoryService.deleteCategory(2L)).thenReturn(List.of(category1));

        // Perform the DELETE request
        mockMvc.perform(delete("/api/v1/categories/{id}", 2L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))  // Check the status field
                .andExpect(jsonPath("$.message").value("Category deleted successfully"))  // Check the message
                .andExpect(jsonPath("$.data[0].title").value("Recycling Tips"));  // Check the remaining category
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

}

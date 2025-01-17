package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.Unit;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.Guideline;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.models.GuidelineDTO;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.repository.GuidelineRepository;
import com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.service.GuidelineService;

import java.util.Optional;

public class GuidelineUnitTests {

    @Mock
    private GuidelineRepository guidelineRepository;

    @InjectMocks
    private GuidelineService guidelineService;

    private GuidelineDTO guidelineDTO;
    private Guideline guideline;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        guidelineDTO = new GuidelineDTO();
        guidelineDTO.setTitle("Recycling Tips");
        guidelineDTO.setInformation("These are some tips for recycling.");
        guideline = new Guideline(guidelineDTO);
    }

    @Test
    public void testSaveGuideline() {
        // Mock the repository to return the guideline when save is called
        when(guidelineRepository.save(any(Guideline.class))).thenReturn(guideline);

        // Call the service method to save the guideline
        Guideline savedGuideline = guidelineService.saveGuideline(guidelineDTO);

        // Verify that the repository's save method was called
        verify(guidelineRepository, times(1)).save(any(Guideline.class));

        // Assert that the saved guideline matches the expected
        assertEquals("Recycling Tips", savedGuideline.getTitle());
        assertEquals("These are some tips for recycling.", savedGuideline.getInformation());
    }

    @Test
    public void testGetGuidelineById() {
        // Mock the repository to return a guideline by ID
        when(guidelineRepository.findById(1L)).thenReturn(Optional.of(guideline));

        // Call the service method to retrieve the guideline by ID
        Optional<Guideline> retrievedGuideline = guidelineService.getGuidelineById(1L);

        // Assert that the retrieved guideline is present and matches the expected
        assertTrue(retrievedGuideline.isPresent());
        assertEquals("Recycling Tips", retrievedGuideline.get().getTitle());
    }
}

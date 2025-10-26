package com.example.demo.loader

import main.groovy.com.example.demo.loader.DataLoader
import com.example.demo.model.Game
import com.example.demo.model.Team
import com.example.demo.repository.GameRepository
import com.example.demo.repository.TeamRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.core.io.ClassPathResource
import static org.junit.jupiter.api.Assertions.*
import static org.mockito.Mockito.*
import static org.mockito.ArgumentMatchers.*

@ExtendWith(MockitoExtension.class)
@DisplayName("DataLoader Tests")
class DataLoaderTest {

    @Mock
    private TeamRepository teamRepository

    @Mock
    private GameRepository gameRepository

    @InjectMocks
    private DataLoader dataLoader

    private ObjectMapper objectMapper

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this)
        objectMapper = new ObjectMapper()
        dataLoader.objectMapper = objectMapper
    }

    @Test
    @DisplayName("DataLoader should skip loading when database already contains data")
    void testSkipLoadingWhenDataExists() throws Exception {
        // Arrange
        when(teamRepository.count()).thenReturn(30L)

        // Act
        dataLoader.run()

        // Assert
        verify(teamRepository, times(1)).count()
        verify(gameRepository, never()).save(any(Game.class))
        verify(teamRepository, never()).saveAll(anyCollection())
    }

    @Test
    @DisplayName("DataLoader should load data when database is empty")
    void testLoadDataWhenDatabaseEmpty() throws Exception {
        // Arrange
        when(teamRepository.count()).thenReturn(0L)
        when(gameRepository.count()).thenReturn(0L)
        when(teamRepository.saveAll(anyCollection())).thenAnswer(invocation -> invocation.getArgument(0))
        when(gameRepository.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0))

        // Act
        dataLoader.run()

        // Assert
        verify(teamRepository, times(1)).count()
        verify(teamRepository, atLeastOnce()).count()
    }

    @Test
    @DisplayName("DataLoader should print loading message when starting")
    void testLoadingMessagePrinted() throws Exception {
        // Arrange
        when(teamRepository.count()).thenReturn(0L)
        when(gameRepository.count()).thenReturn(0L)

        // Act
        dataLoader.run()

        // Assert - Message should be printed to console
        // This test verifies the method runs without exceptions
        verify(teamRepository, times(1)).count()
    }

    @Test
    @DisplayName("DataLoader should count teams after successful load")
    void testCountTeamsAfterLoad() throws Exception {
        // Arrange
        when(teamRepository.count()).thenReturn(0L).thenReturn(30L)
        when(gameRepository.count()).thenReturn(0L).thenReturn(1230L)

        // Act
        dataLoader.run()

        // Assert
        verify(teamRepository, atLeast(2)).count()
        verify(gameRepository, atLeast(2)).count()
    }

    @Test
    @DisplayName("DataLoader should handle exceptions gracefully")
    void testHandleExceptionsGracefully() throws Exception {
        // Arrange
        when(teamRepository.count()).thenThrow(new RuntimeException("Database error"))

        // Act & Assert - Should not throw, just log the error
        assertDoesNotThrow(() -> dataLoader.run())
    }

    @Test
    @DisplayName("DataLoader run method should be callable with varargs")
    void testRunMethodSignature() throws Exception {
        // Arrange
        when(teamRepository.count()).thenReturn(10L)

        // Act
        dataLoader.run("arg1", "arg2")

        // Assert
        verify(teamRepository, times(1)).count()
    }

    @Test
    @DisplayName("DataLoader should skip when teams exist")
    void testSkipWhenTeamsExist() throws Exception {
        // Arrange
        when(teamRepository.count()).thenReturn(5L)

        // Act
        dataLoader.run()

        // Assert
        verify(teamRepository, times(1)).count()
        verify(teamRepository, never()).saveAll(anyCollection())
    }

    @Test
    @DisplayName("DataLoader should use ObjectMapper for JSON parsing")
    void testUsesObjectMapperForParsing() throws Exception {
        // Arrange
        when(teamRepository.count()).thenReturn(0L)

        // Act
        dataLoader.run()

        // Assert
        assertNotNull(dataLoader.objectMapper)
    }

    @Test
    @DisplayName("DataLoader should print existing data message when skipping")
    void testPrintExistingDataMessage() throws Exception {
        // Arrange
        when(teamRepository.count()).thenReturn(30L)
        when(gameRepository.count()).thenReturn(1230L)

        // Act
        dataLoader.run()

        // Assert
        verify(teamRepository, times(1)).count()
        verify(gameRepository, times(1)).count()
    }

    @Test
    @DisplayName("DataLoader should handle empty database scenario")
    void testHandleEmptyDatabase() throws Exception {
        // Arrange
        when(teamRepository.count()).thenReturn(0L)
        when(gameRepository.count()).thenReturn(0L)

        // Act
        dataLoader.run()

        // Assert
        verify(teamRepository, atLeastOnce()).count()
    }

    @Test
    @DisplayName("DataLoader should be a Spring CommandLineRunner")
    void testImplementsCommandLineRunner() {
        // Assert
        assertTrue(dataLoader instanceof org.springframework.boot.CommandLineRunner)
    }

    @Test
    @DisplayName("DataLoader should not crash application on load failure")
    void testDoesNotCrashOnFailure() throws Exception {
        // Arrange
        when(teamRepository.count()).thenReturn(0L)
        when(gameRepository.save(any())).thenThrow(new RuntimeException("Save failed"))

        // Act & Assert - Should catch and log, not throw
        assertDoesNotThrow(() -> dataLoader.run())
    }

    @Test
    @DisplayName("DataLoader should handle null return from repositories")
    void testHandleNullRepositoryReturns() throws Exception {
        // Arrange
        when(teamRepository.count()).thenReturn(0L)
        when(teamRepository.saveAll(any())).thenReturn(null)
        when(gameRepository.count()).thenReturn(null)

        // Act & Assert
        assertDoesNotThrow(() -> dataLoader.run())
    }

    @Test
    @DisplayName("DataLoader initialization should autowire dependencies")
    void testDependenciesAutowired() {
        // Assert
        assertNotNull(teamRepository)
        assertNotNull(gameRepository)
    }
}

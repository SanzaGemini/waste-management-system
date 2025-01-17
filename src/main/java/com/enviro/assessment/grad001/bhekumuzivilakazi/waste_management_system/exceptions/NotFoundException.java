package com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system.exceptions;

public class NotFoundException extends RuntimeException {

    /**
     * Constructor to create an exception with a specific message when an entity is not found by ID.
     *
     * @param type The type of entity (e.g., Category, Tip) that was not found.
     * @param id The ID of the entity that could not be found.
     */
    public NotFoundException(String type, Long id) {
        super(String.format("%s with Id %d not found.", type, id));
    }

    /**
     * Constructor to create a generic exception with a custom message.
     *
     * @param message The custom message describing the exception.
     */
    public NotFoundException(String message) {
        super(message);
    }

    // Optionally, you could add a constructor to handle scenarios where ID might be null.
    public NotFoundException(String type, String message) {
        super(String.format("%s not found: %s", type, message));
    }
}

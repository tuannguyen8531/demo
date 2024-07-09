package com.dev.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.server.dto.APIResponse;
import com.dev.server.dto.TutorialDTO.*;
import com.dev.server.service.TutorialService;
import com.dev.server.util.MessageConstant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Tutorial", description = "Tutorial management APIs")
@RestController
@RequestMapping("/api/v1")
public class TutorialController {
    private final TutorialService tutorialService;

    @Autowired
    public TutorialController(TutorialService tutorialService) {
        this.tutorialService = tutorialService;
    }
    
    @Operation(
        summary = "Get all tutorials",
        description = "Endpoint for getting all tutorials or filtering tutorials by title. \n\n"
                + "Parameters:\n"
                + "- title (string): Filter tutorials by title\n\n"
                + "Request body: None\n"
                + "Response body:\n"
                + "- success (boolean): Response status\n"
                + "- message (string): Response message\n"
                + "- data (array): List of tutorials\n"
                + "  - id (number): Tutorial ID\n"
                + "  - title (string): Tutorial title\n"
                + "  - description (string): Tutorial description\n"
                + "  - published (boolean): Tutorial published status"
    )
    @GetMapping(path = "/tutorial")
    public ResponseEntity<APIResponse<List<TutorialResponse>>> getAllTutorials(@RequestParam(required = false) String title) {
        List<TutorialResponse> tutorials = new ArrayList<>();
        if (title == null) {
            this.tutorialService.getAllTutorials().forEach(tutorials::add);;
        } else {
            this.tutorialService.getTutorialsByTitle(title).forEach(tutorials::add);
        }
        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(new APIResponse<>(true, MessageConstant.RESOURCE_EMPTY, tutorials), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new APIResponse<>(true, MessageConstant.RESOURCE_FOUND, tutorials), HttpStatus.OK);
    }

    @Operation(
        summary = "Get tutorial by ID",
        description = "Endpoint for getting tutorial by ID. \n\n"
                + "Parameters:\n"
                + "- id (number): Tutorial ID\n\n"
                + "Request body: None\n"
                + "Response body:\n"
                + "- success (boolean): Response status\n"
                + "- message (string): Response message\n"
                + "- data (object): Tutorial object\n"
                + "  - id (number): Tutorial ID\n"
                + "  - title (string): Tutorial title\n"
                + "  - description (string): Tutorial description\n"
                + "  - published (boolean): Tutorial published status"
    )
    @GetMapping(path = "/tutorial/{id}")
    public ResponseEntity<APIResponse<TutorialResponse>> getTutorialById(@PathVariable Long id) {
        TutorialResponse tutorial = this.tutorialService.getTutorialById(id);
        return new ResponseEntity<>(new APIResponse<>(true, MessageConstant.RESOURCE_FOUND, tutorial), HttpStatus.OK);
    }

    @Operation(
        summary = "Create new tutorial",
        description = "Endpoint for creating new tutorial. \n\n"
                + "Parameters: None\n\n"
                + "Request body:\n"
                + "- title (string): Tutorial title\n"
                + "- description (string): Tutorial description\n"
                + "- published (boolean): Tutorial published status\n\n"
                + "Response body:\n"
                + "- success (boolean): Response status\n"
                + "- message (string): Response message\n"
                + "- data (object): New tutorial object\n"
                + "  - id (number): Tutorial ID\n"
                + "  - title (string): Tutorial title\n"
                + "  - description (string): Tutorial description\n"
                + "  - published (boolean): Tutorial published status"
    )
    @PostMapping(path = "/tutorial")
    public ResponseEntity<APIResponse<TutorialResponse>> addNewTutorial(@Valid @RequestBody TutorialCreateRequest tutorial) {
        TutorialResponse newTutorial = this.tutorialService.createTutorial(tutorial);
        return new ResponseEntity<>(new APIResponse<>(true, MessageConstant.TUTORIAL_CREATED, newTutorial), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Update tutorial",
        description = "Endpoint for updating an existing tutorial. \n\n"
                + "Parameters:\n"
                + "- id (number): Tutorial ID\n\n"
                + "Request body:\n"
                + "- title (string): Tutorial title\n"
                + "- description (string): Tutorial description\n"
                + "- published (boolean): Tutorial published status\n\n"
                + "Response body:\n"
                + "- success (boolean): Response status\n"
                + "- message (string): Response message\n"
                + "- data (object): Updated tutorial object\n"
                + "  - id (number): Tutorial ID\n"
                + "  - title (string): Tutorial title\n"
                + "  - description (string): Tutorial description\n"
                + "  - published (boolean): Tutorial published status"
    )
    @PutMapping(path = "/tutorial/{id}")
    public ResponseEntity<APIResponse<TutorialResponse>> updateTutorial(@PathVariable Long id, @Valid @RequestBody TutorialUpdateRequest tutorial) {
        TutorialResponse updatedTutorial = this.tutorialService.updateTutorial(id, tutorial);
        return new ResponseEntity<>(new APIResponse<>(true, MessageConstant.TUTORIAL_UPDATED, updatedTutorial), HttpStatus.OK);
    }

    @Operation(
        summary = "Delete tutorial",
        description = "Endpoint for deleting an existing tutorial. \n\n"
                + "Parameters:\n"
                + "- id (number): Tutorial ID\n\n"
                + "Request body: None\n"
                + "Response body:\n"
                + "- success (boolean): Response status\n"
                + "- message (string): Response message\n"
                + "- data: null"
    )
    @DeleteMapping(path = "/tutorial/{id}/delete")
    public ResponseEntity<APIResponse<?>> deleteTutorial(@PathVariable Long id) {
        this.tutorialService.deleteTutorial(id);
        return new ResponseEntity<>(new APIResponse<>(true, MessageConstant.TUTORIAL_DELETED, null), HttpStatus.OK);
    }
}

package com.dev.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.server.dto.TutorialDTO;
import com.dev.server.exception.AppException;
import com.dev.server.exception.GlobalExceptionHandler.ErrorResponse;
import com.dev.server.service.TutorialService;

@RestController
@RequestMapping("/api/v1")
public class TutorialController {
    private final TutorialService tutorialService;

    @Autowired
    public TutorialController(TutorialService tutorialService) {
        this.tutorialService = tutorialService;
    }
    
    // Get all tutorials
    @GetMapping(path = "/tutorial")
    public ResponseEntity<?> getAllTutorials(@RequestParam(required = false) String title) {
        try {
            List<TutorialDTO> tutorials = new ArrayList<TutorialDTO>();
            if (title == null) {
                this.tutorialService.getAllTutorials().forEach(tutorials::add);;
            } else {
                this.tutorialService.getTutorialsByTitle(title).forEach(tutorials::add);
            }
            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (AppException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getStatusCode(), e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
        }
    }

    @GetMapping(path = "/tutorial/{id}")
    public ResponseEntity<?> getTutorialById(@PathVariable Long id) {
        try {
            TutorialDTO tutorial = this.tutorialService.getTutorialById(id);
            if (tutorial == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(tutorial, HttpStatus.OK);
        } catch (AppException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getStatusCode(), e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
        }
    }

    @PostMapping(path = "/tutorial")
    public ResponseEntity<?> addNewTutorial(@RequestBody TutorialDTO tutorial) {
        try {
            TutorialDTO newTutorial = this.tutorialService.createTutorial(tutorial);
            return new ResponseEntity<>(newTutorial, HttpStatus.CREATED);
        } catch (AppException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getStatusCode(), e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
        }
    }

    @PatchMapping(path = "/tutorial/{id}")
    public ResponseEntity<?> updateTutorial(@PathVariable Long id, @RequestBody TutorialDTO tutorial) {
        try {
            TutorialDTO updatedTutorial = this.tutorialService.updateTutorial(id, tutorial);
            return new ResponseEntity<>(updatedTutorial, HttpStatus.OK);
        } catch (AppException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getStatusCode(), e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
        }
    }

    @PatchMapping(path = "/tutorial/{id}/delete")
    public ResponseEntity<?> deleteTutorial(@PathVariable Long id) {
        try {
            this.tutorialService.deleteTutorial(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (AppException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getStatusCode(), e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
        }
    }
}

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.server.dto.APIResponse;
import com.dev.server.dto.TutorialDTO.*;
import com.dev.server.service.TutorialService;
import com.dev.server.util.MessageConstant;

import jakarta.validation.Valid;

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

    @GetMapping(path = "/tutorial/{id}")
    public ResponseEntity<?> getTutorialById(@PathVariable Long id) {
        TutorialResponse tutorial = this.tutorialService.getTutorialById(id);
        return new ResponseEntity<>(new APIResponse<>(true, MessageConstant.RESOURCE_FOUND, tutorial), HttpStatus.OK);
    }

    @PostMapping(path = "/tutorial")
    public ResponseEntity<?> addNewTutorial(@Valid @RequestBody TutorialCreateRequest tutorial) {
        TutorialResponse newTutorial = this.tutorialService.createTutorial(tutorial);
        return new ResponseEntity<>(new APIResponse<>(true, MessageConstant.TUTORIAL_CREATED, newTutorial), HttpStatus.CREATED);
    }

    @PutMapping(path = "/tutorial/{id}")
    public ResponseEntity<?> updateTutorial(@PathVariable Long id, @Valid @RequestBody TutorialUpdateRequest tutorial) {
        TutorialResponse updatedTutorial = this.tutorialService.updateTutorial(id, tutorial);
        return new ResponseEntity<>(new APIResponse<>(true, MessageConstant.TUTORIAL_UPDATED, updatedTutorial), HttpStatus.OK);
    }

    @PatchMapping(path = "/tutorial/{id}/delete")
    public ResponseEntity<?> deleteTutorial(@PathVariable Long id) {
        this.tutorialService.deleteTutorial(id);
        return new ResponseEntity<>(new APIResponse<>(true, MessageConstant.TUTORIAL_DELETED, null), HttpStatus.OK);
    }
}

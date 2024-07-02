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

import com.dev.server.dto.APIRespone;
import com.dev.server.dto.TutorialDTO;
import com.dev.server.service.TutorialService;

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
        List<TutorialDTO> tutorials = new ArrayList<TutorialDTO>();
        if (title == null) {
            this.tutorialService.getAllTutorials().forEach(tutorials::add);;
        } else {
            this.tutorialService.getTutorialsByTitle(title).forEach(tutorials::add);
        }
        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(new APIRespone<>(true, "Data is empty", tutorials), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new APIRespone<>(true, "Data found", tutorials), HttpStatus.OK);
    }

    @GetMapping(path = "/tutorial/{id}")
    public ResponseEntity<?> getTutorialById(@PathVariable Long id) {
        TutorialDTO tutorial = this.tutorialService.getTutorialById(id);
        return new ResponseEntity<>(new APIRespone<>(true, "Data found", tutorial), HttpStatus.OK);
    }

    @PostMapping(path = "/tutorial")
    public ResponseEntity<?> addNewTutorial(@Valid @RequestBody TutorialDTO tutorial) {
        TutorialDTO newTutorial = this.tutorialService.createTutorial(tutorial);
        return new ResponseEntity<>(new APIRespone<>(true, "Tutorial has been created", newTutorial), HttpStatus.CREATED);
    }

    @PutMapping(path = "/tutorial/{id}")
    public ResponseEntity<?> updateTutorial(@PathVariable Long id, @RequestBody TutorialDTO tutorial) {
        TutorialDTO updatedTutorial = this.tutorialService.updateTutorial(id, tutorial);
        return new ResponseEntity<>(new APIRespone<>(true, "Tutorial with id " + id + " has been updated", updatedTutorial), HttpStatus.OK);
    }

    @PatchMapping(path = "/tutorial/{id}/delete")
    public ResponseEntity<?> deleteTutorial(@PathVariable Long id) {
        this.tutorialService.deleteTutorial(id);
        return new ResponseEntity<>(new APIRespone<>(true, "Tutorial with id " + id + " has been deleted", null), HttpStatus.OK);
    }
}

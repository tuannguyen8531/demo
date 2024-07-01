package com.dev.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.server.model.Tutorial;
import com.dev.server.repository.TutorialRepository;

@Service
public class TutorialService {
    private final TutorialRepository tutorialRepository;

    @Autowired
    public TutorialService(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    public List<Tutorial> getAllTutorials() {
        return tutorialRepository.findAll();
    }

    public List<Tutorial> getPublishedTutorials() {
        return tutorialRepository.findByPublished(true);
    }

    public List<Tutorial> getTutorialsByTitle(String title) {
        return tutorialRepository.findByTitleContaining(title);
    }

    public Tutorial getTutorialById(Long id) {
        return tutorialRepository.findById(id).orElse(null);
    }

    public Tutorial createTutorial(Tutorial tutorial) {
        Optional<Tutorial> tutorialOptional = tutorialRepository.findByTitle(tutorial.getTitle());
        if (tutorialOptional.isPresent()) {
            throw new IllegalStateException("Title already taken");
        }
        return tutorialRepository.save(tutorial);
    }

    public void deleteTutorial(Long id) {
        boolean exists = tutorialRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Tutorial with id " + id + " does not exist");
        }
        tutorialRepository.deleteById(id);
    }
}

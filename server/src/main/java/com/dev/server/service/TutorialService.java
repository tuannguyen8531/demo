package com.dev.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.server.model.Tutorial;
import com.dev.server.dto.TutorialDTO;
import com.dev.server.exception.AppException;
import com.dev.server.repository.TutorialRepository;

@Service
public class TutorialService {
    private final TutorialRepository tutorialRepository;

    @Autowired
    public TutorialService(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    public List<TutorialDTO> getAllTutorials() {
        return tutorialRepository.findAllTutorials()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<TutorialDTO> getPublishedTutorials() {
        return tutorialRepository.findTutorialByPublished(true)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<TutorialDTO> getTutorialsByTitle(String title) {
        return tutorialRepository.findTutorialByTitleContaining(title)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public TutorialDTO getTutorialById(Long id) {
        Optional<Tutorial> tutorialOptional = tutorialRepository.findTutorialById(id);
        if (tutorialOptional.isEmpty()) {
            throw new AppException("Tutorial with id " + id + " does not exist", 404);
        }
        return convertToDTO(tutorialOptional.get());
    }

    public TutorialDTO createTutorial(TutorialDTO tutorial) {
        Optional<Tutorial> tutorialOptional = tutorialRepository.findTutorialByTitle(tutorial.getTitle());
        if (tutorialOptional.isPresent()) {
            throw new AppException("Tutorial with title " + tutorial.getTitle() + " already exists", 409);
        }
        Tutorial newTutorial = new Tutorial(tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished());
        return convertToDTO(tutorialRepository.save(newTutorial));
    }

    public TutorialDTO updateTutorial(Long id, TutorialDTO tutorial) {
        Tutorial existingTutorial = tutorialRepository.findTutorialById(id)
                .orElseThrow(() -> new AppException("Tutorial with id " + id + " does not exist", 404));
        if (!existingTutorial.getTitle().equals(tutorial.getTitle())) {
            Optional<Tutorial> tutorialOptional = tutorialRepository.findTutorialByTitle(tutorial.getTitle());
            if (tutorialOptional.isPresent()) {
                throw new AppException("Tutorial with title " + tutorial.getTitle() + " already exists", 409);
            }
        } else if (tutorial.getTitle() == null || tutorial.getTitle().isEmpty()) {
            throw new AppException("Title is required", 400);
        } else if (tutorial.getDescription() == null || tutorial.getDescription().isEmpty()) {
            throw new AppException("Description is required", 400);
        }
        existingTutorial.setTitle(tutorial.getTitle());
        existingTutorial.setDescription(tutorial.getDescription());
        existingTutorial.setPublished(tutorial.isPublished());
        return convertToDTO(tutorialRepository.save(existingTutorial));
    }

    public void deleteTutorial(Long id) {
        Tutorial existingTutorial = tutorialRepository.findTutorialById(id)
                .orElseThrow(() -> new AppException("Tutorial with id " + id + " does not exist", 404));
        existingTutorial.setDeletedFlg(true);
        tutorialRepository.save(existingTutorial);
    }

    private TutorialDTO convertToDTO(Tutorial tutorial) {
        TutorialDTO tutorialDTO = new TutorialDTO(tutorial.getId(), tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished());
        return tutorialDTO;
    }
}

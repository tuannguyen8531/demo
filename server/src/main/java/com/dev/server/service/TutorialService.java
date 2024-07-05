package com.dev.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.server.model.Tutorial;
import com.dev.server.dto.TutorialDTO.*;
import com.dev.server.exception.AppException;
import com.dev.server.exception.ErrorCode;
import com.dev.server.repository.TutorialRepository;

@Service
public class TutorialService {
    private final TutorialRepository tutorialRepository;

    @Autowired
    public TutorialService(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    public List<TutorialResponse> getAllTutorials() {
        return tutorialRepository.findAllTutorials()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<TutorialResponse> getPublishedTutorials() {
        return tutorialRepository.findTutorialByPublished(true)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<TutorialResponse> getTutorialsByTitle(String title) {
        return tutorialRepository.findTutorialByTitleContaining(title)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public TutorialResponse getTutorialById(Long id) {
        Optional<Tutorial> tutorialOptional = tutorialRepository.findTutorialById(id);
        if (tutorialOptional.isEmpty()) {
            throw new AppException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return convertToDTO(tutorialOptional.get());
    }

    public TutorialResponse createTutorial(TutorialCreateRequest tutorial) {
        Optional<Tutorial> tutorialOptional = tutorialRepository.findTutorialByTitle(tutorial.getTitle());
        if (tutorialOptional.isPresent()) {
            throw new AppException(ErrorCode.RESOURCE_ALREADY_EXISTS);
        }
        Tutorial newTutorial = new Tutorial(tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished());
        return convertToDTO(tutorialRepository.save(newTutorial));
    }

    public TutorialResponse updateTutorial(Long id, TutorialUpdateRequest tutorial) {
        Tutorial existingTutorial = tutorialRepository.findTutorialById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        if (!existingTutorial.getTitle().equals(tutorial.getTitle())) {
            Optional<Tutorial> tutorialOptional = tutorialRepository.findTutorialByTitle(tutorial.getTitle());
            if (tutorialOptional.isPresent()) {
                throw new AppException(ErrorCode.RESOURCE_ALREADY_EXISTS);
            }
        }
        existingTutorial.setTitle(tutorial.getTitle());
        existingTutorial.setDescription(tutorial.getDescription());
        existingTutorial.setPublished(tutorial.isPublished());
        return convertToDTO(tutorialRepository.save(existingTutorial));
    }

    public void deleteTutorial(Long id) {
        Tutorial existingTutorial = tutorialRepository.findTutorialById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        existingTutorial.setDeletedFlg(true);
        tutorialRepository.save(existingTutorial);
    }

    private TutorialResponse convertToDTO(Tutorial tutorial) {
        TutorialResponse tutorialResponse = new TutorialResponse(tutorial.getId(), tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished());
        return tutorialResponse;
    }
}

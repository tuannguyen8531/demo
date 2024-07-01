package com.dev.server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.server.model.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long>{
    @Query("SELECT t FROM Tutorial t WHERE t.deletedFlg = false")
    List<Tutorial> findAllTutorials();

    @Query("SELECT t FROM Tutorial t WHERE t.published = true AND t.deletedFlg = false")
    List<Tutorial> findTutorialByPublished(boolean published);

    @Query("SELECT t FROM Tutorial t WHERE t.title LIKE %?1% AND t.deletedFlg = false")
    List<Tutorial> findTutorialByTitleContaining(String title);

    @Query("SELECT t FROM Tutorial t WHERE t.title = ?1 AND t.deletedFlg = false")
    Optional<Tutorial> findTutorialByTitle(String title);

    @Query("SELECT t FROM Tutorial t WHERE t.id = ?1 AND t.deletedFlg = false")
    Optional<Tutorial> findTutorialById(Long id);
}

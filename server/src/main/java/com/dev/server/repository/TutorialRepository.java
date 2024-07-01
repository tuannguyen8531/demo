package com.dev.server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.server.model.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long>{
    @Query("SELECT t FROM Tutorial t WHERE t.deleted_flg = false")
    List<Tutorial> findAllTutorials();

    @Query("SELECT t FROM Tutorial t WHERE t.published = true AND t.deleted_flg = false")
    List<Tutorial> findTutorialByPublished(boolean published);

    @Query("SELECT t FROM Tutorial t WHERE t.title LIKE %?1% AND t.deleted_flg = false")
    List<Tutorial> findTutorialByTitleContaining(String title);

    @Query("SELECT t FROM Tutorial t WHERE t.title = ?1 AND t.deleted_flg = false")
    Optional<Tutorial> findTutorialByTitle(String title);

    @Query("SELECT t FROM Tutorial t WHERE t.id = ?1 AND t.deleted_flg = false")
    Optional<Tutorial> findTutorialById(Long id);
}

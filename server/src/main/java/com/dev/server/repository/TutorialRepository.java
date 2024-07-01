package com.dev.server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.server.model.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long>{
    List<Tutorial> findByPublished(boolean published);
    List<Tutorial> findByTitleContaining(String title);

    @Query("SELECT t FROM Tutorial t WHERE t.title = ?1")
    Optional<Tutorial> findByTitle(String title);
}

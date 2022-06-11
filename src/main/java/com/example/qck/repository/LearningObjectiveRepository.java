package com.example.qck.repository;

import com.example.qck.model.LearningObjective;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearningObjectiveRepository extends JpaRepository<LearningObjective, Long> {
    List<LearningObjective> findAllBySubjectIdAndEnabled(Long subjectId, Boolean enabled);

    List<LearningObjective> findLearningObjectiveByEnabled(Boolean enabled);
}

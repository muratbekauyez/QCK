package com.example.qck.repository;

import com.example.qck.model.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findTestsByEnabled(Boolean enabled);

    @Query( "SELECT t FROM Test t WHERE t.enabled=:enabled AND t.studyYear in :studyYears" )
    List<Test> findTestsByEnabledAndStudyYear(@Param("enabled") Boolean enabled, @Param("studyYears") List<Integer> StudyYears);
}

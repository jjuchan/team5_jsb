package com.team5_jsb.domain.question.question.repository;

import com.team5_jsb.domain.question.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}

package com.team5_jsb.domain.answer.answer.repository;

import com.team5_jsb.domain.answer.answer.entity.Answer;
import com.team5_jsb.domain.question.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestion(Question question);
}

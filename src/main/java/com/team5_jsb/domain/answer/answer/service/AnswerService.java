package com.team5_jsb.domain.answer.answer.service;

import com.team5_jsb.domain.answer.answer.entity.Answer;
import com.team5_jsb.domain.answer.answer.repository.AnswerRepository;
import com.team5_jsb.domain.question.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Transactional(readOnly = true)
    public List<Answer> getAnswers(Question question) {
        return answerRepository.findByQuestion(question);
    }
}

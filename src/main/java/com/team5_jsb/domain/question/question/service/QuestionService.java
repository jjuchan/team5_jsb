package com.team5_jsb.domain.question.question.service;

import com.team5_jsb.domain.question.question.entity.Question;
import com.team5_jsb.domain.question.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public List<Question> getList(String kw) {
        if (kw != null && !kw.isBlank()) {
            return questionRepository.findAllByKeyword(kw);
        }
        return questionRepository.findAll();
    }
}

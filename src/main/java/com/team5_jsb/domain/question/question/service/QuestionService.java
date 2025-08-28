package com.team5_jsb.domain.question.question.service;

import com.team5_jsb.domain.question.question.entity.Question;
import com.team5_jsb.domain.question.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final int PAGE_SIZE = 10;

    @Transactional(readOnly = true)
    public Page<Question> getList(int page, String kw) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createDate"));

        if (kw != null && !kw.isBlank()) {
            return questionRepository.findAllByKeywordOrderByCreateDateDesc(kw, pageable);
        }
        return questionRepository.findAll(pageable);
    }

    @Transactional
    public Question getQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        question.increaseViewCount();
        return question;
    }
}

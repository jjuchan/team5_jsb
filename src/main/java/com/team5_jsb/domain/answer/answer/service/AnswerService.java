package com.team5_jsb.domain.answer.answer.service;

import com.team5_jsb.domain.answer.answer.entity.Answer;
import com.team5_jsb.domain.answer.answer.repository.AnswerRepository;
import com.team5_jsb.domain.question.question.entity.Question;
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
public class AnswerService {
    private final AnswerRepository answerRepository;
    private static final int PAGE_SIZE = 5;

    @Transactional(readOnly = true)
    public Page<Answer> getAnswers(Question question, int page) {
        // 답변은 항상 최신순으로 정렬 (createDate 내림차순)
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createDate"));
        return answerRepository.findByQuestionOrderByCreateDateDesc(question, pageable);
    }
}

package com.team5_jsb.domain.answer.answer.service;

import com.team5_jsb.domain.answer.answer.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
}

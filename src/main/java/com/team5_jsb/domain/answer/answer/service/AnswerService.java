package com.team5_jsb.domain.answer.answer.service;

import com.team5_jsb.domain.answer.answer.entity.Answer;
import com.team5_jsb.domain.answer.answer.repository.AnswerRepository;
import com.team5_jsb.domain.question.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setCreateDate(LocalDateTime.now());
        answer.setContent(content);
        answer.setQuestion(question);
        answerRepository.save(answer);
    }

    public void deleteById(long id) {
        answerRepository.deleteById(id);
    }

    public void update(Answer answer, String content) {
        answerRepository.findById(answer.getId()).orElseThrow(() -> new RuntimeException("답변이 존재하지 않습니다"));

        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        answerRepository.save(answer);
    }

    public Answer getAnswer(long id) {
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new RuntimeException("answer not found");
        }
    }

    public List<Answer> findAll() {
        return answerRepository.findAll();
    }
}
package com.team5_jsb.domain.answer.answer.service;

import com.team5_jsb.domain.answer.answer.entity.Answer;
import com.team5_jsb.domain.answer.answer.repository.AnswerRepository;
import com.team5_jsb.domain.question.question.entity.Question;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.Optional;
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

    public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setCreatedDate(LocalDateTime.now());
        answer.setContent(content);
        answer.setQuestion(question);
        answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    public void update(Answer answer, String content) {
        answerRepository.findById(answer.getId()).orElseThrow(() -> new RuntimeException("답변이 존재하지 않습니다"));

        answer.setContent(content);
        answer.setModifiedDate(LocalDateTime.now());
        answerRepository.save(answer);
    }
    
    @Transactional(readOnly = true)
    public Page<Answer> getAnswers(Question question, int page) {
        // 답변은 항상 최신순으로 정렬 (createdDate 내림차순)
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createdDate"));
        return answerRepository.findByQuestionOrderByCreatedDateDesc(question, pageable);
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

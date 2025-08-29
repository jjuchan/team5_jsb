package com.team5_jsb.domain.question.question.service;

import com.team5_jsb.domain.question.question.dto.QuestionCreateDTO;
import com.team5_jsb.domain.question.question.entity.Question;
import com.team5_jsb.domain.question.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getList() {

        return questionRepository.findAll();
    }

    public void create(QuestionCreateDTO questionCreateDTO) {
        // DTO의 데이터를 엔티티로 변환
        Question question = new Question();
        question.setSubject(questionCreateDTO.getSubject());
        question.setContent(questionCreateDTO.getContent());
        questionRepository.save(question);
        System.out.println("question = " + questionCreateDTO);
    }

    public Question getQuestion(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new RuntimeException("question not found");
        }
    }
}

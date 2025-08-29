package com.team5_jsb.domain.question.question.service;

import com.team5_jsb.domain.question.question.dto.QuestionCreateDTO;
import com.team5_jsb.domain.question.question.dto.QuestionUpdateDto;
import com.team5_jsb.domain.question.question.entity.Question;
import com.team5_jsb.domain.question.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void modify(QuestionUpdateDto dto, Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("질문이 존재하지 않습니다."));

        // DTO에서 값 가져와 기존 엔티티 수정
        if (dto.getSubject() != null) {
            question.setSubject(dto.getSubject());
        }
        if (dto.getContent() != null) {
            question.setContent(dto.getContent());
        }

        questionRepository.save(question);
    }

    @Transactional
    public void deleteQuestion(Long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            questionRepository.deleteById(id);
        } else {
            // 해당 ID의 질문이 없을 경우 처리
            throw new IllegalArgumentException("해당 ID의 질문을 찾을 수 없습니다.");
        }
    }
}

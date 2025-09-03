package com.team5_jsb.domain.question.question.service;

import com.team5_jsb.domain.question.question.dto.QuestionCreateDTO;
import com.team5_jsb.domain.question.question.dto.QuestionResponseDTO;
import com.team5_jsb.domain.question.question.dto.QuestionUpdateDto;
import com.team5_jsb.domain.question.question.entity.Question;
import com.team5_jsb.domain.question.question.repository.QuestionRepository;
import com.team5_jsb.domain.user.user.entity.User;
import com.team5_jsb.domain.user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final int PAGE_SIZE = 10;

    @Transactional(readOnly = true)
    public Page<QuestionResponseDTO> getMyQuestions(Long userId, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return questionRepository
                .findByAuthor_IdOrderByCreatedDateDesc(userId, pageable)
                .map(QuestionResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<QuestionResponseDTO> getList(int page, String kw) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<Question> pageResult;
        if (kw != null && !kw.isBlank()) {
            pageResult = questionRepository.findAllByKeywordOrderByCreatedDateDesc(kw, pageable);
        } else {
            pageResult = questionRepository.findAll(pageable);
        }

        return pageResult.map(QuestionResponseDTO::fromEntity);
    }

    public void create(QuestionCreateDTO questionCreateDTO, User author) {
        // DTO의 데이터를 엔티티로 변환
        Question question = new Question();
        question.setSubject(questionCreateDTO.getSubject());
        question.setContent(questionCreateDTO.getContent());
        question.setAuthor(author);
        questionRepository.save(question);
        System.out.println("question = " + questionCreateDTO);
    }

    @Transactional
    public QuestionResponseDTO getQuestion(Long id) {
        // 기본적으로 조회수를 증가시키는 버전
        return getQuestion(id, true);
    }

    @Transactional
    public QuestionResponseDTO getQuestion(Long id, boolean increaseView) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("질문이 존재하지 않습니다."));

        if (increaseView) {
            question.increaseViewCount(); // 조회수 증가
        }

        return QuestionResponseDTO.fromEntity(question);
    }

    @Transactional(readOnly = true)
    public Question getQuestionEntity(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("질문이 존재하지 않습니다."));
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

    @Transactional
    public void increaseViewCount(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("질문이 존재하지 않습니다."));
        question.increaseViewCount();
        questionRepository.save(question);
    }
}
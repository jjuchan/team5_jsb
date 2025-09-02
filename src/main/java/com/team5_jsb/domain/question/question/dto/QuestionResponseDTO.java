package com.team5_jsb.domain.question.question.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import com.team5_jsb.domain.question.question.entity.Question;

@Data
@AllArgsConstructor
public class QuestionResponseDTO {
    private Long id;
    private String subject;
    private String content;
    private LocalDateTime createDate;
    private String authorUsername;
    private Integer viewCount;

    public static QuestionResponseDTO fromEntity(Question question) {
        return new QuestionResponseDTO(
                question.getId(),
                question.getSubject(),
                question.getContent(),
                question.getCreateDate(),
                (question.getAuthor() != null ? question.getAuthor().getUsername() : null),
                question.getViewCount()
        );
    }
}
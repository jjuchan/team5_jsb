package com.team5_jsb.domain.question.question.dto;

import com.team5_jsb.domain.question.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class QuestionResponseDTO {
    private Long id;
    private String subject;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private String authorUsername;
    private Integer viewCount;
    private Integer answerCount;

    public static QuestionResponseDTO fromEntity(Question question) {

        return new QuestionResponseDTO(
                question.getId(),
                question.getSubject(),
                question.getContent(),
                question.getCreatedDate(),
                question.getModifiedDate(),
                (question.getAuthor() != null ? question.getAuthor().getUsername() : null),
                question.getViewCount(),
                question.getAnswerList().size()
        );
    }
}
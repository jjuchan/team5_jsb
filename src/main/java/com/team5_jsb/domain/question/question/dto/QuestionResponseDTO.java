package com.team5_jsb.domain.question.question.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class QuestionResponseDTO {
    private Long id;
    private String subject;
    private String content;
    private LocalDateTime createDateTime;
}

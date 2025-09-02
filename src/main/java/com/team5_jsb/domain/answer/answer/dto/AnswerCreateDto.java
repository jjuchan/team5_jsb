package com.team5_jsb.domain.answer.answer.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AnswerCreateDto {
    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
}

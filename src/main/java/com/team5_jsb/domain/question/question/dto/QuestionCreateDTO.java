package com.team5_jsb.domain.question.question.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuestionCreateDTO {

    @NotEmpty(message = "제목은 필수입니다.")
    @Size(max = 200)
    private String subject;

    @NotEmpty(message = "내용은 필수입니다.")
    private String content;
}

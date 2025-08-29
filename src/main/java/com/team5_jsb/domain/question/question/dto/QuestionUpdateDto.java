package com.team5_jsb.domain.question.question.dto;

import lombok.Data;

@Data
public class QuestionUpdateDto { // 생성과 다르게 null 값 가능
    private String subject;
    private String content;

}

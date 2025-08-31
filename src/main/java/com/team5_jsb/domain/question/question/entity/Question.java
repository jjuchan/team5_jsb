package com.team5_jsb.domain.question.question.entity;

import com.team5_jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Question extends BaseEntity {

    private String subject;

    private String content;

//    private int viewCount;
}
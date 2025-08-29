package com.team5_jsb.domain.answer.answer.entity;

import com.team5_jsb.domain.question.question.entity.Question;
import com.team5_jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Answer extends BaseEntity {

    @ManyToOne
    private Question question;
}

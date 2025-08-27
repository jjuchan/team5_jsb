package com.team5_jsb.domain.answer.answer.entity;

import com.team5_jsb.domain.question.question.entity.Question;
import com.team5_jsb.domain.user.user.entity.User;
import com.team5_jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@NoArgsConstructor
public class Answer extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private Question question;

    @ManyToOne
    private User author;

    public Answer(String content, Question question, User author) {
        this.content = content;
        this.question = question;
        this.author = author;
    }
}
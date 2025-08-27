package com.team5_jsb.domain.question.question.entity;

import com.team5_jsb.domain.answer.answer.entity.Answer;
import com.team5_jsb.domain.user.user.entity.User;
import com.team5_jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Question extends BaseEntity {

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "integer default 0")
    private Integer viewCount = 0;

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList = new ArrayList<>();

    public Question(String subject, String content, User author) {
        this.subject = subject;
        this.content = content;
        this.author = author;
    }

    public void increaseViewCount() {
        this.viewCount = (this.viewCount == null) ? 1 : this.viewCount + 1;
    }
}
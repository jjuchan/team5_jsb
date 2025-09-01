package com.team5_jsb.domain.question.question.entity;

import com.team5_jsb.domain.answer.answer.entity.Answer;
import com.team5_jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Question extends BaseEntity {

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int viewCount;

//    @ManyToOne
//    private User author;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Answer> answerList = new ArrayList<>();
}

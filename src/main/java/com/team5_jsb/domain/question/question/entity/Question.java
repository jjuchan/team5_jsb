package com.team5_jsb.domain.question.question.entity;

import com.team5_jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Question extends BaseEntity {

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int viewCount;

    /** 추후 통합 때 추가 예정
    @ManyToOne
    private User author;

    @OneToMany
    private List<Answer> answers;
    **/
}

package com.team5_jsb.domain.user.user.entity;

import com.team5_jsb.domain.answer.answer.entity.Answer;
import com.team5_jsb.domain.question.question.entity.Question;
import com.team5_jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(unique = true, nullable = false, length = 50, updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @OneToMany(mappedBy = "author", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Answer> answers = new ArrayList<>();
}
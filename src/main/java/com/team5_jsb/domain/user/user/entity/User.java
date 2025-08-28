package com.team5_jsb.domain.user.user.entity;

import com.team5_jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    // Question, Answer 미구현으로 아래 주석처리. 구현 후 사용
/*
    @OneToMany(mappedBy = "author")
    private List<Question> questions;

    @OneToMany(mappedBy = "author")
    private List<Answer> answers;
*/
}

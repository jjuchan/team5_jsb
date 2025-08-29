package com.team5_jsb.domain.user.user.entity;

import com.team5_jsb.domain.question.question.entity.Question;
import com.team5_jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Table(name = "member")
@Entity
public class User extends BaseEntity {

    @OneToMany(mappedBy = "author", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Question> questions = new ArrayList<>();
}

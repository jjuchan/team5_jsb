package com.team5_jsb.domain.user.user.entity;

import com.team5_jsb.domain.question.question.entity.Question;
import com.team5_jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "member")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
    private String username;
    private String password;
    private String email;
  
    @OneToMany(mappedBy = "author", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Question> questions = new ArrayList<>();
}

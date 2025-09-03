package com.team5_jsb;


import com.team5_jsb.domain.answer.answer.entity.Answer;
import com.team5_jsb.domain.answer.answer.repository.AnswerRepository;
import com.team5_jsb.domain.question.question.entity.Question;
import com.team5_jsb.domain.question.question.repository.QuestionRepository;
import com.team5_jsb.domain.user.user.entity.User;
import com.team5_jsb.domain.user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class TestInitData {

    @Autowired
    @Lazy
    private TestInitData self;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Bean
    ApplicationRunner initApplicationRunner(){
        return args -> {
            self.work1();
        };
    }

    @Transactional
    void work1() {
        if (questionRepository.count() > 0) return;

        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword(passwordEncoder.encode("1234"));
        user1.setEmail("user1@test.com");
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword(passwordEncoder.encode("1234"));
        user2.setEmail("user2@test.com");
        userRepository.save(user2);

        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setAuthor(user1);
        this.questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setAuthor(user2);
        this.questionRepository.save(q2);

        Answer answer1 = new Answer();
        answer1.setContent("댓글 1");
        answer1.setQuestion(q1);
        answer1.setAuthor(user1);
        answerRepository.save(answer1);

        Answer answer2 = new Answer();
        answer2.setContent("댓글 2");
        answer2.setQuestion(q2);
        answer2.setAuthor(user2);
        answerRepository.save(answer2);
    }
}

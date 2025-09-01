package com.team5_jsb;


import com.team5_jsb.domain.answer.answer.repository.AnswerRepository;
import com.team5_jsb.domain.question.question.entity.Question;
import com.team5_jsb.domain.question.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
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

    @Bean
    ApplicationRunner initApplicationRunner(){
        return args -> {
            self.work1();
        };
    }

    @Transactional
    void work1() {
        if (questionRepository.count() > 0 ) return ;

        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDateTime(LocalDateTime.now());
        this.questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDateTime(LocalDateTime.now());
        this.questionRepository.save(q2);  // 두번째 질문 저장

//        Answer answer1 = new Answer();
//        answer1.setCreateDateTime(LocalDateTime.now());
//        answer1.setContent("댓글 1");
//        answer1.setQuestion(q1);
//        answerRepository.save(answer1);
//        q1.setAnswerList();
//
//        Answer answer2 = new Answer();
//        answer2.setCreateDateTime(LocalDateTime.now());
//        answer2.setContent("댓글 2");
//        answer2.setQuestion(q2);
//        answerRepository.save(answer2);

    }
}

package com.team5_jsb.domain.answer.answer;

import com.team5_jsb.domain.answer.answer.entity.Answer;
import com.team5_jsb.domain.answer.answer.service.AnswerService;
import com.team5_jsb.domain.question.question.entity.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("Test")
public class AnswerServiceTest {

    @Autowired
    private AnswerService answerService;

    @Test
    @DisplayName("findAll()")
    void t1() {
        List<Answer> answerList = answerService.findAll();

        assertThat(answerList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("답변 저장")
    void t2() {
        Question question = new Question();
        question.setSubject("질문 제목1");
        question.setContent("질문 내용1");
        question.setCreateDate(LocalDateTime.now());

        answerService.create(question, "답변 내용 1");
        Answer answer = answerService.getAnswer(3);

        assertThat(answer.getContent()).isEqualTo(("답변 내용 1"));
    }

    @Test
    @DisplayName("답변 삭제")
    void t3() {
        //given
        Answer answer = answerService.getAnswer(1);
        assertThat(answer).isNotNull();

        //when
        answerService.deleteById(answer.getId());

        //then
        List<Answer> answerList = answerService.findAll();
        assertThat(answerList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("답변 수정")
    void t4() {
        Answer answer = answerService.getAnswer(1);
        assertThat(answer.getContent()).isEqualTo("댓글 1");

        answerService.update(answer, "댓글 1 수정");
        assertThat(answer.getContent()).isEqualTo("댓글 1 수정");
    }
}

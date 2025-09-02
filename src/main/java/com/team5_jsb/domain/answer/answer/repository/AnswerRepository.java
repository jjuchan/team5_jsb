package com.team5_jsb.domain.answer.answer.repository;

import com.team5_jsb.domain.answer.answer.entity.Answer;
import com.team5_jsb.domain.question.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AnswerRepository extends JpaRepository<Answer, Long> {
    // 답변을 생성일자 내림차순으로 조회
    @Query("SELECT a FROM Answer a WHERE a.question = :question ORDER BY a.createDate DESC")
    Page<Answer> findByQuestionOrderByCreateDateDesc(@Param("question") Question question, Pageable pageable);
}

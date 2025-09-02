package com.team5_jsb.domain.question.question.repository;

import com.team5_jsb.domain.question.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAll(Pageable pageable);

    @Query("""
        select distinct q from Question q 
        left join q.author u1 
        left join q.answerList a 
        left join a.author u2
        where q.subject like %:kw% 
        or q.content like %:kw%
        or u1.username like %:kw%
        or a.content like %:kw%
        or u2.username like %:kw%
            order by q.createdDate desc
    """)
    Page<Question> findAllByKeywordOrderByCreatedDateDesc(@Param("kw") String kw, Pageable pageable);
}
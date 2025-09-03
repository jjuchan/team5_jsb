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
    Page<Question> findByAuthor_IdOrderByCreatedDateDesc(Long authorId, Pageable pageable);

    @Query("""  
        select q from Question q 
        where q.subject like %:kw% 
        or q.content like %:kw%
        or q.author.username like %:kw%
            order by q.createdDate desc
    """)
    Page<Question> findAllByKeywordOrderByCreatedDateDesc(@Param("kw") String kw, Pageable pageable);
}
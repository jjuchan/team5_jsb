package com.team5_jsb.domain.user.user.repository;

import com.team5_jsb.domain.user.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    // 로그인용
    Optional<User> findByUsername(String username);

    // 회원가입 충복 체크
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // 이메일로 찾기
    Optional<User> findByEmail(String email);

    // 비밀번호 찾기용
    Optional<User> findByUsernameAndEmail(String username, String email);
}

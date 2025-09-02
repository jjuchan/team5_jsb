package com.team5_jsb.domain.user.user.service;

import com.team5_jsb.domain.user.user.dto.UserProfileDto;
import com.team5_jsb.domain.user.user.entity.User;
import com.team5_jsb.domain.user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 처리
    public User register(String username, String password, String email) {
        // 1. 입력 검증
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("사용자명은 필수입니다.");
        }
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("비밀번호는 4자 이상이어야 합니다.");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }

        // 2. 중복 검사
        if(userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
        }
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 3. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // 4. 사용자 생성 및 저장
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        
        User savedUser = userRepository.save(user);
        
        // 5. 저장 결과 검증
        if (savedUser == null || savedUser.getId() == null) {
            throw new RuntimeException("회원가입 처리 중 오류가 발생했습니다.");
        }
        
        return savedUser;
    }
    
    // 사용자 조회 메서드 추가
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
    
    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }
    
    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public UserProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. id=" + userId));
        return UserProfileDto.from(user);
    }
}
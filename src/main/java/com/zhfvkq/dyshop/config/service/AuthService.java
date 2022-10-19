package com.zhfvkq.dyshop.config.service;

import com.zhfvkq.dyshop.config.entity.User;
import com.zhfvkq.dyshop.config.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional // Write(Insert, Update, Delete)
    public User signup(User user) {
        String rawPassword = user.getPwd();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPwd(encPassword);
        user.setRole("ROLE_USER");

        User userEntity = userRepository.save(user);
        return userEntity;
    }

    public void logout(HttpSession session) {
    }
}
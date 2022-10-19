package com.zhfvkq.dyshop.config.service;

import com.zhfvkq.dyshop.config.auth.PrincipalDetails;
import com.zhfvkq.dyshop.config.entity.User;
import com.zhfvkq.dyshop.config.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null) {
            return null;
        } else {
            return new PrincipalDetails(userEntity);
        }
    }
}

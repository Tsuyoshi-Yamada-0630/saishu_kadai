package sample.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sample.common.dao.entity.Login;
import sample.common.dao.mapper.LoginMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoginMapper loginMapper;

    public UserDetailsServiceImpl(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login loginUser = loginMapper.findByUsername(username);
        if (loginUser == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりません: " + username);
        }
        
        return User.withUsername(loginUser.getUsername())
                .password(loginUser.getPassword())
                .roles("USER")
                .build();
    }
}
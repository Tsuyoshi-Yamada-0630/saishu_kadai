package sample.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;  // ← 追加
import sample.common.dao.entity.Login;
import sample.common.dao.mapper.LoginMapper;
import sample.common.form.RegisterForm;
import sample.common.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginMapper loginMapper;
    private final PasswordEncoder passwordEncoder;

    public LoginServiceImpl(LoginMapper loginMapper, PasswordEncoder passwordEncoder) {
        this.loginMapper = loginMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean login(String username, String password) {
        Login loginUser = loginMapper.findByUsername(username);
        if (loginUser == null) {
            return false;
        }
        return passwordEncoder.matches(password, loginUser.getPassword());
    }

    @Override
    @Transactional  
    public boolean register(RegisterForm form) {
        
        Login existUser = loginMapper.findByUsername(form.getUsername());
        if (existUser != null) {
            return false;
        }
        Login login = new Login();
        login.setUsername(form.getUsername());
        login.setPassword(passwordEncoder.encode(form.getPassword()));
        loginMapper.insert(login);
        return true;
    }
}
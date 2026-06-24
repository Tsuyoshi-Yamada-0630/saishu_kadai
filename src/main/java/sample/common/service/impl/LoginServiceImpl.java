package sample.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sample.common.dao.entity.Login;
import sample.common.dao.mapper.LoginMapper;
import sample.common.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean login(String username, String password) {
        Login loginUser = loginMapper.findByUsername(username);
        if (loginUser == null) {
            return false;
        }
        // BCryptで比較（平文 vs ハッシュ化済み）
        return passwordEncoder.matches(password, loginUser.getPassword());
    }

    @Override
    public boolean register(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        Login existUser = loginMapper.findByUsername(username);
        if (existUser != null) {
            return false;
        }
        Login login = new Login();
        login.setUsername(username);
        // パスワードをハッシュ化して保存
        login.setPassword(passwordEncoder.encode(password));
        loginMapper.insert(login);
        return true;
    }
}
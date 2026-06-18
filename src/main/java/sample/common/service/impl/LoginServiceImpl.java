package sample.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.common.dao.entity.Login;
import sample.common.dao.mapper.LoginMapper;
import sample.common.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{
	@Autowired
	private LoginMapper loginMapper;
	
	@Override
	public boolean Login(String username, String password) {
		Login loginUser = loginMapper.findByUsername(username);
		if(loginUser == null) {
		    return false;
		}
		if(loginUser.getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
		
	}
	
	@Override
	public boolean Register(String username, String password) {
		if(username.isEmpty() || password.isEmpty()) {
	        return false;
	    }
	    Login existUser = loginMapper.findByUsername(username);
	    if(existUser != null) {
	        return false;
	    }
		Login login = new Login();
		login.setUsername(username);
		login.setPassword(password);
		loginMapper.insert(login);
		return true;
		}	
}

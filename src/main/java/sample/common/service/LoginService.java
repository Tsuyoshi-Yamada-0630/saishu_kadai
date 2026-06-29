package sample.common.service;

import sample.common.form.RegisterForm;

public interface LoginService {
    public boolean login(String username, String password);
    public boolean register(RegisterForm form);  
}
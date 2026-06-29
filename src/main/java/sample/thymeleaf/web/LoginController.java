package sample.thymeleaf.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sample.common.form.RegisterForm;
import sample.common.service.LoginService;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginForm() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerForm() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("registerForm", new RegisterForm());  // ← フォームオブジェクトを渡す
        mv.setViewName("register");
        return mv;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(
            @Valid @ModelAttribute("registerForm") RegisterForm form,
            BindingResult bindingResult) {

        // バリデーションエラーがあればそのまま入力値を保持して再表示
        if (bindingResult.hasErrors()) {
            return "register";
        }

        boolean result = loginService.register(form);
        if (result) {
            return "redirect:/login";
        } else {
            // ユーザ名重複のエラーを項目に紐付け
            bindingResult.rejectValue("username", "error.username", "このユーザ名はすでに使われています");
            return "register";
        }
    }
}
package sample.thymeleaf.web;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpSession;
import sample.common.dao.entity.Task;
import sample.common.service.LoginService;
import sample.common.service.TaskService;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private TaskService taskService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginForm(ModelAndView mv) {
		mv.setViewName("login");
		return mv;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password, ModelAndView mv, HttpSession session) {
	    boolean result = loginService.Login(username, password);
	    if(result) {
	        session.setAttribute("username", username);
	        List<Task> tasks = taskService.all(username);
	        mv.addObject("tasks", tasks);
	        mv.setViewName("tasks/tasks");
	        return mv;
	    }else {
	        mv.addObject("errorMessage", "ユーザー名またはパスワードが違います");
	        mv.setViewName("login");
	        return mv;
	    }
	}	
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerForm(ModelAndView mv) {
		mv.setViewName("register");
		return mv;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(@RequestParam("username") String username, @RequestParam("password") String password, ModelAndView mv) {
	    boolean result = loginService.Register(username, password);
	    if(result) {
	        mv.setViewName("login");
	    } else {
	        mv.addObject("errorMessage", "入力内容を確認してください");
	        mv.setViewName("register");
	    }
	    return mv;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpSession session,ModelAndView mv) {
		session.invalidate();
		mv.setViewName("homePage");
		return mv;
	}
}
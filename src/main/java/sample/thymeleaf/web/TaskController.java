package sample.thymeleaf.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import sample.common.dao.entity.Task;
import sample.common.service.TaskService;

@Controller
public class TaskController{
	
	@Autowired
	private TaskService taskService;
	
	@RequestMapping(value = "/tasks", method = RequestMethod.GET)
	public ModelAndView all(ModelAndView mv,HttpSession session) {
		String username = (String) session.getAttribute("username");
		List<Task> tasks = taskService.all(username);
		mv.addObject("tasks", tasks);
		mv.setViewName("tasks/tasks");
		return mv;
	}
	
	@RequestMapping(value = "/tasks/new", method = RequestMethod.GET)
	public ModelAndView createForm(ModelAndView mv) {
		mv.setViewName("tasks/form-new");
		return mv;
	}
	
	@RequestMapping(value = "/tasks", method = RequestMethod.POST)
	public ModelAndView create(ModelAndView mv,@ModelAttribute Task task,HttpSession session) {
		String username = (String) session.getAttribute("username");
		task.setUsername(username);
		taskService.create(task);
		List<Task> tasks = taskService.all(username);
		mv.addObject("tasks", tasks);
		mv.setViewName("tasks/tasks");
		return mv;
	}
	
	@RequestMapping(value = "/tasks/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editForm(ModelAndView mv, @PathVariable("id") Long id) {
		Task task = taskService.single(id);
		mv.addObject("task", task);
		mv.setViewName("tasks/form-edit");
		return mv;
	}
	
	@RequestMapping(value = "/tasks/update/{id}", method = RequestMethod.POST)
	public ModelAndView update(ModelAndView mv, @PathVariable("id") Long id, @ModelAttribute Task task,HttpSession session) {
		task.setId(id);
		taskService.update(task);
		String username = (String) session.getAttribute("username");
	    List<Task> tasks = taskService.all(username);
	    mv.addObject("tasks", tasks);
		mv.setViewName("tasks/tasks");
		return mv;
	}
	
	@RequestMapping(value = "/tasks/delete/{id}", method = RequestMethod.POST)
	public ModelAndView delete(ModelAndView mv, @PathVariable("id") Long id,HttpSession session) {
		taskService.delete(id);
		String username = (String) session.getAttribute("username");
	    List<Task> tasks = taskService.all(username);
	    mv.addObject("tasks", tasks);
		mv.setViewName("tasks/tasks");
		return mv;
}
}
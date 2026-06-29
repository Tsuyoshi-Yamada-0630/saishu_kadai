package sample.thymeleaf.web;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sample.common.dao.entity.Task;
import sample.common.exception.TaskNotFoundException;
import sample.common.service.TaskService;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ModelAndView all(@RequestParam(value = "page", defaultValue = "1") int page, @AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView mv = new ModelAndView();
        String username = userDetails.getUsername();
        
        List<Task> tasks = taskService.all(username, page);
        
        long totalCount = taskService.count(username);
        int totalPages = (int) Math.ceil((double) totalCount / 10);
        if (totalPages == 0) totalPages = 1;

        mv.addObject("tasks", tasks);
        mv.addObject("currentPage", page);
        mv.addObject("totalPages", totalPages);
        
        mv.setViewName("tasks/tasks");
        return mv;
    }

    @RequestMapping(value = "/tasks/new", method = RequestMethod.GET)
    public ModelAndView createForm() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("task", new Task());
        mv.setViewName("tasks/form-new");
        return mv;
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public ModelAndView create(@Valid @ModelAttribute Task task, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView mv = new ModelAndView();
        String username = userDetails.getUsername();
        
        if (task.getStartDate() != null && task.getEndDate() != null) {
            if (task.getStartDate().isAfter(task.getEndDate())) {
                bindingResult.rejectValue("endDate", "error.endDate", "終了日は開始日より後の日付にしてください");
            }
        }
        
        if (bindingResult.hasErrors()) {
            mv.setViewName("tasks/form-new");
            return mv;
        }
        
        task.setUsername(username);
        taskService.create(task);
        
        return all(1, userDetails);
    }

    @RequestMapping(value = "/tasks/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editForm(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView mv = new ModelAndView();
        String username = userDetails.getUsername();
        Task task = taskService.single(id, username);

        if (task == null) {
            throw new TaskNotFoundException(id);
        }

        mv.addObject("task", task);
        mv.setViewName("tasks/form-edit");
        return mv;
    }

    @RequestMapping(value = "/tasks/update/{id}", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable("id") Long id, @Valid @ModelAttribute Task task, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView mv = new ModelAndView();
        String username = userDetails.getUsername();
        
        if (task.getStartDate() != null && task.getEndDate() != null) {
            if (task.getStartDate().isAfter(task.getEndDate())) {
                bindingResult.rejectValue("endDate", "error.endDate", "終了日は開始日より後の日付にしてください");
            }
        }
        
        if (bindingResult.hasErrors()) {
            mv.setViewName("tasks/form-edit");
            return mv;
        }
        
        task.setId(id);
        task.setUsername(username);
        taskService.update(task);

        return all(1, userDetails);
    }

    @RequestMapping(value = "/tasks/delete/{id}", method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
    	String username = userDetails.getUsername();
    	taskService.delete(id, username);
        
        return all(1, userDetails);
    }
}
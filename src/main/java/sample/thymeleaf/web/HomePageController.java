package sample.thymeleaf.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

	@Controller
	public class HomePageController {
		@RequestMapping(value = "/", method = RequestMethod.GET)
		public ModelAndView top(ModelAndView mv) {
			mv.setViewName("homePage");
			return mv;
		}
}

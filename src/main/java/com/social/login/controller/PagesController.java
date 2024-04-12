
package com.social.login.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.social.login.dto.UserRegistrationForm;
import com.social.login.exception.UserAlreadyExistAuthenticationException;
import com.social.login.service.MessageService;
import com.social.login.service.UserService;

/**
 * PagesController 
 */
@RestController
public class PagesController {

	private final Logger logger = LogManager.getLogger(getClass());

	@Resource
	private MessageService messageService;

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, @RequestParam(value = "errorCode", required = false) String errorCode)
			throws ServletException, IOException {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("css", "danger");
			model.addObject("msg", error);
		} else if (logout != null) {
			model.addObject("css", "success");
			model.addObject("msg", messageService.getMessage("message.logout." + logout));
		}
		model.addObject("title", "Login Page");
		model.setViewName("login");
		return model;
	}

	@GetMapping("/register")
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return new ModelAndView("register", "userRegistrationForm", new UserRegistrationForm());
	}

	@PostMapping("/register")
	public ModelAndView registerUser(@Valid UserRegistrationForm userRegistrationForm, BindingResult result, final HttpServletRequest request, RedirectAttributes attributes) {
		ModelAndView model = new ModelAndView("register");
		if (!result.hasErrors()) {
			try {
				userService.registerNewUser(userRegistrationForm);
				attributes.addFlashAttribute("css", "success");
				attributes.addFlashAttribute("msg", messageService.getMessage("message.regSucc"));
				model = new ModelAndView("redirect:/login");
			} catch (UserAlreadyExistAuthenticationException e) {
				logger.error(e);
				result.rejectValue("email", "message.regError");
			}
		}
		return model;
	}

	
}

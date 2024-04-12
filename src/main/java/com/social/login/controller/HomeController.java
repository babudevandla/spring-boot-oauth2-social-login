package com.social.login.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	private final Logger logger = LogManager.getLogger(getClass());
	
	@GetMapping({ "/", "/home" })
	public ModelAndView home(@RequestParam(value = "view", required = false) String view) {
		logger.info("Entering home page");
		ModelAndView model = new ModelAndView("home");
		model.addObject("title", "Home");
		model.addObject("view", view);
		return model;
	}
}

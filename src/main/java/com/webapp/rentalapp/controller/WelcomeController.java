package com.webapp.rentalapp.controller;

import com.webapp.rentalapp.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;


@Controller
public class WelcomeController {
	private static Logger logger = LoggerFactory.getLogger(WelcomeController.class);


	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	EquipmentRepository equipmentRepository;

	@Autowired
	MyUserDetailsService myUserDetailsService;

	@RequestMapping("/")
	public String welcome(SecurityContextHolderAwareRequestWrapper request) {

		logger.info(String.valueOf(request.isUserInRole("ROLE_ADMIN")));

		if(request.isUserInRole("ROLE_ADMIN"))
		{
			return "adminPage";
		}
		return "welcome";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login( SecurityContextHolderAwareRequestWrapper request) {
		return "login";
	}

	@RequestMapping("/logout")
	public String logout() { return "logout.html"; }


	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("clientForm", new Client());
		return "registration";
	}
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String processRegistration( RegistrationForm form) {

		logger.info(form.toString());
		clientRepository.save(form.toClient(bCryptPasswordEncoder));
		return "login";
	}

	//Users Controllers
	@RequestMapping("/welcome/showDb")
	public String showRentalDB(Model model	,SecurityContextHolderAwareRequestWrapper request) {

		model.addAttribute("equipment", equipmentRepository.findAll());
		return "rentalDB";
	}





	//Admin Controllers
	@RequestMapping("/admin")
	public String admin() {
		return "adminPage";
	}

	@RequestMapping("/admin/showUsers")
	public String getCountries(Model model	,SecurityContextHolderAwareRequestWrapper request) {



		model.addAttribute("clients", clientRepository.findAll());
		logger.info(String.valueOf(model));
		return "showUsers";
	}
}


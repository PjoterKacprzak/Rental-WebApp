package com.webapp.rentalapp.controller;

import com.webapp.rentalapp.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


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

		logger.info(String.valueOf(request.getUserPrincipal()));
		if (request.isUserInRole("ROLE_ADMIN")) {
			return "adminPage";
		} else {

			return "welcome";
		}

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(SecurityContextHolderAwareRequestWrapper request) {
		return "login";
	}

	@RequestMapping("/logout")
	public String logout() {
		return "logout";
	}


	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("clientForm", new Client());
		return "registration";
	}


	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String processRegistration(RegistrationForm form) {

		logger.info(form.toString());
		clientRepository.save(form.toClient(bCryptPasswordEncoder));
		return "login";
	}


	////////////////////Users Controllers///////////////////////////////
	@RequestMapping("/welcome/showDb")
	public String showRentalDB(Model model, SecurityContextHolderAwareRequestWrapper request) {

		model.addAttribute("equipment", equipmentRepository.findAll());
		return "rentalDB";
	}


	/////////////////////Admin Controllers//////////////////////////
	@RequestMapping("/admin")
	public String admin() {
		return "adminPage";
	}

	@RequestMapping("/admin/showUsers")
	public String getCountries(Model model, SecurityContextHolderAwareRequestWrapper request) {

		model.addAttribute("clients", clientRepository.findAll());
		logger.info(String.valueOf(model));
		return "showUsers";
	}


	@RequestMapping(value = "/editUsers/{username}", method = RequestMethod.GET)
	public String editUserPage(@PathVariable String username, Model model) {

		Client client = clientRepository.findByUsername(username);
		model.addAttribute("getClient", client);
		logger.info(String.valueOf(model));
		return "editUser";
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String updateUserData(@ModelAttribute("getClient") Client string) {

		logger.info(string.toString());
		String username = clientRepository.findByIds(string.getId());
		Client test = clientRepository.findByUsername(username);
		logger.info(test.toString());
		Long id = test.getId();

		if (string.getAdress() != "" || string.getAdress() != null) {
			clientRepository.saveAdress(string.getAdress(), id);
		}
		if (string.getTelephone() != "") {
			clientRepository.saveTelephone(string.getTelephone(), id);
		}
		if (string.getEmail() != "") {
			clientRepository.saveEmail(string.getEmail(), id);
		}

		return "redirect:/";
	}
}



//TODO Wszystkie html są do ujednolicenia żeby łatwiej się robiło frontend!
//FIXME Edycja Prawie działa:
// - znika imię - pewnie przez to że jest jako hasło gdzieś tam w tej pamięci
// - pierdoli się rola, jak jest przesyłana taka sama jak była to się zeruje a jak wstawiam 1,2,3 to wywala błędy

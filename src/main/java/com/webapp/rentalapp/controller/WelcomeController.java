package com.webapp.rentalapp.controller;

import com.webapp.rentalapp.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

		logger.info(String.valueOf(request.getUserPrincipal()));
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

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String updateUserData(@ModelAttribute("client") Client client){
		clientRepository.save(client);
		return "redirect:/";
	}

	//Users Controllers
	@RequestMapping("/welcome/showDb")
	public String showRentalDB(Model model	,SecurityContextHolderAwareRequestWrapper request) {

		model.addAttribute("equipment", equipmentRepository.findAll());
		return "rentalDB";
	}
	//Currently not used
//	@RequestMapping(value = "/editUser", method = RequestMethod.GET)
////	public  String currentData(MyUserDetailsService myUserDetailsService, Model model, SecurityContextHolderAwareRequestWrapper request){
//	public  String currentData(Model model	,SecurityContextHolderAwareRequestWrapper request){
//
//		System.out.println(request.getUserPrincipal());
//		model.addAttribute("clients",clientRepository.findByUsername(request.getRemoteUser()));
//		logger.info(String.valueOf(model));
////		myUserDetailsService.loadUserByUsername(request.getRemoteUser());
//		return "editUser";
//	}
//
//	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
//	public  String updatedData(){
////		myUserDetailsService.loadUserByUsername(request.getRemoteUser());
//		return "welcome";
//	}

	//Admin Controllers
	@RequestMapping("/admin")
	public String admin() {
		return "adminPage";
	}

	@RequestMapping("/admin/showUsers")
	public String getCountries(Model model	, SecurityContextHolderAwareRequestWrapper request) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		System.out.println(request.getRemoteUser());
//		System.out.println(auth.getDetails().toString());
//		System.out.println(request.getUserPrincipal());
//		System.out.println(request.getUserPrincipal().getName());

		model.addAttribute("clients", clientRepository.findAll());
		logger.info(String.valueOf(model));
		return "showUsers";
	}

	@RequestMapping("/editUsers/{id}")
	public String editUserPage(@PathVariable long id, Model model) {
		Client client = clientRepository.getOne(id);
		model.addAttribute("clients",client);
		logger.info(String.valueOf(model));
		return "editUser";
	}

}

//TODO Wszystkie html są do ujednolicenia żeby łatwiej się robiło frontend!
//FIXME Edycja Prawie działa:
// - znika imię - pewnie przez to że jest jako hasło gdzieś tam w tej pamięci
// - pierdoli się rola, jak jest przesyłana taka sama jak była to się zeruje a jak wstawiam 1,2,3 to wywala błędy

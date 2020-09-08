package com.webapp.rentalapp.controller;

import com.webapp.rentalapp.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.*;


@Controller
public class WelcomeController {
	private static Logger logger = LoggerFactory.getLogger(WelcomeController.class);

	public void DataBases()
	{
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			String connectionUrl = "jdbc:mysql://localhost:3306/Construction_Rental?serverTimezone=UTC&useLegacyDatetimeCode=false";
			Connection link = DriverManager.getConnection(connectionUrl);
		}catch (ClassNotFoundException e){
			System.out.println("class not foung");
		}

		catch (SQLException e){
			System.out.println("Bad Connections");
		};

	}

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

	//Adding new user
	@RequestMapping(value = "/admin/showUser/newUser", method = RequestMethod.GET)
	public String newUser(Model model) {

		Client newClient =new Client();
		model.addAttribute("newClient",newClient);
		return "newUser";
	}
	@RequestMapping(value = "/admin/showUser/newUser/confirm", method = RequestMethod.POST)
	public String newUserConfirm(@ModelAttribute(value = "newClient") Client newClient,@ModelAttribute(value = "isAdmin") boolean isAdmin) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connectionUrl = "jdbc:mysql://localhost:3306/Construction_Rental?serverTimezone=UTC&useLegacyDatetimeCode=false";
			Connection link = DriverManager.getConnection(connectionUrl,"TestUser","Anakonda-1");
			Statement statement= link.createStatement();
			newClient.setPassword(bCryptPasswordEncoder.encode(newClient.getPassword()));
			clientRepository.save(newClient);
			if(isAdmin)
			{
				try {
					logger.info(newClient.getId().toString());
					PreparedStatement stmt=link.prepareStatement("Insert into clients_roles(user_id,role_id) values("+newClient.getId()+",1)");
					stmt.executeUpdate();

				}catch (SQLException e){
					System.out.println("Bad Query!");}
			}
			else {
				try {
					clientRepository.save(newClient);
					PreparedStatement stmt=link.prepareStatement("Insert into clients_roles(user_id,role_id) values("+newClient.getId()+",3)");

					//statement.execute("Insert into clients_roles(user_id,role_id) values(5,1)");
				} catch (SQLException e) {
					System.out.println("Bad Query!");
				}
			}




//		}catch (ClassNotFoundException e){
//			System.out.println("class not found");
		}

		catch (SQLException | ClassNotFoundException e){
			System.out.println("Bad Connections");
		};
		return "redirect:/admin/showUsers";
	}
}



//TODO Wszystkie html są do ujednolicenia żeby łatwiej się robiło frontend!
//FIXME Edycja Prawie działa:
// - znika imię - pewnie przez to że jest jako hasło gdzieś tam w tej pamięci
// - pierdoli się rola, jak jest przesyłana taka sama jak była to się zeruje a jak wstawiam 1,2,3 to wywala błędy

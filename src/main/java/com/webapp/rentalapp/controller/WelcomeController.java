package com.webapp.rentalapp.controller;

import com.webapp.rentalapp.model.Client;
import com.webapp.rentalapp.model.Equipment;
import com.webapp.rentalapp.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.List;


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
	OrderRepository orderRepository;

	@Autowired
	EquipmentRepository equipmentRepository;

	@Autowired
	MyUserDetailsService myUserDetailsService;

	@RequestMapping("/")
	public String welcome(SecurityContextHolderAwareRequestWrapper request, Model model) {

		logger.info(String.valueOf(request.isUserInRole("ROLE_ADMIN")));

		logger.info(String.valueOf(request.getRemoteUser()));
		if (request.isUserInRole("ROLE_ADMIN")) {
			model.addAttribute("username",request.getRemoteUser());
			model.addAttribute("title","Admin Page");
			return "adminPage";
		} else {
			model.addAttribute("username",request.getRemoteUser());
			model.addAttribute("title","Welcome User");
			return "welcome";
		}

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(SecurityContextHolderAwareRequestWrapper request, Model model) {
		model.addAttribute("title","Login");
		return "login";
	}

	@RequestMapping("/logout")
	public String logout() {
		return "logout";
	}


	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("clientForm", new Client());
		model.addAttribute("title", "Registration");
		return "registration";
	}


	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String processRegistration(RegistrationForm form) {

		logger.info(form.toString());
		clientRepository.save(form.toClient(bCryptPasswordEncoder));
		return "login";
	}


	////////////////////Users Controllers///////////////////////////////
	@RequestMapping("user/showdb")
	public String showRentalDB(Model model, SecurityContextHolderAwareRequestWrapper request) {

		List<Equipment> test=equipmentRepository.findAllByStatusAvailable();
		model.addAttribute("title","Show equipment");
		model.addAttribute("equipments",test);
		model.addAttribute("equipment", equipmentRepository.findAll());
		return "rentalDB";
	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public String editSingleUser(SecurityContextHolderAwareRequestWrapper request, Model model) {

		Client client = clientRepository.findByUsername(request.getRemoteUser());
		model.addAttribute("getClient", client);
		model.addAttribute("title", "Edit user");
		logger.info(String.valueOf(model));
		return "editProfile";
	}

	@RequestMapping(value = "user/addtocart/{id}",method = RequestMethod.GET)
	public String addToCart(@PathVariable Long id, Model model, Authentication authentication)
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		Order order	= new Order(equipmentRepository.findByID(id),username);
		equipmentRepository.setStatusToInCart(id);
		orderRepository.save(order);

		return "redirect:/user/showdb";
	}

	@RequestMapping(value = "user/showcart",method = RequestMethod.GET)
			public String showCart(Model model,Authentication authentication)
	{
			model.addAttribute("pendingEquipments",orderRepository.findByNameAndStatus(authentication.getName()));

		return "showCart";
	}

	@RequestMapping(value = "user/showcart/confirm")
	public String showCartConfirm(Authentication authentication){




		List<Order> ordersInCart = orderRepository.findAllByStatusInCart(authentication.getName());
		Order order;
		Long id;
		for(int i=0;i<ordersInCart.size();i++)
		{
			id=ordersInCart.get(i).getId();
			order = orderRepository.findById(id);
		order.getEquipment().setStatus(Equipment.EquipmentStatus.pending);
		orderRepository.save(order);
		}

		return "welcome";
	}



	/////////////////////Admin Controllers//////////////////////////

	@RequestMapping("/admin")
	public String admin() {
		return "adminPage";
	}
	@RequestMapping("/admin/showUsers")
	public String getCountries(Model model, SecurityContextHolderAwareRequestWrapper request) {
		model.addAttribute("title","Show Users");
		model.addAttribute("clients", clientRepository.findAll());
		logger.info(String.valueOf(model));
		return "showUsers";
	}

	//Edit Existining user as admin
	@RequestMapping(value = "/editUsers/{username}", method = RequestMethod.GET)
	public String editUserPage(@PathVariable String username, Model model) {

		Client client = clientRepository.findByUsername(username);
		model.addAttribute("getClient", client);
		model.addAttribute("title", "Edit user");
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
		model.addAttribute("title","Add new User");
		return "newUser";
	}
	@RequestMapping(value = "/admin/showUser/newUser/confirm", method = RequestMethod.POST)
	public String newUserConfirm(@ModelAttribute(value = "newClient") Client newClient,@ModelAttribute(value = "role") String role) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connectionUrl = "jdbc:mysql://localhost:3306/Construction_Rental?serverTimezone=UTC&useLegacyDatetimeCode=false";
			Connection link = DriverManager.getConnection(connectionUrl,"TestUser","Anakonda-1");
			Statement statement= link.createStatement();
			newClient.setPassword(bCryptPasswordEncoder.encode(newClient.getPassword()));
			clientRepository.save(newClient);
			if(role.equals("admin")|| role.equals("Admin"))
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
					stmt.executeUpdate();

				} catch (SQLException e) {
					System.out.println("Bad Query!");
				}
			}
		} catch (SQLException | ClassNotFoundException e){
			System.out.println("Bad Connections");
		};
		return "redirect:/admin/showUsers";
	}

	//deleting user
	@RequestMapping(value = "/deleteUser/{username}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable String username, Model model) {

		clientRepository.delete(clientRepository.findByUsername(username));
		return "redirect:/admin/showUsers";
	}

	//Rental features

	@RequestMapping(value = "/admin/showDb")
	public String showAdminDB(Model model)
	{
		List<Equipment> equipment=equipmentRepository.findAll();
		model.addAttribute("equipments",equipment);
		return "adminDB";
	}

	@RequestMapping(value = "/admin/showDb/orders")
	public String showAdminOrders(Model model)
	{
		List<Order> orders=orderRepository.findOnlyByStatusPending();
		model.addAttribute("orders",orders);
		return "adminOrders";
	}

	@RequestMapping(value = "/admin/showDb/orders/confirm/{id}",method = RequestMethod.GET)
	public String showAdminOrdersConfirm(@PathVariable Long id, Model model)
	{
		Order order = orderRepository.findById(id);
		order.getEquipment().setStatus(Equipment.EquipmentStatus.accepted);
		orderRepository.save(order);
		return "redirect:/admin/showDb/orders";
	}
}
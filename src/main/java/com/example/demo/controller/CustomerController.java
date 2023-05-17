package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.entity.Customer;

@Controller
public class CustomerController {

	@Autowired
	private CustomerRepository cRepo;

	@GetMapping({"/login", "/"})
	public ModelAndView loginForm() {
		ModelAndView mav = new ModelAndView("login-form");
		return mav;
	}

	@PostMapping("/login")
	public ModelAndView login(@RequestParam("Email") String email, @RequestParam("Password") String password, HttpSession session) {
		ModelAndView mav;
		Optional<Customer> customerOpt = cRepo.findByEmailAndPassword(email, password);
		if (customerOpt.isPresent()) {
			mav = new ModelAndView("redirect:/listAll");
			mav.addObject("message", "Login successful!");
		} else {
			mav = new ModelAndView("redirect:/login");
			mav.addObject("error", "Invalid email or password");
		}
		return mav;
	}

	@GetMapping({"/register"})
	public ModelAndView registerForm() {
		ModelAndView mav = new ModelAndView("register-form");
		Customer newCustomer = new Customer();
		mav.addObject("customer", newCustomer);
		return mav;
	}

	@GetMapping({"/list"})
	public ModelAndView showEmployees() {
		ModelAndView mav = new ModelAndView("list-customers");
		List<Customer> list = cRepo.findAll();
		mav.addObject("customer", list);
		return mav;
	}


	@GetMapping("/addCustomerForm")
	public ModelAndView addEmployeeForm() {
		ModelAndView mav = new ModelAndView("add-customer-form");
		Customer newCustomer = new Customer();
		mav.addObject("customer", newCustomer);
		return mav;
	}

	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute Customer customer) {
		cRepo.save(customer);
		return "redirect:/listAll";
	}


	@GetMapping("/showUpdateForm")
	public ModelAndView showUpdateForm(@RequestParam Long customerId) {
		ModelAndView mav = new ModelAndView("add-customer-form");
		Customer customer = cRepo.findById(customerId).get();
		mav.addObject("customer", customer);
		return mav;
	}

	@GetMapping("/deleteCustomer")
	public String deleteCustomer(@RequestParam Long customerId) {
		cRepo.deleteById(customerId);
		return "redirect:/list";
	}

}
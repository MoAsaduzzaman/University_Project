package com.pulse.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pulse.dao.UserRepository;
import com.pulse.entities.User;
import com.pulse.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/")
	public String home(Model model) {
		
		model.addAttribute("title","Home - ContuctPulse");
		
		return "home";
	}
	
	
	@RequestMapping("/about")
	public String about(Model model) {
		
		model.addAttribute("title","About - ContuctPulse");
		
		return "about";
	}

	@RequestMapping("/signup")
	public String signup(Model model) {
		
		model.addAttribute("title","Register - ContuctPulse");
		model.addAttribute("user",new User());
		
		return "signup";
	}
	
	//handler for registering user
	
	//@PostMapping("/do_register")
	@PostMapping("/do_register")
	public String registerUser(@ModelAttribute("user") User user,@RequestParam(value="agreement",defaultValue = "false") boolean agreement,Model model, HttpSession session) {
		
		try {
			
			if(!agreement) {
				System.out.println("You have not agreed the terms and conditions");
				throw new Exception("You have not agreed the terms and conditions");
			}
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			System.out.println("Agreement: "+agreement);
			System.out.println("USER: "+user);
			
			User result = this.userRepository.save(user);
			
			model.addAttribute("user",new User());
			
			session.setAttribute("message", new Message("Successfully Registered !!","alert-success"));
			return "signup";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message", new Message("Something went wrong !!"+e.getMessage(),"alert-danger"));
			return "signup";
		}
		
	}
	
}

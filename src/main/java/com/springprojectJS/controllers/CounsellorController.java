package com.springprojectJS.controllers;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.springprojectJS.dto.DashboardResponse;
import com.springprojectJS.entities.Counsellor;
import com.springprojectJS.service.CounsellorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class CounsellorController {
	private CounsellorService counsellorService;
	
	public CounsellorController(CounsellorService counsellorService) { 
		//Auto wiring, Injecting dependent obj(CounsellorService) to target obj(CounsellorController) through constructor
		this.counsellorService=counsellorService;
	}
	
	@GetMapping("/")
	public String index(Model model) {
		Counsellor cobj=new Counsellor();
		
		/* We are Developing HTML form using TymeLeaf and doing form binding here
		 * Form Binding...> Mapping object data to form & Mapping form data to object
		 * model object will be sending data from Controller to UI, sending empty counsellor object to binding.using model.addAttribute("counsellor",cobj);
		 *th.object & th.field are thymeleaf attributes
		 
		 * First java class will be mapped to form using th.object
		 * previously......> 	 <form action="" method="post">
		 now .........><form th:action="@{/login}"th:object="${counsellor}" method="post">
		 
		  * .......To map a variable to field then we are using th:field
		previously......>  <td> <input type="password" name=""class="form-control"/>
		now .........>
		In Html , instead of name ..> th.field
		th.action= what is url pattern you want to call.
		
		With this form binding. whatever form we are submitting/doing , automatically saved in counsellor(entity class) counsellor object and it's field
		 */
		//sending data from controller to UI
		model.addAttribute("counsellor", cobj);
		//returning view name
		return "index";
	}
	
	@PostMapping("/login")
	public String handleLoginBtn(Counsellor counsellor, HttpServletRequest request, Model model) {
		
		Counsellor c=counsellorService.login(counsellor.getEmail(), counsellor.getPwd());
		//counsellor may avaiulable or not available
		if (c==null) {
			
			model.addAttribute("emsg", "Invalid Credentials");
			
			return "index";
		
		} else {
			
			//After valid login, store counsellorId in session for future purpose
			/*
			Http Session are a web component,  (21 aug 53)
			Any data if we want to access in entire application & anywhere in application,
			then we use session. it is a servlet topic.
			*/
			HttpSession session=request.getSession(true); //if request true, new sesssion object will come
			session.setAttribute("counsellorId", c.getCounsellorId()); //setting only a id, since counsellorid is req for AddEnquiry, view Enquiry
			
			//DashboardResponse dbresObj=counsellorService.getDashboardInfo(c.getCounsellorId());
			//model.addAttribute("dashboardInfo", dbresObj);
								
			return "redirect:/dashboard";
			
		}
		
	}
	
		@GetMapping("/dashboard")
		public String displayDashboard(HttpServletRequest req, Model model) {
			//get Existing session
			HttpSession session=req.getSession(false);
			Integer counsellorId=(Integer) session.getAttribute("counsellorId");
			
			DashboardResponse dbresObj=counsellorService.getDashboardInfo(counsellorId);
		    model.addAttribute("dashboardInfo", dbresObj);
			return "dashboard";		
		}
	
		@GetMapping("/register")
		public String reisterPage(Model model) { //Model param should be last in param.list
			Counsellor cobj=new Counsellor();
			
			/* Sending binding object has to be send here aswell
			 * Model is a map which is used to store data in the form of key and value
			 and that is used to send data from controller to UI 
			 
			model.addAttribute, adds an attribute to the model, which basically is just a map, 
			which in the end is exposed as request attributes*/
			
			//sending data from controller to UI
			
			model.addAttribute("counsellor", cobj); 
			
			return "register";
		}
		
		@PostMapping("/register")
		public String handleRegistration(Counsellor counsellor, Model model ) {
			
			//To check email is already register or not
			Counsellor byEmail=counsellorService.findByEmail(counsellor.getEmail());
			if (byEmail != null) {
				model.addAttribute("emsg", "Duplicate Email");
				return "register";
			}
			
			boolean isRegistered=counsellorService.register(counsellor);
			if(isRegistered) {
				//success msg
				model.addAttribute("smsg", "Registration Success!!"); 
				
			} else {
				//failure msg
				model.addAttribute("emsg", "Registration failed!"); 
			}
			return "register";
		}
		
		@GetMapping("/logout")
		public String logout(HttpServletRequest req) {
			
			//get existing session and invalidate it
			HttpSession session=req.getSession(false);				
			session.invalidate();
			
			//redirect to login page
			return "redirect:/";
			
		}
	
}

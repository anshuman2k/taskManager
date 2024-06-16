package com.project.taskmanager.controller;

import com.project.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("username")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/loginUser")
    public String login(){
        return "login";
    }


    @PostMapping("/AuthenticateUser")
    public String authenticateUser(@RequestParam(name = "username") String username,
                                   @RequestParam(name = "password") String password,
                                   RedirectAttributes redirectAttributes,Model model) {
        if (userService.authenticate(username, password)) {
            // Authentication successful, redirect to a secure area
            model.addAttribute("username", username);
            return "taskManager";
        } else {
            // Authentication failed, add an error message to the model
            model.addAttribute("errorMessage", "Invalid username or password ... Try Again!");
            return "login"; // Re-render the login page with error
        }
    }

    @PostMapping("/signupUser")
    public String signupUser(@RequestParam(name = "username") String username,
                                   @RequestParam(name = "password") String password,
                                   Model model) {
        if (userService.userExists(username)) {
            // Authentication successful, redirect to a secure area
            model.addAttribute("errorMessage","Username already taken. Please choose a different username.");
            return "signup";
        } else {
            // Authentication failed, add an error message to the model
            userService.createUser(username,password);
            return "redirect:/loginUser"; // Re-render the login page with error
        }
    }

    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete(); // Clear session attributes
        return "redirect:/login";
    }


    @GetMapping("/signup")
    public String printHello(){
        return "signup";
    }
}

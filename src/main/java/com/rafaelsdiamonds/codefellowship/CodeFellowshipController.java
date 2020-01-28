package com.rafaelsdiamonds.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class CodeFellowshipController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ApplicationUserRepository repo;

    @GetMapping("/")
    public String homepage(Principal p){
        if(p!= null){
            System.out.println(p.getName());
        }
        return "index";
    }

    @GetMapping("/signup")
    public String renderSignUpPage(){
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView addNewUser(String username, String password, String firstname, String lastname, String dateOfBirth, String bio){
        ApplicationUser newUser = new ApplicationUser(username,encoder.encode(password),firstname,lastname,dateOfBirth,bio);
        repo.save(newUser);
        return new RedirectView( "/");
    }

//    @GetMapping("/users")
//    public String renderUsers(Model m){
//        List<ApplicationUser> applicationUsers = repo.findAll();
//        m.addAttribute("applicationUsers",applicationUsers);
//        return "/users";
//    }

    @GetMapping("/users/{id}")
    public String renderUserPage(@PathVariable Long id, Model m){
        m.addAttribute("users",repo.getOne(id));
        return "oneuserpage";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}

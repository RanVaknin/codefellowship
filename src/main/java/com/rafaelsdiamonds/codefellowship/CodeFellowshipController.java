package com.rafaelsdiamonds.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CodeFellowshipController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ApplicationUserRepository repo;

    @GetMapping("/")
    public String homepage(Principal p, Model m) {
        if (p != null) {
            ApplicationUser principal = repo.findByUsername(p.getName());
            m.addAttribute("principal", principal);
        }
        return "index";
    }

    @GetMapping("/signup")
    public String renderSignUpPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView addNewUser(String username, String password, String firstname, String lastname, String dateOfBirth, String bio) {
        if (repo.findByUsername(username) == null) {
            ApplicationUser newUser = new ApplicationUser(username, encoder.encode(password), firstname, lastname, dateOfBirth, bio);
            repo.save(newUser);

            Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new RedirectView("/");
        } else {
            return new RedirectView("/signup?taken=true");
        }

    }

    @GetMapping("/users")
    public String renderUsers(Model m, Principal p) {
        if (p != null) {
            ApplicationUser principal = repo.findByUsername(p.getName());
            m.addAttribute("principal", principal);
        }

        List<ApplicationUser> applicationUsers = repo.findAll();
        m.addAttribute("applicationUsers", applicationUsers);
        return "/users";
    }

    @GetMapping("/users/{id}")
    public String renderUserPage(@PathVariable Long id, Model m, Principal p) {
        if (p != null) {
            ApplicationUser principal = repo.findByUsername(p.getName());
            m.addAttribute("principal", principal);

            ApplicationUser user = repo.getOne(id);
            m.addAttribute("user", user);

            return "oneuserpage";
        } else {
            return "index";
        }
    }

    @PostMapping("/posts")
    public String renderPosts(Principal p, String body, String timeStamp, ApplicationUser applicationUser) {
        Post post = new Post(body, timeStamp, repo.findByUsername(p.getName()));
        postRepository.save(post);
        return "oneuserpage";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // error handler
    @GetMapping("/error")
    public RedirectView handleError() {
        //do something like logging
        return new RedirectView("/");
    }

    @PostMapping("/follow/{id}")
    public RedirectView followUser(@PathVariable Long id, Model m, Principal p) {
        // grabs person who is browsing by principal
        ApplicationUser follower = repo.findByUsername(p.getName());
        // grabs author from the path variable and the id on the form.
        ApplicationUser author = repo.getOne(id);
        // adds author to followers' list of peopleWHoIFollow
        follower.getUsersWhoIFollow().add(author);
        //update in db.
        repo.save(follower);

        return new RedirectView("/");
    }

    @GetMapping("/feed")
    public String showFeed(Principal p, Model m) {
        if (p != null) {
            ApplicationUser principal = repo.findByUsername(p.getName());
            m.addAttribute("principal", principal);

        }
        return "feed";
    }
}

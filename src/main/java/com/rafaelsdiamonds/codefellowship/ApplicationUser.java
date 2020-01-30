package com.rafaelsdiamonds.codefellowship;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String dateOfBirth;
    private String bio;

    @OneToMany(mappedBy = "applicationUser")
    private List<Post> posts;

    @ManyToMany
    private Set<ApplicationUser> usersWhoFollowMe;

    @ManyToMany
    @JoinTable(
            name = "Follow",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<ApplicationUser> usersWhoIFollow;

    public ApplicationUser(String username, String password, String firstname, String lastname, String dateOfBirth, String bio) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }

    public ApplicationUser() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Set<ApplicationUser> getUsersWhoFollowMe() {
        return usersWhoFollowMe;
    }

    public Set<ApplicationUser> getUsersWhoIFollow() {
        return usersWhoIFollow;
    }
}



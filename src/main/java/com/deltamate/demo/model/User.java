package com.deltamate.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class User implements UserDetails {
    @Id
    @Column(length = 10)
    @NotBlank(message = "Please insert ID No!")
    private String userID;

    @NotBlank(message = "Please insert Name!")
    private String name;

    @NotBlank(message = "Please insert Password!")
    private String password;

    @Transient
    private String confirmPassword;

    @Transient
    private boolean logined;

    @NotBlank(message = "Please insert Company Name!")
    private String company;

    private String phone;

    @Email(message = "Wrong mail format!")
    private String mail;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(String userID, String name, String password, String confirmPassword, boolean logined, String company, String phone, String mail) {
        this.userID = userID;
        this.name = name;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.logined = logined;
        this.company = company;
        this.phone = phone;
        this.mail = mail;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(a -> new SimpleGrantedAuthority(a.getName().toString())).collect(Collectors.toList());
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userID;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isLogined() {
        return logined;
    }

    public void setLogined(boolean logined) {
        this.logined = logined;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}

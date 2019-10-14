package com.deltamate.demo.service;

import com.deltamate.demo.model.User;
import com.deltamate.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> viewAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void editUser(User user) {
        User user1 = findUserByID(user.getUserID());
        user1.setName(user.getName());
        user1.setCompany(user.getCompany());
        user1.setMail(user.getMail());
        user1.setPhone(user.getPhone());
        user1.setRoles(user.getRoles());
        if(null != user.getPassword() && !user.getPassword().equals("") && !user.getPassword().equals(user1.getPassword()))
            user1.setPassword(user.getPassword());
    }

    @Override
    public void removeUser(String userID) {
        userRepository.deleteById(userID);
    }

    @Override
    public User findUserByID(String userID) {
        return userRepository.findById(userID).orElseThrow(() -> new EntityNotFoundException(userID + " not found!"));
    }
}

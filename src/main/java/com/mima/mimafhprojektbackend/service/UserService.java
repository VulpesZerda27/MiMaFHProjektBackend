package com.mima.mimafhprojektbackend.service;
import com.mima.mimafhprojektbackend.exceptions.EmailAlreadyRegisteredException;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.model.ShoppingBasket;
import com.mima.mimafhprojektbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<MyUser> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
    public Optional<MyUser> getUserByEmail(String email) {return userRepository.getMyUserByEmail(email);}
    public MyUser createUser(MyUser user) throws EmailAlreadyRegisteredException {
        Optional<MyUser> optionalMyUser = userRepository.getMyUserByEmail(user.getEmail());

        if(optionalMyUser.isPresent()) {
            throw new EmailAlreadyRegisteredException("The email " + user.getEmail() + " is already registered.");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String bcryptPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(bcryptPassword);
        user.setRoles(Arrays.asList("USER"));
        user.setEnabled(true);
        user.setShoppingBasket(new ShoppingBasket());
        return userRepository.save(user);
    }

    public Optional<MyUser> deleteUserById(Long userId) {
        Optional<MyUser> toDelete = getUserById(userId);
        if(toDelete != null) userRepository.deleteById(userId);
        return toDelete;
    }
}

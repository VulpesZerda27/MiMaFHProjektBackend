package com.mima.mimafhprojektbackend.service;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.model.ShoppingBasket;
import com.mima.mimafhprojektbackend.repository.ShoppingBasketRepository;
import com.mima.mimafhprojektbackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<MyUser> GetAllUsers() {
        return userRepository.findAll();
    }

    public Optional<MyUser> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public MyUser createUser(MyUser user) {
        return userRepository.save(user);
    }

    public MyUser updateUser(Long userId, MyUser userDetails) {
        MyUser user = userRepository.findById(userId).orElseThrow();
        user.setUserFirstName(userDetails.getUserFirstName());
        user.setUserLastName(userDetails.getUserLastName());
        user.setUserEmail(userDetails.getUserEmail());
        user.setUserPassword(userDetails.getUserPassword());
        user.setUserName(userDetails.getUserName());
        user.setIsAdmin(userDetails.getIsAdmin());
        return userRepository.save(user);
    }


    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }


}

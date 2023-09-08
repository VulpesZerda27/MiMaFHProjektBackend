package com.mima.mimafhprojektbackend.service;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.model.ShoppingBasket;
import com.mima.mimafhprojektbackend.repository.ShoppingBasketRepository;
import com.mima.mimafhprojektbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<MyUser> GetAllUsers() {
        return userRepository.findAll();
    }

    public Optional<MyUser> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
    public Optional<MyUser> getUserByEmail(String email) {return userRepository.getMyUserByUserEmail(email);}

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
        user.setRole(userDetails.getRole());
        return userRepository.save(user);
    }


    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }


}

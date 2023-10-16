package com.mima.mimafhprojektbackend.repository;

import com.mima.mimafhprojektbackend.model.MyUser;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    public Optional<MyUser> getMyUserByEmail(@Email String userEmail);
    @Query("SELECT u.shoppingBasket.id FROM MyUser u WHERE u.id = :userId")
    Optional<Long> findShoppingBasketIdByUserId(@Param("userId") Long userId);
}

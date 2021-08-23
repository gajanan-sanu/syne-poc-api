package com.synechron.user.repository;

import com.synechron.user.model.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

@Repository
public interface AppUserRepository extends JpaRepository <AppUser,Long> {

    AppUser findByEmailId(String emailId);
    AppUser findByUserName(String userName);


}
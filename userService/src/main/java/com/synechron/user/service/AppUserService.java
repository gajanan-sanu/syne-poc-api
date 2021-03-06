package com.synechron.user.service;


import com.synechron.user.exception.AppUserNotFoundException;
import com.synechron.user.model.AppUserResponse;
import com.synechron.user.model.AppUser;
import com.synechron.user.repository.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AppUserResponse userSingUp(AppUser appUser) {

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        log.info("appUser.getPassword() ---->" + appUser.getPassword());
        if (appUserRepository.findByEmailId(appUser.getEmailId()) != null) {
            return new AppUserResponse("failed", appUser.getEmailId() + " this email already registered, please login");
        }
        appUser = appUserRepository.save(appUser);
        return new AppUserResponse("success", appUser.getUserName() + " added into the DB system");
    }

    public AppUser getUseDetails(String emailId) throws AppUserNotFoundException {

        AppUser appUser = appUserRepository.findByEmailId(emailId);
        if (appUser == null) {
            throw new AppUserNotFoundException(emailId + " is not found in system, please singUp");
        }

        appUser.setPassword("*******");

        return appUser;
    }

    public AppUserResponse resetUserPwd(AppUser appUser) {

        log.info("Inside resetUserPwd method");

        if (appUserRepository.findByEmailId(appUser.getEmailId()) == null) {
            //return new AppUser("FAILED", "Product to be updated not found in the system");
            return new AppUserResponse("failed", appUser.getEmailId() + " is not registered in system, please singUp");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUser = appUserRepository.save(appUser);
        return new AppUserResponse("success", appUser.getEmailId() + " added into the DB system");
    }

    public AppUserResponse userLogIn(String emailId, String password) {

        log.info("userLogIn service");

        AppUser appUser = appUserRepository.findByEmailId(emailId);
        if (appUser == null) {
            return new AppUserResponse("failed", appUser.getEmailId() + " is not registered in system, please singUp");
        }

        if (bCryptPasswordEncoder.matches(password, appUser.getPassword())) {
            log.info("password matches");
            return new AppUserResponse("success", appUser.getEmailId() + " added into the DB system");
        }

        return new AppUserResponse("failed", emailId + "and " + password + " not matched");
    }
}

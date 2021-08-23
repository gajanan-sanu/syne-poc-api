package com.synechron.user.model;

import lombok.*;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Component
@Table(name = "AppUser")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String emailId;
    private String password;
    private Long phoneNum;
    private String userType;
    private String role;
    private Boolean locked = false;




}

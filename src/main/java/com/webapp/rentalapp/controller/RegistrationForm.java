package com.webapp.rentalapp.controller;


import com.webapp.rentalapp.model.Client;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {


    private String username;
    private String password;
    private String email;
    private String adress;
    private String telephone;




    private static Logger logger = LoggerFactory.getLogger(RegistrationForm.class);

//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder


    public Client toClient(BCryptPasswordEncoder bCryptPasswordEncoder){

        logger.info(username);
        logger.debug(username);
        logger.info(password);
        logger.info(password);
        return new Client(username,bCryptPasswordEncoder.encode(password),email,adress,telephone);
    }



    public String getUsername() { return username; }
    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "RegistrationForm{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

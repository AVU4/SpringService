package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.security.TokenProvider;
import com.example.demo.service.UserService;
import com.example.demo.validation.UsersValidator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@RestController
@CrossOrigin("*")
public class AuthController {


    public final AuthenticationManager authenticationManager;

    public final TokenProvider tokenProvider;

    public final UserService userService;

    private ObjectMapper mapper = new ObjectMapper();
    {
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }


    private UsersValidator usersValidator;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(PasswordEncoder passwordEncoder, UsersValidator usersValidator, UserService userService, TokenProvider tokenProvider, AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.usersValidator = usersValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @CrossOrigin
    @PostMapping("/login")
    public String Login(HttpServletRequest request){
        try{
            Scanner scanner = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
            User user = mapper.readValue(scanner.next(), User.class);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            User currentUser = userService.findOneByUsername(user.getUsername());

            if (currentUser == null){
                throw new UsernameNotFoundException("User not found");
            }

            String token = tokenProvider.resolveToken(user.getUsername());
            Map<Object, Object> responce = new HashMap<>();
            responce.put("may", "true");
            responce.put("token", token);
            return mapper.writeValueAsString(responce);
        }catch (AuthenticationException e ){
            throw new BadCredentialsException("Bad try");
        }catch (IOException | NullPointerException e1){
            return null;
        }
    }

    @CrossOrigin
    @PostMapping("/access")
    public String entrece(HttpServletRequest request) {
        try {
            Map<Object, Object> responce = new HashMap<>();
            Authentication auth = (Authentication) SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            User user = userService.findOneByUsername(username);
            responce.put("may", "true");
            responce.put("token", user.getAuthToken());
            return mapper.writeValueAsString(responce);
        }catch (JsonProcessingException e){
            return null;
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(HttpServletRequest request) throws JsonProcessingException {
        try {
            Scanner scanner = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
            User newUser = mapper.readValue(scanner.next(), User.class);
            if (!usersValidator.validate(newUser)) {
                return "{\"may\":\"false\"}";
            } else {
                newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
                userService.save(newUser);
                return "{\"may\":\"true\"}";
            }
        }catch (IOException e){
            return "{\"may\":\"false\"}";
        }
    }


    @CrossOrigin
    @PostMapping("/exit")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                String username = auth.getName();
                new SecurityContextLogoutHandler().logout(request, response, auth);
                User user = userService.findOneByUsername(username);
                user.setAuthToken(null);
                userService.save(user);
            }
            Map<Object, Object> resps = new HashMap<>();
            resps.put("may", "true");
            return mapper.writeValueAsString(resps);
        }catch(NullPointerException e){
            System.out.println("null");
            return null;
        }catch (JsonProcessingException e){
            return null;
        }
    }

    @CrossOrigin
    @PostMapping("/can")
    public String can(HttpServletRequest request, HttpServletResponse response){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                String username = auth.getName();
                new SecurityContextLogoutHandler().logout(request, response, auth);
                User user = userService.findOneByUsername(username);
                user.setAuthToken(null);
                userService.save(user);
            }
            return "{\"may\" : \"true\"}";
        }catch (NullPointerException e){
            return null;
        }
    }

}

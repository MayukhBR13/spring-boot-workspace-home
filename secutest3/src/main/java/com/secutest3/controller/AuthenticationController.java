package com.secutest3.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.secutest3.config.JwtRequest;
import com.secutest3.config.JwtResponse;
import com.secutest3.config.JwtUtils;
import com.secutest3.config.MyUserDetailsService;

@RestController
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsServiceImple;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/generate-token")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
    	System.out.println("inside generate-token");
    	System.out.println(jwtRequest.getUsername()+"------"+ jwtRequest.getPassword());
        try{
            authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        }catch(UsernameNotFoundException e){
            e.printStackTrace();
            throw new Exception("User not Found!!");
        }

       
        UserDetails userDetails=this.userDetailsServiceImple.loadUserByUsername(jwtRequest.getUsername());
         System.out.println("outside ***");
         String token=this.jwtUtils.generateToken(userDetails);
         System.out.println("outside2 ***");
        return ResponseEntity.ok(new JwtResponse(token)); 
    }

    private void authenticate(String username,String password) throws Exception{
        try{

            
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch(DisabledException e){
            throw new Exception("USER DISABLED "+e.getMessage());
        }catch(BadCredentialsException e){
            throw new Exception("Invalid Credentials "+e.getMessage());
        }
    }

    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal){
        return ((User)this.userDetailsServiceImple.loadUserByUsername(principal.getName()));
    }
    
    @GetMapping("/hi")
    public String hi(){
        return "hi";//((User)this.userDetailsServiceImple.loadUserByUsername(principal.getName()));
    }
}

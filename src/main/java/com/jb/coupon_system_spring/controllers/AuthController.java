package com.jb.coupon_system_spring.controllers;

import com.jb.coupon_system_spring.beans.Login;
import com.jb.coupon_system_spring.beans.User;
import com.jb.coupon_system_spring.exception.CouponSystemException;
import com.jb.coupon_system_spring.security.TokenService;
import com.jb.coupon_system_spring.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}, allowedHeaders = {"Content-Type", "Authorization"}, allowCredentials = "true")
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody User user) throws CouponSystemException {
        authService.register(user);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public Login login(@RequestBody User user) throws CouponSystemException {
        return authService.login(user);
    }

    @DeleteMapping("/logout/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@PathVariable int userId)  {
        tokenService.deleteToken(userId);
    }



    @GetMapping("/tokenExist")
    public boolean isTokenExist(@RequestBody UUID token) throws CouponSystemException {
        return tokenService.tokenExist(token);
    }
}

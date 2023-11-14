package com.jb.coupon_system_spring.service;

import com.jb.coupon_system_spring.beans.ClientType;
import com.jb.coupon_system_spring.beans.IdName;
import com.jb.coupon_system_spring.beans.Login;
import com.jb.coupon_system_spring.beans.User;
import com.jb.coupon_system_spring.exception.CouponSystemException;
import com.jb.coupon_system_spring.exception.ErrMsg;
import com.jb.coupon_system_spring.repos.CompanyRepository;
import com.jb.coupon_system_spring.repos.CustomerRepository;
import com.jb.coupon_system_spring.repos.UserRepository;
import com.jb.coupon_system_spring.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void addUser(String email, String password, ClientType clientType) {
        User user = User.builder()
                .email(email)
                .password(password)
                .clientType(clientType)
                .build();
        userRepository.save(user);
    }

    @Override
    public void updateUser(int id, String email, String password, ClientType clientType) {
        User user = User.builder()
                .id(id)
                .email(email)
                .password(password)
                .clientType(clientType)
                .build();
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void register(User user) throws CouponSystemException {
        if (user.getClientType().equals(ClientType.ADMINISTRATOR)) {
            throw new CouponSystemException(ErrMsg.SECURITY_CANNOT_CREATE_ADMIN);
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new CouponSystemException(ErrMsg.SECURITY_EMAIL_ALREADY_EXIST);
        }
        if (userRepository.existsByPassword(user.getPassword())) {
            throw new CouponSystemException(ErrMsg.SECURITY_PASSWORD_ALREADY_EXIST);
        }
        userRepository.save(user);
    }

    @Override
    public Login login(User user) throws CouponSystemException {
        if (!userRepository.existsByEmailAndPasswordAndClientType(user.getEmail(), user.getPassword(), user.getClientType())) {
            System.out.println(user.getClientType() + "Email: " + user.getEmail() + "Password: " + user.getPassword());
            throw new CouponSystemException(ErrMsg.SECURITY_EMAIL_OR_PASSWORD_INCORRECT);
        }
        int id = idName(user.getClientType(),user.getEmail()).getId();
        String name = idName(user.getClientType(),user.getEmail()).getName();
        return new Login(tokenService.addToToken(user),id,user.getClientType(),name);
    }
    @Override
    public void logout(int userId)  {
        tokenService.deleteToken(userId);
    }

    @Override
    public IdName idName(ClientType clientType,String email) {
        if (clientType.equals(ClientType.ADMINISTRATOR)) {
            int id = 1;
            String name = "Admin";
            return new IdName(id,name);
        }
        if (clientType.equals(ClientType.COMPANY)) {
            int id = companyRepository.getIdByEmail(email);
            String name = companyRepository.findById(id).get().getName();
            return new IdName(id,name);
        }
        if (clientType.equals(ClientType.CUSTOMER)) {
            int id = customerRepository.getIdByEmail(email);
            String firstName = customerRepository.findById(id).get().getFirstName();
            String lastName = customerRepository.findById(id).get().getLastName();
            String name = firstName+" "+lastName;
            return new IdName(id,name);
        }
        return null;
    }


}

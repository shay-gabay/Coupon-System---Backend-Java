package com.jb.coupon_system_spring.security;

import com.jb.coupon_system_spring.beans.ClientType;
import com.jb.coupon_system_spring.beans.User;
import com.jb.coupon_system_spring.exception.CouponSystemException;
import com.jb.coupon_system_spring.exception.ErrMsg;
import com.jb.coupon_system_spring.repos.CouponRepository;
import com.jb.coupon_system_spring.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Component
public class TokenServiceImpl implements TokenService {
    private Map<UUID, Information> tokens = new HashMap<>();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CouponRepository couponRepository;

    @Override
    public UUID addToToken(User user) {
        UUID token = UUID.randomUUID();
        Information info = Information.builder()
                .id(userRepository.getIdByEmail(user.getEmail()))
                .clientType(user.getClientType())
                .email(user.getEmail())
                .time(LocalDateTime.now())
                .build();
        tokens.put(token, info);
        System.out.println("User - " + user + "\n" + "Token - " + token + " Info - " + info);
        return token;
    }

    @Override
    public void deleteToken(int userId) {
        for (Iterator<Map.Entry<UUID, Information>> iterator = tokens.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<UUID, Information> entry = iterator.next();
            int id = entry.getValue().getId();
            if (userId == id) {
                UUID removedToken = entry.getKey();
                Information removedInfo = entry.getValue();
                System.out.println("************ Deleted Token - " + removedInfo + " UUID(" + removedToken + ")");
                iterator.remove();
            }
        }
    }

    @Override
    public boolean tokenExist(UUID token) throws CouponSystemException {
        if (!tokens.containsKey(token)) {
            throw new CouponSystemException(ErrMsg.TIME_OUT);
        }
        return true;
    }
    @Override
    public boolean isAllowed(UUID token, ClientType clientType) throws CouponSystemException {
        tokenExist(token);
        Information info = tokens.get(token);
        ClientType InfoClientType = info.getClientType();
        if (!clientType.equals(InfoClientType)) {
            throw new CouponSystemException(ErrMsg.CLIENT_NOT_ALLOWED);
        }
        return true;
    }

    @Scheduled(fixedRate = 1000 * 60)
    public void removeExpiredTokens() {
        LocalDateTime currentTime = LocalDateTime.now();
        for (Iterator<Map.Entry<UUID, Information>> iterator = tokens.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<UUID, Information> entry = iterator.next();
            LocalDateTime tokenTime = entry.getValue().getTime();
            if (tokenTime.isBefore(currentTime.minusMinutes(30))) {
                UUID removedToken = entry.getKey();
                Information removedInfo = entry.getValue();
                System.out.println("************ Expired Token - " + removedInfo + " UUID(" + removedToken + ")");
                iterator.remove();
            }
        }
    }

}

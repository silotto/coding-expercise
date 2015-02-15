package com.expercise.service.user;

import com.expercise.domain.user.RememberMeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserRememberMeTokenRepository implements PersistentTokenRepository {

    @Autowired
    private UserService userService;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        userService.saveRememberMeToken(token.getUsername(), token.getTokenValue(), token.getSeries(), token.getDate());
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        userService.updateRememberMeToken(tokenValue, series, lastUsed);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        RememberMeToken rememberMeToken = userService.findRememberMeToken(seriesId);
        return new PersistentRememberMeToken(rememberMeToken.getEmail(), seriesId, rememberMeToken.getToken(), rememberMeToken.getLastUsedTime());
    }

    @Override
    public void removeUserTokens(String userId) {
        userService.removeRememberMeToken(userId);
    }

}

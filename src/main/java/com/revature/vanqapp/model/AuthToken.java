package com.revature.vanqapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * AuthToken used for accessing the KrogerAPI
 */
@Getter @Setter
@RequiredArgsConstructor
@JsonIgnoreProperties(value={ "expire_time"}, allowGetters=true)
public class AuthToken {
    private String access_token;
    private String token_type;
    private int expires_in;
    private long checkout_time = System.currentTimeMillis();
    public boolean isNotTimedOut () {
        return (this.expires_in * 1000L + this.checkout_time < System.currentTimeMillis());
    }
}

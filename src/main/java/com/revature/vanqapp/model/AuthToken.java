package com.revature.vanqapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Singleton token generator
 */
@Data//@RequiredArgsConstructor @Getter @Setter
public class AuthToken {
    private String access_token;
    //Possibly not needed
    private String token_type;
    private int expires_in;
}

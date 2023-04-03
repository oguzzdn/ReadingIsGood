package com.example.demo.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin
@RequestMapping(value = "/v1/tokenApi")
public class SecurityController {
	private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

	@Value("${userName}")
	private String name;
	@Value("${userPassword}")
	private String userPassword;
	@Value("${jwt.expireTime}")
	private String expireTime;

	@RequestMapping(method = RequestMethod.POST, value = "/getToken", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> getToken(@RequestParam(value = "name") String username,
                           @RequestParam(value = "password") String password) {
        if (username.equals(name) && password.equals(userPassword)) {
        	logger.debug("Username={} got token successfully.", username);
            return new ResponseEntity<String>(getJWTToken(username), HttpStatus.OK);
        } else {
        	return new ResponseEntity<String>("Username or password not correct", HttpStatus.BAD_REQUEST);
        }
    }

    private String getJWTToken(String username) {
        String secretKey = "sec22RetKey11qawr";//SnASfXPwu9SsYah9
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        String token = Jwts.builder().setId("id").setSubject(username)
                .claim("authorities", grantedAuthorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+Integer.parseInt(expireTime)))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
        return "{\"access_token\":\""+token+"\"}";
    }
}
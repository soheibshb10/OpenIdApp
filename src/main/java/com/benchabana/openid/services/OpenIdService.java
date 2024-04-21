package com.benchabana.openid.services;


import com.benchabana.openid.model.IdpToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Base64;

@Service
public class OpenIdService {

    private static Logger LOG =  LoggerFactory.getLogger(OpenIdService.class);


    @Value("${openId.token.endpoint}")
    public String token_endpoint;

    @Value("${openId.token.redirect_uri}")
    public String redirect_uri;


    public IdpToken token(String code, String clientId, String secret) {
        String authorization = clientId+":"+secret;
        String authorization64 = "basic "+ Base64.getEncoder().encodeToString(authorization.getBytes());

        MultiValueMap headers = new HttpHeaders();
        headers.put("Authorization", Arrays.asList(new String[] { authorization64}));
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", redirect_uri);

        HttpEntity request = new HttpEntity(map,headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<IdpToken> response;

        try {
            response = restTemplate.exchange(token_endpoint, HttpMethod.POST, request, IdpToken.class);

            LOG.info("response  :\n" + response);

        } catch (HttpClientErrorException e) {
            LOG.info("Erreur : " + e.getStatusCode() + " data : " + e.getResponseBodyAsString());
            throw e;
        }
        return response.getBody();
    }
}

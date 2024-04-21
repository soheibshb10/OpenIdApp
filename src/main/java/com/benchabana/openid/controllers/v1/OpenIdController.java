package com.benchabana.openid.controllers.v1;


import com.benchabana.openid.model.IdpToken;
import com.benchabana.openid.services.OpenIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/openid")
public class OpenIdController {

    private OpenIdService openIdService;

    @Autowired
    public OpenIdController(OpenIdService openIdService) {
        this.openIdService = openIdService;
    }

    @GetMapping("/token")
    public IdpToken code(@RequestParam String code , @RequestParam String clientid , @RequestParam String secret) {

        return openIdService.token(code,clientid, secret);
    }

}
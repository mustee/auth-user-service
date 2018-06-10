package com.algorithms.auth.userservice.controllers;


import com.algorithms.auth.userservice.controllers.models.CreateCompanyModel;
import com.algorithms.auth.userservice.domain.enums.Provider;
import com.algorithms.auth.userservice.result.Result;
import com.algorithms.auth.userservice.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController{

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }


    @PostMapping("/create-company")
    public ResponseEntity createCompany(@Valid @RequestBody CreateCompanyModel model){
        Provider provider = Provider.valueOf(model.getProvider());

        if(provider != Provider.Auth && StringUtils.isBlank(model.getProviderId())){
            return ResponseEntity.badRequest().build();
        }

        Result result = accountService.createCompany(model.getEmail(), model.getEmail(), model.getPassword(), provider, model.getProviderId());
        if(result.hasError()){
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(result);
    }


    @GetMapping("/client")
    public ResponseEntity<Object> client(@RequestParam(value = "clientId") String clientId){
        if(StringUtils.isBlank(clientId)){
            return ResponseEntity.badRequest().build();
        }

        Result result = accountService.getClient(clientId);
        return new ResponseEntity<>(result, result.getSuccess()? HttpStatus.OK: HttpStatus.BAD_REQUEST);
    }

}

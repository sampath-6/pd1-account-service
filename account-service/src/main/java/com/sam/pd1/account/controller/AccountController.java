package com.sam.pd1.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.pd1.account.model.Account;
import com.sam.pd1.account.service.AccountService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/pd1/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

	@Autowired
    private  AccountService accountService;

    // CREATE
    @PostMapping
    public Account create(@RequestBody Account account) {
        return accountService.create(account);
    }

    // GET BY ID
    @GetMapping("/{accountId}")
    public Account getById(@PathVariable String accountId) {
        return accountService.getById(accountId);
    }

    // GET ALL
    @GetMapping
    public List<Account> getAll() {
        return accountService.getAll();
    }

    
}
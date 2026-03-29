package com.sam.pd1.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sam.pd1.account.model.Account;
import com.sam.pd1.account.repository.AccountCosmosRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class AccountService {

	@Autowired
    private  AccountCosmosRepository cosmosTemplate;

    public Account create(Account account) {
        return cosmosTemplate.save(account);
    }

    public Account getById(String accountId) {
        return cosmosTemplate.findById(accountId) .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));
    }


    public List<Account> getAll() {
        return (List<Account>) cosmosTemplate.findAll();
    }
}
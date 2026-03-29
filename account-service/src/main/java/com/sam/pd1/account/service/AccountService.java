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
    private  AccountCosmosRepository accountCosmosRepository;

    public Account create(Account account) {
        return accountCosmosRepository.save(account);
    }

    public Account getById(String accountId) {
        return accountCosmosRepository.findById(accountId) .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));
    }


    public Account getByEmail(String email) {
        return accountCosmosRepository.findByEmail(email);
    }
    
    public List<Account> getAll() {
        return (List<Account>) accountCosmosRepository.findAll();
    }
}
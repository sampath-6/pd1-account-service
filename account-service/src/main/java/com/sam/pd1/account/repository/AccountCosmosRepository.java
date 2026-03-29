package com.sam.pd1.account.repository;

import com.sam.pd1.account.model.Account;

import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;

@Repository
public interface AccountCosmosRepository extends CosmosRepository<Account, String> {

	 Account findByEmail(String email);
}

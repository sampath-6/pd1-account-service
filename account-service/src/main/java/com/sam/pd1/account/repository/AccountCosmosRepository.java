package com.sam.pd1.account.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sam.pd1.account.model.Account;

@Repository
public interface AccountCosmosRepository extends MongoRepository<Account, String> {

}

package com.sam.pd1.account.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "account")
public class Account {

    @Id
    private String accountId;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String city;
    private String country;
}
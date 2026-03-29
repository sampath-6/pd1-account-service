package com.sam.pd1.account.model;

import org.springframework.data.annotation.Id;

import com.azure.spring.data.cosmos.core.mapping.Container;

import lombok.Data;

@Data
@Container(containerName = "account")

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
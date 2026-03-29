package com.sam.pd1.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sam.pd1.account.model.Account;
import com.sam.pd1.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService service;

    @Autowired
    private ObjectMapper objectMapper;

    // 🔹 CREATE
    @Test
    void shouldCreateAccount() throws Exception {
        Account acc = new Account();
        acc.setAccountId("1");
        acc.setFirstname("Sam");

        when(service.create(acc)).thenReturn(acc);

        mockMvc.perform(post("/pd1/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(acc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value("1"));
    }

    // 🔹 GET BY ID
    @Test
    void shouldGetAccountById() throws Exception {
        Account acc = new Account();
        acc.setAccountId("1");

        when(service.getById("1")).thenReturn(acc);

        mockMvc.perform(get("/pd1/v1/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value("1"));
    }

    // 🔹 GET ALL
    @Test
    void shouldGetAllAccounts() throws Exception {
        Account acc = new Account();
        acc.setAccountId("1");

        when(service.getAll()).thenReturn(List.of(acc));

        mockMvc.perform(get("/pd1/v1/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountId").value("1"));
    }

    // 🔹 GET BY EMAIL
    @Test
    void shouldGetByEmail() throws Exception {
        Account acc = new Account();
        acc.setEmail("test@test.com");

        when(service.getByEmail("test@test.com")).thenReturn(acc);

        mockMvc.perform(get("/pd1/v1/accounts/email/test@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@test.com"));
    }
}
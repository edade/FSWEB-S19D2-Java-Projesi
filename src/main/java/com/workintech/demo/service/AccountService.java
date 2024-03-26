package com.workintech.demo.service;



import com.workintech.demo.entity.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Account save(Account account);
}
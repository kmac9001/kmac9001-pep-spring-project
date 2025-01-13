package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;

@Service
public class AccountService {
    AccountRepository AccountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.AccountRepository = accountRepository;
    }

    public Account persistAccount(Account a){
        return AccountRepository.save(a);
    }

    public Account findAccount(Account a){
        if(AccountRepository.accountByName(a.getUsername()) != null){
            return a;
        }
        else{
            return null;
        }
    }

    public Account validLogin(Account a){
        //unlikely to be given account id, so we need to return the database entry which will have the id
        Account check = AccountRepository.validAccount(a.getUsername(), a.getPassword());
        if(check != null){
            return check;
        }
        else{
            return null;
        }
    }

    //so we can check a message is being made by a valid user
    public boolean accountExists(Integer id){
        Account temp = this.AccountRepository.findById(id);
        if(temp != null){
            return true;
        }
        else{
            return false;
        }
    }
}

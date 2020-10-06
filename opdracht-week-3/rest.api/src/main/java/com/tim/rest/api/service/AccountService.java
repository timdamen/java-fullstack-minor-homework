package com.tim.rest.api.service;

import com.tim.rest.api.model.Account;
import com.tim.rest.api.repository.AccountOwnerRepository;
import com.tim.rest.api.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.RelationNotFoundException;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountOwnerRepository accountOwnerRepository;

    public ArrayList<Account> getAll(){
        return accountRepository.getAll();
    }

    public Account getById(int id){
        return accountRepository.getById(id);
    }

    public Account create(Account account){
        return accountRepository.create(account);
    }

    public void update(int id, Account account) throws RelationNotFoundException {
        if(!accountRepository.exists(id)){
            throw new RelationNotFoundException(
                    String.format("Account with ID %s not found.", id)
            );
        }
        accountRepository.update(id, account);
    }

    public void delete(int id) throws RelationNotFoundException {
        if(!accountRepository.exists(id)){
            throw new RelationNotFoundException(
                    String.format("Account with ID %s not found.", id)
            );
        }
        accountRepository.delete(id);
    }

    public void block(int id, boolean block) throws RelationNotFoundException {
        if(!accountRepository.exists(id)){
            throw new RelationNotFoundException(
                    String.format("Account with ID %s not found.", id)
            );
        } else {
            accountRepository.block(id, block);
        }
    }

    public void link(int accountId, int accountOwnerId) throws RelationNotFoundException {
        if(!accountRepository.exists(accountId)){
            throw new RelationNotFoundException(
                    String.format("Account with ID %s not found.", accountId)
            );
        }
        if(!accountOwnerRepository.exists(accountOwnerId)){
            throw new RelationNotFoundException(
                    String.format("Accountowner with ID %s not found.", accountOwnerId)
            );
        }
        accountRepository.link(accountId, accountOwnerId);
    }

    public void unlink(int accountId, int accountOwnerId) throws RelationNotFoundException {
        if(!accountRepository.exists(accountId)){
            throw new RelationNotFoundException(
                    String.format("Account with ID %s not found.", accountId)
            );
        }
        if(!accountOwnerRepository.exists(accountOwnerId)){
            throw new RelationNotFoundException(
                    String.format("Accountonwer with ID %s not found.", accountOwnerId)
            );
        }
        accountRepository.unlink(accountId, accountOwnerId);
    }

}

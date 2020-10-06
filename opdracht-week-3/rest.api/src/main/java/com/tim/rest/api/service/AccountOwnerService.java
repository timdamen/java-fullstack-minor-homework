package com.tim.rest.api.service;

import com.tim.rest.api.model.Account;
import com.tim.rest.api.model.AccountOwner;
import com.tim.rest.api.repository.AccountOwnerRepository;
import com.tim.rest.api.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.RelationNotFoundException;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AccountOwnerService {

    private final AccountRepository accountRepository;

    private final AccountOwnerRepository accountOwnerRepository;

    public Collection<AccountOwner> getAll(){
        return accountOwnerRepository.getAll();
    }

    public AccountOwner getById(int id){
        return accountOwnerRepository.getById(id);
    }

    public Collection<Account> getAccounts(int accountOwnerId) {
        return accountRepository.getByAccountOwnerId(accountOwnerId);
    }

    public AccountOwner create(AccountOwner accountOwner){
        return accountOwnerRepository.create(accountOwner);
    }

    public void update(int id, AccountOwner accountOwner) throws RelationNotFoundException {
        if(!accountOwnerRepository.exists(id)) {
            throw new RelationNotFoundException(
                    String.format("Accountowner with ID %s not found.", id)
            );
        }
        accountOwnerRepository.update(id, accountOwner);
    }

    public void delete(int id) throws RelationNotFoundException {
        if(!accountOwnerRepository.exists(id)) {
            throw new RelationNotFoundException(
                    String.format("Accountowner with ID %s not found.", id)
            );
        }
        accountOwnerRepository.delete(id);
    }
}

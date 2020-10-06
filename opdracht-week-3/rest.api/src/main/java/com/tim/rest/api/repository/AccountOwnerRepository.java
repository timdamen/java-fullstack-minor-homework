package com.tim.rest.api.repository;

import com.tim.rest.api.model.AccountOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AccountOwnerRepository {

    private final Map<Integer, AccountOwner> accountOwners;

    public boolean exists(int id){
        return accountOwners.containsKey(id);
    }

    public Collection<AccountOwner> getAll(){
        return accountOwners.values();
    }

    public AccountOwner getById(int id){
        return accountOwners.getOrDefault(id, null);
    }

    public AccountOwner create(AccountOwner accountOwner) {
        int id = nextId();
        accountOwner.setId(id);
        accountOwners.put(id, accountOwner);
        return accountOwner;
    }
    public void update(int id, AccountOwner accountOwner){
        accountOwners.put(id, accountOwner);
    }

    public void delete(int id){
        accountOwners.remove(id);
    }

    private Integer nextId() {
        return (accountOwners
                        .keySet()
                        .stream()
                        .max(Comparator.naturalOrder())
                        .orElse(0) + 1
        );
    }
}

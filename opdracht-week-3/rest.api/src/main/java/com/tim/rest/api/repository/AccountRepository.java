package com.tim.rest.api.repository;

import com.tim.rest.api.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AccountRepository {

    private final Map<Integer, Account> accounts;

    private final Map<Integer, HashSet<Integer>> accountLinks;

    public ArrayList<Account> getAll(){
        return new ArrayList(accounts.values());
    }

    public Account getById(int id){
        return accounts.getOrDefault(id, null);
    }

    public boolean exists(int id){
        return accounts.containsKey(id);
    }

    public Account create(Account account) {
        int id = nextId();
        account.setId(id);
        accounts.put(id, account);
        return account;
    }

    public void update(int id, Account account){
        accounts.put(id, account);
    }

    public void delete(int id){
        accounts.remove(id);
    }

    public void block(int id, boolean block){
        var account = accounts.get(id);
        account.setIsBlocked(block);
    }

    public Collection<Account> getByAccountOwnerId(int accountOwnerId) {
        var accountIds = accountLinks
                .entrySet()
                .stream()
                .filter(x -> x.getValue().contains(accountOwnerId))
                .map(x -> x.getKey())
                .collect(Collectors.toList());
        return accounts
                .entrySet()
                .stream()
                .filter(x -> accountIds.stream().anyMatch(aId -> aId.equals(x.getKey())))
                .map(x -> x.getValue())
                .collect(Collectors.toList());
    }

    private Integer nextId() {
        return (accounts
                    .keySet()
                    .stream()
                    .max(Comparator.naturalOrder())
                    .orElse(0) + 1
        );
    }

    public void link(int accountId, int accountOwnerId) {
        HashSet accountOwners = new HashSet<Integer>();
        if (accountLinks.containsKey(accountId)) {
            accountOwners = accountLinks.get(accountId);
        }
        accountOwners.add(accountOwnerId);
        accountLinks.put(accountId, accountOwners);
    }

    public void unlink(int accountId, int accountOwnerId) {
        if (accountLinks.containsKey(accountId)) {
            var accountOwners = accountLinks.get(accountId);
            accountOwners.remove(accountOwnerId);
            accountLinks.put(accountId, accountOwners);
        }
    }
}

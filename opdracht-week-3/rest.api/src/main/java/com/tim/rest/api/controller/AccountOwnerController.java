package com.tim.rest.api.controller;

import com.tim.rest.api.model.Account;
import com.tim.rest.api.model.AccountOwner;
import com.tim.rest.api.service.AccountOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationNotFoundException;
import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("account-owner")
@RequiredArgsConstructor
public class AccountOwnerController {

    private final AccountOwnerService accountOwnerService;

    @GetMapping()
    public ResponseEntity<Collection<AccountOwner>> getAll(){
        var accounts = accountOwnerService.getAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountOwner> getById(@PathVariable Integer id) {
        var account = accountOwnerService.getById(id);
        if(account == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/{id}/accounts")
    public ResponseEntity<Collection<Account>> getAllAccountsFromAccountOwners(@PathVariable Integer id) {
        if(accountOwnerService.getById(id) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var accounts = accountOwnerService.getAccounts(id);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountOwner> create(@Valid @RequestBody AccountOwner accountOwner, BindingResult result) {
        if(result.hasErrors()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        var createdAccount = accountOwnerService.create(accountOwner);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @Valid @RequestBody AccountOwner accountOwner, BindingResult result) {
        if(result.hasErrors()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        var savedAccountOwners = accountOwnerService.getById(id);
        if(savedAccountOwners == null) return new ResponseEntity(
                String.format("Accountowner with ID %s not found.", id),
                HttpStatus.NOT_FOUND
        );

        try {
            accountOwnerService.update(id, accountOwner);
        } catch (RelationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(accountOwner, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        if(accountOwnerService.getById(id) == null) return new ResponseEntity(
                String.format("Accountowner with ID %s not found.", id),
                HttpStatus.NOT_FOUND
        );

        try {
            accountOwnerService.delete(id);
        } catch (RelationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}

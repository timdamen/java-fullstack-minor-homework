package com.tim.rest.api.controller;

import com.tim.rest.api.model.Account;
import com.tim.rest.api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationNotFoundException;
import javax.validation.Valid;
import java.util.Collection;

@RequestMapping("account")
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping()
    public ResponseEntity<Collection<Account>> getAccounts(){
        var accounts = accountService.getAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> get(@PathVariable Integer id) {
        var account = accountService.getById(id);
        if(account == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Account> create(@Valid @RequestBody Account account, BindingResult result) {
        if(result.hasErrors()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var createdAccount = accountService.create(account);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @Valid @RequestBody Account account, BindingResult result) {
        if(result.hasErrors()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        var savedAccount = accountService.getById(id);
        if(savedAccount == null) return new ResponseEntity(
                (String.format("Account with ID %s not found.", id)),
                HttpStatus.NOT_FOUND
        );

        try {
            accountService.update(id, account);
        } catch (RelationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        if(accountService.getById(id) == null) return new ResponseEntity(
                (String.format("Account with ID %s not found.", id)),
                HttpStatus.NOT_FOUND
        );
        try {
            accountService.delete(id);
        } catch (RelationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity block(@PathVariable Integer id) {
        var account = accountService.getById(id);
        if(account == null) return new ResponseEntity(
                (String.format("Account with ID %s not found.", id)),
                HttpStatus.NOT_FOUND
        );

        try {
            accountService.block(id, true);
        } catch (RelationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PatchMapping("/{id}/unblock")
    public ResponseEntity unblock(@PathVariable Integer id) {
        var account = accountService.getById(id);
        if(accountService.getById(id) == null) return new ResponseEntity(
                (String.format("Account with ID %s not found.", id)),
                HttpStatus.NOT_FOUND
        );

        try {
            accountService.block(id, false);
        } catch (RelationNotFoundException e) {
            return  new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}

package com.tim.rest.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Account {
    private int id;
    private String iban;
    private Double balance;
    private boolean isBlocked;

    public void setIsBlocked(boolean block) {
        this.isBlocked = block;
    }
}

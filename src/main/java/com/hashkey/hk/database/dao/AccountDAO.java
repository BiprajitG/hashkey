package com.hashkey.hk.database.dao;

import com.hashkey.hk.model.Account;

public interface AccountDAO {

    void save(Account account);

    Account findById(int accountId);
}

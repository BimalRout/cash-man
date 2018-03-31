package com.bkr.atm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bkr.atm.domain.Accounts;

@Repository
public interface AccountRepo extends CrudRepository<Accounts, String> {
}

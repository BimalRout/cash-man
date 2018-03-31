package com.bkr.atm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bkr.atm.domain.Operation;

@Repository
public interface OperationRepo extends CrudRepository<Operation, Long> {
}

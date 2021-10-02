package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public interface TransferDao {

    List<Transfer> getAllTransfers(int userId);
    Transfer getTransferById(int transactionId);
    Transfer sendTransfer(int userFrom, int UserTo, BigDecimal amount);

}

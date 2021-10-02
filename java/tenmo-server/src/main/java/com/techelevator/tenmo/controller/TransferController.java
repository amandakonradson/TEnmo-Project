package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    @Autowired
    private TransferDao transferDao;

    @RequestMapping(path = "account/transfers/{id}", method = RequestMethod.GET)
    public List<Transfer> getAllTransfersById(@PathVariable int id) {
        List<Transfer> output = transferDao.getAllTransfers(id);
        return output;
    }

    @RequestMapping(path = "transfers/{id}", method = RequestMethod.GET)
    public Transfer getSelectedTransfer(@PathVariable int id) {
        Transfer transfer = transferDao.getTransferById(id);
        return transfer;
    }

    @RequestMapping(path = "transfer", method = RequestMethod.POST)
    public Transfer sendTransferRequest(@RequestBody Transfer transfer) {
        Transfer results = transferDao.sendTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        return results;
    }

}












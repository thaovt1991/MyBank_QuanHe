package com.codegym.controller;


import com.codegym.model.Customer;
import com.codegym.model.Transfer;
import com.codegym.service.customer.ICustomerService;
import com.codegym.service.transfer.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/transfers")
public class TransferController {

    @Autowired
    private ITransferService transferService;

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/{id}")
    private ModelAndView viewDeposits(@PathVariable Long id) {

        ModelAndView modelAndView = new ModelAndView();

        Optional<Customer> sender = customerService.findById(id);

        List<Customer> recipients = customerService.findAllNotId(id);

        if (sender.isPresent()) {
            modelAndView.setViewName("/customer/transfer");
            modelAndView.addObject("success", null);
            modelAndView.addObject("error", null);
            modelAndView.addObject("transfer", new Transfer());
            modelAndView.addObject("sender", sender.get());
            modelAndView.addObject("recipients", recipients);
        } else {
            modelAndView.setViewName("/error.404");
        }

        return modelAndView;
    }

    @PostMapping("/{senderId}")
    private ModelAndView doTransfer(@PathVariable Long senderId, @ModelAttribute Transfer transfer) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customer/transfer");

        Optional<Customer> sender = customerService.findById(senderId);

        if (sender.isPresent()) {
            Optional<Customer> recipient = customerService.findById(transfer.getRecipient().getId());

            if (recipient.isPresent()) {
                long senderBalance = sender.get().getBalance();
                long recipientBalance = recipient.get().getBalance();
                long transferAmount = transfer.getTransferAmount();
                long fees = 10;
                long feesAmount = transferAmount / fees;
                long transactionAmount = transferAmount + feesAmount;

                sender.get().setBalance(senderBalance - transactionAmount);
                customerService.save(sender.get());

                recipient.get().setBalance(recipientBalance + transferAmount);
                customerService.save(recipient.get());

                transfer.setFees(fees);
                transfer.setFeesAmount(feesAmount);
                transfer.setTransactionAmount(transactionAmount);
                transferService.save(transfer);

                List<Customer> recipients = customerService.findAllNotId(senderId);

                modelAndView.addObject("success", "Transfer successfully");
                modelAndView.addObject("error", null);
                modelAndView.addObject("transfer", new Transfer());
                modelAndView.addObject("sender", sender.get());
                modelAndView.addObject("recipients", recipients);
            }
        } else {
            modelAndView.setViewName("/error.404");
        }

        return modelAndView;
    }
}

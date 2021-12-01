package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.Deposit;
import com.codegym.model.Withdraw;
import com.codegym.service.customer.ICustomerService;
import com.codegym.service.deposit.IDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class DepositsController {
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IDepositService depositService;

    @GetMapping("/deposit/{id}")
    private ModelAndView viewDeposits(@PathVariable Long id){
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/customer/deposit");
            modelAndView.addObject("deposit", new Deposit());
            modelAndView.addObject("customer", customer.get());
            modelAndView.addObject("success", null);
            modelAndView.addObject("error", null);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }

    }
    @PostMapping("/deposit")
    private ModelAndView saveDeposits(@ModelAttribute("deposits") Deposit deposits){
        Customer customer = customerService.findById(deposits.getCustomer().getId()).get();
        long money_deposits = deposits.getAmount();
        boolean isMoney = false;
        if (money_deposits >= 1000 && money_deposits <= 1000000000) {
            isMoney = true;
        }
        ModelAndView modelAndView = new ModelAndView("/customer/deposit");
        if(isMoney){
        depositService.save(deposits);
        customer.setBalance(customer.getBalance()+deposits.getAmount());
        customerService.save(customer);
            modelAndView.addObject("message", "Deposits successfully");
        }else {
            modelAndView.addObject("error","Deposits error !");
        }
        modelAndView.addObject("deposit", new Deposit());
        modelAndView.addObject("customer",customer);
        return modelAndView ;
    }
}

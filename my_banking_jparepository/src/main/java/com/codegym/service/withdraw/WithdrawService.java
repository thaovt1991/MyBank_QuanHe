package com.codegym.service.withdraw;

import com.codegym.model.Withdraw;
import com.codegym.repository.IWithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class WithdrawService implements IWithdrawService {

    @Autowired
    private IWithdrawRepository withdrawRepository ;
    @Override
    public Iterable<Withdraw> findAll() {
        return withdrawRepository.findAll();
    }

    @Override
    public Optional<Withdraw> findById(Long id) {
        return withdrawRepository.findById(id);
    }

    @Override
    public void save(Withdraw withdraw) {
      withdrawRepository.save(withdraw) ;
    }

    @Override
    public void remove(Long id) {
      Withdraw withdraw = withdrawRepository.findById(id).get() ;
      withdraw.setDelete(true);
      withdrawRepository.save(withdraw);
    }
}

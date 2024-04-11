package utils.converter;

import DTO.BalanceDTO;
import entities.Balance;

public class BalanceConverter {
    public Balance apply(BalanceDTO balanceDTO){
        return Balance.builder()
                .id(balanceDTO.getId())
                .balance(balanceDTO.getBalance())
                .build();
    }
    public BalanceDTO applyDTO(Balance balance){
        return BalanceDTO.builder()
                .id(balance.getId())
                .balance(balance.getBalance())
               .build();
    }
}

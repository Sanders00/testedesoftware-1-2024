package br.edu.utfpr.bankapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.edu.utfpr.bankapi.dto.WithdrawDTO;
import br.edu.utfpr.bankapi.exception.NotFoundException;
import br.edu.utfpr.bankapi.model.Account;
import br.edu.utfpr.bankapi.model.Transaction;
import br.edu.utfpr.bankapi.model.TransactionType;
import br.edu.utfpr.bankapi.repository.AccountRepository;
import br.edu.utfpr.bankapi.repository.TransactionRepository;
import br.edu.utfpr.bankapi.validations.AvailableAccountValidation;
import br.edu.utfpr.bankapi.validations.AvailableBalanceValidation;

@ExtendWith(MockitoExtension.class)
public class WithdrawTest {

    @Mock
	AvailableAccountValidation availableAccountValidation;

    @Mock
	AvailableBalanceValidation availableBalanceValidation;

	@Mock
	AccountRepository accountRepository;

	@Mock
	TransactionRepository transactionRepository;

	@InjectMocks
	TransactionService service;

	@Mock
	Transaction transaction;

	@Captor
	ArgumentCaptor<Transaction> transactionCaptor;

	WithdrawDTO withdrawDTO;

	@Mock
	Account sourceAccount;


    @Test
    void deveriaSacar() throws NotFoundException {
        
        //ARRANGE
        double saldoicial = 150.85;

        long accountNumber = 12345;

        double amount = 100;

        withdrawDTO = new WithdrawDTO(accountNumber, amount);

        sourceAccount = new Account("John Smith", accountNumber, saldoicial, 0);

        transaction = new Transaction(sourceAccount, sourceAccount, amount, TransactionType.WITHDRAW);

        BDDMockito.given(availableAccountValidation
            .validate(withdrawDTO.sourceAccountNumber()))
            .willReturn(sourceAccount);

        BDDMockito.willDoNothing().given(availableBalanceValidation).validate(transaction);

        //ACT
        service.withdraw(withdrawDTO);

        //ASSERT
        BDDMockito.then(transactionRepository).should().save(transactionCaptor.capture());
        Transaction transactionSalva = transactionCaptor.getValue();

        Assertions.assertEquals(sourceAccount, transactionSalva.getSourceAccount());
        Assertions.assertEquals(withdrawDTO.amount(), transactionSalva.getAmount());
        Assertions.assertEquals(TransactionType.WITHDRAW, transactionSalva.getType());
        Assertions.assertEquals(sourceAccount.getBalance(), transactionSalva.getSourceAccount().getBalance());

    }
}

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

import br.edu.utfpr.bankapi.dto.TransferDTO;
import br.edu.utfpr.bankapi.exception.NotFoundException;
import br.edu.utfpr.bankapi.model.Account;
import br.edu.utfpr.bankapi.model.Transaction;
import br.edu.utfpr.bankapi.model.TransactionType;
import br.edu.utfpr.bankapi.repository.AccountRepository;
import br.edu.utfpr.bankapi.repository.TransactionRepository;
import br.edu.utfpr.bankapi.validations.AvailableAccountValidation;
import br.edu.utfpr.bankapi.validations.AvailableBalanceValidation;

@ExtendWith(MockitoExtension.class)
public class TransferTest {
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

	TransferDTO transferDTO;

	@Mock
	Account sourceAccount;

	Account receiverAccount;

    @Test
    void deveriaTransferir() throws NotFoundException {

        // ARRANGE
        double sourceAccountSaldoicial = 150.85;
        double receiverAccountSaldoicial = 300.0;

        long sourceAccountNumber = 12345;
        long receiverAccountNumber = 54321;

        double amount = 100;

        transferDTO = new TransferDTO(sourceAccountNumber, receiverAccountNumber, amount);
        sourceAccount = new Account("John Smith", sourceAccountNumber, sourceAccountSaldoicial, 0);
        receiverAccount = new Account("Jane Doe", receiverAccountNumber, receiverAccountSaldoicial, 0);

        BDDMockito.given(availableAccountValidation
                .validate(transferDTO.sourceAccountNumber())).willReturn(sourceAccount);
        BDDMockito.given(availableAccountValidation
                .validate(transferDTO.receiverAccountNumber())).willReturn(receiverAccount);

        // ACT
        service.transfer(transferDTO);

        // ASSERT
        BDDMockito.then(transactionRepository).should().save(transactionCaptor.capture());
        Transaction transactionSalva = transactionCaptor.getValue();

        Assertions.assertEquals(sourceAccount, transactionSalva.getSourceAccount());
        Assertions.assertEquals(receiverAccount, transactionSalva.getReceiverAccount());
        Assertions.assertEquals(amount, transactionSalva.getAmount());
        Assertions.assertEquals(TransactionType.TRANSFER, transactionSalva.getType());
        Assertions.assertEquals(sourceAccountSaldoicial - amount, sourceAccount.getBalance());
        Assertions.assertEquals(receiverAccountSaldoicial + amount, receiverAccount.getBalance());

    }
}

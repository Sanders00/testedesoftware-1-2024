package br.edu.utfpr.bankapi.validation;

import br.edu.utfpr.bankapi.exception.NotFoundException;
import br.edu.utfpr.bankapi.model.Account;
import br.edu.utfpr.bankapi.validations.AvailableAccountValidation;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestEntityManager

public class AvailableAccountValidationTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AvailableAccountValidation availableAccountValidation;

    @Test
    @Transactional
    void deveriaRetornarTrueCasoExistaConta() throws NotFoundException {

        // ARRANGE
        Account account = new Account("Pafuncio Praxedes", 123123123L, 1000.0, 500.0);
        entityManager.persist(account);

        // ACT
        var result = availableAccountValidation.validate(account.getNumber());

        // ASSERT
        Assertions.assertEquals(account, result);
    }

    @Test
    @Transactional
    void deveriaRetornarNotFoundExceptionCasoNaoExistaConta() {

        // ARRANGE
        long accountNumber = 123123123L;

        // ACT / ASSERT
        Assertions.assertThrows(NotFoundException.class, () -> availableAccountValidation.validate(accountNumber));

    }
}

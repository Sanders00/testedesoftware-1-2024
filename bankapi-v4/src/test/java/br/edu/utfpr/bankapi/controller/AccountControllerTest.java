package br.edu.utfpr.bankapi.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.edu.utfpr.bankapi.dto.AccountDTO;
import br.edu.utfpr.bankapi.model.Account;
import br.edu.utfpr.bankapi.service.AccountService;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
class AccountControllerTest {
        
    @Autowired
    MockMvc mvc;

    
    // Gerenciador de persistência para os testes des classe
    @Autowired
    TestEntityManager entityManager;

    Account account; // Conta para os testes

    @BeforeEach
    void setup() {
        account = new Account("Lauro Lima",
                12346, 1000, 0);
        entityManager.persist(account); // salvando uma conta
    }

    @Test
    void deveriaRetornarStatus200ParaGetAll() throws Exception {

        // ARRANGE
        // ACT
        var res = mvc.perform(
                MockMvcRequestBuilders.get("/account")).andReturn().getResponse();
        
        // ASSERT
        Assertions.assertEquals(200, res.getStatus());
    }

    @Test
    void deveriaRetornarStatus200ParaGetById() throws Exception {
        // ARRANGE
        // ACT
        var res = mvc.perform(
                MockMvcRequestBuilders.get("/account/" + account.getNumber())).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(200, res.getStatus());
    }

    @Test
    void deveraRetornarStatus404ParaGetByIdInvalido() throws Exception {
        // ARRANGE
        var id = -1;
        // ACT
        var res = mvc.perform(
                MockMvcRequestBuilders.get("/account/" + id)).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(404, res.getStatus());
    }

    @Test
    void deveriaRetornarStatus201ParaCreate() throws Exception {

        // ARRANGE
        var json = """
            {
            "name": "Ricardo Sobjak",
            "number": 12345,
            "balance": 1000,
            "specialLimit": 1000
        }
        """;

        // ACT
        var res = mvc.perform(
                MockMvcRequestBuilders.post("/account")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(201, res.getStatus());
    }

    @Test
    void deveriaRetornarStatus400ParaCreateInvalido() throws Exception {
        
        // ARRANGE
        var json = "{}";

        // ACT
        var res = mvc.perform(
                MockMvcRequestBuilders.post("/account")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(400, res.getStatus());
    }

    @Test
    void deveriaRetornarStatus201ParaUpdate() throws Exception {
        
        // ARRANGE
        var id = 1;
        var json = """
            {
                "name": "Ricardo Sobjak",
                "number": 11111,
                "balance": 1000,
                "specialLimit": 1000
            }
        """;

        // ACT
        var res = mvc.perform(
                MockMvcRequestBuilders.put("/account/" + id)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(200, res.getStatus());
    }

    @Test
    void deveriaRetornarStatus404ParaUpdateComIdInvalido() throws Exception {

        // ARRANGE
        var id = -1;
        var json = """
            {
                "name": "Ricardo Sobjak",
                "number": 11111,
                "balance": 1000,
                "specialLimit": 1000
            }
        """;
        
        // ACT
        var res = mvc.perform(
                MockMvcRequestBuilders.put("/account/" + id)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(404, res.getStatus());
    }

    @Test
    void deveriaRetornarStatus400ParaUpdateComJsonInvalido() throws Exception {
        
        // ARRANGE
        var id = 1;
        var json = "{}";
        
        // ACT
        var res = mvc.perform(
                MockMvcRequestBuilders.put("/account/" + id)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(400, res.getStatus());
    }
}

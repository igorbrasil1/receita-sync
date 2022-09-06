package br.com.sicredi.receita.infra.dataprovider;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ReceitaDataProvider.class)
@ActiveProfiles("test")
class ReceitaDataProviderTest {

    @Autowired
    ReceitaDataProvider dataProvider;

    @Test
    void atualizar_conta_com_sucesso() throws RuntimeException, InterruptedException {

        String agencia = "0101";
        String conta = "123456";
        double saldo = 100.50;
        String status = "A";

        final var result = dataProvider.atualizarConta(agencia, conta, saldo, status);
        assertTrue(result);
    }

    @Test
    void atualizar_conta_com_valor_nulo() throws InterruptedException {
        String conta = "123456";
        double saldo = 100.50;
        String status = "A";
        final var result = dataProvider.atualizarConta(null, conta, saldo, status);
        assertFalse(result);
    }

    @Test
    void atualizar_conta_com_valor_errado() throws RuntimeException, InterruptedException {
        String agencia = "0101";
        String conta = "1234567";
        double saldo = 100.50;
        String status = "A";

        final var result = dataProvider.atualizarConta(agencia, conta, saldo, status);
        assertFalse(result);
    }
}
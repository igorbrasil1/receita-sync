package br.com.sicredi.receita.core.usecase;

import br.com.sicredi.receita.core.gateway.ReceitaGateway;
import br.com.sicredi.receita.cross.exception.InfraException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = EnviarDadosCsvReceitaUseCaseImpl.class)
@ActiveProfiles("test")
@TestPropertySource(properties = { "app.csv.delimiter=;" })
class EnviarDadosCsvReceitaUseCaseImplTest {

    @Autowired
    EnviarDadosCsvReceitaUseCaseImpl useCase;

    @MockBean
    ReceitaGateway gateway;

    @Test
    void processarCsv_com_sucesso() throws InterruptedException {
        Path resourceDirectory = Paths.get("src","test","resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        String arquivo = absolutePath + "\\temp\\test.csv";

        when(gateway.atualizarConta(any(), any(), anyDouble(), any())).thenReturn(true);

        useCase.processarCsv(arquivo);

        verify(gateway, times(5)).atualizarConta(any(), any(), anyDouble(), any());
    }

    @Test
    void processarCsv_com_erro() throws InterruptedException {
        Path resourceDirectory = Paths.get("src","test","resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        String arquivo = absolutePath + "\\temp\\test.csv";

        doThrow(RuntimeException.class).when(gateway).atualizarConta(any(), any(), anyDouble(), any());

        assertThrows(InfraException.class, () -> useCase.processarCsv(arquivo));
        verify(gateway, times(1)).atualizarConta(any(), any(), anyDouble(), any());
    }
}
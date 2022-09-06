package br.com.sicredi.receita.entrypoint.job;

import br.com.sicredi.receita.core.usecase.EnviarDadosCsvReceitaUseCase;
import br.com.sicredi.receita.cross.exception.InfraException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProcessarCsvReceitaJob.class)
@ActiveProfiles("test")
class ProcessarCsvReceitaJobTest {

    @Autowired
    ProcessarCsvReceitaJob processarCsvReceitaJob;

    @MockBean
    EnviarDadosCsvReceitaUseCase enviarDadosCsvReceitaUseCase;

    @Test
    void executar_arquivo_com_sucesso() {
        Path resourceDirectory = Paths.get("src","test","resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        String arquivo = absolutePath + "\\temp\\test.csv";

        doNothing().when(enviarDadosCsvReceitaUseCase).processarCsv(any());

        processarCsvReceitaJob.executar(arquivo);

        verify(enviarDadosCsvReceitaUseCase, times(1)).processarCsv(arquivo);
    }

    @Test
    void executar_arquivo_sem_sucesso() throws RuntimeException {
        Path resourceDirectory = Paths.get("src","test","resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        String arquivo = absolutePath + "\\temp\\test.csv";

        doThrow(InfraException.class).when(enviarDadosCsvReceitaUseCase).processarCsv(any());

        assertThrows(InfraException.class, () -> processarCsvReceitaJob.executar(arquivo));
        verify(enviarDadosCsvReceitaUseCase, times(1)).processarCsv(any());
    }
}
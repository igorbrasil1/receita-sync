package br.com.sicredi.receita.entrypoint.job;

import br.com.sicredi.receita.core.usecase.EnviarDadosCsvReceitaUseCase;
import br.com.sicredi.receita.cross.exception.InfraException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class ProcessarCsvReceitaJob {

    private final EnviarDadosCsvReceitaUseCase enviarDadosCsvReceitaUseCase;

    public ProcessarCsvReceitaJob(EnviarDadosCsvReceitaUseCase enviarDadosCsvReceitaUseCase) {
        this.enviarDadosCsvReceitaUseCase = enviarDadosCsvReceitaUseCase;
    }

    //@Scheduled(cron = "* * * * 7 *")
    public void executar(String arquivo) {
        log.info("Iniciando serviço que processa csv para RFB");
        try {
            enviarDadosCsvReceitaUseCase.processarCsv(arquivo);
        } catch (InfraException iex) {
            log.error("Erro no processamento do arquivo RFB. {}", iex.getMessage());
            throw iex;
        }

        log.info("Serviço RFB finalizado");
    }
}

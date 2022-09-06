package br.com.sicredi.receita.core.usecase;

import br.com.sicredi.receita.core.gateway.ReceitaGateway;
import br.com.sicredi.receita.cross.exception.InfraException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Service
public class EnviarDadosCsvReceitaUseCaseImpl implements EnviarDadosCsvReceitaUseCase {

    final ReceitaGateway receitaGateway;

    @Value("${app.csv.delimiter}")
    String delimiter;

    public EnviarDadosCsvReceitaUseCaseImpl(ReceitaGateway receitaGateway) {
        this.receitaGateway = receitaGateway;
    }

    @Override
    public void processarCsv(String arquivo) {
        log.info("Iniciando processamento do arquivo {}", arquivo);
        try (
            final var fr = new FileReader(arquivo);
            final var br = new BufferedReader(fr);
        ) {
            String linha = " ";
            String[] tempArr;
            while ((linha = br.readLine()) != null) {
                log.debug("Processando linha {}", linha);
                tempArr = linha.split(delimiter);
                Double saldo = null;
                try {
                    saldo = Double.parseDouble(tempArr[2].replace(',', '.'));
                } catch (NullPointerException | NumberFormatException npe) {
                    log.error("Registro com saldo invalido. Linha: {}", linha);
                    continue;
                }
                final var sucesso = receitaGateway.atualizarConta(
                        tempArr[0],
                        tempArr[1].replaceAll("\\-", ""),
                        saldo,
                        tempArr[3]);

                if (!sucesso) {
                    log.error("Registro invalido. Linha: {}", linha);
                }
            }

            log.info("Processamento do arquivo finalizado com sucesso");
        } catch (IOException ioe) {
            throw new InfraException("Erro ao abrir arquivo. Mensagem: {}", ioe.getMessage());
        } catch (RuntimeException | InterruptedException e) {
            throw new InfraException("Erro no serviço de comunicação com a RFB. Mensagem: {}", e.getMessage());
        }

    }
}

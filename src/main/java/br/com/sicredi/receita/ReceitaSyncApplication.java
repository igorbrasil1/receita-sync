package br.com.sicredi.receita;

import br.com.sicredi.receita.entrypoint.job.ProcessarCsvReceitaJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class ReceitaSyncApplication implements CommandLineRunner {

    @Autowired
    ProcessarCsvReceitaJob processarCsvReceitaJob;

    public static void main(String[] args) {
        SpringApplication.run(ReceitaSyncApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        final var arquivo = Arrays.stream(args).findFirst();
        processarCsvReceitaJob.executar(arquivo.orElseThrow());
    }
}

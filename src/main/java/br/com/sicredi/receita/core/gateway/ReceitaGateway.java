package br.com.sicredi.receita.core.gateway;

public interface ReceitaGateway {
    boolean atualizarConta(String agencia, String conta, double saldo, String status) throws InterruptedException;
}

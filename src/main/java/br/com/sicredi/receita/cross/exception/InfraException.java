package br.com.sicredi.receita.cross.exception;

public class InfraException extends BaseException {
    public InfraException(String defaultMessage) {
        super(defaultMessage);
    }

    public InfraException(String defaultMessage, String code) {
        super(defaultMessage, code);
    }

    public InfraException(String defaultMessage, String internalMessage, String code) {
        super(defaultMessage, internalMessage, code);
    }
}

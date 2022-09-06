package br.com.sicredi.receita.cross.exception;

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String internalMessage;
    private final String code;

    public BaseException(String defaultMessage) {
        super(defaultMessage);
        this.code = null;
        this.internalMessage = null;
    }

    public BaseException(String defaultMessage, String code) {
        super(defaultMessage);
        this.code = code;
        this.internalMessage = null;
    }

    public BaseException(String defaultMessage, String internalMessage, String code) {
        super(defaultMessage);
        this.internalMessage = internalMessage;
        this.code = code;
    }

    public String getInternalMessage() {
        return internalMessage;
    }

    public String getCode() {
        return code;
    }
}
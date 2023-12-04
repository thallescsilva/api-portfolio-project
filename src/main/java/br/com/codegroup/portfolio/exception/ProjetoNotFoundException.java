package br.com.codegroup.portfolio.exception;

public class ProjetoNotFoundException extends RuntimeException {
    public ProjetoNotFoundException(String message) {
        super(message);
    }
}

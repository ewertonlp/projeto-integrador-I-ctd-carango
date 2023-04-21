package com.backendprojetointegrador.lajeDev.domain.exception;

public class RecursoJaExistenteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RecursoJaExistenteException(String mensagem){
        super(mensagem);
    }
}

package com.backendprojetointegrador.lajeDev.domain.exception;

public class RecursoNaoEncontrado extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public RecursoNaoEncontrado(String mensagem) {
        super(mensagem);
    }
}

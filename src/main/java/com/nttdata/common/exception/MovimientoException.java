package com.nttdata.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MovimientoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MovimientoException(String message){
        super(message);
    }
}

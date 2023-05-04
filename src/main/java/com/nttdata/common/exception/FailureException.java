package com.nttdata.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FailureException extends RuntimeException {
    private final ErrorResponse failure;
    private final int errorCode;
}

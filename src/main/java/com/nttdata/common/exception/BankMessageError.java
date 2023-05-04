package com.nttdata.common.exception;

import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

public class BankMessageError {

    private BankMessageError() {
    }

    private static final String ERROR_CODE_001 = "NTT-001";
    private static final String ERROR_DESCRIPTION_001 = "Failed to get customer information";
    private static final String ERROR_CODE_002 = "NTT-002";
    private static final String ERROR_DESCRIPTION_002 = "Error al crear cuenta";
    private static final String ERROR_CODE_003 = "NTT-003";
    private static final String ERROR_DESCRIPTION_003 = "Error al consultar cuenta";
    private static final String ERROR_CODE_004 = "NTT-004";
    private static final String ERROR_DESCRIPTION_004 = "Errror al actualizar cuenta";
    private static final String ERROR_CODE_005 = "NTT-005";
    private static final String ERROR_DESCRIPTION_005 = "Error al eliminar cuenta";
    private static final String ERROR_CODE_006 = "NTT-006";
    private static final String ERROR_DESCRIPTION_006 = "Error al crear cliente";
    private static final String ERROR_CODE_007 = "NTT-007";
    private static final String ERROR_DESCRIPTION_007 = "Error al consultar clientes";
    private static final String ERROR_CODE_008 = "NTT-008";
    private static final String ERROR_DESCRIPTION_008 = "Error al actualizar";
    private static final String ERROR_CODE_009 = "NTT-009";
    private static final String ERROR_DESCRIPTION_009 = "Error al eliminar cliente";
    private static final String ERROR_CODE_010 = "NTT-010";
    private static final String ERROR_DESCRIPTION_010 = "Saldo insuficiente para realizar las transacciones";
    private static final String ERROR_CODE_011 = "NTT-011";
    private static final String ERROR_DESCRIPTION_011 = "Failed to create movement";
    private static final String ERROR_CODE_012 = "NTT-012";
    private static final String ERROR_DESCRIPTION_012 = "The user not assigned to the account does not exist";

    private static final String ERROR_CODE_013 = "NTT-013";
    private static final String ERROR_DESCRIPTION_013 = "Cupo diario retiro excede los $1000.00";

    public static final FailureException NTT001 = new FailureException(
            new ErrorResponse().errorCode(ERROR_CODE_001).userMessage(ERROR_DESCRIPTION_001), HttpStatus.BAD_REQUEST.value());
    public static final FailureException NTT002 = new FailureException(
            new ErrorResponse().errorCode(ERROR_CODE_002).userMessage(ERROR_DESCRIPTION_002), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT003 = new FailureException(
            new ErrorResponse().errorCode(ERROR_CODE_003).userMessage(ERROR_DESCRIPTION_003), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT004 = new FailureException(
            new ErrorResponse().errorCode(ERROR_CODE_004).userMessage(ERROR_DESCRIPTION_004), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT005 = new FailureException(
            new ErrorResponse().errorCode(ERROR_CODE_005).userMessage(ERROR_DESCRIPTION_005), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT006 = new FailureException(
            new ErrorResponse().errorCode(ERROR_CODE_006).userMessage(ERROR_DESCRIPTION_006), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT007 = new FailureException(
            new ErrorResponse().errorCode(ERROR_CODE_007).userMessage(ERROR_DESCRIPTION_007), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT008 = new FailureException(
            new ErrorResponse().errorCode(ERROR_CODE_008).userMessage(ERROR_DESCRIPTION_008), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT009 = new FailureException(
            new ErrorResponse().errorCode(ERROR_CODE_009).userMessage(ERROR_DESCRIPTION_009), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT010 = new FailureException(
            new ErrorResponse().errorCode(ERROR_CODE_010).userMessage(ERROR_DESCRIPTION_010), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT011 = new FailureException(
            new ErrorResponse().errorCode(ERROR_CODE_011).userMessage(ERROR_DESCRIPTION_011), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT012 = new FailureException(
            new ErrorResponse().errorCode(ERROR_CODE_012).userMessage(ERROR_DESCRIPTION_012), HttpStatus.INTERNAL_SERVER_ERROR.value());

    public static final FailureException NTT013 = new FailureException(
            new ErrorResponse().errorCode(ERROR_CODE_013).userMessage(ERROR_DESCRIPTION_013).status(BigDecimal.valueOf(HttpStatus.NO_CONTENT.value())), HttpStatus.INTERNAL_SERVER_ERROR.value());

}

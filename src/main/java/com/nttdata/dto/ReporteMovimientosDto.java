/**
 *
 */
package com.nttdata.dto;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gustavoefrainparcosanchez
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteMovimientosDto {

    private String fecha;
    private String cliente;
    private String numeroCuenta;
    private String tipo;
    private String saldoInicial;
    private String estado;
    private String movimiento;
    private String saldoDisponible;

    /*@Name(value = "Cliente")
    private String client;
    private String accountNumber;
    private String accountType;
    private Double initialBalance;
    private Double totalCredits;
    private Double totalDebits;
    private Double balance;
    private Boolean status;*/
}

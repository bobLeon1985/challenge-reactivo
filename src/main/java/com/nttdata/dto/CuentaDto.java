/**
 *
 */
package com.nttdata.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.enums.TipoCuentaEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author edwinleon
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CuentaDto {
    private Long idCuenta;
    private String numeroCuenta;
    private TipoCuentaEnum tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long idCliente;
    private String name;
}

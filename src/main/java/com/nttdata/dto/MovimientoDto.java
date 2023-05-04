/**
 * 
 */
package com.nttdata.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * @author edwinleon
 *
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MovimientoDto {

	private Long idMovimiento;
	private Date fecha;
	private String tipoMovimiento;
	private Double valor;
	private Double saldo;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String estado;
	private String numeroCuenta;
	private String tipoCuenta;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Long idCuenta;

}

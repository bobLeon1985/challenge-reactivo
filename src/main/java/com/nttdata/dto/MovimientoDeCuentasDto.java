/**
 * 
 */
package com.nttdata.dto;


import com.nttdata.enums.TipoMovimientoEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author edwinleon
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDeCuentasDto {
	private String numeroCuenta;
	private TipoMovimientoEnums tipoMovimiento;
	private BigDecimal valor;
	private Long idCuenta;
}

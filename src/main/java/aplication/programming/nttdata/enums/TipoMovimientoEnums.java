/**
 * 
 */
package aplication.programming.nttdata.enums;

import lombok.Getter;

/**
 * @author edwinleon
 *
 */
public enum TipoMovimientoEnums {
	
	R("Retiro"), D("Deposito");

	@Getter
	private String descripcion;

	TipoMovimientoEnums(String descripcion) {
		this.descripcion = descripcion;
	}

	
}

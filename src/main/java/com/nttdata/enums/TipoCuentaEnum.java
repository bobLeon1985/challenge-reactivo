/**
 * 
 */
package com.nttdata.enums;

import lombok.Getter;

/**
 * @author edwinleon
 *
 */
public enum TipoCuentaEnum {

	A("AHORRO"), C("CORRIENTE");

	@Getter
	private final String descripcion;

	TipoCuentaEnum(String descripcion) {
		this.descripcion = descripcion;
	}

}

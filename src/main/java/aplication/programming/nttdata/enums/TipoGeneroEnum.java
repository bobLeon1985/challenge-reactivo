/**
 * 
 */
package aplication.programming.nttdata.enums;

import lombok.Getter;

/**
 * @author edwinleon
 *
 */
public enum TipoGeneroEnum {

	M("Masculino"), F("Femenino");

	@Getter
	private String tipoGenero;

	TipoGeneroEnum(String tipoGenero) {
		this.tipoGenero = tipoGenero;
	}

		
}

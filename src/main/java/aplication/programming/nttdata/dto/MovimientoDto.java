/**
 * 
 */
package aplication.programming.nttdata.dto;

import aplication.programming.nttdata.enums.TipoMovimientoEnums;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

/**
 * @author edwinleon
 *
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovimientoDto {

	@NotNull(message = "The field date can not be null.")
	private Date date;

	@NotNull(message = "The field movementType can not be null.")
	private String movementType;

	@NotNull(message = "The field value can not be null.")
	private Double value;

	@NotNull(message = "The field accountNumber can not be null.")
	private String accountNumber;

	@NotNull(message = "The field accountType can not be null.")
	private String accountType;
}

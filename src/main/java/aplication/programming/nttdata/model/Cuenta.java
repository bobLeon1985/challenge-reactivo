package aplication.programming.nttdata.model;

import aplication.programming.nttdata.enums.TipoCuentaEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "cuenta")
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id_cuenta")
    private Long idCuenta;

    private String numeroCuenta;

    private TipoCuentaEnum tipoCuenta;

    private Double saldoInicial;

    private Boolean estado;

    @Column("fk_cliente")
    private Long idCliente;

}

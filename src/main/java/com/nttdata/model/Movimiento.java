package com.nttdata.model;

import com.nttdata.enums.TipoMovimientoEnums;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDate;

@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "movimiento")
public class Movimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id_movimiento")
    private Long idMovimiento;

    private LocalDate fecha;

    private TipoMovimientoEnums tipoMovimiento;

    private Double valor;

    private Double saldo;

    @Column("fk_cuenta")
    private Long idCuenta;
}

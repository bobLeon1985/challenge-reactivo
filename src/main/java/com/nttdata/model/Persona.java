package com.nttdata.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.checkerframework.common.aliasing.qual.Unique;
import com.nttdata.enums.TipoGeneroEnum;

import java.io.Serializable;


@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuperBuilder
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;

    private TipoGeneroEnum genero;

    private Integer edad;

    @Unique
    private String identificacion;

    private String direccion;

    private String telefono;

}

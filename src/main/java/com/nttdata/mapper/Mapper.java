package com.nttdata.mapper;

import com.nttdata.dto.CuentaDto;
import com.nttdata.model.Cuenta;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper()
public interface Mapper {
    Mapper INSTANCE = Mappers.getMapper(Mapper.class);

    @Mapping(source = "id", target = "idCliente")
    Cuenta cuentaDTotoCuenta(CuentaDto cuentaDto, Long id);

    @Mapping(source = "nombre", target = "name")
    CuentaDto cuentaToCuentaDto(Cuenta cuenta , String nombre);
}

package com.nttdata.mapper;

import com.nttdata.dto.CuentaDto;
import com.nttdata.dto.MovimientoDto;
import com.nttdata.model.Cuenta;
import com.nttdata.model.Movimiento;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper()
public interface Mapper {
    Mapper INSTANCE = Mappers.getMapper(Mapper.class);

    @Mapping(source = "id", target = "idCliente")
    Cuenta cuentaDTotoCuenta(CuentaDto cuentaDto, Long id);

    @Mapping(source = "nombre", target = "cliente")
    CuentaDto cuentaToCuentaDto(Cuenta cuenta, String nombre);

    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "tipoCuenta", source = "tipocuenta")
    @Mapping(target = "numeroCuenta", source = "numerocuenta")
    MovimientoDto movimientoToMoviemientoDto(Movimiento cuenta, String numerocuenta, String tipocuenta);

}


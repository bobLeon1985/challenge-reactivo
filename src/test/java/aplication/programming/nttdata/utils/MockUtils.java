package aplication.programming.nttdata.utils;

import aplication.programming.nttdata.dto.ClienteDto;
import aplication.programming.nttdata.enums.TipoCuentaEnum;
import aplication.programming.nttdata.enums.TipoGeneroEnum;
import aplication.programming.nttdata.enums.TipoMovimientoEnums;
import aplication.programming.nttdata.model.Cliente;
import aplication.programming.nttdata.model.Cuenta;
import aplication.programming.nttdata.model.Movimiento;
import aplication.programming.nttdata.vo.request.AccountRequestVO;
import aplication.programming.nttdata.vo.request.MovementRequestVO;
import aplication.programming.nttdata.vo.response.AccountResponseVO;
import aplication.programming.nttdata.vo.response.MovementResponseVO;
import aplication.programming.nttdata.vo.response.StatementResponseVO;

import java.sql.Date;
import java.time.LocalDate;

public class MockUtils {

    public static AccountRequestVO buildAccountRequestVO(){
        return AccountRequestVO.builder()
                .id(Long.valueOf(1))
                .accountNumber("478758")
                .accountType("Ahorro")
                .initialBalance(200.00)
                .status(Boolean.TRUE)
                .identification("1234567890")
                .name("Jose Lema")
                .build();
    }

    public static AccountResponseVO buildAccountResponseVO(){
        return AccountResponseVO.builder()
                .accountNumber("478758")
                .accountType("Ahorro")
                .initialBalance(2000.00)
                .status(Boolean.TRUE)
                .name("Jose Lema")
                .build();
    }
    public static Cliente buildClient (){
        return Cliente.builder()
                .idCliente(Long.valueOf(1))
                .identificacion("1234567890")
                .nombre("Jose Lema")
                .genero(TipoGeneroEnum.F)
                .edad(20)
                .direccion("Otavalo sn y principal")
                .telefono("098254785")
                .contrasenia("1234")
                .estado(Boolean.TRUE)
                .build();
    }

    public static ClienteDto buildClientDto (){
        return ClienteDto.builder()
                .idCliente(Long.valueOf(1))
                .identificacion("1234567890")
                .nombre("Jose Lema")
                .genero(TipoGeneroEnum.F)
                .edad(20)
                .direccion("Otavalo sn y principal")
                .telefono("098254785")
                .contrasenia("1234")
                .estado(Boolean.TRUE)
                .build();
    }

    public static MovementRequestVO buildMovementRequestVO(){
        return MovementRequestVO.builder()
                .date(Date.valueOf(LocalDate.now()))
                .movementType("Retiro")
                .value(100.00)
                .accountNumber("12345")
                .accountType("Ahorro")
                .build();
    }

    public static MovementResponseVO buildMovementResponseVO(){
        return MovementResponseVO.builder()
                .accountNumber("12345")
                .accountType("Ahorro")
                .date(Date.valueOf(LocalDate.now()))
                .movementType("Retiro")
                .value(100.00)
                .balance(300.00)
                .status(true)
                .build();
    }

    public static StatementResponseVO buildStatementResponseVO(){
        return StatementResponseVO.builder()
                .client("Jon Doe")
                .accountNumber("12345")
                .accountType("Ahorro")
                .initialBalance(200.00)
                .totalCredits(500.00)
                .totalDebits(300.00)
                .balance(400.00)
                .status(true)
                .build();

    }

    public static Cuenta buildAccount(){
        Cuenta account = new Cuenta();
        account.setIdCliente(1L);
        account.setNumeroCuenta("178496");
        account.setTipoCuenta(TipoCuentaEnum.A);
        account.setSaldoInicial(200.00);
        account.setEstado(true);
        return account;
    }

    public static Cuenta buildAccountFail(){
        Cuenta account = new Cuenta();
        account.setIdCliente(1L);
        account.setNumeroCuenta("86321");
        account.setTipoCuenta(TipoCuentaEnum.A);
        account.setSaldoInicial(0.00);
        account.setEstado(true);
        return account;
    }

    public static Movimiento buildMovement(){
        Movimiento movement = new Movimiento();
        movement.setIdMovimiento(1L);
        movement.setFecha(LocalDate.now());
        movement.setTipoMovimiento(TipoMovimientoEnums.D);
        movement.setSaldo(500.00);
        movement.setIdCuenta(1L);
        return movement;
    }
}

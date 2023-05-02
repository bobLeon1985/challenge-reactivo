package aplication.programming.nttdata.controller;

import aplication.programming.nttdata.services.ICuentaServicio;
import aplication.programming.nttdata.vo.request.AccountRequestVO;
import aplication.programming.nttdata.vo.response.AccountResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private ICuentaServicio cuentaServicio;

    @PostMapping
    public Mono<ResponseEntity<AccountResponseVO>> registrar(@RequestBody AccountRequestVO request){
        return cuentaServicio.registrar(request)
                .map(response -> ResponseEntity.ok().body(response));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<AccountResponseVO>>> listar(){
        return Mono.just(ResponseEntity.ok().body(cuentaServicio.listar()));
    }

    @PutMapping("/{idCuenta}")
    public Mono<ResponseEntity<String>> actualizar(@PathVariable Long idCuenta, @RequestBody AccountRequestVO request){
        return cuentaServicio.actualizar(idCuenta, request)
                .thenReturn(ResponseEntity.ok().body("Account updated successfully"));
    }

    @DeleteMapping("/{idCuenta}")
    public Mono<ResponseEntity<String>> eliminar(@PathVariable Long idCuenta){
        return cuentaServicio.eliminar(idCuenta)
                .thenReturn(ResponseEntity.ok().body("Account deleted successfully"));
    }
}

package aplication.programming.nttdata.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean("modelMapper")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /*@Bean("cuentaMapper")
    public ModelMapper cuentaMapper(){
        ModelMapper mapper = new ModelMapper();
        TypeMap<CuentaDto, Cuenta> typeMap = mapper.createTypeMap(CuentaDto.class, Cuenta.class);
        typeMap.addMapping(CuentaDto::getIdCliente, (dest, v)-> dest.setIdCliente((Long) v));
        return mapper;
    }*/
}

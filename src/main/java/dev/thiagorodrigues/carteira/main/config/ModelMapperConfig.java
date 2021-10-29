package dev.thiagorodrigues.carteira.main.config;

import dev.thiagorodrigues.carteira.application.dtos.TransacaoFormDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioFormDto;
import dev.thiagorodrigues.carteira.domain.entities.Transacao;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(TransacaoFormDto.class, Transacao.class)
                .addMappings(mapper -> mapper.skip(Transacao::setId));

        modelMapper.createTypeMap(UsuarioFormDto.class, Usuario.class)
                .addMappings(mapper -> mapper.skip(Usuario::setId));

        return modelMapper;
    }

}

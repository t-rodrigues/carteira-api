package dev.thiagorodrigues.carteira.domain.services;

import dev.thiagorodrigues.carteira.application.dtos.ItemCarteiraDto;
import dev.thiagorodrigues.carteira.application.dtos.ItemCarteiraProjection;
import dev.thiagorodrigues.carteira.infra.repositories.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RelatorioService {

    private final TransacaoRepository transacaoRepository;

    public List<ItemCarteiraProjection> relatorioCarteiraDeInvestimentos() {
        return transacaoRepository.relatorioCarteiraDeInvestimentos();
    }

    public List<ItemCarteiraDto> relatorioCarteiraDeInvestimentosDto() {
        return transacaoRepository.relatorioCarteiraDeInvestimentosDto();
    }

}

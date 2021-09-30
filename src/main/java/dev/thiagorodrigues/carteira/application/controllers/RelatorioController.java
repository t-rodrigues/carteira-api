package dev.thiagorodrigues.carteira.application.controllers;

import dev.thiagorodrigues.carteira.application.dtos.ItemCarteiraDto;
import dev.thiagorodrigues.carteira.application.dtos.ItemCarteiraProjection;
import dev.thiagorodrigues.carteira.domain.services.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping("/carteira")
    public List<ItemCarteiraProjection> relatorioCarteiraDeInvestimentos() {
        return relatorioService.relatorioCarteiraDeInvestimentos();
    }

    @GetMapping("/carteira/dto")
    public List<ItemCarteiraDto> relatorioCarteiraDeInvestimentosDto() {
        return relatorioService.relatorioCarteiraDeInvestimentosDto();
    }

}

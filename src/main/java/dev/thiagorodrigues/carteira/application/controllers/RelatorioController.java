package dev.thiagorodrigues.carteira.application.controllers;

import dev.thiagorodrigues.carteira.application.dtos.ItemCarteiraDto;
import dev.thiagorodrigues.carteira.application.dtos.ItemCarteiraProjection;
import dev.thiagorodrigues.carteira.domain.services.RelatorioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Relatórios")
@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;

    @ApiOperation("Relatório de transações com interface")
    @GetMapping("/carteira")
    public List<ItemCarteiraProjection> relatorioCarteiraDeInvestimentos() {
        return relatorioService.relatorioCarteiraDeInvestimentos();
    }

    @ApiOperation("Relatório de transações com classe")
    @GetMapping("/carteira/dto")
    public List<ItemCarteiraDto> relatorioCarteiraDeInvestimentosDto() {
        return relatorioService.relatorioCarteiraDeInvestimentosDto();
    }

}

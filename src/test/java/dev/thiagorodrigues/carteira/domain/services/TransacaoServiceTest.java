package dev.thiagorodrigues.carteira.domain.services;

import dev.thiagorodrigues.carteira.application.dtos.TransacaoDetalhadaResponseDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoFormDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoResponseDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoUpdateFormDto;
import dev.thiagorodrigues.carteira.application.exceptions.ResourceNotFoundException;
import dev.thiagorodrigues.carteira.domain.entities.Transacao;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import dev.thiagorodrigues.carteira.domain.exceptions.DomainException;
import dev.thiagorodrigues.carteira.domain.mocks.TransacaoFactory;
import dev.thiagorodrigues.carteira.domain.mocks.UsuarioFactory;
import dev.thiagorodrigues.carteira.infra.repositories.TransacaoRepository;
import dev.thiagorodrigues.carteira.infra.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TransacaoService transacaoService;

    private Transacao transacao;
    private TransacaoFormDto transacaoFormDto;
    private TransacaoUpdateFormDto transacaoUpdateFormDto;
    private TransacaoResponseDto transacaoResponseDto;
    private TransacaoResponseDto transacaoAtualizadaResponseDto;
    private TransacaoDetalhadaResponseDto transacaoDetalhadaResponseDto;

    private Usuario usuario;
    private Usuario usuarioLogado;

    @BeforeEach
    void setUp() {
        this.usuario = UsuarioFactory.criarUsuario();
        this.usuarioLogado = UsuarioFactory.criarUsuario();

        this.transacao = TransacaoFactory.criarTransacao();
        this.transacaoFormDto = TransacaoFactory.criarTransacaoFormDto();
        this.transacaoUpdateFormDto = TransacaoFactory.criarTransacaoUpdateFormDto();
        this.transacaoResponseDto = TransacaoFactory.criarTransacaoResponseDto();
        this.transacaoAtualizadaResponseDto = TransacaoFactory.criarTransacaoAtualizadaResponseDto();
        this.transacaoDetalhadaResponseDto = TransacaoFactory.criarTransacaoDetalhadaResponseDto();
    }

    @Test
    void cadastrarDeveriaLancarResourceNotFoundExceptionQuandoUsuarioIdInvalido() {
        doThrow(EntityNotFoundException.class).when(usuarioRepository).getById(anyLong());

        assertThrows(DomainException.class, () -> {
            transacaoService.add(transacaoFormDto, usuarioLogado);
        });
    }

    @Test
    void cadastrarDeveriaLancarDomainExceptionQuandoUsuarioIdInvalido() {
        doThrow(EntityNotFoundException.class).when(usuarioRepository).getById(anyLong());

        assertThrows(DomainException.class, () -> {
            transacaoService.add(transacaoFormDto, usuarioLogado);
        });
    }

    @Test
    void criarDeveriaRetornarUmaTransacaoQuandoDadosValidos() {
        when(usuarioRepository.getById(anyLong())).thenReturn(usuario);
        when(modelMapper.map(transacaoFormDto, Transacao.class)).thenReturn(transacao);
        when(modelMapper.map(transacao, TransacaoResponseDto.class)).thenReturn(transacaoResponseDto);

        var dto = transacaoService.add(transacaoFormDto, usuarioLogado);

        assertEquals(transacaoFormDto.getTicker(), dto.getTicker());
        assertEquals(transacaoFormDto.getPreco(), dto.getPreco());
        assertEquals(transacaoFormDto.getQuantidade(), dto.getQuantidade());
        assertEquals(transacaoFormDto.getTipo(), dto.getTipo());
        verify(usuarioRepository, times(1)).getById(anyLong());
        verify(transacaoRepository, times(1)).save(any());
    }

    @Test
    void mostrarDeveLancarResouceNotFoundQuandoIdTransacaoInvalido() {
        assertThrows(ResourceNotFoundException.class, () -> transacaoService.mostrar(1l, null));
    }

    @Test
    void mostrarDeveRetornarTransacaoQuandoIdValido() {
        when(transacaoRepository.findById(anyLong())).thenReturn(Optional.of(transacao));
        when(modelMapper.map(transacao, TransacaoDetalhadaResponseDto.class)).thenReturn(transacaoDetalhadaResponseDto);

        var transacaoResponseDto = transacaoService.mostrar(transacao.getId(), usuarioLogado);

        assertEquals(transacao.getId(), transacaoResponseDto.getId());
        assertEquals(transacao.getTicker(), transacaoResponseDto.getTicker());
        assertEquals(transacao.getQuantidade(), transacaoResponseDto.getQuantidade());
        assertEquals(transacao.getPreco(), transacaoResponseDto.getPreco());
        verify(transacaoRepository, times(1)).findById(anyLong());
    }

    @Test
    void atualizarDeveLancarResourceNotFoundQuandoTransacaoIdInvalido() {
        when(transacaoRepository.getById(anyLong())).thenThrow(EntityNotFoundException.class);

        assertThrows(ResourceNotFoundException.class,
                () -> transacaoService.atualizar(transacaoUpdateFormDto, usuarioLogado));
    }

    @Test
    void atualizarDeveRetornarTransacaoAtualizada() {
        when(transacaoRepository.getById(transacaoUpdateFormDto.getId())).thenReturn(transacao);
        when(modelMapper.map(transacao, TransacaoResponseDto.class)).thenReturn(transacaoAtualizadaResponseDto);

        var transacaoResponseDto = transacaoService.atualizar(transacaoUpdateFormDto, usuarioLogado);

        assertEquals(transacaoUpdateFormDto.getTicker(), transacaoResponseDto.getTicker());
        assertEquals(transacaoUpdateFormDto.getTipo(), transacaoResponseDto.getTipo());
        verify(transacaoRepository, times(1)).save(any());
    }

    @Test
    void removerDeveriaLancarResourceNotFoundQuandoIdInvalido() {
        long invalidId = 100L;
        doThrow(EntityNotFoundException.class).when(transacaoRepository).getById(invalidId);

        assertThrows(ResourceNotFoundException.class, () -> transacaoService.remover(invalidId, usuarioLogado));
    }

    @Test
    void removerNaoDeveTerRetornoComIdValido() {
        var validId = 1l;

        when(transacaoRepository.getById(validId)).thenReturn(transacao);

        assertDoesNotThrow(() -> transacaoService.remover(validId, usuarioLogado));
        verify(transacaoRepository, times(1)).deleteById(validId);
    }

}

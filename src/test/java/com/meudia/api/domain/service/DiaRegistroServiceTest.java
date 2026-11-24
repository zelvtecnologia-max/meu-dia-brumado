package com.meudia.api.domain.service;

import com.meudia.api.domain.dto.DiaRegistroDTO;
import com.meudia.api.domain.model.DiaRegistro;
import com.meudia.api.domain.repository.DiaRegistroRepository;
import com.meudia.api.exception.RecursoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DiaRegistroService Tests")
class DiaRegistroServiceTest {

    @Mock
    private DiaRegistroRepository repository;

    @InjectMocks
    private DiaRegistroService service;

    private DiaRegistroDTO dtoValido;
    private DiaRegistro entity;
    private final LocalDateTime agora = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        dtoValido = DiaRegistroDTO.builder()
                .titulo("Teste")
                .descricao("Descrição de teste")
                .dataRegistro(agora)
                .status(DiaRegistroDTO.StatusRegistro.PLANEJADO)
                .build();

        entity = DiaRegistro.builder()
                .id(1L)
                .titulo("Teste")
                .descricao("Descrição de teste")
                .dataRegistro(agora)
                .status(DiaRegistro.StatusRegistro.PLANEJADO)
                .createdAt(agora)
                .updatedAt(agora)
                .build();
    }

    @Nested
    @DisplayName("Criar registro")
    class Criar {

        @Test
        @DisplayName("Deve criar registro com sucesso")
        void deveCriarRegistroComSucesso() {
            when(repository.save(any(DiaRegistro.class))).thenReturn(entity);

            DiaRegistroDTO resultado = service.criar(dtoValido);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getTitulo()).isEqualTo("Teste");
            assertThat(resultado.getStatus()).isEqualTo(DiaRegistroDTO.StatusRegistro.PLANEJADO);
            assertThat(resultado.getCreatedAt()).isNotNull();
            assertThat(resultado.getUpdatedAt()).isNotNull();
            verify(repository).save(any(DiaRegistro.class));
        }
    }

    @Nested
    @DisplayName("Buscar por ID")
    class BuscarPorId {

        @Test
        @DisplayName("Deve retornar registro quando encontrado")
        void deveRetornarRegistroQuandoEncontrado() {
            when(repository.findById(1L)).thenReturn(Optional.of(entity));

            DiaRegistroDTO resultado = service.buscarPorId(1L);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getId()).isEqualTo(1L);
            assertThat(resultado.getTitulo()).isEqualTo("Teste");
        }

        @Test
        @DisplayName("Deve lançar exceção quando não encontrado")
        void deveLancarExcecaoQuandoNaoEncontrado() {
            when(repository.findById(999L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.buscarPorId(999L))
                    .isInstanceOf(RecursoNaoEncontradoException.class)
                    .hasMessageContaining("Registro não encontrado com ID: 999");
        }
    }

    @Nested
    @DisplayName("Listar todos")
    class ListarTodos {

        @Test
        @DisplayName("Deve retornar página de registros")
        void deveRetornarPaginaDeRegistros() {
            Pageable pageable = PageRequest.of(0, 20);
            Page<DiaRegistro> page = new PageImpl<>(List.of(entity), pageable, 1);
            when(repository.findAll(pageable)).thenReturn(page);

            Page<DiaRegistroDTO> resultado = service.listar(pageable);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getContent()).hasSize(1);
            assertThat(resultado.getTotalElements()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("Listar por status")
    class ListarPorStatus {

        @Test
        @DisplayName("Deve retornar registros filtrados por status")
        void deveRetornarRegistrosFiltradosPorStatus() {
            Pageable pageable = PageRequest.of(0, 20);
            Page<DiaRegistro> page = new PageImpl<>(List.of(entity), pageable, 1);
            when(repository.findByStatus(DiaRegistro.StatusRegistro.PLANEJADO, pageable)).thenReturn(page);

            Page<DiaRegistroDTO> resultado = service.listarPorStatus(DiaRegistroDTO.StatusRegistro.PLANEJADO, pageable);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getContent()).hasSize(1);
        }
    }

    @Nested
    @DisplayName("Atualizar registro")
    class Atualizar {

        @Test
        @DisplayName("Deve atualizar registro existente")
        void deveAtualizarRegistroExistente() {
            when(repository.findById(1L)).thenReturn(Optional.of(entity));
            when(repository.save(any(DiaRegistro.class))).thenReturn(entity);

            DiaRegistroDTO dtoAtualizado = DiaRegistroDTO.builder()
                    .titulo("Atualizado")
                    .descricao("Nova descrição")
                    .dataRegistro(agora.plusDays(1))
                    .status(DiaRegistroDTO.StatusRegistro.CONCLUIDO)
                    .build();

            DiaRegistroDTO resultado = service.atualizar(1L, dtoAtualizado);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getTitulo()).isEqualTo("Atualizado");
            assertThat(resultado.getStatus()).isEqualTo(DiaRegistroDTO.StatusRegistro.CONCLUIDO);
            verify(repository).save(any(DiaRegistro.class));
        }

        @Test
        @DisplayName("Deve lançar exceção ao atualizar registro inexistente")
        void deveLancarExcecaoAoAtualizarRegistroInexistente() {
            when(repository.findById(999L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.atualizar(999L, dtoValido))
                    .isInstanceOf(RecursoNaoEncontradoException.class);
        }
    }

    @Nested
    @DisplayName("Remover registro")
    class Remover {

        @Test
        @DisplayName("Deve remover registro existente")
        void deveRemoverRegistroExistente() {
            when(repository.existsById(1L)).thenReturn(true);

            service.remover(1L);

            verify(repository).deleteById(1L);
        }

        @Test
        @DisplayName("Deve lançar exceção ao remover registro inexistente")
        void deveLancarExcecaoAoRemoverRegistroInexistente() {
            when(repository.existsById(999L)).thenReturn(false);

            assertThatThrownBy(() -> service.remover(999L))
                    .isInstanceOf(RecursoNaoEncontradoException.class);
        }
    }
}
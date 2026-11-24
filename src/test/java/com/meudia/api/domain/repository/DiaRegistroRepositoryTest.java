package com.meudia.api.domain.repository;

import com.meudia.api.domain.model.DiaRegistro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("DiaRegistroRepository Tests")
class DiaRegistroRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DiaRegistroRepository repository;

    private final LocalDateTime agora = LocalDateTime.now();

    @Test
    @DisplayName("Deve salvar e buscar registro por ID")
    void deveSalvarEBucarPorId() {
        DiaRegistro entity = DiaRegistro.builder()
                .titulo("Teste")
                .descricao("Descrição")
                .dataRegistro(agora)
                .status(DiaRegistro.StatusRegistro.PLANEJADO)
                .build();

        DiaRegistro salvo = entityManager.persistAndFlush(entity);

        assertThat(salvo.getId()).isNotNull();
        assertThat(repository.findById(salvo.getId())).isPresent();
        assertThat(repository.findById(salvo.getId()).get().getTitulo()).isEqualTo("Teste");
    }

    @Test
    @DisplayName("Deve buscar por status")
    void deveBuscarPorStatus() {
        DiaRegistro planejado = DiaRegistro.builder()
                .titulo("Planejado")
                .dataRegistro(agora)
                .status(DiaRegistro.StatusRegistro.PLANEJADO)
                .build();

        DiaRegistro concluido = DiaRegistro.builder()
                .titulo("Concluído")
                .dataRegistro(agora)
                .status(DiaRegistro.StatusRegistro.CONCLUIDO)
                .build();

        entityManager.persistAndFlush(planejado);
        entityManager.persistAndFlush(concluido);

        Pageable pageable = PageRequest.of(0, 10);
        Page<DiaRegistro> resultado = repository.findByStatus(DiaRegistro.StatusRegistro.PLANEJADO, pageable);

        assertThat(resultado.getContent()).hasSize(1);
        assertThat(resultado.getContent().get(0).getStatus()).isEqualTo(DiaRegistro.StatusRegistro.PLANEJADO);
    }

    @Test
    @DisplayName("Deve buscar por data exata")
    void deveBuscarPorDataExata() {
        LocalDateTime dataEspecifica = LocalDateTime.of(2026, 11, 15, 10, 0);

        DiaRegistro entity = DiaRegistro.builder()
                .titulo("Na data")
                .dataRegistro(dataEspecifica)
                .status(DiaRegistro.StatusRegistro.PLANEJADO)
                .build();

        entityManager.persistAndFlush(entity);

        List<DiaRegistro> resultado = repository.findByDataRegistro(dataEspecifica);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getDataRegistro()).isEqualTo(dataEspecifica);
    }

    @Test
    @DisplayName("Deve buscar por período")
    void deveBuscarPorPeriodo() {
        LocalDateTime inicio = LocalDateTime.of(2026, 11, 1, 0, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 11, 30, 23, 59);

        DiaRegistro dentro = DiaRegistro.builder()
                .titulo("Dentro do período")
                .dataRegistro(LocalDateTime.of(2026, 11, 15, 10, 0))
                .status(DiaRegistro.StatusRegistro.PLANEJADO)
                .build();

        DiaRegistro fora = DiaRegistro.builder()
                .titulo("Fora do período")
                .dataRegistro(LocalDateTime.of(2026, 12, 15, 10, 0))
                .status(DiaRegistro.StatusRegistro.PLANEJADO)
                .build();

        entityManager.persistAndFlush(dentro);
        entityManager.persistAndFlush(fora);

        Pageable pageable = PageRequest.of(0, 10);
        Page<DiaRegistro> resultado = repository.findByDataRegistroBetween(inicio, fim, pageable);

        assertThat(resultado.getContent()).hasSize(1);
        assertThat(resultado.getContent().get(0).getTitulo()).isEqualTo("Dentro do período");
    }

    @Test
    @DisplayName("Deve verificar existência por ID")
    void deveVerificarExistenciaPorId() {
        DiaRegistro entity = DiaRegistro.builder()
                .titulo("Teste")
                .dataRegistro(agora)
                .status(DiaRegistro.StatusRegistro.PLANEJADO)
                .build();

        DiaRegistro salvo = entityManager.persistAndFlush(entity);

        assertThat(repository.existsById(salvo.getId())).isTrue();
        assertThat(repository.existsById(999L)).isFalse();
    }
}
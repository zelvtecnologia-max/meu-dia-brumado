package com.meudia.api.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meudia.api.MeuDiaApiApplication;
import com.meudia.api.domain.dto.DiaRegistroDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MeuDiaApiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("DiaRegistroController Integration Tests")
class DiaRegistroControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/dia-registros - Deve criar novo registro")
    void deveCriarNovoRegistro() throws Exception {
        DiaRegistroDTO dto = DiaRegistroDTO.builder()
                .titulo("Reunião de alinhamento")
                .descricao("Alinhar sprint com o time")
                .dataRegistro(LocalDateTime.now().plusDays(1))
                .status(DiaRegistroDTO.StatusRegistro.PLANEJADO)
                .build();

        mockMvc.perform(post("/api/dia-registros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.titulo").value("Reunião de alinhamento"))
                .andExpect(jsonPath("$.status").value("PLANEJADO"))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    @DisplayName("POST /api/dia-registros - Deve retornar 400 para título vazio")
    void deveRetornar400ParaTituloVazio() throws Exception {
        String jsonInvalido = """
                {
                    "titulo": "",
                    "descricao": "Teste",
                    "dataRegistro": "2026-11-10T09:00:00",
                    "status": "PLANEJADO"
                }
                """;

        mockMvc.perform(post("/api/dia-registros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.titulo").exists());
    }

    @Test
    @DisplayName("GET /api/dia-registros/{id} - Deve buscar registro por ID")
    void deveBuscarRegistroPorId() throws Exception {
        // Primeiro cria um registro
        DiaRegistroDTO dto = DiaRegistroDTO.builder()
                .titulo("Teste Busca")
                .descricao("Descrição")
                .dataRegistro(LocalDateTime.now())
                .status(DiaRegistroDTO.StatusRegistro.PLANEJADO)
                .build();

        MvcResult createResult = mockMvc.perform(post("/api/dia-registros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = createResult.getResponse().getContentAsString();
        Long id = objectMapper.readTree(responseBody).get("id").asLong();

        // Agora busca por ID
        mockMvc.perform(get("/api/dia-registros/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.titulo").value("Teste Busca"));
    }

    @Test
    @DisplayName("GET /api/dia-registros/{id} - Deve retornar 404 para ID inexistente")
    void deveRetornar404ParaIdInexistente() throws Exception {
        mockMvc.perform(get("/api/dia-registros/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Recurso não encontrado"));
    }

    @Test
    @DisplayName("GET /api/dia-registros - Deve listar paginado")
    void deveListarPaginado() throws Exception {
        // Cria alguns registros
        for (int i = 1; i <= 5; i++) {
            DiaRegistroDTO dto = DiaRegistroDTO.builder()
                    .titulo("Registro " + i)
                    .descricao("Desc " + i)
                    .dataRegistro(LocalDateTime.now().plusDays(i))
                    .status(DiaRegistroDTO.StatusRegistro.PLANEJADO)
                    .build();
            mockMvc.perform(post("/api/dia-registros")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isCreated());
        }

        mockMvc.perform(get("/api/dia-registros")
                        .param("page", "0")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(3))
                .andExpect(jsonPath("$.totalElements").value(5))
                .andExpect(jsonPath("$.totalPages").value(2));
    }

    @Test
    @DisplayName("GET /api/dia-registros/status/{status} - Deve filtrar por status")
    void deveFiltrarPorStatus() throws Exception {
        // Cria registros com diferentes status
        DiaRegistroDTO planejado = DiaRegistroDTO.builder()
                .titulo("Planejado")
                .dataRegistro(LocalDateTime.now())
                .status(DiaRegistroDTO.StatusRegistro.PLANEJADO)
                .build();

        DiaRegistroDTO concluido = DiaRegistroDTO.builder()
                .titulo("Concluído")
                .dataRegistro(LocalDateTime.now())
                .status(DiaRegistroDTO.StatusRegistro.CONCLUIDO)
                .build();

        mockMvc.perform(post("/api/dia-registros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(planejado)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/dia-registros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(concluido)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/dia-registros/status/PLANEJADO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].status", everyItem(equalTo("PLANEJADO"))));
    }

    @Test
    @DisplayName("GET /api/dia-registros/data/{data} - Deve filtrar por data")
    void deveFiltrarPorData() throws Exception {
        LocalDateTime dataEspecifica = LocalDateTime.of(2026, 11, 15, 10, 0);

        DiaRegistroDTO dto = DiaRegistroDTO.builder()
                .titulo("Registro na data")
                .dataRegistro(dataEspecifica)
                .status(DiaRegistroDTO.StatusRegistro.PLANEJADO)
                .build();

        mockMvc.perform(post("/api/dia-registros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/dia-registros/data/2026-11-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(greaterThanOrEqualTo(1)));
    }

    @Test
    @DisplayName("GET /api/dia-registros/periodo - Deve filtrar por período")
    void deveFiltrarPorPeriodo() throws Exception {
        mockMvc.perform(get("/api/dia-registros/periodo")
                        .param("inicio", "2026-11-01T00:00:00")
                        .param("fim", "2026-11-30T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("PUT /api/dia-registros/{id} - Deve atualizar registro")
    void deveAtualizarRegistro() throws Exception {
        // Cria registro
        DiaRegistroDTO dto = DiaRegistroDTO.builder()
                .titulo("Original")
                .dataRegistro(LocalDateTime.now())
                .status(DiaRegistroDTO.StatusRegistro.PLANEJADO)
                .build();

        MvcResult createResult = mockMvc.perform(post("/api/dia-registros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn();

        Long id = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

        // Atualiza
        DiaRegistroDTO dtoAtualizado = DiaRegistroDTO.builder()
                .titulo("Atualizado")
                .descricao("Nova descrição")
                .dataRegistro(LocalDateTime.now().plusDays(1))
                .status(DiaRegistroDTO.StatusRegistro.CONCLUIDO)
                .build();

        mockMvc.perform(put("/api/dia-registros/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Atualizado"))
                .andExpect(jsonPath("$.status").value("CONCLUIDO"));
    }

    @Test
    @DisplayName("DELETE /api/dia-registros/{id} - Deve remover registro")
    void deveRemoverRegistro() throws Exception {
        DiaRegistroDTO dto = DiaRegistroDTO.builder()
                .titulo("Para remover")
                .dataRegistro(LocalDateTime.now())
                .status(DiaRegistroDTO.StatusRegistro.PLANEJADO)
                .build();

        MvcResult createResult = mockMvc.perform(post("/api/dia-registros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn();

        Long id = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(delete("/api/dia-registros/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/dia-registros/{id}", id))
                .andExpect(status().isNotFound());
    }
}
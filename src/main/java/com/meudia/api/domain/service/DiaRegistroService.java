package com.meudia.api.domain.service;

import com.meudia.api.domain.dto.DiaRegistroDTO;
import com.meudia.api.domain.model.DiaRegistro;
import com.meudia.api.domain.repository.DiaRegistroRepository;
import com.meudia.api.exception.RecursoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaRegistroService {

    private final DiaRegistroRepository repository;

    public DiaRegistroDTO criar(DiaRegistroDTO dto) {
        DiaRegistro entity = toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        DiaRegistro salvo = repository.save(entity);
        return toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public DiaRegistroDTO buscarPorId(Long id) {
        DiaRegistro entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Registro não encontrado com ID: " + id));
        return toDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<DiaRegistroDTO> listar(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<DiaRegistroDTO> listarPorStatus(DiaRegistroDTO.StatusRegistro status, Pageable pageable) {
        DiaRegistro.StatusRegistro modelStatus = DiaRegistro.StatusRegistro.valueOf(status.name());
        return repository.findByStatus(modelStatus, pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public List<DiaRegistroDTO> listarPorData(LocalDateTime data) {
        return repository.findByDataRegistro(data).stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<DiaRegistroDTO> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim, Pageable pageable) {
        return repository.findByDataRegistroBetween(inicio, fim, pageable).map(this::toDTO);
    }

    public DiaRegistroDTO atualizar(Long id, DiaRegistroDTO dto) {
        DiaRegistro existente = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Registro não encontrado com ID: " + id));

        existente.setTitulo(dto.getTitulo());
        existente.setDescricao(dto.getDescricao());
        existente.setDataRegistro(dto.getDataRegistro());
        existente.setStatus(DiaRegistro.StatusRegistro.valueOf(dto.getStatus().name()));
        existente.setUpdatedAt(LocalDateTime.now());

        DiaRegistro atualizado = repository.save(existente);
        return toDTO(atualizado);
    }

    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Registro não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }

    private DiaRegistro toEntity(DiaRegistroDTO dto) {
        DiaRegistro entity = new DiaRegistro();
        entity.setTitulo(dto.getTitulo());
        entity.setDescricao(dto.getDescricao());
        entity.setDataRegistro(dto.getDataRegistro());
        entity.setStatus(DiaRegistro.StatusRegistro.valueOf(dto.getStatus().name()));
        return entity;
    }

    private DiaRegistroDTO toDTO(DiaRegistro entity) {
        return DiaRegistroDTO.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .descricao(entity.getDescricao())
                .dataRegistro(entity.getDataRegistro())
                .status(DiaRegistroDTO.StatusRegistro.valueOf(entity.getStatus().name()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
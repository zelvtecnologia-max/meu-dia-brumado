package com.meudia.api.domain.controller;

import com.meudia.api.domain.dto.DiaRegistroDTO;
import com.meudia.api.domain.service.DiaRegistroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/dia-registros")
@RequiredArgsConstructor
public class DiaRegistroController {

    private final DiaRegistroService service;

    @PostMapping
    public ResponseEntity<DiaRegistroDTO> criar(@Valid @RequestBody DiaRegistroDTO dto) {
        DiaRegistroDTO criado = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaRegistroDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<DiaRegistroDTO>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "dataRegistro,asc") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort[1].toUpperCase()), sort[0]));
        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<DiaRegistroDTO>> listarPorStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "dataRegistro,asc") String[] sort
    ) {
        DiaRegistroDTO.StatusRegistro statusEnum = DiaRegistroDTO.StatusRegistro.valueOf(status.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort[1].toUpperCase()), sort[0]));
        return ResponseEntity.ok(service.listarPorStatus(statusEnum, pageable));
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<List<DiaRegistroDTO>> listarPorData(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime data
    ) {
        return ResponseEntity.ok(service.listarPorData(data));
    }

    @GetMapping("/periodo")
    public ResponseEntity<Page<DiaRegistroDTO>> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "dataRegistro,asc") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort[1].toUpperCase()), sort[0]));
        return ResponseEntity.ok(service.listarPorPeriodo(inicio, fim, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiaRegistroDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody DiaRegistroDTO dto
    ) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
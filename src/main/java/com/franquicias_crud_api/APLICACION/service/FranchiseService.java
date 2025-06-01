package com.franquicias_crud_api.APLICACION.service;

import com.franquicias_crud_api.APLICACION.dto.FranchiseDTO;
import com.franquicias_crud_api.APLICACION.model.Franchise;
import com.franquicias_crud_api.APLICACION.repository.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FranchiseService {
    private final FranchiseRepository franchiseRepository;

    @Autowired
    public FranchiseService(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public List<FranchiseDTO> getFranchises() {
        return franchiseRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<FranchiseDTO> getFranchiseById(Integer id) {
        return franchiseRepository.findById(id)
                .map(this::convertToDTO);
    }

    public ResponseEntity<Object> createFranchise(Franchise franchise) {
        Map<String, Object> msg = new HashMap<>();

        if (franchise.getId() != null) {
            msg.put("error", true);
            msg.put("message", "La franquicia nueva no debe tener ID");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        Optional<Franchise> existing = franchiseRepository.findByName(franchise.getName());
        if (existing.isPresent()) {
            msg.put("error", true);
            msg.put("message", "Ya existe una franquicia con este nombre");
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        }

        Franchise saved = franchiseRepository.save(franchise);
        msg.put("data", convertToDTO(saved));
        msg.put("success", true);
        msg.put("message", "Franquicia creada exitosamente");
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateFranchise(Franchise franchise) {
        Map<String, Object> msg = new HashMap<>();

        if (franchise.getId() == null) {
            msg.put("error", true);
            msg.put("message", "El ID de la franquicia es obligatorio para actualizar");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        Optional<Franchise> existing = franchiseRepository.findById(franchise.getId());
        if (existing.isEmpty()) {
            msg.put("error", true);
            msg.put("message", "No se encontró la franquicia a actualizar");
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }

        Optional<Franchise> other = franchiseRepository.findByName(franchise.getName());
        if (other.isPresent() && !other.get().getId().equals(franchise.getId())) {
            msg.put("error", true);
            msg.put("message", "Ya existe otra franquicia con este nombre");
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        }

        Franchise updated = franchiseRepository.save(franchise);
        msg.put("data", convertToDTO(updated));
        msg.put("success", true);
        msg.put("message", "Franquicia actualizada exitosamente");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteFranchise(Integer id) {
        Map<String, Object> msg = new HashMap<>();

        Optional<Franchise> existing = franchiseRepository.findById(id);
        if (existing.isEmpty()) {
            msg.put("error", true);
            msg.put("message", "No se encontró la franquicia a eliminar");
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }

        franchiseRepository.deleteById(id);
        msg.put("success", true);
        msg.put("message", "Franquicia eliminada exitosamente");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateFranchiseName(Integer franchiseId, String newName) {
        Map<String, Object> msg = new HashMap<>();

        Optional<Franchise> optionalFranchise = franchiseRepository.findById(franchiseId);
        if (optionalFranchise.isEmpty()) {
            msg.put("error", true);
            msg.put("message", "Franquicia no encontrada");
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }

        Franchise franchise = optionalFranchise.get();
        franchise.setName(newName);
        franchiseRepository.save(franchise);

        msg.put("success", true);
        msg.put("message", "Nombre de la franquicia actualizado correctamente");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    public FranchiseDTO convertToDTO(Franchise franchise) {
        return new FranchiseDTO(franchise.getId(), franchise.getName());
    }

}

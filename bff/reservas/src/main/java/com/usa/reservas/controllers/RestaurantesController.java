package com.usa.reservas.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usa.reservas.DAO.MesaDAO;
import com.usa.reservas.DAO.RestauranteDAO;
import com.usa.reservas.model.Mesa;
import com.usa.reservas.model.Restaurante;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1")
public class RestaurantesController {
    
    
    @GetMapping("/consult/restaurantes")
    public List<Restaurante> getRestaurantes() {
        RestauranteDAO restauranteDAO = new RestauranteDAO();
        List<Restaurante> restaurantes = restauranteDAO.findAll();
        return restaurantes;
    }


    
    @GetMapping("/consult/restaurante/mesas")
    public Map<String, Object> getMesasRestaurantes(@RequestParam Integer idRestaurante) {
        MesaDAO mesaDAO = new MesaDAO();
        List<Mesa> mesas = mesaDAO.findByRestaurante(idRestaurante);

        List<Integer> mesasDisponibles = mesas.stream()
                                                 .map(mesa -> mesa.getId())
                                                 .collect(Collectors.toList());
        
        Map<String, Object> response = new HashMap<>();
        response.put("idRestaurante", idRestaurante);
        response.put("mesasDisponibles", mesasDisponibles);
        
        return response;
    }
    
}

package com.usa.reservas.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usa.reservas.DAO.MesaDAO;
import com.usa.reservas.model.Mesa;


import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1")
public class MesasController {
    
    
    @GetMapping("/consult/detail/mesa")
    public Mesa getMesa(@RequestParam Integer idMesa) {
        MesaDAO mesaDao = new MesaDAO();
        Mesa mesa = mesaDao.findById(idMesa);
        return mesa;
    }

    
}

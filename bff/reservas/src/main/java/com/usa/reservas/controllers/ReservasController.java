package com.usa.reservas.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usa.reservas.DAO.ReservaDAO;
import com.usa.reservas.DAO.UsuarioDAO;
import com.usa.reservas.model.Reserva;
import com.usa.reservas.model.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1")
public class ReservasController {
    
    @GetMapping("/consult/usuario/reservas")
    public List<Reserva> getMethodName(@RequestParam Integer cedulaUsuario) {
        ReservaDAO reservaDAO = new ReservaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.findById(cedulaUsuario);
        if (usuario == null) {
            return null;
        }
        System.out.println(usuario.getRol());
        if ("Admin".equals(usuario.getRol()) || "Consierge".equals(usuario.getRol())) {
            return reservaDAO.findAll();
        }
        return reservaDAO.findByUsuario(cedulaUsuario);
    }


    @PostMapping("/create/reserva")
    public Map<String, String> postMethodName(@RequestBody Reserva entity) {
        
        System.out.println("Reserva: " + entity);

        ReservaDAO reservaDAO = new ReservaDAO();
         
        if(reservaDAO.insert(entity)){
            Map<String, String> response = new HashMap<>();
            response.put("idReserva", "Creada");
            return response;
            

        }

    }
    
    
}

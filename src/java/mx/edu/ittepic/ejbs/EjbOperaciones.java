/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.ejbs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import mx.edu.ittepic.entidades.Persona;

/**
 *
 * @author israelarjonavizcaino
 */
@Stateless
public class EjbOperaciones {
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    EntityManager entity;
    
    public String consultaPersonas() {
        Query q;
        List<Persona> personas;
        GsonBuilder builder;
        Gson gson;
        String result;
        
        q = entity.createNamedQuery("Persona.findAll");
        personas = q.getResultList();
        builder = new GsonBuilder();
        gson = builder.create();
        result = gson.toJson(personas);
        
        return result;
    }
    
    public String actualizarPersona(String id, String edad, String name, String email) {
        Query q;
        String msj;
        Persona p;
        try {
            p = entity.find(Persona.class, Integer.parseInt(id));
            p.setEdad(Integer.parseInt(edad));
            p.setName(name);
            p.setEmail(email);
            
            entity.merge(p);
            
            msj = "{\"code\":200, \"msj\":\"La operación se realizó correctamente\"}";
            
        } catch(NumberFormatException e) {
            msj = "{\"code\":400, \"msj\":\"Error en los tipos de parámetros\"}";
        }
        return msj;
    }
    
    public String crearPersona(String edad, String name, String email) {
        Query q;
        String msj;
        Persona p = new Persona();
        try {
            p.setEdad(Integer.parseInt(edad));
            p.setName(name);
            p.setEmail(email);
            
            entity.persist(p);
            
            msj = "{\"code\":200, \"msj\":\"La operación se realizó correctamente\"}";
            
        } catch(NumberFormatException e) {
            msj = "{\"code\":400, \"msj\":\"Error en los tipos de parámetros\"}";
        }
        return msj;
    }
    
    public String eliminarPersona(String id) {
        Query q;
        String msj;
        Persona p;
        try {
            p = entity.find(Persona.class, Integer.parseInt(id));
            
            entity.remove(p);
            
            msj = "{\"code\":200, \"msj\":\"La operación se realizó correctamente\"}";
            
        } catch(NumberFormatException e) {
            msj = "{\"code\":400, \"msj\":\"Error en los tipos de parámetros\"}";
        }
        return msj;
    }
}

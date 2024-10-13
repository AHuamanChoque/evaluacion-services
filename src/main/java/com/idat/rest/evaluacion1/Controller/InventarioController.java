package com.idat.rest.evaluacion1.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idat.rest.evaluacion1.model.Inventario;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("api/inventario")
public class InventarioController {

    private final List<Inventario> inventarios = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong();

    public InventarioController(){
        initData();
    }

    private void initData(){
        Inventario arroz = new Inventario(contador.incrementAndGet(), "Arroz", "Compra por kilo", 4.50, 30);
        Inventario aceite = new Inventario(contador.incrementAndGet(), "Aceite", "Eficiente en el 80% de comida peruana", 8.30, 20);
        Inventario jabon = new Inventario(contador.incrementAndGet(), "Jabon", "Perfecto para el cuidado personal", 3.80, 25);
        Inventario res = new Inventario(contador.incrementAndGet(), "Res", "Rica en zinc, que ayuda a protegernos contra el daño oxidativo, a la cicatrización de la piel y para crear hemoglobina", 7.30, 12);
        Inventario leche = new Inventario(contador.incrementAndGet(), "Leche", "Mejor laceo apra los desayunos en familia", 3.50, 40);

        inventarios.add(arroz);
        inventarios.add(aceite);
        inventarios.add(jabon);
        inventarios.add(res);
        inventarios.add(leche);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Inventario> obtener(@PathVariable long id){
        
        Inventario inventario = inventarios.stream().filter(p -> p.getId().equals(id)).findAny().orElse(null);
        if (inventario != null) {
            return new ResponseEntity<>(inventario, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Inventario> registrar(@RequestBody Inventario inventario){
        Inventario inventarioNuevo = new Inventario(contador.incrementAndGet(), inventario.getNombre(), inventario.getDescripcion(), inventario.getPrecio(), inventario.getCantidad());
        inventarios.add(inventarioNuevo);
        return new ResponseEntity<>(inventarioNuevo, HttpStatus.CREATED);    
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Inventario> actualizar(@PathVariable long id, @RequestBody Inventario p){
        Inventario inventarioActualizado = null;
        for(Inventario inventario: inventarios) {
            if (inventario.getId() == id) {
                inventario.setNombre(p.getNombre());
                inventario.setDescripcion(p.getDescripcion());
                inventario.setPrecio(p.getPrecio());
                inventario.setCantidad(p.getCantidad());
                inventarioActualizado = inventario;
                break;
                
            }
        }
        return new ResponseEntity<>(inventarioActualizado, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    private ResponseEntity<Inventario> eliminar(@PathVariable Long id){
        Inventario inventario = inventarios.stream().filter(p -> p.getId().equals(id)).findAny().orElse(null);
        if (inventario != null) {
            inventarios.remove(inventario);
        }
        return new ResponseEntity<Inventario>(HttpStatus.NO_CONTENT);
    }


    

}

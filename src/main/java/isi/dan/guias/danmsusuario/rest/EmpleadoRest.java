package isi.dan.guias.danmsusuario.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import isi.dan.guias.danmsusuario.domain.Empleado;


@RestController
@RequestMapping("/api/empleado")
public class EmpleadoRest {

	private static final List<Empleado> listaEmpleados = new ArrayList<>();
    private static Integer ID_GEN = 1;
	@PostMapping
    public ResponseEntity<Empleado> crear(@RequestBody Empleado empleadoNuevo){
    	System.out.println(" crear empleado "+empleadoNuevo);
    	empleadoNuevo.setId(ID_GEN++);
        listaEmpleados.add(empleadoNuevo);
        return ResponseEntity.ok(empleadoNuevo);
    }
	
	
	@PutMapping(path = "/{id}")
    public ResponseEntity<Empleado> actualizar(@RequestBody Empleado empleadoNuevo,  @PathVariable Integer id){
        OptionalInt indexOpt =   IntStream.range(0, listaEmpleados.size())
        .filter(i -> listaEmpleados.get(i).getId().equals(id))
        .findFirst();

        if(indexOpt.isPresent()){
            listaEmpleados.set(indexOpt.getAsInt(), empleadoNuevo);
            return ResponseEntity.ok(empleadoNuevo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@DeleteMapping(path = "/{id}")
    public ResponseEntity<Empleado> borrar(@PathVariable Integer id){
        OptionalInt indexOpt =   IntStream.range(0, listaEmpleados.size())
        .filter(i -> listaEmpleados.get(i).getId().equals(id))
        .findFirst();

        if(indexOpt.isPresent()){
            listaEmpleados.remove(indexOpt.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	 @GetMapping(path = "/{id}")
	    public ResponseEntity<Empleado> empleadoPorId(@PathVariable Integer id){

	        Optional<Empleado> empleadoBuscado =  listaEmpleados
	                .stream()
	                .filter(unEmpleado -> unEmpleado.getId().equals(id))
	                .findFirst();
	        return ResponseEntity.of(empleadoBuscado);
	    }
	 
	  @GetMapping
	    public ResponseEntity<List<Empleado>> todos(@RequestParam(required = false) String nombre){
	    	if(nombre==null) return ResponseEntity.ok(listaEmpleados);
	    	List<Empleado> empleadosBuscados =  listaEmpleados
	                .stream()
	                .filter(unEmpleado -> unEmpleado.getUser().getUser().equals(nombre))
	                .collect(Collectors.toList());
	    	return(empleadosBuscados.isEmpty()? ResponseEntity.of(Optional.empty()) : ResponseEntity.ok(empleadosBuscados));  	
	    }
	  
	
}

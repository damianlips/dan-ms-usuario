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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import isi.dan.guias.danmsusuario.domain.*;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/cliente")
@Api(value = "ClienteRest", description = "Permite gestionar los clientes de la empresa")
public class ClienteRest {

	 private static final List<Cliente> listaClientes = new ArrayList<>();
	    private static Integer ID_GEN = 1;

	    @GetMapping(path = "/{id}")
	    @ApiOperation(value = "Busca un cliente por id")
	    public ResponseEntity<Cliente> clientePorId(@PathVariable Integer id){

	        Optional<Cliente> c =  listaClientes
	                .stream()
	                .filter(unCli -> unCli.getId().equals(id))
	                .findFirst();
	        return ResponseEntity.of(c);
	    }
	    
	    @GetMapping(path = "/Cuit/{cuit}")
	    @ApiOperation(value = "Busca un cliente por cuit")
	    public ResponseEntity<Cliente> clientePorCuit(@PathVariable String cuit){
	    	Optional<Cliente> c = listaClientes
	    			.stream()
	    			.filter(unCli -> unCli.getCuit().equals(cuit))
	    			.findFirst();
	    	return ResponseEntity.of(c);
	    }
	    

	    @GetMapping
	    public ResponseEntity<List<Cliente>> todos(@RequestParam(required = false) String razonSocial){
	    	if(razonSocial==null) return ResponseEntity.ok(listaClientes);
	    	List<Cliente> c =  listaClientes
	                .stream()
	                .filter(unCli -> unCli.getRazonSocial().equals(razonSocial))
	                .collect(Collectors.toList());
	    	return(c.isEmpty()? ResponseEntity.notFound().build() : ResponseEntity.ok(c) );
	    	
	    }
	    /*
	    @GetMapping
	    public ResponseEntity<List<Cliente>> clientePorRazonSocial(@RequestParam(required = false) String razonSocial){
	    	if(razonSocial==null) return this.todos();
	    	else {
	    		Optional<Cliente> c =  listaClientes
		                .stream()
		                .filter(unCli -> unCli.getRazonSocial().equals(razonSocial))
		                .findFirst();
	    		if(c.isEmpty()) return ResponseEntity.of(Optional.empty());
	    		else {
	    			List<Cliente> clienteEnLista = new ArrayList<Cliente>();
	    			clienteEnLista.add(c.get());
	    			return ResponseEntity.of(Optional.of(clienteEnLista));
	    		}
	    	}
	    }
	    
	    
	    @GetMapping
	    public ResponseEntity<List<Cliente>> clientePorRazonSocial(@RequestParam(required = false) String razonSocial){
	    	if(razonSocial==null) return this.todos();
	    	else {
	    		List<Cliente> c =  listaClientes
		                .stream()
		                .filter(unCli -> unCli.getRazonSocial().equals(razonSocial))
		                .collect(Collectors.toList());
	    		if(c.isEmpty()) return ResponseEntity.of(Optional.empty());
	    		else {
	    			return ResponseEntity.of(Optional.of(c));
	    		}
	    	}
	    }
	    */
	    

	    @PostMapping
	    public ResponseEntity<Cliente> crear(@RequestBody Cliente nuevo){
	    	System.out.println(" crear cliente "+nuevo);
	        nuevo.setId(ID_GEN++);
	        listaClientes.add(nuevo);
	        return ResponseEntity.ok(nuevo);
	    }

	    
	    /*
	    @ApiOperation(value = "Actualiza un cliente")
	    @ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Actualizado correctamente"),
	        @ApiResponse(code = 401, message = "No autorizado"),
	        @ApiResponse(code = 403, message = "Prohibido"),
	        @ApiResponse(code = 404, message = "El ID no existe")
	    })
	    */
	    @PutMapping(path = "/{id}")
	    @ApiOperation(value = "Actualiza un cliente")
	    @ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Actualizado correctamente"),
	        @ApiResponse(code = 401, message = "No autorizado"),
	        @ApiResponse(code = 403, message = "Prohibido"),
	        @ApiResponse(code = 404, message = "El ID no existe")
	    })
	    public ResponseEntity<Cliente> actualizar(@RequestBody Cliente nuevo,  @PathVariable Integer id){
	        OptionalInt indexOpt =   IntStream.range(0, listaClientes.size())
	        .filter(i -> listaClientes.get(i).getId().equals(id))
	        .findFirst();

	        if(indexOpt.isPresent()){
	            listaClientes.set(indexOpt.getAsInt(), nuevo);
	            return ResponseEntity.ok(nuevo);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @DeleteMapping(path = "/{id}")
	    public ResponseEntity<Cliente> borrar(@PathVariable Integer id){
	        OptionalInt indexOpt =   IntStream.range(0, listaClientes.size())
	        .filter(i -> listaClientes.get(i).getId().equals(id))
	        .findFirst();

	        if(indexOpt.isPresent()){
	            listaClientes.remove(indexOpt.getAsInt());
	            return ResponseEntity.ok().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
}

package isi.dan.guias.danmsusuario.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
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

import isi.dan.guias.danmsusuario.domain.Obra;


@RestController
@RequestMapping("/api/obra")
public class ObraRest {

	private static final List<Obra> listaObras = new ArrayList<>();
    private static Integer ID_GEN = 1;
    
    @PostMapping 
    public ResponseEntity<Obra> crear (@RequestBody Obra obraNueva){
    	obraNueva.setId(ID_GEN++);
    	listaObras.add(obraNueva);
    	return ResponseEntity.ok(obraNueva);
    }
    
    @PutMapping
    public ResponseEntity<Obra> actualizar(@RequestBody Obra obraNueva, @PathVariable Integer id){
    	OptionalInt indexOpt = IntStream.range(0, listaObras.size())
    			.filter(index -> listaObras.get(index).getId().equals(id))
    			.findFirst();
    	if(indexOpt.isPresent()) {
    		listaObras.set(indexOpt.getAsInt(), obraNueva);
    		return ResponseEntity.ok(obraNueva);
    	} else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Obra> borrar(@PathVariable Integer id){
    	OptionalInt indexOpt = IntStream.range(0, listaObras.size())
    			.filter(index -> listaObras.get(index).getId().equals(id))
    			.findFirst();
    	if(indexOpt.isPresent()) {
    		listaObras.remove(indexOpt.getAsInt());
    		return ResponseEntity.ok().build();
    	} else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping(path= "/{id}")
    public ResponseEntity<Obra> obraPorId(@PathVariable Integer id){
    	Optional<Obra> obraBuscada = listaObras.stream()
    			.filter(unaObra -> unaObra.getId().equals(id))
    			.findFirst();
    	return ResponseEntity.of(obraBuscada);
    }
    
    @GetMapping
    public ResponseEntity<List<Obra>> todos(@RequestParam(required = false) Integer idCliente, @RequestParam(required = false) Integer idTipoObra){
    	int cont = 0;
    	Predicate<Obra> pred = (obra -> true);
    	if(idCliente!=null) {
    		++cont;
    		pred = pred.and( obra -> obra.getCliente().getId().equals(idCliente) );
    	}
    	if(idTipoObra!=null) {
    		++cont;
    		pred = pred.and( obra -> obra.getTipo().getId().equals(idTipoObra));
    	}
    	
    	if(cont==0) return ResponseEntity.ok(listaObras);
    	
    	List<Obra> obrasBuscadas = listaObras.stream()
    			.filter(pred)
    			.collect(Collectors.toList());
    	
    	return (obrasBuscadas.isEmpty()? ResponseEntity.notFound().build() : ResponseEntity.ok(obrasBuscadas) );
    }
    
}

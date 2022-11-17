package com.springboot.webflux.app.controller;

import java.net.URI;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.webflux.app.models.document.Producto;
import com.springboot.webflux.app.models.service.ProductoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/productos")
public class ProductoController {
@Autowired
	private ProductoService service;

	@GetMapping
	public Mono<ResponseEntity<Flux<Producto>>>lista(){
		
		return Mono.just(
				ResponseEntity
				.ok().
				body(service.findAll())
	);
	
}
@GetMapping("/{id}")
public Mono<ResponseEntity<Producto>> ver(
	@PathVariable String id) {
	return service.findById(id).map(p-> ResponseEntity.ok()
	.body(p))
	.defaultIfEmpty(ResponseEntity.notFound().build());
	
}

@PostMapping
public Mono<ResponseEntity<Producto>> crear(@RequestBody Producto producto) {
	if(producto.getCreateAt()==null){
		producto.setCreateAt(new Date());
	}

	return service.save(producto).map(p->ResponseEntity
	.created(URI.create("/api/productos/".concat(p.getId())))
	.body(p) );

}

}


// public Mono<ResponseEntity<Flux<Producto>>>lista(){
		
// 	return Mono.just(
// 			ResponseEntity
// 			.ok().
// 			body(service.findAll())
// );





























package com.springboot.webflux.app.models.service;

import com.springboot.webflux.app.models.document.Categoria;
import com.springboot.webflux.app.models.document.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {
    
    
	public Flux<Producto>  findAll();

	public Mono<Producto> findById(String id);

	public Mono<Producto> save(Producto producto);
 
	public Mono<Void> delete(Producto producto);

	  public Flux<Producto>  findAllConNombreUpperCase();

	  public Flux<Producto>  findAllConNombreUpperCaseRepeat(); 

	 public Flux<Categoria>  findAllCategoria();

 public Mono<Categoria> findCategoriaById(String id);

	 public Mono<Categoria> saveCategoria(Categoria categoria);
}

package com.springboot.webflux.app.handler;

import java.net.URI;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static org.springframework.web.reactive.function.BodyInserters.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.springboot.webflux.app.models.document.Producto;
import com.springboot.webflux.app.models.service.ProductoService;
import com.springboot.webflux.app.models.service.Produto;

import reactor.core.publisher.Mono;

@Component
public class ProductoHandler {

    @Autowired
private ProductoService service;
    
    public Mono<ServerResponse> listar(ServerRequest serverRequest){
        return ServerResponse.ok()
        .body(service.findAll(),Produto.class);
    }

    public Mono<ServerResponse> ver(ServerRequest request){
             String id= request.pathVariable("id");

             return service.findById(id).flatMap(p-> 
             ServerResponse
             .ok().body(fromObject(p))
             .switchIfEmpty(ServerResponse.notFound().build()));
    }

    public Mono<ServerResponse> crear(ServerRequest  request){

        Mono<Producto> producto = request.bodyToMono(Producto.class);

         return producto.flatMap(p-> {

            if(p.getCreateAt() == null){
                p.setCreateAt(new Date());
            }
            return service.save(p);
         }).flatMap(p-> ServerResponse
         .created(URI.create("/api/v2/productos/".concat(p.getId())))
         .body(fromObject(p)));
    }
    public Mono<ServerResponse> editar(ServerRequest request){

        System.out.println("se ejecuto el put");
       
        Mono<Producto> producto = request.bodyToMono(Producto.class);
        
        String id= request.pathVariable("id");

        Mono<Producto> productoDb = service.findById(id);

        return productoDb.zipWith(producto,(db,req)->{

            db.setNombre(req.getNombre());
            db.setPrecio(req.getPrecio());
            db.setCategoria(req.getCategoria());
            return db;
        }
        ).flatMap(p-> ServerResponse.created(URI.create("/api/v2/productos/".concat(p.getId())))
        .body(service.save(p), Producto.class))
        .switchIfEmpty(ServerResponse.notFound().build());

    }
    public Mono<ServerResponse> eliminar(ServerRequest request){
        System.out.println("se ejecuto el delete");
        String id = request.pathVariable("id");
        Mono<Producto> productoDb = service.findById(id);

        return productoDb.flatMap(p-> service.delete(p).then(ServerResponse.noContent().build()))
        .switchIfEmpty(ServerResponse.notFound().build());
    }
}


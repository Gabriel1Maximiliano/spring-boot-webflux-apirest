package com.springboot.webflux.app.handler;

import java.net.http.HttpRequest.BodyPublishers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static org.springframework.web.reactive.function.BodyInserters.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

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

             Mono test = service.findById(id);
             System.out.println(test);
             return service.findById(id).flatMap(p-> 
             ServerResponse
             .ok().body(fromObject(p))
             .switchIfEmpty(ServerResponse.notFound().build()));
    }
}

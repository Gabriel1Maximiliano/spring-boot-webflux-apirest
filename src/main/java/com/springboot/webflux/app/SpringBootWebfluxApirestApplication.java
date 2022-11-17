package com.springboot.webflux.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.springboot.webflux.app.models.document.Categoria;
import com.springboot.webflux.app.models.document.Producto;
import com.springboot.webflux.app.models.service.ProductoService;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApirestApplication implements CommandLineRunner{
	 @Autowired
		private ProductoService service;
		@Autowired
		private ReactiveMongoTemplate mongoTemplate;

		private static final Logger log= (Logger) LoggerFactory.getLogger(SpringBootWebfluxApirestApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApirestApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		mongoTemplate.dropCollection("productos").subscribe();
		mongoTemplate.dropCollection("categorias").subscribe();
		Categoria electrónico = new Categoria("Electrónico");
		Categoria deporte = new Categoria("Deporte ");
		Categoria computacion = new Categoria("Computación");
		Categoria muebles = new Categoria("Muebles");
		Categoria varios = new Categoria("Varios");
		Flux.just(electrónico,deporte,computacion,muebles,varios)
		.flatMap(c-> service.saveCategoria(c))
		.doOnNext(c->{
			log.info("Categoría creada: "+ c.getNombre()+", id: "+c.getId());
		}).thenMany(
			Flux.just(
			new Producto("mesa", 23.33,muebles),
			new Producto("mancuerna",56.3,deporte),
			new Producto("soga",15.33,varios),
			new Producto("celular", 23.33,electrónico),
			new Producto("silla",56.3,muebles),
			new Producto("pesa",15.33,deporte)

		)
		.flatMap(producto->{
			producto.setCreateAt(new Date(0));
			return service.save(producto);
		})
	
	)
	.subscribe(producto-> log.info("insert: "+ producto.getId()+" "+ producto.getNombre()));
	
	}

}

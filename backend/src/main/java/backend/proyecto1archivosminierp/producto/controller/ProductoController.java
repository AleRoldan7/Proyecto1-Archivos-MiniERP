package backend.proyecto1archivosminierp.producto.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @PostConstruct
    public void init() {
        System.out.println("ProductoController cargado");
    }

    @GetMapping
    public String test(){
        return "test";
    }
}

package backend.proyecto1archivosminierp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class prueba {

    @GetMapping("api/test")
    public String test(){
        return "test";
    }
}

package mx.comer.controller;

import mx.comer.model.UsuariosModel;
import mx.comer.service.UsuariosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/middleware")
public class UsuariosController {

    @Autowired
    private UsuariosService servicio;

    @PostMapping("/usuarios/registrar")
    public ResponseEntity<Object> registrar(@RequestBody(required = false) UsuariosModel peticion) {

        return servicio.registrar(peticion);

    }

    @GetMapping("/usuarios/consultar/{identificador}")
    public ResponseEntity<Object> consultar_usuario(@PathVariable Long identificador) {

        return servicio.consultar_usuario(identificador);

    }

    @GetMapping("/usuarios")
    public ResponseEntity<Object> consultar() {

        return servicio.consultar_usuarios();

    }

    @DeleteMapping("/usuarios/eliminar/{identificador}")
    public ResponseEntity<Object> eliminar(@PathVariable Long identificador) {

        return servicio.eliminar(identificador);

    }

    @PutMapping("/usuarios/actualizar/{identificador}")
    public ResponseEntity<Object> eliminar(@PathVariable Long identificador, @RequestBody(required = false) UsuariosModel peticion) {

        return servicio.actualizar(identificador, peticion);

    }

}

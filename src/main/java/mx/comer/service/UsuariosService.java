package mx.comer.service;

import java.util.List;
import java.util.HashMap;
import java.util.Objects;
import java.util.LinkedHashMap;

import mx.comer.client.CopomexClient;
import mx.comer.model.UsuariosModel;
import mx.comer.entity.UsuariosEntity;
import org.springframework.http.HttpStatus;
import mx.comer.repository.UsuariosRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository repositorio;

    @Autowired
    private CopomexClient cliente;

    public ResponseEntity<Object> registrar(UsuariosModel peticion) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        if (Objects.isNull(peticion)) {

            resultado.put("codigo", "101");
            resultado.put("descripcion", "Solicitud vacía.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getNombre()) || peticion.getNombre().trim().isEmpty()) {

            resultado.put("codigo", "102");
            resultado.put("descripcion", "Ingresar nombre.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getApellido_paterno()) || peticion.getApellido_paterno().trim().isEmpty()) {

            resultado.put("codigo", "103");
            resultado.put("descripcion", "Ingresar apellido paterno.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getApellido_materno()) || peticion.getApellido_materno().trim().isEmpty()) {

            resultado.put("codigo", "104");
            resultado.put("descripcion", "Ingresar apellido materno.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getCorreo_electronico()) || peticion.getCorreo_electronico().trim().isEmpty()) {

            resultado.put("codigo", "105");
            resultado.put("descripcion", "Ingresar correo electrónico.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }

        List<String> direccion = cliente.direccion();

        try{

            Long identificador = repositorio.identificador();

            if (Objects.isNull(identificador)) {

                identificador = 1L;

            }else{

                identificador = identificador + 1;

            }

            if(direccion.get(0).equals("1")){

                repositorio.registrar_usuario(identificador,
                        peticion.getNombre().trim(),
                        peticion.getApellido_paterno().trim(),
                        peticion.getApellido_materno().trim(),
                        peticion.getCorreo_electronico().trim(),
                        " ",
                        " ",
                        " ",
                        " "
                );

            }else{

                repositorio.registrar_usuario(identificador,
                        peticion.getNombre().trim(),
                        peticion.getApellido_paterno().trim(),
                        peticion.getApellido_materno().trim(),
                        peticion.getCorreo_electronico().trim(),
                        direccion.get(0),
                        direccion.get(1),
                        direccion.get(2),
                        direccion.get(3)
                );

            }

            resultado.put("codigo", "100");
            resultado.put("descripcion", "Petición realizada con éxito.");

            respuesta.put("resultado", resultado);
            respuesta.put("identificador", identificador);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);

        }catch(Exception e){

            resultado.put("codigo", "-54");
            resultado.put("descripcion", "No fue posible registrar la información del usuario.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


    public ResponseEntity<Object> consultar_usuario(Long identificador){

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        try{

            UsuariosEntity usuario = repositorio.consultar_usuario(identificador, 1L);

            if(Objects.isNull(usuario)){

                resultado.put("codigo", "101");
                resultado.put("descripcion", "Usuario no válido.");

                respuesta.put("resultado", resultado);
                return new ResponseEntity<>(respuesta, HttpStatus.CONFLICT);

            }

            HashMap<String, String> informacion = new LinkedHashMap<>();
            informacion.put("identificador", usuario.getIdentificador() + "");
            informacion.put("nombre", usuario.getNombre());
            informacion.put("apellido_paterno", usuario.getApellido_paterno());
            informacion.put("apellido_materno", usuario.getApellido_materno());
            informacion.put("correo_electronico", usuario.getCorreo_electronico());
            informacion.put("ciudad", usuario.getCiudad());
            informacion.put("codigo_postal", usuario.getCodigo_postal());
            informacion.put("latitud", usuario.getLatitud());
            informacion.put("longitud", usuario.getLongitud());
            informacion.put("estatus", usuario.getEstatus() + "");

            resultado.put("codigo", "100");
            resultado.put("descripcion", "Petición realizada con éxito.");

            respuesta.put("resultado", resultado);
            respuesta.put("usuario", informacion);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);


        }catch(Exception e){

            resultado.put("codigo", "-55");
            resultado.put("descripcion", "No fue posible consultar la información del usuario.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


    public ResponseEntity<Object> consultar_usuarios(){

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        try{

            List<UsuariosEntity> usuarios = repositorio.consultar_usuarios(1L);

            if(usuarios.isEmpty()){

                resultado.put("codigo", "101");
                resultado.put("descripcion", "Sin usuarios registrados.");

                respuesta.put("resultado", resultado);
                return new ResponseEntity<>(respuesta, HttpStatus.CONFLICT);

            }

            resultado.put("codigo", "100");
            resultado.put("descripcion", "Petición realizada con éxito.");

            respuesta.put("resultado", resultado);
            respuesta.put("usuarios", usuarios);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);


        }catch(Exception e){

            resultado.put("codigo", "-56");
            resultado.put("descripcion", "No fue posible consultar la información de los usuarios.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


    public ResponseEntity<Object> eliminar(Long identificador){

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        try{

            Integer eliminar = repositorio.eliminar(identificador, 1L);

            if(eliminar != 1){

                resultado.put("codigo", "101");
                resultado.put("descripcion", "Eliminación fallida.");

                respuesta.put("resultado", resultado);
                return new ResponseEntity<>(respuesta, HttpStatus.CONFLICT);

            }

            resultado.put("codigo", "100");
            resultado.put("descripcion", "Petición realizada con éxito.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);


        }catch(Exception e){

            resultado.put("codigo", "-57");
            resultado.put("descripcion", "No fue posible eliminar la información del usuario.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


    public ResponseEntity<Object> actualizar(Long identificador, UsuariosModel peticion) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        if (Objects.isNull(peticion)) {

            resultado.put("codigo", "101");
            resultado.put("descripcion", "Solicitud vacía.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getNombre()) || peticion.getNombre().trim().isEmpty()) {

            resultado.put("codigo", "102");
            resultado.put("descripcion", "Ingresar nombre.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getApellido_paterno()) || peticion.getApellido_paterno().trim().isEmpty()) {

            resultado.put("codigo", "103");
            resultado.put("descripcion", "Ingresar apellido paterno.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getApellido_materno()) || peticion.getApellido_materno().trim().isEmpty()) {

            resultado.put("codigo", "104");
            resultado.put("descripcion", "Ingresar apellido materno.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getCorreo_electronico()) || peticion.getCorreo_electronico().trim().isEmpty()) {

            resultado.put("codigo", "105");
            resultado.put("descripcion", "Ingresar correo electrónico.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }

        Integer actualizar;

        List<String> direccion = cliente.direccion();

        try{

            if(direccion.get(0).equals("1")){

                actualizar = repositorio.actualizar(peticion.getNombre().trim(),
                        peticion.getApellido_paterno().trim(),
                        peticion.getApellido_materno().trim(),
                        peticion.getCorreo_electronico().trim(),
                        " ",
                        " ",
                        " ",
                        " ",
                        identificador,
                        1L
                );

            }else{

                actualizar = repositorio.actualizar(peticion.getNombre().trim(),
                        peticion.getApellido_paterno().trim(),
                        peticion.getApellido_materno().trim(),
                        peticion.getCorreo_electronico().trim(),
                        direccion.get(0),
                        direccion.get(1),
                        direccion.get(2),
                        direccion.get(3),
                        identificador,
                        1L
                );

            }

            if(actualizar != 1){

                resultado.put("codigo", "106");
                resultado.put("descripcion", "Actualización fallida.");

                respuesta.put("resultado", resultado);
                return new ResponseEntity<>(respuesta, HttpStatus.CONFLICT);

            }

            resultado.put("codigo", "100");
            resultado.put("descripcion", "Petición realizada con éxito.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);

        }catch(Exception e){

            resultado.put("codigo", "-54");
            resultado.put("descripcion", "No fue posible actualizar la información el usuario.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}

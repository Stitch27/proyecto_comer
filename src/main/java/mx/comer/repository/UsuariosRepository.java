package mx.comer.repository;

import java.util.List;
import mx.comer.entity.UsuariosEntity;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
@Transactional
public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Long> {

    @Query(value = "SELECT MAX(U_IDENTIFICADOR_INT) FROM TAB_USUARIOS", nativeQuery = true)
    Long identificador();


    @Modifying
    @Query(value = "INSERT INTO TAB_USUARIOS(U_IDENTIFICADOR_INT, U_NOMBRE_VAR, U_APELLIDO_PATERNO_VAR, U_APELLIDO_MATERNO_VAR, U_CORREO_ELECTRONICO_VAR, U_CIUDAD_VAR, U_CODIGO_POSTAL_VAR, U_LATITUD_VAR, U_LONGITUD_VAR) VALUES(:identificador, :nombre, :apellido_paterno, :apellido_materno, :correo_electronico, :ciudad, :codigo_portal, :latitud, :longitud)", nativeQuery = true)
    void registrar_usuario(@Param("identificador") Long identificador, @Param("nombre") String nombre, @Param("apellido_paterno") String apellido_paterno, @Param("apellido_materno") String apellido_materno, @Param("correo_electronico") String correo_electronico, @Param("ciudad") String ciudad, @Param("codigo_portal") String codigo_portal, @Param("latitud") String latitud, @Param("longitud") String longitud);


    @Query(value = "SELECT U_IDENTIFICADOR_INT, U_NOMBRE_VAR, U_APELLIDO_PATERNO_VAR, U_APELLIDO_MATERNO_VAR, U_CORREO_ELECTRONICO_VAR, U_CIUDAD_VAR, U_CODIGO_POSTAL_VAR, U_LATITUD_VAR, U_LONGITUD_VAR, U_ESTATUS_INT FROM TAB_USUARIOS WHERE U_IDENTIFICADOR_INT = :identificador AND U_ESTATUS_INT = :estatus", nativeQuery = true)
    UsuariosEntity consultar_usuario(@Param("identificador") Long identificador, @Param("estatus") Long estatus);


    @Query(value = "SELECT U_IDENTIFICADOR_INT, U_NOMBRE_VAR, U_APELLIDO_PATERNO_VAR, U_APELLIDO_MATERNO_VAR, U_CORREO_ELECTRONICO_VAR, U_CIUDAD_VAR, U_CODIGO_POSTAL_VAR, U_LATITUD_VAR, U_LONGITUD_VAR, U_ESTATUS_INT FROM TAB_USUARIOS WHERE U_ESTATUS_INT = :estatus", nativeQuery = true)
    List<UsuariosEntity> consultar_usuarios(@Param("estatus") Long estatus);


    @Modifying
    @Query(value = "UPDATE TAB_USUARIOS SET U_ESTATUS_INT = 0 WHERE U_IDENTIFICADOR_INT = :identificador AND U_ESTATUS_INT = :estatus", nativeQuery = true)
    Integer eliminar(@Param("identificador") Long identificador, @Param("estatus") Long estatus);


    @Modifying
    @Query(value = "UPDATE TAB_USUARIOS SET U_NOMBRE_VAR = :nombre, U_APELLIDO_PATERNO_VAR = :apellido_paterno, U_APELLIDO_MATERNO_VAR = :apellido_materno, U_CORREO_ELECTRONICO_VAR = :correo_electronico, U_CIUDAD_VAR = :ciudad, U_CODIGO_POSTAL_VAR = :codigo_postal, U_LATITUD_VAR = :latitud, U_LONGITUD_VAR = :longitud WHERE U_IDENTIFICADOR_INT = :identificador AND U_ESTATUS_INT = :estatus", nativeQuery = true)
    Integer actualizar(@Param("nombre") String nombre, @Param("apellido_paterno") String apellido_paterno, @Param("apellido_materno") String apellido_materno, @Param("correo_electronico") String correo_electronico, @Param("ciudad") String ciudad, @Param("codigo_postal") String codigo_postal, @Param("latitud") String latitud, @Param("longitud") String longitud, @Param("identificador") Long identificador, @Param("estatus") Long estatus);

}

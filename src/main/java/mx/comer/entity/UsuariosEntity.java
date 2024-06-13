package mx.comer.entity;

import lombok.Data;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@Table(name = "TAB_USUARIOS")
public class UsuariosEntity implements Serializable {

    @Id
    @Column(name = "U_IDENTIFICADOR_INT")
    private Long identificador;

    @Column(name = "U_NOMBRE_VAR")
    private String nombre;

    @Column(name = "U_APELLIDO_PATERNO_VAR")
    private String apellido_paterno;

    @Column(name = "U_APELLIDO_MATERNO_VAR")
    private String apellido_materno;

    @Column(name = "U_CORREO_ELECTRONICO_VAR")
    private String correo_electronico;

    @Column(name = "U_CIUDAD_VAR")
    private String ciudad;

    @Column(name = "U_CODIGO_POSTAL_VAR")
    private String codigo_postal;

    @Column(name = "U_LATITUD_VAR")
    private String latitud;

    @Column(name = "U_LONGITUD_VAR")
    private String longitud;

    @Column(name = "U_ESTATUS_INT")
    private Long estatus;

}

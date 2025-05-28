/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByIdUsuario", query = "SELECT u FROM Usuarios u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuarios.findByNombre", query = "SELECT u FROM Usuarios u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuarios.findByCorreo", query = "SELECT u FROM Usuarios u WHERE u.correo = :correo"),
    @NamedQuery(name = "Usuarios.findByContrasenaHash", query = "SELECT u FROM Usuarios u WHERE u.contrasenaHash = :contrasenaHash"),
    @NamedQuery(name = "Usuarios.findByRol", query = "SELECT u FROM Usuarios u WHERE u.rol = :rol"),
    @NamedQuery(name = "Usuarios.findByNumeroControl", query = "SELECT u FROM Usuarios u WHERE u.numeroControl = :numeroControl"),
    @NamedQuery(name = "Usuarios.findByFechaRegistro", query = "SELECT u FROM Usuarios u WHERE u.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Usuarios.findByUltimaModificacion", query = "SELECT u FROM Usuarios u WHERE u.ultimaModificacion = :ultimaModificacion"),
    @NamedQuery(name = "Usuarios.findByActivo", query = "SELECT u FROM Usuarios u WHERE u.activo = :activo")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "contrasena_hash")
    private String contrasenaHash;
    @Basic(optional = false)
    @Column(name = "rol")
    private String rol;
    @Column(name = "numero_control")
    private String numeroControl;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "ultima_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @Column(name = "activo")
    private Boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioComentarista")
    private List<ComentariosRevisionTaller> comentariosRevisionTallerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioDestinatario")
    private List<Notificaciones> notificacionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioSubio")
    private List<Evidencias> evidenciasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioProponente")
    private List<Talleres> talleresList;
    @OneToMany(mappedBy = "idDocenteResponsable")
    private List<Eventos> eventosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioRegistra")
    private List<BitacorasEventos> bitacorasEventosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioPublica")
    private List<Convocatorias> convocatoriasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTallerista")
    private List<EventoParticipantesTalleres> eventoParticipantesTalleresList;

    public Usuarios() {
    }

    public Usuarios(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuarios(Integer idUsuario, String nombre, String correo, String contrasenaHash, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
        this.rol = rol;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getUltimaModificacion() {
        return ultimaModificacion;
    }

    public void setUltimaModificacion(Date ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<ComentariosRevisionTaller> getComentariosRevisionTallerList() {
        return comentariosRevisionTallerList;
    }

    public void setComentariosRevisionTallerList(List<ComentariosRevisionTaller> comentariosRevisionTallerList) {
        this.comentariosRevisionTallerList = comentariosRevisionTallerList;
    }

    @XmlTransient
    public List<Notificaciones> getNotificacionesList() {
        return notificacionesList;
    }

    public void setNotificacionesList(List<Notificaciones> notificacionesList) {
        this.notificacionesList = notificacionesList;
    }

    @XmlTransient
    public List<Evidencias> getEvidenciasList() {
        return evidenciasList;
    }

    public void setEvidenciasList(List<Evidencias> evidenciasList) {
        this.evidenciasList = evidenciasList;
    }

    @XmlTransient
    public List<Talleres> getTalleresList() {
        return talleresList;
    }

    public void setTalleresList(List<Talleres> talleresList) {
        this.talleresList = talleresList;
    }

    @XmlTransient
    public List<Eventos> getEventosList() {
        return eventosList;
    }

    public void setEventosList(List<Eventos> eventosList) {
        this.eventosList = eventosList;
    }

    @XmlTransient
    public List<BitacorasEventos> getBitacorasEventosList() {
        return bitacorasEventosList;
    }

    public void setBitacorasEventosList(List<BitacorasEventos> bitacorasEventosList) {
        this.bitacorasEventosList = bitacorasEventosList;
    }

    @XmlTransient
    public List<Convocatorias> getConvocatoriasList() {
        return convocatoriasList;
    }

    public void setConvocatoriasList(List<Convocatorias> convocatoriasList) {
        this.convocatoriasList = convocatoriasList;
    }

    @XmlTransient
    public List<EventoParticipantesTalleres> getEventoParticipantesTalleresList() {
        return eventoParticipantesTalleresList;
    }

    public void setEventoParticipantesTalleresList(List<EventoParticipantesTalleres> eventoParticipantesTalleresList) {
        this.eventoParticipantesTalleresList = eventoParticipantesTalleresList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idUsuario + " | " + nombre;
        //return "modelo.Usuarios[ idUsuario=" + idUsuario + " ]";
    }
    
}

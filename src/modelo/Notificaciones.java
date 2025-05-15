/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "notificaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notificaciones.findAll", query = "SELECT n FROM Notificaciones n"),
    @NamedQuery(name = "Notificaciones.findByIdNotificacion", query = "SELECT n FROM Notificaciones n WHERE n.idNotificacion = :idNotificacion"),
    @NamedQuery(name = "Notificaciones.findByTipoNotificacion", query = "SELECT n FROM Notificaciones n WHERE n.tipoNotificacion = :tipoNotificacion"),
    @NamedQuery(name = "Notificaciones.findByLeida", query = "SELECT n FROM Notificaciones n WHERE n.leida = :leida"),
    @NamedQuery(name = "Notificaciones.findByFechaCreacion", query = "SELECT n FROM Notificaciones n WHERE n.fechaCreacion = :fechaCreacion")})
public class Notificaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_notificacion")
    private Integer idNotificacion;
    @Basic(optional = false)
    @Lob
    @Column(name = "mensaje")
    private String mensaje;
    @Basic(optional = false)
    @Column(name = "tipo_notificacion")
    private String tipoNotificacion;
    @Column(name = "leida")
    private Boolean leida;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "id_convocatoria_relacionada", referencedColumnName = "id_convocatoria")
    @ManyToOne
    private Convocatorias idConvocatoriaRelacionada;
    @JoinColumn(name = "id_evento_relacionado", referencedColumnName = "id_evento")
    @ManyToOne
    private Eventos idEventoRelacionado;
    @JoinColumn(name = "id_taller_relacionado", referencedColumnName = "id_taller")
    @ManyToOne
    private Talleres idTallerRelacionado;
    @JoinColumn(name = "id_usuario_destinatario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuarios idUsuarioDestinatario;

    public Notificaciones() {
    }

    public Notificaciones(Integer idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Notificaciones(Integer idNotificacion, String mensaje, String tipoNotificacion) {
        this.idNotificacion = idNotificacion;
        this.mensaje = mensaje;
        this.tipoNotificacion = tipoNotificacion;
    }

    public Integer getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Integer idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipoNotificacion() {
        return tipoNotificacion;
    }

    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }

    public Boolean getLeida() {
        return leida;
    }

    public void setLeida(Boolean leida) {
        this.leida = leida;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Convocatorias getIdConvocatoriaRelacionada() {
        return idConvocatoriaRelacionada;
    }

    public void setIdConvocatoriaRelacionada(Convocatorias idConvocatoriaRelacionada) {
        this.idConvocatoriaRelacionada = idConvocatoriaRelacionada;
    }

    public Eventos getIdEventoRelacionado() {
        return idEventoRelacionado;
    }

    public void setIdEventoRelacionado(Eventos idEventoRelacionado) {
        this.idEventoRelacionado = idEventoRelacionado;
    }

    public Talleres getIdTallerRelacionado() {
        return idTallerRelacionado;
    }

    public void setIdTallerRelacionado(Talleres idTallerRelacionado) {
        this.idTallerRelacionado = idTallerRelacionado;
    }

    public Usuarios getIdUsuarioDestinatario() {
        return idUsuarioDestinatario;
    }

    public void setIdUsuarioDestinatario(Usuarios idUsuarioDestinatario) {
        this.idUsuarioDestinatario = idUsuarioDestinatario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotificacion != null ? idNotificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notificaciones)) {
            return false;
        }
        Notificaciones other = (Notificaciones) object;
        if ((this.idNotificacion == null && other.idNotificacion != null) || (this.idNotificacion != null && !this.idNotificacion.equals(other.idNotificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Notificaciones[ idNotificacion=" + idNotificacion + " ]";
    }
    
}

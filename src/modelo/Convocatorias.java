/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@Table(name = "convocatorias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Convocatorias.findAll", query = "SELECT c FROM Convocatorias c"),
    @NamedQuery(name = "Convocatorias.findByIdConvocatoria", query = "SELECT c FROM Convocatorias c WHERE c.idConvocatoria = :idConvocatoria"),
    @NamedQuery(name = "Convocatorias.findByTitulo", query = "SELECT c FROM Convocatorias c WHERE c.titulo = :titulo"),
    @NamedQuery(name = "Convocatorias.findByFechaPublicacion", query = "SELECT c FROM Convocatorias c WHERE c.fechaPublicacion = :fechaPublicacion"),
    @NamedQuery(name = "Convocatorias.findByFechaLimitePropuestas", query = "SELECT c FROM Convocatorias c WHERE c.fechaLimitePropuestas = :fechaLimitePropuestas"),
    @NamedQuery(name = "Convocatorias.findByDocumentoAdjuntoRuta", query = "SELECT c FROM Convocatorias c WHERE c.documentoAdjuntoRuta = :documentoAdjuntoRuta"),
    @NamedQuery(name = "Convocatorias.findByFechaCreacion", query = "SELECT c FROM Convocatorias c WHERE c.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Convocatorias.findByUltimaModificacion", query = "SELECT c FROM Convocatorias c WHERE c.ultimaModificacion = :ultimaModificacion")})
public class Convocatorias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_convocatoria")
    private Integer idConvocatoria;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "fecha_publicacion")
    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;
    @Column(name = "fecha_limite_propuestas")
    @Temporal(TemporalType.DATE)
    private Date fechaLimitePropuestas;
    @Column(name = "documento_adjunto_ruta")
    private String documentoAdjuntoRuta;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "ultima_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "idConvocatoriaRelacionada")
    private List<Notificaciones> notificacionesList;
    @OneToMany(mappedBy = "idConvocatoriaOrigen")
    private List<Eventos> eventosList;
    @JoinColumn(name = "id_usuario_publica", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuarios idUsuarioPublica;

    public Convocatorias() {
    }

    public Convocatorias(Integer idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
    }

    public Convocatorias(Integer idConvocatoria, String titulo, String descripcion, Date fechaPublicacion) {
        this.idConvocatoria = idConvocatoria;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
    }

    public Integer getIdConvocatoria() {
        return idConvocatoria;
    }

    public void setIdConvocatoria(Integer idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaLimitePropuestas() {
        return fechaLimitePropuestas;
    }

    public void setFechaLimitePropuestas(Date fechaLimitePropuestas) {
        this.fechaLimitePropuestas = fechaLimitePropuestas;
    }

    public String getDocumentoAdjuntoRuta() {
        return documentoAdjuntoRuta;
    }

    public void setDocumentoAdjuntoRuta(String documentoAdjuntoRuta) {
        this.documentoAdjuntoRuta = documentoAdjuntoRuta;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getUltimaModificacion() {
        return ultimaModificacion;
    }

    public void setUltimaModificacion(Date ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    @XmlTransient
    public List<Notificaciones> getNotificacionesList() {
        return notificacionesList;
    }

    public void setNotificacionesList(List<Notificaciones> notificacionesList) {
        this.notificacionesList = notificacionesList;
    }

    @XmlTransient
    public List<Eventos> getEventosList() {
        return eventosList;
    }

    public void setEventosList(List<Eventos> eventosList) {
        this.eventosList = eventosList;
    }

    public Usuarios getIdUsuarioPublica() {
        return idUsuarioPublica;
    }

    public void setIdUsuarioPublica(Usuarios idUsuarioPublica) {
        this.idUsuarioPublica = idUsuarioPublica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConvocatoria != null ? idConvocatoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Convocatorias)) {
            return false;
        }
        Convocatorias other = (Convocatorias) object;
        if ((this.idConvocatoria == null && other.idConvocatoria != null) || (this.idConvocatoria != null && !this.idConvocatoria.equals(other.idConvocatoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Convocatorias[ idConvocatoria=" + idConvocatoria + " ]";
    }
    
}

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
@Table(name = "talleres")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Talleres.findAll", query = "SELECT t FROM Talleres t"),
    @NamedQuery(name = "Talleres.findByIdTaller", query = "SELECT t FROM Talleres t WHERE t.idTaller = :idTaller"),
    @NamedQuery(name = "Talleres.findByNombre", query = "SELECT t FROM Talleres t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Talleres.findByManualRuta", query = "SELECT t FROM Talleres t WHERE t.manualRuta = :manualRuta"),
    @NamedQuery(name = "Talleres.findByEstado", query = "SELECT t FROM Talleres t WHERE t.estado = :estado"),
    @NamedQuery(name = "Talleres.findByFechaCreacion", query = "SELECT t FROM Talleres t WHERE t.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Talleres.findByUltimaModificacion", query = "SELECT t FROM Talleres t WHERE t.ultimaModificacion = :ultimaModificacion")})
public class Talleres implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_taller")
    private Integer idTaller;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Lob
    @Column(name = "descripcion_publica")
    private String descripcionPublica;
    @Lob
    @Column(name = "detalles_internos")
    private String detallesInternos;
    @Basic(optional = false)
    @Lob
    @Column(name = "requisitos_materiales")
    private String requisitosMateriales;
    @Column(name = "manual_ruta")
    private String manualRuta;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "ultima_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTaller")
    private List<ComentariosRevisionTaller> comentariosRevisionTallerList;
    @OneToMany(mappedBy = "idTallerRelacionado")
    private List<Notificaciones> notificacionesList;
    @OneToMany(mappedBy = "idTallerAsociado")
    private List<Evidencias> evidenciasList;
    @JoinColumn(name = "id_usuario_proponente", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuarios idUsuarioProponente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTallerImpartido")
    private List<EventoParticipantesTalleres> eventoParticipantesTalleresList;

    public Talleres() {
    }

    public Talleres(Integer idTaller) {
        this.idTaller = idTaller;
    }

    public Talleres(Integer idTaller, String nombre, String descripcionPublica, String requisitosMateriales, String estado) {
        this.idTaller = idTaller;
        this.nombre = nombre;
        this.descripcionPublica = descripcionPublica;
        this.requisitosMateriales = requisitosMateriales;
        this.estado = estado;
    }

    public Integer getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(Integer idTaller) {
        this.idTaller = idTaller;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionPublica() {
        return descripcionPublica;
    }

    public void setDescripcionPublica(String descripcionPublica) {
        this.descripcionPublica = descripcionPublica;
    }

    public String getDetallesInternos() {
        return detallesInternos;
    }

    public void setDetallesInternos(String detallesInternos) {
        this.detallesInternos = detallesInternos;
    }

    public String getRequisitosMateriales() {
        return requisitosMateriales;
    }

    public void setRequisitosMateriales(String requisitosMateriales) {
        this.requisitosMateriales = requisitosMateriales;
    }

    public String getManualRuta() {
        return manualRuta;
    }

    public void setManualRuta(String manualRuta) {
        this.manualRuta = manualRuta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public Usuarios getIdUsuarioProponente() {
        return idUsuarioProponente;
    }

    public void setIdUsuarioProponente(Usuarios idUsuarioProponente) {
        this.idUsuarioProponente = idUsuarioProponente;
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
        hash += (idTaller != null ? idTaller.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Talleres)) {
            return false;
        }
        Talleres other = (Talleres) object;
        if ((this.idTaller == null && other.idTaller != null) || (this.idTaller != null && !this.idTaller.equals(other.idTaller))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idTaller + " / " + nombre;
        //return "modelo.Talleres[ idTaller=" + idTaller + " ]";
    }
    
}

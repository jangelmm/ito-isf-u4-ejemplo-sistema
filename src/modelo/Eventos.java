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
@Table(name = "eventos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Eventos.findAll", query = "SELECT e FROM Eventos e"),
    @NamedQuery(name = "Eventos.findByIdEvento", query = "SELECT e FROM Eventos e WHERE e.idEvento = :idEvento"),
    @NamedQuery(name = "Eventos.findByNombre", query = "SELECT e FROM Eventos e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Eventos.findByFechaEvento", query = "SELECT e FROM Eventos e WHERE e.fechaEvento = :fechaEvento"),
    @NamedQuery(name = "Eventos.findByHoraInicioEvento", query = "SELECT e FROM Eventos e WHERE e.horaInicioEvento = :horaInicioEvento"),
    @NamedQuery(name = "Eventos.findByHoraFinEvento", query = "SELECT e FROM Eventos e WHERE e.horaFinEvento = :horaFinEvento"),
    @NamedQuery(name = "Eventos.findByLugarEvento", query = "SELECT e FROM Eventos e WHERE e.lugarEvento = :lugarEvento"),
    @NamedQuery(name = "Eventos.findByEstadoEvento", query = "SELECT e FROM Eventos e WHERE e.estadoEvento = :estadoEvento"),
    @NamedQuery(name = "Eventos.findByFechaCreacion", query = "SELECT e FROM Eventos e WHERE e.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Eventos.findByUltimaModificacion", query = "SELECT e FROM Eventos e WHERE e.ultimaModificacion = :ultimaModificacion")})
public class Eventos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_evento")
    private Integer idEvento;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Column(name = "descripcion_publica")
    private String descripcionPublica;
    @Basic(optional = false)
    @Column(name = "fecha_evento")
    @Temporal(TemporalType.DATE)
    private Date fechaEvento;
    @Column(name = "hora_inicio_evento")
    @Temporal(TemporalType.TIME)
    private Date horaInicioEvento;
    @Column(name = "hora_fin_evento")
    @Temporal(TemporalType.TIME)
    private Date horaFinEvento;
    @Column(name = "lugar_evento")
    private String lugarEvento;
    @Basic(optional = false)
    @Column(name = "estado_evento")
    private String estadoEvento;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "ultima_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "idEventoRelacionado")
    private List<Notificaciones> notificacionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEvento")
    private List<Evidencias> evidenciasList;
    @JoinColumn(name = "id_convocatoria_origen", referencedColumnName = "id_convocatoria")
    @ManyToOne
    private Convocatorias idConvocatoriaOrigen;
    @JoinColumn(name = "id_docente_responsable", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuarios idDocenteResponsable;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEvento")
    private List<BitacorasEventos> bitacorasEventosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEvento")
    private List<EventoParticipantesTalleres> eventoParticipantesTalleresList;

    public Eventos() {
    }

    public Eventos(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Eventos(Integer idEvento, String nombre, Date fechaEvento, String estadoEvento) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.fechaEvento = fechaEvento;
        this.estadoEvento = estadoEvento;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
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

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public Date getHoraInicioEvento() {
        return horaInicioEvento;
    }

    public void setHoraInicioEvento(Date horaInicioEvento) {
        this.horaInicioEvento = horaInicioEvento;
    }

    public Date getHoraFinEvento() {
        return horaFinEvento;
    }

    public void setHoraFinEvento(Date horaFinEvento) {
        this.horaFinEvento = horaFinEvento;
    }

    public String getLugarEvento() {
        return lugarEvento;
    }

    public void setLugarEvento(String lugarEvento) {
        this.lugarEvento = lugarEvento;
    }

    public String getEstadoEvento() {
        return estadoEvento;
    }

    public void setEstadoEvento(String estadoEvento) {
        this.estadoEvento = estadoEvento;
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
    public List<Evidencias> getEvidenciasList() {
        return evidenciasList;
    }

    public void setEvidenciasList(List<Evidencias> evidenciasList) {
        this.evidenciasList = evidenciasList;
    }

    public Convocatorias getIdConvocatoriaOrigen() {
        return idConvocatoriaOrigen;
    }

    public void setIdConvocatoriaOrigen(Convocatorias idConvocatoriaOrigen) {
        this.idConvocatoriaOrigen = idConvocatoriaOrigen;
    }

    public Usuarios getIdDocenteResponsable() {
        return idDocenteResponsable;
    }

    public void setIdDocenteResponsable(Usuarios idDocenteResponsable) {
        this.idDocenteResponsable = idDocenteResponsable;
    }

    @XmlTransient
    public List<BitacorasEventos> getBitacorasEventosList() {
        return bitacorasEventosList;
    }

    public void setBitacorasEventosList(List<BitacorasEventos> bitacorasEventosList) {
        this.bitacorasEventosList = bitacorasEventosList;
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
        hash += (idEvento != null ? idEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Eventos)) {
            return false;
        }
        Eventos other = (Eventos) object;
        if ((this.idEvento == null && other.idEvento != null) || (this.idEvento != null && !this.idEvento.equals(other.idEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idEvento + " | " + nombre + " | " + fechaEvento;
        //return "modelo.Eventos[ idEvento=" + idEvento + " ]";
    }
    
}

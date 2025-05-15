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
@Table(name = "evidencias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evidencias.findAll", query = "SELECT e FROM Evidencias e"),
    @NamedQuery(name = "Evidencias.findByIdEvidencia", query = "SELECT e FROM Evidencias e WHERE e.idEvidencia = :idEvidencia"),
    @NamedQuery(name = "Evidencias.findByTipoEvidencia", query = "SELECT e FROM Evidencias e WHERE e.tipoEvidencia = :tipoEvidencia"),
    @NamedQuery(name = "Evidencias.findByRutaArchivo", query = "SELECT e FROM Evidencias e WHERE e.rutaArchivo = :rutaArchivo"),
    @NamedQuery(name = "Evidencias.findByFechaSubida", query = "SELECT e FROM Evidencias e WHERE e.fechaSubida = :fechaSubida")})
public class Evidencias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_evidencia")
    private Integer idEvidencia;
    @Basic(optional = false)
    @Column(name = "tipo_evidencia")
    private String tipoEvidencia;
    @Basic(optional = false)
    @Column(name = "ruta_archivo")
    private String rutaArchivo;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_subida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSubida;
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento")
    @ManyToOne(optional = false)
    private Eventos idEvento;
    @JoinColumn(name = "id_taller_asociado", referencedColumnName = "id_taller")
    @ManyToOne
    private Talleres idTallerAsociado;
    @JoinColumn(name = "id_usuario_subio", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuarios idUsuarioSubio;

    public Evidencias() {
    }

    public Evidencias(Integer idEvidencia) {
        this.idEvidencia = idEvidencia;
    }

    public Evidencias(Integer idEvidencia, String tipoEvidencia, String rutaArchivo) {
        this.idEvidencia = idEvidencia;
        this.tipoEvidencia = tipoEvidencia;
        this.rutaArchivo = rutaArchivo;
    }

    public Integer getIdEvidencia() {
        return idEvidencia;
    }

    public void setIdEvidencia(Integer idEvidencia) {
        this.idEvidencia = idEvidencia;
    }

    public String getTipoEvidencia() {
        return tipoEvidencia;
    }

    public void setTipoEvidencia(String tipoEvidencia) {
        this.tipoEvidencia = tipoEvidencia;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(Date fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public Eventos getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Eventos idEvento) {
        this.idEvento = idEvento;
    }

    public Talleres getIdTallerAsociado() {
        return idTallerAsociado;
    }

    public void setIdTallerAsociado(Talleres idTallerAsociado) {
        this.idTallerAsociado = idTallerAsociado;
    }

    public Usuarios getIdUsuarioSubio() {
        return idUsuarioSubio;
    }

    public void setIdUsuarioSubio(Usuarios idUsuarioSubio) {
        this.idUsuarioSubio = idUsuarioSubio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvidencia != null ? idEvidencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evidencias)) {
            return false;
        }
        Evidencias other = (Evidencias) object;
        if ((this.idEvidencia == null && other.idEvidencia != null) || (this.idEvidencia != null && !this.idEvidencia.equals(other.idEvidencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Evidencias[ idEvidencia=" + idEvidencia + " ]";
    }
    
}

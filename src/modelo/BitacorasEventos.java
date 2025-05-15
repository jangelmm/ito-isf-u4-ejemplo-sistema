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
@Table(name = "bitacoras_eventos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BitacorasEventos.findAll", query = "SELECT b FROM BitacorasEventos b"),
    @NamedQuery(name = "BitacorasEventos.findByIdBitacora", query = "SELECT b FROM BitacorasEventos b WHERE b.idBitacora = :idBitacora"),
    @NamedQuery(name = "BitacorasEventos.findByFechaHoraEntrada", query = "SELECT b FROM BitacorasEventos b WHERE b.fechaHoraEntrada = :fechaHoraEntrada")})
public class BitacorasEventos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_bitacora")
    private Integer idBitacora;
    @Basic(optional = false)
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "fecha_hora_entrada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraEntrada;
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento")
    @ManyToOne(optional = false)
    private Eventos idEvento;
    @JoinColumn(name = "id_usuario_registra", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuarios idUsuarioRegistra;

    public BitacorasEventos() {
    }

    public BitacorasEventos(Integer idBitacora) {
        this.idBitacora = idBitacora;
    }

    public BitacorasEventos(Integer idBitacora, String observacion) {
        this.idBitacora = idBitacora;
        this.observacion = observacion;
    }

    public Integer getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Integer idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaHoraEntrada() {
        return fechaHoraEntrada;
    }

    public void setFechaHoraEntrada(Date fechaHoraEntrada) {
        this.fechaHoraEntrada = fechaHoraEntrada;
    }

    public Eventos getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Eventos idEvento) {
        this.idEvento = idEvento;
    }

    public Usuarios getIdUsuarioRegistra() {
        return idUsuarioRegistra;
    }

    public void setIdUsuarioRegistra(Usuarios idUsuarioRegistra) {
        this.idUsuarioRegistra = idUsuarioRegistra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBitacora != null ? idBitacora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BitacorasEventos)) {
            return false;
        }
        BitacorasEventos other = (BitacorasEventos) object;
        if ((this.idBitacora == null && other.idBitacora != null) || (this.idBitacora != null && !this.idBitacora.equals(other.idBitacora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.BitacorasEventos[ idBitacora=" + idBitacora + " ]";
    }
    
}

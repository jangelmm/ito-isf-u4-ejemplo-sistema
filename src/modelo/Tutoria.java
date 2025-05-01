/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
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

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "tutoria")
@NamedQueries({
    @NamedQuery(name = "Tutoria.findAll", query = "SELECT t FROM Tutoria t"),
    @NamedQuery(name = "Tutoria.findByIdTutoria", query = "SELECT t FROM Tutoria t WHERE t.idTutoria = :idTutoria")})
public class Tutoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tutoria")
    private Integer idTutoria;
    @Basic(optional = false)
    @Lob
    @Column(name = "acciones")
    private String acciones;
    @JoinColumn(name = "id_cita", referencedColumnName = "id_cita")
    @ManyToOne(optional = false)
    private Cita idCita;
    @JoinColumn(name = "id_tutorado", referencedColumnName = "id_tutorado")
    @ManyToOne(optional = false)
    private Tutorado idTutorado;

    public Tutoria() {
    }

    public Tutoria(Integer idTutoria) {
        this.idTutoria = idTutoria;
    }

    public Tutoria(Integer idTutoria, String acciones) {
        this.idTutoria = idTutoria;
        this.acciones = acciones;
    }

    public Integer getIdTutoria() {
        return idTutoria;
    }

    public void setIdTutoria(Integer idTutoria) {
        this.idTutoria = idTutoria;
    }

    public String getAcciones() {
        return acciones;
    }

    public void setAcciones(String acciones) {
        this.acciones = acciones;
    }

    public Cita getIdCita() {
        return idCita;
    }

    public void setIdCita(Cita idCita) {
        this.idCita = idCita;
    }

    public Tutorado getIdTutorado() {
        return idTutorado;
    }

    public void setIdTutorado(Tutorado idTutorado) {
        this.idTutorado = idTutorado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTutoria != null ? idTutoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tutoria)) {
            return false;
        }
        Tutoria other = (Tutoria) object;
        if ((this.idTutoria == null && other.idTutoria != null) || (this.idTutoria != null && !this.idTutoria.equals(other.idTutoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Tutoria[ idTutoria=" + idTutoria + " ]";
    }
    
}

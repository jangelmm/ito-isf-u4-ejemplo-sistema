/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "tutor")
@NamedQueries({
    @NamedQuery(name = "Tutor.findAll", query = "SELECT t FROM Tutor t"),
    @NamedQuery(name = "Tutor.findByIdPersona", query = "SELECT t FROM Tutor t WHERE t.idPersona = :idPersona"),
    @NamedQuery(name = "Tutor.findByNumTarjeta", query = "SELECT t FROM Tutor t WHERE t.numTarjeta = :numTarjeta"),
    @NamedQuery(name = "Tutor.findByNombre", query = "SELECT t FROM Tutor t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Tutor.findByCarrera", query = "SELECT t FROM Tutor t WHERE t.carrera = :carrera"),
    @NamedQuery(name = "Tutor.findByDias", query = "SELECT t FROM Tutor t WHERE t.dias = :dias"),
    @NamedQuery(name = "Tutor.findByHoras", query = "SELECT t FROM Tutor t WHERE t.horas = :horas")})
public class Tutor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_persona")
    private Integer idPersona;
    @Column(name = "num_tarjeta")
    private Integer numTarjeta;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "carrera")
    private String carrera;
    @Column(name = "dias")
    private String dias;
    @Column(name = "horas")
    private String horas;
    @OneToMany(mappedBy = "tutor")
    private List<Tutorado> tutoradoList;
    @OneToMany(mappedBy = "tutor")
    private List<Cita> citaList;

    public Tutor() {
    }

    public Tutor(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(Integer numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }
    
    // Metodo en clase tutor que regresa el Num de tutorados
    public int getNumeroDeTutorados() {
        if (tutoradoList != null) {
            return tutoradoList.size();
        } else {
            return 0;
        }
    }
    
    // Regresa la lista de tutorados que tiene el tutor
    public List<Tutorado> getTutoradoList() {
        return tutoradoList;
    }

    public void setTutoradoList(List<Tutorado> tutoradoList) {
        this.tutoradoList = tutoradoList;
    }

    public List<Cita> getCitaList() {
        return citaList;
    }

    public void setCitaList(List<Cita> citaList) {
        this.citaList = citaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tutor)) {
            return false;
        }
        Tutor other = (Tutor) object;
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Tutor[ idPersona=" + idPersona + " ]";
    }
    
}

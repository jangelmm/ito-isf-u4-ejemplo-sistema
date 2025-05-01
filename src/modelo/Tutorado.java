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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "tutorado")
@NamedQueries({
    @NamedQuery(name = "Tutorado.findAll", query = "SELECT t FROM Tutorado t"),
    @NamedQuery(name = "Tutorado.findByIdTutorado", query = "SELECT t FROM Tutorado t WHERE t.idTutorado = :idTutorado"),
    @NamedQuery(name = "Tutorado.findByNc", query = "SELECT t FROM Tutorado t WHERE t.nc = :nc"),
    @NamedQuery(name = "Tutorado.findByNombre", query = "SELECT t FROM Tutorado t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Tutorado.findByGenero", query = "SELECT t FROM Tutorado t WHERE t.genero = :genero"),
    @NamedQuery(name = "Tutorado.findByDias", query = "SELECT t FROM Tutorado t WHERE t.dias = :dias"),
    @NamedQuery(name = "Tutorado.findByFechaNacimiento", query = "SELECT t FROM Tutorado t WHERE t.fechaNacimiento = :fechaNacimiento")})
public class Tutorado implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTutorado")
    private List<Tutoria> tutoriaList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tutorado")
    private Integer idTutorado;
    @Column(name = "nc")
    private String nc;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "genero")
    private Character genero;
    @Column(name = "dias")
    private String dias;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @JoinColumns({
        @JoinColumn(name = "id_tutor", referencedColumnName = "id_persona"),
        @JoinColumn(name = "id_tutor", referencedColumnName = "id_persona")})
    @ManyToOne
    private Tutor tutor;

    public Tutorado() {
    }

    public Tutorado(Integer idTutorado) {
        this.idTutorado = idTutorado;
    }

    public Integer getIdTutorado() {
        return idTutorado;
    }

    public void setIdTutorado(Integer idTutorado) {
        this.idTutorado = idTutorado;
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Character getGenero() {
        return genero;
    }

    public void setGenero(Character genero) {
        this.genero = genero;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTutorado != null ? idTutorado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tutorado)) {
            return false;
        }
        Tutorado other = (Tutorado) object;
        if ((this.idTutorado == null && other.idTutorado != null) || (this.idTutorado != null && !this.idTutorado.equals(other.idTutorado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Tutorado[ idTutorado=" + idTutorado + " ]";
    }

    public List<Tutoria> getTutoriaList() {
        return tutoriaList;
    }

    public void setTutoriaList(List<Tutoria> tutoriaList) {
        this.tutoriaList = tutoriaList;
    }
    
}

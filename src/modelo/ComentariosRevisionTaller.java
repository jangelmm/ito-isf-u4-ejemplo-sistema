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
@Table(name = "comentarios_revision_taller")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComentariosRevisionTaller.findAll", query = "SELECT c FROM ComentariosRevisionTaller c"),
    @NamedQuery(name = "ComentariosRevisionTaller.findByIdComentario", query = "SELECT c FROM ComentariosRevisionTaller c WHERE c.idComentario = :idComentario"),
    @NamedQuery(name = "ComentariosRevisionTaller.findByFechaComentario", query = "SELECT c FROM ComentariosRevisionTaller c WHERE c.fechaComentario = :fechaComentario")})
public class ComentariosRevisionTaller implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_comentario")
    private Integer idComentario;
    @Basic(optional = false)
    @Lob
    @Column(name = "comentario")
    private String comentario;
    @Column(name = "fecha_comentario")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaComentario;
    @JoinColumn(name = "id_taller", referencedColumnName = "id_taller")
    @ManyToOne(optional = false)
    private Talleres idTaller;
    @JoinColumn(name = "id_usuario_comentarista", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuarios idUsuarioComentarista;

    public ComentariosRevisionTaller() {
    }

    public ComentariosRevisionTaller(Integer idComentario) {
        this.idComentario = idComentario;
    }

    public ComentariosRevisionTaller(Integer idComentario, String comentario) {
        this.idComentario = idComentario;
        this.comentario = comentario;
    }

    public Integer getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Integer idComentario) {
        this.idComentario = idComentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(Date fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public Talleres getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(Talleres idTaller) {
        this.idTaller = idTaller;
    }

    public Usuarios getIdUsuarioComentarista() {
        return idUsuarioComentarista;
    }

    public void setIdUsuarioComentarista(Usuarios idUsuarioComentarista) {
        this.idUsuarioComentarista = idUsuarioComentarista;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComentario != null ? idComentario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComentariosRevisionTaller)) {
            return false;
        }
        ComentariosRevisionTaller other = (ComentariosRevisionTaller) object;
        if ((this.idComentario == null && other.idComentario != null) || (this.idComentario != null && !this.idComentario.equals(other.idComentario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ComentariosRevisionTaller[ idComentario=" + idComentario + " ]";
    }
    
}

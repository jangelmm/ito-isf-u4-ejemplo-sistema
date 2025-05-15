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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "evento_participantes_talleres")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventoParticipantesTalleres.findAll", query = "SELECT e FROM EventoParticipantesTalleres e"),
    @NamedQuery(name = "EventoParticipantesTalleres.findByIdEventoParticipanteTaller", query = "SELECT e FROM EventoParticipantesTalleres e WHERE e.idEventoParticipanteTaller = :idEventoParticipanteTaller"),
    @NamedQuery(name = "EventoParticipantesTalleres.findByRolParticipante", query = "SELECT e FROM EventoParticipantesTalleres e WHERE e.rolParticipante = :rolParticipante")})
public class EventoParticipantesTalleres implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_evento_participante_taller")
    private Integer idEventoParticipanteTaller;
    @Column(name = "rol_participante")
    private String rolParticipante;
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento")
    @ManyToOne(optional = false)
    private Eventos idEvento;
    @JoinColumn(name = "id_taller_impartido", referencedColumnName = "id_taller")
    @ManyToOne(optional = false)
    private Talleres idTallerImpartido;
    @JoinColumn(name = "id_tallerista", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuarios idTallerista;

    public EventoParticipantesTalleres() {
    }

    public EventoParticipantesTalleres(Integer idEventoParticipanteTaller) {
        this.idEventoParticipanteTaller = idEventoParticipanteTaller;
    }

    public Integer getIdEventoParticipanteTaller() {
        return idEventoParticipanteTaller;
    }

    public void setIdEventoParticipanteTaller(Integer idEventoParticipanteTaller) {
        this.idEventoParticipanteTaller = idEventoParticipanteTaller;
    }

    public String getRolParticipante() {
        return rolParticipante;
    }

    public void setRolParticipante(String rolParticipante) {
        this.rolParticipante = rolParticipante;
    }

    public Eventos getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Eventos idEvento) {
        this.idEvento = idEvento;
    }

    public Talleres getIdTallerImpartido() {
        return idTallerImpartido;
    }

    public void setIdTallerImpartido(Talleres idTallerImpartido) {
        this.idTallerImpartido = idTallerImpartido;
    }

    public Usuarios getIdTallerista() {
        return idTallerista;
    }

    public void setIdTallerista(Usuarios idTallerista) {
        this.idTallerista = idTallerista;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEventoParticipanteTaller != null ? idEventoParticipanteTaller.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventoParticipantesTalleres)) {
            return false;
        }
        EventoParticipantesTalleres other = (EventoParticipantesTalleres) object;
        if ((this.idEventoParticipanteTaller == null && other.idEventoParticipanteTaller != null) || (this.idEventoParticipanteTaller != null && !this.idEventoParticipanteTaller.equals(other.idEventoParticipanteTaller))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EventoParticipantesTalleres[ idEventoParticipanteTaller=" + idEventoParticipanteTaller + " ]";
    }
    
}

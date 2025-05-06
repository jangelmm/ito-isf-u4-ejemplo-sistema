package modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Tutoria;

public class MTtutoria extends AbstractTableModel {
    
    private List<Tutoria> tutorias;
    private final String[] encabezados = {
        "ID Tutoria", 
        "Acciones", 
        "Cita (Asunto)", 
        "Tutorado"
    };
    
    public MTtutoria(List<Tutoria> tutorias) {
        this.tutorias = tutorias;
    }

    @Override
    public int getRowCount() {
        return tutorias != null ? tutorias.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tutoria tutoria = tutorias.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return tutoria.getIdTutoria();
            case 1: return tutoria.getAcciones();
            case 2: return obtenerAsuntoCita(tutoria);
            case 3: return obtenerNombreTutorado(tutoria);
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return encabezados[column];
    }
    
    // Métodos auxiliares para obtener datos relacionados
    private String obtenerAsuntoCita(Tutoria tutoria) {
        if(tutoria.getIdCita() == null) return "N/A";
        return tutoria.getIdCita().getAsunto();
    }
    
    private String obtenerNombreTutorado(Tutoria tutoria) {
        if(tutoria.getIdTutorado() == null) return "N/A";
        return tutoria.getIdTutorado().getNombre();
    }
    public void setTutores(List<Tutoria> nuevasTutorias) {
        this.tutorias = nuevasTutorias;
        fireTableDataChanged();
    }
}
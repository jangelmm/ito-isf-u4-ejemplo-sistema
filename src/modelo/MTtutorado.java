package modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Tutorado;

public class MTtutorado extends AbstractTableModel {
    
    private List<Tutorado> lt;
    private String[] encabezados = {"Matrícula", "Nombre", "Género", "Días", 
                                  "Fecha Nacimiento", "Tutor Asignado"};
    
    public MTtutorado(List<Tutorado> tutorados) {
        lt = tutorados;
    }

    @Override
    public int getRowCount() {
        return lt != null ? lt.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tutorado tutorado = lt.get(rowIndex);
        switch (columnIndex) {
            case 0: return tutorado.getNc();
            case 1: return tutorado.getNombre();
            case 2: return tutorado.getGenero();
            case 3: return tutorado.getDias();
            case 4: return tutorado.getFechaNacimiento();
            case 5: return (tutorado.getTutor() != null) ? 
                          tutorado.getTutor().getNumTarjeta() : "Sin tutor";
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return encabezados[column];
    }
}
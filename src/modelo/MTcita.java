package modelo;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Cita;

public class MTcita extends AbstractTableModel {
    
    private List<Cita> citas;
    private final String[] encabezados = {
        "ID Cita", 
        "Fecha", 
        "Hora", 
        "Asunto", 
        "Estado", 
        "Tutor"
    };
    
    public MTcita(List<Cita> citas) {
        this.citas = citas;
    }

    @Override
    public int getRowCount() {
        return citas != null ? citas.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cita cita = citas.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return cita.getIdCita();
            case 1:
                return formatFecha(cita.getFecha());
            case 2:
                return formatHora(cita.getHora());
            case 3:
                return cita.getAsunto();
            case 4:
                return cita.getEstado();
            case 5:
                return getNumTargeta(cita);
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return encabezados[column];
    }
    
    // Métodos auxiliares
    private String formatFecha(java.util.Date fecha) {
        if(fecha == null) return "N/A";
        return new SimpleDateFormat("dd/MM/yyyy").format(fecha);
    }
    
    private String formatHora(Integer hora) {
        if(hora == null) return "N/A";
        return String.format("%02d:00", hora); // Formato 14 → "14:00"
    }
    
    private int getNumTargeta(Cita cita) {
        if(cita.getTutor() == null) return 0;
        return cita.getTutor().getNumTarjeta();
    }
}
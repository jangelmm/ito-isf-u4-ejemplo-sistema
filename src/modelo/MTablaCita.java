package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jesus
 */
public class MTablaCita extends AbstractTableModel{

    private ArrayList<DatosTablaCitas> datosCitas;
    String encabezados[] = {" Tutorado ", " Asistencia ", " Acción "};
    Class clasesC[] = {String.class, Boolean.class, String.class};

    public MTablaCita(ArrayList mtc){
        datosCitas = mtc;
    }
    
    public boolean isCellEditable(int r, int c){
        if(c == 1){
            return true;
        }
        if(c == 2 && datosCitas.get(r).getAsistencia() == true){
            return true;
        }
        return false;
    }
            
    @Override
    public String getColumnName(int c){
        return encabezados[c];
    }
    
    @Override
    public int getRowCount() {
        return datosCitas.size();
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }
    
    @Override
    public Class getColumnClass(int c){
        return clasesC[c];
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch(c){
            case 0:
                return datosCitas.get(r).getTutorado().getNombre();
            case 1:
                return datosCitas.get(r).getAsistencia();
            case 2:
                return datosCitas.get(r).getAccion();
            default: 
                return null;
        }
    }
    
    public void getValueAt(Object dato, int r, int c){
        if(c == 1){
            datosCitas.get(r).setAsistencia((Boolean) dato);
        }  
    }
    
    
}

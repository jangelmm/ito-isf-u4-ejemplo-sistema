package modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Tutor;

public class MTtutor extends AbstractTableModel{
    
    private List<Tutor> lt;
    private String encabezados[] = {"No. de Tarjeta", " Nombre ", " Carrera ", " Dias ", " Horario "};
    
    public MTtutor(List<Tutor> tutores){
        lt = tutores;
    }

    @Override
    public int getRowCount() {
        if(lt != null)
            return lt.size();
        return 0;
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return lt.get(rowIndex).getNumTarjeta();
            case 1:
                return lt.get(rowIndex).getNombre();
            case 2:
                return lt.get(rowIndex).getCarrera();
            case 3:
                return lt.get(rowIndex).getDias();
            case 4:
                return lt.get(rowIndex).getHoras();
            default:
                return null;
        }
        
    }
    @Override
    public String getColumnName(int column) {
        return encabezados[column];
    }
}

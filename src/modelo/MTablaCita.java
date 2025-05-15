c
package modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jesus
 */
public class MTablaCita extends AbstractTableModel{

    private List<Tutor> lt;
    private String encabezados[] = {"No. de Tarjeta", " Nombre ", " Carrera ", " Dias ", " Horario "};
    
    public MTtutor(List<Tutor> tutores){
        lt = tutores;
    }
            
    @Override
    public int getRowCount() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getColumnCount() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

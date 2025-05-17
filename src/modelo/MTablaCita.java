package modelo;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class MTablaCita extends AbstractTableModel {

    private ArrayList<DatosTablaCitas> datosCitas;
    // Asegúrate que los nombres y el orden coincidan con tu UI
    private final String[] encabezados = {"Tutorado", "Asistencia", "Acción"};
    private final Class<?>[] clasesColumnas = {String.class, Boolean.class, String.class};

    public MTablaCita(ArrayList<DatosTablaCitas> listaDatosCitas) {
        this.datosCitas = (listaDatosCitas != null) ? listaDatosCitas : new ArrayList<>();
    }

    @Override
    public String getColumnName(int column) {
        return encabezados[column];
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
    public Class<?> getColumnClass(int columnIndex) {
        return clasesColumnas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= datosCitas.size()) return null;
        DatosTablaCitas fila = datosCitas.get(rowIndex);
        switch (columnIndex) {
            case 0: // Tutorado
                return (fila.getTutorado() != null) ? fila.getTutorado().getNombre() : "N/A";
            case 1: // Asistencia
                return fila.getAsistencia(); // Método que devuelve boolean
            case 2: // Acción
                return fila.getAccion(); // Método que devuelve String
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= datosCitas.size()) return false;

        if (columnIndex == 1) { // Columna "Asistencia" (JCheckBox)
            return true; // Siempre editable
        }
        if (columnIndex == 2) { // Columna "Acción" (JComboBox)
            // Solo editable si la asistencia para ESTA fila está marcada
            // Asumimos que getAsistencia() devuelve el estado actual del modelo para esa fila
            Boolean asistenciaMarcada = datosCitas.get(rowIndex).getAsistencia();
            return asistenciaMarcada != null && asistenciaMarcada;
        }
        return false; // Columna de Tutorado (0) no editable desde la tabla
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= datosCitas.size()) return;

        DatosTablaCitas fila = datosCitas.get(rowIndex);

        if (columnIndex == 1) { // Cambió la "Asistencia"
            if (aValue instanceof Boolean) {
                boolean nuevaAsistencia = (Boolean) aValue;
                // Solo actuar si el valor realmente cambió para evitar bucles o trabajo innecesario
                if (fila.getAsistencia() != nuevaAsistencia) {
                    fila.setAsistencia(nuevaAsistencia);
                    fireTableCellUpdated(rowIndex, columnIndex); // Notificar el cambio de la celda de asistencia

                    if (!nuevaAsistencia) { // Si se DESMARCÓ la asistencia
                        // Borrar la acción si no es el valor por defecto
                        String accionActual = fila.getAccion();
                        String valorPorDefectoAccion = "Sin acción"; // O tu valor por defecto
                        if (accionActual != null && !accionActual.equals(valorPorDefectoAccion)) {
                            fila.setAccion(valorPorDefectoAccion);
                            // Notificar que la celda de acción también cambió
                            fireTableCellUpdated(rowIndex, 2);
                        }
                    }
                    // IMPORTANTE: Notificar a la tabla que la fila entera pudo haber cambiado
                    // su estado de editabilidad, especialmente la columna de Acción.
                    // Esto fuerza al JTable a re-consultar isCellEditable para las celdas de esta fila.
                    fireTableRowsUpdated(rowIndex, rowIndex);
                }
            }
        } else if (columnIndex == 2) { // Cambió la "Acción"
            if (aValue instanceof String) {
                // Solo actualizar si el valor realmente cambió
                if (!aValue.equals(fila.getAccion())) {
                    fila.setAccion((String) aValue);
                    fireTableCellUpdated(rowIndex, columnIndex); // Notificar el cambio de la celda de acción
                }
            }
        }
    }

    // Método para refrescar la tabla con nuevos datos si es necesario
    public void actualizarListaDatos(ArrayList<DatosTablaCitas> nuevosDatos) {
        this.datosCitas = (nuevosDatos != null) ? nuevosDatos : new ArrayList<>();
        // Notificar a la tabla que todos los datos cambiaron.
        // Esto es útil cuando cargas datos completamente nuevos (ej. al seleccionar una nueva cita).
        fireTableDataChanged();
    }
}
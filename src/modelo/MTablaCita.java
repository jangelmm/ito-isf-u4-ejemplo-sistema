package modelo;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class MTablaCita extends AbstractTableModel {

    private ArrayList<DatosTablaCitas> datosCitas;
    // Asegúrate que los nombres y el orden coincidan con tu UI
    private final String[] encabezados = {"Tutorado", "Asistencia", "Acción"};
    private final Class<?>[] clasesColumnas = {String.class, Boolean.class, String.class};

    public MTablaCita(ArrayList<DatosTablaCitas> listaDatosCitas) {
        // Es mejor trabajar con una copia o asegurar que la lista no sea null
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
        if (rowIndex < 0 || rowIndex >= datosCitas.size()) {
            return null;
        }
        DatosTablaCitas fila = datosCitas.get(rowIndex);
        switch (columnIndex) {
            case 0: // Tutorado
                return (fila.getTutorado() != null) ? fila.getTutorado().getNombre() : "N/A";
            case 1: // Asistencia
                return fila.getAsistencia(); // Asume que getAsistencia() devuelve boolean
            case 2: // Acción
                return fila.getAccion();   // Asume que getAccion() devuelve String
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= datosCitas.size()) {
            return false;
        }
        if (columnIndex == 1) { // Columna "Asistencia" (JCheckBox)
            return true; // Siempre editable
        }
        if (columnIndex == 2) { // Columna "Acción" (JComboBox)
            // Solo editable si la asistencia para ESTA fila está marcada
            Boolean asistenciaMarcada = datosCitas.get(rowIndex).getAsistencia();
            return asistenciaMarcada != null && asistenciaMarcada;
        }
        return false; // Columna de Tutorado (0) no es editable desde la tabla
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= datosCitas.size()) {
            return;
        }

        DatosTablaCitas fila = datosCitas.get(rowIndex);
        boolean seRealizoCambio = false;

        if (columnIndex == 1) { // Cambió la "Asistencia"
            if (aValue instanceof Boolean) {
                boolean nuevaAsistencia = (Boolean) aValue;
                if (fila.getAsistencia() != nuevaAsistencia) { // Solo si el valor realmente cambió
                    fila.setAsistencia(nuevaAsistencia);
                    fireTableCellUpdated(rowIndex, columnIndex); // Notificar cambio en celda de asistencia
                    seRealizoCambio = true;

                    if (!nuevaAsistencia) { // Si se DESMARCÓ la asistencia
                        String accionActual = fila.getAccion();
                        String valorPorDefectoAccion = "Sin acción"; // O el valor por defecto de tu JComboBox
                        if (accionActual != null && !accionActual.equals(valorPorDefectoAccion)) {
                            fila.setAccion(valorPorDefectoAccion);
                            fireTableCellUpdated(rowIndex, 2); // Notificar cambio en celda de Acción
                        }
                    }
                }
            }
        } else if (columnIndex == 2) { // Cambió la "Acción"
            if (aValue instanceof String) {
                if (!aValue.equals(fila.getAccion())) { // Solo si el valor realmente cambió
                    fila.setAccion((String) aValue);
                    fireTableCellUpdated(rowIndex, columnIndex); // Notificar cambio en celda de acción
                    // No es necesario 'seRealizoCambio = true;' aquí a menos que afecte otros estados.
                }
            }
        }

        // Si la asistencia cambió, la editabilidad de la columna de Acción pudo haber cambiado.
        // Notificar a la JTable para que re-evalúe la fila.
        if (seRealizoCambio && columnIndex == 1) {
            fireTableRowsUpdated(rowIndex, rowIndex);
        }
    }

    // Método para actualizar la lista de datos y notificar a la tabla
    public void actualizarListaDatos(ArrayList<DatosTablaCitas> nuevosDatos) {
        this.datosCitas = (nuevosDatos != null) ? nuevosDatos : new ArrayList<>();
        fireTableDataChanged(); // Notifica que toda la estructura/datos de la tabla pudieron haber cambiado
    }
}
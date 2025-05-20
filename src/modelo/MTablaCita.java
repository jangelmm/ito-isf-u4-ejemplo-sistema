package modelo;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial") // Añade esto si no tienes serialVersionUID y quieres quitar el warning
public class MTablaCita extends AbstractTableModel {

    private ArrayList<DatosTablaCitas> datosCitas;
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
        // Una fila extra para los totales
        return datosCitas.size() + 1;
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        // Para la última fila, si quieres mostrar texto, asegúrate que la columna pueda manejar String.
        // Como la columna "Acción" (índice 2) ya es String, está bien.
        return clasesColumnas[columnIndex];
    }

    /**
     * Método para calcular el total de asistencias.
     */
    public int calcularTotalAsistencias() {
        int totalAsistencias = 0;
        for (DatosTablaCitas dato : datosCitas) {
            if (dato.getAsistencia()) { // Asume que getAsistencia() devuelve boolean
                totalAsistencias++;
            }
        }
        return totalAsistencias;
    }

    @Override
    // Dentro de MTablaCita.java
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex == datosCitas.size()) { // Fila de totales
            switch (columnIndex) {
                case 0: // Columna "Tutorado"
                    return "Total Asistencias:"; // Etiqueta
                case 1: // Columna "Asistencia"
                    // Para la fila de totales, esta columna no debe mostrar un checkbox activo.
                    // Devolver null o un String vacío si la columna espera Boolean pero no quieres un checkbox.
                    // O, si quieres que esté vacía y no muestre el renderer de Boolean,
                    // podrías necesitar un renderer personalizado para esta celda específica,
                    // o simplemente devolver un String vacío y asegurar que getColumnClass
                    // puede manejarlo (aunque getColumnClass se define por columna, no por celda).
                    // Lo más simple es devolver null para que el renderer de Boolean muestre una celda vacía/desmarcada.
                    return null; // O false, si quieres un checkbox desmarcado
                case 2: // Columna "Acción"
                    return "" + calcularTotalAsistencias(); // Mostrar el conteo como String aquí
                default:
                    return "";
            }
        }

        // Si no es la fila de totales, es una fila de datos normal
        if (rowIndex < 0 || rowIndex >= datosCitas.size()) {
            return null;
        }

        DatosTablaCitas fila = datosCitas.get(rowIndex);
        switch (columnIndex) {
            case 0: // Tutorado
                return (fila.getTutorado() != null) ? fila.getTutorado().getNombre() : "N/A";
            case 1: // Asistencia
                return fila.getAsistencia();
            case 2: // Acción
                return fila.getAccion();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // La fila de totales no es editable
        if (rowIndex == datosCitas.size()) {
            return false;
        }

        // Lógica de editabilidad para las filas de datos (como la tenías antes)
        if (rowIndex < 0 || rowIndex >= datosCitas.size()) {
            return false;
        }
        if (columnIndex == 1) { // Columna "Asistencia"
            return true;
        }
        if (columnIndex == 2) { // Columna "Acción"
            Boolean asistenciaMarcada = datosCitas.get(rowIndex).getAsistencia();
            return asistenciaMarcada != null && asistenciaMarcada;
        }
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // No permitir edición en la fila de totales
        if (rowIndex == datosCitas.size()) {
            return;
        }

        if (rowIndex < 0 || rowIndex >= datosCitas.size()) {
            return;
        }

        DatosTablaCitas fila = datosCitas.get(rowIndex);
        boolean asistenciaCambioEstado = false;

        if (columnIndex == 1) { // Cambió la "Asistencia"
            if (aValue instanceof Boolean) {
                boolean nuevaAsistencia = (Boolean) aValue;
                if (fila.getAsistencia() != nuevaAsistencia) {
                    fila.setAsistencia(nuevaAsistencia);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    asistenciaCambioEstado = true;

                    if (!nuevaAsistencia) {
                        String valorPorDefectoAccion = "Sin acción";
                        if (!valorPorDefectoAccion.equals(fila.getAccion())) {
                            fila.setAccion(valorPorDefectoAccion);
                            fireTableCellUpdated(rowIndex, 2);
                        }
                    }
                    // Notificar que la fila de totales necesita ser actualizada
                    fireTableCellUpdated(datosCitas.size(), 0); // Para la etiqueta "Total Asistencias:"
                    fireTableCellUpdated(datosCitas.size(), 1); // Para el valor del conteo
                }
            }
        } else if (columnIndex == 2) { // Cambió la "Acción"
            if (aValue instanceof String) {
                if (!aValue.equals(fila.getAccion())) {
                    fila.setAccion((String) aValue);
                    fireTableCellUpdated(rowIndex, columnIndex);
                }
            }
        }

        if (asistenciaCambioEstado) {
        fireTableRowsUpdated(rowIndex, rowIndex); 
        fireTableCellUpdated(datosCitas.size(), 0); // Etiqueta en col 0
        fireTableCellUpdated(datosCitas.size(), 2); // Conteo en col 2
    }
    }

    public void actualizarListaDatos(ArrayList<DatosTablaCitas> nuevosDatos) {
        this.datosCitas = (nuevosDatos != null) ? nuevosDatos : new ArrayList<>();
        fireTableDataChanged(); // Esto repintará toda la tabla, incluyendo la fila de totales
    }
}
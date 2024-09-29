package Frames;

import com.github.lgooddatepicker.components.DatePicker;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entidad.SucursalEntidad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;

public class ReporteSucursalesFrame extends JFrame {
    private JComboBox<SucursalEntidad> comboSucursales;
    private DatePicker datePickerInicio;
    private DatePicker datePickerFin;
    private JButton btnGenerarReporte;

    public ReporteSucursalesFrame(List<SucursalEntidad> sucursales) {
        setTitle("Generar Reporte de Sucursales");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        // Combobox para seleccionar sucursales
        JPanel panelSucursal = new JPanel();
        panelSucursal.add(new JLabel("Seleccionar Sucursal:"));
        comboSucursales = new JComboBox<>();
        for (SucursalEntidad sucursal : sucursales) {
            comboSucursales.addItem(sucursal);
        }
        panelSucursal.add(comboSucursales);

        // Selector de Fecha de Inicio
        JPanel panelFechaInicio = new JPanel();
        panelFechaInicio.add(new JLabel("Fecha de Inicio:"));
        datePickerInicio = new DatePicker();
        panelFechaInicio.add(datePickerInicio);

        // Selector de Fecha de Fin
        JPanel panelFechaFin = new JPanel();
        panelFechaFin.add(new JLabel("Fecha de Fin:"));
        datePickerFin = new DatePicker();
        panelFechaFin.add(datePickerFin);

        // Botón para generar el reporte
        btnGenerarReporte = new JButton("Generar Reporte en PDF");
        btnGenerarReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SucursalEntidad sucursalSeleccionada = (SucursalEntidad) comboSucursales.getSelectedItem();
                LocalDate fechaInicio = datePickerInicio.getDate();
                LocalDate fechaFin = datePickerFin.getDate();
                if (sucursalSeleccionada != null && fechaInicio != null && fechaFin != null) {
                    generarReportePDF(sucursalSeleccionada, fechaInicio, fechaFin);
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona una sucursal y ambas fechas.");
                }
            }
        });

        // Agregar todo al frame
        add(panelSucursal);
        add(panelFechaInicio);
        add(panelFechaFin);
        add(btnGenerarReporte);
        setVisible(true);
    }

    // Método para generar el reporte en PDF
    private void generarReportePDF(SucursalEntidad sucursal, LocalDate fechaInicio, LocalDate fechaFin) {
        Document documento = new Document() {
            @Override
            public int getLength() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void addDocumentListener(DocumentListener listener) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void removeDocumentListener(DocumentListener listener) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void addUndoableEditListener(UndoableEditListener listener) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void removeUndoableEditListener(UndoableEditListener listener) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Object getProperty(Object key) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void putProperty(Object key, Object value) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void remove(int offs, int len) throws BadLocationException {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public String getText(int offset, int length) throws BadLocationException {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void getText(int offset, int length, Segment txt) throws BadLocationException {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Position getStartPosition() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Position getEndPosition() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Position createPosition(int offs) throws BadLocationException {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Element[] getRootElements() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Element getDefaultRootElement() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void render(Runnable r) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        };

        try {
            String nombreArchivo = "Reporte_Sucursales_" + sucursal.getNombre() + ".pdf";
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));

            documento.open();

            // Título del reporte
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("Reporte de Sucursal: " + sucursal.getNombre(), fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            // Información del filtro
            Paragraph infoFechas = new Paragraph("Periodo: " + fechaInicio + " a " + fechaFin);
            infoFechas.setSpacingBefore(20);
            documento.add(infoFechas);

            // Tabla con la información de las sucursales filtradas
            PdfPTable tabla = new PdfPTable(3); // 3 columnas: Nombre, Dirección, Fecha de Registro
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);

            PdfPCell celdaNombre = new PdfPCell(new Paragraph("Nombre"));
            PdfPCell celdaDireccion = new PdfPCell(new Paragraph("Dirección"));
            PdfPCell celdaFecha = new PdfPCell(new Paragraph("Fecha de Registro"));
            tabla.addCell(celdaNombre);
            tabla.addCell(celdaDireccion);
            tabla.addCell(celdaFecha);

            // Aquí agregarías los datos reales de las sucursales
            // Vamos a simularlo con datos ficticios
            tabla.addCell(sucursal.getNombre());
            tabla.addCell(sucursal.getDireccion());
            tabla.addCell(sucursal.getFechaRegistro().toString());

            documento.add(tabla);

            // Mensaje de éxito
            JOptionPane.showMessageDialog(this, "Reporte generado exitosamente: " + nombreArchivo);

        } catch (DocumentException | IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar el PDF: " + ex.getMessage());
        } finally {
            documento.close();
        }
    }
}

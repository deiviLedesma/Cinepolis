package Presentacion;

import dtos.ClienteDTO;
import dtos.SalaDTO;
import enumerador.ClienteCRUDEnumerador;
import static enumerador.ClienteCRUDEnumerador.ELIMINAR;
import static enumerador.ClienteCRUDEnumerador.MODIFICAR;
import static enumerador.ClienteCRUDEnumerador.NUEVO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import negocio.IClienteService;
import negocio.ISalaService;
import negocio.NegocioException;

public class frmSalaOpcion extends javax.swing.JDialog {

    private ISalaService salaService;
    private ClienteCRUDEnumerador opcionPantalla;
    private int idSala = 0;

    public frmSalaOpcion(java.awt.Frame parent,
            ISalaService salaService,
            ClienteCRUDEnumerador opcionPantalla,
            int id) {
        super(parent, true);
        initComponents();

        this.salaService = salaService;
        this.opcionPantalla = opcionPantalla;
        this.idSala = id;
        setLocationRelativeTo(parent);

        try {
            this.configuracionPantalla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Información", JOptionPane.ERROR_MESSAGE);
            new javax.swing.Timer(100, e -> this.dispose()).start();
        }
    }

    private void configuracionPantalla() throws Exception {
        switch (this.opcionPantalla) {
            case NUEVO ->
                this.opcionNuevo();
            case MODIFICAR ->
                this.opcionModificar();
            case ELIMINAR ->
                this.opcionEliminar();
        }
    }

    private void opcionNuevo() {
        this.setTitle("Nuevo registro de cliente");
        this.btnAccion.setText("Guardar");
        this.habilitarControlesFormulario(true);
        txtSucursal.enable(true);
        this.idSala = 0;
    }

    private void opcionModificar() throws Exception {
        if (this.idSala <= 0) {
            throw new Exception("La clave recibida no es valida");
        }
        this.setTitle("Modificar cliente");
        this.btnAccion.setText("Modificar");
        SalaDTO sala = this.obtenerSala();
        this.IngresarValoresAlFormulario(sala);
        this.habilitarControlesFormulario(true);
    }

    private void opcionEliminar() throws Exception {
        if (this.idSala <= 0) {
            throw new Exception("La clave recibida no es valida");
        }
        this.setTitle("Eliminar cliente");
        this.btnAccion.setText("Eliminar");
        SalaDTO sala = this.obtenerSala();
        this.IngresarValoresAlFormulario(sala);
        this.habilitarControlesFormulario(false);
    }

    private void habilitarControlesFormulario(boolean opcion) {
        txtNombre.setEnabled(opcion);
        txtAsientos.setEnabled(opcion);
        txtLimpieza.setEnabled(opcion);
        
    }

    private SalaDTO obtenerSala() throws Exception {
        try {
            if (this.idSala <= 0) {
                throw new IllegalArgumentException("La clave del cliente no es válida para su búsqueda.");
            }
            SalaDTO sala = this.salaService.buscarPorId(this.idSala);
            return sala;
        } catch (NegocioException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void IngresarValoresAlFormulario(SalaDTO sala) {
        
        txtNombre.setText(sala.getNombre());
        txtAsientos.setText(String.valueOf(sala.getAsientosDisponibles()));
        txtLimpieza.setText(String.valueOf(sala.getTiempoLimpiezaEnMinutos()));
        txtPrecio.setText(String.valueOf(sala.getPrecioActual()));
    }

    private void validarCampos() throws NegocioException {
        String nombre = txtNombre.getText();
        int asientos = Integer.valueOf(txtAsientos.getText());
        double precio =Float.valueOf(txtPrecio.getText());
        if (nombre == null || nombre.isEmpty() || nombre.length() > 50) {
            throw new NegocioException("El nombre no debe estar en blanco y tampoco debe de pasar los 50 caracteres.");
        }
        if (precio <= 0) {
            throw new NegocioException("El precio no puede ser de 0 o menos.");
        }
        if (asientos < 0) {
            throw new NegocioException("Los asientos no pueden ser negativos.");
        }
    }

    private SalaDTO obtenerSalaGuardarDTO() {
        
        
        String nombre = txtNombre.getText();
        int asientos = Integer.valueOf(txtAsientos.getText());
        int limpieza = Integer.valueOf(txtLimpieza.getText());
        double precio = Float.valueOf(txtPrecio.getText());
        int sucursal = Integer.valueOf(txtSucursal.getText());
        return new SalaDTO(0,nombre,asientos,limpieza,(float)precio,sucursal);
    }

    private void guardarSala() throws SQLException {
        try {
            this.validarCampos();
            
            SalaDTO salaGuardar = this.obtenerSalaGuardarDTO();
            this.salaService.guardar(salaGuardar);
            JOptionPane.showMessageDialog(this, "El cliente de nombre " + salaGuardar.getNombre() + " fue guardado", "Información", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Información", JOptionPane.ERROR_MESSAGE);
        }
    }

    private SalaDTO obtenerSalaModificarDTO() {
        String nombre = txtNombre.getText();
        int asientos = Integer.valueOf(txtAsientos.getText());
        int limpieza = Integer.valueOf(txtLimpieza.getText());
        double precio = Float.valueOf(txtPrecio.getText());
        
        try {
            SalaDTO sala = this.salaService.buscarPorId(this.idSala);
            sala.setNombre(nombre);
            sala.setAsientosDisponibles(asientos);
            sala.setPrecioActual((float)precio);
            sala.setTiempoLimpiezaEnMinutos(limpieza);
            return sala;
        } catch (NegocioException ex) {
            Logger.getLogger(frmSalaOpcion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(frmSalaOpcion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    private void modificarSala(){
        try {
            this.validarCampos();
            SalaDTO salaModificar = this.obtenerSalaModificarDTO();
            this.salaService.modificar(salaModificar);
            JOptionPane.showMessageDialog(this, "El cliente fue modificado", "Información", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Información", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(frmSalaOpcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarSala() {
        try {
            this.salaService.eliminar(this.idSala);
            JOptionPane.showMessageDialog(this, "El cliente fue eliminado", "Información", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Información", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAccion = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblNota = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblAsientos = new javax.swing.JLabel();
        txtAsientos = new javax.swing.JTextField();
        lblLimpieza = new javax.swing.JLabel();
        txtLimpieza = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        lblPrecio = new javax.swing.JLabel();
        lblSucursal = new javax.swing.JLabel();
        txtSucursal = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nuevo empleado");
        setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        setMinimumSize(new java.awt.Dimension(320, 347));
        setResizable(false);

        btnAccion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAccion.setText("Accion");
        btnAccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccionActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblNota.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNota.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNota.setText("* Significan campos obligatorios");
        lblNota.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblAsientos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblAsientos.setText("Asientos *");

        txtAsientos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblLimpieza.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblLimpieza.setText("Tiempo de limpeza*");

        txtLimpieza.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNombre.setText("Nombre *");

        txtPrecio.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblPrecio.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPrecio.setText("Precio*");

        lblSucursal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSucursal.setText("Sucursal*");

        txtSucursal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSucursal.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                            .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblLimpieza, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                            .addComponent(txtLimpieza)
                            .addComponent(lblAsientos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAsientos)
                            .addComponent(btnAccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(lblNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAsientos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAsientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblLimpieza)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLimpieza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPrecio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblSucursal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(lblNota)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAccion)
                    .addComponent(btnCancelar))
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccionActionPerformed
        switch (this.opcionPantalla) {
            case NUEVO ->
            {
                try {
                    this.guardarSala();
                } catch (SQLException ex) {
                    Logger.getLogger(frmSalaOpcion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            case MODIFICAR ->
                this.modificarSala();
            case ELIMINAR ->
                this.eliminarSala();
        }
    }//GEN-LAST:event_btnAccionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccion;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel lblAsientos;
    private javax.swing.JLabel lblLimpieza;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNota;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblSucursal;
    private javax.swing.JTextField txtAsientos;
    private javax.swing.JTextField txtLimpieza;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtSucursal;
    // End of variables declaration//GEN-END:variables
}

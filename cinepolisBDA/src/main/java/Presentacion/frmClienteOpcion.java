package Presentacion;

import dtos.ClienteDTO;
import enumerador.ClienteCRUDEnumerador;
import static enumerador.ClienteCRUDEnumerador.ELIMINAR;
import static enumerador.ClienteCRUDEnumerador.MODIFICAR;
import static enumerador.ClienteCRUDEnumerador.NUEVO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import negocio.IClienteService;
import negocio.NegocioException;

public class frmClienteOpcion extends javax.swing.JDialog {

    private IClienteService clienteService;
    private ClienteCRUDEnumerador opcionPantalla;
    private int idCliente = 0;

    public frmClienteOpcion(java.awt.Frame parent,
            IClienteService clienteNegocio,
            ClienteCRUDEnumerador opcionPantalla,
            int idCliente) {
        super(parent, true);
        initComponents();

        this.clienteService = clienteNegocio;
        this.opcionPantalla = opcionPantalla;
        this.idCliente = idCliente;
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
        this.idCliente = 0;
    }

    private void opcionModificar() throws Exception {
        if (this.idCliente <= 0) {
            throw new Exception("La clave recibida no es valida");
        }
        this.setTitle("Modificar cliente");
        this.btnAccion.setText("Modificar");
        ClienteDTO cliente = this.obtenerCliente();
        this.IngresarValoresAlFormulario(cliente);
        this.habilitarControlesFormulario(true);
    }

    private void opcionEliminar() throws Exception {
        if (this.idCliente <= 0) {
            throw new Exception("La clave recibida no es valida");
        }
        this.setTitle("Eliminar cliente");
        this.btnAccion.setText("Eliminar");
        ClienteDTO cliente = this.obtenerCliente();
        this.IngresarValoresAlFormulario(cliente);
        this.habilitarControlesFormulario(false);
    }

    private void habilitarControlesFormulario(boolean opcion) {
        txtNombres.setEnabled(opcion);
        txtApellidoPaterno.setEnabled(opcion);
        txtApellidoMaterno.setEnabled(opcion);
    }

    private ClienteDTO obtenerCliente() throws Exception {
        try {
            if (this.idCliente <= 0) {
                throw new IllegalArgumentException("La clave del cliente no es válida para su búsqueda.");
            }
            ClienteDTO cliente = this.clienteService.buscarPorId(this.idCliente);
            return cliente;
        } catch (NegocioException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void IngresarValoresAlFormulario(ClienteDTO cliente) {
        txtNombres.setText(cliente.getNombres());
        txtApellidoPaterno.setText(cliente.getApellidoPaterno());
        txtApellidoMaterno.setText(cliente.getApellidoMaterno());
    }

    private void validarCampos() throws NegocioException {
        String nombre = txtNombres.getText();
        String apellidoPaterno = txtApellidoPaterno.getText();
        String apellidoMaterno = txtApellidoMaterno.getText();
        if (nombre == null || nombre.isEmpty() || nombre.length() > 50) {
            throw new NegocioException("El nombre no debe estar en blanco y tampoco debe de pasar los 50 caracteres.");
        }
        if (apellidoPaterno == null || apellidoPaterno.isEmpty() || apellidoPaterno.length() > 50) {
            throw new NegocioException("El apellido paterno no debe estar en blanco y tampoco debe de pasar los 50 caracteres.");
        }
        if (apellidoMaterno == null || apellidoMaterno.isEmpty() || apellidoMaterno.length() > 50) {
            throw new NegocioException("El apellido materno no debe estar en blanco y tampoco debe de pasar los 50 caracteres.");
        }
    }

    private ClienteDTO obtenerClienteGuardarDTO() {
        String nombre = txtNombres.getText();
        String apellidoPaterno = txtApellidoPaterno.getText();
        String apellidoMaterno = txtApellidoMaterno.getText();
        return new ClienteDTO(nombre, apellidoPaterno, apellidoMaterno);
    }

    private void guardarCliente() throws SQLException {
        try {
            this.validarCampos();
            
            ClienteDTO clienteGuardar = this.obtenerClienteGuardarDTO();
            this.clienteService.guardar(clienteGuardar);
            JOptionPane.showMessageDialog(this, "El cliente de nombre " + clienteGuardar.getNombres() + " fue guardado", "Información", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Información", JOptionPane.ERROR_MESSAGE);
        }
    }

    private ClienteDTO obtenerClienteModificarDTO() {
        String nombre = txtNombres.getText();
        String apellidoPaterno = txtApellidoPaterno.getText();
        String apellidoMaterno = txtApellidoMaterno.getText();
        
        try {
            ClienteDTO cliente = this.clienteService.buscarPorId(this.idCliente);
            cliente.setNombres(nombre);
            cliente.setApellidoPaterno(apellidoPaterno);
            cliente.setApellidoMaterno(apellidoMaterno);
            return cliente;
        } catch (NegocioException ex) {
            Logger.getLogger(frmClienteOpcion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    private void modificarCliente() {
        try {
            this.validarCampos();
            ClienteDTO clienteModificar = this.obtenerClienteModificarDTO();
            this.clienteService.modificar(clienteModificar);
            JOptionPane.showMessageDialog(this, "El cliente fue modificado", "Información", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Información", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCliente() {
        try {
            this.clienteService.eliminar(this.idCliente);
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
        txtNombres = new javax.swing.JTextField();
        lblApellidoPaterno = new javax.swing.JLabel();
        txtApellidoPaterno = new javax.swing.JTextField();
        lblApellidoMaterno = new javax.swing.JLabel();
        txtApellidoMaterno = new javax.swing.JTextField();
        lblNombres1 = new javax.swing.JLabel();

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

        txtNombres.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblApellidoPaterno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblApellidoPaterno.setText("Apellido paterno *");

        txtApellidoPaterno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblApellidoMaterno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblApellidoMaterno.setText("Apellido materno *");

        txtApellidoMaterno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblNombres1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNombres1.setText("Nombres *");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblNota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNombres, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                        .addComponent(lblNombres1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblApellidoMaterno, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                        .addComponent(txtApellidoMaterno)
                        .addComponent(lblApellidoPaterno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtApellidoPaterno)
                        .addComponent(btnAccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(lblNombres1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblApellidoPaterno)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblApellidoMaterno)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNota)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAccion)
                    .addComponent(btnCancelar))
                .addContainerGap(21, Short.MAX_VALUE))
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
                    this.guardarCliente();
                } catch (SQLException ex) {
                    Logger.getLogger(frmClienteOpcion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            case MODIFICAR ->
                this.modificarCliente();
            case ELIMINAR ->
                this.eliminarCliente();
        }
    }//GEN-LAST:event_btnAccionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccion;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel lblApellidoMaterno;
    private javax.swing.JLabel lblApellidoPaterno;
    private javax.swing.JLabel lblNombres1;
    private javax.swing.JLabel lblNota;
    private javax.swing.JTextField txtApellidoMaterno;
    private javax.swing.JTextField txtApellidoPaterno;
    private javax.swing.JTextField txtNombres;
    // End of variables declaration//GEN-END:variables
}

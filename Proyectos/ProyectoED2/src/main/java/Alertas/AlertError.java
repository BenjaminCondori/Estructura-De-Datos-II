package Alertas;

import java.awt.Color;
import java.awt.Cursor;
import static java.awt.Frame.HAND_CURSOR;

public class AlertError extends javax.swing.JDialog {

    int xMouse, yMouse;
    
    public AlertError(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        btn_aceptar.setCursor(new Cursor(HAND_CURSOR));
        setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRound1 = new button.PanelRound();
        icono = new javax.swing.JLabel();
        titulo = new javax.swing.JLabel();
        mensaje = new javax.swing.JLabel();
        panelRound2 = new button.PanelRound();
        btn_aceptar = new button.MyButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(382, 357));
        setUndecorated(true);

        panelRound1.setBackground(new java.awt.Color(255, 255, 255));
        panelRound1.setMinimumSize(new java.awt.Dimension(382, 357));
        panelRound1.setRoundBottomLeft(25);
        panelRound1.setRoundBottomRight(25);
        panelRound1.setRoundTopLeft(25);
        panelRound1.setRoundTopRight(25);
        panelRound1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelRound1MouseDragged(evt);
            }
        });
        panelRound1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelRound1MousePressed(evt);
            }
        });

        icono.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N

        titulo.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("ALERT ERROR");

        mensaje.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        mensaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mensaje.setText("Mensaje del error");
        mensaje.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        panelRound2.setBackground(new java.awt.Color(153, 0, 0));
        panelRound2.setPreferredSize(new java.awt.Dimension(0, 72));
        panelRound2.setRoundBottomLeft(25);
        panelRound2.setRoundBottomRight(25);
        panelRound2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_aceptar.setBorder(null);
        btn_aceptar.setForeground(new java.awt.Color(153, 0, 0));
        btn_aceptar.setText("ACEPTAR");
        btn_aceptar.setBorderColor(new java.awt.Color(153, 0, 0));
        btn_aceptar.setBorderPainted(false);
        btn_aceptar.setColorClick(new java.awt.Color(255, 255, 255));
        btn_aceptar.setColorOver(new java.awt.Color(255, 255, 255));
        btn_aceptar.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        btn_aceptar.setRadius(10);
        btn_aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_aceptarActionPerformed(evt);
            }
        });
        panelRound2.add(btn_aceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 8, 360, 55));

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(icono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(icono)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_aceptarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_aceptarActionPerformed

    private void panelRound1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound1MousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_panelRound1MousePressed

    private void panelRound1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound1MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_panelRound1MouseDragged

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AlertError.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlertError.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlertError.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlertError.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AlertError dialog = new AlertError(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private button.MyButton btn_aceptar;
    private javax.swing.JLabel icono;
    public static javax.swing.JLabel mensaje;
    private button.PanelRound panelRound1;
    private button.PanelRound panelRound2;
    public static javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}

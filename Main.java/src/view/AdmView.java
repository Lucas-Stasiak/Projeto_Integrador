/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

/**
 *
 * @author Th3Br
 */
public class AdmView extends javax.swing.JFrame {

    UsuarioPanel usuarioPane = new UsuarioPanel();
    PrincipalMenuPane menuPrincipal = new PrincipalMenuPane();
    ProdutoPane produtoPane = new ProdutoPane();
    
    public AdmView() {
        this.setUndecorated(true);   //fullscreen
      
        initComponents();
        this.setExtendedState(AdmView.MAXIMIZED_BOTH);//resolução do monitor
        
        MainPane.add(usuarioPane);
        MainPane.add(menuPrincipal);
        MainPane.add(produtoPane);
     
        menuPrincipal.setVisible(true);
        usuarioPane.setVisible(false);
        produtoPane.setVisible(false);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BotaoAbrirPaneUsuario = new javax.swing.JButton();
        CampoAbrirPaneProduto = new javax.swing.JButton();
        BotaoAbrirPaneClientes = new javax.swing.JButton();
        BotaoAbrirPaneHistorico = new javax.swing.JButton();
        BotaoAbrirPaneVenda = new javax.swing.JButton();
        MainPane = new javax.swing.JLayeredPane();
        BotaoAbrirPaneHome = new javax.swing.JButton();
        BotaoFecharSistema = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        BotaoAbrirPaneUsuario.setText("Usuarios");
        BotaoAbrirPaneUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoAbrirPaneUsuarioActionPerformed(evt);
            }
        });

        CampoAbrirPaneProduto.setText("Produtos");
        CampoAbrirPaneProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CampoAbrirPaneProdutoActionPerformed(evt);
            }
        });

        BotaoAbrirPaneClientes.setText("Clientes");
        BotaoAbrirPaneClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoAbrirPaneClientesActionPerformed(evt);
            }
        });

        BotaoAbrirPaneHistorico.setText("Histórico de vendas");

        BotaoAbrirPaneVenda.setText("Iniciar uma venda");

        MainPane.setLayout(new java.awt.CardLayout());

        BotaoAbrirPaneHome.setText("Home");
        BotaoAbrirPaneHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoAbrirPaneHomeActionPerformed(evt);
            }
        });

        BotaoFecharSistema.setText("X");
        BotaoFecharSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoFecharSistemaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BotaoAbrirPaneUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CampoAbrirPaneProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BotaoAbrirPaneClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BotaoAbrirPaneHistorico, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(BotaoAbrirPaneVenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BotaoAbrirPaneHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(106, 106, 106)
                .addComponent(MainPane, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                .addGap(169, 169, 169))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BotaoFecharSistema)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BotaoFecharSistema)
                .addGap(141, 141, 141)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BotaoAbrirPaneHome, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BotaoAbrirPaneVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BotaoAbrirPaneUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CampoAbrirPaneProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BotaoAbrirPaneClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BotaoAbrirPaneHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(MainPane))
                .addGap(58, 58, 58))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotaoAbrirPaneUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoAbrirPaneUsuarioActionPerformed
        usuarioPane.setVisible(true);
        menuPrincipal.setVisible(false);
        produtoPane.setVisible(false);
    }//GEN-LAST:event_BotaoAbrirPaneUsuarioActionPerformed

    private void CampoAbrirPaneProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CampoAbrirPaneProdutoActionPerformed
        usuarioPane.setVisible(false);
        menuPrincipal.setVisible(false);
        produtoPane.setVisible(true);
    }//GEN-LAST:event_CampoAbrirPaneProdutoActionPerformed

    private void BotaoAbrirPaneHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoAbrirPaneHomeActionPerformed
        usuarioPane.setVisible(false);
        menuPrincipal.setVisible(true);
        produtoPane.setVisible(false);
    }//GEN-LAST:event_BotaoAbrirPaneHomeActionPerformed

    private void BotaoFecharSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoFecharSistemaActionPerformed
        System.exit(0);
    }//GEN-LAST:event_BotaoFecharSistemaActionPerformed

    private void BotaoAbrirPaneClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoAbrirPaneClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoAbrirPaneClientesActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(AdmView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdmView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdmView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdmView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdmView().setVisible(true);
            }
        });
    } 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotaoAbrirPaneClientes;
    private javax.swing.JButton BotaoAbrirPaneHistorico;
    private javax.swing.JButton BotaoAbrirPaneHome;
    private javax.swing.JButton BotaoAbrirPaneUsuario;
    private javax.swing.JButton BotaoAbrirPaneVenda;
    private javax.swing.JButton BotaoFecharSistema;
    private javax.swing.JButton CampoAbrirPaneProduto;
    private javax.swing.JLayeredPane MainPane;
    // End of variables declaration//GEN-END:variables

}
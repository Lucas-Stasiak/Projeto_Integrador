
package view;

/**
 *
 * @author C2testenovo
 */
public class FuncionarioView extends javax.swing.JFrame {

    public String cpf;
    
    public FuncionarioView() {
        
        initComponents();
        this.setExtendedState(AdmView.MAXIMIZED_BOTH);//resolução do monitor
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BotaoAbrirPaneHome = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        BotaoAbrirPaneVenda = new javax.swing.JButton();
        BotaoAbrirPaneProdutos = new javax.swing.JButton();
        BotaoAbrirPaneSuasVendas = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1920, 1080));
        setSize(new java.awt.Dimension(1920, 1080));
        getContentPane().setLayout(null);

        BotaoAbrirPaneHome.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        BotaoAbrirPaneHome.setText("Home");
        BotaoAbrirPaneHome.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        BotaoAbrirPaneHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoAbrirPaneHomeActionPerformed(evt);
            }
        });
        getContentPane().add(BotaoAbrirPaneHome);
        BotaoAbrirPaneHome.setBounds(80, 30, 150, 37);

        jLayeredPane1.setMaximumSize(new java.awt.Dimension(1570, 1000));
        jLayeredPane1.setMinimumSize(new java.awt.Dimension(1570, 1000));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/img2.jpg"))); // NOI18N
        jLabel1.setMaximumSize(new java.awt.Dimension(1570, 1000));
        jLabel1.setMinimumSize(null);
        jLabel1.setPreferredSize(new java.awt.Dimension(1570, 1000));
        jLayeredPane1.add(jLabel1);
        jLabel1.setBounds(0, 0, 1570, 940);

        getContentPane().add(jLayeredPane1);
        jLayeredPane1.setBounds(310, 30, 1570, 1000);

        BotaoAbrirPaneVenda.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        BotaoAbrirPaneVenda.setText("Iniciar uma Venda");
        BotaoAbrirPaneVenda.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        BotaoAbrirPaneVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoAbrirPaneVendaActionPerformed(evt);
            }
        });
        getContentPane().add(BotaoAbrirPaneVenda);
        BotaoAbrirPaneVenda.setBounds(80, 70, 150, 37);

        BotaoAbrirPaneProdutos.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        BotaoAbrirPaneProdutos.setText("Produtos");
        BotaoAbrirPaneProdutos.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        getContentPane().add(BotaoAbrirPaneProdutos);
        BotaoAbrirPaneProdutos.setBounds(80, 110, 150, 36);

        BotaoAbrirPaneSuasVendas.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        BotaoAbrirPaneSuasVendas.setText("Suas Vendas");
        BotaoAbrirPaneSuasVendas.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        BotaoAbrirPaneSuasVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoAbrirPaneSuasVendasActionPerformed(evt);
            }
        });
        getContentPane().add(BotaoAbrirPaneSuasVendas);
        BotaoAbrirPaneSuasVendas.setBounds(80, 150, 150, 37);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/img2.jpg"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 1920, 1080);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    private void BotaoAbrirPaneHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoAbrirPaneHomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoAbrirPaneHomeActionPerformed

    private void BotaoAbrirPaneVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoAbrirPaneVendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoAbrirPaneVendaActionPerformed

    private void BotaoAbrirPaneSuasVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoAbrirPaneSuasVendasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoAbrirPaneSuasVendasActionPerformed

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
            java.util.logging.Logger.getLogger(FuncionarioView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FuncionarioView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FuncionarioView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FuncionarioView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FuncionarioView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotaoAbrirPaneHome;
    private javax.swing.JButton BotaoAbrirPaneProdutos;
    private javax.swing.JButton BotaoAbrirPaneSuasVendas;
    private javax.swing.JButton BotaoAbrirPaneVenda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables
}

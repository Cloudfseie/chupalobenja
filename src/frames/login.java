/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;
import frames.Venta;
import funciones.conectar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author buba
 */
public class login extends javax.swing.JFrame {

    /**
     * Creates new form login
     */
    public login() {
        initComponents();
        
        this.usuario.requestFocus();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(login.EXIT_ON_CLOSE);
    }
 void acceder (String usuario,String pass) 
{   
    String usu = usuario;
    String pas=pass;
    
    String tipoUsuario="";
    String sql="select * from usuario inner join tipo_empleado on id_type = id_tipo where usuario ='"+usu+"'  and contraseña='"+pas+"'";
    
    
    try{
         Statement st=cn.createStatement();
         ResultSet rs= st.executeQuery(sql);
         
         while(rs.next()){
             tipoUsuario=rs.getString("nombre_cargo");
         }
         
         if(tipoUsuario.equals("administrador") || tipoUsuario.equals("jefe"))
         {
             this.setVisible(false);
             JOptionPane.showMessageDialog(null, "¡Bienvenido!");
             menu_adminjefe obj= new menu_adminjefe();
             obj.setVisible(true);
             dispose();
             
            
         }
          if(tipoUsuario.equals("empleado"))
         {
             this.setVisible(false);
             JOptionPane.showMessageDialog(null, "¡Bienvenido! ");
             Venta obj=new Venta();
             
             //Cambie la vista empleado ya que su finalidad principal es vender asi que lo tire directo a 
             //el frame de ventas
             //menu_empleado obj= new menu_empleado();
             obj.setVisible(true);
             Venta.NombreEmpleado.setText(usu);
             dispose();
         }
          if(!tipoUsuario.equals("empleado") && !tipoUsuario.equals("jefe")&& !tipoUsuario.equals("administrador")){
            JOptionPane.showMessageDialog(this, "Usuario o contraseñano no encontrado");
    }
         
         
         
        
    }
    catch(SQLException ex)
    {
        JOptionPane.showMessageDialog(null,ex,"ERROR DE CONEXION",JOptionPane.ERROR_MESSAGE);
        
    }
    
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usuario = new javax.swing.JTextField();
        ingresar = new javax.swing.JButton();
        contrasenia = new javax.swing.JPasswordField();
        btn_salir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MINIMARKET_OBRAENPROGRESO");

        ingresar.setText("ingresar");
        ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ingresarActionPerformed(evt);
            }
        });

        btn_salir.setText("salir");
        btn_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salirActionPerformed(evt);
            }
        });

        jLabel1.setText("usuario:");

        jLabel2.setText("contraseña:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(ingresar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usuario, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                            .addComponent(contrasenia))))
                .addContainerGap(107, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(25, 25, 25)
                .addComponent(ingresar)
                .addGap(20, 20, 20)
                .addComponent(btn_salir))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ingresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ingresarActionPerformed
         String user=this.usuario.getText();
                 
        
        char[] pass=this.contrasenia.getPassword();
        String pa="";
        for(int i=0;i<pass.length;i++)
        {
            pa+=pass[i];
        }
        if("".equals(user)){
            JOptionPane.showMessageDialog(null,"Ingrese Usuario");
        }
        else if(pa==""){
            JOptionPane.showMessageDialog(null,"Ingrese Contraseña");
        }
  
        else{
        //Cargarusuario(user,pass);
        acceder(user,pa);}
    }//GEN-LAST:event_ingresarActionPerformed

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
        dispose();
        JOptionPane.showMessageDialog(null,"Hasta Pronto"); 
        System.exit(0);
    }//GEN-LAST:event_btn_salirActionPerformed

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
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_salir;
    private javax.swing.JPasswordField contrasenia;
    private javax.swing.JButton ingresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField usuario;
    // End of variables declaration//GEN-END:variables
conectar cc = new conectar();
Connection cn= cc.conexion();

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;
import funciones.conectar;
import java.sql.Connection;
import java.sql.ResultSet;


import java.awt.Color;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Cloud
 */
public class Venta extends javax.swing.JFrame {

    int total=0;
    String fila[][]={};
    String columna[]={"Id","Nombre","Cantidad","Precio","Tipo"};
    String cod_igo="";
    String coi="";
    String f[][]={};
    String c[]={"Id","Nombre","Cantidad","Precio","Tipo"};
    DefaultTableModel Compra,limpiar;
    
   
    /**
     * Creates new form Venta
     */
    public Venta() {
        initComponents();
        this.getContentPane().setBackground(Color.orange);
        llenarpaneles();
        Compra= new DefaultTableModel(fila,columna);
        limpiar= new DefaultTableModel(f,c);
        this.tablacomprar.setModel(Compra);
        filasmostrar();
        filascomprar();
        textTotal.setText(String.valueOf(total));
        BloquearTextField();
        
        
        
        
        
        
        
    }
    //Bloquea la edicion de los textfield
    private void BloquearTextField(){
        NombreEmpleado.setEditable(false);
        NombreEmpleado.setEnabled(false);
        textTotal.setEnabled(false);
        textTotal.setEnabled(false);
    }
    //Con esto al seleccionar alguna cosa de las tablas
    //seleccionaremos la fila completa
    private void filasmostrar(){
        tablamostrar.setRowSelectionAllowed(true);
        tablamostrar.setColumnSelectionAllowed(false);
    }
    private void filascomprar(){
        tablacomprar.setRowSelectionAllowed(true);
        tablacomprar.setColumnSelectionAllowed(false);
    }
    
    private void PrepararVenta(){
        DefaultTableModel modelo= new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Tipo");
        
        tablacomprar.setModel(modelo);
    }
    private void Eliminar(){
        String Precio, Claudia;
        
        DefaultTableModel model = (DefaultTableModel) this.tablacomprar.getModel();
        if(tablacomprar.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null,"seleccione un producto","advertencia",JOptionPane.WARNING_MESSAGE);
        }
        else{
            Precio=String.valueOf(model.getValueAt(tablacomprar.getSelectedRow(),3));
            int calcu=Integer.parseInt(Precio);
        
            String Subtotal=textTotal.getText();
            int Sub=Integer.parseInt(Subtotal);
            int totala=Sub-calcu;
            String hola=Integer.toString(totala);
            textTotal.setText(hola);
            total=0;
        }
        int[] rows = tablacomprar.getSelectedRows();
        for(int i=0;i<rows.length;i++){
            model.removeRow(rows[i]-i);    
        }

    }
    //buscamos coincidencias en la base de datos con lo que pedimos
    //si tienen el codigo para buscar en el mismo jtable pasenlo ahora
    private void Buscar(String a){
        DefaultTableModel modelo= new DefaultTableModel();
        //buscara en la linea completa hasta encontrar coincidencias 
        String nombre= "%"+a+"%";
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Tipo");
        
        tablamostrar.setModel(modelo);
        
        String[]datos=new String[11];
        
        String sql="call BuscarPorNombreProducto('"+nombre+"')";

        
         try{
            
            Statement st=cc.createStatement();
            
            ResultSet rs= st.executeQuery(sql);
            
            while(rs.next())
            {
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                
                modelo.addRow(datos);
            }
            
         }catch(SQLException e){
         }
            
        }
    //benja este codigo te lo robe
    private void llenarpaneles(){
        
        DefaultTableModel modelo= new DefaultTableModel();
        
        
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Tipo");
            
        tablamostrar.setModel(modelo);
        String[]datos=new String[11];
        
        
        
        
        try{
            Statement st=cc.createStatement();
            ResultSet rs=st.executeQuery("select * from VistaProductos");
            
            while(rs.next())
            {
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                
                modelo.addRow(datos);
                
            }
            
        }catch(SQLException e) {
            
        }
    
    }
    int CantidadMostrar(String codigo){
        int cod=0;
     String sql="select stock.cantidad from stock where id_stock='"+codigo+"'";
     try{
         Statement psr=cc.createStatement();
         ResultSet ps=psr.executeQuery(sql);
         while(ps.next())
         {
             cod=ps.getInt(1);
         }
     }
     catch(SQLException e)
     {
         JOptionPane.showMessageDialog(null, e);
     }
       return cod; 
    }
    
    void agregarCompra(){
        int filaselec=this.tablamostrar.getSelectedRow();
        coi=this.tablamostrar.getValueAt(filaselec, 0).toString();
        int num=this.CantidadMostrar(coi);
   
        try{
            String Id, Nombre, Cantidadt, Precio, Tipo;

            if(filaselec==-1)
            {
                JOptionPane.showMessageDialog(null,"seleccione un producto","advertencia",JOptionPane.WARNING_MESSAGE);
            }
            else{
                String importe="";
                this.tablamostrar.getModel();
                Id= this.tablamostrar.getValueAt(filaselec, 0).toString();
                Nombre=this.tablamostrar.getValueAt(filaselec, 1).toString();
                Tipo=this.tablamostrar.getValueAt(filaselec,4).toString();
                Precio=this.tablamostrar.getValueAt(filaselec, 3).toString();
                Cantidadt=this.Cantidad.getText();
                int cos=Integer.parseInt(Cantidadt);
          
                if(num<cos)
                {
                    JOptionPane.showMessageDialog(null, "Stock insuficiente para el pedido");
                }
                else{
                    int x=Integer.parseInt(Precio)*Integer.parseInt(Cantidadt);
                    importe=String.valueOf(x);
                    this.tablacomprar.getModel();
                    String filas[]={Id,Nombre,Cantidadt,importe,Tipo};
                    
                    Compra.addRow(filas);
                    

                    int calcula=0;
                    calcula+=Integer.parseInt(importe);

                    total= total + calcula;

                    this.textTotal.setText(String.valueOf(total));
                }
            }
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
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

        jLabel1 = new javax.swing.JLabel();
        txtbuscar = new javax.swing.JTextField();
        Buscar = new javax.swing.JButton();
        Agregar = new javax.swing.JButton();
        EliminarBoton = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablamostrar = tablamostrar = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jScrollPane4 = new javax.swing.JScrollPane();
        tablacomprar = tablacomprar = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        txt1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        NombreEmpleado = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Cantidad = new javax.swing.JTextField();
        txtTotal = new javax.swing.JLabel();
        textTotal = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel1.setText("Buscar producto por su nombre:");

        Buscar.setText("Buscar");
        Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarActionPerformed(evt);
            }
        });

        Agregar.setText("Agregar");
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });

        EliminarBoton.setText("Eliminar");
        EliminarBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarBotonActionPerformed(evt);
            }
        });

        jButton4.setText("Pagar");

        tablamostrar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablamostrar.setColumnSelectionAllowed(true);
        tablamostrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablamostrarMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablamostrar);
        tablamostrar.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        tablacomprar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablacomprar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablacomprarMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablacomprar);

        txt1.setText("Total: ");

        jLabel2.setText("Empleado:");

        jLabel3.setText("Cantidad: ");

        txtTotal.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(19, 19, 19)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
                                .addComponent(jScrollPane3))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(EliminarBoton)
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(102, 102, 102))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(Agregar)
                                    .addGap(73, 73, 73)
                                    .addComponent(txt1)
                                    .addGap(18, 18, 18)
                                    .addComponent(textTotal))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(Buscar)
                            .addGap(121, 121, 121)
                            .addComponent(jLabel2)
                            .addGap(18, 18, 18)
                            .addComponent(NombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Buscar)
                    .addComponent(jLabel2)
                    .addComponent(NombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Agregar)
                            .addComponent(txt1)
                            .addComponent(textTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(Cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(EliminarBoton)
                        .addGap(99, 99, 99)
                        .addComponent(jButton4)))
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        agregarCompra();
    }//GEN-LAST:event_AgregarActionPerformed

    private void BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarActionPerformed
    String busca=this.txtbuscar.getText();
    
    Buscar(busca);
    
    }//GEN-LAST:event_BuscarActionPerformed

    private void EliminarBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarBotonActionPerformed
        Eliminar();        
    }//GEN-LAST:event_EliminarBotonActionPerformed

    private void tablamostrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablamostrarMouseClicked
    if (evt.getClickCount()==2){
        agregarCompra();
    }
    }//GEN-LAST:event_tablamostrarMouseClicked

    private void tablacomprarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacomprarMouseClicked
    if(evt.getClickCount()==2){
        Eliminar();
    }
    }//GEN-LAST:event_tablacomprarMouseClicked
               
     
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
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Venta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Agregar;
    private javax.swing.JButton Buscar;
    private javax.swing.JTextField Cantidad;
    private javax.swing.JButton EliminarBoton;
    public static javax.swing.JTextField NombreEmpleado;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tablacomprar;
    private javax.swing.JTable tablamostrar;
    private javax.swing.JTextField textTotal;
    private javax.swing.JLabel txt1;
    private javax.swing.JLabel txtTotal;
    private javax.swing.JTextField txtbuscar;
    // End of variables declaration//GEN-END:variables
conectar cn= new conectar();
Connection cc= cn.conexion();
}


package funciones;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;



public class conectar {
    Connection con=null;
    
    public Connection conexion(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/aa","root","");
        }
        catch(ClassNotFoundException | SQLException e){
            System.out.print(e.getMessage());
        }
       
        
        return con;
    }
    
    
}

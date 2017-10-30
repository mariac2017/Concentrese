package pqt.ConJuego;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException; 

public class ConConex {
	// constructor conexión a bases de datos
	
		// Para que la conexion funcione es necesario 
		// crear el Referenced Libraries con mysql-connector
		// Se debe entrar a propiedades y copiarlo alli
		// Por el menu principal seguir la ruta de :
		// TblLaberintos --> propiedades --> JavaBuildPath --> Add External Jars --> copia MySqlConnect
		
		private String usuario = "root";
		private String pwd = "root";
		private static String db= "db_concentrese";
		static String url="jdbc:mysql://localhost/" + db;
		
		private Connection conn=null;
			
			public ConConex() {
				
				// System.out.println("url " + url);
				try {
					conn = (Connection)DriverManager.getConnection(url, usuario, pwd);
					if (conn != null){
					//	System.out.println("Conecto a " + conn);
					}
				}
				
				catch (SQLException ex){
					System.out.println("fallo la conexion " + conn);
				}

			}
		
			public void desconectar(){
				conn = null;
			}
			
			//Retornar la conexion
			public Connection getConnection(){
				return conn;
			}

			// *********************
			
			public PreparedStatement prepareStatement(String string) {
				// TODO Auto-generated method stub
				return null;
			}
	}

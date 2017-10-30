package pqt.ConJuego;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import pqt.ConJuego.ConConex;
import pqt.ConJuego.ConInicio;
import pqt.ConJuego.ConPpal;
import pqt.ConJuego.ConTema;

import java.awt.Color;
import java.awt.Point;

public class ConInicio extends JFrame {

	private JPanel Tablero;
	private boolean ALLOW_ROW_SELECTION = true;
	
    // variables del tema 
	public Object[][] data = new Object[100][3];
	public JPanel panTema;
	public JTable tableTema;
	public JTextField txtTema;
	public JButton btnTema;
	
	public int wfila = 0;
	public static int wTema = 0;
	public static String wNombre = "";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConInicio frame = new ConInicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	//	 * Create the frame.
	public ConInicio() {
		
		super("ConInicio");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1, 1, 440, 600);
		setTitle("Concentrese");
		Tablero = new JPanel();
		Tablero.setBorder(new EmptyBorder(5, 5, 5, 5));
		Tablero.setLayout(null);
		setContentPane(Tablero);

    	CrearComandos();		
    	CargarTemas();
    	CrearJTableTema();
	}
	
    public void CrearComandos() {
    	
     	JLabel lblTema = new JLabel();
    	lblTema.setText("Tema ");
    	lblTema.setBounds(10, 11, 36, 34);
   		Tablero.add(lblTema);

    	txtTema = new JTextField();
    	txtTema.setSize(194, 34);
    	txtTema.setLocation(new Point(56, 11));
    	txtTema.setBackground(Color.YELLOW);
		Tablero.add(txtTema);
		
		btnTema = new JButton();
		btnTema.setText("Seguir");
		btnTema.setBounds(265, 12, 90, 32);
		btnTema.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Tablero.add(btnTema, 1);

		btnTema.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent arg0) {
				     ConNroFichas ventanaNueva = new ConNroFichas();
					 ventanaNueva.setVisible(true);
					 dispose();
				}
			});
	}

 	public void CrearJTableTema() {
		
		//Crear Tema
		panTema = new JPanel();
		panTema.setBackground(Color.BLACK);
		panTema.setBounds(55, 50, 300, 500);
		Tablero.add(panTema);
	
	 	String[] columnNames = {"Codigo", "Tema"};
        final JTable tableTema = new JTable(data, columnNames);
        tableTema.setPreferredScrollableViewportSize(new Dimension(255, 465));

	    //Crear Dimensiones de la tabla
        //Alto de Filas
	    tableTema.setRowHeight(25);
	    //Ancho de Columnas
        tableTema.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);   
   		TableColumn col_0 = null;
   		col_0 = tableTema.getColumnModel().getColumn(0);
   		col_0.setPreferredWidth(50);
   		TableColumn col_1 = null;
   		col_1 = tableTema.getColumnModel().getColumn(1);
   		col_1.setPreferredWidth(200);
 
        //Crear scroll de la tabla 
        JScrollPane scrollPane = new JScrollPane(tableTema);
        panTema.add(scrollPane);
           
        // Selecciona Fila
        tableTema.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        if (ALLOW_ROW_SELECTION) { // true by default
            ListSelectionModel rowSM = tableTema.getSelectionModel();
            rowSM.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    //Si selecciona fila
                    if (lsm.isSelectionEmpty()) {
                        System.out.println("No rows are selected.");
                    } else {
                    	wfila = lsm.getMinSelectionIndex();
                      	SelectTema();
                    }
                }
            });
            
        } else {
            tableTema.setRowSelectionAllowed(false);
        }
	}

	public void CargarTemas() {

		int fila = 0; 
		try {
			 ConConex conex = new ConConex();
	       	 Statement estatuto = conex.getConnection().createStatement();
			 ResultSet rs = estatuto.executeQuery("SELECT * from tbl_tema ");
			 ConTema tema;
			 
			 	//Obtener todos los registros
			    fila = 0;
				while (rs.next()){
					  tema = new ConTema();
					  tema.settemaCod(Integer.parseInt(rs.getString("temaCod")));
					  tema.settemaNom(rs.getString("temaNom"));
					  
					  if (rs.getString("temaNom") != "*") {
						  data[fila][0] = rs.getInt("temaCod");
					  	  data[fila][1] = rs.getString("temaNom");
					  	  fila = fila + 1;
					  }	  
				}
				rs.close();
				estatuto.close();
				conex.desconectar();
		}	
		catch (SQLException e){
			   System.out.println(e.getMessage());
			   JOptionPane.showMessageDialog(null, "Consulta Fallido");			
		}
}

	public void SelectTema() {

		try {
			wfila = wfila + 1;  // se suma ya que la tabla comienza en 0 y los datos en 1
		    wTema = wfila;
		    
			ConConex conex = new ConConex();
			Statement estatuto = conex.getConnection().createStatement();
			ResultSet rs = estatuto.executeQuery("SELECT * FROM tbl_tema WHERE temaCod = " + wfila);
			ConTema tema;
		  
		 		//Obtener todos los registros
				if(rs.next()); {
					tema = new ConTema();
					tema.settemaCod(Integer.parseInt(rs.getString("temaCod")));
					tema.settemaNom(rs.getString("temaNom"));
					  
					wNombre = rs.getString("temaNom");
					txtTema.setText(wNombre);
				}	  
		}
	
		catch (SQLException e){
			   System.out.println(e.getMessage());
			   JOptionPane.showMessageDialog(null, "Consulta Fallido");			
		}
	
	}
}

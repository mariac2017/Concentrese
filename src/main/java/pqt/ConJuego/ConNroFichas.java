package pqt.ConJuego;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pqt.ConJuego.ConCmpImg;
import pqt.ConJuego.ConConex;
import pqt.ConJuego.ConNroFichas;
import pqt.ConJuego.ConPpal;

public class ConNroFichas extends JFrame {

	public JPanel Tablero;
	public JPanel Tapiz;
	public JLabel lblImagen;
	public JButton btnInicio = new JButton();
	public JButton btnMas    = new JButton();
	public JButton btnMenos  = new JButton();
	public byte[] imagen = null;
	
	public int fila = 0;
	public int colu = 0;
	public static int tama = 0;
	public int lado = 0;
	public int i = 0;
	public int j = 0;
	public int k = 0;
	
 	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConNroFichas frame = new ConNroFichas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


//	 * Create the frame.
	public ConNroFichas() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1, 1, 700, 650);
		setTitle("Concentrese");
		Tablero = new JPanel();
		Tablero.setBorder(new EmptyBorder(5, 5, 5, 5));
		Tablero.setLayout(null);
		setContentPane(Tablero);
		
		tama = 4;
		metLeerLogo();
		metCrearPlano();
		metCrearBotones();
		
	}
	
	public void metLeerLogo()
	{
		int wCodigo = 999;
		
		try {
			ConConex conex = new ConConex();
			Statement estatuto = conex.getConnection().createStatement();
			ResultSet rs = estatuto.executeQuery("SELECT * from tbl_imgs WHERE imgCod = " + wCodigo);
			ConCmpImg campo;
			
			//Obtener imagen
			 if (rs.next()){
					  campo = new ConCmpImg();
					  campo.setImgCod(Integer.parseInt(rs.getString("imgCod")));
					  campo.setImgNom(rs.getString("imgNom"));
					  campo.setImgImg(rs.getBlob("imgImg"));
					  imagen = rs.getBytes("imgImg");
			 }
			 rs.close();
			 estatuto.close();
			 conex.desconectar();
		}
		
		catch (SQLException e){
			   System.out.println(e.getMessage());
			   JOptionPane.showMessageDialog(null, "Lectura de Logo Fallida");			
		}
	}

	public void metCrearBotones() {
		
		//Boton Inicio
    	btnInicio = new JButton();
    	btnInicio.setText("Inicio");
    	btnInicio.setBounds(613, 15, 67, 32);
    	btnInicio.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Tablero.add(btnInicio);

		btnInicio.addActionListener(new ActionListener() {
				  public void actionPerformed(ActionEvent arg0) {
					     ConPpal ventanaNueva = new ConPpal();
						 ventanaNueva.setVisible(true);
						 dispose();
					}
				});
		
		//Boton Más
    	btnMas = new JButton();
    	btnMas.setText("+");
    	btnMas.setBounds(623, 55, 45, 32);
    	btnMas.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Tablero.add(btnMas);

		btnMas.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent arg0) {
		  	    tama = tama + 2;
		  	    if (tama > 10) { tama = 10;};
		  	      metCrearPlano();
			  }
		});

		// Boton Menos
		btnMenos = new JButton();
    	btnMenos.setText("-");
    	btnMenos.setBounds(623, 95, 45, 32);
    	btnMenos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Tablero.add(btnMenos);

		btnMenos.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent arg0) {
		  	      tama = tama - 2;
		  	      if (tama < 2) { tama = 2;};
		  	      metCrearPlano();
		  	  }
	    });
	}
	
	
	public void metCrearPlano()
	{
		  // Se crear el plano de tablero Concentrese	
		  colu = 0;
		  fila = 0;
		  lado = 600/tama;
		  System.out.println("tama " + tama);
		  System.out.println("lado " + lado);
		  
		  Tablero.removeAll();
		  Tablero.repaint();
		  
		  for (i=0; i<tama; i++){
			  fila =  15 + (i*lado);
			  for(j=0; j<tama; j++){
				  colu = 10 + (j*lado);
				  lblImagen = new JLabel();
				  lblImagen.setBounds(colu, fila, lado, lado);
				  ImageIcon icoImag = new ImageIcon(imagen);
			      // ajusta imagen al tamaño 
				  ImageIcon icono = new ImageIcon(icoImag.getImage().getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_DEFAULT));
				  lblImagen.setIcon(icono);
				  Tablero.add(lblImagen);
				  lblImagen.setLayout(null);
				  Tablero.repaint();
			  }
		  }
		  
		  metCrearBotones();
	}

}
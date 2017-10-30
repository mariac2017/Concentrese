package pqt.ConJuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pqt.ConJuego.ConCmpImg;
import pqt.ConJuego.ConConex;

public class ConPpal extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel  Tablero;
	public JButton btnInicio   = new JButton();
	public JButton btnFinal    = new JButton();
	public JButton btnTotal    = new JButton();
	public JButton[] btnImagen = new JButton[100];    //arreglo de botones para cada cuadro
	
	public static JButton btnTiempo = new JButton();  // cuando se muestre continuamente el tiempo
	
	public JPanel contentPane;
	
	
	public int tama = pqt.ConJuego.ConNroFichas.tama;
	public static int wTema = pqt.ConJuego.ConInicio.wTema;;
	public int Vector[] = new int[50];
	public int Tabla[]  = new int[100];
	public int Intento[]  = new int[3];
	public int PosiTbl[]  = new int[3];
	
	public String ficha;
	public int NroIntento;
	public int wCodigo = 0;
	public int wTotImg = 0;
	public int fila = 0;
	public int colu = 0;
	public int lado = 0;
   
	public int x = 0;
	public int y = 0;
	public int i = 0;
	public int j = 0;
	public int k = 0;
	public int m = 0;
	public int p = 0;

    public int no = 0;
    public int wPosi = 0;

	public int i_hra = 0;
	public int i_min = 0;
	public int i_seg = 0;
	public int f_hra = 0;
	public int f_min = 0;
	public int f_seg = 0;
	public int t_hra = 0;
	public int t_min = 0;
	public int t_seg = 0;
	
	public int w_fecha = 0;
	public String w_inicio;
	public String w_final;
	
	public static int seg=0;
	public Integer A = 0; 
	public String  s = A.toString();
	public int NroFicha = 0;
	public int NroCuadros = 0;
	public int NroAciertos = 0;
	
	public byte[] imagen = null;
	public byte[] imgRev = null;
	public byte[] imgFin = null;
	public byte[] imgSeguir = null;
	public byte[] sndGlp = null;
	
	
//	public static boolean congelado;

	public static void main(String[] args)
	       throws InterruptedException {
				try {
					ConPpal frame = new ConPpal();
					frame.setVisible(true);
	       		    

				} catch (Exception e) {
					e.printStackTrace();
			}
	}
	
	//** Create the frame.

	public ConPpal() {

    //  Crear Superficie para incertar el plano SopaDeLetras (lienzo Jpanel llamado Tablero)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1, 1, 725, 670);
		Tablero = new JPanel();
		Tablero.setBorder(new EmptyBorder(1, 1, 1, 1));
		Tablero.setBackground(Color.YELLOW);
		setContentPane(Tablero);
		setTitle("Concentrese");
		Tablero.setLayout(null);
		
		NroIntento = 0;
		NroCuadros = tama * tama;
		metLimpiar();
		metIniciarReloj();
		metLeerImgSeguir();
		metLeerImgReves();
		metLeerImgAcierto();
		metCrearPlanoReves();
		metLeerImgs();
		metCargarMatriz();
		metVerificarMatriz();
	}

//  *******************************************   CREAR JUEGO   ************************	
	
	
// ***********************  PLANO AL REVES ************************************+

	public void metCrearPlanoReves()
	{
		
		System.out.println("Entre a crear plano ");
		
		  // Se crea el plano de tablero Concentrese	
		  colu = 0;
		  fila = 0;
		  lado = 600/tama;
		  System.out.println("tama " + tama);
		  System.out.println("lado " + lado);
	  
		  k = 0;
		  for (i=0; i<tama; i++){
			  fila =  15 + (i*lado);
			  for(j=0; j<tama; j++){
				  
				  Integer A = new Integer(k); 
			      String S = A.toString();
			    	
				  colu = 10 + (j*lado); 
				  btnImagen[k] = new JButton();
				  btnImagen[k].setName(S);
				  btnImagen[k].setBounds(colu, fila, lado, lado);
				  ImageIcon icoImag = new ImageIcon(imgRev);
			      // ajusta imagen al tamaño 
				  ImageIcon icono = new ImageIcon(icoImag.getImage().getScaledInstance(btnImagen[k].getWidth(), btnImagen[k].getHeight(), Image.SCALE_DEFAULT));
				  btnImagen[k].setIcon(icono);
				  btnImagen[k].setLayout(null);
				  Tablero.add(btnImagen[k]);
				  Tablero.repaint();
				  				  
				  // Acción de Ir a Mostrar Ficha
				  ficha = btnImagen[k].getName();
				  btnImagen[k].addActionListener(new ActionListener() {
				  public void actionPerformed(ActionEvent arg0) {
			    	          ficha = (((JButton) arg0.getSource()).getName());    
							  NroFicha = Integer.valueOf(ficha).intValue();
						      System.out.println("Accion Btn Mostrar Ficha  " + NroFicha);
						    	         metMostrarFicha();
						    			 if (NroIntento == 2){
						    			  	 metVerCoincidencia();
						    			 }

					      }
				  });		
				  k = k+1;
			  }
		  }
	}

	// *****************************   MOSTRAR IMAGEN **************************
	
	public void metMostrarFicha() 
	{
		NroIntento = NroIntento + 1;
		System.out.println("Entre a metMostrarFicha NroIntento " + NroIntento);
		
		// Revisar las imagenes ya que tiene las 2 
		if (NroIntento > 2) {metBtnSeguir(); NroIntento = 1; }
		
		// Revisar que no sea la misma casilla 2 veces		
		if ((NroIntento == 2) && (PosiTbl[1] == NroFicha)) {NroIntento = 1;	}
		
		// Revisar que no sea una casilla de acierto 998
		if ((Tabla[NroFicha]) > 900) {NroIntento = NroIntento - 1;	}

		if ((Tabla[NroFicha] < 900)) {
			
			 Intento[NroIntento] = Tabla[NroFicha]; 
			 PosiTbl[NroIntento] = NroFicha;
		     try {
		    	  ConConex conex = new ConConex();
		    	  Statement estatuto = conex.getConnection().createStatement();
		    	  ResultSet rs = estatuto.executeQuery("SELECT * from tbl_imgs WHERE imgCod = " + Tabla[NroFicha]);
				
		    	  // Obtener Imagen
		    	  if (rs.next()){
		    		  imagen = rs.getBytes("imgImg");
		    	  }
		    	  rs.close();
		    	  estatuto.close();
		    	  conex.desconectar();
		     }
		     	  catch (SQLException e){
		     		   System.out.println(e.getMessage());
		     		   JOptionPane.showMessageDialog(null, "Lectura de Ficha Fallida");			
		     	  }

			// Mostrar Ficha
			fila = (((NroFicha)/tama) * lado) + 15;
			colu = ((NroFicha % tama) * lado) + 10; 
		
			// Cargar Imagen al Boton
			btnImagen[NroFicha].setBounds(colu, fila, lado, lado);
			ImageIcon icoImag = new ImageIcon(imagen);
			// ajusta imagen al tamaño 
			ImageIcon icono = new ImageIcon(icoImag.getImage().getScaledInstance(btnImagen[NroFicha].getWidth(), btnImagen[NroFicha].getHeight(), Image.SCALE_DEFAULT));
			btnImagen[NroFicha].setIcon(icono);
			Tablero.add(btnImagen[NroFicha]);
			btnImagen[NroFicha].setLayout(null);
			Tablero.repaint();
		}	
	}

	public void metVerCoincidencia()
	{
		 System.out.println("Entre metVerCoincidencia ");
		 
		 // Ver si hubo Coincidencia
		 System.out.println("Intento[1] " + Intento[1]);
		 System.out.println("Intento[2] " + Intento[2]);
		 
		 if (Intento[1] == Intento[2]) {
			// Sonido Golpe
			File Golpe = new File("Snds/SndGolpe.WAV");
	 		PlaySound(Golpe);
	 		NroAciertos = NroAciertos + 1;
		 }
	}	 

	public void metBtnSeguir()
	{
		System.out.println("Entre metMostrarFicha intento " + NroIntento);

//		if (NroIntento == 3 && Tabla[NroFicha] > 900){
//			NroIntento = 0;
//			metColocarReves();
//		}

		if (NroIntento >= 2){
			NroIntento = 0;
			metColocarReves();
		}
	}

	public void metColocarReves()
	{
		no = 0;
		if (Intento[1] == Intento[2]) {
			imagen = imgFin;
 			no = 1;
 			} else{
			  imagen = imgRev;
		}	
		
		Intento[1] = 0; Intento[2] = 0;
	 	
			for (i=1; i<3; i++) {
	 			// Colocar imagen de acierto o de reves segun caso
	 			j = PosiTbl[i];
	 			fila = ((j/tama) * lado) + 15;
	 			colu = ((j % tama) * lado) + 10;
	 			
	 			// Cargar Imagen al Boton
	 			btnImagen[j].setBounds(colu, fila, lado, lado);
	 			ImageIcon icoImag = new ImageIcon(imagen);
	 			// ajusta imagen al tamaño 
	 			ImageIcon icono = new ImageIcon(icoImag.getImage().getScaledInstance(btnImagen[j].getWidth(), btnImagen[j].getHeight(), Image.SCALE_DEFAULT));
	 			btnImagen[j].setIcon(icono);
	 			Tablero.add(btnImagen[j]);
	 			btnImagen[j].setLayout(null);
	 			Tablero.repaint();
	 			
	 			// Retirar el imagen de vector de intentos
	 			Intento[i] = 991;
	 			if (no == 1) { 
	 	 			Tabla[j] = 998;
	 			}
	 			
	 		}
	 		
	 		//Si ya se Termino
	 		if (NroAciertos == tama){
	 			metFinalizarReloj();
	 			
		 		for (i=1; i<3; i++) {
		 			// Colocar imagen de acierto o de reves segun caso
		 			j = PosiTbl[i];
		 			fila = ((j/tama) * lado) + 15;
		 			colu = ((j % tama) * lado) + 10;
		 			
		 			// Cargar Imagen al Boton
		 			btnImagen[j].setBounds(colu, fila, lado, lado);
		 			ImageIcon icoImag = new ImageIcon(imagen);
		 			// ajusta imagen al tamaño 
		 			ImageIcon icono = new ImageIcon(icoImag.getImage().getScaledInstance(btnImagen[j].getWidth(), btnImagen[j].getHeight(), Image.SCALE_DEFAULT));
		 			btnImagen[j].setIcon(icono);
		 			Tablero.add(btnImagen[j]);
		 			btnImagen[j].setLayout(null);
		 			Tablero.repaint();
		 		}
	 		} 
	}

	
	// ***************************** CARGAR MATRIZ *******************************
	
	public void metCargarMatriz()
	{
		System.out.println("ENTRE A CARGAR MTRIZ tamaño " + tama*tama);
		System.out.println("Numero de Cuadros " + NroCuadros);

		for (m=0; m<((NroCuadros/2)); m++){
			// Randomizar para esocger imagenes
			metRamImagen();

			// colocar primera imagen
			metRamPosicion();
		    Tabla[wPosi] = Vector[wCodigo];
		    
		    // colocar segunda imagen
			metRamPosicion();
		    Tabla[wPosi] = Vector[wCodigo];
        } 
	}
	
	public void metRamImagen()
	{
		//buscar posición de imagen
	   	wCodigo = (int)(Math.random() * (wTotImg) + 1);
		if (wCodigo > wTotImg){ wCodigo = (wCodigo - wTotImg);}
		if (wCodigo > wTotImg){ wCodigo = wTotImg-1;}
		if (wCodigo < 1){ wCodigo = (wTotImg + wCodigo); }
		if (wCodigo <= 0){ wCodigo = 1;}
		
		 // Verificar si ya existe imagen
		no = 0;
		for (i=0; i<(NroCuadros); i++) {
			 if(Tabla[i] == Vector[wCodigo]){
			    no=1; i=NroCuadros+1;
			}
		}
		
		// si imagen existe
		if (no == 1){ 
			//Se recorren imagenes o sea el vector
			for (i=1; i<(wTotImg+1); i++) {
				 // Se recorre tabla
				 int existe = 0;
				 for (p=0; p<NroCuadros; p++) {
					  if(Tabla[p] == Vector[i]){
						 existe = 1;
						 p = NroCuadros+1;
					  }
				 }
				 if (existe == 0) {
					 wCodigo = Vector[i];
					 i = wTotImg + 1;
				  }
			}
		}
	}
	
	public void metRamPosicion()
	{
		//buscar posición de imagen
	   	wPosi = (int)(Math.random() * (NroCuadros - 1) + 1);
		if (wPosi > NroCuadros){ wPosi = (wPosi - NroCuadros);}
		if (wPosi > NroCuadros){ wPosi = NroCuadros-1;}
		if (wPosi < 1){ wPosi = (NroCuadros + wPosi); }
		if (wPosi < 1){ wPosi = 0;}

	     if(Tabla[wPosi] != 999){
	    	for (p=0; p<NroCuadros+1; p++){
	    		if(Tabla[p] == 999) {
	    			wPosi = p;
	    			p = NroCuadros+2;
	    		}
	    	}
        }
	}
	
	public void metVerificarMatriz()
	{
    	 System.out.println(" Entre a Verificar Matriz ");
   	     for (i=0; i<NroCuadros; i++){
    		  if(Tabla[i] > 900) {
    			 System.out.println("ERROR " + i); 	    		
    		  }
    		  System.out.println(" Tabla[i] " + Tabla[i]);
         }
	}
	
	// **************************** Limpiar tabla y vector  ******************************
	
	public void metLimpiar()
	{
		for(i=0;i<50;i++){ 
			Vector[i] = 0;
			Tabla[i] = 999;
			Tabla[i+50] = 999;
		}
		Intento[1]  = 1; Intento[2]  = 2;
	}
	
	// **************************** Cargar Imagenes del tema en Vector ******************************
	
	public void metLeerImgs()
	{
		
		System.out.println("ENTRE A LEER IMAGENES wTema " + wTema);

		try {
			 ConConex conex = new ConConex();
	       	 Statement estatuto = conex.getConnection().createStatement();
	       	 ResultSet rs = estatuto.executeQuery("SELECT * from tbl_imgs WHERE imgTema = " + wTema);
			 ConCmpImg campo;
			 
			 	//Obtener todos los registros
			    fila = 1;
			    while (rs.next()){
					  campo = new ConCmpImg();
					  campo.setImgCod(Integer.parseInt(rs.getString("imgCod")));
					  campo.setImgImg(rs.getBlob("imgImg"));
					  wCodigo = rs.getInt("imgCod"); 
					  imagen = rs.getBytes("imgImg");
					  
					  //Cargar vector con imagenes del tema
					  if ( wCodigo < 900){
					       Vector[fila] = wCodigo;
					       fila = fila + 1;
					       wTotImg = wTotImg +1;
					  }   
				}
			    System.out.println("wTotal imagenes leidas " + wTotImg);
				rs.close();
				estatuto.close();
				conex.desconectar();
		}	
		catch (SQLException e){
			   System.out.println(e.getMessage());
			   JOptionPane.showMessageDialog(null, "Consulta Fallido");			
		}
	}


	// *****************************  Imagenes Reves, Acierto, Siguiente  *******************************

	public void metLeerImgReves()
	{
		// leer logo de la empresa
		int wCodigo = 999;
		System.out.println("Entre a leer img Al reves ConPpal ");
		
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
					  imgRev = rs.getBytes("imgImg");
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


	public void metLeerImgAcierto()
	{
		// leer logo de la empresa
		int wCodigo = 998;
		System.out.println("Entre a leer img Acierto ");
		
		try {
			ConConex conex = new ConConex();
			Statement estatuto = conex.getConnection().createStatement();
			ResultSet rs = estatuto.executeQuery("SELECT * from tbl_imgs WHERE imgCod = " + wCodigo);
			ConCmpImg campo;
			
			//Obtener imagen
			 if (rs.next()){
					  campo = new ConCmpImg();
					  campo.setImgImg(rs.getBlob("imgImg"));
					  imgFin = rs.getBytes("imgImg");
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


	public void metLeerImgSeguir()
	{
		// leer logo de la empresa
		int wCodigo = 997;
		System.out.println("Entre a leer img Seguir ");
		
		try {
			ConConex conex = new ConConex();
			Statement estatuto = conex.getConnection().createStatement();
			ResultSet rs = estatuto.executeQuery("SELECT * from tbl_imgs WHERE imgCod = " + wCodigo);
			ConCmpImg campo;
			
			//Obtener imagen
			 if (rs.next()){
					  campo = new ConCmpImg();
					  campo.setImgImg(rs.getBlob("imgImg"));
					  imgSeguir = rs.getBytes("imgImg");
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

	
	
	// ******************************   Sonidos    *****************************
	

	private void PlaySound(File sound) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
			
			Thread.sleep(clip.getMicrosecondLength()/1000);
		}	catch(Exception e)
		{}	
	}

	// ******************************   Reloj    *****************************
	
	public void metIniciarReloj()
	{
		Date w_fecha=new Date(); 
		System.out.println(w_fecha.toString());
		
		Calendar cal1 = Calendar.getInstance();
		i_hra = cal1.get(Calendar.HOUR_OF_DAY);
		i_min = cal1.get(Calendar.MINUTE);
		i_seg = cal1.get(Calendar.SECOND);

	    Integer x_hra = new Integer(i_hra); 
	    String  s_hra = x_hra.toString();
	    Integer x_min = new Integer(i_min); 
	    String  s_min = x_min.toString();
	    Integer x_seg = new Integer(i_seg); 
	    String  s_seg = x_seg.toString();
		
		w_inicio = s_hra + ":" + s_min + ":" + s_seg;		
		System.out.println(w_inicio);
		
		//Boton Inicio
    	btnInicio = new JButton();
    	btnInicio.setText(w_inicio);
    	btnInicio.setBounds(613, 15, 90, 32);
    	btnInicio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Tablero.add(btnInicio);
		
		//Boton Final
		btnFinal = new JButton();
		btnFinal.setText("*");
		btnFinal.setBounds(613, 50, 90, 32);
		btnFinal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Tablero.add(btnFinal);
		
		//Boton Total
		btnTotal = new JButton();
		btnTotal.setText("*");
		btnTotal.setBounds(613, 85, 90, 32);
		btnTotal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Tablero.add(btnTotal);
		
	}
	
	public void metFinalizarReloj()
	{
		Date w_fecha=new Date(); 
		System.out.println(w_fecha.toString());
		
		Calendar cal1 = Calendar.getInstance();
		f_hra = cal1.get(Calendar.HOUR_OF_DAY);
		f_min = cal1.get(Calendar.MINUTE);
		f_seg = cal1.get(Calendar.SECOND);

	    Integer x_hra = new Integer(f_hra); 
	    String  s_hra = x_hra.toString();
	    Integer x_min = new Integer(f_min); 
	    String  s_min = x_min.toString();
	    Integer x_seg = new Integer(f_seg); 
	    String  s_seg = x_seg.toString();
		
		w_final = s_hra + ":" + s_min + ":" + s_seg;		
		System.out.println(w_final);
		
		btnFinal.setText(w_final);
		btnFinal.setBounds(613, 50, 90, 32);
		btnFinal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnFinal.setBorderPainted(false);
		Tablero.add(btnFinal,1);
		Tablero.setLayout(null);
		
		// total segundos
		
		t_hra = f_hra - i_hra;
		t_min = f_min - i_min -1;
		t_seg = (60 - i_seg) + f_seg;
		if (t_min < 0){t_min=0;}
		t_seg = t_seg + (t_min * 60) + (t_hra * 60 * 60);

		System.out.println("TOTAL SEG " + t_seg);
		
	    Integer x_tot = new Integer(t_seg); 
	    String  s_tot = x_tot.toString();

		btnTotal.setText(s_tot);
		btnTotal.setBounds(613, 85, 90, 32);
		btnTotal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnTotal.setBorderPainted(false);
		Tablero.add(btnTotal,1);
		Tablero.setLayout(null);
	}

	public void metParar()  
	{
		// Dormir la accion por unmomento
	    //    try {
	    //    	  Tablero.repaint();
	    //    	  Thread.sleep(2000);
		// 		} catch (InterruptedException e) {
		// 				 Tablero.repaint();
		//	}
	}
} // final

package pqt.ConJuego;

import java.sql.Blob;

public class ConCmpJug {

	public Integer jugCod;
	public String  jugNom;
	public Blob jugImg; 

	// ********************* CODIGO ************
	
	// Retorna Codigo
	public Integer getJugCod() {
		return jugCod;
	}

	// Muestra Codigo
	public void setJugCod(Integer jugCod) {
		this.jugCod = jugCod;
	}
	
	// ********************* NOMBRE  **********
	
	// Retorna nombre
	public String getJugNom() {
		return jugNom;
	}
	
	// Muestra nombre	
	public void setJugNom(String jugNom){
		this.jugNom = jugNom;
	}
	
	
	// ********************* IMAGEN  **********
	
	// Retorna imagen
	public Blob getJugImg() {
		return jugImg;
    }
	
	// Muestra imagen
	public void setJugImg(Blob jugImg){
		this.jugImg = jugImg;
	}
}

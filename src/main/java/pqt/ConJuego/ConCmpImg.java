package pqt.ConJuego;

import java.sql.Blob;

public class ConCmpImg {


	public Integer imgCod;
	public String  imgNom;
	public Blob imgImg; 

	// ********************* CODIGO ************
	
	// Retorna Codigo
	public Integer getImgCod() {
		return imgCod;
	}

	// Muestra Codigo
	public void setImgCod(Integer imgCod) {
		this.imgCod = imgCod;
	}
	
	// ********************* NOMBRE  **********
	
	// Retorna nombre
	public String getImgNom() {
		return imgNom;
	}
	
	// Muestra nombre	
	public void setImgNom(String imgNom){
		this.imgNom = imgNom;
	}
	
	
	// ********************* IMAGEN  **********
	
	// Retorna imagen
	public Blob getImgImg() {
		return imgImg;
    }
	
	// Muestra imagen	
	public void setImgImg(Blob imgImg){
		this.imgImg = imgImg;
	}
}

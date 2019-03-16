package modelo;

import java.util.ArrayList;

public class EstadoMealy {

	private String nombre;
	private ArrayList<String> trans;
	
	public EstadoMealy(String nombre, String primeraTrans) {
		this.nombre=nombre;
		trans = new ArrayList<String>();
		this.trans.add(primeraTrans);
	}

	public void renombramiento(String nombreAct, String nombreNuev) {
		if(nombre.equals(nombreAct)) nombre = nombreNuev;
		
		for(String transAct: trans) {
			String[] transArr = transAct.split(",");
			if(transArr[1].equals(nombreAct)) {
				transArr[1] = nombreNuev;
				
			}
			
			
		}
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<String> getTrans() {
		return trans;
	}

	public void setTrans(ArrayList<String> trans) {
		this.trans = trans;
	}
	
}

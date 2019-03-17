package modelo;

import java.util.ArrayList;

public class EstadoMealy {

	private String nombre; //Nombre: A
	private ArrayList<String> trans; //Input, Estado al que se dirige, output. Ejemplo: 0,B,1
	private boolean inicial =false;
	
	public EstadoMealy(String nombre, String primeraTrans) {
		if(nombre.equals("q0")) inicial = true;
		this.nombre=nombre;
		trans = new ArrayList<String>();
		this.trans.add(primeraTrans);
	}
	

	public void renombramiento(String nombreAct, String nombreNuev) {
		if(nombre.equals(nombreAct)) nombre = nombreNuev;
		
			for(int i = 0; i<trans.size();i++) {
			String[] transArr = trans.get(i).split(",");
			if(transArr[1].equals(nombreAct)) {
				transArr[1] = nombreNuev;
			}
			
			String transnueva = transArr[0]+","+transArr[1]+","+transArr[2];
			trans.remove(trans.get(i));
			trans.add(i,transnueva);
		}
	}
	
	
	public ArrayList<String> getSetOutputs() {
		ArrayList<String> outputs = new ArrayList<String>();
		for(String transi: trans) {
		String transiArr[] = transi.split(",");
		//INPUT,OUTPUT
		outputs.add(transiArr[0]+","+transiArr[2]);
		
		}
	
		return outputs;
	}
	
	public ArrayList<String> getSetStateGoingTo() {
		ArrayList<String> outputs = new ArrayList<String>();
		for(String transi: trans) {
		String transiArr[] = transi.split(",");
		//INPUT,OUTPUT
		outputs.add(transiArr[1]);
		}
	
		return outputs;
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


	public boolean isInicial() {
		return inicial;
	}


	public void setInicial(boolean inicial) {
		this.inicial = inicial;
	}
	
	
	
}

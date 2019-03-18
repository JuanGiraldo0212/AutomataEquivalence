package modelo;

import java.util.ArrayList;

public class EstadoMealy {

	private String nombre; //Nombre: A
	private ArrayList<String> trans; //Input, Estado al que se dirige, output. Ejemplo: 0,B,1
	private boolean inicial =false;
	
	/**
	 * Inicializa un estado de mealy
	 * @param nombre, una cadena con el nombre del estado
	 * @param primeraTrans, una cadena con la primera transición del estado. Sigue el formato: "Input,State-To-Go,Outpu"
	 */
	public EstadoMealy(String nombre, String primeraTrans) {
		if(nombre.equals("q0")) inicial = true;
		this.nombre=nombre;
		trans = new ArrayList<String>();
		this.trans.add(primeraTrans);
	}
	
	/**
	 * Renombra el estado actual dandole de nombre el parametro nombreNuev, si el parametro nombreAct es igual al nombre del estado. Renombra dentro de las transiciones
	 * todos los estados con nombre igual a nombreAct.
	 * @param nombre, una cadena con el nombre del estado
	 * @param primeraTrans, una cadena con la primera transición del estado. Sigue el formato: "Input,State-To-Go,Outpu"
	 */
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
	
	/**
	 * Devuelve un arraylist de cadenas de todos los outputs en respuesta a sus input respectivos.
	 * @return ArrayList<String> donde cada uno de los elementos contiene el input y la respuesta al input. Se da en el formato: "Input,Output".
	 */
	public ArrayList<String> getSetOutputs() {
		ArrayList<String> outputs = new ArrayList<String>();
		for(String transi: trans) {
		String transiArr[] = transi.split(",");
		//INPUT,OUTPUT
		outputs.add(transiArr[0]+","+transiArr[2]);
		
		}
	
		return outputs;
	}
	
	/**
	 * Se encarga de obtener todos los estados a los que el estado actual puede dirigirse.
	 * @return ArrayList<String> donde cada uno de los elementos contiene un estado al que se dirige dado el input. Se da en el formato: "State-To-Go".
	 */
	
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

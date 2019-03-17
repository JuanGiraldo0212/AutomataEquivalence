package modelo;

import java.util.ArrayList;
import java.util.HashMap;

public class EstadoMoore {
	
	private String nombre;
	private ArrayList<String> trans;
	private String salida;
	

	public EstadoMoore(String nombre, String primeraTrans,String salida) {
		this.nombre=nombre;
		trans = new ArrayList<String>();
		this.trans.add(primeraTrans);
		this.salida=salida;
	}
	
	/**
	 * Renombra los estados repetidos de la maquina
	 * @param map es un hashmap con los estados viejos y los nuevos
	 */
	public void renombramiento(HashMap<String, String> map) {
        

        for(int i=0;i<trans.size();i++) {
        
        	String transAct=trans.get(i);
            String[] transArr = transAct.split(",");
            transArr[1] = map.get(transArr[1]);
            String transnueva = transArr[0]+","+transArr[1];
            trans.remove(transAct);
            trans.add(i,transnueva);
            
        }
        nombre = map.get(nombre);
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
	
	public String getSalida() {
		return salida;
	}
	
	public void setSalida(String salida) {
		this.salida = salida;
	}

}

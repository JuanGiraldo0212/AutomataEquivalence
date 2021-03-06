package modelo;

import java.util.ArrayList;

public class MaquinaMealy {

	private ArrayList<EstadoMealy> estados;
	private ArrayList<String> accesibles= new ArrayList<>();
	private EstadoMealy inicial;
	
	/**
	 * Inicializa la maquina de mealy
	 * @param infoestados una cadena con la informacion de los estados de la maquina
	 */
	public MaquinaMealy(String infoestados) {
	
	estados = new ArrayList<>();
	
	String[] estadosS =infoestados.split(";");
	
	for (int i = 0; i < estadosS.length; i++) {
		
		String [] info =estadosS[i].split(",");
		
		EstadoMealy temp = estadoExiste(info[0]);
		
		if(temp!=null) {
			
			temp.getTrans().add(info[1]+","+info[2]+","+info[3]);
			
		}else{
			
			EstadoMealy state= new EstadoMealy(info[0],info[1]+","+info[2]+","+info[3]);
			
			this.estados.add(state);
			
		}
	  }
	}
	/**
	 * Inicializa la maquina de mealy para suma directa
	 * @param estadosM1, lista de estados de la maquina de mealy 1
	 * @param estadosM1, lista de estados de la maquina de mealy 2
	 */
	public MaquinaMealy(ArrayList<EstadoMealy> estadosM1,ArrayList<EstadoMealy> estadosM2) {
		
		
	estados = new ArrayList<>();
	for (EstadoMealy estadoMealy : estadosM1) {
		estados.add(estadoMealy);
	}
	for (EstadoMealy estadoMealy : estadosM2) {
		estados.add(estadoMealy);
	}
	
	}
	
	/**
	 * Revisa si existe un estado con el nombre entregado por parametro
	 * @param nombre, una cadena con el nombre de un estado.
	 * @return EstadoMealy correspondiente al nombre entregado.
	 */
	
	public EstadoMealy estadoExiste(String nombre) {
		
		EstadoMealy estado = null;

		for (EstadoMealy a : estados) {
			
			if(nombre.equals(a.getNombre())) estado=a;	
			
		}
		
		return estado;
	}

	public ArrayList<EstadoMealy> getEstados() {
		return estados;
	}

	public void setEstados(ArrayList<EstadoMealy> estados) {
		this.estados = estados;
	}
	
	/**
	 * Se encarga de revisar y eliminar todos los estados inaccesibles de la maquina de mealy
	 */
	
	public void estadosInaccesibles() {
		accesiblesmet(estados.get(0).getNombre());
		ArrayList<EstadoMealy> inaccesibles = new ArrayList<>();
		for (int i = 0; i<estados.size();i++) {
			boolean accesible = false;
			for(int j = 0; j<accesibles.size();j++) {
			if(estados.get(i).getNombre().equals(accesibles.get(j))) {
				accesible =true;
				accesibles.remove(accesibles.get(j));
			}
			}
			
			if(!accesible) {
			
				inaccesibles.add(estados.get(i));
			
			}
		}
		
		for (int i = 0; i < inaccesibles.size(); i++) {
			estados.remove(inaccesibles.get(i));
		}
	}
	
	/**
	 * Se encarga de recorrer de manera recursiva todos los estados haciendo uso de las transiciones y partiendo desde el estado entregado por parametro.
	 * @param estado, una cadena con el nombre de un estado con el que empezara el recorrido.
	 */
	
	public void accesiblesmet(String estado) {
	 
	EstadoMealy actual = null;
	 for(EstadoMealy est: estados) {
	 if(est.getNombre().equals(estado)) {
		 actual =est;
	 }
	 }
	 if(actual!=null) {
		 
		 if(!accesibles.contains(actual.getNombre())) {
			 accesibles.add(actual.getNombre());
			 for(int i = 0; i<actual.getTrans().size();i++) {
				 String[] transic = actual.getTrans().get(i).split(","); //0,B,0
				 String nombreEst = transic[1];
				 accesiblesmet(nombreEst);
			 }
		 }
		 
		 
	 }
	
		
	}
	
	/**
	 * Se encarga de buscar el estado inicial de la maquina mealy.
	 * @return estado inicial de la maquina de mealy
	 */
	public EstadoMealy getInicial() {
		for (EstadoMealy estado : estados) {
			if(estado.isInicial()) return estado;
		}
		
		return null;
	}
	public void setInicial(EstadoMealy inicial) {
		this.inicial = inicial;
	}
	
	
}

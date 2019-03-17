package modelo;

import java.util.ArrayList;

public class MaquinaMealy {

	private ArrayList<EstadoMealy> estados;
	private ArrayList<String> accesibles= new ArrayList<>();
	private EstadoMealy inicial;
	
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
	public MaquinaMealy(ArrayList<EstadoMealy> estadosM1,ArrayList<EstadoMealy> estadosM2) {
		
		
	estados = new ArrayList<>();
	for (EstadoMealy estadoMealy : estadosM1) {
		estados.add(estadoMealy);
	}
	for (EstadoMealy estadoMealy : estadosM2) {
		estados.add(estadoMealy);
	}
	
	}
	
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
	
	
	public void estadosInaccesibles() {
		accesiblesmet(estados.get(0).getNombre());
		
		for (int i = 0; i<estados.size();i++) {
			boolean accesible = false;
			for(String estadoAcces: accesibles) {
			if(estados.get(i).getNombre().equals(estadoAcces)) accesible =true;
			}
			
			if(!accesible) {
			estados.remove(estados.get(i));
			}
		}
	}
	
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

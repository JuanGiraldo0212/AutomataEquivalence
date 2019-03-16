package modelo;

import java.util.ArrayList;

public class MaquinaMealy {

	private ArrayList<EstadoMealy> estados;
	private ArrayList<String> accesibles= new ArrayList<>();
	
	public MaquinaMealy(String infoestados) {
	
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
		ArrayList<String> acces = accesiblesmet(estados.get(0).getNombre());
		
		for (EstadoMealy estado : estados) {
			boolean accesible = false;
			for(String estadoAcces: acces) {
			if(estado.getNombre().equals(estadoAcces)) accesible =true;
			}
			
			if(!accesible) {
			estados.remove(estado);
			}
		}
	}
	
	public ArrayList<String> accesiblesmet(String estado) {
	 
	EstadoMealy actual = null;
	 for(EstadoMealy est: estados) {
	 if(est.getNombre().equals(estado)) {
		 actual =est;
	 }
	 }
	 if(actual!=null) {
		 
		 if(!accesibles.contains(actual.getNombre())) accesibles.add(actual.getNombre());
		 for(int i = 0; i<actual.getTrans().size();i++) {
			 String[] transic = actual.getTrans().get(i).split(",");
			 String nombreEst = transic[1];
			 accesiblesmet(nombreEst);
		 }
		 
	 }
	 return accesibles;
		
	}
}

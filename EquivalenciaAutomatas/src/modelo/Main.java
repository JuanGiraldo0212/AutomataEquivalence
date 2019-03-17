package modelo;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	private String tipo;
	private MaquinaMealy mealy1;
	private MaquinaMealy mealy2;
	private MaquinaMoore moore1;
	private MaquinaMoore moore2;
	private MaquinaMoore sumaDirectaMoore;
	
	public Main(String tipo, String infoestados1, String infoestados2)  {

	this.tipo=tipo;	
		
	if(tipo.equals("Mealy")) {
	mealy1 = new MaquinaMealy(infoestados1);

	mealy2 = new MaquinaMealy(infoestados2);
	}else {
		moore1=new MaquinaMoore(infoestados1);
		
		moore2=new MaquinaMoore(infoestados2);

		
	}
	}
	
	/**
	 * Revisa si dos maquinas son equivalentes 
	 * @return un mensaje afirmando o negando la equivalencia
	 */
	public String equivalenciaMaq() {
		String equiv = "";
		
		if(tipo.equals("Mealy")) {
			
			
			mealy1.estadosInaccesibles();
			mealy2.estadosInaccesibles();
		}
		else {
			//Elimina los estados inaccesibles de las maquina
			moore1.estadosInaccesibles();
			moore2.estadosInaccesibles();
			//Hace el renombramiento de estados repetidos
			int inicioEstados=moore1.getEstados().size();
			HashMap<String, String> cambiarEstados=new HashMap<String, String>();
			for(int i=0;i<moore2.getEstados().size();i++) {
				EstadoMoore actual=moore2.getEstados().get(i);
				cambiarEstados.put(actual.getNombre(),"q"+inicioEstados);
				inicioEstados++;
			}
			for(int i=0;i<moore2.getEstados().size();i++) {
				EstadoMoore actual=moore2.getEstados().get(i);
				actual.renombramiento(cambiarEstados);
				inicioEstados++;
			}
			//Hace la suma directa de las maquinas
			sumaDirecta(moore1.getEstados(), moore2.getEstados());
			
			//Verifica si las maquinas son equivalentes
			if(verificacionEquivalencia(sumaDirectaMoore.particionamiento(sumaDirectaMoore.primerParticionamiento()))) {
				equiv="Las maquinas son equivalentes";
			}
			else {
				equiv="Las maquinas no son equivalentes";
			}
			
			
		}
		
		
		return equiv;
	}
	
	
	public boolean verificacionEquivalencia(ArrayList<ArrayList<String>> pfinal) {
        boolean check = false;

        int contador = 0;

        for (ArrayList<String> list : pfinal) {
            if (list.contains(moore1.darEstados().get(0)) && list.contains(moore2.darEstados().get(0))) {
            contador++;
            }
        }
        boolean checkTemp = true;

        for (ArrayList<String> list : pfinal) {
            for (int i = 0; i < list.size(); i++) {
                if(!moore1.darEstados().contains(list.get(i))&&!moore2.darEstados().contains(list.get(i))) {
                    checkTemp =false;
                }
            }
        }
        if(checkTemp) contador++;

        if(contador==2)check=true;
        return check;
    }
	
	public void sumaDirecta(ArrayList<EstadoMoore> estadosM1,ArrayList<EstadoMoore> estadosM2) {

	    sumaDirectaMoore = new MaquinaMoore(estadosM1, estadosM2);

	    }
	
	

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public MaquinaMealy getMealy1() {
		return mealy1;
	}

	public void setMealy1(MaquinaMealy mealy1) {
		this.mealy1 = mealy1;
	}

	public MaquinaMealy getMealy2() {
		return mealy2;
	}

	public void setMealy2(MaquinaMealy mealy2) {
		this.mealy2 = mealy2;
	}
	
	
}

package modelo;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.ArrayList;

public class Main {

	private String tipo;
	private MaquinaMealy mealy1;
	private MaquinaMealy mealy2;
	private MaquinaMoore moore1;
	private MaquinaMoore moore2;
	private MaquinaMoore sumaDirectaMoore;
	private MaquinaMealy sumaDirecta;
	
	
	
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
		
		//ARREGLO TEMPORAL QUE VERIFICA
		
		
		for(int i =mealy1.getEstados().size(); i<mealy1.getEstados().size()+mealy2.getEstados().size();i++) {
			
				//SE RENOMBRA EL ESTADO REPETIDO, Y SE RENOMBRA EN TODAS LAS TRANSICIONES QUE ESTE.
				String nombrEst = mealy2.getEstados().get(i-mealy1.getEstados().size()).getNombre();
				for(EstadoMealy estdo : mealy2.getEstados()) {
					estdo.renombramiento(nombrEst, "q"+i);
				}
			
		}
		
		//Suma Directa de las dos maquinas una vez renombrados sus estados.
		sumaDirectaMealy(mealy1.getEstados(),mealy2.getEstados());
		
		//Primera particion
		
		ArrayList<ArrayList<EstadoMealy>> p1 = particionUno();
		//Algoritmo de particion
		ArrayList<ArrayList<EstadoMealy>> pfinal = reduccionParticiones(p1);
		
		//Revisa a traves del metodo las condiciones en las cuales los estados iniciales deben estar en una misma particion, y que cada uno
		//de los estados de cada maquina pertenezca al menos a alguna partici�n.
		if(verificacionEquivalenciaMealy(pfinal)) {
			equiv="Las maquinas de Mealy son equivalentes";
		}else {

			equiv="Las maquinas de Mealy no son equivalentes";
		}
		System.out.println(equiv);
			
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
			if(verificacionEquivalenciaMoore(sumaDirectaMoore.particionamiento(sumaDirectaMoore.primerParticionamiento()))) {
				equiv="Las maquinas son equivalentes";
			}
			else {
				equiv="Las maquinas no son equivalentes";
			}
			
			
		}
		
		
		return equiv;
	}
	
	
	public boolean verificacionEquivalenciaMoore(ArrayList<ArrayList<String>> pfinal) {
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
	
	/**
	 * Se encarga de revisar si las dos maquinas son equivalentes, realizando el paso de verificaci�n sobre la partici�n final obtenida
	 * @param ArrayList<ArrayList<EstadoMealy>> pfinal, la partici�n final obtenida en el algoritmo de particionamiento.
	 * @return un boolean que indica si las maquinas son equivalentes o no.
	 */
	
	public boolean verificacionEquivalenciaMealy(ArrayList<ArrayList<EstadoMealy>> pfinal) {
		boolean check = false;
		
		int contador = 0;
		
		for (ArrayList<EstadoMealy> list : pfinal) {
			if (list.contains(mealy1.getInicial()) && list.contains(mealy2.getInicial())) {
			contador++;	
			}
		}
		boolean checkTemp = true;
		
		for (ArrayList<EstadoMealy> list : pfinal) {
			for (int i = 0; i < list.size(); i++) {
				if(!mealy1.getEstados().contains(list.get(i))&&!mealy2.getEstados().contains(list.get(i))) {
					checkTemp =false;
				}
			}
		}
		if(checkTemp) contador++;
		
		if(contador==2)check=true;
		return check;
	}
	
	/**
	 * De manera recursiva realiza todas las particiones siguientes a la particion 1.
	 * @param ArrayList<ArrayList<EstadoMealy>> pi, la partici�n prev�a a la que se realizara en el metodo. Para la primera vez que se llama, pi es equivalente a la partici�n uno.
	 * @return un ArrayList<ArrayList<EstadoMealy>> con la ultima partici�n posible.
	 */
	
	public ArrayList<ArrayList<EstadoMealy>> reduccionParticiones(ArrayList<ArrayList<EstadoMealy>> pi){
		ArrayList<ArrayList<EstadoMealy>> piTemp = new ArrayList<>();
		for (ArrayList<EstadoMealy> claseEq : pi) {
				
					ArrayList<EstadoMealy> claseEqNuev = new ArrayList<>();
					ArrayList<EstadoMealy> claseEqNuev2 = new ArrayList<>();
					EstadoMealy act = claseEq.get(0);
					claseEqNuev.add(act);
					
							ArrayList<String> transClaseEq =act.getSetStateGoingTo(); //q2,q1
							for (int j = 1; j < claseEq.size(); j++) {
								ArrayList<String> transClaseEq2 =claseEq.get(j).getSetStateGoingTo(); //q1,q2
								int count = 0;
								for (int k = 0; k < transClaseEq2.size(); k++) {
									if(estadosEquivalentes(pi, transClaseEq.get(k), transClaseEq2.get(k))) count++;
								}
								if(count == transClaseEq2.size()) {
									claseEqNuev.add(claseEq.get(j));
								}else {
									claseEqNuev2.add(claseEq.get(j));
								}
							}
							
						
				
					piTemp.add(claseEqNuev);
					if(!claseEqNuev2.isEmpty()) {
						
						piTemp.add(claseEqNuev2);
					}
				
			
		}
		String b ="{";
		for (ArrayList<EstadoMealy> a : piTemp) {
		 	b +="{";
			for (EstadoMealy estadoMealy : a) {
				b+=estadoMealy.getNombre()+",";
			}
			b+="}";
		}
		b+="}";
		System.out.println(b);
		if (particionEquivalente(pi, piTemp)) {
			return piTemp;
		}else {
			return reduccionParticiones(piTemp);
		}
		
		
	}
	
	public boolean chequeoClaseEstExiste(EstadoMealy state, ArrayList<EstadoMealy> claseEq) {
		
		boolean check = false;
		
		for (EstadoMealy st : claseEq) {
			if(st.getNombre().equals(state.getNombre()))check=true;
		}
		
		return check;
		
	}
	
	public boolean particionEquivalente(ArrayList<ArrayList<EstadoMealy>> pi,ArrayList<ArrayList<EstadoMealy>> piTemp) {
		
		boolean same=true;
        if(pi.size()==piTemp.size()) {

            for(int i=0;i<pi.size();i++) {
                if(!pi.get(i).equals(piTemp.get(i))) {
                    same=false;
                }
            }
        }
        else {
            same=false;
        }
        return same;
		
	}
	/**
	 * Se encarga de revisar si dos estados son equivalentes. Es decir, que se encuentren en la clase de equivalencia
	 * @param ArrayList<ArrayList<EstadoMealy>> pi, particion entregada en donde se hara la busqueda.
	 * @param String state1, nombre del primer estado entregado
	 * @param String state2, nombre del segundo estado entregado
	 * @return equivalentes, un booleano que indica si dos estados se encuentran en la misma clase de equivalencia dada la particion.
	 */
	public boolean estadosEquivalentes(ArrayList<ArrayList<EstadoMealy>> pi, String state1, String state2) {
		
		boolean equivalentes =false;
		
		EstadoMealy estado1 = null;
		EstadoMealy estado2 = null;
		if(state1.equals(state2)) {
			for (EstadoMealy estado : sumaDirecta.getEstados()) {
				if(estado.getNombre().equals(state1)) {
					estado1 = estado;
					estado2 =estado;
				}
			}
		}else {
			
			for (EstadoMealy estado : sumaDirecta.getEstados()) {
				if(estado.getNombre().equals(state1)) {
					estado1 = estado;
				}else if(estado.getNombre().equals(state2)) {
					estado2 = estado;
				}
			}
		}
		
		for (ArrayList<EstadoMealy> arrayList : pi) {
			if(arrayList.contains(estado1) && arrayList.contains(estado2)) {
				equivalentes = true;
				break;
			}
		}
		
		
		return equivalentes;
		
	}
	
	/**
	 * Se encarga de realizar el algoritmo para encontrar la primera partici�n de la maquina resultante de la suma directa.
	 * @return un ArrayList<ArrayList<EstadoMealy>> el cual es la primera partici�n realizada de la maquina resultante por suma directa.
	 */
	
	public ArrayList<ArrayList<EstadoMealy>> particionUno(){
		
		ArrayList<ArrayList<EstadoMealy>> p1 = new ArrayList<>();
		ArrayList<ArrayList<String>> outs = new ArrayList<>();
		
		for (int i = 0; i< sumaDirecta.getEstados().size();i++) {
			
			if(!outs.contains(sumaDirecta.getEstados().get(i).getSetOutputs())) {
				outs.add(sumaDirecta.getEstados().get(i).getSetOutputs());
				ArrayList<EstadoMealy> temp = new ArrayList<>();
				temp.add(sumaDirecta.getEstados().get(i));
				for(int j = i+1; j<sumaDirecta.getEstados().size();j++) {
					ArrayList<String> tempOut =sumaDirecta.getEstados().get(j).getSetOutputs();
					int count = 0;
					for(String str: sumaDirecta.getEstados().get(i).getSetOutputs()) {
						if(tempOut.contains(str)) count++;
					}
					
					if(count==tempOut.size()) temp.add(sumaDirecta.getEstados().get(j));
					
				}
				p1.add(temp);
			}
		}
		String b ="{";
		for (ArrayList<EstadoMealy> a : p1) {
		 	b +="{";
			for (EstadoMealy estadoMealy : a) {
				b+=estadoMealy.getNombre()+",";
			}
			b+="}";
		}
		b+="}";
		System.out.println(b);
		
		return p1;
	}
	
	/**
	 * Se encarga de inicializar la maquina mealy resultante por suma directa
	 * @param estadosM1, arrayList de estados de la maquina mealy 1
	 * @param estadosM2, arrayList de estados de la maquina mealy 2
	 */
	public void sumaDirectaMealy(ArrayList<EstadoMealy> estadosM1,ArrayList<EstadoMealy> estadosM2) {
		
	sumaDirecta = new MaquinaMealy(estadosM1, estadosM2);
		
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

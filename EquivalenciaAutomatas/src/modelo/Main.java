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
		String[] estados = new String[mealy1.getEstados().size()+mealy2.getEstados().size()];
		
		for (int i =0; i< mealy1.getEstados().size();i++) {
			estados[i] = mealy1.getEstados().get(i).getNombre();
		}
		
		for(int i =mealy1.getEstados().size(); i<mealy1.getEstados().size()+mealy2.getEstados().size();i++) {
			
				//SE RENOMBRA EL ESTADO REPETIDO, Y SE RENOMBRA EN TODAS LAS TRANSICIONES QUE ESTE.
				String nombrEst = mealy2.getEstados().get(i-mealy1.getEstados().size()).getNombre();
				for(EstadoMealy estdo : mealy2.getEstados()) {
					estdo.renombramiento(nombrEst, "q"+i);
					estados[i]=mealy2.getEstados().get(i-mealy1.getEstados().size()).getNombre();
				}
			
		}
		
		//Suma Directa de las dos maquinas una vez renombrados sus estados.
		sumaDirecta(mealy1.getEstados(),mealy2.getEstados());
		
		//Primera particion
		
		ArrayList<ArrayList<EstadoMealy>> p1 = particionUno();
		//Algoritmo de particion
		ArrayList<ArrayList<EstadoMealy>> pfinal = reduccionParticiones(p1);
		
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
	
	public ArrayList<ArrayList<EstadoMealy>> reduccionParticiones(ArrayList<ArrayList<EstadoMealy>> pi){
		ArrayList<ArrayList<EstadoMealy>> piTemp = new ArrayList<>();
		for (ArrayList<EstadoMealy> claseEq : pi) {
				if(claseEq.size()!=1) {
					
					ArrayList<EstadoMealy> claseEqTemp = new ArrayList<>();
					ArrayList<EstadoMealy> claseEqTemp2 = new ArrayList<>();
					for (int i = 0; i < claseEq.size(); i++) {
						
						if(!chequeoClaseEstExiste(claseEq.get(i), claseEqTemp)) {
							claseEqTemp.add(claseEq.get(i));
							ArrayList<String> transClaseEq =claseEq.get(i).getSetStateGoingTo(); //q2,q1,q3
							for (int j = i+1; j < claseEq.size(); j++) {
								ArrayList<String> transClaseEq2 =claseEq.get(j).getSetStateGoingTo(); //q1,q2,q3
								int count = 0;
								for (int k = 0; k < transClaseEq2.size(); k++) {
									if(estadosEquivalentes(pi, transClaseEq.get(k), transClaseEq2.get(k))) count++;
								}
								if(count == transClaseEq2.size()) {
									claseEqTemp.add(claseEq.get(j));
									
								}else {
									claseEqTemp2.add(claseEq.get(j));
								}
							}
						}
						
					}
					piTemp.add(claseEqTemp);
					if(!claseEqTemp2.isEmpty()) {
						
						piTemp.add(claseEqTemp2);
					}
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
	
	public boolean estadosEquivalentes(ArrayList<ArrayList<EstadoMealy>> pi, String state1, String state2) {
		
		boolean equivalentes =false;
		
		EstadoMealy estado1 = null;
		EstadoMealy estado2 = null;
		
		for (EstadoMealy estado : sumaDirecta.getEstados()) {
			if(estado.getNombre().equals(state1)) {
			estado1 = estado;
			}else if(estado.getNombre().equals(state2)) {
			estado2 = estado;
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
	
	public void sumaDirecta(ArrayList<EstadoMealy> estadosM1,ArrayList<EstadoMealy> estadosM2) {
		
	sumaDirecta = new MaquinaMealy(estadosM1, estadosM2);
		
	}
	
	public boolean nombreRepetidoEstado(String[] estados,String estadito) {
		
		boolean check =false;
		if(estados.length!=0) {
			
			for (String estaditi : estados) {
				if(estadito.equals(estaditi)) check = true;
			}
		}
		
		return check;
		
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

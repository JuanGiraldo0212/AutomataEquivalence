package modelo;

import java.util.ArrayList;

public class MaquinaMoore {
	private ArrayList<EstadoMoore> estados;
	private ArrayList<String> accesibles;
	
	/**
	 * Inicializa la maquina de moore
	 * @param infoestados una cadena con la informacion de los estados de la maquina
	 */
	public MaquinaMoore(String infoestados) {
		
		estados=new ArrayList<EstadoMoore>();
		accesibles=new ArrayList<String>();
		
		String[] estadosS =infoestados.split(";");
		
		for (int i = 0; i < estadosS.length; i++) {
			
			String [] info =estadosS[i].split(",");
			
			EstadoMoore temp = estadoExiste(info[0]);
			
			if(temp!=null) {
				
				temp.getTrans().add(info[1]+","+info[2]);
				
			}else{
				
				EstadoMoore state= new EstadoMoore(info[0],info[1]+","+info[2],info[3]);
				
				this.estados.add(state);
				
			}
		  }
		
	}
	
	/**
	 * Sobrecarga el constructor e inicializa la maquina de suma directa
	 * @param estadosM1 son los estados de la primera maquina
	 * @param estadosM2 son los estados de la segunda maquina
	 */
	public MaquinaMoore(ArrayList<EstadoMoore> estadosM1,ArrayList<EstadoMoore> estadosM2) {


	    estados = new ArrayList<>();
	    for (EstadoMoore estadoMoore : estadosM1) {
	        estados.add(estadoMoore);
	    }
	    for (EstadoMoore estadoMoore : estadosM2) {
	        estados.add(estadoMoore);
	    }

	    }
	
	/**
	 * Revisa si un estado ya se agregó a la maquina
	 * @param nombre el nombre del estado
	 * @return el estado si ya existe y null si no
	 */
	public EstadoMoore estadoExiste(String nombre) {
		
		EstadoMoore estado = null;
		
			
			for (EstadoMoore a : estados) {
				
				if(nombre.equals(a.getNombre())) estado=a;	
				
			}
		

		
		return estado;
	}
	
	/**
	 * Elimina los estados inaccesibles de la maquina
	 */
	public void estadosInaccesibles() {
        accesiblesmet(estados.get(0).getNombre());
        ArrayList<EstadoMoore> inaccesibles = new ArrayList<>();
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
	 * Recorre recursivamente la maquina y revisa si estos son accesibles
	 * @param estado el estado inicial desde donde se va a revisar
	 */
    public void accesiblesmet(String estado) {

    EstadoMoore actual = null;
     for(EstadoMoore est: estados) {
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
     * Realiza el primer particionamiento de la maquina 
     * @return un arraylist de arraylist de cadenas conteniendo el primer particionamiento
     */
    public ArrayList<ArrayList<String>> primerParticionamiento(){
    	ArrayList<ArrayList<String>> particiones=new ArrayList<ArrayList<String>>();
    	ArrayList<String> s0=new ArrayList<String>();
    	ArrayList<String> s1=new ArrayList<String>();
    	particiones.add(s0);
    	particiones.add(s1);
    	
    	for(int i=0;i<estados.size();i++) {
    		EstadoMoore actual=estados.get(i);
    		if(actual.getSalida().equals("0")) {
    			particiones.get(0).add(actual.getNombre());
    		}
    		else {
    			particiones.get(1).add(actual.getNombre());
    		}
    	}
    	return particiones;
    }
    
    /**
     * Revisa si ya se finalizo la recursion
     * @param part la particion final
     * @return true si ya acabo el particionamiento y false si aun no ha acabado
     */
    public boolean finish(ArrayList<ArrayList<String>> part) {
    	boolean ok=true;
    	for(int i=0;i<part.size();i++) {
    		if(part.get(i).size()!=1) {
    			
    			ok=false;
    		}
    	}
    	return ok;
    }
    
    /**
     * Revisa si dos particiones son iguales
     * @param part la particion final
     * @param previo la particion previa
     * @return true si son iguales y false si no
     */
    public boolean same(ArrayList<ArrayList<String>> part,ArrayList<ArrayList<String>> previo) {
    	boolean same=true;
    	if(part.size()==previo.size()) {
    		
    		for(int i=0;i<part.size();i++) {
    			if(!part.get(i).equals(previo.get(i))) {
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
     * Partciona recusrivamente la primera partcion
     * @param primer es la primera particion o la inicial de la recursion
     * @return la partcion final 
     */
    public ArrayList<ArrayList<String>> particionamiento(ArrayList<ArrayList<String>> primer){
    	
    		ArrayList<ArrayList<String>> previo=primer;
    		ArrayList<ArrayList<String>> particiones=new ArrayList<ArrayList<String>>();
    		for(int i=0;i<previo.size();i++) {
    			ArrayList<String> actual=previo.get(i);
    			ArrayList<String> nuevo=new ArrayList<String>();
    			String comp=actual.get(0);
    			nuevo.add(comp);
    			ArrayList<String> afuera=new ArrayList<String>();
    			for(int j=1;j<actual.size();j++) {
    				String estado=actual.get(j);
     				if(revisarTabla(buscarTabla(comp, estado), previo)) {
    					nuevo.add(estado);
    				}
    				else {
    					afuera.add(estado);
    				}
    				
    				
    			}
    			particiones.add(nuevo);
    			if(afuera.size()>0) {
    				particiones.add(afuera);
    			}
    		}
    		System.out.println("ok");
    		System.out.println(printParticiones(particiones));
    		if(finish(particiones)) {
    			return particiones;
    		}
    		else if(same(particiones, previo)) {
    			return particiones;
    		}
    		else {
    			
    			return particionamiento(particiones);
    		}
    	
    	
    }
    
    /**
     * Revisa sin un subconjunto de una particion debe de particionarce
     * @param revisar el subconjunto de la particion
     * @param previo la particion previa
     * @return true si debe particonarce y false si no
     */
    public boolean revisarTabla(ArrayList<ArrayList<String>> revisar, ArrayList<ArrayList<String>> previo) {
    	boolean revisado=true;
    	
    	for(int i=0;i<revisar.size() && revisado;i++) {
    		ArrayList<String> actual=revisar.get(i);
    		for(int j=0;j<previo.size();j++) {
    			ArrayList<String> p1=previo.get(j);
    			if(p1.contains(actual.get(0))) {
    				if(!p1.contains(actual.get(1))) {
    					revisado=false;
    				}
    			}
    			
    		}
    	}
    	return revisado;
    }
    
    /**
     * Crea los subconjuntos de una particion desde dos estados para ser evaluados
     * @param estA el primer estado a evaluar
     * @param estB el segundo estado a evaluar
     * @return
     */
    public ArrayList<ArrayList<String>> buscarTabla(String estA,String estB){
    	ArrayList<ArrayList<String>> revisado=new ArrayList<ArrayList<String>>();
    	EstadoMoore a=encontrarEstado(estA);
    	EstadoMoore b=encontrarEstado(estB);
    	for(int i=0;i<a.getTrans().size();i++) {
    		ArrayList<String> temp=new ArrayList<String>();
    		String transA=a.getTrans().get(i).split(",")[1];
    		temp.add(transA);
    		String transB=b.getTrans().get(i).split(",")[1];
    		temp.add(transB);
    		revisado.add(temp);
    	}
    
    	return revisado;
    }
    
    /**
     * Encuentra un estado 
     * @param nom el nombre del estado
     * @return eñ estado buscado
     */
    public EstadoMoore encontrarEstado(String nom) {
    	EstadoMoore encontrado=null;
    	for(int i=0;i<estados.size();i++) {
    		EstadoMoore actual=estados.get(i);
    		if(actual.getNombre().equals(nom)) {
    			encontrado=actual;
    		}
    	}
    	return encontrado;
    }
    
    
    /**
     * Imprime las particiones
     * @param part las partciones
     * @return una cadena con las particiones en un formato dado
     */
    public String printParticiones(ArrayList<ArrayList<String>> part) {
    	String cadena="{";
    	System.out.println("imprimir");
    	for(int i=0;i<part.size();i++) {
    		cadena+="{";
    		ArrayList<String> actual=part.get(i);
    		for(int j=0;j<actual.size();j++) {
    			cadena+=actual.get(j)+",";
    		}
    		cadena+="},";
    	}
    	cadena+="}";
    	System.out.println("impreso");
    	return cadena;
    }
    
    /**
     * Extrae los estados como una cadena
     * @return un arraylist con los estados como cadena
     */
    public ArrayList<String> darEstados(){
    	ArrayList<String> estad=new ArrayList<String>();
    	for(int i=0;i<estados.size();i++) {
    		estad.add(estados.get(i).getNombre());
    	}
    	return estad;
    }

	public ArrayList<EstadoMoore> getEstados() {
		return estados;
	}

	public void setEstados(ArrayList<EstadoMoore> estados) {
		this.estados = estados;
	}

	public ArrayList<String> getAccesibles() {
		return accesibles;
	}

	public void setAccesibles(ArrayList<String> accesibles) {
		this.accesibles = accesibles;
	}
	
	

}

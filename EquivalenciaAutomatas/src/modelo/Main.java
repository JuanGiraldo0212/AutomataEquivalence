package modelo;

public class Main {

	private String tipo;
	private MaquinaMealy mealy1;
	private MaquinaMealy mealy2;
	
	public Main(String tipo, String infoestados1, String infoestados2) {

	this.tipo=tipo;	
		
	if(tipo.equals("Mealy")) {
	mealy1 = new MaquinaMealy(infoestados1);

	mealy2 = new MaquinaMealy(infoestados2);
	}else {
		
	}
	}
	
	public String equivalenciaMaq() {
		String equiv = "";
		
		mealy1.estadosInaccesibles();
		mealy2.estadosInaccesibles();
		
		
		return equiv;
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

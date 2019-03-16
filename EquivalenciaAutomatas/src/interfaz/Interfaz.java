package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

public class Interfaz extends JFrame{
	public final static int ANCHO=1000;
	public final static int LARGO=800;
	private PanelEntradas entradas;
	private PanelAutomataMoore[] moore;
	private PanelAutomataMealy[] mealy;
	private JButton comparar;
	private JPanel aux;
	public Interfaz() {
		setTitle("Equivalencia entre autómatas");
		setLayout(new BorderLayout());
		setSize(new Dimension(ANCHO, LARGO));
		setResizable(false);
		entradas= new PanelEntradas(this);
		add(entradas,BorderLayout.NORTH);
		aux=new JPanel();
		comparar=new JButton("=~");
		comparar.setPreferredSize(new Dimension(60,20));
		aux.add(comparar);
		add(aux,BorderLayout.SOUTH);
		moore=new PanelAutomataMoore[2];
		mealy=new PanelAutomataMealy[2];
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	public static void main(String[] args) {
		Interfaz ventana= new Interfaz();
		ventana.setVisible(true);
		
	}
	
	public void addAutomata(String tipo,int row1,int row2, int column, String ent) {
		if(tipo.equals("Mealy")) {
			PanelAutomataMealy actual1=new PanelAutomataMealy(this, row1, column,ent);
			PanelAutomataMealy actual2=new PanelAutomataMealy(this, row2, column,ent);
			mealy[0]=actual1;
			mealy[1]=actual2;
			add(mealy[0],BorderLayout.WEST);
			add(mealy[1],BorderLayout.EAST);
			pack();
		}
		else {
			PanelAutomataMoore actual1=new PanelAutomataMoore(this, row1, column,ent);
			PanelAutomataMoore actual2=new PanelAutomataMoore(this, row2, column,ent);
			moore[0]=actual1;
			moore[1]=actual2;
			add(moore[0],BorderLayout.WEST);
			add(moore[1],BorderLayout.EAST);
			pack();
		}
	}

}

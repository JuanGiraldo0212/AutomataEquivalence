package interfaz;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelAutomataMealy extends JPanel{
	
	Interfaz principal;
	JTextField[][] fields;
	JLabel[] entradas;
	JLabel[] estados;
	
	public PanelAutomataMealy(Interfaz v, int row,int column,String ent) {
		principal=v;
		String[] nomEnt=ent.split(",");
		setLayout(new GridLayout(row+1, column+1));
		fields=new JTextField[row][column+1];
		entradas=new JLabel[column];
		estados=new JLabel[row];
		for(int i=0;i<row+1;i++) {
			for(int j=0;j<column+1;j++) {
				if(i!=0 || j!=0) {
					if(i==0) {
					
						JLabel actual=new JLabel(nomEnt[j-1]);;
						actual.setPreferredSize(new Dimension(50,20));
						entradas[j-1]=actual;
						add(actual);
						System.out.println("ok");
						
					}
					else if(j==0) {
						JLabel actual=new JLabel("q"+(i-1));
						actual.setPreferredSize(new Dimension(50,20));
						estados[i-1]=actual;
						add(actual);
					}
					else {
						JTextField actual=new JTextField();
						actual.setPreferredSize(new Dimension(50,20));
						fields[i-1][j-1]=actual;
						add(actual);
					}
				}
				else {
					add(new JLabel());
				}
			}
		}
	}
	
	public String infoEstados() {
		String infoestados ="";
		
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				String[] temp = fields[i][j].getText().split(",");
				//infoestados = ESTADO,INPUT,ESTADO-AL-QUE-SE-DIRIGE,OUTPUT
				infoestados+=estados[i].getText()+","+entradas[j].getText()+","+temp[0]+temp[1];
			}
			
		}
		
		
		return infoestados;
	}

}

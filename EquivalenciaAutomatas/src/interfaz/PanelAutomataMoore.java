package interfaz;
import javax.swing.*;
import java.awt.*;
public class PanelAutomataMoore extends JPanel{
	
	Interfaz principal;
	JTextField[][] fields;
	JLabel[] entradas;
	JLabel[] estados;
	public PanelAutomataMoore(Interfaz v, int row,int column,String entra) {
		principal=v;
		String[] nomEnt=entra.split(",");
		setLayout(new GridLayout(row+1, column+2));
		fields=new JTextField[row][column+1];
		entradas=new JLabel[column+1];
		estados=new JLabel[row];
		for(int i=0;i<row+1;i++) {
			for(int j=0;j<column+2;j++) {
				if(i!=0 || j!=0) {
					if(i==0) {
						JLabel actual;
						if(j!=column+1) {
							actual=new JLabel(nomEnt[j-1]);;
						}
						else {
							actual=new JLabel(" ");;
							
						}
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
			String estadoInicial=estados[i].getText();
			String salida=fields[i][fields[0].length-1].getText();
			for (int j = 0; j < fields[i].length-1; j++) {
				String estadoFinal = fields[i][j].getText();
				String entrada=entradas[j].getText();
				//infoestados = ESTADO,INPUT,ESTADO-AL-QUE-SE-DIRIGE,OUTPUT
				if(i==fields.length-1 && j ==fields[i].length-1) infoestados+=estadoInicial+","+entrada+","+estadoFinal+","+salida;
                else infoestados+=estadoInicial+","+entrada+","+estadoFinal+","+salida+";";
				
				
			}
			
		}
		
		System.out.println(infoestados);
		return infoestados;
	}

}

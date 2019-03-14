package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelEntradas extends JPanel implements ActionListener{

	public final static String INICIAR="Iniciar";
	public final static String[] AUTOMATAS= {"Moore","Mealy"};
	private Interfaz principal;
	private JLabel lblAutomata;
	private JComboBox<String> automata;
	private JLabel lblEntradas;
	private JTextField txtEntradas;
	private JLabel lblEstados1;
	private JTextField txtEstados1;
	private JLabel lblEstados2;
	private JTextField txtEstados2;
	private JButton boton;
	private JPanel aux1;
	private JPanel aux2;
	private JPanel aux3;
	public PanelEntradas(Interfaz v) {
		setLayout(new BorderLayout());
		principal=v;
		aux1=new JPanel();
		aux2=new JPanel();
		aux3=new JPanel();
		lblAutomata=new JLabel("Tipo de automata: ");
		automata=new JComboBox<String>(AUTOMATAS);
		automata.setPreferredSize(new Dimension(150,20));
		lblEntradas=new JLabel("Numero de entradas: ");
		txtEntradas=new JTextField();
		txtEntradas.setPreferredSize(new Dimension(50,20));
		lblEstados1=new JLabel("Numero de estados de M1: ");
		txtEstados1= new JTextField();
		txtEstados1.setPreferredSize(new Dimension(50,20));
		lblEstados2=new JLabel("Numero de estados de M2: ");
		txtEstados2= new JTextField();
		txtEstados2.setPreferredSize(new Dimension(50,20));
		boton=new JButton("Generar");
		boton.setActionCommand(INICIAR);
		boton.addActionListener(this);
		aux1.add(lblAutomata);
		aux1.add(automata);
		aux1.add(lblEntradas);
		aux1.add(txtEntradas);
		add(aux1,BorderLayout.NORTH);
		aux2.add(lblEstados1);
		aux2.add(txtEstados1);
		aux2.add(lblEstados2);
		aux2.add(txtEstados2);
		add(aux2,BorderLayout.CENTER);
		aux3.add(boton);
		add(aux3,BorderLayout.SOUTH);
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String comand=arg0.getActionCommand();
		if(comand.equals(INICIAR)) {
			principal.addAutomata((String)automata.getSelectedItem(),Integer.parseInt(txtEstados1.getText()),Integer.parseInt(txtEstados2.getText()),Integer.parseInt(txtEntradas.getText()));
		}
		boton.setEnabled(false);
		
	}
}

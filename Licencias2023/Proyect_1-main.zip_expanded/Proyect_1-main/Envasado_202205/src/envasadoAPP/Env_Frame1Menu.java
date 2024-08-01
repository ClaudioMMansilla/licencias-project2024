package envasadoAPP;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import conector.Conexion;

public class Env_Frame1Menu extends JFrame {

	private JPanel contentPane;
	private Connection cn; //hago que el object cn sea accesible desde toda la clase.

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		setDefaultLookAndFeelDecorated(true);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Env_Frame1Menu frame = new Env_Frame1Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}


	/**
	 * Create the frame.
	 */
	public Env_Frame1Menu() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(50, 50, 223, 325);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setTitle(" Consulta ");
		setResizable(false);

    	Conexion conexion = new Conexion();
    	cn = null;
    	Statement stm = null;
    	ResultSet rs = null;

		JLabel label = new JLabel("Selecione modo de consulta");
		label.setBounds(10, 0, 172, 22);
		label.setFont(new Font ("Tw Cen MT", 1, 14));
		contentPane.add(label);

		JButton exit = new JButton("Salir");
		exit.setBounds(132, 258, 65, 22);
		exit.setFont(new Font ("Tw Cen MT", 1, 14));
		contentPane.add(exit);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JPanel panel_budines = new JPanel();
		panel_budines.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), " Planta Budines ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		panel_budines.setBounds(10, 44, 191, 92);
		contentPane.add(panel_budines);

		JButton jbt1_informe = new JButton("Informe Envasado");
		panel_budines.add(jbt1_informe);
		jbt1_informe.setFont(new Font("Tw Cen MT", 1, 16));
		jbt1_informe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				envasadoFR3 frame03 = new envasadoFR3();
				Env_Frame3Informe4936 frame03 = new Env_Frame3Informe4936();
				frame03.setVisible(true);
			}
		});

		JButton jbt2_registro = new JButton("Revisar  envasado");
		panel_budines.add(jbt2_registro);
		jbt2_registro.setFont(new Font("Tw Cen MT", 1, 16));
		jbt2_registro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Env_Frame2Movim4936 frame02 = new Env_Frame2Movim4936();
				frame02.setVisible(true);
			}
		});

		JPanel panel_pdulces = new JPanel();
		panel_pdulces.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), " Planta Pan Dulces ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		panel_pdulces.setBounds(10, 147, 191, 91);
		contentPane.add(panel_pdulces);

		JButton jbt3_informe = new JButton("Informe Envasado");
		jbt3_informe.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
		panel_pdulces.add(jbt3_informe);
		jbt3_informe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				envasadoFR5 frame05 = new envasadoFR5();
				frame05.setVisible(true);
			}
		});

		JButton jbt4_registro = new JButton("Revisar  envasado");
		jbt4_registro.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
		panel_pdulces.add(jbt4_registro);
		jbt4_registro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Env_Frame4Movim4945 frame04 = new Env_Frame4Movim4945();
				frame04.setVisible(true);
			}
		});

	}
}

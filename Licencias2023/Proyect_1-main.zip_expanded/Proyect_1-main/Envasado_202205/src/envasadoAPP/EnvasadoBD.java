package envasadoAPP;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowStateListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import conector.Conexion;


public class EnvasadoBD extends JFrame {
	// UNICAS VARIABLES GLOBALES, PARA REDIRECCIONAR CONEXION A BASE DE DATOS
	String tablaInput = "pruebas_2022.env_input_budines";
	String tablaProductos = "pruebas_2022.productos";

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					EnvasadoBD frame = new EnvasadoBD();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EnvasadoBD() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(javax.swing.BorderFactory.createTitledBorder(" REGISTRO DE ENVASADO "));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setDefaultLookAndFeelDecorated(true);
		setTitle("Planta:  Budin   |   M�dulo:  Envasado ");
        setBounds(30, 20, 900, 630);
        setResizable(false);
        contentPane.setBackground(Color.DARK_GRAY);

		EventoDeTeclado tecla=new EventoDeTeclado();
		addKeyListener(tecla);
        contentPane.addKeyListener(tecla);

		//Inicio creaci�n de componentes
		JLabel label1 = new JLabel("Informaci�n requerida a ingresar: Id, Lote y Vencimiento (DD-MM-AAAA)");
		label1.setBounds(25,26,617,23);
		label1.setFont(new Font ("Tw Cen MT", 1, 20));
		label1.setForeground(Color.white);
		contentPane.add(label1);

		JLabel label2 = new JLabel ("Id: ");
		label2.setBounds(47,79,30,15);
		label2.setFont(new Font ("Tw Cen MT",0,24));
		label2.setForeground(Color.white);
		contentPane.add(label2);

		JLabel label3 = new JLabel ("Producto: ");
		label3.setBounds(25,151,100,15);
		label3.setFont(new Font("Tw Cen MT",0,24));
		label3.setForeground(Color.white);
		contentPane.add(label3);

		JLabel label4 = new JLabel ("Lote: ");
		label4.setBounds(304,79,59,15);
		label4.setFont(new Font("Tw Cen MT",0,24));
		label4.setForeground(Color.white);
		contentPane.add(label4);

		JLabel label5 = new JLabel ("Vto: ");
		label5.setBounds(529,79,46,15);
		label5.setFont(new Font("Tw Cen MT",0,24));
		label5.setForeground(Color.white);
		contentPane.add(label5);

		label6 = new JLabel (); //label que debe mostrar la descrip del producto
		label6.setBounds(15,164,857,49);
		label6.setFont(new Font("Arial",1,28));
		label6.setForeground(Color.yellow);
		contentPane.add(label6);

		JLabel label7 = new JLabel ("CAJAS : ");
		label7.setBounds(327,366,147,72);
		label7.setFont(new Font("Tw Cen MT",1,40));
		label7.setForeground(Color.white);
		contentPane.add(label7);


		field_id = new JTextField();
		field_id.setBounds(81,60,135,50);
		field_id.setFont(new java.awt.Font("Tw Cen MT", 0, 40));
		contentPane.add(field_id);
		field_id.addKeyListener(tecla);
		field_id.addActionListener(new ActionListener(){
			public void actionPerformed(KeyEvent e){
				codigo = e.getKeyCode();
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (codigo == 10) {
					KeyIntro();
				}else {
					System.out.println("saliendo del if en field.id"+ codigo);
				}
			}
			});

		field_lote = new JTextField();
		field_lote.setBounds(356,60,84,50);
		field_lote.setFont(new java.awt.Font("Tw Cen MT", 0, 40));
		contentPane.add(field_lote);
		field_lote.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				}
			});

		field_vto1 = new JFormattedTextField();
		field_vto1.setBounds(573,60,50,50);
		field_vto1.setFont(new java.awt.Font("Tw Cen MT", 0, 40));
		contentPane.add(field_vto1);
		field_vto1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				}
			});

		field_vto2 = new JFormattedTextField();
		field_vto2.setFont(new Font("Tw Cen MT", Font.PLAIN, 40));
		field_vto2.setBounds(651, 60,50,50);
		contentPane.add(field_vto2);
		field_vto2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				}
			});

		field_vto3 = new JFormattedTextField();
		field_vto3.setFont(new Font("Tw Cen MT", Font.PLAIN, 40));
		field_vto3.setBounds(734, 60,91,50);
		contentPane.add(field_vto3);
		field_vto3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				}
			});

		field_cantidad = new JTextField();
		field_cantidad.setBounds(474,366,180,72);
		field_cantidad.setFont(new java.awt.Font("Tw Cen MT", 0, 70));
		contentPane.add(field_cantidad);
		/*
		field_cantidad.addActionListener(new ActionListener(){
			public void actionPerformed(KeyEvent e){
				codigo = e.getKeyCode();
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (codigo == 10) {
					try {
						KeyIntro();
						JBTGuardar();

					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					System.out.println("saliendo del if en field.id"+ codigo);
				}
			}
			});
		*/

		field_descrip = new JTextField();
		//field_descrip.setBounds(15, 214, 799, 49);
		//contentPane.add(field_descrip);
		///field_descrip.setFont(new Font("Arial",1,26));
		field_descrip.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField descrip = (JTextField)e.getSource();
				producto = descrip.getSelectedText();
				}
			});


		JBT_guardar = new JButton("Guardar");
		JBT_guardar.setBounds(712,366,141,72);
		JBT_guardar.setFont(new Font("Tw Cen MT", 1, 28));
		contentPane.add(JBT_guardar);
		JBT_guardar.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent e) {
                try {
					KeyIntro();
					JBTGuardar();
				} catch (UnknownHostException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

		JPanel panelINF =new JPanel();
		panelINF.setBounds(25, 445, 836, 145);
		contentPane.add(panelINF);
		panelINF.setBorder(javax.swing.BorderFactory.createTitledBorder(" ULTIMO INGRESO "));
		panelINF.setLayout(null);
		panelINF.setBackground(Color.BLACK);
//		panelINF.setForeground(Color.RED);


		JLabel label9 = new JLabel ("Id: ");
		label9.setBounds(638,50,35,28); //(280,239,85,15);
		label9.setFont(new Font("Tw Cen MT",1,26));
		label9.setForeground(Color.white);
		panelINF.add(label9);

		JLabel label10 = new JLabel ("Hora: ");
		label10.setBounds(10,42,76,41); //(280,239,85,15);
		label10.setFont(new Font("Tw Cen MT",1,30));
		label10.setForeground(Color.white);
		panelINF.add(label10);

		JLabel label11 = new JLabel ("Cantidad: ");
		label11.setBounds(367,50,113,28); //(138,239,70,15);
		label11.setFont(new Font("Tw Cen MT",1,26));
		label11.setForeground(Color.white);
		panelINF.add(label11);

		label12 = new JLabel (); //label que debe mostrar e
		label12.setBounds(15,164,820,49);
		label12.setFont(new Font("Arial",1,26));
		label12.setForeground(Color.red);
		contentPane.add(label12);

		info_hora = new JLabel (""); //AJUSTAR FORMATO DE DATE para hora y minuto
		info_hora.setBounds(91,28,221,64);
		info_hora.setFont(new Font("ARIAL",1,54));
		info_hora.setForeground(Color.yellow);
		panelINF.add(info_hora);

		info_cantidad = new JLabel ("");
		info_cantidad.setBounds(477,42,143,41);
		info_cantidad.setFont(new Font("ARIAL",1,50));
		info_cantidad.setForeground(Color.yellow);
		panelINF.add(info_cantidad);

		info_id = new JLabel ("");
		info_id.setBounds(683,42,132,41);
		info_id.setFont(new Font("ARIAL",1,50));
		info_id.setForeground(Color.yellow);
		panelINF.add(info_id);

		info_scrap_unid = new JLabel("");
		info_scrap_unid.setForeground(Color.YELLOW);
		info_scrap_unid.setFont(new Font("Arial", Font.BOLD, 30));
		info_scrap_unid.setBounds(497, 107, 87, 30);
		panelINF.add(info_scrap_unid);

		JLabel label18 = new JLabel("Scrap Envase: ");
		label18.setForeground(Color.WHITE);
		label18.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		label18.setBounds(594, 109, 130, 28);
		panelINF.add(label18);

		JLabel label17 = new JLabel("Scrap Unidades: ");
		label17.setForeground(Color.WHITE);
		label17.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		label17.setBounds(353, 109, 143, 28);
		panelINF.add(label17);

		info_scrap_env = new JLabel("");
		info_scrap_env.setForeground(Color.YELLOW);
		info_scrap_env.setFont(new Font("Arial", Font.BOLD, 30));
		info_scrap_env.setBounds(722, 107, 93, 30);
		panelINF.add(info_scrap_env);

		JLabel label19 = new JLabel("Pallet: ");
		label19.setForeground(Color.WHITE);
		label19.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		label19.setBounds(221, 109, 62, 28);
		panelINF.add(label19);

		info_pallets = new JLabel("");
		info_pallets.setForeground(Color.YELLOW);
		info_pallets.setFont(new Font("Arial", Font.BOLD, 30));
		info_pallets.setBounds(279, 107, 69, 30);
		panelINF.add(info_pallets);

		JLabel label13 = new JLabel("PALLET Nro : ");
		label13.setForeground(Color.WHITE);
		label13.setFont(new Font("Tw Cen MT", Font.BOLD, 26));
		label13.setBounds(25, 366, 148, 72);
		contentPane.add(label13);

		JLabel label14 = new JLabel("Unidades : ");
		label14.setForeground(Color.WHITE);
		label14.setFont(new Font ("Tw Cen MT",0,24));
		label14.setBounds(25, 282, 109, 40);
		contentPane.add(label14);

		JLabel label15 = new JLabel("Kg Envase : ");
		label15.setForeground(Color.WHITE);
		label15.setFont(new Font ("Tw Cen MT",0,24));
		label15.setBounds(343, 282, 120, 40);
		contentPane.add(label15);

		field_scrap_prod = new JTextField();
		field_scrap_prod.setFont(new Font("Tw Cen MT", Font.PLAIN, 40));
		field_scrap_prod.setBounds(144, 266, 120,62);
		contentPane.add(field_scrap_prod);

		field_pallet = new JTextField();
		field_pallet.setFont(new Font("Tw Cen MT", Font.PLAIN, 70));
		field_pallet.setBounds(173, 366, 91, 72);
		contentPane.add(field_pallet);

		JLabel label16 = new JLabel("Ingreso de Scrap por tipo");
		label16.setForeground(Color.WHITE);
		label16.setFont(new Font("Tw Cen MT",0,24));
		label16.setBounds(25, 224, 283, 39);
		contentPane.add(label16);

		field_scrap_envase = new JFormattedTextField();
		field_scrap_envase.setFont(new Font("Tw Cen MT", Font.PLAIN, 40));
		field_scrap_envase.setBounds(472, 266, 136,62);
		contentPane.add(field_scrap_envase);


		JLabel label_Vto_barra1 = new JLabel("-");
		label_Vto_barra1.setForeground(Color.WHITE);
		label_Vto_barra1.setFont(new Font("Tw Cen MT", Font.PLAIN, 50));
		label_Vto_barra1.setBounds(631, 60, 23, 46);
		contentPane.add(label_Vto_barra1);

		JLabel label_Vto_barra1_1 = new JLabel("-");
		label_Vto_barra1_1.setForeground(Color.WHITE);
		label_Vto_barra1_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 50));
		label_Vto_barra1_1.setBounds(712, 60, 23, 46);
		contentPane.add(label_Vto_barra1_1);

		JLabel label15_1 = new JLabel("Separar gramos con punto (no con coma)");
		label15_1.setForeground(Color.WHITE);
		label15_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		label15_1.setBounds(615, 282, 269, 40);
		contentPane.add(label15_1);

		//Cierro creaci�n de componentes
	}

	//declaracion de variables
	private JTextField field_id,
					   field_lote,
					   field_cantidad,
					   field_descrip,
					   field_pallet,
					   field_scrap_prod;

	private JButton JBT_guardar;

	private JLabel info_hora,
				   info_id,
				   info_cantidad,
				   info_scrap_env,
				   info_scrap_unid,
				   info_pallets;

	public JLabel label6,
				  label12;

	private int codigo,
				id,
				pallets,
				scUnidades;

	private String producto,
				   texto_scUnid,
				   texto_scEnvases ;

	private JFormattedTextField field_vto1,
								field_vto2,
								field_vto3,
								field_scrap_envase;

	public String [] datos;
	private double scKilos;
	private float scFloat;
	//cierro declaracion de variables

	//seteo el preparedStatement
    private void KeyIntro() {
		System.out.println("entrando a KeyIntro");
    	Conexion conexion = new Conexion();
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		System.out.println("saliendo de KeyIntro");

   		try {
   			System.out.println("entrando a try");
   			cn = conexion.Conectar();
   			stm = cn.createStatement();

			System.out.println("entrando a if");
				if(field_id.getText().equals("")) {
		   			//label6.setForeground(Color.RED);
					label12.setText("ERROR, el c�digo ID no fue ingresado o es incorrecto");
					label6.setText(null);

		   		 }else {
		   			System.out.println("entrando else");
					String texto_id = field_id.getText();
					int id = Integer.parseInt(texto_id);
		   	   		rs = stm.executeQuery("SELECT * FROM "+tablaProductos+" where IdProductos='"+id+"'");
		   		    datos  = new String [2];

			   		while(rs.next()) {
			   	   		System.out.println("Ejecutando en while");
			   			//producto = rs.getString("producto");
				   	   	datos[0] = rs.getString(1); //capturo idProductos de pruebas_2022.productos
			            datos[1] = rs.getString(2);//capturo producto de pruebas_2022.productos

			   			label6.setText(datos[1]);
			   			label12.setText(null);
			   			}
		   		 }

		System.out.println("Bloque try ejecutado - Line 406");
   		}catch (SQLException e){}

    }

    private void JBTGuardar() throws UnknownHostException, SQLException {
    	Conexion conexion = new Conexion();
		Connection cn;
		Statement stm;
		ResultSet rs;
		System.out.println("clase conexion instanciada - Line 442");

		InetAddress identifPC;
		identifPC = InetAddress.getLocalHost();
		String identidad = identifPC.toString();

		System.out.println("entrando en try catch - Line 453");
   		try {
   			cn = conexion.Conectar();
   			stm = cn.createStatement();
   			rs = stm.executeQuery("SELECT * FROM "+tablaProductos);
   			System.out.println("executeQuery ejecutado");
   			System.out.println("entrando a primer if");

   			if (datos[0]==null || field_id.getText().equals("") || field_lote.getText().equals("") || field_vto1.getText().equals("")  || field_cantidad.getText().equals("")) {
   				System.out.println("adentro de primer if");
	   			info_hora.setText(null);
		   		info_cantidad.setText(null);
		   		info_id.setText(null);
		   		//label6.setForeground(Color.RED);
		   		label12.setText("Alguno de los campos obligatorios es inv�lido");
		   		label6.setText(null);

				}

	   		else {

	   			System.out.println("adentro de else");

				String texto_id = field_id.getText();
				//int id = Integer.parseInt(texto_id);
				int id = Integer.parseInt(datos[0]);

				String producto = datos[1];

				String texto_lote = field_lote.getText();
				int lote = Integer.parseInt(texto_lote);

				String texto_vto1 = field_vto1.getText();
				String texto_vto2 = field_vto2.getText();
				String texto_vto3 = field_vto3.getText();
				String texto_vto = texto_vto1 + "-" + texto_vto2 + "-" + texto_vto3;

   				String texto_cantidad = field_cantidad.getText();
				int cantidad = Integer.parseInt(texto_cantidad);

				String texto_pallets = field_pallet.getText();
				pallets = Integer.parseInt(texto_pallets);


				try {
					String texto_scUnid = field_scrap_prod.getText();
					scUnidades = Integer.parseInt(texto_scUnid);
			   		info_scrap_unid.setText(texto_scUnid);
					}catch (NumberFormatException e){
						scUnidades = 0;
						info_scrap_unid.setText("-");
					}

				try {
					String texto_scEnvases = field_scrap_envase.getText();
					//scKilos = Integer.parseInt(texto_scEnvases);
					scFloat = Float.parseFloat(texto_scEnvases);
					info_scrap_env.setText(texto_scEnvases);


					}catch (NumberFormatException e){
						scKilos = 0;
						info_scrap_env.setText("-");
					}


	   			if (cantidad < 0 || datos[0] == null)
	   			{
					System.out.println("entrando a segundo if");
			   		info_hora.setText(null);
				   	info_cantidad.setText(null);
				   	info_id.setText(null);
				   	info_scrap_env.setText(null);
				   	info_scrap_unid.setText(null);
				   	info_pallets.setText(null);
				   	//label6.setForeground(Color.RED);
				   	label12.setText("Error, la cantidad ingresada no puede ser negativa");
			   		label6.setText(null);
				}
	   			else
	   			{

					//obtener la hora y salida por pantalla con formato:
					Date date = new Date();
					DateFormat hourFormatOperador = new SimpleDateFormat("HH:mm:ss");
					String FormatOperador = hourFormatOperador.format(date);
					System.out.println(FormatOperador);


					//String sql = "INSERT into pruebas_2022.envasado_ingresos values (null, now(), now(),'"+id+"','budines 4936','"+producto+"','"+cantidad+"','"+lote+"','"+texto_vto+"','"+identidad+"','"+pallets+"','"+scUnidades+"','"+scFloat+"')";
	  				String sql = "INSERT into "+tablaInput+" values (null, now(),'"+id+"','budines 4936','"+producto+"','"+cantidad+"','"+lote+"','"+texto_vto+"','"+identidad+"','"+pallets+"','"+scUnidades+"','"+scFloat+"')";
			   		int rowsAffected = stm.executeUpdate(sql);
			   		System.out.println("Rows affected: " + rowsAffected);

			   		System.out.println("cantidad es: "+texto_cantidad);
			   		info_hora.setText(FormatOperador);
			   		info_cantidad.setText(texto_cantidad);
			   		info_id.setText(texto_id);
			   		field_cantidad.setText(null);
			   		field_pallet.setText(null);
			   		field_scrap_envase.setText(null);
			   		field_scrap_prod.setText(null);
			   		info_pallets.setText(texto_pallets);

			   		System.out.println("SQL Ejecutado");
	 			}
	   		}

   		}catch (SQLException e){
   			System.out.println(e);
   		}
   	}


	public class EventoDeTeclado implements KeyListener {

    	@Override
    	public void keyPressed(KeyEvent e) { // para escuchar tecla al presionar
    		codigo = e.getKeyCode();
    		System.out.println(codigo);
    	}

    	@Override
    	public void keyReleased(KeyEvent e) { // para escucha tecla al soltar

    	}

    	@Override
    	public void keyTyped(KeyEvent e) { // para escuchar que tecla fue presionada y soltada
    		char letra = e.getKeyChar();
    		System.out.println(letra);
    	}
    }

    class CambiaEstado implements WindowStateListener {
    	@Override
    	public void windowStateChanged(java.awt.event.WindowEvent e) {
    		System.out.println("el estado de la ventana ha cambiado");
    		System.out.println(e.getNewState());//recibo el c�digo que identifica el tipo de evento o�do
    	}
    }
}




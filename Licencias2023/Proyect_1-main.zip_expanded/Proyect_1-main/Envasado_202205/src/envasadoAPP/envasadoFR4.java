package envasadoAPP;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import conector.ConexionLH;

public class envasadoFR4 extends JFrame {

	private JPanel contentPane;
	private Connection cn; //hago que el object cn sea accesible desde toda la clase.
	private PreparedStatement pst1, pst2;
	private JTable tablaLectura;
	private String incognita1, incognita2, incognita3,incognita4;
	private JComboBox<String> izquierdo, izqMedio, derMedio, derecho;
	private JButton jb1;
	private JTextField FieldIZQ,FieldDER;


	private final String pstConsulta1 = "SELECT fechario, idprod, planta, producto, cajas, pallet, lote, vto "
			+ "FROM dbase.envasado_ingresos WHERE planta='pan dulces 4945' and fechario >=? AND fechario <=?"; // FINAL para que sea constante y no pueda ser modificada
	//WHERE fecha BETWEEN ? AND ? AND hora BETWEEN ? AND ?";

	private String pstConsulta2 = "SELECT fecha, hora, idProductos, producto, cant_movim, tipo_movim, usuario_movim "
			+ "FROM dbase.reg_movimientos_pt WHERE fecha >=? AND fecha <=?";

/*	private final String pstConsulta1 = "SELECT fecha, hora, idprod, planta, producto, cajas, pallet, lote, vto "
			+ "FROM dbase.envasado_ingresos WHERE fecha BETWEEN ? AND ? AND hora BETWEEN ? AND ?"; // FINAL para que sea constante y no pueda ser modificada
	//WHERE fecha BETWEEN ? AND ? AND hora BETWEEN ? AND ?";
*/
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					envasadoFR4 frame = new envasadoFR4();
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
	public envasadoFR4() {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 10, 950, 660);
		setTitle("04 - PLANTA PAN DULCES: LECTURA REGISTRO DE ENVASADO");
		contentPane = new JPanel();
		contentPane.setBorder(javax.swing.BorderFactory.createTitledBorder(" DETALLE PARA LECTURA USUARIO "));
		contentPane.setLayout(null);
		setContentPane(contentPane);

        try {
        	UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        	//UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        	//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        	//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	ConexionLH conexion = new ConexionLH();
    	cn = null;
    	Statement stm = null;
    	ResultSet rs = null;

		izquierdo = new JComboBox();
		izquierdo.setBounds(25, 58, 170, 20);
		contentPane.add(izquierdo);
		izquierdo.setFont(new Font("Tw Cen MT", 0, 14));
		izquierdo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox box1 = (JComboBox)e.getSource();
				incognita1 = (String) box1.getSelectedItem();
				}
			});

		derMedio = new JComboBox();
		derMedio.setBounds(225, 58, 170, 20);
		contentPane.add(derMedio);
		derMedio.setFont(new Font("Tw Cen MT", 0, 14));
		derMedio.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox box2 = (JComboBox)e.getSource();
				incognita2 = (String) box2.getSelectedItem();
				}
			});

		try {

	        cn = conexion.Conectar();
	        stm = cn.createStatement();


			// -- CARGA DATOS EN COMBOX IZQUIERDO   DISTINCTROW
			String consulta = "SELECT fechario FROM dbase.envasado_ingresos WHERE planta='pan dulces 4945' and fechario >= DATE_ADD(CURDATE(), INTERVAL -15 DAY) AND fechario <= NOW()";
			rs = stm.executeQuery(consulta);

	        while(rs.next()) {
	        	izquierdo.addItem(rs.getString(1));
	        }
	        rs.close();

			// -- CARGA DATOS EN COMBOX DERECHO
			consulta = "SELECT fechario FROM dbase.envasado_ingresos WHERE planta='pan dulces 4945' and fechario >= DATE_ADD(CURDATE(), INTERVAL -15 DAY) AND fechario <= NOW()";
			rs = stm.executeQuery(consulta);

	        while(rs.next()) {
	        	derMedio.addItem(rs.getString(1));
	        }
	        rs.close();

		}catch (Exception e2) {
			e2.printStackTrace();

		}

        JButton jb1 = new JButton();
		jb1.setBounds(438, 58, 141, 20);
		jb1.setText("Consulta r�pida");
		contentPane.add(jb1);
		jb1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ejecutaConsulta1();
				}
			});


		JLabel label1 = new JLabel("Desde Fecha y Hora:");
		label1.setBounds(25, 41, 168, 14);
		label1.setFont(new Font ("Tw Cen MT", 0, 15));
		contentPane.add(label1);

		JLabel label3 = new JLabel("Hasta Fecha y Hora:");
		label3.setBounds(225, 41, 178, 14);
		label3.setFont(new Font("Tw Cen MT",0,15));
		contentPane.add(label3);

        tablaLectura = new JTable();
	    tablaLectura.setFont(new Font("Tw Cen MT", 0, 12));
        tablaLectura.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {

        	}
        ));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 120, 900, 500);
        contentPane.add(scrollPane);
        scrollPane.add(tablaLectura);
        scrollPane.setViewportView(tablaLectura);

/*
        FieldIZQ = new JTextField();
        FieldIZQ.setBounds(25, 89, 170, 20);
        FieldIZQ.setFont(new Font("Tw Cen MT", 0, 14));
        contentPane.add(FieldIZQ);
        FieldIZQ.setColumns(10);


        FieldDER = new JTextField();
        FieldDER.setBounds(225, 89, 170, 20);
        FieldDER.setFont(new Font("Tw Cen MT", 0, 14));
        contentPane.add(FieldDER);
        FieldDER.setColumns(10);


        JButton jb2 = new JButton();
        jb2.setBounds(438, 89, 141, 20);
        jb2.setText("Consulta manual");
		contentPane.add(jb2);
		jb2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ejecutaConsulta2();
				}
			});
*/


	}
		public void ejecutaConsulta1() {
			System.out.println("entrando a ejecutaCBoxes");

	    	ResultSet rs = null;

		    DefaultTableModel modelo = new DefaultTableModel();
		    modelo.addColumn("Fecha y Hora");
		    modelo.addColumn("Id");
		    modelo.addColumn("Planta");
		    modelo.addColumn("Producto");
		    modelo.addColumn("Cajas");
		    modelo.addColumn("Pallet");
		    modelo.addColumn("Lote");
		    modelo.addColumn("Vencimiento");
		    tablaLectura.setModel(modelo);
		    tablaLectura.setFont(new Font("Arial", 0, 13));


		    String [] datos  = new String [8];

		    //indico ancho de cada columna
		    TableColumnModel columnModel = tablaLectura.getColumnModel();
		    columnModel.getColumn(0).setPreferredWidth(85);
		    columnModel.getColumn(1).setPreferredWidth(10);
		    columnModel.getColumn(2).setPreferredWidth(50);
		    columnModel.getColumn(3).setPreferredWidth(360);
		    columnModel.getColumn(4).setPreferredWidth(10);
		    columnModel.getColumn(5).setPreferredWidth(5);
		    columnModel.getColumn(6).setPreferredWidth(5);
		    columnModel.getColumn(7).setPreferredWidth(40);


		    //Instanciamos el TableRowSorter y lo a�adimos al JTable
		    TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
		    tablaLectura.setRowSorter(elQueOrdena);


			try {

				System.out.println("entrando en Try");

		    	rs = null;

				PreparedStatement pst1 = cn.prepareStatement(pstConsulta1); //pstConsulta por tipo y fecha.-
				pst1.setString(1, incognita1);
				pst1.setString(2, incognita2);
				//pst1.setString(3, incognita3);
				//pst1.setString(4, incognita4);

				rs = pst1.executeQuery();
				System.out.println("linea post executeQuery");
				System.out.println(incognita1);
				System.out.println(incognita2);
				//System.out.println(incognita2);
				//System.out.println(incognita4);

				while(rs.next()) {
					System.out.println("entrando en while rs.next");
					datos[0] = rs.getString(1);
		            datos[1] = rs.getString(2);
		            datos[2] = rs.getString(3);
		            datos[3] = rs.getString(4);
		            datos[4] = rs.getString(5);
		            datos[5] = rs.getString(6);
		            datos[6] = rs.getString(7);
		            datos[7] = rs.getString(8);
		            //datos[8] = rs.getString(9);
		            modelo.addRow(datos);
		            System.out.println("saliendo del while");
		        }
		        tablaLectura.setModel(modelo);

			}catch (Exception e2) {
				e2.printStackTrace();
				tablaLectura.setModel(null);
			}

	}

		public void ejecutaConsulta2() {
			System.out.println("entrando a ejecuta 2");

	    	ResultSet rs = null;

		    DefaultTableModel modelo = new DefaultTableModel();
		    modelo.addColumn("Fecha y Hora");
		    modelo.addColumn("Id");
		    modelo.addColumn("Planta");
		    modelo.addColumn("Producto");
		    modelo.addColumn("Cajas");
		    modelo.addColumn("Pallet");
		    modelo.addColumn("Lote");
		    modelo.addColumn("Vencimiento");
		    tablaLectura.setModel(modelo);
		    tablaLectura.setFont(new Font("Arial", 0, 13));

		    String [] datos  = new String [8];

		    //indico ancho de cada columna
		    TableColumnModel columnModel = tablaLectura.getColumnModel();
		    columnModel.getColumn(0).setPreferredWidth(85);
		    columnModel.getColumn(1).setPreferredWidth(10);
		    columnModel.getColumn(2).setPreferredWidth(50);
		    columnModel.getColumn(3).setPreferredWidth(360);
		    columnModel.getColumn(4).setPreferredWidth(10);
		    columnModel.getColumn(5).setPreferredWidth(5);
		    columnModel.getColumn(6).setPreferredWidth(5);
		    columnModel.getColumn(7).setPreferredWidth(40);


		    //Instanciamos el TableRowSorter y lo a�adimos al JTable
		    TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
		    tablaLectura.setRowSorter(elQueOrdena);


			try {

				System.out.println("entrando en Try");

				incognita3 = FieldIZQ.getText();
				incognita4 = FieldDER.getText();

		    	rs = null;

				PreparedStatement pst2 = cn.prepareStatement(pstConsulta1); //pstConsulta por tipo y fecha.-
				pst2.setString(1, incognita3);
				pst2.setString(2, incognita4);
				System.out.println("Icong3 "+ incognita3);
				System.out.println("Icong4 "+ incognita4);

				rs = pst1.executeQuery();
				System.out.println("linea post executeQuery");
				System.out.println(incognita1);
				System.out.println(incognita2);
				//System.out.println(incognita2);
				//System.out.println(incognita4);

				while(rs.next()) {
					System.out.println("entrando en while rs.next");
					datos[0] = rs.getString(1);
		            datos[1] = rs.getString(2);
		            datos[2] = rs.getString(3);
		            datos[3] = rs.getString(4);
		            datos[4] = rs.getString(5);
		            datos[5] = rs.getString(6);
		            datos[6] = rs.getString(7);
		            datos[7] = rs.getString(8);
		            //datos[8] = rs.getString(9);
		            modelo.addRow(datos);
		            System.out.println("saliendo del while");
		        }
		        tablaLectura.setModel(modelo);

			}catch (Exception e2) {
				e2.printStackTrace();
				tablaLectura.setModel(null);
			}
	  }
}

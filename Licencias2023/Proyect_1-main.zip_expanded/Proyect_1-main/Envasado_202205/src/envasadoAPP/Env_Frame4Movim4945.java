package envasadoAPP;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import conector.Conexion;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import reportes.LeerEnvasado;

public class Env_Frame4Movim4945 extends JFrame {

	String tablaInput = "pruebas_2022.env_input_budines";
	String tablaProductos = "pruebas_2022.productos";

	private JPanel contentPane;
	private JTable tablaLectura;
	private DefaultTableModel modelo;
	public String incognita1, incognita2;
	private JComboBox<String> izquierdo, derecho;
	public String [] datos;
	public List<LeerEnvasado> listItems;

	private Connection cn;
	private PreparedStatement pstguardarEnEnvasado;
	public int flag = 1;


	private String pst = "SELECT numPallet, fechaIngreso, idProducto, producto, "
			+ "cajasInformadas, lote, vencimiento "
			+ "FROM "+tablaInput+" where numPallet >=? AND numPallet <=? AND flagDisponible = '"+flag+"' ";



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Env_Frame4Movim4945 frame = new Env_Frame4Movim4945();
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
	public Env_Frame4Movim4945() {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 10, 950, 660);
		setTitle("02 - PLANTA BUDINES: LECTURA REGISTRO DE ENVASADO");
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

    	Conexion conexion = new Conexion();
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

		derecho = new JComboBox();
		derecho.setBounds(225, 58, 170, 20);
		contentPane.add(derecho);
		derecho.setFont(new Font("Tw Cen MT", 0, 14));
		derecho.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox box2 = (JComboBox)e.getSource();
				incognita2 = (String) box2.getSelectedItem();
				}
			});

		try {

			LocalDate fechaHoy = LocalDate.now();
			int diaDelAnio = fechaHoy.getDayOfYear();   // int con el valor del dia
			int diaAntes = diaDelAnio -30;

	        cn = Conexion.Conectar();
	        stm = cn.createStatement();

			// -- CARGA DATOS EN COMBOX IZQUIERDO   DISTINCTROW
			String consulta = "SELECT fechaIngreso FROM pruebas_2022.env_input_budines "
			+ "WHERE planta='Planta: 4936' and lote >='"+diaAntes+"' "
					+ "and lote <='"+diaDelAnio+"'  ";

			rs = stm.executeQuery(consulta);

	        while(rs.next()) {
	        	izquierdo.addItem(rs.getString(1));
	        }
	        rs.close();

			// -- CARGA DATOS EN COMBOX DERECHO
			rs = stm.executeQuery(consulta);

	        while(rs.next()) {
	        	derecho.addItem(rs.getString(1));
	        }
	        rs.close();

		}catch (Exception e2) {
			e2.printStackTrace();
			JOptionPane.showMessageDialog(null, e2);

		}

        JButton jb1 = new JButton();
		jb1.setBounds(438, 58, 141, 20);
		jb1.setText("Consulta r�pida");
		contentPane.add(jb1);
		jb1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//ejecutaConsulta1();
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

        JButton jb3 = new JButton("Imprimir");
        jb3.setBounds(748, 58, 91, 20);
        contentPane.add(jb3);
        jb3.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		try {
        			Reporte02();
				} catch (JRException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e);
				}
        	}
        });
	}


		public  void Reporte02() throws JRException {

	    	/*
	    	 	    		PrintEnvasado listado = new PrintEnvasado(String.valueOf(tablaLectura.getValueAt(fila, 0).toString()),
	    				String.valueOf(tablaLectura.getValueAt(fila, 1).toString()),
	    				String.valueOf(tablaLectura.getValueAt(fila, 2).toString()),
	    				String.valueOf(tablaLectura.getValueAt(fila, 3).toString()),
	    				String.valueOf(tablaLectura.getValueAt(fila, 4).toString()),
	    				String.valueOf(tablaLectura.getValueAt(fila, 5).toString()),
	    				String.valueOf(tablaLectura.getValueAt(fila, 6).toString()));
	    				lista.add(listado);
	    	*/

	    	/* Convert List to JRBeanCollectionDataSource */
	    	JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);

	        /* Map to hold Jasper report Parameters */
	        Map<String, Object> parameters = new HashMap<>();
	        parameters.put("CollectionBeanParam", itemsJRBean);
	        parameters.put("P_DesdeCuando", "Per�odo analizado Desde: "+incognita1+" || Hasta: "+incognita2);
	        parameters.put("P_DesdeCuando_piepag", "Las partes asumen la responsabilidad de registro y verificaci�n por el per�odo analizado: desde "+incognita1+" --- Hasta "+incognita2);
	        //read jrxml file and creating jasperdesign object
	        InputStream input;
			try {
				//input = new FileInputStream(new File("C:\\MyReports\\env_informe.jrxml"));
				input = new FileInputStream(new File("src\\envasadoAPP\\env_registros.jrxml"));
				JasperDesign jasperDesign = JRXmlLoader.load(input);

		        /*compiling jrxml with help of JasperReport class*/
		        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

		        /* Using jasperReport object to generate PDF */
		        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

		        /*call jasper engine to display report in jasperviewer window*/
		        //JasperViewer.viewReport(jasperPrint, false);
				JasperViewer jasperViewer = new JasperViewer(jasperPrint, false); // el ,false = hide_on_close
				jasperViewer.setVisible(true);
				jasperViewer.setTitle("Informe de Envasado");


			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }


		public void consultaSumaTotal() {

			Conexion conexion = new Conexion();
			Connection cn;
			Statement stm;
			ResultSet rs;

			System.out.println("entrando a consultar");

			DefaultTableModel modelo = new DefaultTableModel();
			modelo.addColumn("Orden N�");
			modelo.addColumn("Envasado");
			modelo.addColumn("id");
			modelo.addColumn("Descripci�n");
			modelo.addColumn("Cajas");
			modelo.addColumn("Lote");
			modelo.addColumn("Vencimiento");
			tablaLectura.setModel(modelo);

			//indico ancho de cada columna
			TableColumnModel columnModel = tablaLectura.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(5);
			columnModel.getColumn(1).setPreferredWidth(100);
			columnModel.getColumn(2).setPreferredWidth(10);
			columnModel.getColumn(3).setPreferredWidth(380);
			columnModel.getColumn(4).setPreferredWidth(5);
			columnModel.getColumn(5).setPreferredWidth(10);
			columnModel.getColumn(6).setPreferredWidth(60);

			//Instanciamos el TableRowSorter y lo a�adimos al JTable
			TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
			tablaLectura.setRowSorter(elQueOrdena);


			try {

				System.out.println("entrando en Try");
				cn = Conexion.Conectar();
				stm = cn.createStatement();
				rs = stm.executeQuery(""
					+ "SELECT numPallet, fechaIngreso, idProducto, producto, "
					+ "cajasInformadas, lote, vencimiento "
					+ "FROM "+tablaInput+" where flagDisponible = '"+flag+"' ");

//				String [] datos  = new String [7];
				datos  = new String [7];

				// codigo 04.2022
				//rsDos = stm.executeQuery("SELECT numPallet FROM "+tablaInput+" where fechaIngreso ='"+fechaIngreso+"' ");
				System.out.println("linea post executeQuery");

				while(rs.next()) {
//					System.out.println("datos[0] = rs.getString(1)");
					datos[0] = rs.getString(1);
					datos[1] = rs.getString(2);
					datos[2] = rs.getString(3);
					datos[3] = rs.getString(4);
					datos[4] = rs.getString(5);
					datos[5] = rs.getString(6);
					datos[6] = rs.getString(7);
					modelo.addRow(datos);
					System.out.println("modelo.addRow(datos)");
				}
				tablaLectura.setModel(modelo);

				//List <PrintEnvasado> listItems= new ArrayList<PrintEnvasado>();
				listItems= new ArrayList<>();

				for (int i = 0; i<tablaLectura.getRowCount(); i++) {
					LeerEnvasado listado = new LeerEnvasado(
							tablaLectura.getValueAt(i, 0).toString(),
							tablaLectura.getValueAt(i, 1).toString(),
							tablaLectura.getValueAt(i, 2).toString(),
							tablaLectura.getValueAt(i, 3).toString(),
							tablaLectura.getValueAt(i, 4).toString(),
							tablaLectura.getValueAt(i, 5).toString(),
							tablaLectura.getValueAt(i, 6).toString());
					listItems.add(listado);
				}

				if(datos [0] == null) {
					JOptionPane.showMessageDialog(null, "No hay datos disponibles");
				}

			}catch (Exception e2) {
				e2.printStackTrace();
				tablaLectura.setModel(null);
			}

		}


		public void consultaPorInput() {

			Conexion conexion = new Conexion();
			Connection cn;
//			Statement stm;
			ResultSet rs;

			System.out.println("entrando a consultar");

			DefaultTableModel modelo = new DefaultTableModel();
			modelo.addColumn("Orden N�");
			modelo.addColumn("Envasado");
			modelo.addColumn("id");
			modelo.addColumn("Descripci�n");
			modelo.addColumn("Cajas");
			modelo.addColumn("Lote");
			modelo.addColumn("Vencimiento");
			tablaLectura.setModel(modelo);

			//indico ancho de cada columna
			TableColumnModel columnModel = tablaLectura.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(5);
			columnModel.getColumn(1).setPreferredWidth(100);
			columnModel.getColumn(2).setPreferredWidth(10);
			columnModel.getColumn(3).setPreferredWidth(380);
			columnModel.getColumn(4).setPreferredWidth(5);
			columnModel.getColumn(5).setPreferredWidth(10);
			columnModel.getColumn(6).setPreferredWidth(60);

			//Instanciamos el TableRowSorter y lo a�adimos al JTable
			TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
			tablaLectura.setRowSorter(elQueOrdena);

			try {

				System.out.println("entrando en Try");
				cn = Conexion.Conectar();
//				stm = cn.createStatement();
//				rs = stm.executeQuery(""
//					+ "SELECT numPallet, fechaIngreso, idProducto, producto, "
//					+ "cajasInformadas, lote, vencimiento "
//					+ "FROM "+tablaInput+" where flagDisponible = '"+flag+"' ");

//				String [] datos  = new String [7];
				datos  = new String [7];

				// codigo 04.2022
				//rsDos = stm.executeQuery("SELECT numPallet FROM "+tablaInput+" where fechaIngreso ='"+fechaIngreso+"' ");
				System.out.println("linea post executeQuery");

				PreparedStatement pstFinal = cn.prepareStatement(pst); //pstConsulta por tipo y fecha.-
				pstFinal.setString(1, incognita1);
				pstFinal.setString(2, incognita2);

				int num1 = Integer.parseInt(incognita1);
				int num2 = Integer.parseInt(incognita2);

				if(num1 <= num2) {

					rs = pstFinal.executeQuery();

					while(rs.next()) {
//						System.out.println("datos[0] = rs.getString(1)");
						datos[0] = rs.getString(1);
						datos[1] = rs.getString(2);
						datos[2] = rs.getString(3);
						datos[3] = rs.getString(4);
						datos[4] = rs.getString(5);
						datos[5] = rs.getString(6);
						datos[6] = rs.getString(7);
						modelo.addRow(datos);
						System.out.println("modelo.addRow(datos)");
					}
					tablaLectura.setModel(modelo);

					//List <PrintEnvasado> listItems= new ArrayList<PrintEnvasado>();
					listItems= new ArrayList<>();

					for (int i = 0; i<tablaLectura.getRowCount(); i++) {
						LeerEnvasado listado = new LeerEnvasado(
								tablaLectura.getValueAt(i, 0).toString(),
								tablaLectura.getValueAt(i, 1).toString(),
								tablaLectura.getValueAt(i, 2).toString(),
								tablaLectura.getValueAt(i, 3).toString(),
								tablaLectura.getValueAt(i, 4).toString(),
								tablaLectura.getValueAt(i, 5).toString(),
								tablaLectura.getValueAt(i, 6).toString());
						listItems.add(listado);
					}


					if(datos [0] == null) {
						JOptionPane.showMessageDialog(null, "No hay datos disponibles");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "El intervalo ingresado es incorrecto. \n\n'Desde' debe ser menor o igual a 'Hasta'\n\n");
				}


			}catch (Exception e2) {
				e2.printStackTrace();
				tablaLectura.setModel(null);
			}

		}

}
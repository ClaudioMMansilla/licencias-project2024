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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import reportes.PrintEnvasado;

public class envasadoFR3 extends JFrame {

	private JPanel contentPane;
	private Connection cn; //hago que el object cn sea accesible desde toda la clase.
	private PreparedStatement pst1, pst2;
	private JTable tablaLectura;
	public String incognita1, incognita2, incognita3,incognita4;
	private JComboBox<String> izquierdo, izqMedio, derMedio, derecho;
	private JButton jb1;
	private JTextField FieldIZQ,FieldDER;
	public String [] datos;
	public List listItems;


	private final String pstConsulta1 = "SELECT fechario, planta, idprod, producto, lote, sum(cajas) as Qcajas, sum(scrap_unid) as QScrap  "
			+ "FROM dbase.envasado_ingresos WHERE planta='budines 4936' AND fechario >=? AND fechario <=? group by idprod"; // FINAL para que sea constante y no pueda ser modificada
	//WHERE fecha BETWEEN ? AND ? AND hora BETWEEN ? AND ?";

	private String pstConsulta2 = "SELECT fecha, hora, idProductos, producto, cant_movim, tipo_movim, usuario_movim "
			+ "FROM dbase.reg_movimientos_pt WHERE fecha >=? AND fecha <=?";

/*	private final String pstConsulta1 = "SELECT fechario, idprod, planta, producto, cajas, pallet, lote, vto "
			+ "FROM dbase.envasado_ingresos WHERE fechario >=? AND fechario <=?"; // FINAL para que sea constante y no pueda ser modificada
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
					envasadoFR3 frame = new envasadoFR3();
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
	public envasadoFR3() {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 10, 950, 660);
		setTitle("03 - PLANTA BUDINES: INFORME DE ENVASADO");
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
			String consulta = "SELECT fechario FROM dbase.envasado_ingresos WHERE planta='budines 4936' and fechario >= DATE_ADD(CURDATE(), INTERVAL -30 DAY) AND fechario <= NOW()";
			rs = stm.executeQuery(consulta);

	        while(rs.next()) {
	        	izquierdo.addItem(rs.getString(1));
	        }
	        rs.close();

			// -- CARGA DATOS EN COMBOX DERECHO
			consulta = "SELECT fechario FROM dbase.envasado_ingresos WHERE planta='budines 4936' and fechario >= DATE_ADD(CURDATE(), INTERVAL -30 DAY) AND fechario <= NOW()";
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

 /*       JButton jb2 = new JButton();
        jb2.setBounds(438, 89, 141, 20);
        jb2.setText("Consulta manual");
		contentPane.add(jb2);
		jb2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//ejecutaConsulta2();
				}
			});
*/
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

/*        FieldIZQ = new JTextField();
        FieldIZQ.setBounds(25, 89, 170, 20);
        FieldIZQ.setFont(new Font("Tw Cen MT", 0, 14));
        contentPane.add(FieldIZQ);
        FieldIZQ.setColumns(10);


        FieldDER = new JTextField();
        FieldDER.setBounds(225, 89, 170, 20);
        FieldDER.setFont(new Font("Tw Cen MT", 0, 14));
        contentPane.add(FieldDER);
        FieldDER.setColumns(10);
*/
        JButton jb3 = new JButton("Imprimir");
        jb3.setBounds(748, 58, 91, 20);
        contentPane.add(jb3);
        jb3.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		try {
        			Reporte03();
				} catch (JRException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e);
				}
        	}
        });

	}

		public void ejecutaConsulta1() {
			System.out.println("entrando a ejecutaCBoxes");

	    	ResultSet rs = null;

		    DefaultTableModel modelo = new DefaultTableModel();
		    modelo.addColumn("Fecha y Hora");
		    modelo.addColumn("Planta");
		    modelo.addColumn("Id");
		    modelo.addColumn("Producto");
		    modelo.addColumn("Lote");
		    modelo.addColumn("Cajas");
		    //modelo.addColumn("Pallets");
		    modelo.addColumn("Scrap");

		    tablaLectura.setModel(modelo);
		    tablaLectura.setFont(new Font("Arial", 0, 13));

		    datos  = new String [7];

		    //indico ancho de cada columna
		    TableColumnModel columnModel = tablaLectura.getColumnModel();
		    columnModel.getColumn(0).setPreferredWidth(85);
		    columnModel.getColumn(1).setPreferredWidth(50);
		    columnModel.getColumn(2).setPreferredWidth(10);
		    columnModel.getColumn(3).setPreferredWidth(360);
		    columnModel.getColumn(4).setPreferredWidth(10);
		    columnModel.getColumn(5).setPreferredWidth(10);
		    columnModel.getColumn(6).setPreferredWidth(10);
		    //columnModel.getColumn(7).setPreferredWidth(10);

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
		            //datos[7] = rs.getString(8);
		            //datos[8] = rs.getString(9);
		            modelo.addRow(datos);
		            System.out.println("saliendo del while");
		        }
		        tablaLectura.setModel(modelo);

				//List <PrintEnvasado> listItems= new ArrayList<PrintEnvasado>();
				listItems= new ArrayList();


		    	for (int fila = 0; fila<tablaLectura.getRowCount(); fila++) {
		    		PrintEnvasado listado = new PrintEnvasado(tablaLectura.getValueAt(fila, 0).toString(),
		    				tablaLectura.getValueAt(fila, 1).toString(),
		    				tablaLectura.getValueAt(fila, 2).toString(),
		    				tablaLectura.getValueAt(fila, 3).toString(),
		    				tablaLectura.getValueAt(fila, 4).toString(),
		    				tablaLectura.getValueAt(fila, 5).toString(),
		    				tablaLectura.getValueAt(fila, 6).toString());
		    				listItems.add(listado);

		    				}

			}catch (Exception e2) {
				e2.printStackTrace();
				tablaLectura.setModel(null);
			}

	}

	/*	public void ejecutaConsulta2() {
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
		    TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(modelo);
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

	*/


		public  void Reporte03() throws JRException {


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

	        //read jrxml file and creating jasperdesign object
	        InputStream input;
			try {
				//input = new FileInputStream(new File("C:\\MyReports\\env_informe.jrxml"));
				input = new FileInputStream(new File("src\\envasadoAPP\\env_informe.jrxml"));
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

/*	    	JasperReport reporte2 = (JasperReport) JRLoader.loadObjectFromFile("C:\\MyReports\\reporte2.jasper");

	    	//creo el mapeo para insertar el par�metro que sera enviado a Jasper Report
	    	Map parametros = new HashMap();

	    	parametros.put("datos_0", lista);

	    	//el fillmanager necesita argumento (objeto jasper, el parametro mapeado, conector tipo Connection)
	    	JasperPrint print = JasperFillManager.fillReport(
					//reporte2, parametros,new JREmptyDataSource());
	    			reporte2, parametros,new JRBeanCollectionDataSource(lista));

			JasperViewer jasperViewer = new JasperViewer(print, false); // el ,false = hide_on_close
			jasperViewer.setVisible(true);
*/


	}
}

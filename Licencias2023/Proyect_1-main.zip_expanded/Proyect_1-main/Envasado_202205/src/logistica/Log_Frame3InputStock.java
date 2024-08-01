package logistica;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import conector.Conexion;
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

public class Log_Frame3InputStock extends JFrame {

//	String tablaInput = "pruebas_2022.test_inputsbudines";
//	String tablaProductos = "desarrollo.productos";

	String tablaInput = "pruebas_2022.env_input_budines";
	String tablaProductos = "pruebas_2022.productos";

	private JPanel contentPane;
	private JTable tablaLectura;
	private DefaultTableModel modelo;
	public String incognita1, incognita2;
	private JComboBox<String> comboBoxIZQ, comboBoxDER;
	public String [] datos;
	public List<LeerEnvasado> listItems;

	private Connection cn;
	private PreparedStatement pstguardarEnEnvasado;
	public int flag = 1;

	private final String queryBox =
	"SELECT numPallet FROM pruebas_2022.env_input_budines WHERE flagDisponible = 1";

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
					Log_Frame3InputStock frame = new Log_Frame3InputStock();
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
	public Log_Frame3InputStock() {

		addWindowListener(new Ventana()); //pongo la ventana en oyente
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 10, 960, 660);
		setTitle("03 - PLANTA BUDINES: INFORME DE ENVASADO ");
		contentPane = new JPanel();
		contentPane.setBorder(javax.swing.BorderFactory.createTitledBorder(" DETALLE PARA LECTURA USUARIO "));
		contentPane.setLayout(null);
		setResizable(false);
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


		tablaLectura = new JTable();
		tablaLectura.setRowHeight(24);
		tablaLectura.setFont(new Font("Arial", 1, 14));
		tablaLectura.setForeground(Color.BLUE);
		tablaLectura.setBackground(Color.lightGray);
		tablaLectura.setShowVerticalLines(false);
		tablaLectura.setSurrendersFocusOnKeystroke(true);
		tablaLectura.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {

				}
				));

//		tablaLectura.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//		    @Override
//		    public void valueChanged(ListSelectionEvent e) {
//		        String escuchaPallet = tablaLectura.getValueAt(tablaLectura.getSelectedRow(), 0).toString();
//		        String escuchaId = tablaLectura.getValueAt(tablaLectura.getSelectedRow(), 2).toString();
//		        String escuchaCajas = tablaLectura.getValueAt(tablaLectura.getSelectedRow(), 4).toString();
//		        System.out.println("Nro Pallet: " + escuchaPallet + " | Prod ID: " + escuchaId + " | Cantidad: " + escuchaCajas);
//		    }
//		});


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 120, 934, 402);
		contentPane.add(scrollPane);
		scrollPane.add(tablaLectura);
		scrollPane.setViewportView(tablaLectura);
		consultaTotal();


		JButton jb1 = new JButton("TOTALES ");
		jb1.setBounds(466, 58, 90, 25);
		contentPane.add(jb1);
		jb1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				consultaTotal();
			}
		});


		JButton jb3 = new JButton("Guardar");
		jb3.setBounds(836, 58, 90, 25);
		contentPane.add(jb3);
		jb3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			      JPanel panel = new JPanel();
			      panel.setSize(new Dimension(250, 250));
			      panel.setLayout(null);
//			      JLabel label1 = new JLabel("Confirmar acci�n ");
//			      label1.setVerticalAlignment(SwingConstants.BOTTOM);
//			      label1.setBounds(10, 20, 300, 100);
//			      label1.setHorizontalAlignment(SwingConstants.LEFT);
//			      panel.add(label1);
			      JLabel label2 = new JLabel("�Desea continuar? ");
			      label2.setVerticalAlignment(SwingConstants.TOP);
			      label2.setHorizontalAlignment(SwingConstants.LEFT);
			      label2.setBounds(10, 20, 300, 100);
			      panel.add(label2);
			      UIManager.put("OptionPane.minimumSize", new Dimension(300, 100));
			      int res = JOptionPane.showConfirmDialog(null, panel, "Confirm",
			      JOptionPane.YES_NO_OPTION);
			      if(res == 0)
			      {
			    	  try {
			    		  guardarEnEnvasado(e);
			    		  guardarEnStock(e);
			    	  } catch (SQLException | UnknownHostException e1) {
			    		  // TODO Auto-generated catch block
			    		  e1.printStackTrace();
			    		  JOptionPane.showMessageDialog(null, e1);
			    	  }

			    	  System.out.println("Pressed YES");

			      } else if (res == 1) {
			         System.out.println("Pressed NO");
			         JOptionPane.showMessageDialog(null, "La acci�n ha sido cancelada");

			      } else {
			         System.out.println("Pressed CANCEL");
			      }



//				JOptionPane.showMessageDialog(null, "Opci�n restringida, consultar a logistica");
			}
		});

		JButton jb4 = new JButton("Filtrar");
		jb4.setBounds(233, 58, 90, 25);
		contentPane.add(jb4);
		jb4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				consultaFiltrada();
			}
		});


		JButton jb2 = new JButton("Imprimir");
		jb2.setBounds(733, 58, 90, 25);
		contentPane.add(jb2);
		jb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Reporte();
				} catch (JRException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});


		JLabel lblHasta = new JLabel("Desde: ");
		lblHasta.setBounds(28, 42, 90, 14);
		contentPane.add(lblHasta);

		JLabel lblDesde = new JLabel("Hasta: ");
		lblDesde.setBounds(133, 42, 90, 14);
		contentPane.add(lblDesde);

		comboBoxIZQ = new JComboBox<>();
		comboBoxIZQ.setBounds(28, 58, 90, 23);
		contentPane.add(comboBoxIZQ);
		comboBoxIZQ.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("rawtypes")
				JComboBox box1 = (JComboBox)e.getSource();
				incognita1 = (String) box1.getSelectedItem();
				}
			});

		comboBoxDER = new JComboBox<>();
		comboBoxDER.setBounds(133, 58, 90, 23);
		contentPane.add(comboBoxDER);
		comboBoxDER.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("rawtypes")
				JComboBox box2 = (JComboBox)e.getSource();
				incognita2 = (String) box2.getSelectedItem();
				}
			});

		try {
		     //Connection miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbase?serverTimezone=UTC", "root", "admin");
			//java.sql.Statement sentencia = miConexion.createStatement();
			Conexion conexion = new Conexion();
			Connection cn;
			Statement stm;
			ResultSet rs;

	        cn = Conexion.Conectar();
	        stm = cn.createStatement();

			// -- CARGA DATOS EN COMBOX IZQUIERDO
			rs = stm.executeQuery(queryBox);
	        while(rs.next()) {
	        	comboBoxIZQ.addItem(rs.getString(1));
	        }
	        rs.close();

			// -- CARGA DATOS EN COMBOX DERECHO
			rs = stm.executeQuery(queryBox);
	        while(rs.next()) {
	        	comboBoxDER.addItem(rs.getString(1));
	        }
	       rs.close();

		}catch (Exception e2) {
			e2.printStackTrace();
			JOptionPane.showMessageDialog(null, e2);
		}

	}

	public void consultaTotal() {

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

//			String [] datos  = new String [7];
			datos  = new String [7];

			// codigo 04.2022
			//rsDos = stm.executeQuery("SELECT numPallet FROM "+tablaInput+" where fechaIngreso ='"+fechaIngreso+"' ");
			System.out.println("linea post executeQuery");

			while(rs.next()) {
//				System.out.println("datos[0] = rs.getString(1)");
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


	public void consultaFiltrada() {

		Conexion conexion = new Conexion();
		Connection cn;
//		Statement stm;
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
//			stm = cn.createStatement();
//			rs = stm.executeQuery(""
//				+ "SELECT numPallet, fechaIngreso, idProducto, producto, "
//				+ "cajasInformadas, lote, vencimiento "
//				+ "FROM "+tablaInput+" where flagDisponible = '"+flag+"' ");

//			String [] datos  = new String [7];
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


    private void guardarEnEnvasado(ActionEvent evt) throws SQLException, UnknownHostException {

    	Conexion conexion = new Conexion();
    	Connection cn;
    	Statement stm;
//    	ResultSet rs;

    	Date diaHoy = new Date();
    	DateFormat horaFormatoHMS = new SimpleDateFormat("HH:mm");
    	String horaMinSeg = horaFormatoHMS.format(diaHoy);
    	DateFormat fechaHoy_diaMesAnio = new SimpleDateFormat("dd/MM/yyyy");
    	String diaMesAnio = fechaHoy_diaMesAnio.format(diaHoy);

    	// concatenar diaMesAnio + horaMinSeg para la salida del query
    	String fechaEgreso = diaMesAnio +" "+ horaMinSeg;

		InetAddress identifPC = InetAddress.getLocalHost();
		String pcUsuario = identifPC.toString();

    	if(tablaLectura.getRowCount() > 0) {

    		for(int i = 0; i < tablaLectura.getRowCount(); i++)
    		{
    			// System.out.println("i: "+i+" "+tablaLectura.getValueAt(0, i));

    			try {

    				cn = Conexion.Conectar();
    				stm = cn.createStatement();
    				String sql = "UPDATE "+tablaInput+" "
    						+ "set fechaEgreso = '"+fechaEgreso+"', usuarioEgreso = '"+pcUsuario+"', "
							+ "cajasValidadas ='"+tablaLectura.getValueAt(i, 4)+"', flagDisponible = 0 "
							+ "where numPallet = '"+tablaLectura.getValueAt(i, 0)+"' ";

    				int rowsAffected = stm.executeUpdate(sql);
    				System.out.println("Rows affected: " + rowsAffected);
    			} catch (SQLException e){
    				System.out.println(e);
    			}

    		}
    	}
    }


    private void guardarEnStock(ActionEvent evt) throws SQLException, UnknownHostException {

    	Conexion conexion = new Conexion();
    	Connection cn;
    	Statement stm;
    	ResultSet rsLeer;


    	InetAddress identifPC = InetAddress.getLocalHost();
    	String pcUsuario = identifPC.toString();

    	int stockAntes;
    	int stockAhora;
    	int stockFinal;


    	if(tablaLectura.getRowCount() > 0) {

    		for(int i = 0; i < tablaLectura.getRowCount(); i++) {

    			try {

    				cn = Conexion.Conectar();
    				stm = cn.createStatement();
    				rsLeer = stm.executeQuery("SELECT * FROM "+tablaProductos+" "
    						+ "where idProducto= '"+tablaLectura.getValueAt(i, 2)+"' ");

    				while(rsLeer.next()) {
    					stockAntes = rsLeer.getInt("stock");
    					stockAhora = Integer.parseInt((String) tablaLectura.getValueAt(i, 4));
    					stockFinal = stockAntes + stockAhora;

    					try {
    						cn = Conexion.Conectar();
    						stm = cn.createStatement();
    						String sql = "UPDATE "+tablaProductos+" "
    								+ "set stock = '"+stockFinal+"', movimUsuario = '"+pcUsuario+"', "
    								+ "movimTipo ='Ingreso', movimCantidad = '"+stockAhora+"' "
    								+ "where idProducto = '"+tablaLectura.getValueAt(i, 2)+"' ";

    						int rowsAffected = stm.executeUpdate(sql);
    						System.out.println("Rows affected: " + rowsAffected);

    					} catch (SQLException e){
    						System.out.println(e);
    					}

    				}

    			} catch (SQLException e){
    				System.out.println(e);
    			}
    		}

    		JOptionPane.showMessageDialog(null, "\nProceso realizado correctamente\n");

    	}
    	else
    	{
			JOptionPane.showMessageDialog(null, "No hay datos disponibles");
    	}

    }


	public  void Reporte() throws JRException {

		/* Convert List to JRBeanCollectionDataSource */
		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);

		/* Map to hold Jasper report Parameters */
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("CollectionBeanParam", itemsJRBean);


		//read jrxml file and creating jasperdesign object

		try {
//			File path = new File("\\MyReports\\env_informe.jrxml");

			File path = new File(new File("").getAbsolutePath() + "//env_informe.jrxml");

			InputStream input = new FileInputStream(path);
//			input = new FileInputStream(new File("Envasado_202205\\src\\reportes\\env_informe.jrxml"));
//			input = new FileInputStream(new File("C://MyReports//env_informe.jrxml"));

			JasperDesign jasperDesign = JRXmlLoader.load(input);

			/*compiling jrxml with help of JasperReport class*/
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

			/* Using jasperReport object to generate PDF */
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(listItems));


//			JRPdfExporter exp = new JRPdfExporter();
//			exp.setExporterInput(new SimpleExporterInput(jasperPrint));
//			exp.setExporterOutput(new SimpleOutputStreamExporterOutput("C://MyReports//Informes//informe.pdf"));
//			SimplePdfExporterConfiguration conf = new SimplePdfExporterConfiguration();
//			exp.setConfiguration(conf);
//			exp.exportReport();
//			System.out.println("creacion de pdf iterada");
//	    	// --------- cambio orden porque sino al cancelar impresion tampoco se genera pdf


			/*call jasper engine to display report in jasperviewer window*/
//			JasperViewer.viewReport(jasperPrint, false);
			JasperViewer jasperViewer = new JasperViewer(jasperPrint, false); // el ,false = hide_on_close
			jasperViewer.setVisible(true);
			jasperViewer.setTitle("Informe de Envasado");


		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

	}


	class Ventana extends WindowAdapter { //extends refiere que hereda los atributos de clase WindowAdapter

		@Override
		public void windowIconified(java.awt.event.WindowEvent e) {
			System.out.println("windowIconified: cambio a estado minimizado");
		}
		@Override
		public void windowClosing(java.awt.event.WindowEvent e) {
			System.out.println("dispose ejecutado");
			dispose();
		}
		@Override
		public void windowDeiconified(java.awt.event.WindowEvent e) {
			System.out.println("windowDeiconified: cambio a estado normal");
		}
}

}



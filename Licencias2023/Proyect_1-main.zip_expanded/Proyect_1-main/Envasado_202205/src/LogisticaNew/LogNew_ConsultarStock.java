package LogisticaNew;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import reportes.LeerStock;

@SuppressWarnings("serial")
public class LogNew_ConsultarStock extends javax.swing.JFrame {

	String tablaInput = "pruebas_2022.env_input_budines";
	String tablaProductos = "pruebas_2022.productos";

	public String incognita;

	private String pst = "SELECT idProducto, producto, stock, marca "
			+ "FROM "+tablaProductos+" where familiaProducto = ? ";

	public List<LeerStock> listItems;

	private Services servicio = new Services();

    // Variables declaration - do not modify
    private javax.swing.JButton JbtExcel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaLectura;
    // End of variables declaration


	public static void main(String args[]) {
		JFrame.setDefaultLookAndFeelDecorated(true);
    	JDialog.setDefaultLookAndFeelDecorated(true);

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
			public void run() {
                new LogNew_ConsultarStock().setVisible(true);
            }
        });
    }

    public LogNew_ConsultarStock() {
        initComponents();

        //setSize(600, 600);
        setBounds(450, 20, 800, 600);
        //setLocationRelativeTo(null);
        setResizable(false);
        setTitle("");
        setDefaultCloseOperation(HIDE_ON_CLOSE);

    }

    private void initComponents() {

    	addWindowListener(new Ventana()); //pongo la ventana en oyente
        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();


        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        JbtExcel = new javax.swing.JButton();



        tablaLectura = new javax.swing.JTable();
		tablaLectura = new JTable();
		tablaLectura.setRowHeight(24);
		tablaLectura.setFont(new Font("Arial", 1, 12));
		tablaLectura.setForeground(Color.BLUE);
		tablaLectura.setBackground(Color.lightGray);
		tablaLectura.setShowVerticalLines(false);
		tablaLectura.setSurrendersFocusOnKeystroke(true);
//        tablaLectura.setFont(new java.awt.Font("Tw Cen MT", 0, 12)); // NOI18N
        tablaLectura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaLectura);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Menu de consulta"));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 1, 16)); // NOI18N
        jLabel1.setText("Elige una opción para realizar consulta");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Tw Cen MT", 1, 16)); // NOI18N
        jRadioButton3.setText("Inventario Completo");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton6);
        jRadioButton6.setFont(new java.awt.Font("Tw Cen MT", 1, 16)); // NOI18N
        jRadioButton6.setText("Pudding");
        jRadioButton6.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
            	select03();
            }
        });

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Tw Cen MT", 1, 16)); // NOI18N
        jRadioButton4.setText("Pan Dulce");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
            	select01();
            }
        });


        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Tw Cen MT", 1, 16)); // NOI18N
        jRadioButton2.setText("Budines");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
            	select02();
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2Layout.setHorizontalGroup(
        	jPanel2Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel2Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.TRAILING, false)
        				.addComponent(jLabel1, Alignment.LEADING)
        				.addGroup(jPanel2Layout.createSequentialGroup()
        					.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jRadioButton3)
        						.addComponent(jRadioButton4))
        					.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
        					.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jRadioButton6)
        						.addComponent(jRadioButton2))))
        			.addContainerGap(210, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
        	jPanel2Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel2Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jLabel1)
        			.addGap(18)
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jRadioButton3)
        				.addComponent(jRadioButton2))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jRadioButton4)
        				.addComponent(jRadioButton6))
        			.addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2.setLayout(jPanel2Layout);

        JbtExcel.setText("Imprimir");
        JbtExcel.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					JbtExcelActionPerformed(evt);
//					Reporte();
				} catch (JRException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(JbtExcel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JbtExcel)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        // radiobutton "Producto Terminado"
    	mostrardatosPT();
    }


    private void JbtExcelActionPerformed(java.awt.event.ActionEvent evt) throws JRException {
    	//indico la ruta del objeto a cargar
    	JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile("C:\\MyReports\\stock.jasper");

    	//creo el mapeo para insertar el par�metro que sera enviado a Jasper Report
    	Map<String, Object> parametros = new HashMap<>();
    	parametros.put("titulo", "Version 05-2022");

    	//el fillmanager necesita argumento (objeto jasper, el parametro mapeado, conector tipo Connection)
    	JasperPrint jasperPrintWindow = JasperFillManager.fillReport(
				reporte, parametros,
				servicio.abrirConexion());
  /*
    	JasperPrint jasperPrintWindow = JasperFillManager.fillReport(
				"C:\\MyReports\\stock.jasper", null,
				Conexion.Conectar());
  */
		JasperViewer jasperViewer = new JasperViewer(jasperPrintWindow, false); // false = hide_on_close
		jasperViewer.setVisible(true);

		JasperPrintManager.printReport( jasperPrintWindow, true); // false = dispara impresion a impresora por default
																  // true = abre ventana dialogo

	}


    public void mostrardatosPT(){
    	Connection cn;
    	Statement stm = null;
    	ResultSet rs = null;
    	    DefaultTableModel modelo = new DefaultTableModel();
    	    modelo.addColumn("ID");
    	    modelo.addColumn("Descripción");
    	    modelo.addColumn("Cantidad");
    	    modelo.addColumn("Marca");
    	    tablaLectura.setModel(modelo);
    	    String [] datos  = new String [4];

    	    //Instanciamos el TableRowSorter y lo a�adimos al JTable
    	    TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
    	    tablaLectura.setRowSorter(elQueOrdena);

    	    //Si usted tiene una tabla de x, usted tiene que definir todas pues
    	    //JTable necesita tener los tama�os de todas para funcionar.

    	    //JTable.getColumnModel().getColumn(0).setPreferredWidth(100); TODAS

    	    //indico ancho de cada columna
    	    TableColumnModel columnModel = tablaLectura.getColumnModel();
    	    columnModel.getColumn(0).setPreferredWidth(100);
    	    columnModel.getColumn(1).setPreferredWidth(450);
    	    columnModel.getColumn(2).setPreferredWidth(100);
    	    columnModel.getColumn(3).setPreferredWidth(230);

    	    try {
    	        cn = servicio.abrirConexion();
    	        stm = cn.createStatement();
    	        rs = stm.executeQuery("SELECT idProducto, producto, stock, marca FROM "+tablaProductos);

    	        while(rs.next()) {
    	            datos[0] = rs.getString(1);
    	            datos[1] = rs.getString(2);
    	            datos[2] = rs.getString(3);
    	            datos[3] = rs.getString(4);
    	            modelo.addRow(datos);
    	        }
    	        tablaLectura.setModel(modelo);

    	    }catch (SQLException e){
    	    	e.printStackTrace();
    	    }
    	  }


    public void select01(){ // Pan Dulces
    	Connection cn;
    	Statement stm = null;
    	ResultSet rs = null;

    	incognita = "1";

	    DefaultTableModel modelo = new DefaultTableModel();
	    modelo.addColumn("ID");
	    modelo.addColumn("Descripción");
	    modelo.addColumn("Cantidad");
	    modelo.addColumn("Marca");
	    tablaLectura.setModel(modelo);
	    String [] datos  = new String [4];

	    //Instanciamos el TableRowSorter y lo a�adimos al JTable
	    TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
	    tablaLectura.setRowSorter(elQueOrdena);

	    //Si usted tiene una tabla de x, usted tiene que definir todas pues
	    //JTable necesita tener los tama�os de todas para funcionar.

	    //JTable.getColumnModel().getColumn(0).setPreferredWidth(100); TODAS

	    //indico ancho de cada columna
	    TableColumnModel columnModel = tablaLectura.getColumnModel();
	    columnModel.getColumn(0).setPreferredWidth(100);
	    columnModel.getColumn(1).setPreferredWidth(450);
	    columnModel.getColumn(2).setPreferredWidth(100);
	    columnModel.getColumn(3).setPreferredWidth(230);

	    try {
	        cn = servicio.abrirConexion();
	        stm = cn.createStatement();
			PreparedStatement pstFinal = cn.prepareStatement(pst); //pstConsulta por tipo y fecha.-
			pstFinal.setString(1, incognita);
			rs = pstFinal.executeQuery();

	        while(rs.next()) {
	            datos[0] = rs.getString(1);
	            datos[1] = rs.getString(2);
	            datos[2] = rs.getString(3);
	            datos[3] = rs.getString(4);
	            modelo.addRow(datos);
	        }
	        tablaLectura.setModel(modelo);

	    }catch (SQLException e){
	    	e.printStackTrace();
	    }
	  }


    public void select02(){ // Budines
    	Connection cn;
    	Statement stm = null;
    	ResultSet rs = null;

    	incognita = "2";

	    DefaultTableModel modelo = new DefaultTableModel();
	    modelo.addColumn("ID");
	    modelo.addColumn("Descripción");
	    modelo.addColumn("Cantidad");
	    modelo.addColumn("Marca");
	    tablaLectura.setModel(modelo);
	    String [] datos  = new String [4];

	    //Instanciamos el TableRowSorter y lo a�adimos al JTable
	    TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
	    tablaLectura.setRowSorter(elQueOrdena);


	    //indico ancho de cada columna
	    TableColumnModel columnModel = tablaLectura.getColumnModel();
	    columnModel.getColumn(0).setPreferredWidth(100);
	    columnModel.getColumn(1).setPreferredWidth(450);
	    columnModel.getColumn(2).setPreferredWidth(100);
	    columnModel.getColumn(3).setPreferredWidth(230);

	    try {
	        cn = servicio.abrirConexion();
	        stm = cn.createStatement();
			PreparedStatement pstFinal = cn.prepareStatement(pst); //pstConsulta por tipo y fecha.-
			pstFinal.setString(1, incognita);
			rs = pstFinal.executeQuery();

	        while(rs.next()) {
	            datos[0] = rs.getString(1);
	            datos[1] = rs.getString(2);
	            datos[2] = rs.getString(3);
	            datos[3] = rs.getString(4);
	            modelo.addRow(datos);
	        }
	        tablaLectura.setModel(modelo);

	    }catch (SQLException e){
	    	e.printStackTrace();
	    }
	  }


    public void select03(){ // Pudding
    	Connection cn;
    	Statement stm = null;
    	ResultSet rs = null;

    	incognita = "3";

	    DefaultTableModel modelo = new DefaultTableModel();
	    modelo.addColumn("ID");
	    modelo.addColumn("Descripción");
	    modelo.addColumn("Cantidad");
	    modelo.addColumn("Marca");
	    tablaLectura.setModel(modelo);
	    String [] datos  = new String [4];

	    //Instanciamos el TableRowSorter y lo añadimos al JTable
	    TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
	    tablaLectura.setRowSorter(elQueOrdena);


	    //indico ancho de cada columna
	    TableColumnModel columnModel = tablaLectura.getColumnModel();
	    columnModel.getColumn(0).setPreferredWidth(100);
	    columnModel.getColumn(1).setPreferredWidth(450);
	    columnModel.getColumn(2).setPreferredWidth(100);
	    columnModel.getColumn(3).setPreferredWidth(230);

	    try {
	        cn = servicio.abrirConexion();
	        stm = cn.createStatement();
			PreparedStatement pstFinal = cn.prepareStatement(pst); //pstConsulta por tipo y fecha.-
			pstFinal.setString(1, incognita);
			rs = pstFinal.executeQuery();

	        while(rs.next()) {
	            datos[0] = rs.getString(1);
	            datos[1] = rs.getString(2);
	            datos[2] = rs.getString(3);
	            datos[3] = rs.getString(4);
	            modelo.addRow(datos);
	        }
	        tablaLectura.setModel(modelo);

			if(datos [0] == null) {
				JOptionPane.showMessageDialog(null, "No hay datos disponibles");
			}


			listItems= new ArrayList<>();

			for (int i = 0; i<tablaLectura.getRowCount(); i++) {
				LeerStock listado = new LeerStock(
						tablaLectura.getValueAt(i, 0).toString(),
						tablaLectura.getValueAt(i, 1).toString(),
						tablaLectura.getValueAt(i, 2).toString(),
						tablaLectura.getValueAt(i, 3).toString());
				listItems.add(listado);
			}



	    }catch (SQLException e){
	    	e.printStackTrace();
	    }
	  }


	public  void Reporte() throws JRException {

    	if(tablaLectura.getRowCount() > 0) {

			/* Convert List to JRBeanCollectionDataSource */
			JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);

			/* Map to hold Jasper report Parameters */
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("CollectionBeanParam", itemsJRBean);
//			parameters.put("P_DesdeCuando", "Per�odo analizado Desde: "+incognita1+" || Hasta: "+incognita2);

			//read jrxml file and creating jasperdesign object
			InputStream input;
			try {
				input = new FileInputStream(new File("C:\\MyReports\\stock_informe.jrxml"));
//				input = new FileInputStream(new File("src\\oficina\\envasado_202205\\env_informe.jrxml"));
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
				e.printStackTrace();
			}

		}
    	else
    	{
    		JOptionPane.showMessageDialog(null, "No hay datos disponibles");
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

package logistica;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import conector.Conexion;


public class Log_Frame1 extends javax.swing.JFrame {


    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
	public final Connection cn; //hago que el object cn sea accesible desde toda la clase.

    // End of variables declaration

    public Log_Frame1() {
        initComponents();
        setBounds(10, 20, 415, 200);
        //setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Administraci�n de Inventario: ");

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

    }

    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("CONSULTAR STOCK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("INGRESAR budines");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					jButton2ActionPerformed(evt);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
            }
        });

        jButton3.setText("CONSULTA DE MOVIMIENTOS");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("INGRESAR (manual)");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("INGRESAR P Dulces");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("SALIR");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("EGRESO MERCADERIA");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
//            	JOptionPane.showMessageDialog(null, "Opci�n restringida, consultar a logistica");
            }
        });

        jLabel1.setText("Bienvenido al Menu Principal:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addContainerGap()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        								.addComponent(jButton4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addComponent(jButton2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addComponent(jButton1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE))
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(jButton7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addComponent(jButton5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addComponent(jButton3)))
        						.addComponent(jLabel1)))
        				.addComponent(jButton6, Alignment.TRAILING))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jLabel1)
        			.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jButton1)
        				.addComponent(jButton3))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jButton2)
        				.addComponent(jButton5))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jButton4)
        				.addComponent(jButton7))
        			.addGap(18)
        			.addComponent(jButton6))
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) throws UnknownHostException {
//    	Ventana03IngresoOK frame03 = new Ventana03IngresoOK();
    	Log_Frame3InputStock frame03 = new Log_Frame3InputStock();
        frame03.setVisible(true);
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
        //boton "salir"
        System.exit(0);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // boton "Consulta Stock"
//    	Ventana02ConsultaOK frame02 = new Ventana02ConsultaOK();
    	Log_Frame2ConsultarStock frame02 = new Log_Frame2ConsultarStock();
        frame02.setVisible(true);
        //dispose();
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        // boton "Consulta movimientos"
		try {
			Log_Frame5Movimientos window = new Log_Frame5Movimientos();
			window.Ventana05.setVisible(true);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
    }

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
//    	Ventana04EgresoOK frame04 = new Ventana04EgresoOK();
    	Log_Frame7ReadFile frame04 = new Log_Frame7ReadFile();
        frame04.setVisible(true);

    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
    	Log_Frame6OutputManual frame06= new Log_Frame6OutputManual();
    	frame06.setVisible(true);
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			Log_Frame3InputStockPD frame07 = new Log_Frame3InputStockPD();
			frame07.setVisible(true);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
    }

    public static void main(String args[]) {

    	JFrame.setDefaultLookAndFeelDecorated(true);
    	JDialog.setDefaultLookAndFeelDecorated(true);

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
			public void run() {
                new Log_Frame1().setVisible(true);
            }
        });
    }

}


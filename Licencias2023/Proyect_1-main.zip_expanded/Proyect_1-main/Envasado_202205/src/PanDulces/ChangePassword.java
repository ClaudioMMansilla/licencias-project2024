package PanDulces;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import conector.Conexion;

public class ChangePassword extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JLabel lblEnterNewPassword;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
			public void run() {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ChangePassword(String name) {
        setBounds(450, 360, 500, 234);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 34));
        textField.setBounds(205, 29, 250, 50);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton btnSearch = new JButton("Guardar");
        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 29));
        btnSearch.setBackground(new Color(240, 240, 240));
        btnSearch.setBounds(285, 108, 170, 59);
        contentPane.add(btnSearch);
        btnSearch.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {

                String pstr = textField.getText();
                try {
                    System.out.println("update password name " + name);
                    System.out.println("update password");

            		Conexion conexion = new Conexion();
            		Connection cn = conexion.Conectar();
//                    Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/swing_demo",
//                        "root", "root");

                    PreparedStatement st = cn
                        .prepareStatement("Update pruebas_2022.usuarios set password=? where user=?");

                    st.setString(1, pstr);
                    st.setString(2, name);
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(btnSearch, "Contase�a actualizada correctamente");
                    dispose();
//                    UserHome ah = new UserHome(name);
//                    ah.setTitle("Bienvenido");
//                    ah.setVisible(true);

                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }

            }
        });


        lblEnterNewPassword = new JLabel("Nueva contrase�a :");
        lblEnterNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblEnterNewPassword.setBounds(23, 29, 192, 50);
        contentPane.add(lblEnterNewPassword);
    }
}
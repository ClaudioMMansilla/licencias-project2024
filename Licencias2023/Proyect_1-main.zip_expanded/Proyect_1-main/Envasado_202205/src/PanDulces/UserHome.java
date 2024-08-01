package PanDulces;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class UserHome extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private static String userSes;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
			public void run() {
                try {
                    UserHome frame = new UserHome(userSes);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
//
//    public UserHome() {
//
//    }

    /**
     * Create the frame.
     */
    public UserHome(String userSes) {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(450, 190, 649, 400);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnNewButton = new JButton("Vto 6 meses");
        btnNewButton.setForeground(new Color(0, 0, 0));
        btnNewButton.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton.setBounds(26, 33, 290, 50);
        contentPane.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				EnvasadoInputPD6meses frame6meses = new EnvasadoInputPD6meses(userSes);
				frame6meses.setTitle("Vencimiento: 6 meses -- Operador: "+userSes);
				frame6meses.setVisible(true);
            }
        });


        JButton btnNewButton2 = new JButton("Vto 7 meses");
        btnNewButton2.setForeground(new Color(0, 0, 0));
        btnNewButton2.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnNewButton2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton2.setBounds(328, 33, 290, 50);
        contentPane.add(btnNewButton2);
        btnNewButton2.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				EnvasadoInputPD7meses frame7meses = new EnvasadoInputPD7meses(userSes);
				frame7meses.setTitle("Vencimiento: 7 meses -- Operador: "+userSes);
				frame7meses.setVisible(true);
            }
        });

        JButton btnNewButton3 = new JButton("Vto 8 meses");
        btnNewButton3.setForeground(new Color(0, 0, 0));
        btnNewButton3.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnNewButton3.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton3.setBounds(26, 94, 290, 50);
        contentPane.add(btnNewButton3);
        btnNewButton3.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				EnvasadoInputPD8meses frame8meses = new EnvasadoInputPD8meses(userSes);
				frame8meses.setTitle("Vencimiento: 8 meses -- Operador: "+userSes);
				frame8meses.setVisible(true);
            }
        });

        JButton btnNewButton4 = new JButton("Vto 12 meses");
        btnNewButton4.setForeground(new Color(0, 0, 0));
        btnNewButton4.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnNewButton4.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton4.setBounds(328, 94, 290, 50);
        contentPane.add(btnNewButton4);
        btnNewButton4.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				EnvasadoInputPD12meses frame12meses = new EnvasadoInputPD12meses(userSes);
				frame12meses.setTitle("Vencimiento: 12 meses -- Operador: "+userSes);
				frame12meses.setVisible(true);
            }
        });


        JButton btnNewButton5 = new JButton("Modificar pallet");
        btnNewButton5.setForeground(new Color(0, 0, 0));
        btnNewButton5.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnNewButton5.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton5.setBounds(328, 155, 290, 50);
        contentPane.add(btnNewButton5);
        btnNewButton5.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	Env_UpdateInputPD frameUpdate = new Env_UpdateInputPD(userSes);
            	frameUpdate.setTitle("Modificacion de pallet ingresados");
            	frameUpdate.setVisible(true);
            }
        });


        JButton btnNewButton6 = new JButton("Vto / Lote Manual");
        btnNewButton6.setForeground(new Color(0, 0, 0));
        btnNewButton6.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnNewButton6.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton6.setBounds(26, 155, 290, 50);
        contentPane.add(btnNewButton6);
        btnNewButton6.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        });


        JButton button = new JButton("Cambiar Contrase�a\r\n");
        button.setBackground(UIManager.getColor("Button.disabledForeground"));
        button.setFont(new Font("Tahoma", Font.PLAIN, 20));
        button.setBounds(26, 288, 290, 50);
        contentPane.add(button);
        button.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {

                ChangePassword bo = new ChangePassword(userSes);
                bo.setTitle("Change Password");
                bo.setVisible(true);

            }
        });

        JButton button_1 = new JButton("Cerrar Sesi�n");
        button_1.setForeground(Color.BLACK);
        button_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        button_1.setBackground(SystemColor.textInactiveText);
        button_1.setBounds(328, 288, 290, 50);
        contentPane.add(button_1);
        button_1.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(btnNewButton, "Desea continuar?");
                // JOptionPane.setRootFrame(null);
                if (a == JOptionPane.YES_OPTION) {
                    dispose();
//                    UserLogin obj = new UserLogin();
//                    obj.setTitle("Student-Login");
//                    obj.setVisible(true);
                }
                dispose();
                UserLogin_PanDulce obj = new UserLogin_PanDulce();

                obj.setTitle("Student-Login");
                obj.setVisible(true);
        	}
        });



    }
}
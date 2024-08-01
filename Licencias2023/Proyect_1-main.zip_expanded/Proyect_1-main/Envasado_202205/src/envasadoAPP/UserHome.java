package envasadoAPP;

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
        setBounds(450, 190, 350, 300);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnNewButton = new JButton("Estaci�n de trabajo");
        btnNewButton.setForeground(new Color(0, 0, 0));
        btnNewButton.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnNewButton.setBounds(26, 36, 290, 50);
        contentPane.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				EnvasadoInputBudines frame = new EnvasadoInputBudines(userSes);
				frame.setVisible(true);
            }
        });


        JButton button = new JButton("Cambiar Contrase�a\r\n");
        button.setBackground(UIManager.getColor("Button.disabledForeground"));
        button.setFont(new Font("Tahoma", Font.PLAIN, 26));
        button.setBounds(26, 198, 290, 50);
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
        button_1.setFont(new Font("Tahoma", Font.PLAIN, 26));
        button_1.setBackground(SystemColor.textInactiveText);
        button_1.setBounds(26, 117, 290, 50);
        contentPane.add(button_1);
        button_1.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(btnNewButton, "Desea continuar?");
                // JOptionPane.setRootFrame(null);
                if (a == JOptionPane.YES_OPTION) {
                    dispose();
                    UserLogin obj = new UserLogin();
                    obj.setTitle("Student-Login");
                    obj.setVisible(true);
                }
                dispose();
                UserLogin obj = new UserLogin();

                obj.setTitle("Student-Login");
                obj.setVisible(true);
        	}
        });



    }
}
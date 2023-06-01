import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Cliente {

	public static void main(String[] args) {

		Marco marco = new Marco();

	}

}

class Marco extends JFrame {

	public Marco() {

		setTitle("Cliente");
		setBounds(30, 30, 300, 300);
		setDefaultCloseOperation(3);

		LaminaCliente laminaCliente = new LaminaCliente();
		add(laminaCliente);
		setVisible(true);
	}

}

class LaminaCliente extends JPanel {

	private JTextField campo1;
	private JButton boton;

	public LaminaCliente() {

		JLabel texto = new JLabel("Cliente");
		add(texto);

		campo1 = new JTextField(20);
		add(campo1);
		boton = new JButton("Enviar");
		add(boton);

		boton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.out.println(campo1.getText());
				try {
					Socket socket = new Socket("192.168.81.1", 9999);

					DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
					salida.writeUTF(campo1.getText());
					salida.close();

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println(e1.getMessage());

				}
			}
		});

	}

}
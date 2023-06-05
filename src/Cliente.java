import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Cliente {

	public static void main(String[] args) {

		Marco marco = new Marco();

	}

}

class Marco extends JFrame {

	public Marco() {

		setTitle("Cliente");
		setBounds(30, 30, 300, 350);
		setDefaultCloseOperation(3);
		LaminaCliente laminaCliente = new LaminaCliente();
		add(laminaCliente);
		setVisible(true);
	}

}

class LaminaCliente extends JPanel implements Runnable {

	private JTextField campo1;
	private JButton boton;
	private JTextArea areaTexto;
	private JTextField nick, IP;

	public LaminaCliente() {
		nick = new JTextField(5);
		add(nick);
		JLabel texto = new JLabel("-Chat-");
		texto.setFont(new Font("Arial", 1, 14));
		add(texto);
		IP = new JTextField(8);
		add(IP);

		areaTexto = new JTextArea(12, 20);
		add(areaTexto);
		campo1 = new JTextField(20);
		add(campo1);
		boton = new JButton("Enviar");
		add(boton);

		boton.addActionListener(new ActionListener() {
			String ip;

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ip = InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException r) {
					// TODO Auto-generated catch block
					r.printStackTrace();
				}
				try {

					Empaquetado paqueteDatos = new Empaquetado();
					paqueteDatos.setNickName(nick.getText());
					paqueteDatos.setIP(IP.getText());
					paqueteDatos.setMensaje(campo1.getText());

					Socket socket = new Socket(ip, 9999);

					ObjectOutputStream paqueteParaEnviar = new ObjectOutputStream(socket.getOutputStream());
					paqueteParaEnviar.writeObject(paqueteDatos);
					socket.close();

//					DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
//					salida.writeUTF(campo1.getText());
//					salida.close();

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println(e1.getMessage());

				}
			}
		});
		
		Thread miHilo = new Thread(this);
		miHilo.start();

	}

	@Override
	public void run() {

		try {
			ServerSocket socketRegreso = new ServerSocket(9090);
			Socket devuelta;
			Empaquetado regreso;

			while (true) {
				devuelta = socketRegreso.accept();
				ObjectInputStream recibido = new ObjectInputStream(devuelta.getInputStream());

				
				regreso = (Empaquetado)recibido.readObject();
				areaTexto.append(regreso.getNickName()+": " + regreso.getMensaje()+"\n");
				
				
				
				
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}

class Empaquetado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 12345;
	private String nickName;
	private String IP;
	private String mensaje;

	public Empaquetado() {

	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
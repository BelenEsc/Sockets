import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Cliente {

	public static void main(String[] args) {

		Marco marco = new Marco();
		try {
			System.out.println(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		addWindowListener(new EnvioOnline());
	}

}


class EnvioOnline extends WindowAdapter{
	public void windowOpened(WindowEvent e) {
		try {
			Socket miSocket =new Socket("192.168.44.128",9999);
			Empaquetado datos= new Empaquetado();
			datos.setMensaje("online");
			ObjectOutputStream paqueteDatos = new ObjectOutputStream(miSocket.getOutputStream());
			paqueteDatos.writeObject(datos);
			miSocket.close();
			
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}


class LaminaCliente extends JPanel implements Runnable {

	private JTextField campo1;
	private JButton boton;
	private JTextArea areaTexto;
	private JLabel nick, nickNombre;
	private JComboBox IP;

	public LaminaCliente() {
		nickNombre = new JLabel("Nick: ");
		add(nickNombre);
		nick = new JLabel();
		String ponNick=JOptionPane.showInputDialog("pon tu nick");
		nick.setText(ponNick);
		add(nick);
		JLabel texto = new JLabel("Online: ");
		texto.setFont(new Font("Arial", 1, 14));
		add(texto);
		IP = new JComboBox();
		IP.addItem("Usuario1");
		IP.addItem("Usuario2");
		IP.addItem("Usuario3");
		add(IP);

		areaTexto = new JTextArea(12, 20);
		add(areaTexto);
		campo1 = new JTextField(20);
		add(campo1);
		boton = new JButton("Enviar");
		add(boton);

		boton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			
				try {

					Empaquetado paqueteDatos = new Empaquetado();
					paqueteDatos.setNickName(nick.getText());
					paqueteDatos.setIP(IP.getSelectedItem().toString());
					paqueteDatos.setMensaje(campo1.getText());

					Socket socket = new Socket("192.168.44.128", 9999);

					ObjectOutputStream paqueteParaEnviar = new ObjectOutputStream(socket.getOutputStream());
					paqueteParaEnviar.writeObject(paqueteDatos);
					socket.close();
					
					System.out.println(InetAddress.getLocalHost().getHostAddress());
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
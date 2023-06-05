import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Servidor {

	public static void main(String[] args) {

		Marco2 marcoServidor = new Marco2();

	}

}

class Marco2 extends JFrame {

	public Marco2() {

		setTitle("marco servidor");
		setBounds(30, 50, 300, 300);
		setDefaultCloseOperation(3);
		Lamina2 laminaServidor = new Lamina2();
		add(laminaServidor);
		setVisible(true);

	}

}

class Lamina2 extends JPanel implements Runnable {
	private JTextArea textoServidor;

	public Lamina2() {
		setLayout(new BorderLayout());
		textoServidor = new JTextArea();
		add(textoServidor, BorderLayout.CENTER);
		Thread hilo = new Thread(this);
		hilo.start();

	}

	Socket puerta = new Socket();

	@Override
	public void run() {

		try {
			ServerSocket serverSocket = new ServerSocket(9999);
			String nickInput;
			String IPInput;
			String mensajeInput;

			while (true) {
				Socket miSocket = serverSocket.accept();

				ObjectInputStream entrada = new ObjectInputStream(miSocket.getInputStream());
				Empaquetado entradaPaquete = (Empaquetado)entrada.readObject();
				nickInput = entradaPaquete.getNickName();
				IPInput = entradaPaquete.getIP();
				mensajeInput = entradaPaquete.getMensaje();
				textoServidor.append(nickInput + ": " + mensajeInput + "\n");
				
				Socket miSocketSalida = new Socket(IPInput,9090);
				ObjectOutputStream salidaPaquete = new ObjectOutputStream(miSocketSalida.getOutputStream());
				salidaPaquete.writeObject(entradaPaquete);
				miSocketSalida.close();
				miSocket.close();
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		System.out.println("hola" + hashCode());

	}
}

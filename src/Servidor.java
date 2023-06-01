import java.awt.BorderLayout;
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

class Lamina2 extends JPanel{
	private JTextArea textoServidor;
	public Lamina2() {
		setLayout(new BorderLayout());
		textoServidor= new JTextArea();
		add(textoServidor,BorderLayout.CENTER);
	}
	
	Socket puerta = new Socket();
	
	
}
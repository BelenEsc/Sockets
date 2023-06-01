import java.net.InetAddress;
import java.net.UnknownHostException;

public class test {

	public static void main(String[] args) {

		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}

package password;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.security.SecureRandom;

public class passGen {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(createPassword(10));
	}
	
	public static String createPassword(int length){
	    final String allowedCharsZahl = "0123456789";
	    final String allowedCharsGross = "ABCDEFGHJKLMNPQRSTUVWXYZ";
	    final String allowedCharsKlein = "abcdefghjklmnpqrstuvwxyz";
	    SecureRandom random = new SecureRandom();
	    StringBuilder pass = new StringBuilder(length);
	    for (int i = 0; i < length/4; i++) {
	        pass.append(allowedCharsZahl.charAt(random.nextInt(allowedCharsZahl.length())));
	    }
	    for (int i = 0; i < length/2; i++) {
	        pass.append(allowedCharsGross.charAt(random.nextInt(allowedCharsGross.length())));
	    }
	    for (int i = 0; i < length/3; i++) {
	        pass.append(allowedCharsKlein.charAt(random.nextInt(allowedCharsKlein.length())));
	    }
	    
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
	               new StringSelection(pass.toString()), null
	          );
	    
	    
	    
	    
	    return pass.toString();
	    
	    
	    
	    
	    
	}

}

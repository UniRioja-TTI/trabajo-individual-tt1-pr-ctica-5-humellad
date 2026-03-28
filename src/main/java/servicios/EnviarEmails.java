package servicios;



import com.tt1.trabajo.utilidades.ApiClient;
import com.tt1.trabajo.utilidades.ApiException;
import com.tt1.trabajo.utilidades.api.EmailApi;
import com.tt1.trabajo.utilidades.model.EmailResponse;

import modelo.Destinatario;

public class EnviarEmails implements interfaces.InterfazEnviarEmails{

	private EmailApi emailApi;
	
	public EnviarEmails() {
		ApiClient cliente = new ApiClient();
		cliente.setBasePath("http://localhost:8080");
		this.emailApi = new EmailApi(cliente);
	}
	@Override
	public boolean enviarEmail(Destinatario dest, String email) {
		EmailResponse respuesta;
		try {
			respuesta = emailApi.emailPost(dest.getDireccion(), email);
			if (respuesta == null) {
	        	return false;
	        }
	        else if(respuesta.getDone()) {
	        	return true;
	        }
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return false;
	}
}

package servicios;


import org.openapitools.client.*;
import org.openapitools.client.api.*;
import org.openapitools.client.model.*;

import modelo.Destinatario;

public class EnviarEmails implements interfaces.InterfazEnviarEmails{

	private EmailApi emailApi;
	
	public EnviarEmails() {
		ApiClient cliente = new ApiClient();
		cliente.setBasePath("http://servicio-consumible:8080");
		this.emailApi = new EmailApi(cliente);
	}
	@Override
	public boolean enviarEmail(Destinatario dest, String email) {
		EmailResponse respuesta;
		respuesta = emailApi.emailPost(dest.getDireccion(), email).block();
		if (respuesta == null) {
	        return false;
	    }
	    else if(respuesta.getDone()) {
	        return true;
	    }
        return false;
	}
}

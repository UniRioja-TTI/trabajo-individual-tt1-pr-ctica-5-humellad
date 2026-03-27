package servicios;

import org.openapitools.client.ApiClient;
import org.openapitools.client.api.EmailApi;
import org.openapitools.client.model.EmailResponse;

import modelo.Destinatario;

public class EnviarEmails implements interfaces.InterfazEnviarEmails{

	@Override
	public boolean enviarEmail(Destinatario dest, String email) {
        ApiClient apiClient = new ApiClient();

        EmailApi emailApi = new EmailApi(apiClient);

        try {
            EmailResponse respuesta = emailApi.emailPost(dest.toString(), email);
            
            if (respuesta.getDone()) {
                System.out.println("Correo enviado con éxito");
            }
        } catch (Exception e) {
            System.err.println("Error al enviar email: " + e.getMessage());
            return false;
        }
        return true;
	}
}

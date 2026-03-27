package servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openapitools.client.ApiClient;
import org.openapitools.client.api.SolicitudApi;

import modelo.DatosSimulation;
import modelo.DatosSolicitud;
import modelo.Entidad;

public class ContactoSim implements interfaces.InterfazContactoSim{
	private ApiClient cliente = new ApiClient();
    
	@Override
	public int solicitarSimulation(DatosSolicitud sol) {
		SolicitudApi solicitud = new SolicitudApi(cliente);
		solicitud.
	}

	@Override
	public DatosSimulation descargarDatos(int ticket) {
		return null;
	}

	@Override
	public List<Entidad> getEntities() {
		return this.entidades;
	}

	@Override
	public boolean isValidEntityId(int id) {
		// TODO Auto-generated method stub
		return false;
	}
}

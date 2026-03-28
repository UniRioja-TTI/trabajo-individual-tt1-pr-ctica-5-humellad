package servicios;

import java.util.*;

import org.springframework.stereotype.Service;

import com.tt1.trabajo.utilidades.*;
import com.tt1.trabajo.utilidades.api.*;
import com.tt1.trabajo.utilidades.model.ResultsResponse;
import com.tt1.trabajo.utilidades.model.Solicitud;
import com.tt1.trabajo.utilidades.model.SolicitudResponse;

import modelo.*;

@Service
public class ContactoSim implements interfaces.InterfazContactoSim{
	private final String usuario = "USUARIO";
	private Map<Integer, Entidad> entidades = new HashMap<Integer, Entidad>();
	private ApiClient cliente;
	private SolicitudApi solicitudApi;
	private ResultadosApi resultados;
	
	public ContactoSim() {
		cliente = new ApiClient();
        cliente.setBasePath("http://host.docker.internal:8080");
        solicitudApi = new SolicitudApi(cliente);
        resultados = new ResultadosApi(cliente);
        
		Entidad a = new Entidad(1, " a", "Ej1");
    	Entidad b = new Entidad(2, " b", "Ej2");
    	Entidad c = new Entidad(3, " c", "Ej3");
    	Entidad d = new Entidad(4, " d", "Ej4");
    	
    	entidades.put(a.getId(), a);
    	entidades.put(b.getId(), b);
    	entidades.put(c.getId(), c);
    	entidades.put(d.getId(), d);
	}
    
	@Override
	public int solicitarSimulation(DatosSolicitud sol) {
		if(sol==null){
            return -1;
        }
		List<Integer> cant = new ArrayList<Integer>();
		List<String> nom = new ArrayList<String>();
		Map<Integer, Integer> datos = sol.getNums();
		
		for(Map.Entry<Integer, Integer> dato : datos.entrySet()) {
			if(!this.entidades.containsKey(dato.getKey())) {
				return -1;
			}
			nom.add(this.entidades.get(dato.getKey()).getName());
			cant.add(dato.getValue());
		}
		
		Solicitud solicitud = new Solicitud();
		solicitud.setCantidadesIniciales(cant);
		solicitud.setNombreEntidades(nom);
		
		try {
			SolicitudResponse respuesta = solicitudApi.solicitudSolicitarPost(this.usuario, solicitud);
			if(respuesta==null) {
				return -2;
			}
			else if(respuesta.getTokenSolicitud() == null) {
				return -3;
			}
			return respuesta.getTokenSolicitud();
		} catch (ApiException e) {
			e.printStackTrace();
			return -4;
		}
	}

	@Override
	public DatosSimulation descargarDatos(int ticket) {
		try {
			ResultsResponse resultado = resultados.resultadosPost(this.usuario, ticket);
			if(resultado == null) {
				return null;
			}
			if(resultado.getDone()) {
				return dataEnFormato(resultado.getData());
			}
			else {
				return null;
			}
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Entidad> getEntities() {
		return List.copyOf(this.entidades.values());
	}

	@Override
	public boolean isValidEntityId(int id) {
		return this.entidades.containsKey(id);
	}
	
	private DatosSimulation dataEnFormato(String data) {
		DatosSimulation datos = new DatosSimulation();
		Integer tiempo = 0;
		
		String[] lineas = data.split("\n");
		int ancho = Integer.parseInt(lineas[0].trim());
		datos.setAnchoTablero(ancho);
		
		Map<Integer, List<Punto>> puntos = new HashMap<Integer, List<Punto>>();
		for (int i = 1; i < lineas.length; i++) {
		    String[] partes = lineas[i].split(",");
		    
		    int t = Integer.parseInt(partes[0].trim());
		    int y = Integer.parseInt(partes[1].trim());
		    int x = Integer.parseInt(partes[2].trim());
		    String color = partes[3].trim();
		    
		    
		    
		    Punto p = new Punto(x, y, color);
		    if(!puntos.containsKey(t)) {
		    	puntos.put(t, new ArrayList<Punto>());
		    }
		    puntos.get(t).add(p);
		    
		    if(tiempo<t) {
		    	tiempo=t;
		    }
		}
		
		datos.setPuntos(puntos);
		datos.setMaxSegundos(tiempo);
		return datos;
	}
}

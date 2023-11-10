package sugeridor;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import modelo.Core;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SuppressWarnings("deprecation")
public class Sugeridor extends Observable implements Observer{
	
		private final OkHttpClient client;
	    private final ObjectMapper mapper;
	    
	    public Sugeridor() {
	    	this.client = new OkHttpClient();
	    	this.mapper = new ObjectMapper();
	    }
	    
	    public List<String> buscarSugerencias(List<String> productos){
	        
	    	validarProductos(productos);
	    	
	    	String url = "https://recomendador-de-productos-production.up.railway.app/api/v1/recomendador/productosSugeridos";
	        String json;
			try {
				json = mapper.writeValueAsString(productos);
			} catch (JsonProcessingException e1) {
				return null;
			}
	        RequestBody body = RequestBody.create(
	                MediaType.parse("application/json; charset=utf-8"), json);
	        Request request = new Request.Builder()
	                .url(url)
	                .post(body)
	                .build();
	        try (Response response = client.newCall(request).execute()) {
	            return Arrays.asList(mapper.readValue(response.body().string(), String[].class));
	        }
	        catch(Exception e) {
	        	return null;
	        }
	    
	    }

		@SuppressWarnings("unchecked")
		@Override
		public void update(Observable o, Object arg) {
			if(o instanceof Core){
				List<String> sugerencias = buscarSugerencias((List<String>) arg);
				String resultado = sugerencias.stream().map(String::toUpperCase).collect(Collectors.joining(" - "));
				setChanged();
		        notifyObservers(resultado);
		    }
		}
		
		private void validarProductos(List<String> productos) {
			if(productos == null || productos.isEmpty())
	    		throw new IllegalArgumentException();
			productos = productos.stream().map(String::toLowerCase).collect(Collectors.toList());
		}

}

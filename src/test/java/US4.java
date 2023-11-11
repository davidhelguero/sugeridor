import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.Core;
import modelo.CoreInit;
import sugeridor.Sugeridor;
import static org.mockito.Mockito.*;

class US4 {
	
	private Sugeridor sugeridor;
	private List<String> productos = Arrays.asList("banana");
	private List<String> productosSinSugerencias = Arrays.asList("rulemanes");

	private CoreInit coreInit;
	private Core coreObservado;
	private String rutaJarCriterio = "src/test/resources/Criterios.jar";
	private Sugeridor sugeridorMock;
	
	private List<String> sugerenciasObtenidas;
	private List<String> sugerenciasEsperadas = Arrays.asList("manzana", "zanahoria");
	
	@BeforeEach 
	public void setup() {
		sugeridor = new Sugeridor();
		sugeridorMock = mock(Sugeridor.class);
		CoreInit.RUTA_JAR_CRITERIO = rutaJarCriterio;
	    coreInit = new CoreInit();
	    coreObservado = coreInit.inicializar();
	    coreObservado.setCriterio("Distancia");
	}
	
	@Test
	public void CA1_ProductosDelUsuarioNulos() {
		assertThrows(IllegalArgumentException.class, () -> {sugeridor.buscarSugerencias(null);});
	}
	
	@Test
	public void CA2_SinSugerenciasParaLosProductosDelUsuario() {
		assertTrue(sugeridor.buscarSugerencias(productosSinSugerencias).isEmpty());
	}
	
	@Test
	public void CA3_SugerenciasEncontradasALosProductosDelUsuario() {
		
		sugerenciasObtenidas = sugeridor.buscarSugerencias(productos);
		Collections.sort(sugerenciasEsperadas);
		Collections.sort(sugerenciasObtenidas);
		assertEquals(sugerenciasEsperadas, sugerenciasObtenidas);
	}
	
	@Test
	public void CA4_SugerenciasDespuesDeUnaRecomendacion() {
		coreObservado.addObserver(sugeridorMock);
		coreObservado.recomendar(productos);
		verify(sugeridorMock).update(eq(coreObservado), eq(productos));
	}
}

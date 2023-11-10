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
	private List<String> productosNulos = null;
	private List<String> productosSinSugerencias = Arrays.asList("rulemanes");
	
	private Sugeridor sugeridorMock;
	private CoreInit coreInit = new CoreInit();
	private Core coreObservado = coreInit.inicializar();
	private List<String> sugerencias;
	private List<String> sugerenciasEsperadas = Arrays.asList("manzana", "zanahoria");
	
	@BeforeEach 
	public void setup() {
		sugeridor = new Sugeridor();
		sugeridorMock = mock(Sugeridor.class);
	}
	
	@Test
	public void CA1_ProductosDelUsuarioNulos() {
		assertThrows(IllegalArgumentException.class, () -> {sugeridor.buscarSugerencias(productosNulos);});
	}
	
	@Test
	public void CA2_SinSugerenciasParaLosProductosDelUsuario() {
		assertTrue(sugeridor.buscarSugerencias(productosSinSugerencias).isEmpty());
	}
	
	@Test
	public void CA3_SugerenciasEncontradasALosProductosDelUsuario() {
		
		sugerencias = sugeridor.buscarSugerencias(productos);
		Collections.sort(sugerenciasEsperadas);
		Collections.sort(sugerencias);
		assertEquals(sugerenciasEsperadas, sugerencias);
	}
	
	@Test
	public void CA4_SugerenciasDespuesDeUnaRecomendacion() {
		coreObservado.addObserver(sugeridorMock);
		coreObservado.recomendar(productos);
		verify(sugeridorMock).update(eq(coreObservado), eq(productos));
	}
}

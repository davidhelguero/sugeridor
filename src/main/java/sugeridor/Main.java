package sugeridor;

import java.util.List;

import modelo.Core;
import modelo.CoreInit;

public class Main {

	public static void main(String[] args) {
				
		CoreInit coreInit = new CoreInit();
		Core core = coreInit.inicializar();
		System.out.println(core.obtenerNombresCriterios());
		
		Sugeridor sugeridor = new Sugeridor();
		core.addObserver(sugeridor);
		String recomendacion = core.recomendar(List.of("P2"));
		
	}

}

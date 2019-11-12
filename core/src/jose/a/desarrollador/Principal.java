package jose.a.desarrollador;

import com.badlogic.gdx.Game;
import jose.a.desarrollador.Pantallas.PantallaInicio;


public class Principal extends Game {	
	
	@Override
	public void create () {
            System.out.println(System.currentTimeMillis());
            setScreen(new PantallaInicio(this));
           // setScreen(new PantallaApuntarseCompeticion(this,"Eva"));
            //setScreen(new BoxeadoresHUD());
           // setScreen(new PantallaLoguin(this));
            //setScreen(new PantallaMostrarEstadisticasBoxeador(this,""));
            //setScreen(new PantallaModificarDatosBoxeador(this,""));
		//setScreen(new PantallaCrearCompeticiones(this,"m"));
              //  setScreen(new PantallaAccionesBoxeador(this,"m"));
              //  setScreen(new PantallaEmparejamiento(this,"m"));
	}

	
}

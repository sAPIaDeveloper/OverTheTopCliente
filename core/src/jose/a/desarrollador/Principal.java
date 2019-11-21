package jose.a.desarrollador;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import jose.a.desarrollador.Pantallas.PantallaInicio;
import jose.a.desarrollador.Pantallas.PantallaLoguin;


public class Principal extends Game{	
    public Graphics.DisplayMode display;
	@Override
	public void create () {
            Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);
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

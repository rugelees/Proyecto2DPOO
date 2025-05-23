package vistaAdmin;

import java.awt.BorderLayout;
import java.util.List;
import vistaAdmin.VentanaIncioSesion;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Inicial extends JFrame
{

	private VentanaIncioSesion inicio;
    private PanelOpciones panelOpciones;


    public Inicial(  )
    {
  
        BannerParque panelFondo = new BannerParque("./imagenes/parque.jpg");
        panelFondo.setLayout(new BorderLayout());
        
        panelOpciones = new PanelOpciones(null, null);
        panelFondo.add(panelOpciones, BorderLayout.EAST);

        setContentPane(panelFondo);

        setTitle( "Parque de diversiones" );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
//        setSize( 1100, 1100 );
        setLocationRelativeTo( null );
        setVisible( true );
        
//        Este es para que se abra por completo en la pantallas
        setExtendedState(JFrame.MAXIMIZED_BOTH);

    }


    public static void main( String[] args )
    {

    	Inicial ventana =new Inicial();
    }

}

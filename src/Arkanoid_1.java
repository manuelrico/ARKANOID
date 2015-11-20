
import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.graphics.GRectangle;
import acm.program.*;


public class Arkanoid_1 extends acm.program.GraphicsProgram{

	
	private static int ANCHO_PANTALLA=400;
	private static int ALTO_PANTALLA=400;
	private static int ANCHO_CURSOR = 60;
	
	//constantes para la pirámide
	private static final int ANCHO_LADRILLO = 30;
	private static final int ALTO_LADRILLO = 12;
	private static final int LADRILLOS_BASE = 12;
	
	GImage fondo = new GImage ("Gif.gif");
	int alto_pelota = 10;
	
	GRect cursor;
	GOval pelota;
	double xVelocidad = 3;  //velocidad en el eje x
	double yVelocidad = -3;  //velocidad en el eje y
	
	public void init(){
		add(fondo,0,0);
		setSize(ANCHO_PANTALLA, ALTO_PANTALLA);
		cursor = new GRect(ANCHO_CURSOR,5);
		cursor.setFilled(true);
		cursor.setFillColor(Color.white);
		add(cursor, 0, ALTO_PANTALLA-100);
		
		pelota = new GOval(alto_pelota, alto_pelota);
		pelota.setFilled(true);
		pelota.setFillColor(Color.white);
		add(pelota, 185, ALTO_PANTALLA-110);
		
		
		pintaPiramide();
		
		addMouseListeners();
	}
	
	public void run(){
		while(true){
			pelota.move(xVelocidad, yVelocidad);
			chequeaColision();
			pause(20);
		}
	}
	
	
	
	private void pintaPiramide(){
		int x= -(ANCHO_PANTALLA - LADRILLOS_BASE*ANCHO_LADRILLO) /2;
		int y= 0;

		for (int j=0; j<LADRILLOS_BASE; j++){
			for (int i=j; i<LADRILLOS_BASE; i++){
				GRect ladrillo = new GRect (ANCHO_LADRILLO,ALTO_LADRILLO);
				add (ladrillo,i*ANCHO_LADRILLO-x,y+j*ALTO_LADRILLO);
				ladrillo.setFilled(true);
				ladrillo.setFillColor(Color.white);
				pause(60);
			}
			x = x+ANCHO_LADRILLO/2;
		}
	}
	
	
	private void chequeaColision(){
		if (chequeaPared()){
			//chequeo si toca con el cursor
			if(!chequeaCursor()){
				//chequeaLadrillos();
			}
		}

	}
	
	//private boolean chequeaLadrillos(){
	//boolean auxiliar = true;
	//GRectangle ladrillo;
	//if(pelota.getBounds() <=ladrillo){
	//yVelocidad = -yVelocidad;
			//auxiliar = false;
	//}
	//return auxiliar;
		
	//}
	
	private boolean chequeaPared(){
		boolean auxiliar = true;
		//si toca el techo
		if (pelota.getY() <= 0){
			yVelocidad = -yVelocidad;
			auxiliar = false;
		}

		//si toca la pared derecha
		if (pelota.getX() >= ANCHO_PANTALLA - alto_pelota){
			xVelocidad = -xVelocidad;
			auxiliar = false;
		}

		//si toca la pared izquierda
		if (pelota.getX() <= 0){
			xVelocidad = -xVelocidad;
			auxiliar = false;
		}
		return auxiliar;
	}
	
	
	//chequeaCursor devolverá true si ha chocado el cursor con la pelota
	// y false si no ha chocado.
	private boolean chequeaCursor(){
		if (getElementAt(pelota.getX(), pelota.getY()+alto_pelota)==cursor){
			yVelocidad = -yVelocidad;	
		}
		else if (getElementAt(pelota.getX()+alto_pelota, pelota.getY()+alto_pelota)==cursor){
			yVelocidad = -yVelocidad;	
		}
		else if (getElementAt(pelota.getX(), pelota.getY())==cursor){
			xVelocidad = -xVelocidad;	
		}
		else if (getElementAt(pelota.getX()+alto_pelota, pelota.getY())==cursor){
			xVelocidad = -xVelocidad;	
		}else {
			return false;
		} 
		return true;
	}
	

	

	
	//mueve el cursor siguiendo la posición del ratón
	public void mouseMoved (MouseEvent evento){
		if( (evento.getX()+ANCHO_CURSOR) <= ANCHO_PANTALLA){
			cursor.setLocation(evento.getX(),ALTO_PANTALLA-100);
		}
	}
	
	
	
	
	
	
}
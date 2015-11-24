
import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.graphics.GRectangle;
import acm.program.*;
import acm.util.RandomGenerator;


public class Arkanoid_1 extends acm.program.GraphicsProgram{


	private static int ANCHO_PANTALLA=400;
	private static int ALTO_PANTALLA=400;
	private static int ANCHO_CURSOR = 60;

	//constantes para la pirámide
	private static final int ANCHO_LADRILLO = 30;
	private static final int ALTO_LADRILLO = 12;
	private static final int LADRILLOS_BASE = 12;

	GImage fondo = new GImage ("Gif.gif");
	GImage fondo2 = new GImage ("fondo2.jpg");
	int alto_pelota = 10;

	GRect cursor;
	GOval pelota;
	double xVelocidad = 3;  //velocidad en el eje x
	double yVelocidad = -3;  //velocidad en el eje y
	boolean gameOver = false;
	
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
		
		waitForClick();
		while(!gameOver){
			pelota.move(xVelocidad, yVelocidad);
			chequeaColision();
			pause(10);
		}
		if(pelota.getY() == ALTO_PANTALLA -1){
			add(fondo2);
		}
		
	}
	



	private void pintaPiramide(){
		RandomGenerator random = new RandomGenerator();
		int x= -(ANCHO_PANTALLA - LADRILLOS_BASE*ANCHO_LADRILLO) /2;
		int y= 0;

		for (int j=0; j<LADRILLOS_BASE; j++){
			for (int i=j; i<LADRILLOS_BASE; i++){
				GRect ladrillo = new GRect (ANCHO_LADRILLO,ALTO_LADRILLO);
				add (ladrillo,i*ANCHO_LADRILLO-x,y+j*ALTO_LADRILLO);
				ladrillo.setFilled(true);
				ladrillo.setFillColor(random.nextColor());
				pause(60);
			}
			x = x+ANCHO_LADRILLO/2;
		}
	}


	private void chequeaColision(){
		if (chequeaPared()){
			//chequeo si toca con el cursor
			if(!chequeaCursor()){
				chequeaLadrillos();
			}
		}
		if(pelota.getY() <=300){
			gameOver(true);
		}

	}
	
	private void gameOver(boolean b) {
		// TODO Auto-generated method stub
		
	}

	private void chequeaLadrillos() {

		int pelotaX = (int) pelota.getX();
		int pelotaY = (int) pelota.getY();
		int lado = alto_pelota;

		// si chequea posicion devuelve false sigue mirando el resto de puntos
		//de la pelota

		if( !chequeaPosicion(pelotaX, pelotaY,'y')){
			if( !chequeaPosicion(pelotaX+lado, pelotaY-1,'y')){
				if( !chequeaPosicion(pelotaX-1, pelotaY+lado,'x')){
					if( !chequeaPosicion(pelotaX+lado, pelotaY+lado,'y')){
					}
				}
			}
		}
	}



	/**
	 * chequeaPosicion dadas unas cordenadas (posX y posY)de la pelota y una
	 * direccion, calcula el rebote teniendo en cuenta el objeto que se encuentra en esas
	 * coordenadas.
	 * 
	 */
	private boolean chequeaPosicion(int posX, int posY, char direccion) {

		GObject auxiliar;
		boolean choque = false;
		auxiliar = getElementAt(posX, posY);

		// Chequeamos los ladrillos
		if ((auxiliar != cursor) && (auxiliar != fondo )) {
			remove(auxiliar);
			if (direccion == 'y') {
				yVelocidad = -yVelocidad;
			} else {
				xVelocidad = -xVelocidad;
			}
			//puntuacion++;// aumentamos la puntuacion en uno
			choque = true;
		}


		// devolvemos el valor de choque
		return (choque);
	}

	

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
		//si toca la pared de abajo
		if (pelota.getY() ==0){
			yVelocidad = -yVelocidad;
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
import com.nokia.mid.ui.*;
import javax.microedition.lcdui.*;

abstract class GameCanvas extends FullCanvas {
	byte UP_PRESSED=-1;//ÉÏ¼ü
	byte DOWN_PRESSED=-2;//ÏÂ¼ü
	byte LEFT_PRESSED=-3;//×ó¼ü
	byte RIGHT_PRESSED=-4;//ÓÒ¼ü
	byte FIRE_PRESSED=53;//È·¶¨/¿ª»ð¼ü
	byte GAME_A_PRESSED=49;//1¼ü
	byte GAME_B_PRESSED=51;//3¼ü
	byte GAME_C_PRESSED=-6;//×óÈí¼ü
	byte GAME_D_PRESSED=-7;//ÓÒÈí¼ü
	Graphics g0;
	GameCanvas(boolean f) {
	}
	void setFullScreenMode(boolean f){
	}
	int getKeyStates(){
		return 0x4000;
	}
	public void paint(Graphics g1) {
		g0=g1;
		drawPaint(g0);
	}
	abstract void drawPaint(Graphics g);

	Graphics getGraphics(){
		return g0;
	}
	void flushGraphics(){
		repaint();
		serviceRepaints();
	}
}

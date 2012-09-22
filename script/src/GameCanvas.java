import com.nokia.mid.ui.*;
import javax.microedition.lcdui.*;

abstract class GameCanvas extends FullCanvas {
	short UP_PRESSED=0x0002;//ÉÏ¼ü
	short DOWN_PRESSED=0x0040;//ÏÂ¼ü
	short LEFT_PRESSED=0x0004 ;//×ó¼ü
	short RIGHT_PRESSED=0x0020;//ÓÒ¼ü
	short FIRE_PRESSED=0x0100;//È·¶¨/¿ª»ð¼ü
	short GAME_A_PRESSED=0x0200;//1¼ü
	short GAME_B_PRESSED=0x0400;//3¼ü
	short GAME_C_PRESSED=0x0800;//×óÈí¼ü
	short GAME_D_PRESSED=0x1000;//ÓÒÈí¼ü
	Graphics _g0;
	GameCanvas(boolean f) {
	}
	void setFullScreenMode(boolean f){
	}
	int getKeyStates(){
		return 25200;
	}
	public void paint(Graphics g) {
		_g0=g;
		_drawPaint(_g0);
	}
	abstract void _drawPaint(Graphics g);

	Graphics getGraphics(){
		return _g0;
	}
	void flushGraphics(){
		repaint();
		serviceRepaints();
	}
}

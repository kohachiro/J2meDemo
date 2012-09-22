import com.nokia.mid.ui.*;
import javax.microedition.lcdui.*;

abstract class GameCanvas extends FullCanvas {
	short UP_PRESSED=0x0002;//�ϼ�2
	short DOWN_PRESSED=0x0040;//�¼�64
	short LEFT_PRESSED=0x0004 ;//���4
	short RIGHT_PRESSED=0x0020;//�Ҽ�32
	short FIRE_PRESSED=0x0100;//ȷ��/�����256
	short GAME_A_PRESSED=0x0200;//1��512
	short GAME_B_PRESSED=0x0400;//3��1024
	short GAME_C_PRESSED=0x0800;//�����2048
	short GAME_D_PRESSED=0x1000;//�����4096
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

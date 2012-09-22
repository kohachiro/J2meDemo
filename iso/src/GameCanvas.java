import com.nokia.mid.ui.*;
import javax.microedition.lcdui.*;

abstract class GameCanvas extends FullCanvas {
	byte UP_PRESSED=-1;//�ϼ�
	byte DOWN_PRESSED=-2;//�¼�
	byte LEFT_PRESSED=-3;//���
	byte RIGHT_PRESSED=-4;//�Ҽ�
	byte FIRE_PRESSED=53;//ȷ��/�����
	byte GAME_A_PRESSED=49;//1��
	byte GAME_B_PRESSED=51;//3��
	byte GAME_C_PRESSED=-6;//�����
	byte GAME_D_PRESSED=-7;//�����
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

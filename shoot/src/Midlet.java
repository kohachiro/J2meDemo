import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class Midlet extends MIDlet {
	static Midlet intance;
	public Midlet() {
		super();
		// TODO �Զ����ɹ��캯�����
		intance=this;
		
	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO �Զ����ɷ������
		Display.getDisplay(this).setCurrent(new MainCanvas());
	}

	protected void pauseApp() {
		// TODO �Զ����ɷ������

	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO �Զ����ɷ������

	}

}

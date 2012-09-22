import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class Midlet extends MIDlet {
	static Midlet intance;
	public Midlet() {
		super();
		// TODO 自动生成构造函数存根
		intance=this;
		
	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO 自动生成方法存根
		Display.getDisplay(this).setCurrent(new MainCanvas());
	}

	protected void pauseApp() {
		// TODO 自动生成方法存根

	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO 自动生成方法存根

	}

}

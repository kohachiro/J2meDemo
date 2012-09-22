import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public class Midlet extends MIDlet {
	static Midlet instance;
	public Midlet() {
		instance=this;
	}
	protected void startApp() {
		Display.getDisplay(this).setCurrent(new MainCanvas());
	}
	protected void pauseApp() {}
	protected void destroyApp(boolean b){}

}

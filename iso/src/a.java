import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public class a extends MIDlet {
	static a instance;
	public a() {
		instance=this;
	}
	protected void startApp() {
		Display.getDisplay(this).setCurrent(new MainCanvas());
	}
	protected void pauseApp() {}
	protected void destroyApp(boolean arg0){
		notifyDestroyed();
	}

}

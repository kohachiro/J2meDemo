import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public class Hero extends MIDlet
{

    public Hero()
    {
    }

    public void startApp()
    {
        Display.getDisplay(this).setCurrent(new GameCanvas(this));
    }

    public void pauseApp()
    {
    }

    public void destroyApp(boolean flag)
    {
    }
}

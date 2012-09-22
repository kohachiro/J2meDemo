import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
public class a extends MIDlet
{
    public void startApp()
    {
        Display.getDisplay(this).setCurrent(new MainCanvas());
    }

    public void pauseApp()
    {
    }

    public void destroyApp(boolean flag)
    {
    }
}

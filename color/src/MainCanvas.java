import javax.microedition.lcdui.*;
import com.nokia.mid.ui.FullCanvas;
import javax.microedition.io.*;
import java.io.*;


class MainCanvas extends FullCanvas implements Runnable{

    Thread thread;
    boolean active=true;
	byte[] imgStream;
	int[] crcTable = new int[256];
	InputStream is = null;
	Image i;
	MainCanvas(){
		thread = new Thread(this);
        thread.start();
		imageRGBConvertInit();
		try{
			imgStream = readImageToBytes("/c1.png", is,386);
		}catch (Exception e) {}
		try {
			i = Image.createImage(imgStream, 0, imgStream.length);
		}catch (Exception e){}
	}
	protected void paint(Graphics g) {//Canvas»æÍ¼·½·¨
		g.setFont(Font.getFont(0,0,8));
		g.setColor(0);
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(0xFFFFFF);
		g.drawImage(i, getWidth() / 2, getHeight() / 2, g.VCENTER | g.HCENTER);
	}
    public void run(){
		while(active){			
			repaint();
			serviceRepaints();
			try {
				i = Image.createImage(imageHUBConvert(imgStream, 20), 0, imgStream.length);
			}catch (Exception e){}
			try {
				Thread.sleep(100L);
			}catch(Exception e){}
		}
        thread = null;
    }
	int[] addHub(int r, int g, int b,int hub) {
		int[] hsv = new int[3];
		int[] rgb = new int[3];
		if (r != g || g != b){			
			rgb2hsv(r, g, b, hsv);
			hsv2rgb((hsv[0]+hub)%360, hsv[1], hsv[2],rgb);
		}
		return rgb;
	}
	void hsv2rgb(int h, int s, int b, int rgb[]) {
		rgb[0]=rgb[1]=rgb[2]=0;
		if (s==0){
			rgb[0] = rgb[1] = rgb[2] = (int) (b*255);
		}else {
			int f = h * 1000/6-h/60*10000;
			int p = b * (100 - s)*255/10000;
			int q = add5(b * (1000000 - s * f)/1000*255/10000);
			int t = add5(b * (1000000 - s * (10000 - f))/1000*255/10000);
			int x = add5(b * 255/10);
			switch (h/60) {
			case 0:
				rgb[0] = x;
				rgb[1] = t;
				rgb[2] = p;
				break;
			case 1:
				rgb[0] = q;
				rgb[1] = x;
				rgb[2] = p;
				break;
			case 2:
				rgb[0] = p;
				rgb[1] = x;
				rgb[2] = t;
				break;
			case 3:
				rgb[0] = p;
				rgb[1] = t;
				rgb[2] = x;
				break;
			case 4:
				rgb[0] = t;
				rgb[1] = p;
				rgb[2] = x;
				break;
			case 5:
				rgb[0] = x;
				rgb[1] = p;
				rgb[2] = q;
				break;
			}
  	    }
	}
	void rgb2hsv(int r, int g, int b, int hsv[]) {
		int min,max;
		if (r > g) { min = g; max = r; }
		else { min = r; max = g; }
		if (b > max) max = b;
		if (b < min) min = b;							
		int delMax = max - min;
		if ( delMax == 0 ) { 
			hsv[0] = 0;
		}else {                                   
			if ( r == max ) 
				hsv[0] = add5((        (g - b)*100/delMax)*6);
			else if ( g == max ) 
				hsv[0] = add5(( 200 +  (b - r)*100/delMax)*6);
			else 
				hsv[0] = add5(( 400 +  (r - g)*100/delMax)*6);   
		}
		hsv[1] = add5(delMax*1000/max);
		hsv[2] = add5(max*1000/255);
	}
	int add5(int f){
		if (f%10>4)
			f=f/10+1;
		else 
			f=f/10;
		return f;
	}
	byte[] readImageToBytes(String url, InputStream is, int length) throws IOException{
		byte[] ref;
		is = getClass().getResourceAsStream(url);
		ref = new byte[length];
		is.read(ref);
		is.close();
		System.gc();
		return ref;
	}
	void imageRGBConvertInit() {		// Initialize CRC table
		for (int n = 0; n < 256; n++) {
			int c = n;
			for (int k = 0; k < 8; k++) {
				if ( (c & 1) == 1) {
					c = 0xedb88320 ^ (c >>> 1);
				} else {
					c >>>= 1;
				}
				crcTable[n] = c;
			}
		}
	}
	int updateCRC(byte[] data, int off, int len) {
		int c = 0xffffffff;
		for (int n = 0; n < len; n++) {
			c = crcTable[ (c ^ data[off + n]) & 0xff] ^ (c >>> 8);
		}
		return c ^ 0xffffffff;
	}
	byte[] imageHUBConvert(byte[] image, int H) {
		int length=0;
		int i;
		int crc;
		int rgb[]=new int[3];
		for (i = 0; i < image.length; i++) {
			if (image[i]==0x50 && image[i+1]==0x4c && image[i+2]==0x54 && image[i+3]==0x45) {
				length=image[i-1];
				break;
			}
		}
		for (byte j = 0; j < length; j+=3) {
			rgb=addHub(getValue(image[i+j+4]),getValue(image[i+j+5]),getValue(image[i+j+6]), H);
			image[i+j+4]=(byte)rgb[0];
			image[i+j+5]=(byte)rgb[1];
			image[i+j+6]=(byte)rgb[2];
		}
		crc = updateCRC(image, i, length + 4);
		image[i + length + 7] = (byte) (crc >>> 0);
		image[i + length + 6] = (byte) (crc >>> 8);
		image[i + length + 5] = (byte) (crc >>> 16);
		image[i + length + 4] = (byte) (crc >>> 24);
		return image;
	}
	int getValue(int i) {
		return i <= -1 ? i + 256 : i;		
	}
}
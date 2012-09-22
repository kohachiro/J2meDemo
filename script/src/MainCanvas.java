import java.io.*;
import javax.microedition.lcdui.*;
class MainCanvas extends MenuCanvas{
	Image image[]=new Image[2];
	byte char_x=10;
	byte char_y=10;
	byte map_w;
	byte map_h;
	byte balk;
	byte towards;
	byte num_x;
	byte num_y;
	byte map[][];
	byte msgBox;
	short msgLen;
	int MOVE[][]={{1,0,1,2},{4,3,4,5},{7,6,7,8},{4,3,4,5}};
	Sprite charS;
	Sprite mapS;
	void init(){
		try {
			image[0]=Image.createImage("/char.png");
			image[1]=Image.createImage("/map.png");
		} catch (Exception e) {}
		charS=setSprite(image[0],image[0].getWidth()/9,MOVE[towards],0);
		mapS=setSprite(image[1],image[1].getHeight(),setIntArray(3),0);
		num_x=(byte)(ceil(getWidth(),mapS.getWidth())+1);
		num_y=(byte)(ceil(getHeight(),mapS.getWidth())+1);
		setMap("0");
	}
	void loadSave(){

	}
	void drawGame(Graphics g){
		g.setColor(0);
		g.fillRect(0,0,getWidth(),getHeight());
		drawMap(g);
		drawChar(g);
		drawMsgBox(g,"1234567¹þ¹þ¹þeqewqeeuewhrkewÄãµØµÄÈø°¢´ïÈø£¬dasdedeoo112323");
	}
	void drawMap(Graphics g){
		int fx=0,fy=0;
		if (char_x%2==1)
			fx=-mapS.getWidth()/2;
		else if (char_y%2==1)
			fy=-mapS.getHeight()/2;
		for(byte j=0;j<num_y;j++){
			for(byte i=0;i<num_x;i++){
				int x=char_x/2-(num_x-1)/2+i;
				int y=char_y/2-(num_y-1)/2+j;
				int sx=getWidth()/2-1+(2*i-num_x)*mapS.getWidth()/2+fx;
				int sy=getHeight()/2-1+(2*j-num_y)*mapS.getHeight()/2+fy;
				if (x>=0&&x<map_w&&y>=0&&y<map_h&&map[x][y]>=0){
					mapS.setPosition(sx,sy);
					mapS.setFrame(map[x][y]);
					mapS.paint(g);
				}
			}			
		}
	}
	void drawChar(Graphics g){
		charS.setPosition(getWidth()/2-1-charS.getWidth()/2,getHeight()/2-1-charS.getHeight()/2-10);
		charS.paint(g);
	}
	void drawMsgBox(Graphics g,String s){
		if (msgBox*30<s.length()){
			msgLen=(short)s.length();
			g.setColor(0x0000FF);
			g.fillRect(0,getHeight()-45,getWidth(),44);
			g.setColor(0);
			g.drawRect(2,getHeight()-43,getWidth()-3,44-2);		
			g.setColor(0x99DDFF);
			g.drawRect(1,getHeight()-44,getWidth()-3,44-2);
			g.setColor(0xFFFFFF);
			g.drawRect(0,getHeight()-45,getWidth()-3,44-2);
			int l;
			if (msgBox*30+10<s.length()){
				l=Math.min(s.length(),msgBox*30+10);
				g.drawString(s.substring(msgBox*30,l),5,getHeight()-41,20);
			}
			if (msgBox*30+10<s.length()){		
				l=Math.min(s.length(),msgBox*30+20);
				g.drawString(s.substring(msgBox*30+10,l),5,getHeight()-29,20);
			}
			if (msgBox*30+20<s.length()){
				l=Math.min(s.length(),msgBox*30+30);
				g.drawString(s.substring(msgBox*30+20,l),5,getHeight()-17,20);
			}
		}
	}
	void charMove(){
		if (char_x%2==0&&char_y%2==0){
			if (isPressed(UP_PRESSED)){
				if(towards!=0){
					towards=0;
					charS.setFrameSequence(MOVE[towards]);
					charS.setTransform(0);
				}
				if (char_y>=2&&map[char_x/2][char_y/2-1]>balk)
					char_y--;
				charS.nextFrame();
			}else if (isPressed(RIGHT_PRESSED)){
				if(towards!=1){
					towards=1;
					charS.setFrameSequence(MOVE[towards]);
					charS.setTransform(0);
				}
				if (char_x<2*map_w-2&&map[char_x/2+1][char_y/2]>balk)
					char_x++;
				charS.nextFrame();
			}else if (isPressed(DOWN_PRESSED)){
				if(towards!=2){
					towards=2;
					charS.setFrameSequence(MOVE[towards]);
					charS.setTransform(0);
				}
				if (char_y<2*map_h-2&&map[char_x/2][char_y/2+1]>balk)
					char_y++;
				charS.nextFrame();
			}else if (isPressed(LEFT_PRESSED)){
				if(towards!=3){
					towards=3;
					charS.setFrameSequence(MOVE[towards]);
					charS.setTransform(2);
				}
				if (char_x>=2&&map[char_x/2-1][char_y/2]>balk)
					char_x--;
				charS.nextFrame();
			}else if (isPressed(FIRE_PRESSED)){
				if (msgBox*30>msgLen)
					msgBox=0;
				else
					msgBox++;
			}else{
				charS.setFrame(0);
			}
		}else{
			if (towards==0)
				char_y--;
			else if (towards==1)
				char_x++;
			else if (towards==2)
				char_y++;
			else
				char_x--;
		}
	}
	void setMap(String s){
		InputStream is=getClass().getResourceAsStream("/"+s+".map");
		try {
			balk=(byte)is.read();
			map_w=(byte)is.read();
			map_h=(byte)is.read();
			map=new byte[map_w][map_h];
			for (byte i=0;i<map_w;i++)
				is.read(map[i]);
			runScript(s);
		}catch (Exception e){}
	}
	void runScript(String s){
		InputStream is=getClass().getResourceAsStream("/"+s+".s");
		String cmd=null;
		while(!(cmd=nextEntry(is)).equals("")){
			if(cmd.equals("SAY")){
				System.out.println(nextEntry(is));
			}
		}
	}
    String nextEntry(InputStream is){
		StringBuffer m = new StringBuffer();
		try {
			for (int ch=0;(ch=is.read())!=-1&&ch!='\n'&&ch!=','&&ch!=':'&&ch!='=';)
				m.append((char)ch);
			return new String(m.toString().getBytes(),"UTF-8");
		} catch (Exception e){
			return "";
		}
    }
}
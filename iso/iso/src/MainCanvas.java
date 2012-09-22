import java.io.*;
import javax.microedition.lcdui.*;


class MainCanvas extends GameCanvas implements Runnable {
	Image logoImage;
	Image charImage;
	Image mapImage;
	short key;
	byte mode=-2;
	byte keyRelease;
	byte choice;
	byte step;
	byte char_x=0;
	byte char_y=0;
	byte char_w=16;
	byte char_h=31;
	byte towards;
	byte num_x;
	byte num_y;
	byte tiled_w=32;
	byte tiled_h=16;
	byte tiled_z=8;
	byte balk;
	byte map_w;
	byte map_h;
	byte map[][];
	int MOVE[][]={{2,0,2,1},{5,3,5,4},{5,3,5,4},{2,0,2,1}};
	String MENU[]={"开始游戏","继续游戏","帮助","关于","退出"};
	Sprite sprite;
	Sprite logo;
	Sprite tiled;
	MainCanvas() {
		super(true);
		setFullScreenMode(true);
		new Thread(this).start();
		try {
			logoImage=Image.createImage("/l.png");
			charImage=Image.createImage("/c.png");
			mapImage=Image.createImage("/m.png");
		} catch (Exception e) {}
		logo=setSprite(logoImage,logoImage.getWidth()/2,logoImage.getHeight(),setIntArray(2),0);
	}
	void init(){
		sprite=setSprite(charImage,char_w,char_h,MOVE[towards],0);
		tiled=setSprite(mapImage,tiled_w,mapImage.getHeight(),setIntArray(mapImage.getWidth()/tiled_w),0);
		num_x=(byte)(cell(getWidth(),tiled_w)+1);
		num_y=(byte)(cell(getHeight()-1,tiled_h)+1);
		setMap("/1.map");
	}
	void drawPaint(Graphics g) {
		g.setFont(Font.getFont(0,0,8));
		if(mode==-2){
			drawLogo(g);//画logo
		}else if(mode==-1){
			drawMenu(g);//画菜单
		}else if(mode==0){
			gameStart(g);//游戏开始
		}else if(mode==1){
			loadSave();
			mode=0;
		}else if(mode==2){
			drawHelp(g);//画帮助
		}else if(mode==3){
			drawAbout(g);//画关于
		}else if(mode==4){
		}
	}
	void keyInput(){
		short temp = (short)getKeyStates();
		if (temp!=0x4000)
			key=temp;
		if(mode==-2){			
		}else if(mode==-1){
			if(key==UP_PRESSED){
				key=0;
				if(choice==0)
					choice=(byte)(MENU.length-1);
				else
					choice--;
			}else if(key==DOWN_PRESSED){
				key=0;
				if(choice==MENU.length-1)
					choice=0;
				else
					choice++;
			}else if(key!=0){
				key=0;
				mode=choice;
			}
		}else if(mode==0){
			charMove();
		}else if(mode==2){
			if(key!=0){//返回主菜单
				key=0;
				mode=-1;
			}
		}else if(mode==3){
			if(key!=0){//返回主菜单
				key=0;
				mode=-1;
			}
		}else if(mode==4){
		}
		if(keyRelease==2)
			key=0;
		keyRelease=0;
	}
	void drawLogo(Graphics g) {
		g.setColor(0xFFFFFF);
		g.fillRect(0,0,getWidth(),getHeight());
		if(step<20){
			logo.setFrame(step/10);
			logo.setPosition(getWidth()/2-logoImage.getWidth()/4,getHeight()/2-logoImage.getHeight()/2);
			logo.paint(g);
		}else{
			logoImage=null;
			logo=null;
			mode=-1;
		}
	}
	void drawHelp(Graphics g) {
		g.setColor(0);
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(0xFFFFFF);
		g.drawString("移动 上下左右",getWidth()/2,getHeight()/2-16,32|1);
		g.drawString("确定 攻击",getWidth()/2,getHeight()/2,32|1);
		g.drawString("取消 返回",getWidth()/2,getHeight()/2+16,32|1);
	}
	void drawMenu(Graphics g) {
		g.setColor(0);
		g.fillRect(0,0,getWidth(),getHeight());
		for(byte i=0;i<MENU.length;i++){
			if(choice==i)
				g.setColor(0xFFFFFF);
			else
				g.setColor(0xBBBBBB);
			g.drawString(MENU[i],getWidth()/2,getHeight()/2-32+i*16,32|1);
		}		
	}
	void drawAbout(Graphics g) {
		g.setColor(0);
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(0xFFFFFF);
		g.drawString("XXXX公司版权所有",getWidth()/2,getHeight()/2-16,32|1);
		g.drawString("客服电话xxx-xxx",getWidth()/2,getHeight()/2,32|1);
		g.drawString("服务信箱xx@xxxx",getWidth()/2,getHeight()/2+16,32|1);
	}
	void loadSave() {
	}
	void gameStart(Graphics g) {
		g.setColor(0);
		g.fillRect(0,0,getWidth(),getHeight());
		drawMap(g);
	}
	void drawMap(Graphics g) {
		/*if ((num_x+num_y)%2==0){
			for(byte j=0;j<num_y*2-1;j++){
				int odd=j%2;
				for(byte i=0;i<num_x-odd;i++){
					int x=char_x+1-(num_x+num_y)/2+i+j/2+odd;
					int y=char_y+(num_y-num_x+1)/2-i+j/2;
					int sx=getWidth()/2-1-tiled_w/2-(num_x-1)*tiled_w/2+i*tiled_w+odd*tiled_w/2;
					int sy=getHeight()/2-1-mapImage.getHeight()+tiled_h/2+tiled_z+1-(num_y-1)*tiled_h/2+j/2*tiled_h+odd*tiled_h/2;					
					if(x>=0&&y>=0&&x<map_w&&y<map_h&&map[x][y]>=0){
						tiled.setPosition(sx,sy);
						tiled.setFrame(map[x][y]);					
						tiled.paint(g);
					}
				}
				if(j==num_y)
					drawChar(g);
			}
		}else{
			for(byte j=0;j<num_y*2-1;j++){
				int odd=j%2;
				for(byte i=0;i<num_x-1+odd;i++){
					int x=char_x+1-(num_x+num_y)/2+i+j/2;
					int y=char_y+(num_y-num_x+1)/2-i+j/2+odd;
					int sx=getWidth()/2-1-tiled_w/2-(num_x-1)*tiled_w/2+i*tiled_w+(1-odd)*tiled_w/2;
					int sy=getHeight()/2-1-mapImage.getHeight()+tiled_h/2+tiled_z+1-(num_y-1)*tiled_h/2+j/2*tiled_h+odd*tiled_h/2;					
					if(x>=0&&y>=0&&x<map_w&&y<map_h&&map[x][y]>=0){
						tiled.setPosition(sx,sy);
						tiled.setFrame(map[x][y]);					
						tiled.paint(g);
					}
				}
				if(j==num_y)
					drawChar(g);
			}
		}*/
		int m_odd=(num_x+num_y)%2;
		for(byte j=0;j<num_y*2-1;j++){
			int odd=j%2;
			for(byte i=0;i<num_x+m_odd-odd;i++){
				int x=char_x+1-(num_x+num_y)/2+i+j/2+(1-m_odd)*odd;
				int y=char_y+(num_y-num_x+1)/2-i+j/2+m_odd*odd;
				int sx=getWidth()/2-1+(2*i-num_x+odd+m_odd-2*odd*m_odd)*tiled_w/2;
				int sy=getHeight()/2-mapImage.getHeight()+tiled_z+(2-num_y+odd)*tiled_h/2+j/2*tiled_h;					
				if(x>=0&&y>=0&&x<map_w&&y<map_h&&map[x][y]>=0){
					tiled.setPosition(sx,sy);
					tiled.setFrame(map[x][y]);					
					tiled.paint(g);
				}
			}
			if(j==num_y-1)
				drawChar(g);
		}
	}
	void drawChar(Graphics g) {
		sprite.setPosition(getWidth()/2-1-char_w/2,getHeight()/2-1-char_h/2-12);
		sprite.paint(g);
	}
	void charMove() {
		if(key==UP_PRESSED){
			if(towards!=0){
				towards=0;
				sprite.setFrameSequence(MOVE[0]);
				sprite.setTransform(0);				
			}
			sprite.nextFrame();
			char_y--;
		}else if(key==RIGHT_PRESSED){
			if(towards!=1){
				towards=1;
				sprite.setFrameSequence(MOVE[1]);
				sprite.setTransform(0);				
			}
			sprite.nextFrame();
			char_x++;
		}else if(key==DOWN_PRESSED){
			if(towards!=2){
				towards=2;
				sprite.setFrameSequence(MOVE[1]);
				sprite.setTransform(2);
			}
			sprite.nextFrame();
			char_y++;
		}else if(key==LEFT_PRESSED){	
			if(towards!=3){
				towards=3;
				sprite.setFrameSequence(MOVE[0]);
				sprite.setTransform(2);
			}
			sprite.nextFrame();
			char_x--;
		}else{
			sprite.setFrame(0);
		}
	}
	public void run() {
		Graphics g=getGraphics();
		try {
			Thread.sleep(500);
			init();
			while(mode!=MENU.length-1){
				if(getKeyStates()!=0x4000)
					drawPaint(g);
				flushGraphics();
				keyInput();
				step=(byte)(++step%120);
				Thread.sleep(100);
			}
		} catch (Exception e) {}
		a.instance.notifyDestroyed();
	}
	protected void keyPressed(int k) {
		if(k==-6||k==-21)
			key=GAME_C_PRESSED;
		else if(k==-7||k==-22)
			key=GAME_D_PRESSED;
		else if(k==49)
			key=GAME_A_PRESSED;
		else if(k==51)
			key=GAME_B_PRESSED;
		else if(k==53||k==-5)
			key=FIRE_PRESSED;
		else 
			key=(byte)k;
		keyRelease=1;
	}

	protected void keyReleased(int k) {
		if (keyRelease==0)
			key=0;
		else
			keyRelease=2;
	}
	void setMap(String s){
		try{
			InputStream is = getClass().getResourceAsStream(s);
			balk = (byte)is.read();
			map_w = (byte)is.read();
			map_h = (byte)is.read();
			map=new byte[map_w][map_h];
			for(byte j=0;j<map_h;j++)
				for(byte i=0;i<map_w;i++)
					map[i][j] = (byte)is.read();
		}catch(Exception e) {}
	}
   	int cell(int i, byte x){
		return i%x==0?i/x:i/x + 1;
  	}
	int[] setIntArray(int n){
		int[] x=new int[n];
		for(byte i=0;i<x.length;i++)
			x[i]=i;
		return x;
	}
	Sprite setSprite(Image i,int w,int h,int[] x,int f){
		Sprite s=new Sprite(i,w,h);
		s.setFrameSequence(x);
		s.setFrame(f);
		return s;
	}
}

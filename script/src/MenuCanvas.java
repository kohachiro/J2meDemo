import java.util.*;
import javax.microedition.lcdui.*;
class MenuCanvas extends GameCanvas implements Runnable {
	Image _logoImage;
	Image _menuImage;
	Image _arrowImage;
	byte _menu;
	byte _mode=-2;
	byte _choice;
	short step;
	short key;
	short _oldkey;
	short _keyRelease;
	String _MENU[]={"开始游戏","继续游戏","帮助","关于","退出"};
	MenuCanvas() {
		super(true);
		setFullScreenMode(true);
		new Thread(this).start();
		try {
			_logoImage=Image.createImage("/l.png");
			_menuImage=Image.createImage("/t.png");
			_arrowImage=Image.createImage("/a.png");
		} catch (Exception e) {}
	}
	void init(){};
	void loadSave(){};
	void drawGame(Graphics g){};
	void charMove(){};
	void _drawPaint(Graphics g) {
		g.setFont(Font.getFont(0,0,8));
		if(_mode==-2){
			_drawLogo(g);//画logo
		}else if(_mode==-1){
			_drawMenu(g);//画菜单
		}else if(_mode==0){
			drawGame(g);//游戏开始
		}else if(_mode==1){
			loadSave();
			_mode=0;
		}else if(_mode==2){
			_drawHelp(g);//画帮助
		}else if(_mode==3){
			_drawAbout(g);//画关于
		}
	}
	void _keyInput(){
		if (getKeyStates()!=25200)
			key=(short)(_oldkey|getKeyStates());
		else
			key=_oldkey;
		if(_mode==-2){
			if(key!=0){//返回主菜单
				key=0;
				_mode=-1;
			}			
		}else if(_mode==-1){
			if (_menu!=0){
				if (_menu>0)
					_menu--;
				else
					_menu++;
			}else{
				if(isPressed(UP_PRESSED)){
					key=0;
					_menu=-4;
					if(_choice==0)
						_choice=(byte)(_MENU.length-1);
					else
						_choice--;
				}else if(isPressed(DOWN_PRESSED)){
					key=0;
					_menu=4;
					if(_choice==_MENU.length-1)
						_choice=0;
					else
						_choice++;
				}else if(key!=0){
					key=0;
					_mode=_choice;
				}
			}
		}else if(_mode==0){
			_menuImage=null;
			charMove();
		}else if(_mode==2){
			if(key!=0){//返回主菜单
				key=0;
				_mode=-1;
			}
		}else if(_mode==3){
			if(key!=0){//返回主菜单
				key=0;
				_mode=-1;
			}
		}
		if(_keyRelease>0)
			_oldkey &= ~_keyRelease;
		_keyRelease=0;
	}
	void _drawLogo(Graphics g) {
		g.setColor(0xFFFFFF);
		g.fillRect(0,0,getWidth(),getHeight());
		if(step<20){
			Sprite	_logoS=setSprite(_logoImage,2,setIntArray(2),0);
			_logoS.setFrame(step/10);
			_logoS.setPosition(getWidth()/2-_logoS.getWidth()/2,getHeight()/2-_logoS.getHeight()/2);
			_logoS.paint(g);
		}else{
			_logoImage=null;
			_mode=-1;
			step=1;
		}
	}
	void _drawHelp(Graphics g) {
		g.setColor(0);
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(0xFFFFFF);
		g.drawString("移动 上下左右",getWidth()/2,getHeight()/2-16,33);
		g.drawString("确定 攻击",getWidth()/2,getHeight()/2,33);
		g.drawString("取消 返回",getWidth()/2,getHeight()/2+16,33);

	}
	void _drawMenu(Graphics g) {
		short color[]={64,152,200};
		g.setColor(0);
		g.fillRect(0,0,getWidth(),getHeight());
		int num=getHeight()/200*2+3;
		int h=8+num*2;
		int sy=_menuImage.getHeight()+h;
		int u=32,fs=0,fy=0,e=0,f=0;
		fs=Math.min(step,u);
		for(byte i=0;i<fs;i++){
			g.setColor(i*color[0]/u*65536+i*color[1]/u*256+i*color[2]/u);
			g.fillRect(0,i*getHeight()/fs,getWidth(),getHeight()/fs+1);
		}
		if (step>u){
			fy=Math.min((step-u)*2,3*h/2);
			for(byte i=0;i<num+2;i++){
				if(i==num/2+1)
					g.setColor(0xFFFFFF);
				else
					g.setColor(0xBBBBBB);
				int y=i-num/2-1+_choice;
				if (y>=0&&y<_MENU.length){
					g.drawString(_MENU[y],getWidth()/2,sy+(i-1)*h+4+_menu*2,17);
				}else if (y<0){
					g.drawString(_MENU[_MENU.length+y],getWidth()/2,sy+(i-1)*h+4+_menu*2,17);			
				}else{
					g.drawString(_MENU[y-_MENU.length],getWidth()/2,sy+(i-1)*h+4+_menu*2,17);
				}
			}
			for(byte i=0;i<fs;i++){
				g.setColor(i*color[0]/u*65536+i*color[1]/u*256+i*color[2]/u);
				if (i*getHeight()/fs<sy+num/2*h+9-fy||i*getHeight()/fs+getHeight()/fs+1>sy+num/2*h+9+fy){
					if (i*getHeight()/fs+getHeight()/fs+1<sy+num/2*h+9-fy)
						e=getHeight()/fs+1;
					else
						e=sy+num/2*h+9-fy-i*getHeight()/fs;
					g.fillRect(0,i*getHeight()/fs,getWidth(),e);
					if (i*getHeight()/fs>sy+num/2*h+9+fy)
						f=i*getHeight()/fs;
					else
						f=sy+num/2*h+9+fy;
					g.fillRect(0,f,getWidth(),getHeight()/fs+1);
				}
			}
			g.setColor(0xFFFFFF);
			g.drawLine(0,sy+num/2*h+9+fy,getWidth(),sy+num/2*h+9+fy);
			g.drawLine(0,sy+num/2*h+9-fy,getWidth(),sy+num/2*h+9-fy);
			if (step-u>3*h/4){
				Sprite	s=setSprite(_arrowImage,2,setIntArray(2),0);
				s.setPosition(getWidth()/2-s.getWidth()/2,sy+num/2*h+9-3*h/2-s.getHeight()-step%4/2);
				s.paint(g);
				s.setFrame(1);
				s.setPosition(getWidth()/2-s.getWidth()/2,sy+num/2*h+9+3*h/2+1+step%4/2);
				s.paint(g);
			}
		}
		if (getHeight()<200)
			g.drawImage(_menuImage,getWidth()/2,0,17);
		else
			g.drawImage(_menuImage,getWidth()/2,getHeight()-190,17);
	}
	void _drawAbout(Graphics g) {
		g.setColor(0);
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(0xFFFFFF);
		g.drawString("XXXX公司版权所有",getWidth()/2,getHeight()/2-16,33);
		g.drawString("客服电话xxx-xxx",getWidth()/2,getHeight()/2,33);
		g.drawString("服务信箱xx@xxxx",getWidth()/2,getHeight()/2+16,33);
	}
	public void run() {
		Graphics g=getGraphics();
		try {
			Thread.sleep(500);
			init();
			while(_mode!=_MENU.length-1){
				long l1 = System.currentTimeMillis();
				if(getKeyStates()!=25200)
					_drawPaint(g);
				flushGraphics();
				_keyInput();
				step=(short)(++step%25200);
				long l2 = 100 - System.currentTimeMillis() + l1;
				if(l2 >0)
					Thread.sleep(l2);
			}
		} catch (Exception e) {}
		Midlet.instance.notifyDestroyed();
	}
	protected void keyPressed(int k) {
		_oldkey |= _getKey(k);
		_keyRelease=-1;	
	}

	protected void keyReleased(int k) {
		if (_keyRelease==0)
			_oldkey &= ~_getKey(k);
		else
			_keyRelease=_getKey(k);
	}
	short _getKey(int k){
		if(k==-6||k==-21)
			return GAME_C_PRESSED;
		else if(k==-7||k==-22)
			return GAME_D_PRESSED;
		else if(k==49)
			return GAME_A_PRESSED;
		else if(k==51)
			return GAME_B_PRESSED;
		else if(k==53||k==-5)
			return FIRE_PRESSED;
		else if(k==50||k==-1)
			return UP_PRESSED;
		else if(k==56||k==-2)
			return DOWN_PRESSED;
		else if(k==52||k==-3)
			return LEFT_PRESSED;
		else if(k==54||k==-4)
			return RIGHT_PRESSED;
		else
			return 0;
	}
	boolean isPressed(int k){
		return (key&k)!=0;
	}
	int[] setIntArray(int n){
		int[] x=new int[n];
		for(byte i=0;i<x.length;i++)
			x[i]=i;
		return x;
	}
	Sprite setSprite(Image i,int n,int[] x,int f){
		Sprite s=new Sprite(i,i.getWidth()/n,i.getHeight());
		s.setFrameSequence(x);
		s.setFrame(f);
		return s;
	}
	int ceil(int w,int t){
		if (w%t==0)
			return w/t;
		else
			return w/t+1;
	}
	int getRandom(int start,int end){		
		return Math.abs(new Random().nextInt())%(end-start)+start;
	}
}

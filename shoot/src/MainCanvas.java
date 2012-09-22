import java.io.IOException;
import java.io.InputStream;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class MainCanvas extends GameCanvas implements Runnable {
	Image logoImage;
	Image charImage;
	Image mapImage;
	Image fireImage;
	Image eFireImage;	
	Image boomImage;
	Image lifeImage;
	Image powerImage;	
	Image enemyImage[];
	short key;
	short step;
	byte mode;
	byte keyRelease;
	byte choice;
	byte flash;
	byte level;
	byte hp=100;
	byte life=3;
	Sprite enemyFire;	
	Sprite logo;
	Sprite player;
	Sprite enemyPlane;
	Sprite migPlane;
	Sprite boss;
	Sprite backGround;
	Sprite boom;	
	String MENU[]={"开始游戏","继续游戏","帮助","关于","退出"};
	int MOVE[]={1,2,0,2,1};
	int EMOVE[]={4,5,6};	
	byte char_w=26;
	byte char_h=23;
	short char_y;
	short char_x;
	byte speed=4;
	byte map_w;
	byte map_h;
	byte map[][];
	byte num_y;
	byte num_x;
	byte tiled=16;
	byte startLine;
	byte PATH[][]={{0,10},{63,10},{-63,70},{63,70},{-63,10}};
	short bom[][]=new short[6][3];//1:x;2:y;
	short fire[][]=new short[30][3];//0:on/off;1:x;2:y
	short eFire[][]=new short[30][3];//0:on/off;1:x;2:y	
	short power[][]=new short[3][3];//0:on/off;1:x;2:y
	short enemy[][]=new short[9][9];//0:on/off;1:x;2:y;3:type;4:towards;5:frame;6frameWidth;7:hp;8:step
	
	boolean isFire;
	byte fen;
	boolean isBoom;
	public MainCanvas() {
		super(true);
		setFullScreenMode(true);
		new Thread(this).start();
		try {
			enemyImage=new Image[3];
			logoImage=Image.createImage("/logo.png");
			charImage=Image.createImage("/player.png");
			mapImage=Image.createImage("/hai.png");
			fireImage=Image.createImage("/22.png");
			eFireImage=Image.createImage("/28.png");
			boomImage=Image.createImage("/27.png");
			lifeImage=Image.createImage("/45.png");
			powerImage=Image.createImage("/0.png");
			enemyImage[0]=Image.createImage("/gameloft.png");
			enemyImage[1]=Image.createImage("/2.png");
			enemyImage[2]=Image.createImage("/5.png");			
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		setMap("/1.map");
		logo=new Sprite(logoImage,logoImage.getWidth()/2,logoImage.getHeight());
		int f[]={0,1};
		logo.setFrameSequence(f);
		player=new Sprite(charImage,char_w,char_h);
		player.setFrameSequence(MOVE);
		player.setFrame(2);
		enemyPlane=new Sprite(enemyImage[1],enemyImage[1].getWidth()/17,enemyImage[1].getHeight());
		enemyPlane.setFrameSequence(EMOVE);
		enemyPlane.setFrame(4);
		migPlane=new Sprite(enemyImage[2],enemyImage[2].getWidth(),enemyImage[2].getHeight());
		int b[]={0};
		migPlane.setFrameSequence(b);
		boss=new Sprite(enemyImage[0],enemyImage[0].getWidth(),enemyImage[0].getHeight());
		int c[]={0};
		boss.setFrameSequence(c);
		enemyFire=new Sprite(eFireImage,eFireImage.getWidth()/2,eFireImage.getHeight());
		int d[]={0,1};
		enemyFire.setFrameSequence(d);
		backGround=new Sprite(mapImage,tiled,tiled);
		int m[]={0,1,2,3,4,5};
		backGround.setFrameSequence(m);
		int n[]={0,1,2,3,4,5};
		boom=new Sprite(boomImage,boomImage.getWidth()/6,boomImage.getHeight());
		boom.setFrameSequence(n);
		char_y=(short)(getHeight()-1-char_h/2);
		char_x=(short)(getWidth()/2-1);
		startLine=(byte)(map_h-1);
		initBoomArray();
	}

	void initBoomArray(){
		bom[0][0]=10;
		bom[0][1]=10;
		bom[0][2]=7;
		bom[1][0]=100;
		bom[1][1]=100;
		bom[1][2]=10;
		bom[2][0]=50;
		bom[2][1]=50;
		bom[2][2]=13;
		bom[3][0]=50;
		bom[3][1]=50;
		bom[3][2]=13;
		bom[4][0]=10;
		bom[4][1]=100;
		bom[4][2]=14;
		bom[5][0]=80;
		bom[5][1]=80;
		bom[5][2]=15;

		//bom[][]={{10,10,7},{100,100,10},{50,50,13},{10,100,14},{80,80,15},{30,30,17}};//1:x;2:y;
	}
	void drawPaint(Graphics g) {
		// TODO 自动生成方法存根
		g.setFont(Font.getFont(0,0,8));			
		if (mode==0){
			drawLogo(g);
		}else if(mode==1){
			drawMenu(g);
		}else if(mode==2){
			gameStart(g);
		}else if(mode==3){
			loadSave();
			gameStart(g);
		}else if(mode==4){
			drawHelp(g);
		}else if(mode==5){
			drawAbout(g);
		}else if(mode==7){
			drawOver(g);		
		}
	}
	private void drawOver(Graphics g) {
		// TODO 自动生成方法存根
		g.setColor(0);
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(0xFFFFFF);
		g.drawString("GAMEOVER",getWidth()/2,getHeight()/2,32|1);
	}


	void  keyInput(){
		int temp=getKeyStates();
		if(temp!=0x4000)
			key=(short)temp;
		if (mode==0){
			key=0;			
			if(key!=0)
				mode=1;				
		}else if(mode==1){
			if(key==UP_PRESSED){
				key=0;		
				if(choice==0)
					choice=(byte)(MENU.length-1);
				else
					choice--;
			}else if(key==DOWN_PRESSED){
				key=0;		
				if (choice==MENU.length-1)
					choice=0;
				else
					choice++;
			}else if(key!=0){
				key=0;
				mode=(byte)(choice+2);
				step=0;
			}
				
		}else if(mode==2||mode==3){
			charMove();
		}else if(mode==4){
			if(key!=0)
				mode=1;				
			key=0;
		}else if(mode==5){
			if(key!=0)
				mode=1;
			key=0;				
		}
		if (keyRelease==2)
			key=0;
		keyRelease=0;
	}
	private void charMove() {
		// TODO 自动生成方法存根
		if (flash>0)
			flash--;
		else 
			if(key==UP_PRESSED){
				if(char_y>=char_h/2+2)
					char_y-=speed;
				nextFrame(0);
			}else if(key==RIGHT_PRESSED){
				if(char_x<getWidth()-1-char_w/2)
					char_x+=speed;
				nextFrame(1);
			}else if(key==DOWN_PRESSED){
				if (char_y<getHeight()-1-char_h/2)
					char_y+=speed;
				nextFrame(0);
			}else if(key==LEFT_PRESSED){
				if(char_x>=char_w/2)
					char_x-=speed;
				nextFrame(-1);
			}else if(key==FIRE_PRESSED){
				isFire=!isFire;
			}else if(key==GAME_A_PRESSED){
				isBoom=true;
			}
			else{
				nextFrame(0);
			}
	}




	private void nextFrame(int i) {
		// TODO 自动生成方法存根
		int f=player.getFrame();
		if (i==-1&&f!=0)
			--f;
		if(i==1&&f!=MOVE.length-1)
			++f;
		if (i==0){
			if(f==0)
				f=1;
			if(f==1||f==2||f==3)
				f=2;
			if (f==4)
				f=3;
		}
		player.setFrame(f);
		if (f==3||f==4){
			player.setTransform(2);
		}else{
			player.setTransform(0);
		}
			
	}

	private void drawAbout(Graphics g) {
		// TODO 自动生成方法存根
		g.setColor(0);
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(0xFFFFFF);
		g.drawString("XXXX公司版权所有",getWidth()/2,getHeight()/2-16,32|1);
		g.drawString("客服电话xxx-xxx",getWidth()/2,getHeight()/2,32|1);
		g.drawString("服务信箱xx@xxxx",getWidth()/2,getHeight()/2+16,32|1);
		
	}

	private void drawHelp(Graphics g) {
		// TODO 自动生成方法存根
		g.setColor(0);
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(0xFFFFFF);
		g.drawString("移动 上下左右",getWidth()/2,getHeight()/2-16,32|1);
		g.drawString("确定 攻击",getWidth()/2,getHeight()/2,32|1);
		g.drawString("取消 返回",getWidth()/2,getHeight()/2+16,32|1);
	}

	private void loadSave() {
		// TODO 自动生成方法存根

	}

	private void gameStart(Graphics g) {
		// TODO 自动生成方法存根
		g.setColor(0);
		g.fillRect(0,0,getWidth(),getHeight());
		initEnemy();
		enemyMove();
		fireMove();
		drawMap(g);
		drawFire(g);
		drawEnemy(g);		
		drawChar(g);
		drawBar(g);
		drawPower(g);
		drawboo(g);
	}

	private void drawboo(Graphics g) {
		// TODO 自动生成方法存根
		if (isBoom)
			for(byte i=0;i<bom.length;i++){			
				if (bom[i][2]>0&&bom[i][2]<7){
					boom.setFrame(bom[i][2]-1);
					boom.setPosition(bom[i][0],bom[i][1]);
					boom.paint(g);
				}
				if (bom[i][2]>0)
					bom[i][2]--;
			}
			if (bom[bom.length-1][2]==0){
				isBoom=false;
				initBoomArray();
			}
	}


	private void drawPower(Graphics g) {
		// TODO 自动生成方法存根
		for (byte i=0;i<power.length;i++){
			if (power[i][0]==1){
				if (isCollision(power[i][1],power[i][2],powerImage.getWidth(),powerImage.getHeight(),char_x,char_y,char_w,char_h)){
					power[i][0]=0;
					level++;
				}else if (power[i][2]>getHeight()+powerImage.getHeight()){
					power[i][0]=0;
				}else{
					power[i][2]+=speed/2;
					g.drawImage(powerImage,power[i][1]-powerImage.getWidth()/2,power[i][2]-powerImage.getHeight()/2,20);
				}
			}
		}
	}


	private void drawBar(Graphics g) {
		// TODO 自动生成方法存根
		g.setColor(0);
		g.drawRect(3,3,40,5);
		g.setColor(0xFF0000);
		g.fillRect(3,4,hp*40/100,4);
		for (byte i=0;i<life;i++)
			g.drawImage(lifeImage,1+i*(lifeImage.getWidth()+1),getHeight()-1-lifeImage.getHeight()-1,20);
		g.drawString("得分:"+fen,getWidth()-10,12,32|8);
	}
	void proessCollision(){
		hp-=50;
		if (hp>0){
			flash=12;
		}else if(life>0){
			char_y=(short)(getHeight()-1-char_h/2);
			char_x=(short)(getWidth()/2-1);
			life-=1;
			hp=100;
			flash=40;
		}else{
			mode=7;
		}
		
	}

	private void enemyMove() {
		// TODO 自动生成方法存根
		for (byte i=0;i<enemy.length;i++){
			if (enemy[i][0]<0)
				enemy[i][0]++;
			if (enemy[i][0]==1){
				if (flash==0&&(isCollision(enemy[i][1],enemy[i][2],enemy[i][6]/3,enemyImage[enemy[i][3]].getHeight(),char_x,char_y,char_w/3,char_h)
						||isCollision(enemy[i][1],enemy[i][2],enemy[i][6],enemyImage[enemy[i][3]].getHeight()/3,char_x,char_y,char_w,char_h/3)
						||isCollision(enemy[i][1],enemy[i][2],enemy[i][6],enemyImage[enemy[i][3]].getHeight()/3,char_x,char_y,char_w/3,char_h)
						||isCollision(enemy[i][1],enemy[i][2],enemy[i][6]/3,enemyImage[enemy[i][3]].getHeight(),char_x,char_y,char_w,char_h/3))){
					enemy[i][7]-=5;
					
					if (enemy[i][7]<=0){				
						enemy[i][0]=-6;
						fen+=10;
						if (enemy[i][3]==2)
							newPower(enemy[i][1],enemy[i][2]);						
					}
					proessCollision();
				}
				if (enemy[i][2]>getHeight()+enemyImage[enemy[i][3]].getHeight()
						||enemy[i][1]<-enemy[i][6]
						||enemy[i][1]>getWidth()+enemyImage[enemy[i][3]].getWidth()){
					enemy[i][0]=0;
				}else {
					if (enemy[i][3]==0){
						if (enemy[i][1]==PATH[enemy[i][8]][0]+getWidth()/2&&enemy[i][2]<PATH[enemy[i][8]][1]){
							enemy[i][2]++;
						}else if (enemy[i][1]==PATH[enemy[i][8]][0]+getWidth()/2&&enemy[i][2]>PATH[enemy[i][8]][1]){
							enemy[i][2]--;
						}else if (enemy[i][1]<PATH[enemy[i][8]][0]+getWidth()/2&&enemy[i][2]==PATH[enemy[i][8]][1]){
							enemy[i][1]++;
						}else if (enemy[i][1]>PATH[enemy[i][8]][0]+getWidth()/2&&enemy[i][2]==PATH[enemy[i][8]][1]){
							enemy[i][1]--;
						}else if (enemy[i][1]<PATH[enemy[i][8]][0]+getWidth()/2&&enemy[i][2]<PATH[enemy[i][8]][1]){
							enemy[i][1]++;
							enemy[i][2]++;
						}else if (enemy[i][1]>PATH[enemy[i][8]][0]+getWidth()/2&&enemy[i][2]>PATH[enemy[i][8]][1]){							
							enemy[i][1]--;
							enemy[i][2]--;
						}else if (enemy[i][1]>PATH[enemy[i][8]][0]+getWidth()/2&&enemy[i][2]<PATH[enemy[i][8]][1]){
							enemy[i][1]--;
							enemy[i][2]++;
						}else if (enemy[i][1]<PATH[enemy[i][8]][0]+getWidth()/2&&enemy[i][2]>PATH[enemy[i][8]][1]){
							enemy[i][2]--;
							enemy[i][1]++;							
						}else{
							if (enemy[i][8]>=PATH.length-1)
								enemy[i][8]=0;
							else
								enemy[i][8]++;
								
						}
						if (step%20>12&&step%2==0)
							addeFire(enemy[i][1],enemy[i][2]);
					}else{
						if (enemy[i][4]==0){
							if (enemy[i][3]==2)
								enemy[i][2]+=1;
							else
								enemy[i][2]+=speed;
						}else if (enemy[i][4]==-1){
							enemy[i][1]-=speed;
							enemy[i][2]+=speed;
						}else if (enemy[i][4]==1){
							enemy[i][1]+=speed;
							enemy[i][2]+=speed;
						}else if (enemy[i][4]==-2){
							enemy[i][2]+=speed;
							if (enemy[i][2]>=getHeight()/3)	{
								enemy[i][5]=2;
								enemy[i][4]=2;
							}
						}else if (enemy[i][4]==2){
							enemy[i][1]-=speed;
						}
						
					}if (step%20>16&&step%2==0)
					addeFire(enemy[i][1],enemy[i][2]);
				}
			}
		}
	}


	private void initEnemy() {
		// TODO 自动生成方法存根
		if (step%300==50){
			addEnemy(50,0,1,-2,20);
			addEnemy(50,-20,1,-2,20);
			addEnemy(50,-40,1,-2,20);			
		}else if (step%300==100){
			addEnemy(0,0,1,0,20);
			addEnemy(60,0,1,-1,20);
			addEnemy(-60,0,1,1,20);
		}else if (step%300==150){
			addEnemy(60,0,1,-1,20);
			addEnemy(60,-20,1,-1,20);
			addEnemy(60,-40,1,-1,20);
		}else if (step%300==200){
			addEnemy(-60,0,1,1,20);
			addEnemy(-60,-20,1,1,20);
			addEnemy(-60,-40,1,1,20);
		}else if (step%300==250){
			addEnemy(0,0,1,0,20);
			addEnemy(20,-20,1,0,20);
			addEnemy(-20,-20,1,0,20);
		}else if (step%300==299){
			addEnemy(0,0,2,0,100);
		}
		if (step==1300){
			addEnemy(0,0,0,0,500);
		}
	}


	private void addEnemy(int fx,int fy,int type,int towards,int hp) {
		// TODO 自动生成方法存根
		for (byte i=0;i<enemy.length;i++){
			if (enemy[i][0]==0){
				enemy[i][0]=1;
				enemy[i][1]=(short)(getWidth()/2-1+fx);
				enemy[i][2]=(short)(-enemyImage[type].getHeight()+fy);
				enemy[i][3]=(short)type;
				enemy[i][4]=(short)towards;
				enemy[i][5]=0;
				enemy[i][7]=(short)hp;
				if (enemy[i][3]==0||enemy[i][3]==2)
					enemy[i][6]=(short)enemyImage[type].getWidth();
				else if (enemy[i][3]==1)
					enemy[i][6]=(short)(enemyImage[type].getWidth()/17);
				break;
			}
		}
	}
	private void newPower(short x,short y) {	
		for (byte i=0;i<power.length;i++){
			if (power[i][0]==0){
				power[i][0]=1;
				power[i][1]=x;
				power[i][2]=y;
				break;
			}
		}
	}
	private void newFire(int fx) {
		for (byte i=0;i<fire.length;i++){
			if (fire[i][0]==0){
				fire[i][0]=1;
				fire[i][1]=(short)(char_x+fx);
				fire[i][2]=char_y;
				break;
			}
		}		
	}
	private void addeFire(short x,short y) {
		for (byte i=0;i<eFire.length;i++){
			if (eFire[i][0]==0){
				eFire[i][0]=1;
				eFire[i][1]=x;
				eFire[i][2]=y;
				break;
			}
		}		
	}	
	private void fireMove() {
		// TODO 自动生成方法存根
		if (isFire&&step%3==0){
			if (level==0){
				newFire(0);
			}else if (level==1){
				newFire(-2);				
				newFire(2);
			}else if (level==2){
				newFire(0);				
				newFire(-3);				
				newFire(3);	
			}else if (level>=3){
				newFire(-6);				
				newFire(-2);				
				newFire(2);
				newFire(6);				
			}
		}
		for (byte i=0;i<fire.length;i++){
			if (fire[i][0]==1){
				for (byte j=0;j<enemy.length;j++){
					if (enemy[j][0]==1){
						if (isCollision(enemy[j][1],enemy[j][2],enemy[j][6]/3,enemyImage[enemy[j][3]].getHeight(),fire[i][1],fire[i][2],fireImage.getWidth(),fireImage.getHeight())
								||isCollision(enemy[j][1],enemy[j][2],enemy[j][6],enemyImage[enemy[j][3]].getHeight()/3,fire[i][1],fire[i][2],fireImage.getWidth(),fireImage.getHeight())){
							enemy[j][7]-=5;
							
							if (enemy[j][7]<=0){				
								enemy[j][0]=-6;
								fen+=10;
								if (enemy[j][3]==2)
									newPower(enemy[j][1],enemy[j][2]);						
							}
							fire[i][0]=0;

						}
					}
				}
				if (fire[i][2]<0-fireImage.getHeight())
					fire[i][0]=0;	
				else
					fire[i][2]-=speed*2;
			}
		}
		for (byte i=0;i<eFire.length;i++){
			if (eFire[i][0]==1){
				if (flash==0&&(isCollision(eFire[i][1],eFire[i][2],eFireImage.getWidth()/2,eFireImage.getHeight(),char_x,char_y,char_w/3,char_h)
					||isCollision(eFire[i][1],eFire[i][2],eFireImage.getWidth()/2,eFireImage.getHeight(),char_x,char_y,char_w,char_h/3))){
					proessCollision();
				}					
				if (eFire[i][2]>getHeight()+eFireImage.getHeight())
					eFire[i][0]=0;	
				else
					eFire[i][2]+=speed*2;
			}
		}
	}


	private void drawFire(Graphics g) {
		// TODO 自动生成方法存根
		for (byte i=0;i<fire.length;i++){
			if (fire[i][0]==1){
				g.drawImage(fireImage,fire[i][1],fire[i][2],2|1);
			}
		}
		for (byte i=0;i<eFire.length;i++){
			if (eFire[i][0]==1){
				enemyFire.setPosition(eFire[i][1]-eFireImage.getWidth()/4,eFire[i][2]-eFireImage.getHeight()/2);
				enemyFire.paint(g);
			}
		}

	}

	private void drawEnemy(Graphics g) {
		// TODO 自动生成方法存根
		for (byte i=0;i<enemy.length;i++){
			if (enemy[i][0]<0){
				boom.setFrame(6+enemy[i][0]);
				boom.setPosition(enemy[i][1]-boomImage.getWidth()/12,enemy[i][2]-boomImage.getHeight()/2);
				boom.paint(g);
			}else if (enemy[i][0]==1){
				if (enemy[i][3]==0){//boss
					boss.setPosition(enemy[i][1]-enemy[i][6]/2,enemy[i][2]-enemyImage[enemy[i][3]].getHeight()/2);
					boss.paint(g);
				}else if (enemy[i][3]==1){//enemy
					enemyPlane.setFrame(enemy[i][5]);
					enemyPlane.setPosition(enemy[i][1]-enemy[i][6]/2,enemy[i][2]-enemyImage[enemy[i][3]].getHeight()/2);
					enemyPlane.paint(g);
				}else if (enemy[i][3]==2){//enemy
					migPlane.setPosition(enemy[i][1]-enemy[i][6]/2,enemy[i][2]-enemyImage[enemy[i][3]].getHeight()/2);
					migPlane.paint(g);
				}
			}
		}
	}
	private void drawMap(Graphics g) {
		// TODO 自动生成方法存根
		if (startLine==num_y-1)
			startLine=(byte)(map_h-1);
		if (step*speed/2%tiled==0)
			startLine--;
		for (byte j=0;j<num_y;j++){
			for (byte i=0;i<num_x;i++){
				int x=(map_w-1)/2-(num_x-1)/2+i;
				int y=startLine-num_y+1+j;
				int sx=getWidth()/2-1-tiled/2-(num_x-1)/2*tiled+i*tiled;
				int sy=getHeight()-tiled-(num_y-1)*tiled+j*tiled+step*speed/2%tiled;
				backGround.setFrame(map[x][y]);
				backGround.setPosition(sx,sy);
				backGround.paint(g);
			}
		}
	}

	private void drawChar(Graphics g) {
		// TODO 自动生成方法存根
		if (flash%2==0){
			player.setPosition(char_x-char_w/2,char_y-char_h/2);
			player.paint(g);
		}
	}

	private void drawMenu(Graphics g) {
		// TODO 自动生成方法存根
		g.setColor(0);
		g.fillRect(0,0,getWidth(),getHeight());	
		for(byte i=0;i<MENU.length;i++){
			if(choice==i)
				g.setColor(0xFFFFFF);
			else
				g.setColor(0x888888);
			g.drawString(MENU[i],getWidth()/2-1,getHeight()/2-1-(MENU.length-1)/2*16+i*16,33);
		}
	}

	private void drawLogo(Graphics g) {
		// TODO 自动生成方法存根
		g.setColor(0xFFFFFF);
		g.fillRect(0,0,getWidth(),getHeight());
		if (step<20){
			logo.setFrame(step/10);
			logo.setPosition(getWidth()/2-1-logoImage.getWidth()/4,getHeight()/2-1-logoImage.getHeight()/2);
			logo.paint(g);
		}else{
			mode=1;
			logo=null;
			logoImage=null;
		}
			
	}

	void setMap(String s) {
		// TODO 自动生成方法存根
		try {
			InputStream is=getClass().getResourceAsStream(s);
			is.read();
			map_w=(byte)is.read();
			map_h=(byte)is.read();
			map=new byte[map_w][map_h];
			for (byte j=0;j<map_h;j++)
				for (byte i=0;i<map_w;i++)
					map[i][j]=(byte)is.read();
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
	public void run() {
		// TODO 自动生成方法存根
		Graphics g=getGraphics();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
		}
		num_x=(byte)(cell(getWidth(),tiled));
		if (num_x%2==0)
			++num_x;
		num_y=(byte)(cell(getHeight(),tiled)+1);
		while(mode!=6){
			if(getKeyStates()!=0x4000)
				drawPaint(g);
			flushGraphics();
			keyInput();
			step=(short)(++step%24000);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
		}
	}
	int cell(int w, int t) {
		// TODO 自动生成方法存根
		if (w%t==0)
			return w/t;
		else
			return w/t+1;
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
	boolean isCollision(int ax,int ay,int aw,int ah,int bx,int by,int bw,int bh){
		
		return  !(by-bh/2-ay>ah/2||ax-aw/2-bx>bw/2||ay-ah/2-by>bh/2||bx-bw/2-ax>aw/2);
	}
}

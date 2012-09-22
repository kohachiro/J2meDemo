import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.rms.RecordStore;

class MainCanvas extends MenuCanvas{
	Image image[]=new Image[2];
	short char_x;
	short char_y;
	short pos_x;
	short pos_y;
	short camera;//镜头坐标x
	byte towards=1;
	byte tiled=16;
	byte map[][];
	byte map_w;
	byte map_h;
	byte num_x;
	byte num_y;
	byte num_b;
	int MOVE[]={0,1,2,3,2,10,4,5};
	byte switch_camera;
	byte jump;
	byte attack;
	boolean moveJump;
	String MENU[]={"开始游戏","继续游戏","帮助","关于","退出"};
	Sprite charS;
	Sprite mapS;
	Sprite swordsS;
	Sprite fireS;
	Sprite lightS;
	Sprite flyS;
	Sprite backS;
	Sprite gunS;	
	Sprite monsterS[]=new Sprite[5]; 
	Monster monster[]=new Monster[30];//0:on/off;1:x;2:y;3:type;4:towards;5:frame
	byte light[][][]={
			{{-10,22,0,0},{-16,8,0,1},{-16,16,0,0},{-8,16,0,2},{-8,16,0,0},{0,16,2,0},{8,16,0,2},{0,8,0,3}},
			{{-15,22,0,2},{8,16,2,0},{0,16,0,0},{-16,8,0,1},{-8,16,0,2},{8,16,2,0},{0,16,0,0},{-8,8,0,3}}
			};
	void init(){
		try {		
			charS=setSprite(Image.createImage("/char.png"),11,MOVE,0);
			backS=setSprite(Image.createImage("/b0.png"),1,setIntArray(1),0);
			mapS=setSprite(Image.createImage("/tiled1.png"),8,setIntArray(8),0);
			swordsS=setSprite(Image.createImage("/attack1.png"),2,setIntArray(2),0);
			fireS=setSprite(Image.createImage("/fire.png"),4,setIntArray(4),0);
			flyS=setSprite(Image.createImage("/fly.png"),1,setIntArray(1),0);
			lightS=setSprite(Image.createImage("/light.png"),4,setIntArray(4),0);
			monsterS[0]=setSprite(Image.createImage("/monster3.png"),2,setIntArray(2),0);
			monsterS[1]=setSprite(Image.createImage("/monster2.png"),2,setIntArray(2),0);
			monsterS[2]=setSprite(Image.createImage("/monster6.png"),4,setIntArray(4),0);
			monsterS[3]=setSprite(Image.createImage("/leader3.png"),5,setIntArray(5),1);
			monsterS[4]=setSprite(Image.createImage("/boss2_1.png"),2,setIntArray(2),0);
			image[0]=Image.createImage("/boss2_2.png");
//			image[1]=Image.createImage("/boss2_3.png");
			gunS=setSprite(image[1],1,setIntArray(1),0);
		} catch (Exception e) {}
		for (byte i=0;i<monster.length;i++)
			monster[i]=new Monster();
		char_x=(short)(110*tiled);
		char_y=(short)(7*tiled);
		setMap("/0.map");
		num_x=(byte)(ceil(getWidth(),tiled)+1);
		num_y=map_h;
		num_b=(byte)(1+ceil(getWidth(),backS.getWidth()));
	}
	void loadSave() {
		try {
			RecordStore rs = RecordStore.openRecordStore("MyRecord", true);				
			if (rs.getNumRecords()>0){
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(rs.getRecord(1)));
				char_x=dis.readShort();
				char_y=dis.readShort();
			}
			rs.closeRecordStore();
		} catch (Exception e) {}
	}
	void save() {
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		try {
			RecordStore rs = RecordStore.openRecordStore("MyRecord", true);
			dos.writeShort(char_x);
			dos.writeShort(char_y);
			byte data[]=bos.toByteArray();
			if (rs.getNumRecords()>0){
				rs.setRecord(1,data,0,data.length);
			}else{
				rs.addRecord(data,0,data.length);
			}
			rs.closeRecordStore();
		} catch (Exception e) {}
	}	
	void drawGame(Graphics g) {
		g.setColor(0);
		g.fillRect(0,0,getWidth(),getHeight());	
		setCamera();
		monsterMove();
		drawBack(g);
		drawMap(g);
		drawChar(g);
		drawMonster(g);
		if (attack>0)
			attack--;
	}
	void setCamera(){
		if (switch_camera==0){
			camera=(short)(char_x+2*towards*tiled);
		}else {
/*			if (switch_camera==1){
				camera=(short)(char_x+2*towards*tiled);
			}else if (switch_camera==2){
				camera=(short)(char_x+1*towards*tiled);
			}else if (switch_camera==3){
				camera=(short)(char_x+0*towards*tiled);
			}else if (switch_camera==4){
				camera=(short)(char_x-1*towards*tiled);
			}else if (switch_camera==5){
				camera=(short)(char_x-2*towards*tiled);
			}*/
			camera=(short)(char_x+(3-switch_camera)*towards*tiled);
			if (switch_camera>0)
				switch_camera--;
		}
		if (camera<(num_x-3+num_x%2)*tiled/2)
			//9 7 10 7 11 9 12 9 13 11 14 11 15 13 16 13
			camera=(short)((num_x-3+num_x%2)*tiled/2);					
		if (camera>(map_w*2-num_x-1+num_x%2)*tiled/2)
			//9 245 -9  10 243  -11 11 243 -11 12 241 13 241 14 239 15 239 16 237
			camera=(short)((map_w*2-num_x-1+num_x%2)*tiled/2);
		//System.out.println(char_x*2/tiled);
	}
	void drawMap(Graphics g) {
		int fx=0;
		if ((camera*2/tiled)%2==1)
			fx=-tiled/2;
		for(byte j=0;j<num_y;j++){
			for(byte i=0;i<num_x;i++){
				int x=camera/tiled-(num_x-1)/2+i;
				int y=j;
				int sx=getWidth()/2-1-tiled/2-(num_x-1)*tiled/2+i*tiled+fx;
				int sy=getHeight()-map_h*tiled+j*tiled;
				if (x>=0&&x<map_w&&map[x][y]>=0){
					if (map[x][y]==14){
						g.setColor(0);
						g.fillRect(sx,sy,tiled,tiled);
					}else if (map[x][y]>15){
						addMonster(map[x][y]-16,x,y+1);
						map[x][y]=-1;					
					}else {						
						if (map[x][y]<8){
							mapS.setTransform(0);
							mapS.setFrame(map[x][y]);
						}else if (map[x][y]>=8&&map[x][y]<13){
							mapS.setTransform(2);
							mapS.setFrame(map[x][y]-6);
						}
						mapS.setPosition(sx,sy);
						mapS.paint(g);
					}					
				}
			}
		}
	}
	void drawBack(Graphics g) {
		g.setColor(0x002830);
		g.fillRect(0,0,getWidth(),getHeight());
		for (byte i=0;i<num_b;i++){
			backS.setPosition(i*backS.getWidth()-(camera/2)%backS.getWidth(),getHeight()-1-mapS.getHeight()-backS.getHeight());
			backS.paint(g);
		}
	}
	void drawChar(Graphics g) {
/*		if (switch_camera==0){
			charS.setPosition(getWidth()/2-1-charS.getWidth()/2-2*towards*tiled,getHeight()-1-charS.getHeight()-tiled+char_y-(map_h-1)*tiled);
		}else if (switch_camera==1){
			charS.setPosition(getWidth()/2-1-charS.getWidth()/2-1*towards*tiled,getHeight()-1-charS.getHeight()-tiled+char_y-(map_h-1)*tiled);
		}else if (switch_camera==2){
			charS.setPosition(getWidth()/2-1-charS.getWidth()/2-0*towards*tiled,getHeight()-1-charS.getHeight()-tiled+char_y-(map_h-1)*tiled);
		}else if (switch_camera==3){
			charS.setPosition(getWidth()/2-1-charS.getWidth()/2+1*towards*tiled,getHeight()-1-charS.getHeight()-tiled+char_y-(map_h-1)*tiled);
		}else if (switch_camera==4){
			charS.setPosition(getWidth()/2-1-charS.getWidth()/2+2*towards*tiled,getHeight()-1-charS.getHeight()-tiled+char_y-(map_h-1)*tiled);
		}else if (switch_camera==5){
			charS.setPosition(getWidth()/2-1-charS.getWidth()/2+3*towards*tiled,getHeight()-1-charS.getHeight()-tiled+char_y-(map_h-1)*tiled);
		}*/
		if (camera==(num_x-3+num_x%2)*tiled/2){
			pos_x=(short)(char_x-tiled/2);
		}else if (camera==(map_w*2-num_x-1+num_x%2)*tiled/2){
			pos_x=(short)(char_x-(map_w-1-(num_x-1)/2-2)*tiled+tiled/2);
		}else{
			pos_x=(short)(getWidth()/2-1-charS.getWidth()/2+(switch_camera-2)*towards*tiled);
		}
		pos_y=(short)(getHeight()-1-charS.getHeight()-tiled+char_y-(map_h-1)*tiled);
		//setAttack
		if (attack==3){
			charS.setFrame(6);
		}else if (attack==2){
			charS.setFrame(7);
			swordsS.setFrame(0);
		}else if (attack==1){
			charS.setFrame(7);
			swordsS.setFrame(1);
		}
		charS.setPosition(pos_x,getHeight()-1-charS.getHeight()-tiled+char_y-(map_h-1)*tiled);
		charS.paint(g);
		if (attack>0&&attack<3){
			if (towards==1)
				swordsS.setPosition(pos_x+charS.getWidth(),pos_y-2);
			else
				swordsS.setPosition(pos_x-swordsS.getWidth(),pos_y-2);
			swordsS.paint(g);
		}

	}
	void drawMonster(Graphics g) {
		for (byte i=0;i<monster.length;i++){
			if (monster[i].visible==1){
				monsterS[monster[i].type].setTransform(1+monster[i].towards);
				monsterS[monster[i].type].setFrame(monster[i].frame);
				monsterS[monster[i].type].setPosition(monster[i].x-char_x+pos_x,monster[i].y-monsterS[monster[i].type].getHeight());
				monsterS[monster[i].type].paint(g);				
				if (monster[i].type==4){
					gunS.setTransform(0);
					gunS.setPosition(monster[i].x-char_x+pos_x-4,monster[i].y-monsterS[monster[i].type].getHeight()-76);						
					if (monster[i].step%60>5&&monster[i].step%60<15){
						g.drawImage(image[0],monster[i].x-char_x+pos_x,monster[i].y-monsterS[monster[i].type].getHeight()-43,20);
						gunS.paint(g);	
						lightS.setPosition(monster[i].x-char_x+pos_x-4,monster[i].y-monsterS[monster[i].type].getHeight()-76);
						for (byte j=0;j<monster[i].step%60-6;j++){
								lightS.setTransform(light[0][j][2]);
								lightS.setFrame(light[0][j][3]);
								lightS.move(light[0][j][0],light[0][j][1]);
								lightS.paint(g);
						}
					}else if (monster[i].step%60>20&&monster[i].step%60<30){
						g.drawImage(image[0],monster[i].x-char_x+pos_x,monster[i].y-monsterS[monster[i].type].getHeight()-43,20);
						gunS.paint(g);	
						lightS.setPosition(monster[i].x-char_x+pos_x+9,monster[i].y-monsterS[monster[i].type].getHeight()-69);
						for (byte j=0;j<monster[i].step%60-21;j++){
								lightS.setTransform(light[1][j][2]);
								lightS.setFrame(light[1][j][3]);
								lightS.move(light[1][j][0],light[1][j][1]);
								lightS.paint(g);
						}
					}else if (monster[i].step%60>35&&monster[i].step%60<45){
						gunS.setTransform(6);
						gunS.setPosition(monster[i].x-char_x+pos_x-4-64,monster[i].y-monsterS[monster[i].type].getHeight()-76+72);
						gunS.paint(g);	
						g.drawImage(image[0],monster[i].x-char_x+pos_x,monster[i].y-monsterS[monster[i].type].getHeight()-43,20);
						monster[i].x-=tiled/2;
					}else if (monster[i].step%60>50&&monster[i].step%60<60){
						g.drawImage(image[0],monster[i].x-char_x+pos_x,monster[i].y-monsterS[monster[i].type].getHeight()-43,20);
						gunS.paint(g);	
						monster[i].x+=tiled/2;
					}else{
						g.drawImage(image[0],monster[i].x-char_x+pos_x,monster[i].y-monsterS[monster[i].type].getHeight()-43,20);
						gunS.paint(g);	
					}
				}else if (monster[i].type==3){
					if (monster[i].step%20>2&&monster[i].step%20<9){
						if (monster[i].step%20==3){
							flyS.setPosition(monster[i].x-char_x+pos_x-flyS.getWidth(),monster[i].y-monsterS[monster[i].type].getHeight());
							flyS.move(-tiled*3/2,0);
						}else if (monster[i].step%20==4){
							flyS.move(-tiled/2,0);
						}else if (monster[i].step%20==5){
						}else if (monster[i].step%20==6){
						}else if (monster[i].step%20==7){
							flyS.move(tiled/2,0);
						}else if (monster[i].step%20==8){
							flyS.move(tiled*3/2,0);
						}
						flyS.setTransform(monster[i].step%2*2);
						flyS.paint(g);
					}
				}

				if (attack>0&&swordsS.collidesWith(monsterS[monster[i].type],true))
					monster[i].visible=-4;
			}else if (monster[i].visible<0){
				fireS.setTransform(1-towards);
				fireS.setFrame(4+monster[i].visible);
				fireS.setPosition(monster[i].x-char_x+pos_x,monster[i].y-fireS.getHeight());
				fireS.paint(g);
			}
		}
	}
	void charMove() {
		if(isPressed(RIGHT_PRESSED)||isPressed(LEFT_PRESSED)){
			if(towards!=1&&isPressed(RIGHT_PRESSED)||towards!=-1&&isPressed(LEFT_PRESSED)){
				switch_camera=5;
				towards=(byte)-towards;
				charS.setFrameSequence(MOVE);
				charS.setTransform(1-towards);
				swordsS.setTransform(1-towards);
			}else if (isMove()){
				char_x+=towards*tiled/2;
			}
			if (jump==0){
				nextFrame();
			}else{
				moveJump=true;
				charS.setFrame(2);
			}
		}else if(attack==0&&isPressed(FIRE_PRESSED)){
			attack=3;
		}else if(jump==0&&isPressed(UP_PRESSED)){
			jump=4;
		}else if(jump==0&&isPressed(DOWN_PRESSED)){
			charS.setFrame(5);
		}else if(jump==0&&(isPressed(GAME_A_PRESSED)||isPressed(GAME_B_PRESSED))){
			jump=4;
			moveJump=true;
		}else if (isPressed(GAME_C_PRESSED)){
			save();
		}else if (isPressed(GAME_D_PRESSED)){			
			loadSave();
		}else if(jump==0&&attack==0){
			charS.setFrame(0);
		}
		move();
	}
	boolean isMove(){
		if (towards==-1&&char_x<tiled||towards==1&&char_x>(map_w-1)*tiled-tiled/2){
			return false;
		}else if(char_x*2/tiled%2==0&&map[char_x/tiled+towards][(char_y+tiled-1)/tiled-1]>=0){//char_y+tiled-1逢余进一tiled
			return false;
		}else{
			return true;
		}
	}
	void move(){
		if (jump>0){
			charS.setFrame(2);
			if (moveJump&&isMove()){
				char_x+=towards*tiled/2;
			}
			if(jump==4){
				char_y-=tiled*3/2;
			}else if(jump==3){
				char_y-=tiled/2+tiled/8;
			}else if(jump==2){
				char_y-=tiled/4;
			}else if(jump==1){
				char_y-=tiled/8;
			}
			if(jump==1)
				jump=-1;
			else
				jump--;
		}else if(char_x*2/tiled%2==0&&map[char_x/tiled][char_y/tiled]==-1//脚下无砖
				||char_x*2/tiled%2==1&&(map[(char_x-tiled/2)/tiled][char_y/tiled]==-1&&map[(char_x+tiled/2)/tiled][char_y/tiled]==-1)//踩半砖，左右都无砖
				){
			if(moveJump&&char_x*2/tiled%2==0&&char_y*2/tiled%2==0&&isMove()&&map[char_x/tiled+towards][char_y/tiled-1]==-1&&map[char_x/tiled+towards][char_y/tiled]>=0){//踩砖尖，平移一步
				char_x+=towards*tiled/2;//
			}else{
				if (moveJump&&isMove())//是否能横移
					char_x+=towards*tiled/2;
				if (char_y*2/tiled%2==1)
					char_y+=tiled/2;
				else
					char_y+=tiled;
			}
		}else if(jump<0){
			charS.setFrame(5);
			moveJump=false;
			jump=0;
		}
	}
	void addMonster(int type,int x,int y){
		for (byte i=0;i<monster.length;i++){
			if (monster[i].visible==0){
				monster[i].visible=1;
				monster[i].x=(short)(x*tiled);
				monster[i].y=(short)(y*tiled);
				monster[i].type=(byte)type;
				monster[i].towards=-1;
				monster[i].frame=0;
				monster[i].step=0;
				break;
			}
		}
	}
	void monsterMove(){
		for (byte i=0;i<monster.length;i++){
			if (monster[i].visible==1){
				monster[i].step=(byte) (++monster[i].step%120);
				if (monster[i].type==0){
					if (monster[i].x%tiled==0&&(map[monster[i].x/tiled+monster[i].towards][monster[i].y/tiled]==-1||map[monster[i].x/tiled+monster[i].towards][monster[i].y/tiled-1]>=0))
						monster[i].towards*=-1;
					else
						monster[i].x+=(short)(monster[i].towards*tiled/2);
					monster[i].frame=(byte)(1-monster[i].frame);
				}else if (monster[i].type==1){
					if (char_x-monster[i].x>getWidth())
						monster[i].visible=0;
					else
						monster[i].x-=tiled;
					//monster[i].frame=(short)(1-monster[i].frame);
				}else if (monster[i].type==2){
					if (monster[i].x%tiled==0&&monster[i].y%tiled==0&&map[monster[i].x/tiled+monster[i].towards][monster[i].y/tiled-1]>=0){
						monster[i].towards*=-1;
						monster[i].frame=(byte)(++monster[i].frame%3);
					}else if (monster[i].y%tiled!=0||monster[i].x%tiled==0&&map[monster[i].x/tiled+monster[i].towards][monster[i].y/tiled]==-1){
						monster[i].x+=(short)(monster[i].towards*tiled/2);
						monster[i].y+=tiled/4;
						monster[i].frame=3;
					}else{
						monster[i].x+=(short)(monster[i].towards*tiled/2);
						monster[i].frame=(byte)(++monster[i].frame%3);
					}
				}else if (monster[i].type==3){
					if (monster[i].step%20<10){
						if (monster[i].step%20==0){
							monster[i].frame=2;
						}else if (monster[i].step%20==1){
							monster[i].frame=3;
						}else{
							monster[i].frame=4;
						}
					}else{
						if (monster[i].step%20<15)
							monster[i].x-=tiled/2;
						else
							monster[i].x+=tiled/2;
						monster[i].frame=(byte) (++monster[i].frame%2);
					}
				}else if (monster[i].type==4){
					if (monster[i].step%2==0)
						monster[i].frame=(byte)(1-monster[i].frame);
				}
			}else if(monster[i].visible<0){
				monster[i].visible++;
			}
		}
	}
	void nextFrame(){
		int f=charS.getFrame();
		if (f==4)
			charS.setFrame(1);
		else if (f<4)
			charS.nextFrame();
		else
			charS.setFrame(0);
	}
	void setMap(String s){
		InputStream is= getClass().getResourceAsStream(s);
		try{
			map_w=(byte)is.read();
			map_h=(byte)is.read();
			map=new byte[map_w][map_h];
			for (byte i=0;i<map_w;i++)
				is.read(map[i]);
		} catch (Exception e) {}
	}
}
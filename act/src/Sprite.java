import javax.microedition.lcdui.*;
import com.nokia.mid.ui.*;
class Sprite {
	Image _i;
	byte _n;
	byte _t;
	int _f[]={0};
	boolean _v=true;
	byte _w;
	byte _h;
	byte _cw;
	byte _ch;
	byte _cx;
	byte _cy;
	short _sx;
	short _sy;
	Sprite(Image image){
		setImage(image,image.getWidth(),image.getHeight());
    }
	Sprite(Image image, int frameWidth, int frameHeight){
		setImage(image,frameWidth,frameHeight);
	}
	Sprite(Sprite s){
		setImage(s._i,s._w,s._h);
		defineCollisionRectangle(s._cx,s._cy,s._cw,s._ch);
		_n=s._n;
		_t=s._t;
		_f=s._f;
		_sx=s._sx;
		_sy=s._sy;
	}
	boolean collidesWith(Image image, int x, int y, boolean pixelLevel){
		//return !(sy-y>image.getWidth()||sx-x>image.getHeight()||y-sy>h||x-sx>w);
		return !(_sy+_cy-y>image.getHeight()||_sx+_cx-x>image.getWidth()||y-_sy-_cy>_ch||x-_sx-_cx>_cw);
	} 
	boolean collidesWith(Sprite s,boolean pixelLevel){
		//return !(sy-s.sy>s.w||sx-s.sx>s.h||s.sy-sy>h||s.sx-sx>w);
		return !(_sy+_cy-s._sy>s._h||_sx+_cx-s._sx>s._w||s._sy-_sy-_cy>_ch||s._sx-_sx-_cx>_cw);
	}
	//boolean collidesWith(TiledLayer t, boolean pixelLevel) {
	//}
	void defineCollisionRectangle(int x, int y, int width, int height){
		_cx=(byte)x;
		_cy=(byte)y;
		_cw=(byte)width;
		_ch=(byte)height;
	} 
	//void defineReferencePixel(int x, int y) {
	//}
	int getFrame(){
		return _n;
	}
	int getFrameSequenceLength() {
		return _f.length;
	}
	int getRawFrameCount() {
		return _i.getWidth()/_w;
	}
	/*int getRefPixelX() {
	}
	int getRefPixelY() {
	}*/
	void nextFrame(){
		_n=(byte)(++_n%_f.length);
	}
	void prevFrame(){
		if(_n==0)
			_n=(byte)(_f.length-1);
		else
			_n--;
	}
	void setFrame(int sequenceIndex){
		_n=(byte)sequenceIndex;
	}
	void setFrameSequence(int sequence[]){
		_f=sequence;
		_n=0;
	}
	void setImage(Image img, int frameWidth, int frameHeight) {
		_cw = _w = (byte)frameWidth;
		_ch = _h = (byte)frameHeight;
		_i = img;
	}
	/*void setRefPixelPosition(int x, int y) {
	}*/
	void setTransform(int transform){
		_t=(byte)transform;
	}
	int getWidth(){
		return _w;
	}
	int getHeight(){
		return _h;
	}
	int getX(){
		return _sx;
	}
	int getY(){
		return _sy;
	}
	boolean isVisible(){
		return _v;
	} 
	void move(int dx, int dy) {
		_sx+=dx;
		_sy+=dy;
	}
	void paint(Graphics g){
		//drawRegion(g,i,f[n]*w,0,frameWidth,i.getHeight(),t,sx,sy,20);
		if (_v)
			drawRegion(g,_i,_f[_n],_w,_t,_sx,_sy);
	}
	void setPosition(int x, int y){
		_sx=(short)x;
		_sy=(short)y;
	}
	void setVisible(boolean visible) {
		_v=visible;
	}
	//void drawRegion(Graphics g,Image i,int i_w,int i_h,int w,int h,int m,int x,int y,int a){
	void drawRegion(Graphics g,Image i,int f,int w,int m,int x,int y){
		//g.drawRegion(i,f*w,0,w,i.getHeight(),2*m,x,y,20);
	    //int f=i_w/w;
	    int X=g.getClipX();
	    int Y=g.getClipY();
	    int W=g.getClipWidth();
	    int H=g.getClipHeight();
		if (m<4)
			g.setClip(x, y, w, i.getHeight());
		else
			g.setClip(x, y,i.getHeight(),w);
		if (m==0)//TRANS_NONE 0
			g.drawImage(i,x - f * w,y,20);
		else if(m==1)//TRANS_MIRROR_ROT180  1
			DirectUtils.getDirectGraphics(g).drawImage(i,x - f * w,y,20,0x4000);
		else if(m==2)//TRANS_MIRROR 2
			DirectUtils.getDirectGraphics(g).drawImage(i,x - (i.getWidth()/w-1-f) * w,y,20,0x2000);		
		else if(m==3)//TRANS_ROT180 3
			DirectUtils.getDirectGraphics(g).drawImage(i,x - (i.getWidth()/w-1-f) * w,y,20,180);
		else if(m==4)//TRANS_MIRROR_ROT270 4
			DirectUtils.getDirectGraphics(g).drawImage(i,x ,y-i.getWidth()+((i.getWidth()/w-1-f)+1)*w,20,270|0x2000);
		else if(m==5)//TRANS_ROT90 5
			DirectUtils.getDirectGraphics(g).drawImage(i,x ,y-i.getWidth()+((i.getWidth()/w-1-f)+1)*w,20,270);
		else if(m==6)//TRANS_ROT270 6
			DirectUtils.getDirectGraphics(g).drawImage(i,x ,y-i.getWidth()+(f+1)*w,20,90);
		else if(m==7)//TRANS_MIRROR_ROT90 7
			DirectUtils.getDirectGraphics(g).drawImage(i,x ,y-i.getWidth()+(f+1)*w,20,90|0x2000);
		g.setClip(X, Y, W, H);		
	}
}
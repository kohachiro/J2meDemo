import com.nokia.mid.ui.*;
import java.io.*;
import java.util.*;
import javax.microedition.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.media.*;
import javax.microedition.media.control.*;

public class GameCanvas extends FullCanvas implements Runnable{

	private static final byte n_npc[][] = {
		{
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 1, 5, 
			99, 99, 2, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 6, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 3, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 0, 99, 99, 99, 
			99, 99, 99, 99, 7, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 4, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}
	};
	private static final byte K_M[][] = {
		{
			99, 99, 99, 99, 99, 3, 3, 3, 3, 3, 
			3, 3, 3, 3, 3, 3
		}, {
			99, 99, 99, 99, 99, 3, 3, 1, 1, 1, 
			1, 1, 1, 2, 3, 3
		}, {
			99, 99, 99, 99, 99, 3, 4, 1, 1, 1, 
			1, 2, 1, 2, 1, 3
		}, {
			99, 99, 99, 99, 99, 0, 2, 2, 2, 2, 
			2, 2, 1, 1, 4, 3
		}, {
			99, 99, 99, 99, 99, 3, 4, 1, 1, 1, 
			2, 2, 2, 2, 2, 0
		}, {
			99, 99, 99, 99, 99, 3, 1, 2, 1, 1, 
			1, 1, 1, 1, 4, 3
		}, {
			99, 99, 99, 99, 99, 3, 1, 1, 1, 2, 
			1, 3, 1, 1, 1, 3
		}, {
			99, 99, 99, 99, 99, 3, 3, 1, 1, 2, 
			1, 1, 1, 1, 3, 3
		}, {
			99, 99, 99, 99, 99, 3, 3, 3, 3, 3, 
			3, 3, 3, 3, 3, 3
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}
	};
	private static final byte E_M[][] = {
		{
			99, 99, 99, 99, 99, 4, 4, 4, 4, 4, 
			4, 4, 4, 4, 4, 4
		}, {
			99, 99, 99, 99, 99, 4, 3, 1, 1, 1, 
			1, 1, 1, 1, 3, 4
		}, {
			99, 99, 99, 99, 99, 4, 3, 1, 1, 1, 
			1, 1, 1, 1, 1, 4
		}, {
			99, 99, 99, 99, 99, 0, 2, 2, 2, 2, 
			2, 1, 1, 1, 3, 4
		}, {
			99, 99, 99, 99, 99, 4, 3, 1, 1, 1, 
			2, 2, 2, 2, 2, 0
		}, {
			99, 99, 99, 99, 99, 4, 1, 1, 1, 1, 
			2, 1, 1, 1, 3, 4
		}, {
			99, 99, 99, 99, 99, 4, 1, 1, 1, 1, 
			2, 3, 1, 1, 1, 4
		}, {
			99, 99, 99, 99, 99, 4, 3, 1, 1, 1, 
			2, 1, 1, 1, 3, 4
		}, {
			99, 99, 99, 99, 99, 4, 4, 4, 4, 4, 
			0, 4, 4, 4, 4, 4
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}
	};
	private static final byte m_1[][] = {
		{
			4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 
			4, 4, 4, 4, 4, 4
		}, {
			4, 3, 3, 3, 3, 3, 2, 2, 2, 1, 
			1, 1, 1, 1, 3, 4
		}, {
			4, 3, 3, 4, 1, 1, 1, 2, 1, 3, 
			3, 3, 1, 1, 1, 4
		}, {
			4, 4, 4, 1, 1, 1, 1, 2, 1, 1, 
			1, 1, 1, 3, 1, 4
		}, {
			4, 1, 1, 1, 4, 1, 2, 2, 1, 1, 
			1, 3, 1, 3, 1, 4
		}, {
			4, 3, 3, 3, 4, 4, 2, 3, 1, 1, 
			3, 4, 1, 3, 1, 4
		}, {
			4, 3, 4, 1, 3, 2, 2, 1, 1, 1, 
			4, 1, 1, 4, 1, 4
		}, {
			4, 2, 1, 1, 2, 2, 3, 1, 3, 1, 
			4, 1, 1, 1, 2, 4
		}, {
			4, 2, 2, 2, 2, 2, 1, 4, 1, 1, 
			2, 2, 2, 2, 2, 0
		}, {
			4, 2, 1, 2, 1, 4, 3, 1, 3, 3, 
			2, 1, 1, 3, 2, 4
		}, {
			4, 1, 3, 2, 2, 2, 2, 2, 2, 2, 
			2, 2, 1, 3, 1, 4
		}, {
			4, 1, 1, 3, 4, 2, 2, 1, 1, 4, 
			1, 2, 2, 2, 1, 4
		}, {
			4, 1, 1, 3, 3, 2, 1, 3, 1, 1, 
			1, 4, 3, 2, 2, 4
		}, {
			4, 4, 3, 1, 1, 1, 2, 1, 1, 4, 
			1, 3, 2, 2, 2, 4
		}, {
			4, 4, 3, 1, 1, 3, 2, 2, 2, 2, 
			2, 2, 2, 3, 3, 4
		}, {
			4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 
			4, 4, 4, 4, 4, 4
		}
	};
	private static final byte m_2[][] = {
		{
			3, 3, 4, 3, 3, 4, 4, 3, 3, 4, 
			4, 3, 3, 4, 4, 3
		}, {
			4, 4, 4, 3, 3, 3, 2, 2, 2, 3, 
			3, 2, 2, 2, 2, 3
		}, {
			4, 4, 2, 4, 1, 4, 1, 1, 3, 3, 
			2, 2, 3, 3, 2, 4
		}, {
			3, 4, 2, 2, 1, 1, 1, 2, 2, 2, 
			2, 4, 4, 3, 2, 4
		}, {
			3, 4, 4, 1, 2, 2, 4, 4, 1, 1, 
			2, 4, 3, 2, 2, 3
		}, {
			4, 3, 1, 2, 2, 3, 1, 2, 1, 4, 
			2, 2, 2, 2, 3, 3
		}, {
			4, 1, 3, 1, 3, 3, 2, 2, 4, 3, 
			4, 2, 1, 3, 4, 4
		}, {
			3, 2, 3, 1, 3, 1, 2, 3, 3, 3, 
			4, 2, 1, 1, 2, 4
		}, {
			3, 2, 4, 2, 3, 3, 2, 1, 1, 1, 
			1, 2, 2, 3, 2, 0
		}, {
			4, 2, 1, 4, 3, 3, 2, 2, 3, 3, 
			1, 1, 1, 1, 2, 4
		}, {
			4, 1, 1, 4, 3, 3, 3, 2, 3, 3, 
			3, 1, 4, 1, 2, 4
		}, {
			3, 4, 2, 4, 3, 1, 1, 2, 1, 1, 
			3, 3, 3, 2, 2, 3
		}, {
			3, 4, 2, 1, 3, 1, 1, 2, 1, 1, 
			3, 3, 1, 2, 4, 3
		}, {
			4, 4, 3, 1, 2, 4, 1, 1, 4, 1, 
			2, 2, 1, 2, 3, 4
		}, {
			3, 3, 3, 1, 1, 2, 2, 2, 2, 2, 
			4, 3, 3, 3, 3, 3
		}, {
			3, 4, 4, 3, 4, 4, 3, 3, 0, 4, 
			4, 3, 3, 4, 4, 3
		}
	};
	private static final byte m_3[][] = {
		{
			3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 
			3, 3, 3, 3, 3, 3
		}, {
			3, 1, 1, 1, 1, 1, 2, 2, 2, 1, 
			1, 1, 1, 1, 1, 3
		}, {
			3, 1, 1, 3, 1, 1, 1, 4, 1, 1, 
			1, 3, 1, 1, 1, 3
		}, {
			3, 1, 3, 4, 3, 1, 1, 2, 1, 1, 
			3, 4, 3, 3, 1, 3
		}, {
			3, 1, 1, 3, 1, 1, 1, 2, 1, 1, 
			1, 3, 3, 1, 1, 3
		}, {
			3, 1, 1, 3, 1, 2, 2, 2, 2, 2, 
			1, 3, 1, 1, 1, 3
		}, {
			3, 1, 1, 1, 2, 2, 1, 3, 1, 2, 
			2, 1, 1, 1, 1, 3
		}, {
			3, 2, 1, 1, 2, 1, 3, 4, 3, 1, 
			2, 1, 1, 1, 2, 3
		}, {
			3, 2, 4, 2, 2, 3, 4, 3, 4, 3, 
			2, 2, 2, 4, 2, 0
		}, {
			3, 2, 1, 1, 2, 2, 3, 4, 3, 2, 
			2, 1, 1, 1, 2, 3
		}, {
			3, 1, 1, 1, 1, 2, 1, 3, 1, 2, 
			1, 1, 1, 1, 1, 3
		}, {
			3, 1, 1, 3, 1, 2, 2, 2, 2, 2, 
			1, 3, 1, 1, 1, 3
		}, {
			3, 1, 1, 3, 1, 1, 1, 2, 1, 1, 
			1, 3, 3, 1, 1, 3
		}, {
			3, 1, 1, 3, 1, 1, 1, 4, 1, 1, 
			1, 3, 1, 1, 1, 3
		}, {
			3, 1, 1, 1, 1, 1, 2, 2, 2, 1, 
			1, 1, 1, 1, 1, 3
		}, {
			3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 
			3, 3, 3, 3, 3, 3
		}
	};
	private static final byte D_1[][] = {
		{
			8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 
			8, 8, 8, 8, 8, 8
		}, {
			8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 
			8, 8, 8, 8, 8, 8
		}, {
			8, 8, 5, 5, 5, 8, 5, 5, 5, 5, 
			8, 5, 5, 5, 8, 8
		}, {
			8, 8, 5, 5, 5, 8, 5, 5, 5, 5, 
			8, 5, 5, 5, 8, 8
		}, {
			8, 8, 5, 5, 5, 5, 5, 5, 5, 5, 
			5, 5, 5, 5, 8, 8
		}, {
			8, 8, 8, 8, 5, 8, 5, 8, 8, 5, 
			8, 5, 8, 8, 8, 8
		}, {
			8, 8, 5, 5, 5, 5, 5, 8, 8, 5, 
			5, 5, 5, 5, 8, 8
		}, {
			8, 8, 5, 5, 5, 8, 5, 5, 5, 5, 
			8, 5, 5, 5, 8, 8
		}, {
			8, 8, 5, 5, 5, 8, 5, 5, 5, 5, 
			8, 5, 5, 5, 8, 8
		}, {
			8, 8, 5, 5, 5, 5, 5, 8, 8, 5, 
			5, 5, 5, 5, 8, 8
		}, {
			8, 8, 8, 8, 5, 8, 5, 8, 8, 5, 
			8, 5, 8, 8, 8, 8
		}, {
			8, 8, 5, 5, 5, 5, 5, 5, 5, 5, 
			5, 5, 5, 5, 8, 8
		}, {
			8, 8, 5, 5, 5, 8, 5, 5, 5, 5, 
			8, 5, 5, 5, 8, 8
		}, {
			8, 8, 5, 5, 5, 8, 5, 5, 5, 5, 
			8, 5, 5, 5, 8, 8
		}, {
			8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 
			8, 8, 8, 8, 8, 8
		}, {
			8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 
			8, 8, 8, 8, 8, 8
		}
	};
	private static final byte D_2[][] = {
		{
			8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 
			8, 8, 8, 8, 8, 8
		}, {
			8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 
			8, 8, 8, 8, 8, 8
		}, {
			8, 8, 5, 5, 5, 8, 5, 5, 5, 5, 
			8, 5, 5, 5, 8, 8
		}, {
			8, 8, 5, 5, 5, 8, 5, 5, 5, 5, 
			8, 5, 5, 5, 8, 8
		}, {
			8, 8, 5, 5, 5, 5, 5, 5, 5, 5, 
			8, 5, 5, 5, 8, 8
		}, {
			8, 8, 8, 8, 5, 8, 8, 8, 8, 5, 
			8, 5, 8, 8, 8, 8
		}, {
			8, 8, 5, 5, 5, 8, 5, 5, 5, 5, 
			8, 5, 5, 5, 8, 8
		}, {
			8, 8, 5, 5, 5, 5, 8, 5, 8, 8, 
			5, 5, 5, 5, 8, 8
		}, {
			8, 8, 5, 5, 5, 5, 8, 5, 8, 5, 
			5, 5, 5, 5, 8, 8
		}, {
			8, 8, 5, 5, 5, 8, 5, 5, 5, 8, 
			8, 5, 5, 5, 8, 8
		}, {
			8, 8, 8, 8, 5, 8, 5, 8, 8, 8, 
			8, 5, 8, 8, 8, 8
		}, {
			8, 8, 5, 5, 5, 8, 5, 5, 5, 5, 
			5, 5, 5, 5, 8, 8
		}, {
			8, 8, 5, 5, 5, 8, 5, 5, 5, 5, 
			8, 5, 5, 5, 8, 8
		}, {
			8, 8, 5, 5, 5, 8, 5, 5, 5, 5, 
			8, 5, 5, 5, 8, 8
		}, {
			8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 
			8, 8, 8, 8, 8, 8
		}, {
			8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 
			8, 8, 8, 8, 8, 8
		}
	};
	private static final byte D_B[][] = {
		{
			8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 
			8, 99, 99, 99, 99, 99
		}, {
			8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 
			8, 99, 99, 99, 99, 99
		}, {
			8, 8, 5, 5, 5, 5, 5, 5, 5, 8, 
			8, 99, 99, 99, 99, 99
		}, {
			8, 8, 5, 5, 5, 5, 5, 5, 5, 8, 
			8, 99, 99, 99, 99, 99
		}, {
			8, 8, 5, 5, 5, 5, 5, 5, 5, 8, 
			8, 99, 99, 99, 99, 99
		}, {
			8, 8, 5, 5, 5, 5, 5, 5, 5, 8, 
			8, 99, 99, 99, 99, 99
		}, {
			8, 8, 5, 5, 5, 5, 5, 5, 5, 8, 
			8, 99, 99, 99, 99, 99
		}, {
			8, 8, 5, 5, 5, 5, 5, 5, 5, 8, 
			8, 99, 99, 99, 99, 99
		}, {
			8, 8, 5, 5, 5, 5, 5, 5, 5, 8, 
			8, 99, 99, 99, 99, 99
		}, {
			8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 
			8, 99, 99, 99, 99, 99
		}, {
			8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 
			8, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}, {
			99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 
			99, 99, 99, 99, 99, 99
		}
	};
	/*
	private static final byte LEVER_UP[] = {
		2, 74, 58, 64, 4, 0, 25, 40, 70, -120, 
		49, 68, -107, -95, -118, 40, -115, 9, 80, -91, 
		12, 48, 0
	};
	private static final byte CHAR_DIE[] = {
		2, 74, 58, 64, 4, 0, 23, 34, 70, 8, 
		57, 3, 112, 53, 3, 112, 53, 3, 48, 53, 
		2, 104, 0
	};
	private static final byte MOB_DIE[] = {
		2, 74, 58, 64, 4, 0, 11, 26, -126, -12, 
		45, 66, -47, 0
	};
	private static final byte BMOB_DIE[] = {
		2, 74, 58, 64, 4, 0, 21, 40, 70, -124, 
		104, 70, -120, 57, 67, 116, 73, -104, 25, 96, 
		0
	};
	private static final byte MENU_MOVE[] = {
		2, 74, 58, 64, 4, 0, 11, 36, -125, 112, 
		39, 3, 16, 0
	};
	private static final byte MENU_SEL[] = {
		2, 74, 58, 64, 4, 0, 9, 24, -126, 53, 
		45, 80, 0
	};*/
	private byte now_map[][];
	private byte save_maul;
	private Hero midlet;
	private byte n_d;
	private byte n_c;
	private byte n_s;
	private Monster_data mob_d[];
	private byte mob_a;
	private boolean mob_die[];
	private byte c_3[] = {
		0, 0, 0, 0, 0, 0, 0, 0
	};
	private int item;
	private boolean b_m_a;
	private boolean m_p_state;
	private Data_save d_s;
	//private Sound sound;
	private boolean sound_on;
	private boolean vib_on;
	private Thread thread;
	private int timer;
	private byte state;
	private byte g_state;
	private boolean active;
	private boolean g_p_state;
	private byte c_state;
	private byte cho;
	private byte cho_1;
	private int in;
	private byte in_1;
	private int sX;
	private int sY;
	private boolean key;
	private byte stX;
	private byte stY;
	private byte spX;
	private byte spY;
	private int r_c;
	private byte w_p;
	private byte w_s;
	private byte m_w_s;
	private byte c_a;
	private byte m_l;
	private int m_e;
	private int m_h;
	private int m_m;
	private byte m_s[] = { 0, 0, 0, 0, 0 };
	private byte m_i[] = { 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private byte end_m;
	private byte s_p;
	private String main_key;
	private String midi;
	private int m_mm;
	private Image l_img;
	private Image g_img;
	private Image mo_img;
	private Image m_img[];
	private Image gr_img[];
	private Image c_img[];
	private Image n_img[];
	private Image bmo_img[];
	private Image s_img[];
	private Image st_img[];
	private Image h_img[];
	private Image m_txt[];
	private Player player;

	GameCanvas(Hero hero){
		now_map = new byte[16][16];
		mob_d = new Monster_data[8];
		mob_a = 10;
		mob_die = new boolean[8];
		sound_on = true;
		vib_on = true;
		timer = 150;
		state = 0;
		g_state = 1;
		active = true;
		key = true;
		stX = -1;
		stY = 10;
		spX = 1;
		spY = 1;
		w_p = 1;
        m_l = 1;
        m_h = 21;
        m_m = 10000;
		end_m = 0;
		main_key = "";
		//System.gc();
		midlet = hero;
		d_s = new Data_save();
		DeviceControl.setLights(0, 100);
		sX = getWidth() / 2 - 60;
		sY = getHeight() / 2 - 66;
		m_img = new Image[5];
		try{
			l_img = Image.createImage("/logo.png");
			g_img = Image.createImage("/intro.png");
			m_img[0] = Image.createImage("/number.png");
			m_img[1] = Image.createImage("/item.png");
			m_img[2] = Image.createImage("/game_hp.png");
			m_img[3] = Image.createImage("/game_top.png");
			m_img[4] = Image.createImage("/game_bottom.png");
		}catch(Exception exception) {}

		thread = new Thread(this);
		active = true;
		thread.start();
		//System.gc();
	}

	private void charImgLoad()
	{
		c_img = new Image[14];
		try
		{
			c_img[0] = Image.createImage("/char1_2_1.png");
			c_img[1] = Image.createImage("/char1_2_2.png");
			c_img[2] = Image.createImage("/char2_2_1.png");
			c_img[3] = Image.createImage("/char2_2_2.png");
			c_img[4] = Image.createImage("/char3_2_1.png");
			c_img[5] = Image.createImage("/char3_2_2.png");
			c_img[6] = Image.createImage("/char4_2_1.png");
			c_img[7] = Image.createImage("/char4_2_2.png");
			c_img[8] = Image.createImage("/level_up.png");
			c_img[9] = Image.createImage("/claw.png");
			//System.gc();
		}
		catch(Exception exception) { }
	}

	private void gameImgLoad()
	{
		n_img = new Image[5];
		gr_img = new Image[9];
		try{
			n_img[0] = Image.createImage("/house.png");
			n_img[1] = Image.createImage("/mark_2.png");
			n_img[2] = Image.createImage("/mark_3.png");
			n_img[3] = Image.createImage("/mark_1.png");
			n_img[4] = Image.createImage("/npc.png");
			gr_img[0] = Image.createImage("/ground_3.png");
			gr_img[1] = Image.createImage("/ground_2.png");
			gr_img[2] = Image.createImage("/ground_3.png");
			gr_img[3] = Image.createImage("/ground_4.png");
			gr_img[4] = Image.createImage("/ground_5.png");
			gr_img[5] = Image.createImage("/d_ground1.png");
			gr_img[6] = Image.createImage("/d_ground3.png");
			gr_img[7] = Image.createImage("/d_ground4.png");
			gr_img[8] = Image.createImage("/d_ground2.png");
			//System.gc();
		}catch(Exception exception) {}
	}

	private void mon_ImgLoad()
	{
		try{
			mo_img = Image.createImage("/monster.png");
		}catch(Exception exception) {}
		//System.gc();
	}

	private void boss_ImgLoad_1()
	{
		bmo_img = new Image[5];
		try
		{
			bmo_img[0] = Image.createImage("/monster1_k1_1.png");
			bmo_img[1] = Image.createImage("/monster1_k1_2.png");
			bmo_img[2] = Image.createImage("/monster_k2_1.png");
			bmo_img[3] = Image.createImage("/monster_k2_3.png");
			bmo_img[4] = Image.createImage("/ice.png");
		}
		catch(Exception exception) {}
		//System.gc();
	}

	private void storeImgLoad()
	{
		st_img = new Image[2];
		try
		{
			st_img[0] = Image.createImage("/item2.png");
			st_img[1] = Image.createImage("/money.png");
			//System.gc();
		}
		catch(Exception exception) {}
	}

	public void run()
	{
		byte byte0 = 0;
		byte byte1 = 0;
		while(active) 
		{
			if(state == 0 && g_state == 1)
			{
				byte0++;
				if(byte0 > 10)
				{
					if(!g_p_state)
					{
						g_p_state = true;
						byte0 = 0;
					} else
					{
						g_p_state = false;
						byte0 = 0;
						g_state = 0;
						//if(sound_on)
							playMidi("title");
					}
					repaint(0, 0, getWidth(), getHeight());
					serviceRepaints();
				}
			} else
			if(state == 0 && g_state == 2 || state == 3)
			{
				if(g_p_state)
					g_p_state = false;
				else
					g_p_state = true;
				repaint(0, 0, getWidth(), getHeight());
				serviceRepaints();
			} else
			if(state == 1 && g_state == 2)
			{
				if(cho == 0)
				{
					now_map = K_M;
					s_p = 1;
					gameImgLoad();
					charImgLoad();
					storeImgLoad();
					boss_ImgLoad_1();
					mon_ImgLoad();
					state = 2;
					g_state = 0;
					n_d = 3;
					c_a = 3;
					repaint(0, 0, getWidth(), getHeight());
					serviceRepaints();
					//if(sound_on)
						playMidi("town");
				} else
				if(cho == 1)
				{
					s_p = d_s.s_p;
					m_i = d_s.save_inventory;
					m_s = d_s.save_my_state;
					m_l = d_s.save_level;
					m_e = d_s.save_exp;
					m_h = d_s.save_health;
					m_m = d_s.save_money;
					n_d = d_s.n_d;
					n_c = d_s.n_c;
					n_s = d_s.n_s;
					stX = d_s.stX;
					stY = d_s.stY;
					end_m = d_s.end_m;
					gameImgLoad();
					charImgLoad();
					storeImgLoad();
					boss_ImgLoad_1();
					mon_ImgLoad();
					g_state = d_s.save_state;
					if(g_state == 0)
					{
						if(s_p == 1)
						{
							now_map = K_M;
							save_maul = 0;
						} else
						if(s_p == 2)
						{
							now_map = E_M;
							save_maul = 1;
						} else
						if(s_p == 3)
						{
							now_map = K_M;
							save_maul = 2;
						}
						//if(sound_on)
							playMidi("town");
					} else
					if(g_state == 1)
					{
						save_maul = 0;
						if(s_p == 1)
							now_map = m_1;
						else
						if(s_p == 2)
							now_map = map_change(m_2, 2);
						else
						if(s_p == 3)
							now_map = map_change(m_3, 0);
						else
						if(s_p == 4)
							now_map = m_3;
						else
						if(s_p == 5)
							now_map = map_change(m_2, 0);
						else
						if(s_p == 6)
							now_map = map_change(m_2, 1);
						else
						if(s_p == 7)
							now_map = map_change(m_3, 1);
						else
						if(s_p == 8)
							now_map = map_change(m_1, 2);
						else
						if(s_p == 9)
							now_map = map_change(m_1, 1);
						else
						if(s_p == 10)
							now_map = map_change(m_1, 2);
						make_mob(g_state);
						//if(sound_on)
							playMidi("town");
					} else
					if(g_state == 2)
					{
						save_maul = 0;
						if(s_p == 1)
							now_map = D_1;
						else
						if(s_p == 2)
							now_map = map_change(D_1, 0);
						else
						if(s_p == 3)
							now_map = map_change(D_1, 1);
						else
						if(s_p == 4)
							now_map = D_2;
						else
						if(s_p == 5)
							now_map = map_change(D_2, 0);
						else
						if(s_p == 6)
							now_map = map_change(D_2, 1);
						else
						if(s_p == 7)
							now_map = map_change(D_2, 2);
						make_mob(g_state);
						//if(sound_on)
							playMidi("cave");
					} else
					{
						now_map = D_B;
						save_maul = 0;
						make_mob(g_state);
						//if(sound_on)
							playMidi("cave");
					}
					cho = 0;
					state = 2;
					repaint(0, 0, getWidth(), getHeight());
					serviceRepaints();
				}
			} else
			if(state == 2)
			{
				boolean flag = false;
				if(m_p_state)
					m_p_state = false;
				else
					m_p_state = true;
				if(g_state == 0 && m_h < 7 + m_l * 14)
				{
					if(m_l < 50)
					{
						if(byte0 >= 3)
						{
							m_h++;
							byte0 = 0;
							repaint(0, 0, getWidth(), getHeight());
						}
					} else
					if(m_l >= 50 && byte0 >= 3)
					{
						m_h += 3;
						byte0 = 0;
						repaint(0, 0, getWidth(), getHeight());
					}
					byte0++;
				} else
				if(g_state == 1 || g_state == 2 || g_state == 6)
				{
					if(m_p_state)
						if(g_state == 6)
						{
							mob_d[0].m_a = 0;
							if(monster_action(0))
							{
								if(m_w_s == 0)
									m_w_s = 1;
								else
								if(m_w_s == 1)
									m_w_s = 0;
								flag = true;
							}
							if(c_3[0] == 2)
							{
								flag = true;
								mob_d[0].m_a = 1;
								if(defense_data() - mob_d[0].mo_a < 0)
									m_h = (m_h + defense_data()) - mob_d[0].mo_a;
								else
									m_h--;
								if(m_h <= 0)
								{
									state = 9;
									//if(sound_on)
										//playSound(CHAR_DIE, 1);
								}
								c_3[0] = 0;
							}
						} else
						{
							for(int i = 0; i < 8; i++)
							{
								mob_d[i].m_a = 0;
								boolean flag1 = monster_action(i);
								if(flag1)
									flag = true;
								if(c_3[i] == 2)
								{
									flag = true;
									mob_d[i].m_a = 1;
									if(defense_data() - mob_d[i].mo_a < 0)
										m_h = (m_h + defense_data() * 2) - mob_d[i].mo_a * 2;
									else
										m_h -= 2;
									if(m_h <= 0)
									{
										state = 9;
										//if(sound_on)
											//playSound(CHAR_DIE, 1);
									}
									c_3[i] = 0;
								}
							}

						}
				} else
				if(g_state == 3 && c_a != 3 && n_s != 4 || g_state == 4 && c_a != 3 && n_s != 6)
				{
					byte0++;
					mob_d[0].m_a = 0;
					if(byte0 >= 5)
					{
						byte0 = 0;
						byte1++;
						if(byte1 >= 4)
						{
							byte1 = 0;
							flag = true;
							boss_monster_action(mob_d[0], g_state);
						} else
						if(byte1 == 1 || byte1 == 2)
						{
							mob_d[0].m_a = 1;
							m_h = (m_h + defense_data()) - mob_d[0].mo_a;
							if(m_h <= 0)
							{
								state = 9;
								//if(sound_on)
									//playSound(CHAR_DIE, 1);
							}
							flag = true;
						}
					}
				} else
				if(g_state == 5 && c_a != 3)
				{
					byte0++;
					mob_d[0].m_a = 0;
					if(byte0 >= 5)
					{
						byte0 = 0;
						byte1++;
						if(byte1 >= 5)
						{
							byte1 = 0;
							flag = true;
							boss_monster_action(mob_d[0], g_state);
						} else
						if(byte1 == 1 || byte1 == 2 || byte1 == 3 || byte1 == 4)
						{
							mob_d[0].m_a = 1;
							m_h = (m_h + defense_data()) - mob_d[0].mo_a;
							if(m_h <= 0)
							{
								state = 9;
								//if(sound_on)
									//playSound(CHAR_DIE, 1);
							}
							flag = true;
						}
					}
				}
				if(item != 0)
				{
					in_1++;
					if(in_1 == 5)
					{
						g_img = null;
						in_1 = 0;
						item = 0;
					}
				}
				if(r_c != 0)
				{
					repeat_action(r_c, g_state);
					if(g_state == 1 || g_state == 2)
						in++;
					repaint(0, 0, getWidth(), getHeight());
					serviceRepaints();
					if(in == 20)
					{
						in = 0;
						byte abyte0[] = {
							2, 8, 6, 14, 8, 4, 12, 10, 3, 10, 
							6, 5, 11, 8, 8, 12
						};
						for(int j = 0; j < 8; j++)
							if(mob_die[j])
							{
								mob_d[j].mob_live(abyte0[j * 2], abyte0[j * 2 + 1], m_l);
								mob_die[j] = false;
							}

					}
				} else
				if(c_a == 1 || c_a == 2 || flag)
				{
					repaint(0, 0, getWidth(), getHeight());
					serviceRepaints();
				} else
				if(c_a == 3 && n_d == 2 && cho == 4)
				{
					if(ranking(1))
						cho = 1;
					else
						cho = 2;
					repaint(0, 0, getWidth(), getHeight());
					serviceRepaints();
				}
			} else
			if(state == 8)
				if(cho == 4)
				{
					if(ranking(2))
						cho = 2;
					else
						cho = 3;
					repaint(0, 0, getWidth(), getHeight());
					serviceRepaints();
				} else
				if(cho_1 < 57)
				{
					repaint(0, 0, getWidth(), getHeight());
					serviceRepaints();
					cho_1++;
				}
			try
			{
				Thread.sleep(timer);
			}
			catch(Exception exception) { }
			if(c_a != 1)
				key = true;
			//System.gc();
		}
		thread = null;
	}

	public void paint(Graphics g)
	{
		g.setFont(Font.getFont(0, 0, 8));
		switch(state)
		{
		default:
			break;
		case 0: // '\0'
			if(g_state == 1)
			{
				drawLogo(g);
			}
			if(g_state == 0)
			{
				l_img=null;
				drawIntro(g);
			}
			if(g_state == 2)
			{
				adviceView(g);
			}
			if(g_state == 11)
			{
				adviceMove(g);
			}
			if(g_state == 12)
			{
				adviceView(g);
			}
			if(g_state == 3)
			{
				adviceMove(g);
			}
			if(g_state == 4)
				drawQNA(g);
			break;

		case 1: // '\001'
			g_img=null;
            if(g_state == 0)
                drawNewStart(g);
            if(g_state == 2)
                drawLoading(g);
			break;
		case 2: // '\002'
			drawGame(g);
			break;

		case 3: // '\003'
			minimap_view(g);
			break;

		case 4: // '\004'
			inventory_view(g);
			break;

		case 5: // '\005'
			change_view(g);
			break;

		case 6: // '\006'
			item_view(g, (byte)0);
			break;

		case 7: // '\007'
			menu_pause(g);
			break;

		case 8: // '\b'
			ending(g);
			break;

		case 9: // '\t'
			char_die(g);
			break;

		case 10: // '\n'			
			drawBack(g);
			g.setColor(0);
			g.drawRect(sX-1, sY + 14, 124, 120);
			g.setColor(0x2870F8);
			g.fillRect(sX+1, sY + 16, 121, 117);
			g.setColor(0x1050B0);
			g.fillRect(sX+10, sY + 41, 103, 72);
			drawBlackshadow(g,0x80B0F8,0x1050B0,122,118,sX,sY+15);
			drawBlackshadow(g,0x184060,0x80B0F8,103,72,sX+10,sY+40);
			g.setColor(0);
			drawString(g,"�洢", sX + 59, sY + 10 + 10, 0x10 | 0x1);
			g.setColor(0xFFFFFF);
			drawString(g,"�洢", sX + 60, sY + 11 + 10, 0x10 | 0x1);
			if(c_state == 0)
			{
				drawString(g,"�������" , sX + 13, sY + 50, 0x10 | 0x4);
				drawString(g,"����ʧȥ��������", sX + 13, sY + 70, 0x10 | 0x4);
				drawString(g,"ȷ��", 2, getHeight() - 2, 0x20 | 0x4);
				drawString(g,"����", getWidth() - 2, getHeight() - 2, 0x20 | 0x8);
			}
			if(c_state == 1)
			{
				drawString(g,"�������.", sX + 13, sY + 70, 0x10 | 0x4);
				g.setColor(0xFFFFFF);
				drawString(g,"ȷ��", 2, getHeight() - 2, 0x20 | 0x4);
			}
			break;
		case 11: // '\013'
			if(cho_1 == 0)
			{
				store_view(g);
			}
			if(cho_1 == 1)
			{
				item_view(g, (byte)1);
			}
			if(cho_1 == 2)
			{
				item_view(g, (byte)2);
			}
			if(cho_1 == 3)
				item_view(g, (byte)3);
			break;

		case 12: // '\f'
			drawMenuBack(g);
			g.setColor(0xffffff);
			drawString(g,"������", sX + 3, sY + 92, 0x10 | 0x4);
			drawString(g,"����ķϺܾ���", sX + 3, sY + 105, 0x10 | 0x4);
			break;
		}
		//System.gc();
	}

	protected void keyPressed(int i)
	{
		switch(state)
		{
		default:
			break;

		case 0: // '\0'
			if(g_state == 0)
			{
				if(i == -5 || i == -7 || i == 53 || i== -6)
				{
					if(cho == 0)
					{
						state = 1;
						g_state = 0;
						g_img = null;
					} else
					if(cho == 1)
					{
						d_s.openRecStore();
						boolean flag1 = d_s.readRecordsUpdate();
						if(flag1)
						{
							state = 1;
							g_state = 2;
							cho = 1;
							g_img = null;
						} else
						{
							state = 1;
							g_state = 0;
							g_img = null;
						}
						d_s.closeRecStore();
					} else
					if(cho == 2)
					{
						g_state = 2;
						cho = 0;
					} else
					if(cho == 3)
					{
						g_state = 3;
						cho = 0;
					} else
					if(cho == 4)
					{
						try{
							if(sound_on){
								sound_on = false;
								player.stop();
							}else{
								sound_on = true;
								//playMidi("title");
								player.setMediaTime(0);
								player.start();
							}
						}catch(Exception exception){}

					} else
					if(cho == 5)
					{
						g_state = 4;
						cho = 0;
					} else
					if(cho == 6)
						midlet.notifyDestroyed();
				} else
				if(i == -1)
				{
					cho--;
					if(cho == -1)
						cho = 6;
				} else
				if(i == -2)
				{
					cho++;
					if(cho == 7)
						cho = 0;
				}
			} else
			if(g_state == 2)
			{
				if(i == -1)
				{
					cho--;
					if(cho == -1)
						cho = 3;
				} else
				if(i == -2)
				{
					cho++;
					if(cho == 4)
						cho = 0;
				} else
				if(i == -5 || i == -7 || i == 53)
				{
					g_state = 0;
					cho = 2;
				}
			} else
			if(g_state == 3)
			{
				if(i == -5 || i == -7 || i == 53)
				{
					if(in == 0)
					{
						g_state = 0;
						cho = 3;
					} else
					{
						state = 7;
						cho = 0;
					}
				}
			} else
			if(g_state == 4 && (i == -5 || i == -7 || i == 53))
			{
				g_state = 0;
				cho = 5;
			}
			repaint(0, 0, getWidth(), getHeight());
			serviceRepaints();
			break;

		case 1: // '\001'
			if(g_state == 0)
			{
				if(i == -5 || i == -7 || i == 53 || i == -6)
				{
					g_img = null;
					g_state = 2;
					cho = 0;
					repaint(0, 0, getWidth(), getHeight());
					serviceRepaints();
				}
				break;
			}
			if(g_state != 1)
				break;
			if(i == -1)
			{
				cho--;
				if(cho == -1)
					cho = 1;
			} else
			if(i == -2)
			{
				cho++;
				if(cho == 2)
					cho = 0;
			} else
			if((i == -5 || i == -6) && cho == 0)
			{
				g_img = null;
				g_state = 2;
				cho = 1;
			} else
			if((i == -5 || i == -6) && cho == 1)
			{
				g_state = 0;
				cho = 0;
			} else
			if(i == -7)
			{
				state = 0;
				g_state = 0;
				cho = 0;
			}
			repaint(0, 0, getWidth(), getHeight());
			serviceRepaints();
			break;

		case 2: // '\002'
			if(c_a == 3)
			{
				if(n_d == 1 && m_m >= 5000 && (i == 49 || i == 50 || i == 51))
				{
					if(i == 49)
					{
						s_p = 1;
						save_maul = 0;
						now_map = K_M;
					} else
					if(i == 50)
					{
						s_p = 2;
						save_maul = 1;
						now_map = E_M;
					} else
					if(i == 51)
					{
						s_p = 3;
						save_maul = 2;
						now_map = K_M;
					}
					m_m -= 5000;
					stX = 1;
					stY = 10;
					n_c = 0;
					c_a = 0;
				} else
				if(i == -5 || i == -7 || i == 53)
				{
					if(n_d == 2 && m_m > 10000)
					{
						if(cho == 0)
							cho = 4;
						else
						if(cho == 2)
						{
							cho = 0;
							c_a = 0;
						}
					} else
					if(n_d == 3)
					{
						if(save_maul == 0)
						{
							if(n_s == 0)
							{
								n_c++;
								if(n_c == 7)
								{
									n_s = 1;
									n_d = 0;
									n_c = 0;
									c_a = 0;
								}
							} else
							{
								c_a = 0;
							}
						} else
						if(save_maul == 1)
							c_a = 0;
						else
						if(save_maul == 2)
							c_a = 0;
					} else
					if(n_d == 4)
					{
						if(save_maul == 0)
						{
							if(n_s == 1)
							{
								n_c++;
								if(n_c == 6)
								{
									n_s = 2;
									n_d = 0;
									n_c = 0;
									c_a = 0;
								}
							} else
							{
								c_a = 0;
							}
						} else
						if(save_maul == 1)
						{
							if(n_s == 2)
							{
								n_c++;
								if(n_c == 7)
								{
									n_s = 3;
									n_d = 0;
									n_c = 0;
									c_a = 0;
								}
							} else
							if(n_s == 4)
							{
								n_c++;
								if(n_c == 4)
								{
									n_s = 5;
									n_d = 0;
									n_c = 0;
									c_a = 0;
								}
							} else
							{
								c_a = 0;
							}
						} else
						if(save_maul == 2)
							c_a = 0;
					} else
					if(n_d == 5)
					{
						n_c++;
						if(n_c == 3)
							c_a = 0;
						else
						if(n_c == 13)
						{
							n_s = 4;
							n_d = 0;
							n_c = 0;
							c_a = 0;
						}
					} else
					if(n_d == 6)
					{
						n_c++;
						if(n_c == 4)
						{
							n_c = 0;
							c_a = 0;
						}
					} else
					if(n_d == 7)
					{
						n_c++;
						if(n_c == 5)
							c_a = 0;
						else
						if(n_c == 7)
						{
							n_c = 0;
							c_a = 0;
							g_state = 6;
							mob_d[0].mob_live((byte)2, (byte)8, (byte)0);
							mob_die[0] = false;
							state = 8;
						}
					} else
					if(n_d == 2 && cho == 1)
					{
						cho = 3;
					} else
					{
						c_a = 0;
						cho = 0;
					}
				} else
				if(n_d == 2 && i == -6)
				{
					c_a = 0;
					cho = 0;
				}
				repaint(0, 0, getWidth(), getHeight());
				serviceRepaints();
				break;
			}
			if(key && (i == -1 || i == -2 || i == -3 || i == -4))
			{
				r_c = i;
				break;
			}
			if((i == -5 || i == -7 || i == 53) && key)
			{
				key = false;
				c_a = 1;
				r_c = 0;
				if(m_s[3] == 1)
					m_s[4] = 1;
				else
				if(m_s[3] == 2)
				{
					if(m_s[4] == 1)
						m_s[4] = 2;
					else
					if(m_s[4] == 2)
						m_s[4] = 1;
				} else
				if(m_s[3] == 3)
					if(m_s[4] == 1)
						m_s[4] = 2;
					else
					if(m_s[4] == 2)
						m_s[4] = 3;
					else
					if(m_s[4] == 3)
						m_s[4] = 1;
				if(g_state == 0)
				{
					if(npc_check((byte)0) != 5 && npc_check((byte)0) != 99)
					{
						c_a = 3;
						n_d = npc_check((byte)0);
						repaint(0, 0, getWidth(), getHeight());
						serviceRepaints();
					}
				} else
				{
					mo_a();
				}
				break;
			}
			if(i == 49)
			{
				if(m_i[0] == 0)
					break;
				m_h += 30;
				if(m_h > 7 + m_l * 14)
					m_h = 7 + m_l * 14;
				m_i[0]--;
				repaint(0, 0, getWidth(), getHeight());
				serviceRepaints();
				break;
			}
			if(i == 50)
			{
				if(m_i[1] == 0)
					break;
				m_h += 100;
				if(m_h > 7 + m_l * 14)
					m_h = 7 + m_l * 14;
				m_i[1]--;
				repaint(0, 0, getWidth(), getHeight());
				serviceRepaints();
				break;
			}
			if(i == 51)
			{
				if(m_i[2] == 0)
					break;
				m_i[2]--;
				if(save_maul == 0)
				{
					s_p = 1;
					now_map = K_M;
				} else
				if(save_maul == 1)
				{
					s_p = 2;
					now_map = E_M;
				} else
				if(save_maul == 2)
				{
					s_p = 3;
					now_map = K_M;
				}
				stX = 1;
				stY = 10;
				g_state = 0;
				if(n_s == 6)
					n_s = 5;
				n_c = 0;
				r_c = 0;
				repaint(0, 0, getWidth(), getHeight());
				serviceRepaints();
				//if(sound_on)
					playMidi("town");
				break;
			}
			if(i == 55 && key && g_state != 3 && g_state != 4 && g_state != 5 && g_state != 6)
			{
				r_c = 0;
				state = 10;
				repaint(0, 0, getWidth(), getHeight());
				serviceRepaints();
				break;
			}
			if(i == 57 && key)
			{
				r_c = 0;
				state = 4;
				repaint(0, 0, getWidth(), getHeight());
				serviceRepaints();
				break;
			}
			if(i == 48 && key)
			{
				m_txt = new Image[6];
				try
				{
					m_txt[0] = Image.createImage("/m_p.png");
					m_txt[1] = Image.createImage("/f_p.png");
					m_txt[2] = Image.createImage("/g_p.png");
					m_txt[3] = Image.createImage("/st.png");
					m_txt[4] = Image.createImage("/so.png");
					m_txt[5] = Image.createImage("/ho.png");
				}
				catch(Exception exception) { }
				r_c = 0;
				state = 3;
				break;
			}
			if(i != -6)
				break;
			in = i;
			in_1 = g_state;
			r_c = 0;
			state = 7;
			repaint(0, 0, getWidth(), getHeight());
			serviceRepaints();
			break;

		case 3: // '\003'
			if(i == 48 || i == -5 || i == -7 || i == -6)
			{
				try
				{
					m_txt = null;
					state = 2;
					repaint(0, 0, getWidth(), getHeight());
					serviceRepaints();
				}
				catch(Exception exception) { }
			}
			break;

		case 4: // '\004'
			if(c_state == 0)
			{
				if(i == -1)
				{
					cho--;
					if(cho == -1)
						cho = 3;
				} else
				if(i == -2)
				{
					cho++;
					if(cho == 4)
						cho = 0;
				} else
				if(i == -5 || i == -6)
				{
					if(cho == 0)
					{
						c_state = 1;
						cho = 0;
					} else
					if(cho == 1)
					{
						c_state = 2;
						cho = 0;
					} else
					if(cho == 4)
					{
						state = 2;
						cho = 0;
					}

				} else
				if(i == 57 || i == -7)
				{
					state = 2;
					cho = 0;
				}
			} else
			if(c_state == 1)
			{
				if(i == -3)
				{
					cho--;
					if(cho < 0)
						cho = 0;
				} else
				if(i == -4)
				{
					cho++;
					if(cho > 12)
						cho = 12;
				} else
				if(i == -1)
				{
					cho -= 3;
					if(cho < 0)
						cho = 0;
				} else
				if(i == -2)
				{
					cho += 3;
					if(cho > 12)
						cho = 12;
				} else
				if(i == -5 || i == -6)
					state = 6;
				else
				if(i == -7)
				{
					c_state = 0;
					cho = 0;
				}
			} else
			if(c_state == 2)
				if(i == -1)
				{
					cho--;
					if(cho == -1)
						cho = 2;
				} else
				if(i == -2)
				{
					cho++;
					if(cho == 3)
						cho = 0;
				} else
				if(i == -5 || i == -6)
					state = 5;
				else
				if(i == -7)
				{
					c_state = 0;
					cho = 1;
				}
			
			repaint(0, 0, getWidth(), getHeight());
			serviceRepaints();
			break;

		case 5: // '\005'
			if(i == -3)
			{
				cho_1--;
				if(cho_1 == -1)
					if(cho == 0)
						cho_1 = 3;
					else
						cho_1 = 2;
			} else
			if(i == -4)
			{
				cho_1++;
				if(cho == 0 && cho_1 == 4)
					cho_1 = 0;
				else
				if(cho != 0 && cho_1 == 3)
					cho_1 = 0;
			} else
			if(i == -5 || i == -6)
			{
				if(cho == 0)
				{
					int ai[] = {
						1, 10, 18, 26
					};
					if(m_i[9 + cho_1] != 0 && ai[cho_1] <= m_l)
					{
						m_i[9 + m_s[0]]++;
						m_i[9 + cho_1]--;
						m_s[0] = cho_1;
						state = 4;
						cho_1 = 0;
					}
				} else
				if(cho == 1)
				{
					int ai1[] = {
						1, 11, 22
					};
					if(m_i[3 + cho_1] != 0 && ai1[cho_1] <= m_l)
					{
						m_i[3 + m_s[2]]++;
						m_i[3 + cho_1]--;
						m_s[2] = cho_1;
						state = 4;
						cho_1 = 0;
					}
				} else
				if(cho == 2)
				{
					int ai2[] = {
						5, 13, 20
					};
					if(m_i[6 + cho_1] != 0 && ai2[cho_1] <= m_l)
					{
						if(m_s[1] != 0)
							m_i[(6 + m_s[1]) - 1]++;
						m_i[6 + cho_1]--;
						m_s[1] = (byte)(cho_1 + 1);
						state = 4;
						cho_1 = 0;
					}
				}
			} else
			if(i == -7)
			{
				state = 4;
				cho_1 = 0;
			}
			repaint(0, 0, getWidth(), getHeight());
			serviceRepaints();
			break;

		case 6: // '\006'
			if(i == -5 || i == -7 || i == 53 || i == -6)
			{
				state = 4;
				repaint(0, 0, getWidth(), getHeight());
				serviceRepaints();
			}
			break;

		case 7: // '\007'
			if(i == -7 || cho == 0 && (i == -5 || i == -6))
			{
				state = 2;
				g_state = in_1;
				in = 0;
				in_1 = 0;
				cho = 0;
				//if(sound_on)
					playMidi(midi);
			} else
			if(cho == 1 && (i == -5 || i == -6))
			{
				cho = 0;
				state = 0;
				g_state = 3;
			} else
			if(cho == 2 && (i == -5 || i == -6))
			{
				try{
					if(sound_on){
						sound_on = false;
						player.stop();
					}else{
						sound_on = true;
						player.setMediaTime(0L);
						player.start();
					}
				}catch(Exception exception){}
			} else
			if(cho == 3 && (i == -5 || i == -6)){
				reset_data();
				//if(sound_on)
					playMidi("title");
			}else
			if(cho == 4 && (i == -5 || i == -6)){
				try{
					player.close();
				}catch(Exception exception){}
				midlet.notifyDestroyed();
			}
			else
			if(i == -1)
			{
				cho--;
				if(cho < 0)
					cho = 4;
			} else
			if(i == -2)
			{
				cho++;
				if(cho > 4)
					cho = 0;
			}
			repaint(0, 0, getWidth(), getHeight());
			serviceRepaints();
			break;

		case 8: // '\b'
			if(cho == 0)
			{
				if((i == -5 || i == -6) && cho_1 > 37)
					cho = 1;
			} else
			if(cho == 1)
			{
				if(i == -5 || i == -7 || i == -6){
					cho = 2;
					reset_data();
				}
			}
			repaint(0, 0, getWidth(), getHeight());
			serviceRepaints();
			break;

		case 9: // '\t'
			if(i == -5 || i == -7 || i == 53 || i == -6)
			{
				reset_data();
				playMidi("title");
				repaint(0, 0, getWidth(), getHeight());
				serviceRepaints();
			}
			break;

		case 10: // '\n'
			if(i == -6 || i == -5)
			{
				if(c_state == 0)
				{
					c_state = 1;
					saveData(0);
				} else
				if(c_state == 1)
				{
					state = 2;
					c_state = 0;
				}
				repaint(0, 0, getWidth(), getHeight());
				serviceRepaints();
				break;
			}
			if(i == -7 || i == 55)
			{
				state = 2;
				c_state = 0;
				repaint(0, 0, getWidth(), getHeight());
				serviceRepaints();
			}
			break;

		case 11: // '\013'
			if(c_state == 0)
			{
				if(i == -1)
				{
					cho--;
					if(cho < 0)
						cho = 2;
				} else
				if(i == -2)
				{
					cho++;
					if(cho > 2)
						cho = 0;
				} else
				if(i == -5 || i == -6)
				{
					if(cho == 0)
					{
						c_state = 1;
						if(n_npc[stX + 3 + 1][stY - 1] == 7)
							cho = 3;
						else
							cho = 0;
					} else
					if(cho == 1)
					{
						c_state = 2;
						if(n_npc[stX + 3 + 1][stY - 1] == 7)
							cho = 3;
						else
							cho = 0;
					} else
					if(cho == 2)
					{
						c_state = 0;
						cho = 0;
						state = 2;
						stY--;
						spX = 1;
						spY = 1;
					}
				} else
				if(i == -7)
				{
					c_state = 0;
					cho = 0;
					state = 2;
					stY--;
					spX = 1;
					spY = 1;
				}
			} else
			if(c_state == 1)
			{
				if(n_npc[stX + 3 + 1][stY - 1] == 5)
				{
					if(i == -5 || i == -6)
					{
						if(m_m - 2500 >= 0)
						{
							m_m -= 2500;
							m_h = 7 + m_l * 14;
							c_state = 3;
						}
					} else
					if(i == -7)
						c_state = 0;
				} else
				if(n_npc[stX + 3 + 1][stY - 1] == 6 || n_npc[stX + 3 + 1][stY - 1] == 7)
					if(cho_1 == 0)
					{
						if(n_npc[stX + 3 + 1][stY - 1] == 6)
						{
							if(i == -3)
							{
								cho--;
								if(cho < 0)
									cho = 2;
							} else
							if(i == -4)
							{
								cho++;
								if(cho > 2)
									cho = 0;
							}
						} else
						if(i == -3)
						{
							cho--;
							if(cho < 3)
								cho = 3;
						} else
						if(i == -4)
						{
							cho++;
							if(cho > 12)
								cho = 12;
						} else
						if(i == -1)
						{
							cho -= 3;
							if(cho < 3)
								cho = 3;
						} else
						if(i == -2)
						{
							cho += 3;
							if(cho > 12)
								cho = 12;
						}
						if(i == -5 || i == -6)
							cho_1 = 1;
						else
						if(i == -7)
						{
							c_state = 0;
							cho = 0;
						}
					} else
					if(cho_1 == 1)
					{
						if(i == -5 || i == -6)
						{
							if(deal_item(cho, c_state))
								cho_1 = 0;
							else
								cho_1 = 3;
						} else
						if(i == -7)
							cho_1 = 0;
					} else
					if(cho_1 == 3 && (i == -5 || i == -6 || i== -7))
						cho_1 = 0;
			} else
			if(c_state == 2)
			{
				if(n_npc[stX + 3 + 1][stY - 1] == 5)
				{
					if(i == -5 || i == -6)
					{
						saveData(0);
						c_state = 3;
					} else
					if(i == -7)
						c_state = 0;
				} else
				if(n_npc[stX + 3 + 1][stY - 1] == 6 || n_npc[stX + 3 + 1][stY - 1] == 7)
					if(cho_1 == 0)
					{
						if(n_npc[stX + 3 + 1][stY - 1] == 6)
						{
							if(i == -3)
							{
								cho--;
								if(cho < 0)
									cho = 2;
							} else
							if(i == -4)
							{
								cho++;
								if(cho > 2)
									cho = 0;
							}
						} else
						if(i == -3)
						{
							cho--;
							if(cho < 3)
								cho = 3;
						} else
						if(i == -4)
						{
							cho++;
							if(cho > 12)
								cho = 12;
						} else
						if(i == -1)
						{
							cho -= 3;
							if(cho < 3)
								cho = 3;
						} else
						if(i == -2)
						{
							cho += 3;
							if(cho > 12)
								cho = 12;
						}
						if((i == -5 || i == -6) && m_i[cho] != 0)
							cho_1 = 2;
						else
						if(i == -7)
						{
							c_state = 0;
							cho = 0;
						}
					} else
					if(cho_1 == 2)
						if(i == -5 || i == -6)
						{
							if(deal_item(cho, c_state))
								cho_1 = 0;
						} else
						if(i == -7)
							cho_1 = 0;
			} else
			if(c_state == 3 && (i == -5 || i == -6 || i == -7))
				c_state = 0;
			repaint(0, 0, getWidth(), getHeight());
			serviceRepaints();
			break;

		case 12: // '\f'
			if(i == -5 || i == -6 || i == -7)
			{
				state = 2;
				repaint(0, 0, getWidth(), getHeight());
				serviceRepaints();
			}
			break;
		}
		//System.gc();
	}

	private void reset_data()
	{
		c_img = null;
		//s_a = null;
		n_img = null;
		gr_img = null;
		mo_img = null;
		bmo_img = null;
		st_img = null;
		g_img = null;
		//System.gc();
		try
		{
			g_img = Image.createImage("/intro.png");
		}
		catch(Exception exception) { }
		save_maul = 0;
		n_d = 0;
		n_c = 0;
		n_s = 0;
		state = 0;
		g_state = 0;
		active = true;
		g_p_state = false;
		c_state = 0;
		cho = 0;
		cho_1 = 0;
		in = 0;
		in_1 = 0;
		key = true;
		stX = -1;
		stY = 10;
		spX = 1;
		spY = 1;
		r_c = 0;
		w_p = 1;
		w_s = 0;
		m_w_s = 0;
		c_a = 0;
		m_l = 1;
		m_e = 0;
		m_h = 21;
		m_m = 10000;
		m_s[0] = 0;
		m_s[1] = 0;
		m_s[2] = 0;
		m_s[3] = 0;
		m_s[4] = 0;
		m_i[0] = 1;
		m_i[1] = 0;
		m_i[2] = 1;
		m_i[3] = 0;
		m_i[4] = 0;
		m_i[5] = 0;
		m_i[6] = 0;
		m_i[7] = 0;
		m_i[8] = 0;
		m_i[9] = 0;
		m_i[10] = 0;
		m_i[11] = 0;
		m_i[12] = 0;
		s_p = 0;
	}

	private boolean deal_item(byte byte0, byte byte1)
	{
		boolean flag = false;
		int i = 0;
		if(byte0 == 0)
		{
			if(byte1 == 1)
				i = 1500;
			else
			if(byte1 == 2)
				i = 200;
		} else
		if(byte0 == 1)
		{
			if(byte1 == 1)
				i = 4000;
			else
			if(byte1 == 2)
				i = 2000;
		} else
		if(byte0 == 2)
		{
			if(byte1 == 1)
				i = 3000;
			else
			if(byte1 == 2)
				i = 1000;
		} else
		if(byte0 == 3)
		{
			if(byte1 == 1)
				i = 4000;
			else
			if(byte1 == 2)
				i = 1500;
		} else
		if(byte0 == 4)
		{
			if(byte1 == 1)
				i = 20000;
			else
			if(byte1 == 2)
				i = 8000;
		} else
		if(byte0 == 5)
		{
			if(byte1 == 1)
				i = 0x13880;
			else
			if(byte1 == 2)
				i = 30000;
		} else
		if(byte0 == 6)
		{
			if(byte1 == 1)
				i = 10000;
			else
			if(byte1 == 2)
				i = 4000;
		} else
		if(byte0 == 7)
		{
			if(byte1 == 1)
				i = 30000;
			else
			if(byte1 == 2)
				i = 13000;
		} else
		if(byte0 == 8)
		{
			if(byte1 == 1)
				i = 60000;
			else
			if(byte1 == 2)
				i = 25000;
		} else
		if(byte0 == 9)
		{
			if(byte1 == 1)
				i = 3000;
			else
			if(byte1 == 2)
				i = 1000;
		} else
		if(byte0 == 10)
		{
			if(byte1 == 1)
				i = 8000;
			else
			if(byte1 == 2)
				i = 4000;
		} else
		if(byte0 == 11)
		{
			if(byte1 == 1)
				i = 40000;
			else
			if(byte1 == 2)
				i = 15000;
		} else
		if(byte0 == 12)
			if(byte1 == 1)
				i = 0x186a0;
			else
			if(byte1 == 2)
				i = 40000;
		if(byte1 == 1 && m_m - i >= 0)
		{
			m_m -= i;
			m_i[byte0]++;
			flag = true;
		} else
		if(byte1 == 2)
		{
			m_m += i;
			m_i[byte0]--;
			flag = true;
		}
		return flag;
	}

	private byte npc_check(byte byte0)
	{
		byte byte1 = 99;
		if(byte0 == 0)
		{
			if(w_p == 3)
			{
				byte byte2 = n_npc[stX + 3 + 1][stY - 1];
				if((byte2 == 0 || byte2 == 1 || byte2 == 2 || byte2 == 3 || byte2 == 4) && spX == 0 && spY == 0)
					byte1 = byte2;
			} else
			if(w_p == 0)
			{
				byte byte3 = n_npc[stX + 3 + 1][stY - 1 - 1];
				if((byte3 == 0 || byte3 == 1 || byte3 == 2 || byte3 == 3 || byte3 == 4) && spX == 0 && spY == 0)
					byte1 = byte3;
			} else
			if(w_p == 2)
			{
				byte byte4 = n_npc[stX + 3 + 1][stY - 1];
				if((byte4 == 0 || byte4 == 1 || byte4 == 2 || byte4 == 3 || byte4 == 4) && spX == 1 && spY == 1)
					byte1 = byte4;
			} else
			if(w_p == 1)
			{
				byte byte5 = n_npc[stX + 3 + 1 + 1][stY - 1];
				if((byte5 == 0 || byte5 == 1 || byte5 == 2 || byte5 == 3 || byte5 == 4) && spX == 1 && spY == 1)
					byte1 = byte5;
			}
		} else
		if(byte0 == 1 && (n_npc[stX + 3 + 1][stY - 1] == 5 || n_npc[stX + 3 + 1][stY - 1] == 6 || n_npc[stX + 3 + 1][stY - 1] == 7))
			byte1 = 5;
		return byte1;
	}

	private void repeat_action(int i, byte byte0)
	{
		switch(i)
		{
		case -1: 
			w_p = 3;
			if(byte0 == 0)
			{
				if(spY + 1 == 2)
				{
					byte byte1 = now_map[stX + 3 + 1][(stY + 1) - 1];
					if(byte1 != 3 && byte1 != 4)
						if(byte1 == 0)
						{
							move_field(1);
						} else
						{
							spY = 0;
							stY++;
						}
				} else
				if(npc_check((byte)0) == 99)
					spY++;
			} else
			if(byte0 == 1)
			{
				if(spY + 1 == 2)
				{
					byte byte2 = now_map[stX + 3 + 1][(stY + 1) - 1];
					if(byte2 != 99 && byte2 != 4 && byte2 != 3 && !monster_search((byte)(stX + 3), (byte)(stY + 1), spX, (byte)0))
						if(byte2 == 0)
						{
							move_field(1);
						} else
						{
							spY = 0;
							stY++;
						}
				} else
				if(!monster_search((byte)(stX + 3), stY, spX, (byte)1))
					spY++;
			} else
			if(spY + 1 == 2)
			{
				byte byte3 = now_map[stX + 3 + 1][(stY + 1) - 1];
				if(byte3 != 99 && byte3 != 8 && !monster_search((byte)(stX + 3), (byte)(stY + 1), spX, (byte)0))
				{
					spY = 0;
					stY++;
				}
			} else
			if(!monster_search((byte)(stX + 3), stY, spX, (byte)1))
				if((byte0 == 3 && n_s == 4 || byte0 == 4 && n_s == 6) && stX + 3 + 1 == 2 && stY - 1 == 8 && spX == 0 && spY == 0)
					move_field(11);
				else
				if(stX + 3 + 1 == 13 && stY - 1 == 3 && spX == 0 && spY == 0 || stX + 3 + 1 == 3 && stY - 1 == 13 && spX == 0 && spY == 0)
					move_field(stX + 3 + 1);
				else
					spY++;
			break;

		case -2: 
			w_p = 0;
			if(byte0 == 0)
			{
				if(spY - 1 == -1)
				{
					byte byte4 = now_map[stX + 3 + 1][stY - 1 - 1];
					if(byte4 != 3 && byte4 != 4)
						if(byte4 == 0)
							move_field(2);
						else
						if(npc_check((byte)0) == 99)
						{
							spY = 1;
							stY--;
						}
				} else
				{
					spY--;
				}
			} else
			if(byte0 == 1)
			{
				if(spY - 1 == -1)
				{
					byte byte5 = now_map[stX + 3 + 1][stY - 1 - 1];
					if(byte5 != 99 && byte5 != 4 && byte5 != 3 && !monster_search((byte)(stX + 3), (byte)(stY - 1), spX, (byte)1))
						if(byte5 == 0)
						{
							move_field(2);
						} else
						{
							spY = 1;
							stY--;
						}
				} else
				if(!monster_search((byte)(stX + 3), stY, spX, (byte)0))
					spY--;
			} else
			if(spY - 1 == -1)
			{
				byte byte6 = now_map[stX + 3 + 1][stY - 1 - 1];
				if(byte6 != 99 && byte6 != 8 && !monster_search((byte)(stX + 3), (byte)(stY - 1), spX, (byte)1))
					if(stX + 3 + 1 == 13 && stY - 1 - 1 == 3 && spX == 0 && spY == 0 || stX + 3 + 1 == 3 && stY - 1 - 1 == 13 && spX == 0 && spY == 0)
					{
						move_field(stX + 3 + 1);
					} else
					{
						spY = 1;
						stY--;
					}
			} else
			if(!monster_search((byte)(stX + 3), stY, spX, (byte)0))
				spY--;
			break;

		case -3: 
			w_p = 2;
			if(byte0 == 0)
			{
				if(spX - 1 == -1)
				{
					byte byte7 = now_map[((stX + 3) - 1) + 1][stY - 1];
					if(byte7 != 3 && byte7 != 4)
						if(byte7 == 0)
						{
							move_field(3);
						} else
						{
							spX = 1;
							stX--;
						}
				} else
				if(npc_check((byte)0) == 99)
					spX--;
			} else
			if(byte0 == 1)
			{
				if(spX - 1 == -1)
				{
					byte byte8 = now_map[((stX + 3) - 1) + 1][stY - 1];
					if(byte8 != 99 && byte8 != 4 && byte8 != 3 && !monster_search((byte)((stX + 3) - 1), stY, (byte)1, spY))
						if(byte8 == 0)
						{
							move_field(3);
						} else
						{
							spX = 1;
							stX--;
						}
				} else
				if(!monster_search((byte)(stX + 3), stY, (byte)0, spY))
					spX--;
			} else
			if(spX - 1 == -1)
			{
				byte byte9 = now_map[((stX + 3) - 1) + 1][stY - 1];
				if(byte9 != 99 && byte9 != 8 && !monster_search((byte)((stX + 3) - 1), stY, (byte)1, spY))
				{
					spX = 1;
					stX--;
				}
			} else
			if(!monster_search((byte)(stX + 3), stY, (byte)0, spY))
				if((byte0 == 3 && n_s == 4 || byte0 == 4 && n_s == 6) && stX + 3 + 1 == 2 && stY - 1 == 8 && spX == 1 && spY == 1)
					move_field(11);
				else
				if(stX + 3 + 1 == 13 && stY - 1 == 3 && spX == 1 && spY == 1 || stX + 3 + 1 == 3 && stY - 1 == 13 && spX == 1 && spY == 1)
					move_field(stX + 3 + 1);
				else
					spX--;
			break;

		case -4: 
			w_p = 1;
			if(byte0 == 0)
			{
				if(spX + 1 == 2)
				{
					byte byte10 = now_map[stX + 3 + 1 + 1][stY - 1];
					if(byte10 != 3 && byte10 != 4)
						if(byte10 == 0)
							move_field(4);
						else
						if(npc_check((byte)0) == 99)
						{
							spX = 0;
							stX++;
						}
				} else
				{
					spX++;
				}
			} else
			if(byte0 == 1)
			{
				if(spX + 1 == 2)
				{
					byte byte11 = now_map[stX + 3 + 1 + 1][stY - 1];
					if(byte11 != 99 && byte11 != 4 && byte11 != 3 && !monster_search((byte)(stX + 3 + 1), stY, (byte)0, spY))
						if(byte11 == 0)
						{
							move_field(4);
						} else
						{
							spX = 0;
							stX++;
						}
				} else
				if(!monster_search((byte)(stX + 3), stY, (byte)1, spY))
					spX++;
			} else
			if(spX + 1 == 2)
			{
				byte byte12 = now_map[stX + 3 + 1 + 1][stY - 1];
				if(byte12 != 99 && byte12 != 8 && !monster_search((byte)(stX + 3 + 1), stY, (byte)0, spY))
					if(stX + 3 + 1 + 1 == 13 && stY - 1 == 3 && spX == 1 && spY == 1 || stX + 3 + 1 + 1 == 3 && stY - 1 == 13 && spX == 1 && spY == 1)
					{
						move_field(stX + 3 + 1 + 1);
					} else
					{
						spX = 0;
						stX++;
					}
			} else
			if(!monster_search((byte)(stX + 3), stY, (byte)1, spY))
				spX++;
			break;
		}
		if(w_s == 0)
			w_s = 1;
		else
		if(w_s == 1)
			w_s = 0;
		if(byte0 == 0 && npc_check((byte)1) == 5)
		{
			r_c = 0;
			state = 11;
		}
	}

	private byte[][] map_change(byte abyte0[][], int i)
	{
		byte abyte1[][] = new byte[16][16];
		if(i == 0)
		{
			for(int j = 0; j < 16; j++)
			{
				for(int i1 = 0; i1 < 16; i1++)
					abyte1[15 - i1][j] = abyte0[j][i1];

			}

		} else
		if(i == 1)
		{
			for(int k = 0; k < 16; k++)
			{
				for(int j1 = 0; j1 < 16; j1++)
					abyte1[15 - k][15 - j1] = abyte0[k][j1];

			}

		} else
		if(i == 2)
		{
			for(int l = 0; l < 16; l++)
			{
				for(int k1 = 0; k1 < 16; k1++)
					abyte1[k1][15 - l] = abyte0[l][k1];

			}

		}
		return abyte1;
	}

	private void move_field(int i)
	{   
		if(g_state == 0)
		{
			if(s_p == 1)
			{
				if(i == 1)
				{
					now_map = map_change(m_2, 2);
					stX = 4;
					stY = 2;
					s_p = 2;
				} else
				if(i == 2)
				{
					now_map = m_1;
					stX = 4;
					stY = 15;
					s_p = 1;
				}
			} else
			if(s_p == 2)
			{
				if(i == 1)
				{
					now_map = map_change(m_3, 0);
					stX = 3;
					stY = 2;
					s_p = 3;
				} else
				if(i == 2)
				{
					now_map = map_change(m_2, 0);
					stX = 3;
					stY = 15;
					s_p = 5;
				} else
				if(i == 4)
				{
					now_map = m_3;
					stX = -3;
					stY = 9;
					s_p = 4;
				}
			} else
			if(s_p == 3)
				if(i == 1)
				{
					now_map = map_change(m_2, 1);
					stX = 3;
					stY = 2;
					s_p = 6;
				} else
				if(i == 2)
				{
					now_map = map_change(m_3, 0);
					stX = 3;
					stY = 15;
					s_p = 3;
				}
			g_state = 1;
		} else
		if(g_state == 1)
		{
			if(s_p == 1)
			{
				if(i == 1)
				{
					now_map = K_M;
					save_maul = 0;
					s_p = 1;
					stX = -1;
					stY = 7;
					g_state = 0;
				}
			} else
			if(s_p == 2)
			{
				if(i == 2)
				{
					now_map = K_M;
					save_maul = 0;
					s_p = 1;
					stX = 0;
					stY = 15;
					g_state = 0;
				} else
				if(i == 4)
				{
					now_map = map_change(m_2, 0);
					stX = -3;
					stY = 9;
					s_p = 5;
				}
			} else
			if(s_p == 3)
			{
				if(i == 1)
				{
					now_map = K_M;
					save_maul = 2;
					s_p = 3;
					stX = -1;
					stY = 7;
					g_state = 0;
				} else
				if(i == 2)
				{
					now_map = E_M;
					save_maul = 1;
					s_p = 2;
					stX = 0;
					stY = 15;
					g_state = 0;
				} else
				if(i == 3)
				{
					now_map = map_change(m_1, 2);
					s_p = 10;
					stX = 10;
					stY = 8;
				}
			} else
			if(s_p == 4)
			{
				if(i == 1)
				{
					now_map = map_change(m_1, 1);
					s_p = 9;
					stX = 3;
					stY = 2;
				} else
				if(i == 3)
				{
					now_map = E_M;
					save_maul = 1;
					s_p = 2;
					stX = 3;
					stY = 11;
					g_state = 0;
				} else
				if(i == 4)
				{
					now_map = D_1;
					stX = 9;
					stY = 4;
					s_p = 1;
					spX = 0;
					spY = 0;
					g_state = 2;
					//if(sound_on)
						playMidi("cave");
				}
			} else
			if(s_p == 5)
			{
				if(i == 1)
				{
					now_map = E_M;
					save_maul = 1;
					s_p = 2;
					stX = -1;
					stY = 7;
					g_state = 0;
				} else
				if(i == 3)
				{
					now_map = map_change(m_2, 2);
					stX = 10;
					stY = 8;
					s_p = 2;
				}
			} else
			if(s_p == 6)
			{
				if(i == 2)
				{
					now_map = K_M;
					save_maul = 2;
					s_p = 3;
					stX = 0;
					stY = 15;
					g_state = 0;
				} else
				if(i == 3)
				{
					now_map = map_change(m_3, 1);
					stX = 10;
					stY = 8;
					s_p = 7;
				}
			} else
			if(s_p == 7)
			{
				if(i == 2)
				{
					if(n_s == 5)
					{
						now_map = D_2;
						stX = 9;
						stY = 4;
						s_p = 4;
						spX = 0;
						spY = 0;
						g_state = 2;
						//if(sound_on)
							playMidi("cave");
					} else
					{
						state = 12;
						r_c = 0;
						return;
					}
				} else
				if(i == 3)
				{
					now_map = map_change(m_1, 2);
					stX = 10;
					stY = 8;
					s_p = 8;
				} else
				if(i == 4)
				{
					now_map = map_change(m_2, 1);
					stX = -3;
					stY = 8;
					s_p = 6;
				}
			} else
			if(s_p == 8)
			{
				if(i == 4)
				{
					now_map = map_change(m_3, 1);
					stX = -3;
					stY = 8;
					s_p = 7;
				}
			} else
			if(s_p == 9)
			{
				if(i == 2)
				{
					now_map = m_3;
					stX = 4;
					stY = 15;
					s_p = 4;
				}
			} else
			if(s_p == 10 && i == 4)
			{
				now_map = map_change(m_3, 0);
				stX = -3;
				stY = 9;
				s_p = 3;
			}
		} else
		if(g_state == 2)
		{
			if(s_p == 1)
			{
				if(i == 13)
				{
					now_map = m_3;
					stX = 10;
					stY = 9;
					s_p = 4;
					g_state = 1;
					//if(sound_on)
						playMidi("town");
				} else
				if(i == 3)
				{
					now_map = map_change(D_1, 0);
					stX = 9;
					stY = 4;
					spX = 0;
					spY = 0;
					s_p = 2;
				}
			} else
			if(s_p == 2)
			{
				if(i == 13)
				{
					now_map = D_1;
					stX = -1;
					stY = 14;
					spX = 0;
					spY = 0;
					s_p = 1;
				} else
				if(i == 3)
				{
					now_map = map_change(D_1, 1);
					stX = 9;
					stY = 4;
					spX = 0;
					spY = 0;
					s_p = 3;
				}
			} else
			if(s_p == 3)
			{
				if(i == 13)
				{
					now_map = map_change(D_1, 0);
					stX = -1;
					stY = 14;
					spX = 0;
					spY = 0;
					s_p = 2;
				} else
				if(i == 3)
					if(n_s == 3)
					{
						now_map = D_B;
						stX = -2;
						stY = 8;
						s_p = 1;
						spX = 1;
						spY = 1;
						c_a = 3;
						n_d = 5;
						g_state = 3;
					} else
					{
						state = 12;
						r_c = 0;
						return;
					}
			} else
			if(s_p == 4)
			{
				if(i == 13)
				{
					now_map = map_change(m_3, 1);
					stX = 3;
					stY = 2;
					s_p = 7;
					g_state = 1;
					//if(sound_on)
						playMidi("town");
				} else
				if(i == 3)
				{
					now_map = map_change(D_2, 0);
					stX = 9;
					stY = 4;
					spX = 0;
					spY = 0;
					s_p = 5;
				}
			} else
			if(s_p == 5)
			{
				if(i == 13)
				{
					now_map = D_2;
					stX = -1;
					stY = 14;
					spX = 0;
					spY = 0;
					s_p = 4;
				} else
				if(i == 3)
				{
					now_map = map_change(D_2, 1);
					stX = 9;
					stY = 4;
					spX = 0;
					spY = 0;
					s_p = 6;
				}
			} else
			if(s_p == 6)
			{
				if(i == 13)
				{
					now_map = map_change(D_2, 0);
					stX = -1;
					stY = 14;
					spX = 0;
					spY = 0;
					s_p = 5;
				} else
				if(i == 3)
				{
					now_map = map_change(D_2, 2);
					stX = 9;
					stY = 4;
					spX = 0;
					spY = 0;
					s_p = 7;
				}
			} else
			if(s_p == 7)
				if(i == 13)
				{
					now_map = map_change(D_2, 1);
					stX = -1;
					stY = 14;
					spX = 0;
					spY = 0;
					s_p = 6;
				} else
				if(i == 3)
				{
					now_map = D_B;
					stX = -2;
					stY = 8;
					s_p = 1;
					spX = 1;
					spY = 1;
					c_a = 3;
					n_d = 6;
					g_state = 4;
				}
		} else
		if(g_state == 3)
		{
			if(i == 11)
			{
				now_map = map_change(D_1, 1);
				stX = -1;
				stY = 14;
				s_p = 3;
				spX = 0;
				spY = 0;
				g_state = 2;
			}
		} else
		if(g_state == 4 && i == 11)
		{
			stX = -2;
			stY = 8;
			spX = 1;
			spY = 1;
			c_a = 3;
			g_state = 5;
		}
		r_c = 0;
		if(g_state == 0)
		{
			//if(sound_on)
				playMidi("town");
		}else{
			make_mob(g_state);
		}
	}

	private void make_mob(byte byte0)
	{
		if(byte0 == 3 || byte0 == 4 || byte0 == 5)
		{
			for(int i = 0; i < 8; i++)
				if(mob_d[i] != null)
					mob_d[i] = null;

			mob_d[0] = new Monster_data(this, (byte)1, (byte)9, (byte)0, (byte)1, (byte)(g_state - 2));
		} else
		{
			byte abyte0[] = new byte[24];
			if(byte0 == 1)
			{
				byte abyte1[] = {
					2, 8, 0, 6, 14, 0, 8, 4, 0, 12, 
					10, 1, 3, 10, 1, 6, 5, 1, 11, 8, 
					2, 8, 13, 2
				};
				abyte0 = abyte1;
			} else
			if(byte0 == 2)
				if(s_p >= 1 && s_p <= 3)
				{
					byte abyte2[] = {
						2, 8, 3, 6, 14, 3, 8, 4, 3, 12, 
						10, 3, 3, 10, 3, 6, 5, 3, 11, 8, 
						3, 8, 13, 3
					};
					abyte0 = abyte2;
				} else
				{
					byte abyte3[] = {
						2, 8, 4, 6, 14, 4, 8, 4, 4, 12, 
						10, 4, 3, 10, 4, 6, 5, 4, 11, 8, 
						4, 8, 13, 4
					};
					abyte0 = abyte3;
				}
			for(int j = 0; j < 8; j++)
			{
				if(mob_d[j] != null)
					mob_d[j] = null;
				mob_d[j] = new Monster_data(this, abyte0[j * 3], abyte0[j * 3 + 1], abyte0[j * 3 + 2], m_l);
			}

		}
	}

	public boolean monster_search(byte byte0, byte byte1, byte byte2, byte byte3)
	{
		boolean flag;
		flag = false;
		mob_a = 10;
		try
		{
			for(int i = 0; i < 8; i++)
			{
				if(byte0 != mob_d[i].mX || byte1 != mob_d[i].mY || byte2 != mob_d[i].m_sX || byte3 != mob_d[i].m_sY)
					continue;
				flag = true;
				mob_a = (byte)i;
				break;
			}

		}
		catch(Exception exception)
		{
			flag = false;
			mob_a = 10;
		}
		return flag;
	}

	private boolean monster_action(int i)
	{
		boolean flag = false;
		try
		{
			if(((stX + 3) - mob_d[i].mX == 1 || (stX + 3) - mob_d[i].mX == 2) && stY - mob_d[i].mY >= -2 && stY - mob_d[i].mY <= 2)
			{
				mob_d[i].m_p = 1;
				if(mob_d[i].m_sX + 1 == 2)
				{
					byte byte0 = now_map[mob_d[i].mX + 1 + 1][mob_d[i].mY - 1];
					if(byte0 != 99 && byte0 != 3 && byte0 != 4 && byte0 != 8)
						if((stX + 3 != mob_d[i].mX + 1 || stY != mob_d[i].mY || spX != 0 || spY != mob_d[i].m_sY) && !monster_search((byte)(mob_d[i].mX + 1), mob_d[i].mY, (byte)0, mob_d[i].m_sY))
						{
							mob_d[i].m_sX = 0;
							mob_d[i].mX++;
							flag = true;
						} else
						if(!monster_search((byte)(mob_d[i].mX + 1), mob_d[i].mY, (byte)0, mob_d[i].m_sY))
						{
							c_3[i]++;
							flag = true;
						}
				} else
				if((stX + 3 != mob_d[i].mX || stY != mob_d[i].mY || spX != 1 || spY != mob_d[i].m_sY) && !monster_search(mob_d[i].mX, mob_d[i].mY, (byte)1, mob_d[i].m_sY))
				{
					mob_d[i].m_sX++;
					flag = true;
				}
			} else
			if((stX + 3) - mob_d[i].mX == 0)
			{
				if(stY - mob_d[i].mY == 1 || stY - mob_d[i].mY == 2)
				{
					mob_d[i].m_p = 3;
					if(mob_d[i].m_sY + 1 == 2)
					{
						byte byte1 = now_map[mob_d[i].mX + 1][(mob_d[i].mY + 1) - 1];
						if(byte1 != 99 && byte1 != 3 && byte1 != 4 && byte1 != 8)
							if((stX + 3 != mob_d[i].mX || stY != mob_d[i].mY + 1 || spX != mob_d[i].m_sX || spY != 0) && !monster_search(mob_d[i].mX, (byte)(mob_d[i].mY + 1), mob_d[i].m_sX, (byte)0))
							{
								mob_d[i].m_sY = 0;
								mob_d[i].mY++;
								flag = true;
							} else
							if(!monster_search(mob_d[i].mX, (byte)(mob_d[i].mY + 1), mob_d[i].m_sX, (byte)0))
							{
								c_3[i]++;
								flag = true;
							}
					} else
					if((stX + 3 != mob_d[i].mX || stY != mob_d[i].mY || spX != mob_d[i].m_sX || spY != 1) && !monster_search(mob_d[i].mX, mob_d[i].mY, mob_d[i].m_sX, (byte)1))
					{
						mob_d[i].m_sY++;
						flag = true;
					}
				} else
				if(stY - mob_d[i].mY == 0)
				{
					if((spX + spY + mob_d[i].m_sX + mob_d[i].m_sY) % 2 == 0)
					{
						if(mob_d[i].m_sX == 0 && !monster_search(mob_d[i].mX, mob_d[i].mY, (byte)1, mob_d[i].m_sY))
						{
							mob_d[i].m_sX = 1;
							flag = true;
						} else
						if(mob_d[i].m_sX == 1 && !monster_search(mob_d[i].mX, mob_d[i].mY, (byte)0, mob_d[i].m_sY))
						{
							mob_d[i].m_sX = 0;
							flag = true;
						}
					} else
					if((spX + spY + mob_d[i].m_sX + mob_d[i].m_sY) % 2 == 1)
					{
						if(spX - mob_d[i].m_sX == 1)
							mob_d[i].m_p = 1;
						else
						if(spX - mob_d[i].m_sX == -1)
							mob_d[i].m_p = 2;
						else
						if(spY - mob_d[i].m_sY == 1)
							mob_d[i].m_p = 3;
						else
						if(spY - mob_d[i].m_sY == -1)
							mob_d[i].m_p = 0;
						c_3[i]++;
						flag = true;
					}
				} else
				if(stY - mob_d[i].mY == -1 || stY - mob_d[i].mY == -2)
				{
					mob_d[i].m_p = 0;
					if(mob_d[i].m_sY - 1 == -1)
					{
						byte byte2 = now_map[mob_d[i].mX + 1][mob_d[i].mY - 1 - 1];
						if(byte2 != 99 && byte2 != 3 && byte2 != 4 && byte2 != 8)
							if((stX + 3 != mob_d[i].mX || stY != mob_d[i].mY - 1 || spX != mob_d[i].m_sX || spY != 1) && !monster_search(mob_d[i].mX, (byte)(mob_d[i].mY - 1), mob_d[i].m_sX, (byte)1))
							{
								mob_d[i].m_sY = 1;
								mob_d[i].mY--;
								flag = true;
							} else
							if(!monster_search(mob_d[i].mX, (byte)(mob_d[i].mY - 1), mob_d[i].m_sX, (byte)1))
								c_3[i]++;
					} else
					if((stX + 3 != mob_d[i].mX || stY != mob_d[i].mY || spX != mob_d[i].m_sX || spY != 0) && !monster_search(mob_d[i].mX, mob_d[i].mY, mob_d[i].m_sX, (byte)0))
					{
						mob_d[i].m_sY--;
						flag = true;
					}
				}
			} else
			if(((stX + 3) - mob_d[i].mX == -1 || (stX + 3) - mob_d[i].mX == -2) && stY - mob_d[i].mY >= -1 && stY - mob_d[i].mY <= 1)
			{
				mob_d[i].m_p = 2;
				if(mob_d[i].m_sX - 1 == -1)
				{
					byte byte3 = now_map[(mob_d[i].mX - 1) + 1][mob_d[i].mY - 1];
					if(byte3 != 99 && byte3 != 3 && byte3 != 4 && byte3 != 8)
						if((stX + 3 != mob_d[i].mX - 1 || stY != mob_d[i].mY || spX != 1 || spY != mob_d[i].m_sY) && !monster_search((byte)(mob_d[i].mX - 1), mob_d[i].mY, (byte)1, mob_d[i].m_sY))
						{
							mob_d[i].m_sX = 1;
							mob_d[i].mX--;
							flag = true;
						} else
						if(!monster_search((byte)(mob_d[i].mX - 1), mob_d[i].mY, (byte)1, mob_d[i].m_sY))
						{
							c_3[i]++;
							flag = true;
						}
				} else
				if((stX + 3 != mob_d[i].mX || stY != mob_d[i].mY || spX != 0 || spY != mob_d[i].m_sY) && !monster_search(mob_d[i].mX, mob_d[i].mY, (byte)0, mob_d[i].m_sY))
				{
					mob_d[i].m_sX--;
					flag = true;
				}
			}
		}
		catch(Exception exception) { }
		return flag;
	}

	private void boss_monster_action(Monster_data monster_data, byte byte0)
	{
		Random random = new Random();
		if(byte0 == 3 || byte0 == 4)
		{
			byte byte1 = (byte)((random.nextInt() >>> 1) % 7 + 2);
			if(byte1 % 2 == 0)
			{
				monster_data.mX = 1;
				monster_data.mY = (byte)(byte1 + 1);
			} else
			{
				monster_data.mX = (byte)(byte1 - 1);
				monster_data.mY = 9;
			}
		} else
		if(byte0 == 5)
		{
			byte byte2 = (byte)((random.nextInt() >>> 1) % 6 + 3);
			byte byte3 = (byte)((random.nextInt() >>> 1) % 6 + 3);
			stX = (byte)(byte2 - 3 - 1);
			stY = (byte)(byte3 + 1);
		}
	}

	private byte attack_data()
	{
		byte byte0 = 0;
		if(m_s[0] == 0)
			byte0 = 4;
		else
		if(m_s[0] == 1)
			byte0 = 6;
		else
		if(m_s[0] == 2)
			byte0 = 8;
		else
		if(m_s[0] == 3)
			byte0 = 17;
		byte0 += m_s[4];
		byte0 *= 3;
		return byte0;
	}

	private byte defense_data()
	{
		byte byte0 = 0;
		if(m_s[2] == 0)
			byte0 = 2;
		else
		if(m_s[2] == 1)
			byte0 = 4;
		else
		if(m_s[2] == 2)
			byte0 = 6;
		byte0 += m_s[1];
		return byte0;
	}

	private void mo_a()
	{
		int ai[] = new int[2];
		switch(w_p)
		{
		default:
			break;

		case 3: // '\003'
			if(spY + 1 == 2)
			{
				byte byte0 = now_map[stX + 3 + 1][(stY + 1) - 1];
				if(monster_search((byte)(stX + 3), (byte)(stY + 1), spX, (byte)0))
				{
					int ai1[] = mob_d[mob_a].monster_health(attack_data());
					if(ai1[0] != 0)
					{
						m_e += ai1[0];
						mob_die[mob_a] = true;
						in = 0;
						if(ai1[1] != 0)
							item_get(ai1[1]);
						if(g_state == 3 || g_state == 5)
						{
							c_a = 3;
							//if(sound_on)
								//playSound(BMOB_DIE, 1);
						} else
						if(g_state == 4)
						{
							n_s = 6;
							n_d = 7;
							//if(sound_on)
								//playSound(BMOB_DIE, 1);
						} else
						if(g_state == 6)
						{
							if(ai1[1] != 0)
								end_m = 1;
							cho = 0;
							cho_1 = 0;
							state = 8;
							//if(sound_on)
								//playSound(BMOB_DIE, 1);
						} //else
						//if(sound_on)
							//playSound(MOB_DIE, 1);
					}
					g_p_state = true;
				}
				break;
			}
			if(!monster_search((byte)(stX + 3), stY, spX, (byte)1))
				break;
			int ai2[] = mob_d[mob_a].monster_health(attack_data());
			if(ai2[0] != 0)
			{
				m_e += ai2[0];
				mob_die[mob_a] = true;
				in = 0;
				if(ai2[1] != 0)
					item_get(ai2[1]);
				if(g_state == 3 || g_state == 5)
				{
					c_a = 3;
					//if(sound_on)
						//playSound(BMOB_DIE, 1);
				} else
				if(g_state == 4)
				{
					n_s = 6;
					n_d = 7;
					//if(sound_on)
						//playSound(BMOB_DIE, 1);
				} else
				if(g_state == 6)
				{
					if(ai2[1] != 0)
						end_m = 1;
					cho = 0;
					cho_1 = 0;
					state = 8;
					//if(sound_on)
						//playSound(BMOB_DIE, 1);
				} //else
				//if(sound_on)
					//playSound(MOB_DIE, 1);
			}
			g_p_state = true;
			break;

		case 0: // '\0'
			if(spY - 1 == -1)
			{
				byte byte1 = now_map[stX + 3 + 1][stY - 1 - 1];
				if(!monster_search((byte)(stX + 3), (byte)(stY - 1), spX, (byte)1))
					break;
				int ai3[] = mob_d[mob_a].monster_health(attack_data());
				if(ai3[0] != 0)
				{
					m_e += ai3[0];
					mob_die[mob_a] = true;
					in = 0;
					if(ai3[1] != 0)
						item_get(ai3[1]);
					if(g_state == 3 || g_state == 5)
					{
						c_a = 3;
						//if(sound_on)
							//playSound(BMOB_DIE, 1);
					} else
					if(g_state == 4)
					{
						n_s = 6;
						n_d = 7;
						//if(sound_on)
							//playSound(BMOB_DIE, 1);
					} else
					if(g_state == 6)
					{
						if(ai3[1] != 0)
							end_m = 1;
						cho = 0;
						cho_1 = 0;
						state = 8;
						//if(sound_on)
							//playSound(BMOB_DIE, 1);
					} //else
					//if(sound_on)
						//playSound(MOB_DIE, 1);
				}
				g_p_state = true;
				break;
			}
			if(!monster_search((byte)(stX + 3), stY, spX, (byte)0))
				break;
			int ai4[] = mob_d[mob_a].monster_health(attack_data());
			if(ai4[0] != 0)
			{
				m_e += ai4[0];
				mob_die[mob_a] = true;
				in = 0;
				if(ai4[1] != 0)
					item_get(ai4[1]);
				if(g_state == 3 || g_state == 5)
				{
					c_a = 3;
					//if(sound_on)
						//playSound(BMOB_DIE, 1);
				} else
				if(g_state == 4)
				{
					n_s = 6;
					n_d = 7;
					//if(sound_on)
						//playSound(BMOB_DIE, 1);
				} else
				if(g_state == 6)
				{
					if(ai4[1] != 0)
						end_m = 1;
					cho = 0;
					cho_1 = 0;
					state = 8;
					//if(sound_on)
						//playSound(BMOB_DIE, 1);
				} //else
				//if(sound_on)
					//playSound(MOB_DIE, 1);
			}
			g_p_state = true;
			break;

		case 2: // '\002'
			if(spX - 1 == -1)
			{
				byte byte2 = now_map[((stX + 3) - 1) + 1][stY - 1];
				if(!monster_search((byte)((stX + 3) - 1), stY, (byte)1, spY))
					break;
				int ai5[] = mob_d[mob_a].monster_health(attack_data());
				if(ai5[0] != 0)
				{
					m_e += ai5[0];
					mob_die[mob_a] = true;
					in = 0;
					if(ai5[1] != 0)
						item_get(ai5[1]);
					if(g_state == 3 || g_state == 5)
					{
						c_a = 3;
						//if(sound_on)
							//playSound(BMOB_DIE, 1);
					} else
					if(g_state == 4)
					{
						n_s = 6;
						n_d = 7;
						//if(sound_on)
							//playSound(BMOB_DIE, 1);
					} else
					if(g_state == 6)
					{
						if(ai5[1] != 0)
							end_m = 1;
						cho = 0;
						cho_1 = 0;
						state = 8;
						//if(sound_on)
							//playSound(BMOB_DIE, 1);
					} //else
					//if(sound_on)
						//playSound(MOB_DIE, 1);
				}
				g_p_state = true;
				break;
			}
			if(!monster_search((byte)(stX + 3), stY, (byte)0, spY))
				break;
			int ai6[] = mob_d[mob_a].monster_health(attack_data());
			if(ai6[0] != 0)
			{
				m_e += ai6[0];
				mob_die[mob_a] = true;
				in = 0;
				if(ai6[1] != 0)
					item_get(ai6[1]);
				if(g_state == 3 || g_state == 5)
				{
					c_a = 3;
					//if(sound_on)
						//playSound(BMOB_DIE, 1);
				} else
				if(g_state == 4)
				{
					n_s = 6;
					n_d = 7;
					//if(sound_on)
						//playSound(BMOB_DIE, 1);
				} else
				if(g_state == 6)
				{
					if(ai6[1] != 0)
						end_m = 1;
					cho = 0;
					cho_1 = 0;
					state = 8;
					//if(sound_on)
						//playSound(BMOB_DIE, 1);
				} //else
				//if(sound_on)
					//playSound(MOB_DIE, 1);
			}
			g_p_state = true;
			break;

		case 1: // '\001'
			if(spX + 1 == 2)
			{
				byte byte3 = now_map[stX + 3 + 1 + 1][stY - 1];
				if(!monster_search((byte)(stX + 3 + 1), stY, (byte)0, spY))
					break;
				int ai7[] = mob_d[mob_a].monster_health(attack_data());
				if(ai7[0] != 0)
				{
					m_e += ai7[0];
					mob_die[mob_a] = true;
					in = 0;
					if(ai7[1] != 0)
						item_get(ai7[1]);
					if(g_state == 3 || g_state == 5)
					{
						c_a = 3;
						//if(sound_on)
							//playSound(BMOB_DIE, 1);
					} else
					if(g_state == 4)
					{
						n_s = 6;
						n_d = 7;
						//if(sound_on)
							//playSound(BMOB_DIE, 1);
					} else
					if(g_state == 6)
					{
						if(ai7[1] != 0)
							end_m = 1;
						cho = 0;
						cho_1 = 0;
						state = 8;
						//if(sound_on)
							//playSound(BMOB_DIE, 1);
					} //else
					//if(sound_on)
						//playSound(MOB_DIE, 1);
				}
				g_p_state = true;
				break;
			}
			if(!monster_search((byte)(stX + 3), stY, (byte)1, spY))
				break;
			int ai8[] = mob_d[mob_a].monster_health(attack_data());
			if(ai8[0] != 0)
			{
				m_e += ai8[0];
				mob_die[mob_a] = true;
				in = 0;
				if(ai8[1] != 0)
					item_get(ai8[1]);
				if(g_state == 3 || g_state == 5)
				{
					c_a = 3;
					//if(sound_on)
						//playSound(BMOB_DIE, 1);
				} else
				if(g_state == 4)
				{
					n_s = 6;
					n_d = 7;
					//if(sound_on)
						//playSound(BMOB_DIE, 1);
				} else
				if(g_state == 6)
				{
					if(ai8[1] != 0)
						end_m = 1;
					cho = 0;
					cho_1 = 0;
					state = 8;
					//if(sound_on)
						//playSound(BMOB_DIE, 1);
				} //else
				//if(sound_on)
					//playSound(MOB_DIE, 1);
			}
			g_p_state = true;
			break;
		}
	}

	private void item_get(int i)
	{
		in_1 = 0;
		if(i / 100 == 11)
		{
			int j = i % 110;
			m_i[j]++;
			if(j == 9)
				item = 6;
			else
			if(j == 10)
				item = 7;
			else
			if(j == 11)
				item = 8;
			else
			if(j == 12)
				item = 9;
			else
				item = j;
		} else
		{
			item = i;
			m_m += i;
		}
	}

	private void saveData(int i)
	{
		if(i == 0)
		{
			d_s.deleteRecords();
			d_s.openRecStore();
			d_s.writeRecord(m_i);
			d_s.writeRecord(m_s);
			d_s.writeRecord("" + m_e);
			d_s.writeRecord("" + m_h);
			d_s.writeRecord("" + m_m);
			d_s.writeRecord(new byte[] {
				m_l, g_state, s_p, n_d, n_c, n_s, stX, stY, end_m
			});
			d_s.closeRecStore();
		} else
		{
			d_s.openRecStore();
			boolean flag = d_s.readRecordsUpdate();
			d_s.closeRecStore();
			if(flag)
			{
				d_s.deleteRecords();
				d_s.openRecStore();
				d_s.writeRecord(m_i);
				d_s.writeRecord(d_s.save_my_state);
				d_s.writeRecord("" + d_s.save_exp);
				d_s.writeRecord("" + d_s.save_health);
				d_s.writeRecord("" + m_m);
				if(i == 1)
					d_s.writeRecord(new byte[] {
						d_s.save_level, d_s.save_state, d_s.s_p, d_s.n_d, d_s.n_c, d_s.n_s, d_s.stX, d_s.stY, d_s.end_m
					});
				else
				if(i == 2)
					d_s.writeRecord(new byte[] {
						d_s.save_level, d_s.save_state, d_s.s_p, d_s.n_d, d_s.n_c, d_s.n_s, d_s.stX, d_s.stY, end_m
					});
				d_s.closeRecStore();
			} else
			{
				d_s.deleteRecords();
				d_s.openRecStore();
				d_s.writeRecord(m_i);
				d_s.writeRecord(m_s);
				d_s.writeRecord("" + m_e);
				d_s.writeRecord("" + m_h);
				d_s.writeRecord("" + m_m);
				d_s.writeRecord(new byte[] {
					m_l, g_state, s_p, n_d, n_c, n_s, stX, stY, end_m
				});
				d_s.closeRecStore();
			}
		}
	}

	private boolean ranking(int i)
	{
		boolean flag;
		HttpConnection httpconnection;
		DataInputStream datainputstream;
		flag = false;
		httpconnection = null;
		datainputstream = null;
		try
		{
			httpconnection = (HttpConnection)Connector.open("http://www.gt9119.com/servlet/MServlet?game=hero&money=" + (m_m - 10000));
			datainputstream = new DataInputStream(httpconnection.openInputStream());
			String s = datainputstream.readUTF();

			if(datainputstream != null)
			{
				datainputstream.close();
				datainputstream = null;
			}
			if(httpconnection != null)
			{
				httpconnection.close();
				httpconnection = null;
			}
		}
		catch(Exception exception) { }
			m_m = m_m - 10000;
			if(i == 1)
				saveData(1);
			else
			if(i == 2)
				saveData(2);
			flag = true;
		return flag;
	}

	private void drawLogo(Graphics g)
	{
		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, getWidth(), getHeight());
		if(!g_p_state)
		{
			drawImageClip(g,l_img,0,116,getWidth()/2-58,getHeight()/2-41);
		} else
		{
			drawImageClip(g,l_img,1,116,getWidth()/2-58,getHeight()/2-41);			
		}
	}

	private void drawIntro(Graphics g)
	{
		String as[] = {
			"��ʼ��Ϸ", "������ս","����˵��", "��Ϸ����", "����", "����", "�˳���Ϸ"
		};
		drawBack(g);
		drawImage(g,g_img, sX + 15, (sY + 5 + 10) - 20, 0x10 | 0x4);
		g.setColor(0x440351);
		drawString(g,"1. " + as[0], sX + 33, (sY + 32 + 10 + 20) - 20, 0x10 | 0x4);
		drawString(g,"2. " + as[1], sX + 33, (sY + 46 + 10 + 20) - 20, 0x10 | 0x4);
		drawString(g,"3. " + as[2], sX + 33, (sY + 60 + 10 + 20) - 20, 0x10 | 0x4);
		drawString(g,"4. " + as[3], sX + 33, (sY + 74 + 10 + 20) - 20, 0x10 | 0x4);
		drawString(g,"5. " + as[4]+(sound_on ? "  ����" : "  �ر�"), sX + 33, (sY + 88 + 10 + 20) - 20, 0x10 | 0x4);
		drawString(g,"6. " + as[5], sX + 33, (sY + 102 + 10 + 20) - 20, 0x10 | 0x4);
		drawString(g,"7. " + as[6], sX + 33, (sY + 116 + 10 + 20) - 20, 0x10 | 0x4);
		g.setColor(0xffffff);
		drawString(g,(cho + 1) + ". " + as[cho], sX + 33, ((sY + 32 + 10 + 20) - 20) + cho * 14, 0x10 | 0x4);
		drawString(g,"ȷ��",  2, getHeight() - 2, 0x20 | 0x4);
		as = null;
	}
	private void drawBack(Graphics g)
	{
		g.setColor(0x30D0F0);
		g.fillRect(0, 0, getWidth(), 26);
		g.setColor(0x30C0F0);
		g.fillRect(0, 26, getWidth(), 26);
		g.setColor(0x28B8F0);
		g.fillRect(0, 52, getWidth(), 26);
		g.setColor(0x28B0E8);
		g.fillRect(0, 78, getWidth(), 26);
		g.setColor(0x2098E8);
		g.fillRect(0, 104, getWidth(), 26);
		g.setColor(0x1890E0);
		g.fillRect(0, 130, getWidth(), 26);
		g.setColor(0x1880D8);
		g.fillRect(0, 156, getWidth(), 26);
		g.setColor(0x1068D8);
		g.fillRect(0, 182, getWidth(), 26);
	}
	private void drawMenuBack(Graphics g)
	{
		g.setColor(0x4669BE);
		g.fillRect(sX + 1, sY + 90, 118, 3);
		g.setColor(0x3D60B5);
		g.fillRect(sX + 1, sY + 93, 118, 4);
		g.setColor(0x3758AD);
		g.fillRect(sX + 1, sY + 97, 118, 4);
		g.setColor(0x2F50A5);
		g.fillRect(sX + 1, sY + 101, 118, 4);
		g.setColor(0x28489D);
		g.fillRect(sX + 1, sY + 105, 118, 4);
		g.setColor(0x204095);
		g.fillRect(sX + 1, sY + 109, 118, 5);
		g.setColor(0x19388D);
		g.fillRect(sX + 1, sY + 114, 118, 5);
		g.setColor(0x122981);
		g.fillRect(sX + 1, sY + 119, 118, 6);
		g.setColor(0x0B1B6B);
		g.fillRect(sX + 1, sY + 125, 118, 6);
		g.setColor(0xFFFFFF);
		g.drawRect(sX + 1, sY + 90, 118, 41);
	}
	private void drawNewStart(Graphics g)
	{
		drawBack(g);
		g.setColor(0x440351);
		drawString(g,"����һ�����ҵ�ʱ��", sX + 60, sY + 4, 0x10 | 0x1);
		drawString(g,"�鳼����,��������", sX + 60, sY + 25, 0x10 | 0x1);
		drawString(g,"ǿ�����йٷ˹���", sX + 60, sY + 46, 0x10 | 0x1);
		drawString(g,"����������ޱ��λ", sX + 60, sY + 67, 0x10 | 0x1);
		drawString(g,"��Ϊ�����峦�ĺú�", sX + 60, sY + 88, 0x10 | 0x1);
		drawString(g,"�������²���", sX + 60, sY + 109, 0x10 | 0x1);
		g.setColor(0xFFFFFF);
		drawString(g,"ȷ��",  2, getHeight() - 2, 0x20 | 0x4);
	}

	private void drawLoading(Graphics g)
	{
		g.setColor(0);
		g.drawRect(sX + 29, sY + 64, 62, 25);
		g.setColor(0x1050B0);
		g.fillRect(sX + 30, sY + 65, 61, 24);
		g.setColor(0xffffff);
		drawString(g,"������...", sX + 60, sY + 70, 0x10 | 0x1);
	}

	private void adviceMove(Graphics g)
	{
		drawBack(g);
		g.setColor(0xffffff);
		drawString(g,"�ƶ� : �������Ҽ� ", sX + 7, ((sY + 40) - 26) + 10, 0x10 | 0x4);
		drawString(g,"����(�Ի�) : ����� ", sX + 7, ((sY + 53) - 26) + 10 + 3, 0x10 | 0x4);
		drawString(g,"������Ϸ : 7��", sX + 7, ((sY + 66) - 26) + 10 + 6, 0x10 | 0x4);
		drawString(g,"С��ͼ : 0��", sX + 7, ((sY + 79) - 26) + 10 + 9, 0x10 | 0x4);
		drawString(g,"״̬�� : 9��", sX + 7, ((sY + 92) - 26) + 10 + 12, 0x10 | 0x4);
		drawString(g,"��ҩ : 1,2��", sX + 7, ((sY + 105) - 26) + 10 + 15, 0x10 | 0x4);
		drawString(g,"�س� : 3��", sX + 7, ((sY + 118) - 26) + 10 + 18, 0x10 | 0x4);
		g.setColor(0xFFFFFF);
		drawString(g,"����", getWidth() - 2, getHeight() - 2, 0x20 | 0x8);
	}

	private void adviceView(Graphics g)
	{
		drawBack(g);
		drawImage(g,m_img[4], sX + 60, sY + 10, 0x10 | 0x1);
		g.setClip(sX + 21, sY + 15, 36, 9);
		drawImage(g,m_img[2], sX + 21, sY + 15, 0x10 | 0x4);
		g.setClip(sX + 21, sY + 28, 32, 9);
		drawImage(g,m_img[2], sX - 15, sY + 28, 0x10 | 0x4);
		drawImageClip(g,m_img[1],0,12,sX + 74,sY + 21);
		drawImageClip(g,m_img[1],1,12,sX + 102,sY + 21);
		drawImageClip(g,m_img[1],2,12,sX + 129,sY + 21);
		g.setClip(sX + 10, sY + 40, 60, 80);
		String as[] = {
			"����", "����", "��ҩ", "�سǾ�"
		};
		g.setColor(0x440351);
		drawString(g,as[0], sX + 10, (sY + 66) - 8 - 15, 0x10 | 0x4);
		drawString(g,as[1], sX + 10, ((sY + 79) - 8 - 15) + 2, 0x10 | 0x4);
		drawString(g,as[2], sX + 10, ((sY + 92) - 8 - 15) + 4, 0x10 | 0x4);
		drawString(g,as[3], sX + 10, ((sY + 105) - 8 - 15) + 6, 0x10 | 0x4);
		g.setClip(0, 0, getWidth(), getHeight());
		g.setColor(0xffffff);
		drawString(g,as[cho], sX + 10, ((sY + 66) - 8 - 15) + cho * 15, 0x10 | 0x4);
		drawString(g,"����", getWidth() - 2, getHeight() - 2, 0x20 | 0x8);
		if(g_p_state)
		{
			g.setColor(65532);
			if(cho == 0)
			{
				g.drawRect(sX + 21, sY + 15, 36, 9);
			} else
			if(cho == 1)
			{
				g.drawRect(sX + 21, sY + 28, 36, 9);
			} else
			if(cho == 2)
			{
				g.drawRect(sX + 64, sY + 10, 56, 28);
			} else
			if(cho == 3)
			{
				g.drawRect(sX + 120, sY + 10, 28, 28);
			}
		}
	}

	private void drawQNA(Graphics g)
	{
		drawBack(g);
		g.setColor(0xffffff);
		drawString(g,"ˮ�Q����Ȩ����", sX + 60, sY + 40, 0x40 | 0x1);
		drawString(g,"��ͨͨ�ŷ�չ���޹�˾", sX + 60, sY + 60, 0x40 | 0x1);
		drawString(g,"��վ http://sms.mir3.com.cn", sX + 60, sY + 80, 0x40 | 0x1);
		drawString(g,"���� mobile@optisp.com", sX + 60, sY + 100, 0x40 | 0x1);
		drawString(g,"�ͷ��绰 010��68324680", sX + 60, sY + 120, 0x40 | 0x1);
		drawString(g,"����", getWidth() - 2, getHeight() - 2, 0x20 | 0x8);
	}

	private void drawGame(Graphics g)
	{
		movingMap(g);
		if(g_state == 0)
			viewNpc(g);
		else
		if((g_state == 2 || g_state == 3 || g_state == 4) && (g_state != 3 || n_s != 3) && (g_state != 4 || n_s != 5))
			viewGateway(g, g_state);
		if(g_state == 1 || g_state == 2)
			monster_moving(g);
		else
		if(g_state != 0)
			monster_moving(g, (byte)(g_state - 2));
		if(g_state != 5 || mob_d[0].m_a != 1 || b_m_a)
			char_moving(g);
		try
		{
			if(g_p_state){
				Random x = new Random();
				byte col=(byte)((x.nextInt() >>> 1) % (m_s[3]+1));
				if(w_p == 3)//����
				{
					g.setColor(0);
					g.drawRect(sX + 81, sY + 32, 12, 2);
					g.setColor(0xffffff);
					g.drawLine(sX + 82, sY + 33, sX + 82 + 10, sY + 33);
					drawImageClip(g,c_img[9],col*2+1,23,sX + 67, sY + 35);
					if(mob_d[mob_a].mo_hp > 0)
					{
						g.setColor(0xff0000);
						g.drawLine(sX + 82, sY + 33, sX + 82 + (mob_d[mob_a].mo_hp * 10) / mob_d[mob_a].mo_bHp, sY + 33);
					}
				} else
				if(w_p == 0)//����
				{
					g.setColor(0);
					g.drawRect(sX + 45, sY + 52, 12, 2);
					g.setColor(0xffffff);
					g.drawLine(sX + 46, sY + 53, sX + 46 + 10, sY + 53);
					drawImageClip(g,c_img[9],col*2+1,23,sX + 31, sY + 55);
					if(mob_d[mob_a].mo_hp > 0)
					{
						g.setColor(0xff0000);
						g.drawLine(sX + 46, sY + 53, sX + 46 + (mob_d[mob_a].mo_hp * 10) / mob_d[mob_a].mo_bHp, sY + 53);
					}
				} else
				if(w_p == 2)//����
				{
					g.setColor(0);
					g.drawRect(sX + 45, sY + 32, 12, 2);
					g.setColor(0xffffff);
					g.drawLine(sX + 46, sY + 33, sX + 46 + 10, sY + 33);
					drawImageClip(g,c_img[9],col*2,23,sX + 31, sY + 35);
					if(mob_d[mob_a].mo_hp > 0)
					{
						g.setColor(0xff0000);
						g.drawLine(sX + 46, sY + 33, sX + 46 + (mob_d[mob_a].mo_hp * 10) / mob_d[mob_a].mo_bHp, sY + 33);

					}
				} else
				if(w_p == 1)//����
				{
					g.setColor(0);
					g.drawRect(sX + 81, sY + 52, 12, 2);
					g.setColor(0xffffff);
					g.drawLine(sX + 82, sY + 53, sX + 82 + 10, sY + 53);
					drawImageClip(g,c_img[9],col*2,23,sX + 67, sY + 55);
					if(mob_d[mob_a].mo_hp > 0)
					{
						g.setColor(0xff0000);
						g.drawLine(sX + 82, sY + 53, sX + 82 + (mob_d[mob_a].mo_hp * 10) / mob_d[mob_a].mo_bHp, sY + 53);
					}
				}
			}
		}
		catch(Exception exception) { }
		if(item != 0)
			if(item == 3 || item == 4 || item == 5)
			{
				g.setColor(0);
				drawString(g," ���", 28, 25, 0x10 | 0x4);
				drawString(g," ���", 32, 25, 0x10 | 0x4);
				drawString(g," ���", 30, 23, 0x10 | 0x4);
				drawString(g," ���", 30, 27, 0x10 | 0x4);
				g.setColor(0xffffff);
				drawString(g," ���", 30, 25, 0x10 | 0x4);
				drawImageClip(g,st_img[0],(item - 3),15,10,28);
			} else
			if(item > 5 && item < 10)
			{
				drawImageClip(g,st_img[0],item,15,10,28);
				g.setColor(0);
				drawString(g," ���", 28, 25, 0x10 | 0x4);
				drawString(g," ���", 32, 25, 0x10 | 0x4);
				drawString(g," ���", 30, 23, 0x10 | 0x4);
				drawString(g," ���", 30, 27, 0x10 | 0x4);
				g.setColor(0xffffff);
				drawString(g," ���", 30, 25, 0x10 | 0x4);
			} else
			if(item >= 10)
			{
				drawImage(g,st_img[1], 10, 28, 0x10 | 0x4);
				g.setColor(0);
				drawString(g,"" + item + " ���", 23, 25, 0x10 | 0x4);
				drawString(g,"" + item + " ���", 27, 25, 0x10 | 0x4);
				drawString(g,"" + item + " ���", 25, 23, 0x10 | 0x4);
				drawString(g,"" + item + " ���", 25, 27, 0x10 | 0x4);
				g.setColor(0xffffff);
				drawString(g,"" + item + " ���", 25, 25, 0x10 | 0x4);
			}
		if(c_a == 3)
		{
			String s = null;
			drawMenuBack(g);
			g.setClip(0, 16, 176, 162);
			g.setColor(0xFFFFFF);
			if(n_d == 0)
			{
				if(save_maul == 1)
				{
					s = "��ͷ";
					if(m_l<7){
						drawString(g,"������", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"�㿴��������Ҫĥ��", sX + 3, sY + 105, 0x10 | 0x4);
					}else if(m_l>=7 && m_s[3]==0){
						m_s[3] = 1;
						m_s[4] = 1;
						drawString(g,"��һ�н�����������", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"�����е�Ǳ��", sX + 3, sY + 105, 0x10 | 0x4);
						drawString(g,"�Ҿ͵㲦��һ��", sX + 3, sY + 118, 0x10 | 0x4);
					}else if(m_l>=15 && m_s[3]==1){
						m_s[3] = 2;
						m_s[4] = 2;
						drawString(g,"�һ���������Ե��", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"���ٽ���һ��", sX + 3, sY + 105, 0x10 | 0x4);
						drawString(g,"��һ�нл�Хɽ��", sX + 3, sY + 118, 0x10 | 0x4);
					}else if(m_l>=25 && m_s[3]==2){
						m_s[3] = 3;
						m_s[4] = 3;
						drawString(g,"�Ǻ�,����Ҿͽ���", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"�ҵĳ�������", sX + 3, sY + 105, 0x10 | 0x4);
						drawString(g,"�Ի�����", sX + 3, sY + 118, 0x10 | 0x4);
					}else{
						drawString(g,"���ս̳���������", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"�ҾͲ�����Ⱦ���", sX + 3, sY + 105, 0x10 | 0x4);
					}
				}else{
					s = "��";
					drawString(g,"�þ�..��Ҫ��..", sX + 3, sY + 105, 0x10 | 0x4);
				}
			} else
			if(n_d == 1)
			{
				s = "�ŷ�";
				drawString(g,"Ҫ�⳵��?ֻҪ5000Ԫ", sX + 3, sY + 92, 0x10 | 0x4);
				drawString(g,"1��:���� 2��:���", sX + 3, sY + 105, 0x10 | 0x4);
				drawString(g,"3��:����", sX + 3, sY + 118, 0x10 | 0x4);
			} else
			if(n_d == 2)
			{
				s = "Ǯׯ";
				if(cho == 0)
				{
					if(m_m > 10000)
					{
						drawString(g,"��Ҫ�ϴ���Ǯ��?", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"�����������㽫", sX + 3, sY + 105, 0x10 | 0x4);
						drawString(g,"����ϴ���������", sX + 3, sY + 118, 0x10 | 0x4);
					} else
					{
						drawString(g,"����һ��Ǯ����.", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"����Ǯ����", sX + 3, sY + 105, 0x10 | 0x4);
					}
				} else
				if(cho == 1 || cho == 2)
				{
					drawString(g,"��Ҵ��ͳɹ�.", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"���ϴ���10000���", sX + 3, sY + 105, 0x10 | 0x4);
					drawString(g,"������"+m_m+"���.", sX + 3, sY + 118, 0x10 | 0x4);
					cho =2;

				/*} else
				if(cho == 2)
				{
					drawString(g,"�޷����ӵ�������", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"���Ժ�����", sX + 3, sY + 105, 0x10 | 0x4);*/
				} else
				if(cho == 3)
				{
					drawString(g,"������վ�鿴����", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"http://sms.mir3.com.cn", sX + 3, sY + 105, 0x10 | 0x4);
				} else
				if(cho == 4)
					drawLoading(g);

			} else
			if(n_d == 3)
			{
				if(save_maul == 0)
				{
					s = "㢸�";
					if(n_s == 0)
					{
						if(n_c == 0)
						{
							drawString(g,"���ү,��������", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"���㱣������", sX + 3, sY + 105, 0x10 | 0x4);
							drawString(g,"���ǾͲ��õ�����", sX + 3, sY + 118, 0x10 | 0x4);
						} else
						if(n_c == 1)
						{
							drawString(g,"Ҳ��֪����ô��", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"��Ʀ��åԽ��Խ��", sX + 3, sY + 105, 0x10 | 0x4);
						} else
						if(n_c == 2)
						{
							drawString(g,"��ѽ,��������˵", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"���˫�����", sX + 3, sY + 105, 0x10 | 0x4);
							drawString(g,"����Ҫ�����", sX + 3, sY + 118, 0x10 | 0x4);
						} else
						if(n_c == 3)
						{
							drawString(g,"�����������ʲô", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"��Ҳ����,��Ҫɱ��", sX + 3, sY + 105, 0x10 | 0x4);
							drawString(g,"��Щǿ����Ʀ", sX + 3, sY + 118, 0x10 | 0x4);
						} else
						if(n_c == 4)
						{
							drawString(g,"���ү,��Ҫ�ǲ���", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"�����ǿ����Ʀ", sX + 3, sY + 105, 0x10 | 0x4);
							drawString(g,"�����������Ǵ��", sX + 3, sY + 118, 0x10 | 0x4);
						} else
						if(n_c == 5)
						{
							drawString(g,"����Ҫ����һ���ѵ", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"�����Ǻúü�ס...", sX + 3, sY + 105, 0x10 | 0x4);
						} else
						if(n_c == 6)
						{
							drawString(g,"����", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"�峤���ڵ�������", sX + 3, sY + 105, 0x10 | 0x4);
						}
					} else
					if(n_s == 1)
					{
						drawString(g,"�峤������Ⱦư�", sX + 3, sY + 105, 0x10 | 0x4);
					} else
					if(n_s == 2)
					{
						drawString(g,"��ȥ����صĻ�,", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"�����߾Ϳ�����", sX + 3, sY + 105, 0x10 | 0x4);
					} else
					if(n_s == 3)
					{
						drawString(g,"��ڤ��λ��", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"����ص��ҷ�.", sX + 3, sY + 105, 0x10 | 0x4);
					} else
					if(n_s == 4)
					{
						drawString(g,"���ү,", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"��������ص���", sX + 3, sY + 105, 0x10 | 0x4);
						drawString(g,"���Ƕ���˵��", sX + 3, sY + 105, 0x10 | 0x4);
					} else
					{
						drawString(g,"˵������", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"��˵�����ر���", sX + 3, sY + 105, 0x10 | 0x4);
						drawString(g,"ɽ�����кܶ����", sX + 3, sY + 118, 0x10 | 0x4);
					}
				} else
				if(save_maul == 1)
				{
					s = "ţ��";
					if(n_s == 1)
					{
						drawString(g,"��������������", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"���Ǵ�Ĵ峤,", sX + 3, sY + 105, 0x10 | 0x4);
						drawString(g,"����������", sX + 3, sY + 118, 0x10 | 0x4);
					} else
					if(n_s == 2)
					{
						drawString(g,"���ǿ���,����", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"�����Ҷ�����˯��", sX + 3, sY + 105, 0x10 | 0x4);
					} else
					if(n_s == 3)
					{
						drawString(g,"��ڤ��λ��", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"����ص��ҷ�.", sX + 3, sY + 105, 0x10 | 0x4);
					} else
					if(n_s == 4)
					{
						drawString(g,"��ţ��.", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"����������ɱ����", sX + 3, sY + 105, 0x10 | 0x4);
					} else
					{
						drawString(g,"��˵�����ر���", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"�����д��������", sX + 3, sY + 105, 0x10 | 0x4);
					}
				} else
				if(save_maul == 2)
				{
					s = "����";
					drawString(g,"�Һ�æ,��������.", sX + 3, sY + 105, 0x10 | 0x4);
				}
			} else
			if(n_d == 4)
			{
				if(save_maul == 0)
				{
					s = "�峤";
					if(n_s == 1)
					{
						if(n_c == 0)
						{
							drawString(g,"����..����..", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"36����72�������", sX + 3, sY + 105, 0x10 | 0x4);
						} else
						if(n_c == 1)
						{
							drawString(g,"����,ʲô���ǵ���", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"���Ϻ�Ϳ�˰�.", sX + 3, sY + 105, 0x10 | 0x4);
						} else
						if(n_c == 2)
						{
							drawString(g,"�����������", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"����������ת��", sX + 3, sY + 105, 0x10 | 0x4);
							drawString(g,"ע��Ҫ�͵�ħ�����", sX + 3, sY + 118, 0x10 | 0x4);
						} else
						if(n_c == 3)
						{
							drawString(g,"ʲô��ʲôѽ", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"�峤,�㲻������", sX + 3, sY + 105, 0x10 | 0x4);
							drawString(g,"������?", sX + 3, sY + 118, 0x10 | 0x4);
						} else
						if(n_c == 4)
						{
							drawString(g,"����,����������", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"�����˹���", sX + 3, sY + 105, 0x10 | 0x4);
							drawString(g,"������ȥ����", sX + 3, sY + 118, 0x10 | 0x4);
						} else
						if(n_c == 5)
						{
							drawString(g,"�����ȥ�����", sX + 3, sY + 105, 0x10 | 0x4);
						}
					} else
					{
						drawString(g,"�㻹Ϲת��ɶ", sX + 3, sY + 105, 0x10 | 0x4);
					}
				} else
				if(save_maul == 1)
				{
					s = "�Ի�";
					if(n_s == 2)
					{
						if(n_c == 0)
						{
							drawString(g,"��..��..��..", sX + 3, sY + 105, 0x10 | 0x4);
						} else
						if(n_c == 1)
						{
							drawString(g,"��λС��Ϊ������", sX + 3, sY + 105, 0x10 | 0x4);
						} else
						if(n_c == 2)
						{
							drawString(g,"��..", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"�ҵĺ���..����.", sX + 3, sY + 105, 0x10 | 0x4);
							drawString(g,"��ǿ��ץ����", sX + 3, sY + 118, 0x10 | 0x4);
						} else
						if(n_c == 3)
						{
							drawString(g,"ǿ��?", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"������ǿ��?", sX + 3, sY + 105, 0x10 | 0x4);
						} else
						if(n_c == 4)
						{
							drawString(g,"��ȥ�Ⱦ���", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"�����������", sX + 3, sY + 105, 0x10 | 0x4);
							drawString(g,"����ڤ��ȥ��..", sX + 3, sY + 118, 0x10 | 0x4);
						} else
						if(n_c == 5)
						{
							drawString(g,"����,", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"��ȥ���㺢��", sX + 3, sY + 105, 0x10 | 0x4);
							drawString(g,"�Ȼ���!", sX + 3, sY + 118, 0x10 | 0x4);
						} else
						if(n_c == 6)
						{
							drawString(g,"���?�ѵ������Ԥ", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"���е��������·�", sX + 3, sY + 105, 0x10 | 0x4);
							drawString(g,"�峤˵�Ķ������?", sX + 3, sY + 118, 0x10 | 0x4);
						}
					} else
					if(n_s == 4)
					{
						if(n_c == 0)
						{
							drawString(g,"�ú����������", sX + 3, sY + 105, 0x10 | 0x4);
						} else
						if(n_c == 1)
						{
							drawString(g,"���ѳ���������..", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"���㺢�����Ѿ�", sX + 3, sY + 105, 0x10 | 0x4);
							drawString(g,"�Բ���..", sX + 3, sY + 118, 0x10 | 0x4);
						} else
						if(n_c == 2)
						{
							drawString(g,"ʲô���ǾͲ���", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"�峤����ƭ�˵�", sX + 3, sY + 105, 0x10 | 0x4);
							drawString(g,"��������Ǿ���", sX + 3, sY + 118, 0x10 | 0x4);
						} else
						if(n_c == 3)
						{
							drawString(g,"�һ�������Щ", sX + 3, sY + 92, 0x10 | 0x4);
							drawString(g,"�����,�ұ�֤", sX + 3, sY + 105, 0x10 | 0x4);
						}
					} else
					{
						drawString(g,"��...��...", sX + 3, sY + 105, 0x10 | 0x4);
					}
				} else
				if(save_maul == 2)
				{
					s = "���";
					if(n_s == 1)
					{
						drawString(g,"�����Ĵ峤", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"��������", sX + 3, sY + 105, 0x10 | 0x4);
					} else
					if(n_s == 2)
					{
						drawString(g,"����г����Ե�", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"���ֳ�û,", sX + 3, sY + 105, 0x10 | 0x4);
						drawString(g,"ר��С������", sX + 3, sY + 118, 0x10 | 0x4);
					} else
					if(n_s == 3)
					{
						drawString(g,"��Щǿ�����ۼ�", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"������صĶ�����", sX + 3, sY + 105, 0x10 | 0x4);
						drawString(g,"��ڤ����", sX + 3, sY + 118, 0x10 | 0x4);
					} else
					if(n_s == 4)
					{
						drawString(g,"����?��ڤ����", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"�������������", sX + 3, sY + 105, 0x10 | 0x4);
						drawString(g,"���������ǵľ���", sX + 3, sY + 118, 0x10 | 0x4);
					} else
					{
						drawString(g,"ʲô��ӡ,�ҿɲ�", sX + 3, sY + 92, 0x10 | 0x4);
						drawString(g,"֪��,����������", sX + 3, sY + 105, 0x10 | 0x4);
						drawString(g,"���и������ɽ��", sX + 3, sY + 118, 0x10 | 0x4);
					}
				}
			} else
			if(n_d == 5)
			{
				s = "����";
				if(n_c == 0)
				{
					drawString(g,"��С�����డ,��Ȼ", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"�ܹ����˵���������", sX + 3, sY + 105, 0x10 | 0x4);
					drawString(g,"�������������ͷ��", sX + 3, sY + 118, 0x10 | 0x4);
				} else
				if(n_c == 1)
				{
					drawString(g,"������Ǹ�ʳ���Ե�", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"������.������ɱ���", sX + 3, sY + 105, 0x10 | 0x4);
				} else
				if(n_c == 2)
				{
					drawString(g,"������,��������", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"��ʬ��,˵�����", sX + 3, sY + 105, 0x10 | 0x4);
					drawString(g,"��ڵ�", sX + 3, sY + 118, 0x10 | 0x4);
				} else
				if(n_c == 3)
				{
					drawString(g,"����,�㻹�е㱾��", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"�����˶�����,������", sX + 3, sY + 105, 0x10 | 0x4);
					drawString(g,"����һ�������Ŀֲ�", sX + 3, sY + 118, 0x10 | 0x4);
				} else
				if(n_c == 4)
				{
					drawString(g,"����!��,��Ȼ��...", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"ħ�Ƿ�ӡ���ܾ�...", sX + 3, sY + 105, 0x10 | 0x4);
					drawString(g,"ħѨ...", sX + 3, sY + 118, 0x10 | 0x4);
				} else
				if(n_c == 5)
				{
					drawString(g,"��ӡ?", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"��ӡ��������?", sX + 3, sY + 105, 0x10 | 0x4);
				} else
				if(n_c == 6)
				{
					drawString(g,"ħ..��..��..��..", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"ħ�ǻ�������", sX + 3, sY + 105, 0x10 | 0x4);
				} else
				if(n_c == 7)
				{
					drawString(g,"����˭", sX + 3, sY + 105, 0x10 | 0x4);
				} else
				if(n_c == 8)
				{
					drawString(g,"����,���μ���", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"���ǵ���ת��", sX + 3, sY + 105, 0x10 | 0x4);
					drawString(g,"��ħ������", sX + 3, sY + 118, 0x10 | 0x4);
				} else
				if(n_c == 9)
				{
					drawString(g,"����ǵ�ħ��?", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"�������ħ��", sX + 3, sY + 105, 0x10 | 0x4);
					drawString(g,"��ӡ,�ٻ�72����.", sX + 3, sY + 118, 0x10 | 0x4);
				} else
				if(n_c == 10)
				{
					drawString(g,"��������,���޷�����", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"һ����ӡ����", sX + 3, sY + 105, 0x10 | 0x4);
					drawString(g,"�������ͻΆ��", sX + 3, sY + 118, 0x10 | 0x4);
				} else
				if(n_c == 11)
				{
					drawString(g,"ʲô?!", sX + 3, sY + 105, 0x10 | 0x4);
				} else
				if(n_c == 12)
				{
					drawString(g,"�����ȸ����,", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"�..��..��..", sX + 3, sY + 105, 0x10 | 0x4);
				}
				if(n_c > 5)
				{
					s = "����";
					g.setClip(sX + 70 + 16, sY + 25, 19, 33);
					drawImage(g,bmo_img[2], sX + 70 + 16, sY + 25, 0x10 | 0x4);
				}
			} else
			if(n_d == 6)
			{
				s = "����";
				if(n_c == 0)
				{
					drawString(g,"��������,����Ϊ�ܹ�", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"֪Ȥһ����", sX + 3, sY + 105, 0x10 | 0x4);
				} else
				if(n_c == 1)
				{
					drawString(g,"��,�㲻��������?", sX + 3, sY + 105, 0x10 | 0x4);
				} else
				if(n_c == 2)
				{
					drawString(g,"�����Ϲ�����", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"ֻҪ�����ٻ�", sX + 3, sY + 105, 0x10 | 0x4);
					drawString(g,"���ܽ�ħ����������", sX + 3, sY + 118, 0x10 | 0x4);
				} else
				if(n_c == 3)
				{
					drawString(g,"����������Ե�����", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"�����޾��ĺڰ�ȥ��", sX + 3, sY + 105, 0x10 | 0x4);
				}
			} else
			if(n_d == 7)
			{
				s = "����";
				if(n_c == 0)
				{
					drawString(g,"��,��ô������?", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"������������?", sX + 3, sY + 105, 0x10 | 0x4);
					drawString(g,"����..", sX + 3, sY + 118, 0x10 | 0x4);
				} else
				if(n_c == 1)
				{
					drawString(g,"���ٽ����������", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"���Ѿ����Ҵ���", sX + 3, sY + 105, 0x10 | 0x4);
				} else
				if(n_c == 2)
				{
					drawString(g,"�޴�����ѽ,����", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"�������˵Ŀ���", sX + 3, sY + 105, 0x10 | 0x4);
					drawString(g,"�Ƕ�ô��Сѽ", sX + 3, sY + 118, 0x10 | 0x4);
				} else
				if(n_c == 3)
				{
					drawString(g,"��˵�ϻ�!", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"����!", sX + 3, sY + 105, 0x10 | 0x4);
				} else
				if(n_c == 4)
				{
					drawString(g,"   --;", sX + 3, sY + 105, 0x10 | 0x4);
				} else
				if(n_c == 5)
				{
					drawString(g,"��..��.. ����", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"ħ�Ƿ�ӡӦ��", sX + 3, sY + 105, 0x10 | 0x4);
					drawString(g,"��Զ������", sX + 3, sY + 118, 0x10 | 0x4);
				} else
				if(n_c == 6)
				{
					drawString(g,"����,�����", sX + 3, sY + 92, 0x10 | 0x4);
					drawString(g,"�ҵ�ħ������", sX + 3, sY + 105, 0x10 | 0x4);
					drawString(g,"����ʹ�ʧ��", sX + 3, sY + 118, 0x10 | 0x4);
				}
			}
			
			if(n_c % 2 == 1)
			{
				g.setColor(0xFFFFFF);
				g.drawRect(sX + 1, sY + 75, 28, 15);
				g.setColor(0x4669BE);
				g.fillRect(sX + 2, sY + 76, 27, 16);
				g.setColor(0xFFFFFF);
				drawString(g,"����", sX + 3, sY + 77, 0x10 | 0x4);
			} else
			{
				g.setColor(0xFFFFFF);
				g.drawRect(sX + 91, sY + 75, 28, 15);
				g.setColor(0x4669BE);
				g.fillRect(sX + 92, sY + 76, 27, 16);
				g.setColor(0xFFFFFF);
				drawString(g,s, sX + 93, sY + 77, 0x10 | 0x4);
			}
			g.setClip(0, 165, getWidth(), 12);
			g.setColor(0);
			drawString(g,"����", getWidth() - 1, 177, 0x20 | 0x8);
			drawString(g,"����", getWidth() - 3, 177, 0x20 | 0x8);
			drawString(g,"����", getWidth() - 2, 176, 0x20 | 0x8);
			drawString(g,"����", getWidth() - 2, 178, 0x20 | 0x8);
			g.setColor(0xFFFFFF);
			drawString(g,"����", getWidth() - 2, 177, 0x20 | 0x8);
		} else
		if(c_a != 1)
		{

			g.setClip(0, 165, getWidth(), 12);
			g.setColor(0);
			drawString(g,"�˵�", 1, 177, 0x20 | 0x4);
			drawString(g,"�˵�", 3, 177, 0x20 | 0x4);
			drawString(g,"�˵�", 2, 176, 0x20 | 0x4);
			drawString(g,"�˵�", 2, 178, 0x20 | 0x4);
			g.setColor(0xFFFFFF);
			drawString(g,"�˵�", 2, 177, 0x20 | 0x4);
		}
		g.setClip(0, 0, 176, 16);
		drawImage(g,m_img[3], sX + 60, 0, 0x10 | 0x1);
		g.setClip(0, sY + 10 + 92 + 38, 176, 29);
		drawImage(g,m_img[4], sX + 60, sY + 10 + 92 + 38, 0x10 | 0x1);
		g.setClip(sX + 21, sY + 145, 36*m_h/(7 + m_l * 14), 9);
		drawImage(g,m_img[2], sX + 21, sY + 145, 0x10 | 0x4);
		g.setClip(sX + 21, sY + 158, 36*m_e/100, 9);
		drawImage(g,m_img[2], sX - 15, sY + 158, 0x10 | 0x4);
		g.setClip(sX + 101, sY + 30 + 92 + 38, 9, 7);
		if(m_i[0] != 0)
		{
			drawImageClip(g,m_img[1],0,12,sX + 74,sY + 151);
		}
		if(m_i[1] != 0)
		{
			drawImageClip(g,m_img[1],1,12,sX + 102,sY + 151);
		}
		if(m_i[2] != 0)
		{
			drawImageClip(g,m_img[1],2,12,sX + 129,sY + 151);
		}		
	}

	private void viewNpc(Graphics g)
	{
		for(int i = 0; i < 11; i++)
		{
			for(int j = 0; j < 5; j++)
				if(i % 2 == 0)
					try
					{
						byte byte0 = n_npc[stX + i / 2 + j][(stY - i / 2) + j];
						if(byte0 == 5 || byte0 == 6 || byte0 == 7)
						{
							drawImage(g,n_img[0], (((sX + 15) - 34 - 10) + 68 * j) - (spX + spY) * 16, (((sY + 24) - 41) + 19 * i) - (spX - spY) * 9, 0x20 | 0x1);
							drawImage(g,n_img[byte0 - 4], (((sX + 5) - 34 - 10) + 68 * j) - (spX + spY) * 16, (((sY + 10) - 41) + 19 * i) - (spX - spY) * 9, 0x20 | 0x1);
						} else
						if(byte0 != 99)
						{
 							drawImageClip(g,n_img[4],(int)byte0,32,(((sX + 84) - 34 - 68 - 10) + 68 * j) - (spX + spY) * 16-16,(((sY + 74) - 57 - 7 - 41) + 19 * i) - (spX - spY) * 9 -32);
						}
					}
					catch(ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) { }
				else
				if(i % 2 == 1)
					try
					{
						byte byte1 = n_npc[stX + (i + 1) / 2 + j][(stY - i / 2) + j];
						if(byte1 == 5 || byte1 == 6 || byte1 == 7)
						{
							
							
							drawImage(g,n_img[0], (((sX + 15) - 10) + 68 * j) - (spX + spY) * 16, (((sY + 24) - 41) + 19 * i) - (spX - spY) * 9, 0x20 | 0x1);
							
							
							drawImage(g,n_img[byte1 - 4], (((sX + 5) - 10) + 68 * j) - (spX + spY) * 16, (((sY + 10) - 41) + 19 * i) - (spX - spY) * 9, 0x20 | 0x1);
						} else
						if(byte1 != 99)
						{
							drawImageClip(g,n_img[4],(int)byte1,32,(((sX + 84) - 68 - 10) + 68 * j) - (spX + spY) * 16-16, (((sY + 74) - 57 - 7 - 41) + 19 * i) - (spX - spY) * 9 -32);
						}
					}
					catch(ArrayIndexOutOfBoundsException arrayindexoutofboundsexception1) { }
		}

	}

	private void viewGateway(Graphics g, byte byte0)
	{
		if(byte0 == 2)
		{
			for(int i = 0; i < 11; i++)
			{
				for(int k = 0; k < 5; k++)
					if(i % 2 == 0)
						try
						{
							if(stX + i / 2 + k == 13 && (stY - i / 2) + k == 3)
							{
								drawImage(g,gr_img[6], (((sX + 84) - 34 - 68 - 10) + 68 * k) - (spX + spY) * 16, (((sY + 74) - 57 - 7 - 41) + 19 * i) - (spX - spY) * 9, 0x20 | 0x1);
							} else
							if(stX + i / 2 + k == 3 && (stY - i / 2) + k == 13)
							{
								drawImage(g,gr_img[7], (((sX + 84) - 34 - 68 - 10) + 68 * k) - (spX + spY) * 16, (((sY + 74) - 57 - 7 - 41) + 19 * i) - (spX - spY) * 9, 0x20 | 0x1);
							}
						}
						catch(ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) { }
					else
					if(i % 2 == 1)
						try
						{
							if(stX + (i + 1) / 2 + k == 13 && (stY - i / 2) + k == 3)
							{
								drawImage(g,gr_img[6], (((sX + 84) - 68 - 10) + 68 * k) - (spX + spY) * 16, (((sY + 74) - 57 - 7 - 41) + 19 * i) - (spX - spY) * 9, 0x20 | 0x1);
							} else
							if(stX + (i + 1) / 2 + k == 3 && (stY - i / 2) + k == 13)
							{
								drawImage(g,gr_img[7], (((sX + 84) - 68 - 10) + 68 * k) - (spX + spY) * 16, (((sY + 74) - 57 - 7 - 41) + 19 * i) - (spX - spY) * 9, 0x20 | 0x1);
							}
						}
						catch(ArrayIndexOutOfBoundsException arrayindexoutofboundsexception1) { }

			}

		} else
		{
			for(int j = 0; j < 11; j++)
			{
				for(int l = 0; l < 5; l++)
					if(j % 2 == 0)
						try
						{
							if(stX + j / 2 + l == 2 && (stY - j / 2) + l == 8)
								if(byte0 == 3)
								{
									drawImage(g,gr_img[6], (((sX + 84) - 34 - 68 - 10) + 68 * l) - (spX + spY) * 16, (((sY + 74) - 57 - 7 - 41) + 19 * j) - (spX - spY) * 9, 0x20 | 0x1);
								} else
								if(byte0 == 4)
								{
									drawImage(g,gr_img[7], (((sX + 84) - 34 - 68 - 10) + 68 * l) - (spX + spY) * 16, (((sY + 74) - 57 - 7 - 41) + 19 * j) - (spX - spY) * 9, 0x20 | 0x1);
								}
						}
						catch(ArrayIndexOutOfBoundsException arrayindexoutofboundsexception2) { }
					else
					if(j % 2 == 1)
						try
						{
							if(stX + (j + 1) / 2 + l == 2 && (stY - j / 2) + l == 8)
								if(byte0 == 3)
								{
									drawImage(g,gr_img[6], (((sX + 84) - 68 - 10) + 68 * l) - (spX + spY) * 16, (((sY + 74) - 57 - 7 - 41) + 19 * j) - (spX - spY) * 9, 0x20 | 0x1);
								} else
								if(byte0 == 4)
								{
									drawImage(g,gr_img[7], (((sX + 84) - 68 - 10) + 68 * l) - (spX + spY) * 16, (((sY + 74) - 57 - 7 - 41) + 19 * j) - (spX - spY) * 9, 0x20 | 0x1);
								}
						}
						catch(ArrayIndexOutOfBoundsException arrayindexoutofboundsexception3) { }
			}

		}
	}

	private void movingMap(Graphics g)
	{
		g.setClip(0, 16, 176, 162);
		drawBack(g);
		for(int i = 0; i < 11; i++)
		{
			for(int j = 0; j < 5; j++)
				if(i % 2 == 0)
					try
					{
						byte byte0 = now_map[stX + i / 2 + j][(stY - i / 2) + j];
						drawImage(g,gr_img[byte0], (((sX + 84) - 34 - 68 - 10) + 68 * j) - (spX + spY) * 16, (((((sY + 74) - 57 - 20) + 19) - 45) + 19 * i) - (spX - spY) * 9, 0x20 | 0x1);
						drawImage(g,gr_img[byte0], (((sX + 84) - 17 - 34 - 68 - 10) + 68 * j) - (spX + spY) * 16, (((((sY + 74) - 57) + 19) - 10 - 45) + 19 * i) - (spX - spY) * 9, 0x20 | 0x1);
						drawImage(g,gr_img[byte0], (((sX + 84 + 17) - 34 - 68 - 10) + 68 * j) - (spX + spY) * 16, (((((sY + 74) - 57) + 19) - 10 - 45) + 19 * i) - (spX - spY) * 9, 0x20 | 0x1);
						drawImage(g,gr_img[byte0], (((sX + 84) - 34 - 68 - 10) + 68 * j) - (spX + spY) * 16, (((((sY + 74) - 57) + 19) - 45) + 19 * i) - (spX - spY) * 9, 0x20 | 0x1);
					}
					catch(Exception exception) { }
				else
				if(i % 2 == 1)
					try
					{
						byte byte1 = now_map[stX + (i + 1) / 2 + j][(stY - i / 2) + j];
						drawImage(g,gr_img[byte1], (((sX + 84) - 68 - 10) + 68 * j) - (spX + spY) * 16, (((((sY + 74) - 57 - 20) + 19) - 45) + 19 * i) - (spX - spY) * 9, 0x20 | 0x1);
						drawImage(g,gr_img[byte1], (((sX + 84) - 68 - 17 - 10) + 68 * j) - (spX + spY) * 16, (((((sY + 74) - 57) + 19) - 10 - 45) + 19 * i) - (spX - spY) * 9, 0x20 | 0x1);
						drawImage(g,gr_img[byte1], (((((sX + 84) - 68) + 17) - 10) + 68 * j) - (spX + spY) * 16, (((((sY + 74) - 57) + 19) - 10 - 45) + 19 * i) - (spX - spY) * 9, 0x20 | 0x1);
						drawImage(g,gr_img[byte1], (((sX + 84) - 68 - 10) + 68 * j) - (spX + spY) * 16, (((((sY + 74) - 57) + 19) - 45) + 19 * i) - (spX - spY) * 9, 0x20 | 0x1);
					}
					catch(Exception exception1) { }
		}
	}

	private void char_moving(Graphics g)
	{
		if(c_a == 1)
		{
			byte byte0 = 0;
			byte byte1 = 0;
			byte byte2 = 0;
			byte byte3 = 0;
			if(w_p == 0)
			{
				byte0 = -23;
				byte1 = 3;
				byte2 = 2;
				byte3 = -5;
			} else
			if(w_p == 1)
			{
				byte0 = 23;
				byte1 = 3;
				byte2 = -2;
				byte3 = 5;
			} else
			if(w_p == 2)
			{
				byte0 = -15;
				byte1 = -15;
				byte2 = 2;
				byte3 = 0;
			} else
			if(w_p == 3)
			{
				byte0 = 15;
				byte1 = -15;
				byte2 = -2;
				byte3 = 0;
			}
			drawImage(g,c_img[w_p * 2], (sX + 70) - 10, (sY + 61) - 12, 0x2 | 0x1);
			c_a = 2;
		} else
		{
			drawImage(g,c_img[w_p * 2 + w_s], (sX + 70) - 10, (sY + 61)-12, 0x2 | 0x1);
			if(c_a != 3)
				c_a = 0;
			g_p_state = false;
		}
		if(m_e >= 100)
		{
			if(m_l < 99)
			{
				m_l++;
				drawImage(g,c_img[8], (sX + 60) - 10, (sY + 66) - 3, 0x10 | 0x1);
				//if(sound_on)
					//playSound(LEVER_UP, 1);
			}
			m_e -= 100;
			m_h = 7 + m_l * 14;
		}
	}

	private void monster_moving(Graphics g)
	{
		for(int i = 0; i < 8; i++)
			if(mob_d[i].m_a == 1)
			{
				drawImageClip(g,mo_img,mob_d[i].mo_l * 4 + mob_d[i].m_p,32,((((sX + 70) - 10) + (mob_d[i].mX - (stX + 3)) * 34 + (mob_d[i].mY - stY) * 34) - (spX + spY) * 16) + (mob_d[i].m_sX + mob_d[i].m_sY) * 16 - 16,(((sY + 62) - 3 - 5) + (mob_d[i].mX - (stX + 3)) * 16) - (mob_d[i].mY - stY) * 16 - (spX - spY) * 9 - (mob_d[i].m_sY - mob_d[i].m_sX) * 9 - 16) ;
			} else
			{
 				drawImageClip(g,mo_img,mob_d[i].mo_l * 4 + mob_d[i].m_p,32,((((sX + 70) - 10) + (mob_d[i].mX - (stX + 3)) * 34 + (mob_d[i].mY - stY) * 34) - (spX + spY) * 16) + (mob_d[i].m_sX + mob_d[i].m_sY) * 16 - 16,(((sY + 62) - 5) + (mob_d[i].mX - (stX + 3)) * 16) - (mob_d[i].mY - stY) * 16 - (spX - spY) * 9 - (mob_d[i].m_sY - mob_d[i].m_sX) * 9 - 16);
			}

	}
	private void monster_attack(Graphics g, boolean fire)
	{
		if (fire)
			drawImage(g,bmo_img[2],(sX + 79) - 21, sY + 53, 0x2 | 0x1);
		else
			drawImage(g,bmo_img[4], (sX + 68) - 10, sY + 70, 0x20 | 0x1);
	}
	private void monster_moving(Graphics g, byte byte0)
	{
		int x=0;
		if(b_m_a){
			b_m_a = false;
			x=0;
		}else{
			b_m_a = true;
			x=1;
		}
		if(byte0 == 1 || byte0 == 2)
		{
			drawImage(g,bmo_img[0], (((sX + 70 + 16) - 10) + (mob_d[0].mX - (stX + 3)) * 34 + (mob_d[0].mY - stY) * 34) - (spX + spY) * 16, (((sY + 62) - 11) + (mob_d[0].mX - (stX + 3)) * 16) - (mob_d[0].mY - stY) * 16 - (spX - spY) * 9, 0x2 | 0x1);
			drawImageClip(g,bmo_img[1],1-x,10,(((sX + 70 + 16) - 10 + 14) + (mob_d[0].mX - (stX + 3)) * 34 + (mob_d[0].mY - stY) * 34) - (spX + spY) * 16,(((sY + 62) - 11 - 24) + (mob_d[0].mX - (stX + 3)) * 16) - (mob_d[0].mY - stY) * 16 - (spX - spY) * 9);							
			if(mob_d[0].m_a == 1){
				//drawImage(g,bmo_img[2],(sX + 79) - 21, sY + 53, 0x2 | 0x1);
				drawImageClip(g,bmo_img[1],x,10,(sX + 79) - 12, sY + 62);
				drawImageClip(g,bmo_img[1],1-x,10,(sX + 72) - 12, sY + 67);
				drawImageClip(g,bmo_img[1],x,10,(sX + 63) - 12, sY + 67);
				drawImageClip(g,bmo_img[1],1-x,10,(sX + 57) - 12, sY + 62);
			}
		} else
		if(byte0 == 3)
		{
			if(mob_d[0].m_a == 1)
			{
				drawImage(g,bmo_img[2], (((sX + 70 + 16 + 2) - 10) + (mob_d[0].mX - (stX + 3)) * 34 + (mob_d[0].mY - stY) * 34) - (spX + spY) * 16, (((sY + 62) - 9 - 9) + (mob_d[0].mX - (stX + 3)) * 16) - (mob_d[0].mY - stY) * 16 - (spX - spY) * 9, 0x2 | 0x1);
				drawImage(g,bmo_img[3], (((((sX + 70 + 16) - 11) + 2) - 10) + (mob_d[0].mX - (stX + 3)) * 34 + (mob_d[0].mY - stY) * 34) - (spX + spY) * 16, (((sY + 62) - 24) + (mob_d[0].mX - (stX + 3)) * 16) - (mob_d[0].mY - stY) * 16 - (spX - spY) * 9, 0x2 | 0x1);
				if(b_m_a)
					drawImage(g,bmo_img[4], (sX + 68) - 10, sY + 70, 0x20 | 0x1);
				else{
					//drawImage(g,bmo_img[2],(sX + 79) - 21, sY + 53, 0x2 | 0x1);
					drawImageClip(g,bmo_img[1],x,10,(sX + 79) - 12, sY + 62);
					drawImageClip(g,bmo_img[1],1-x,10,(sX + 72) - 12, sY + 67);
					drawImageClip(g,bmo_img[1],x,10,(sX + 63) - 12, sY + 67);
					drawImageClip(g,bmo_img[1],1-x,10,(sX + 57) - 12, sY + 62);
				}
			} else
			{
				drawImage(g,bmo_img[2], (((sX + 70 + 16) - 10) + (mob_d[0].mX - (stX + 3)) * 34 + (mob_d[0].mY - stY) * 34) - (spX + spY) * 16, (((sY + 62) - 9 - 9) + (mob_d[0].mX - (stX + 3)) * 16) - (mob_d[0].mY - stY) * 16 - (spX - spY) * 9, 0x2 | 0x1);
			}
		} else
		if(byte0 == 4)
		{
			byte byte1 = 0;
			byte byte2 = 0;
			byte byte3 = 0;
			byte byte4 = 0;
			if(mob_d[0].m_p == 0)
			{
				byte1 = -23;
				byte2 = 3;
				byte3 = 2;
				byte4 = -5;
			} else
			if(mob_d[0].m_p == 1)
			{
				byte1 = 23;
				byte2 = 3;
				byte3 = -2;
				byte4 = 5;
			} else
			if(mob_d[0].m_p == 2)
			{
				byte1 = -15;
				byte2 = -15;
				byte3 = 2;
				byte4 = 0;
			} else
			if(mob_d[0].m_p == 3)
			{
				byte1 = 15;
				byte2 = -15;
				byte3 = -2;
				byte4 = 0;
			}
			if(mob_d[0].m_a == 1)
			{
				drawImage(g,c_img[mob_d[0].m_p * 4 + 2], ((((sX + 70) - 10) + (mob_d[0].mX - (stX + 3)) * 34 + (mob_d[0].mY - stY) * 34) - (spX + spY) * 16) + (mob_d[0].m_sX + mob_d[0].m_sY) * 16, (((sY + 61) - 2) + (mob_d[0].mX - (stX + 3)) * 16) - (mob_d[0].mY - stY) * 16 - (spX - spY) * 9 - (mob_d[0].m_sY - mob_d[0].m_sX) * 9, 0x2 | 0x1);
			} else
			{
				drawImage(g,c_img[mob_d[0].m_p * 4], ((((sX + 70) - 10) + (mob_d[0].mX - (stX + 3)) * 34 + (mob_d[0].mY - stY) * 34) - (spX + spY) * 16) + (mob_d[0].m_sX + mob_d[0].m_sY) * 16, (sY + 62 + (mob_d[0].mX - (stX + 3)) * 16) - (mob_d[0].mY - stY) * 16 - (spX - spY) * 9 - (mob_d[0].m_sY - mob_d[0].m_sX) * 9, 0x20 | 0x1);
				drawImage(g,c_img[mob_d[0].m_p * 4 + 1 + m_w_s], ((((sX + 70) - 10) + (mob_d[0].mX - (stX + 3)) * 34 + (mob_d[0].mY - stY) * 34) - (spX + spY) * 16) + (mob_d[0].m_sX + mob_d[0].m_sY) * 16, (sY + 62 + (mob_d[0].mX - (stX + 3)) * 16) - (mob_d[0].mY - stY) * 16 - (spX - spY) * 9 - (mob_d[0].m_sY - mob_d[0].m_sX) * 9, 0x10 | 0x1);
			}
		}
	}
	void minimap_view(Graphics g)
	{
		drawBack(g);
		for(int i = 0; i < 16; i++)
		{
			for(int j = 0; j < 16; j++)
			{
				byte byte0 = now_map[i][j];
				if(byte0 == 0 || byte0 == 1 || byte0 == 2 || byte0 == 5)
				{
					g.setColor(56064);
					g.fillRect(22 + i * 4 + j * 4, (53 + i * 2) - j * 2+45, 8, 1);
					g.fillRect(24 + i * 4 + j * 4, (52 + i * 2) - j * 2+45, 4, 3);
				} else
				if(byte0 != 99)
				{
					g.setColor(37376);
					g.fillRect(22 + i * 4 + j * 4, (53 + i * 2) - j * 2+45, 8, 1);
					g.fillRect(24 + i * 4 + j * 4, (52 + i * 2) - j * 2+45, 4, 3);
				}
			}

		}

		if(g_state == 0)
		{
			g.setColor(0xff9200);
			g.fillRect(38+24, 37+45, 8, 1);
			g.fillRect(40+24, 36+45, 4, 3);
			g.fillRect(67+24, 76+45, 8, 1);
			g.fillRect(69+24, 75+45, 4, 3);
			g.setColor(0xff15ff);
			g.fillRect(62+24, 29+45, 8, 1);
			g.fillRect(64+24, 28+45, 4, 3);
			g.fillRect(67+24, 89+45, 8, 1);
			g.fillRect(69+24, 88+45, 4, 3);
			g.setColor(0xffff00);
			g.fillRect(78+24, 37+45, 8, 1);
			g.fillRect(80+24, 36+45, 4, 3);
			g.fillRect(67+24, 102+45, 8, 1);
			g.fillRect(69+24, 101+45, 4, 3);
			g.setColor(0xff0000);
			g.fillRect(2+24, 76+45, 8, 1);
			g.fillRect(4+24, 75+45, 4, 3);
			g.setColor(56064);
			g.fillRect(2+24, 89+45, 8, 1);
			g.fillRect(4+24, 88+45, 4, 3);
			g.setColor(37376);
			g.fillRect(2+24, 102+45, 8, 1);
			g.fillRect(4+24, 101+45, 4, 3);
			g.setColor(0xffffff);
			if(save_maul == 0)
			{
				drawString(g,"�����", 14+24, 5+45, 0x10 | 0x4);
			} else
			if(save_maul == 1)
			{
				drawString(g,"�����", 14+24, 5+45, 0x10 | 0x4);
			} else
			if(save_maul == 2)
			{
				drawString(g,"������", 14+24, 5+45, 0x10 | 0x4);
			}
			drawString(g,"��ǰλ��", 12+24, 70+45, 0x10 | 0x4);
			drawString(g,"Ұ��", 12+24, 83+45, 0x10 | 0x4);
			drawString(g,"�ϰ���", 12+24, 96+45, 0x10 | 0x4);
			drawString(g,"�ù�", 77+24, 70+45, 0x10 | 0x4);
			drawString(g,"�ӻ���", 77+24, 83+45, 0x10 | 0x4);
			drawString(g,"������", 77+24, 96+45, 0x10 | 0x4);
		} else
		{
			g.setColor(0xff0000);
			g.fillRect(4+24, 96+45, 8, 1);
			g.fillRect(6+24, 95+45, 4, 3);
			g.setColor(56064);
			g.fillRect(4+24, 109+45, 8, 1);
			g.fillRect(6+24, 108+45, 4, 3);
			g.setColor(37376);
			g.fillRect(4+24, 122+45, 8, 1);
			g.fillRect(6+24, 121+45, 4, 3);
			g.setColor(0xffffff);
			drawString(g,"��ǰλ��", 14+24, 90+45, 0x10 | 0x4);
			drawString(g,"Ұ��", 14+24, 103+45, 0x10 | 0x4);
			drawString(g,"�ϰ���", 14+24, 116+45, 0x10 | 0x4);
		}
		g.setClip(0, 0, 128, 160);
		if(g_p_state)
		{
			g.setColor(0xff0000);
			g.fillRect(-2+24 + (stX + 3) * 4 + stY * 4, (53+45 + (stX + 3) * 2) - stY * 2, 8, 1);
			g.fillRect(0+24 + (stX + 3) * 4 + stY * 4, (52+45 + (stX + 3) * 2) - stY * 2, 4, 3);
		}
	}

	private void inventory_view(Graphics g)
	{
		String as[] = {
			"����", "װ��", "����", "����"
		};
		byte byte0 = 0;
		byte byte1 = 0;
		byte byte2 = 0;
		byte byte3 = 0;
		byte byte4 = 0;
		byte byte5 = 0;
		if(m_s[0] == 0)
		{
			byte0 = 3;
			byte1 = 6;
		} else
		if(m_s[0] == 1)
		{
			byte0 = 4;
			byte1 = 8;
		} else
		if(m_s[0] == 2)
		{
			byte0 = 4;
			byte1 = 12;
		} else
		if(m_s[0] == 3)
		{
			byte0 = 6;
			byte1 = 30;
		}
		if(m_s[1] == 1)
		{
			byte2 = 1;
			byte3 = 1;
		} else
		if(m_s[1] == 2)
		{
			byte2 = 2;
			byte3 = 2;
		} else
		if(m_s[1] == 3)
		{
			byte2 = 3;
			byte3 = 3;
		}
		if(m_s[2] == 0)
		{
			byte4 = 2;
			byte5 = 2;
		} else
		if(m_s[2] == 1)
		{
			byte4 = 3;
			byte5 = 6;
		} else
		if(m_s[2] == 2)
		{
			byte4 = 4;
			byte5 = 8;
		}
		drawBack(g);
		g.setColor(0);
		g.drawRect(sX-1, sY + 14, 124, 120);
		g.setColor(0x2870F8);
		g.fillRect(sX+1, sY + 16, 121, 117);
		g.setColor(0x1050B0);
		g.fillRect(sX+6, sY + 41, 73, 72);
		drawBlackshadow(g,0x80B0F8,0x1050B0,122,118,sX,sY+15);
		drawBlackshadow(g,0x184060,0x80B0F8,73,72,sX+5,sY+40);
		if(c_state == 0)
		{
			g.setColor(0);
			drawString(g,as[cho], sX + 59, sY + 10 + 10, 0x10 | 0x1);
			g.setColor(0xffffff);
			drawString(g,as[cho], sX + 60, sY + 11 + 10, 0x10 | 0x1);
			
			drawBlackshadow(g,0x1050B0,0x80B0F8,27,15,sX + 88,sY + 42 + 15 * cho);
			g.setColor(0xffffff);
			drawString(g,as[0], sX + 90, sY + 44, 0x10 | 0x4);
			drawString(g,as[1], sX + 90, sY + 59, 0x10 | 0x4);
			drawString(g,as[2], sX + 90, sY + 74, 0x10 | 0x4);
			drawString(g,as[3], sX + 90, sY + 89, 0x10 | 0x4);
			drawImage(g,st_img[1], sX + 40, sY + 121, 0x10 | 0x1);
			drawString(g,"" + m_m, sX + 52, sY + 118, 0x10 | 0x4);
			if(cho == 0)
			{
				drawImageClip(g,m_img[1],0,12,sX + 11,sY + 46);
				drawImageClip(g,m_img[1],1,12,sX + 36,sY + 46);
				drawImageClip(g,m_img[1],2,12,sX + 61,sY + 46);
				drawImageClip(g,st_img[0],0,15,sX + 10,sY + 68);
				drawImageClip(g,st_img[0],1,15,sX + 35,sY + 68);
				drawImageClip(g,st_img[0],2,15,sX + 60,sY + 68);
				drawImageClip(g,st_img[0],3,15,sX + 10,sY + 92);
				drawImageClip(g,st_img[0],4,15,sX + 35,sY + 92);
				drawImageClip(g,st_img[0],5,15,sX + 60,sY + 92);
			} else
			if(cho == 1)
			{
				drawImageClip(g,st_img[0],m_s[0]+6,15,sX + 34,sY + 43);
				drawImageClip(g,st_img[0],m_s[2],15,sX + 34,sY + 68);
				if(m_s[1] == 0)
				{
					g.setClip(sX + 15, sY + 80 + 10, 50, 16);
					drawString(g,"û��ͷ��", sX + 15, sY + 80 + 10, 0x10 | 0x4);
				} else
				{
					drawImageClip(g,st_img[0],m_s[1]+2,15,sX + 34,sY + 92);
				}
			} else
			if(cho == 2)
			{
				if(m_s[3] == 0)
				{
					drawString(g,"�޼���", sX + 20, sY + 70 + 10, 0x40 | 0x4);
				} else
				if(m_s[3] == 1)
				{
					drawString(g,"��������", sX + 20, sY + 52 + 10, 0x40 | 0x4);
				} else
				if(m_s[3] == 2)
				{
					drawString(g,"��������", sX + 20, sY + 52 + 10, 0x40 | 0x4);
					drawString(g,"��Хɽ��", sX + 20, sY + 70 + 10, 0x40 | 0x4);
				} else
				if(m_s[3] == 3)
				{
					drawString(g,"��������", sX + 20, sY + 52 + 10, 0x40 | 0x4);
					drawString(g,"��Хɽ��", sX + 20, sY + 70 + 10, 0x40 | 0x4);
					drawString(g,"�Ի�����", sX + 20, sY + 88 + 10, 0x40 | 0x4);
				}
			} else
			if(cho == 3)
			{
				drawString(g,"����    " + m_l, sX + 8, sY + 44, 0x10 | 0x4);
				drawString(g,"����", sX + 8, sY + 57, 0x10 | 0x4);
				drawString(g,"" + m_h + "/" + (7 + m_l * 14), sX + 59, sY + 57, 0x10 | 0x1);
				drawString(g,"������   " + byte0 + "-" + byte1, sX + 8, sY + 70, 0x10 | 0x4);
				drawString(g,"������   " + (byte2 + byte4) + "-" + (byte3 + byte5), sX + 8, sY + 83, 0x10 | 0x4);
				drawString(g,"����ֵ    " + m_e + "%", sX + 8, sY + 96, 0x10 | 0x4);
			}
		} else
		if(c_state == 1)
		{
			g.setColor(0);
			drawString(g,as[0], sX + 59, sY + 10 + 10, 0x10 | 0x1);
			g.setColor(0xffffff);
			drawString(g,as[0], sX + 60, sY + 11 + 10, 0x10 | 0x1);
			drawString(g,"��Ϣ", sX + 90, sY + 35 + 10, 0x10 | 0x4);
			drawString(g,"ȡ��", sX + 90, sY + 65 + 10, 0x10 | 0x4);
			drawImage(g,st_img[1], sX + 40, sY + 111 + 10, 0x10 | 0x1);
			drawString(g,"" + m_m, sX + 52, sY + 108 + 10, 0x10 | 0x4);
			if(cho / 3 == 0)
			{
				drawBlackshadow(g,0x80B0F8,0,15,15,sX + 9 + (cho % 3) * 25,sY + 44);
				g.setColor(0xFFFFFF);
				drawString(g,"x" + m_i[cho], sX + 16 + cho * 25, sY + 34 + 13 + 10, 0x10 | 0x4);
			} else
			if(cho / 3 == 1 || cho / 3 == 2 || cho / 3 == 3)
			{
				drawBlackshadow(g,0x80B0F8,0,15,15,sX + 9 + (cho % 3) * 25,sY + 67);
				g.setColor(0xFFFFFF);
				drawString(g,"x" + m_i[cho], sX + 16 + (cho % 3) * 25, sY + 57 + 13 + 10, 0x10 | 0x4);
			} else
			if(cho / 3 > 3)
			{
				drawBlackshadow(g,0x80B0F8,0,15,15,sX + 9 + (cho % 3) * 25,sY + 90);
				g.setColor(0xFFFFFF);
				drawString(g,"x" + m_i[cho], sX + 24, sY + 83 + 5 + 10, 0x10 | 0x4);
			}
			g.setColor(0xFFFFFF);
			drawString(g,"(ȷ��)", sX + 87, sY + 48 + 10, 0x10 | 0x4);
			drawString(g,"(����)", sX + 87, sY + 78 + 10, 0x10 | 0x4);
			if(cho / 3 == 0 || cho / 3 == 1)
			{
				drawImageClip(g,m_img[1],0,12,sX + 11,sY + 46);
				drawImageClip(g,m_img[1],1,12,sX + 36,sY + 46);
				drawImageClip(g,m_img[1],2,12,sX + 61,sY + 46);
				drawImageClip(g,st_img[0],0,15,sX + 10,sY + 68);
				drawImageClip(g,st_img[0],1,15,sX + 35,sY + 68);
				drawImageClip(g,st_img[0],2,15,sX + 60,sY + 68);
				drawImageClip(g,st_img[0],3,15,sX + 10,sY + 92);
				drawImageClip(g,st_img[0],4,15,sX + 35,sY + 92);
				drawImageClip(g,st_img[0],5,15,sX + 60,sY + 92);
			} else
			if(cho / 3 == 2)
			{
				drawImageClip(g,st_img[0],0,15,sX + 10,sY + 44);
				drawImageClip(g,st_img[0],1,15,sX + 35,sY + 44);
				drawImageClip(g,st_img[0],2,15,sX + 60,sY + 44);
				drawImageClip(g,st_img[0],3,15,sX + 10,sY + 68);
				drawImageClip(g,st_img[0],4,15,sX + 35,sY + 68);
				drawImageClip(g,st_img[0],5,15,sX + 60,sY + 68);
				drawImageClip(g,st_img[0],6,15,sX + 10,sY + 92);
				drawImageClip(g,st_img[0],7,15,sX + 35,sY + 92);
				drawImageClip(g,st_img[0],8,15,sX + 60,sY + 92);
			} else
			if(cho / 3 == 3 || cho / 3 == 4)
			{
				drawImageClip(g,st_img[0],3,15,sX + 10,sY + 44);
				drawImageClip(g,st_img[0],4,15,sX + 35,sY + 44);
				drawImageClip(g,st_img[0],5,15,sX + 60,sY + 44);
				drawImageClip(g,st_img[0],6,15,sX + 10,sY + 68);
				drawImageClip(g,st_img[0],7,15,sX + 35,sY + 68);
				drawImageClip(g,st_img[0],8,15,sX + 60,sY + 68);
				drawImageClip(g,st_img[0],9,15,sX + 10,sY + 91);
			}
		} else
		if(c_state == 2)
		{
			g.setColor(0);
			drawString(g,as[1], sX + 59, sY + 10 + 10, 0x10 | 0x1);
			g.setColor(0xFFFFFF);
			drawString(g,"(ȷ��)", sX + 87, sY + 48 + 10, 0x10 | 0x4);
			drawString(g,"(����)", sX + 87, sY + 78 + 10, 0x10 | 0x4);
			g.setColor(0xffffff);
			drawString(g,as[1], sX + 60, sY + 11 + 10, 0x10 | 0x1);
			drawString(g,"����", sX + 90, sY + 35 + 10, 0x10 | 0x4);
			drawString(g,"ȡ��", sX + 90, sY + 65 + 10, 0x10 | 0x4);
			drawBlackshadow(g,0x80B0F8,0,15,15,sX+9,sY + 43+ cho*25);
			g.setColor(0xFFFFFF);
			if(cho == 0)
			{
				String s = null;
				byte byte6 = 0;
				if(m_s[0] == 0)
				{
					s = "��צ";
					byte6 = 1;
				} else
				if(m_s[0] == 1)
				{
					s = "��צ";
					byte6 = 10;
				} else
				if(m_s[0] == 2)
				{
					s = "����";
					byte6 = 18;
				} else
				if(m_s[0] == 3)
				{
					s = "Ǭ��";
					byte6 = 26;
				}
				drawString(g,"������:" + byte0 + "-" + byte1 + " ����:" + byte6, sX + 60, sY + 108 + 10, 0x10 | 0x1);
				drawImageClip(g,st_img[0],m_s[0]+6,15,sX + 10,sY + 43);
				drawString(g,s, sX + 50, sY + 44 + 10, 0x40 | 0x1);
			} else
			{
				drawImageClip(g,st_img[0],m_s[0]+6,15,sX + 34,sY + 43);
			}
			if(cho == 1)
			{
				String s1 = null;
				byte byte7 = 0;
				if(m_s[2] == 0)
				{
					s1 = "������";
					byte7 = 1;
				} else
				if(m_s[2] == 1)
				{
					s1 = "�ֳļ�";
					byte7 = 11;
				} else
				if(m_s[2] == 2)
				{
					s1 = "�����";
					byte7 = 22;
				}
				drawString(g,"������:" + byte4 + "-" + byte5 + " ����:" + byte7, sX + 60, sY + 108 + 10, 0x10 | 0x1);
				drawImageClip(g,st_img[0],m_s[2],15,sX + 10,sY + 68);
				g.setClip(sX + 32, sY + 58 + 10, 35, 15);
				drawString(g,s1, sX + 32, sY + 60 + 10, 0x10 | 0x4);
			} else
			{
				g.setClip(sX + 34, sY + 58 + 10, 15, 17);
				drawImageClip(g,st_img[0],m_s[2],15,sX + 34,sY + 68);
			}
			if(cho == 2)
			{
				drawBlackshadow(g,0x80B0F8,0,15,15,sX+9,sY + 93);
				g.setColor(0xFFFFFF);
				if(m_s[1] == 0)
				{				
					g.setClip(sX + 29, sY + 82 + 10, 48, 15);
					drawString(g,"û��ͷ��", sX + 29, sY + 82 + 10, 0x10 | 0x4);
				} else
				{
					String s2 = null;
					byte byte8 = 0;
					if(m_s[1] == 1)
					{
						s2 = "��ͭ��";
						byte8 = 5;
					} else
					if(m_s[1] == 2)
					{
						s2 = "���ڿ�";
						byte8 = 13;
					} else
					if(m_s[1] == 3)
					{
						s2 = "�Ͻ��";
						byte8 = 20;
					}
					drawString(g,"������:" + byte2 + "-" + byte3 + " ����:" + byte8, sX + 60, sY + 108 + 10, 0x10 | 0x1);
					drawImageClip(g,st_img[0],m_s[1]+2,15,sX + 10,sY + 94);
					g.setClip(sX + 29, sY + 82 + 10, 48, 15);
					drawString(g,s2, sX + 29, sY + 82 + 10, 0x10 | 0x4);
				}
			} else
			if(m_s[1] == 0)
			{
				g.setClip(sX + 29, sY + 82 + 10, 48, 15);
				drawString(g,"û��ͷ��", sX + 29, sY + 82 + 10, 0x10 | 0x4);
			} else
			{
				drawImageClip(g,st_img[0],m_s[1]+2,15,sX + 34,sY + 92);
			}
		}
		g.setClip(0, getHeight() - 15, getWidth(), 15);
		g.setColor(0xFFFFFF);
		drawString(g,"ȷ��", 2, getHeight() - 2, 0x20 | 0x4);
		drawString(g,"����", getWidth() - 2, getHeight() - 2, 0x20 | 0x8);
	}

	private void item_view(Graphics g, byte byte0)
	{
		String s = null;
		String s1 = null;
		String s2 = null;
		String s3 = null;
		g.setClip(0, 0, getWidth(), getHeight());
		g.setColor(0x2870F8);
		g.fillRect(sX+1, sY + 40, 121, 93);
		g.setColor(0x1050B0);
		g.fillRect(sX+10, sY + 41, 103, 72);
		drawBlackshadow(g,0x184060,0x80B0F8,103,72,sX+9,sY + 40);
		g.setColor(0xFFFFFF);
		if(byte0 == 3)
		{
			drawString(g,"Ǯ����", sX + 30, sY + 70, 0x10 | 0x4);
		} else
		{			
			if(cho == 0)
			{
				drawImageClip(g,m_img[1],0,12,sX + 26,sY + 50);
				s = "��ҩ(С)";
				s1 = "����:30 �ָ� ";
				if(byte0 == 1)
					s2 = "1500";
				else
				if(byte0 == 2)
					s2 = "200";
			} else
			if(cho == 1)
			{
				drawImageClip(g,m_img[1],1,12,sX + 26,sY + 50);
				s = "��ҩ(��)";
				s1 = "����:100 �ָ� ";
				if(byte0 == 1)
					s2 = "4000";
				else
				if(byte0 == 2)
					s2 = "2000";
			} else
			if(cho == 2)
			{
				drawImageClip(g,m_img[1],2,12,sX + 26,sY + 50);
				s = "�سǾ�";
				s1 = "�ص���ׯ";
				if(byte0 == 1)
					s2 = "3000";
				else
				if(byte0 == 2)
					s2 = "1000";
			} else
			if(cho == 3)
			{
				drawImageClip(g,st_img[0],0,15,sX + 25,sY + 49);
				s = "������";
				s1 = "������:2-2";
				s3 = "��Ҫ����:1";
				if(byte0 == 1)
					s2 = "4000";
				else
				if(byte0 == 2)
					s2 = "1500";
			} else
			if(cho == 4)
			{
				drawImageClip(g,st_img[0],1,15,sX + 25,sY + 49);
				s = "�ֳļ�";
				s1 = "������:3-6";
				s3 = "��Ҫ����:11";
				if(byte0 == 1)
					s2 = "20000";
				else
				if(byte0 == 2)
					s2 = "8000";
			} else
			if(cho == 5)
			{
				drawImageClip(g,st_img[0],2,15,sX + 25,sY + 49);
				s = "�����";
				s1 = "������:4-8";
				s3 = "��Ҫ����:22";
				if(byte0 == 1)
					s2 = "80000";
				else
				if(byte0 == 2)
					s2 = "30000";
			} else
			if(cho == 6)
			{
				drawImageClip(g,st_img[0],3,15,sX + 25,sY + 49);
				s = "��ͭ��";
				s1 = "������:1-1";
				s3 = "��Ҫ����:5";
				if(byte0 == 1)
					s2 = "10000";
				else
				if(byte0 == 2)
					s2 = "4000";
			} else
			if(cho == 7)
			{
 				drawImageClip(g,st_img[0],4,15,sX + 25,sY + 49);
				s = "���ڿ�";
				s1 = "������:2-2";
				s3 = "��Ҫ����:13";
				if(byte0 == 1)
					s2 = "30000";
				else
				if(byte0 == 2)
					s2 = "13000";
			} else
			if(cho == 8)
			{
				drawImageClip(g,st_img[0],5,15,sX + 25,sY + 49);
				s = "�Ͻ��";
				s1 = "������:3-3";
				s3 = "��Ҫ����:20";
				if(byte0 == 1)
					s2 = "60000";
				else
				if(byte0 == 2)
					s2 = "25000";
			} else
			if(cho == 9)
			{
				drawImageClip(g,st_img[0],6,15,sX + 25,sY + 49);
				s = "��צ";
				s1 = "������:3-6";
				s3 = "��Ҫ����:1";
				if(byte0 == 1)
					s2 = "3000";
				else
				if(byte0 == 2)
					s2 = "1000";
			} else
			if(cho == 10)
			{
 				drawImageClip(g,st_img[0],7,15,sX + 25,sY + 49);
				s = "��צ";
				s1 = "������:4-8";
				s3 = "��Ҫ����:10";
				if(byte0 == 1)
					s2 = "8000";
				else
				if(byte0 == 2)
					s2 = "4000";
			} else
			if(cho == 11)
			{
				drawImageClip(g,st_img[0],8,15,sX + 25,sY + 49);
				s = "����";
				s1 = "������:4-12";
				s3 = "��Ҫ����:18";
				if(byte0 == 1)
					s2 = "40000";
				else
				if(byte0 == 2)
					s2 = "15000";
			} else
			if(cho == 12)
			{
				drawImageClip(g,st_img[0],9,15,sX + 25,sY + 49);
				s = "Ǭ��";
				s1 = "������:6-30";
				s3 = "��Ҫ����:26";
				if(byte0 == 1)
					s2 = "100000";
				else
				if(byte0 == 2)
					s2 = "40000";
			}
			drawBlackshadow(g,0x80B0F8,0,17,17,sX + 24,sX + 56);
			g.setColor(0xFFFFFF);
			if(byte0 == 1 || byte0 == 2)
			{
				
				drawString(g,s2 + "/" + m_m, sX + 32, sY + 116, 0x10 | 0x4);
				drawImage(g,st_img[1], sX + 20, sY + 119, 0x10 | 0x1);
			}
			drawString(g,s, sX + 45, sY + 51, 0x10 | 0x4);
			drawString(g,s1, sX + 25, sY + 72, 0x10 | 0x4);
			if (s3 !=null){
				drawString(g,s3, sX + 25, sY + 93, 0x10 | 0x4);
			}
		}
	}

	private void change_view(Graphics g)
	{
		g.setColor(0x2870F8);
		g.fillRect(sX+1, sY + 40, 121, 93);
		g.setColor(0x1050B0);
		g.fillRect(sX+10, sY + 41, 103, 72);
		drawBlackshadow(g,0x184060,0x80B0F8,103,72,sX+9,sY + 40);
		g.setColor(0xFFFFFF);
		if(cho == 0)
		{
			String s = null;
			byte byte0 = 0;
			byte byte3 = 0;
			byte byte6 = 0;
			if(cho_1 == 0)
			{
				s = "��צ";
				byte0 = 3;
				byte3 = 6;
				byte6 = 1;
			} else
			if(cho_1 == 1)
			{
				s = "��צ";
				byte0 = 4;
				byte3 = 8;
				byte6 = 10;
			} else
			if(cho_1 == 2)
			{
				s = "����";
				byte0 = 4;
				byte3 = 12;
				byte6 = 18;
			} else
			if(cho_1 == 3)
			{
				s = "Ǭ��";
				byte0 = 6;
				byte3 = 30;
				byte6 = 26;
			}
			g.setColor(0x80B0F8);
			drawBlackshadow(g,0x80B0F8,0,17,17,sX + 15 + cho_1 * 25,sY + 42);
			g.setColor(0xFFFFFF);
			drawString(g,"x" + m_i[9 + cho_1], sX + 17 + cho_1 * 25, sY + 64, 4 | 16);
			drawString(g,s +" ������:" + byte0 + "-" + byte3, sX + 60, sY + 79, 0x10 | 0x1);
			drawString(g,"��Ҫ����:" + byte6, sX + 60, sY + 97, 0x10 | 0x1);
			drawImageClip(g,st_img[0],6,15,sX + 15,sY + 42);
			drawImageClip(g,st_img[0],7,15,sX + 40,sY + 42);
			drawImageClip(g,st_img[0],8,15,sX + 65,sY + 42);
			drawImageClip(g,st_img[0],9,15,sX + 90,sY + 42);
		} else
		if(cho == 1)
		{
			String s1 = null;
			byte byte1 = 0;
			byte byte4 = 0;
			byte byte7 = 0;
			if(cho_1 == 0)
			{
				s1 = "������";
				byte1 = 2;
				byte4 = 2;
				byte7 = 1;
			} else
			if(cho_1 == 1)
			{
				s1 = "�ֳļ�";
				byte1 = 3;
				byte4 = 6;
				byte7 = 11;
			} else
			if(cho_1 == 2)
			{
				s1 = "�����";
				byte1 = 4;
				byte4 = 8;
				byte7 = 22;
			}
			drawBlackshadow(g,0x80B0F8,0,17,17,sX + 20 + cho_1 * 30,sY + 42);
			g.setColor(0xFFFFFF);
			drawString(g,"x" + m_i[3 + cho_1], sX + 22 + cho_1 * 30, sY + 64, 4 | 16);
			drawString(g,s1 +" ������:" + byte1 + "-" + byte4, sX + 60, sY + 79, 0x10 | 0x1);
			drawString(g,"��Ҫ����:" + byte7, sX + 60, sY + 97, 0x10 | 0x1);
			drawImageClip(g,st_img[0],0,15,sX + 22,sY + 43);
			drawImageClip(g,st_img[0],1,15,sX + 52,sY + 43);
			drawImageClip(g,st_img[0],2,15,sX + 82,sY + 43);
		} else
		if(cho == 2)
		{
			String s2 = null;
			byte byte2 = 0;
			byte byte5 = 0;
			if(cho_1 == 0)
			{
				s2 = "��ͭ��";
				byte2 = 1;
				byte5 = 5;
			} else
			if(cho_1 == 1)
			{
				s2 = "���ڿ�";
				byte2 = 2;
				byte5 = 13;
			} else
			if(cho_1 == 2)
			{
				s2 = "�Ͻ��";
				byte2 = 3;
				byte5 = 20;
			}
			drawBlackshadow(g,0x80B0F8,0,17,17,sX + 20 + cho_1 * 30,sY + 42);
			g.setColor(0xFFFFFF);
			drawString(g,"x" + m_i[6 + cho_1], sX + 22 + cho_1 * 30, sY + 64, 4 | 16);
			drawString(g,s2 +" ������:" + byte2, sX + 60, sY + 79, 0x10 | 0x1);
			drawString(g,"��Ҫ����:" + byte5, sX + 60, sY + 97, 0x10 | 0x1);
			drawImageClip(g,st_img[0],3,15,sX + 22,sY + 43);
			drawImageClip(g,st_img[0],4,15,sX + 52,sY + 43);
			drawImageClip(g,st_img[0],5,15,sX + 82,sY + 43);
		}
	}

	private void store_view(Graphics g)
	{
		drawBack(g);
		g.setColor(0);
		g.drawRect(sX-1, sY + 14, 124, 120);
		g.setColor(0x2870F8);
		g.fillRect(sX+1, sY + 16, 121, 117);
		g.setColor(0x1050B0);
		g.fillRect(sX+6, sY + 41, 73, 72);
		drawBlackshadow(g,0x80B0F8,0x1050B0,122,118,sX,sY+15);
		drawBlackshadow(g,0x184060,0x80B0F8,73,72,sX+5,sY+40);
		g.setColor(0xFFFFFF);
		drawImage(g,st_img[1], sX + 40, sY + 111 + 10, 0x10 | 0x1);
		drawString(g,"" + m_m, sX + 52, sY + 108 + 10, 0x10 | 0x4);
		if(n_npc[stX + 3 + 1][stY - 1] == 5)
		{
			g.setColor(0);
			drawString(g,"�ù�", sX + 59, sY + 10 + 10, 0x10 | 0x1);
			g.setColor(0xFFFFFF);
			drawString(g,"�ù�", sX + 60, sY + 11 + 10, 0x10 | 0x1);
			if(c_state == 0)
			{
				drawBlackshadow(g,0x1050B0,0x80B0F8,27,15,sX+88,sY+53+15*cho);
				g.setColor(0xffffff);
				drawString(g,"��ӭ����!", sX + 10, sY + 60 + 10, 0x10 | 0x4);
				drawString(g,"ס��", sX + 90, sY + 45 + 10, 0x10 | 0x4);
				drawString(g,"����", sX + 90, sY + 60 + 10, 0x10 | 0x4);
				drawString(g,"ȡ��", sX + 90, sY + 75 + 10, 0x10 | 0x4);
				g.setColor(0xFFFFFF);
			} else
			{
				g.setColor(0x2870F8);
				g.fillRect(sX+1, sY + 40, 121, 93);
				g.setColor(0x1050B0);
				g.fillRect(sX+10, sY + 41, 103, 72);
				drawBlackshadow(g,0x184060,0x80B0F8,103,72,sX+9,sY+40);
				g.setColor(0xFFFFFF);
				if(c_state == 1)
				{
					drawString(g,"ס���ܲ�������", sX + 13, sY + 70, 0x10 | 0x4);
					drawImage(g,st_img[1], sX + 20, sY + 119, 0x10 | 0x1);
					drawString(g,"2500/" + m_m, sX + 32, sY + 116, 0x10 | 0x4);
				} else
				if(c_state == 2)
				{
					drawString(g,"�������" , sX + 13, sY + 50, 0x10 | 0x4);
					drawString(g,"����ʧȥ��������", sX + 13, sY + 70, 0x10 | 0x4);
				} else
				if(c_state == 3)
				{
					drawString(g,"�������", sX + 13, sY + 70, 0x10 | 0x4);
				}
			}
		} else
		if(n_npc[stX + 3 + 1][stY - 1] == 6)
		{
			g.setColor(0);
			drawString(g,"�ӻ���", sX + 59, sY + 10 + 10, 0x10 | 0x1);
			g.setColor(0xFFFFFF);
			drawString(g,"�ӻ���", sX + 60, sY + 11 + 10, 0x10 | 0x1);
			if(c_state == 0)
			{
				drawBlackshadow(g,0x1050B0,0x80B0F8,27,15,sX+88,sY+53+15*cho);
				g.setColor(0xffffff);
				drawString(g,"��ӭ����!", sX + 10, sY + 60 + 10, 0x10 | 0x4);
				drawString(g,"����", sX + 90, sY + 45 + 10, 0x10 | 0x4);
				drawString(g,"����", sX + 90, sY + 60 + 10, 0x10 | 0x4);
				drawString(g,"ȡ��", sX + 90, sY + 75 + 10, 0x10 | 0x4);
			} else
			{
				drawString(g,"��Ϣ", sX + 90, sY + 35 + 10, 0x10 | 0x4);
				drawString(g,"ȡ��", sX + 90, sY + 65 + 10, 0x10 | 0x4);
				drawBlackshadow(g,0x80B0F8,0,15,15,sX+9+cho * 25,sY + 67);
				g.setColor(0xFFFFFF);
				drawString(g,"(ȷ��)", sX + 87, sY + 48 + 10, 0x10 | 0x4);
				drawString(g,"(����)", sX + 87, sY + 78 + 10, 0x10 | 0x4);
				if(c_state != 2 || m_i[0] != 0)
				{
					drawImageClip(g,m_img[1],0,12,sX + 11,sY + 68);
				}
				if(c_state != 2 || m_i[1] != 0)
				{
					drawImageClip(g,m_img[1],1,12,sX + 36,sY + 68);
				}
				if(c_state != 2 || m_i[2] != 0)
				{
					drawImageClip(g,m_img[1],2,12,sX + 61,sY + 68);
				}
			}
			g.setClip(0, getHeight() - 15, getWidth(), 15);
			g.setColor(0xFFFFFF);
		} else
		if(n_npc[stX + 3 + 1][stY - 1] == 7)
		{
			g.setColor(0);
			drawString(g,"������", sX + 59, sY + 10 + 10, 0x10 | 0x1);
			g.setColor(0xFFFFFF);
			drawString(g,"������", sX + 60, sY + 11 + 10, 0x10 | 0x1);
			if(c_state == 0)
			{
				drawBlackshadow(g,0x1050B0,0x80B0F8,27,15,sX+88,sY+53+15*cho);
				g.setColor(0xffffff);
				drawString(g,"��ӭ����!", sX + 10, sY + 60 + 10, 0x10 | 0x4);
				drawString(g,"����", sX + 90, sY + 45 + 10, 0x10 | 0x4);
				drawString(g,"����", sX + 90, sY + 60 + 10, 0x10 | 0x4);
				drawString(g,"ȡ��", sX + 90, sY + 75 + 10, 0x10 | 0x4);
			} else
			{
				drawString(g,"��Ϣ", sX + 90, sY + 35 + 10, 0x10 | 0x4);
				drawString(g,"ȡ��", sX + 90, sY + 65 + 10, 0x10 | 0x4);
				g.setColor(0xFFFFFF);
				drawString(g,"(ȷ��)", sX + 87, sY + 48 + 10, 0x10 | 0x4);
				drawString(g,"(����)", sX + 87, sY + 78 + 10, 0x10 | 0x4);
				if(cho / 3 == 1)
				{
					drawBlackshadow(g,0x80B0F8,0,15,15,sX+9+(cho % 3)*25,sY+44);
					g.setColor(0xFFFFFF);
					if(c_state == 2)
					{
						drawString(g,"x" + m_i[cho], sX + 19 + (cho % 3) * 25, sY + 10 + 34 + 13, 0x10 | 0x4);
					}
				} else
				if(cho / 3 == 2 || cho / 3 == 3)
				{
					drawBlackshadow(g,0x80B0F8,0,15,15,sX+9+(cho % 3)*25,sY+67);
					g.setColor(0xFFFFFF);
					if(c_state == 2)
					{
						drawString(g,"x" + m_i[cho], sX + 19 + (cho % 3) * 25, sY + 10 + 57 + 13, 0x10 | 0x4);
					}
				} else
				if(cho / 3 > 3)
				{
					drawBlackshadow(g,0x80B0F8,0,15,15,sX+9+(cho % 3)*25,sY+90);
					g.setColor(0xFFFFFF);
					if(c_state == 2)
					{
						drawString(g,"x" + m_i[cho], sX + 27, sY + 83 + 5 + 10, 0x10 | 0x4);
					}
				}
				if(cho / 3 == 1 || cho / 3 == 2)
				{
					drawImageClip(g,st_img[0],0,15,sX + 10,sY + 44);
					drawImageClip(g,st_img[0],1,15,sX + 35,sY + 44);
					drawImageClip(g,st_img[0],2,15,sX + 60,sY + 44);
					drawImageClip(g,st_img[0],3,15,sX + 10,sY + 68);
					drawImageClip(g,st_img[0],4,15,sX + 35,sY + 68);
					drawImageClip(g,st_img[0],5,15,sX + 60,sY + 68);
					drawImageClip(g,st_img[0],6,15,sX + 10,sY + 92);
					drawImageClip(g,st_img[0],7,15,sX + 35,sY + 92);
					drawImageClip(g,st_img[0],8,15,sX + 60,sY + 92);

				} else
				if(cho / 3 == 3 || cho / 3 == 4)
				{
					drawImageClip(g,st_img[0],3,15,sX + 10,sY + 44);
					drawImageClip(g,st_img[0],4,15,sX + 35,sY + 44);
					drawImageClip(g,st_img[0],5,15,sX + 60,sY + 44);
					drawImageClip(g,st_img[0],6,15,sX + 10,sY + 68);
					drawImageClip(g,st_img[0],7,15,sX + 35,sY + 68);
					drawImageClip(g,st_img[0],8,15,sX + 60,sY + 68);
					drawImageClip(g,st_img[0],9,15,sX + 10,sY + 92);
				}
			}
		}
		g.setClip(0, getHeight() - 15, getWidth(), 15);
		g.setColor(0xFFFFFF);
		drawString(g,"ȷ��", 2, getHeight() - 2, 0x20 | 0x4);
		drawString(g,"����", getWidth() - 2, getHeight() - 2, 0x20 | 0x8);
	}

	private void menu_pause(Graphics g)
	{
		drawBack(g);
		if(cho == 0)
			g.setColor(0xffffff);
		else
			g.setColor(0x440351);
		drawString(g,"1. ������Ϸ", sX + 33, sY + 35 + 10, 0x10 | 0x4);
		if(cho == 1)
			g.setColor(0xffffff);
		else
			g.setColor(0x440351);
		drawString(g,"2. ��Ϸ����", sX + 33, sY + 50 + 10, 0x10 | 0x4);
        if(cho == 2)
            g.setColor(0xffffff);
        else
            g.setColor(0x440351);
        drawString(g,"3. ���� " + (sound_on ? "����" : "�ر�"), sX + 33, sY + 65 + 10, 0x10 | 0x4);
		if(cho == 3)
			g.setColor(0xffffff);
		else
			g.setColor(0x440351);
		drawString(g,"4. �����˵�", sX + 33, sY + 80 + 10, 0x10 | 0x4);
		if(cho == 4)
			g.setColor(0xffffff);
		else
			g.setColor(0x440351);
		drawString(g,"5. �˳���Ϸ", sX + 33, sY + 95 + 10, 0x10 | 0x4);
		g.setColor(0xFFFFFF);
		drawString(g,"ȷ��", 2, getHeight() - 2, 0x20 | 0x4);
		drawString(g,"ȡ��", getWidth() - 2, getHeight() - 2, 0x20 | 0x8);
	}

	private void char_die(Graphics g)
	{
		g.setColor(0);
		g.drawRect(sX + 29, sY + 64, 62, 25);
		g.setColor(0x1050B0);
		g.fillRect(sX + 30, sY + 65, 61, 24);
		g.setColor(0xffffff);
		drawString(g,"����ʧ��", sX + 60, sY + 70, 0x10 | 0x1);
	}

	private void ending(Graphics g)
	{
		drawBack(g);
		g.setColor(0x440351);
		if(cho == 0)
		{
			drawString(g,"ħ�Ƿ�ӡ���ٶ�", 88, 135 - 5 * cho_1, 0x10 | 0x1);
			drawString(g,"����,72�����ٶ�", 88, 150 - 5 * cho_1, 0x10 | 0x1);
			drawString(g,"�ص��˺ڰ�����", 88, 165 - 5 * cho_1, 0x10 | 0x1);
			drawString(g,"������ޱ��λ", 88, 180 - 5 * cho_1, 0x10 | 0x1);
			drawString(g,"��Ϊ�����峦�ú�", 88, 195 - 5 * cho_1, 0x10 | 0x1);
			drawString(g,"�������²���", 88, 210 - 5 * cho_1, 0x10 | 0x1);
			drawString(g,"����������", 88, 225 - 5 * cho_1, 0x10 | 0x1);
			drawString(g,"���Ĺ���Ϊ����", 88, 240 - 5 * cho_1, 0x10 | 0x1);
			drawString(g,"����", 88, 255 - 5 * cho_1, 0x10 | 0x1);
			drawString(g,"- ��Ϸ���� -", 88, 385 - 5 * cho_1, 0x10 | 0x1);
			g.setColor(0xffffff);
			if (cho_1>55)
				drawString(g,"ȷ��",  2, getHeight() - 2, 0x20 | 0x4);
		} else
		if(cho == 1)
		{
			drawString(g,"- ������ -", 88, 60, 0x10 | 0x1);
			drawString(g,"������", 88, 80, 0x10 | 0x1);
			drawString(g,"- ������� -", 88, 100, 0x10 | 0x1);
			drawString(g,"��", 88, 120, 0x10 | 0x1);
			g.setColor(0xffffff);
				drawString(g,"ȷ��",  2, getHeight() - 2, 0x20 | 0x4);
		} else
		if(cho == 2)
			drawLoading(g);
	}
	void drawString(Graphics g,String s,int x,int y,int a){
		Graphics _tmp1 = g;
		Graphics _tmp2 = g;		
		g.drawString(s, x, y, a);
	}
	void drawImage(Graphics g,Image i,int x,int y,int a){
		Graphics _tmp3 = g;
		Graphics _tmp4 = g;		
		g.drawImage(i,x,y,a);
	}
	void drawImageClip(Graphics g,Image i,int f,int w,int x,int y){
		int X=g.getClipX();
		int Y=g.getClipY();
		int W=g.getClipWidth();
		int H=g.getClipHeight();
		g.setClip(x, y, w, i.getHeight());
		drawImage(g,i,x - f * w,y,4|16);
		g.setClip(X, Y, W, H);
	}
	void drawBlackshadow(Graphics g,int br,int bl,int w,int h,int x,int y){
		g.setColor(br);
		g.drawLine(x,y,x,y+h);
		g.drawLine(x,y,x+w,y);
		g.setColor(bl);
		g.drawLine(x+w,y+h,x,y+h);
		g.drawLine(x+w,y+h,x+w,y);
	}
	void playMidi(String s){
		try{
			if (midi==null||!midi.equals(s)){
				if (player!=null)
					player.close();
				player = Manager.createPlayer(getClass().getResourceAsStream("/"+s+".mid"),"audio/midi");
				player.realize();
				player.setLoopCount(-1);
				VolumeControl Volume = (VolumeControl)player.getControl("VolumeControl");
				Volume.setLevel(80);
				midi=s;
			}			
			if (sound_on)//��������
				player.start();
		}catch(Exception exception){}
	}
}
import java.util.Random;

public class Monster_data
{

    private GameCanvas gameCanvas;
    private byte base_mobX;
    private byte base_mobY;
    public byte mX;
    public byte mY;
    public byte m_sX;
    public byte m_sY;
    public byte m_p;
    public byte m_a;
    public byte mo_l;
    public byte mo_e;
    public byte mo_i;
    public byte mo_a;
    public byte mo_d;
    public int mo_hp;
    public int mo_bHp;
    public int item;
    public byte c_l;

    Monster_data()
    {
    }

    public Monster_data(GameCanvas gamecanvas, byte byte0, byte byte1, byte byte2, byte byte3)
    {
        base_mobX = byte0;
        base_mobY = byte1;
        mX = byte0;
        mY = byte1;
        mo_l = byte2;
        c_l = byte3;
        monster_data(byte3);
        System.gc();
    }

    public Monster_data(GameCanvas gamecanvas, byte byte0, byte byte1, byte byte2, byte byte3, byte byte4)
    {
        mX = byte0;
        mY = byte1;
        m_sX = byte2;
        m_sY = byte3;
        if(byte4 == 1)
        {
            mo_hp = 800;
            mo_bHp = mo_hp;
            mo_e = 70;
            mo_d = 8;
            mo_a = 36;
            if(random(3) == 1)
                item = random(10001) + 5000;
        } else
        if(byte4 == 2)
        {
            mo_hp = 1000;
            mo_bHp = mo_hp;
            mo_e = 70;
            mo_d = 9;
            mo_a = 40;
            if(random(3) == 1)
                item = random(10001) + 5000;
        } else
        if(byte4 == 3)
        {
            mo_hp = 1200;
            mo_bHp = mo_hp;
            mo_e = 70;
            mo_d = 10;
            mo_a = 46;
            if(random(3) == 1)
                item = random(10001) + 5000;
        }
        System.gc();
    }

    public int[] monster_health(byte byte0)
    {
        int ai[] = {
            0, 0
        };
        if(mo_d - byte0 < 0)
            mo_hp = (mo_hp + mo_d) - byte0;
        else
            mo_hp--;
        if(mo_hp <= 0)
        {
            ai[0] = mo_e;
            ai[1] = item;
            mo_e = 0;
            item = 0;
            mo_hp = 0;
            m_a = 0;
            mX = 20;
            mY = 20;
        }
        return ai;
    }

    public void mob_live(byte byte0, byte byte1, byte byte2)
    {
        if(byte2 == 0)
        {
            mo_hp = 2500;
            mo_bHp = mo_hp;
            mo_e = 100;
            mo_d = 18;
            mo_a = 74;
            if(random(10) == 1)
                item = 50000;
        } else
        {
            monster_data(byte2);
        }
        mX = byte0;
        mY = byte1;
    }

    private void monster_data(byte byte0)
    {
        boolean flag = false;
        if(random(3) == 1)
            flag = true;
        if(mo_l == 0)
        {
            if(byte0 < 10)
                mo_e = (byte)(20 - (byte0 / 3) * 5);
            else
            if(byte0 < 20)
                mo_e = (byte)(7 - byte0 / 5);
            else
            if(byte0 >= 20)
                mo_e = 1;
            mo_hp = 25 + (((byte0 * 10) / 10) * 5) / 10;
            mo_bHp = mo_hp;
            mo_a = (byte)(3 + byte0 / 10);
            if(flag)
                item = random(161) + 200;
            else
            if(random(7) == 1)
            {
                int i = random(5);
                if(i == 0 || i == 1)
                    item = 1109;
                else
                if(i == 2 || i == 3)
                    item = 1103;
                else
                if(i == 4)
                    item = byte0 * 200;
            }
        } else
        if(mo_l == 1)
        {
            if(byte0 < 10)
                mo_e = (byte)(22 - (byte0 / 3) * 5);
            else
            if(byte0 < 20)
                mo_e = (byte)(9 - byte0 / 5);
            else
            if(byte0 < 30)
                mo_e = 2;
            else
            if(byte0 >= 30)
                mo_e = 1;
            mo_hp = 30 + (((byte0 * 10) / 6) * 5) / 10;
            mo_bHp = mo_hp;
            mo_d = (byte)((byte0 * 10) / 7 / 10);
            mo_a = (byte)(3 + byte0 / 6);
            if(flag)
                item = random(151) + 350;
            else
            if(random(7) == 1)
            {
                int j = random(5);
                if(j == 0 || j == 1)
                    item = 1109;
                else
                if(j == 2 || j == 3)
                    item = 1103;
                else
                if(j == 4)
                    item = byte0 * 200;
            }
        } else
        if(mo_l == 2)
        {
            if(byte0 < 10)
                mo_e = (byte)(35 - (byte0 / 3) * 5);
            else
            if(byte0 < 20)
                mo_e = (byte)(11 - byte0 / 5);
            else
            if(byte0 < 30)
                mo_e = 3;
            else
            if(byte0 < 40)
                mo_e = 2;
            else
            if(byte0 >= 40)
                mo_e = 1;
            mo_hp = 40 + (((byte0 * 10) / 3) * 6) / 10;
            mo_bHp = mo_hp;
            mo_d = (byte)((byte0 * 10) / 8 / 10);
            mo_a = (byte)(3 + byte0 / 4);
            if(flag)
                item = random(151) + 450;
            else
            if(random(7) == 1)
            {
                int k = random(5);
                if(k == 0 || k == 1)
                    item = 1109;
                else
                if(k == 2 || k == 3)
                    item = 1103;
                else
                if(k == 4)
                    item = byte0 * 200;
            }
        } else
        if(mo_l == 3)
        {
            if(byte0 < 20)
                mo_e = (byte)(37 - byte0 / 3);
            else
            if(byte0 < 30)
                mo_e = 6;
            else
            if(byte0 < 40)
                mo_e = 4;
            else
            if(byte0 < 50)
                mo_e = 3;
            else
            if(byte0 >= 50)
                mo_e = 2;
            mo_hp = 70 + ((byte0 * 10) / 6) * 2;
            mo_bHp = mo_hp;
            mo_d = (byte)(1 + byte0 / 10);
            mo_a = (byte)(5 + byte0 / 5);
            if(flag)
                item = random(151) + 600;
            else
            if(random(7) == 1)
            {
                int l = random(5);
                if(l == 0 || l == 1)
                    item = 1109;
                else
                if(l == 2 || l == 3)
                    item = 1103;
                else
                if(l == 4)
                    item = byte0 * 200;
            } else
            if(random(30) == 1)
            {
                if(random(2) == 0)
                    item = 1104;
                else
                    item = 1110;
            } else
            if(random(80) == 1)
                if(random(2) == 0)
                    item = 1105;
                else
                    item = 1111;
        } else
        if(mo_l == 4)
        {
            if(byte0 < 20)
                mo_e = (byte)(50 - byte0 / 3);
            else
            if(byte0 < 30)
                mo_e = 8;
            else
            if(byte0 < 40)
                mo_e = 5;
            else
            if(byte0 < 50)
                mo_e = 4;
            else
            if(byte0 >= 50)
                mo_e = 3;
            mo_hp = 100 + ((byte0 * 10) / 9) * 4;
            mo_bHp = mo_hp;
            mo_d = (byte)(4 + byte0 / 10);
            mo_a = (byte)(8 + byte0 / 5);
            if(flag)
                item = random(151) + 800;
            else
            if(random(7) == 1)
            {
                int i1 = random(5);
                if(i1 == 0 || i1 == 1)
                    item = 1109;
                else
                if(i1 == 2 || i1 == 3)
                    item = 1103;
                else
                if(i1 == 4)
                    item = byte0 * 200;
            } else
            if(random(50) == 1)
            {
                if(random(2) == 0)
                    item = 1105;
                else
                    item = 1111;
            } else
            if(random(100) == 1)
                item = 1112;
        }
    }

    private int random(int i)
    {
        Random random1 = new Random();
        int j = (random1.nextInt() >>> 1) % i;
        return j;
    }
}

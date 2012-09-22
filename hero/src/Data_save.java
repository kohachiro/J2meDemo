import java.io.PrintStream;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;

public class Data_save
{

    private RecordStore recStore;
    public byte save_inventory[];
    public byte save_my_state[];
    public byte save_level;
    public int save_exp;
    public int save_health;
    public int save_money;
    public byte save_state;
    public byte s_p;
    public byte n_d;
    public byte n_c;
    public byte n_s;
    public byte stX;
    public byte stY;
    public byte end_m;

    public Data_save()
    {
        save_inventory = new byte[13];
        save_my_state = new byte[5];
    }

    public void openRecStore()
    {
        try
        {
            recStore = RecordStore.openRecordStore("data_db", true);
        }
        catch(Exception exception) { }
    }

    public void writeRecord(String s)
    {
        byte abyte0[] = s.getBytes();
        try
        {
            recStore.addRecord(abyte0, 0, abyte0.length);
        }
        catch(Exception exception)
        {
            System.err.println(exception.toString());
        }
    }

    public void writeRecord(byte abyte0[])
    {
        try
        {
            recStore.addRecord(abyte0, 0, abyte0.length);
        }
        catch(Exception exception)
        {
            System.err.println(exception.toString());
        }
    }

    public boolean readRecordsUpdate()
    {
        boolean flag = false;
		try{
        if(recStore.getNumRecords() > 0)
        {
            RecordEnumeration recordenumeration = recStore.enumerateRecords(null, null, false);
            for(int i = 0; recordenumeration.hasNextElement(); i++)
                if(i == 0)
                    save_inventory = recordenumeration.nextRecord();
                else
                if(i == 1)
                    save_my_state = recordenumeration.nextRecord();
                else
                if(i == 2)
                    save_exp = Integer.parseInt(new String(recordenumeration.nextRecord()));
                else
                if(i == 3)
                    save_health = Integer.parseInt(new String(recordenumeration.nextRecord()));
                else
                if(i == 4)
                    save_money = Integer.parseInt(new String(recordenumeration.nextRecord()));
                else
                if(i == 5)
                {
                    byte abyte0[] = recordenumeration.nextRecord();
                    save_level = abyte0[0];
                    save_state = abyte0[1];
                    s_p = abyte0[2];
                    n_d = abyte0[3];
                    n_c = abyte0[4];
                    n_s = abyte0[5];
                    stX = abyte0[6];
                    stY = abyte0[7];
                    end_m = abyte0[8];
                }

				flag = true;
			}
		}catch(Exception exception) { return false; }
        return flag;
    }

    public void closeRecStore()
    {
        try
        {
            recStore.closeRecordStore();
        }
        catch(Exception exception) { }
        System.gc();
    }

    public void deleteRecords()
    {
        try
        {
            RecordStore.deleteRecordStore("data_db");
        }
        catch(Exception exception) { }
    }
}

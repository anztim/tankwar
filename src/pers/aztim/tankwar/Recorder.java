package pers.aztim.tankwar;

import java.io.*;

public class Recorder {
    private static final Recorder RECORDER = new Recorder();

    private File recordFile;

    public static Recorder instant() {
        return RECORDER;
    }

    private static class Record implements Serializable {
        int recordNum;

        public Record() {
            recordNum = 0;
        }

    }

    private Record record;

    private ObjectInputStream in;

    private ObjectOutputStream out;

    private Recorder() {
        recordFile = new File("record.dat");
        if (!recordFile.exists()) {
            record = new Record();
            try {
                recordFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                in = new ObjectInputStream(new FileInputStream(recordFile));
                record = (Record) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void add() {
        record.recordNum++;
    }

    public int getRecord() {
        return record.recordNum;
    }

    void store() {
        try {
            out = new ObjectOutputStream(new FileOutputStream(recordFile));
            out.writeObject(record);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

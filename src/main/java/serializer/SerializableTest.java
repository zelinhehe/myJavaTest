package serializer;


import java.io.Serializable;
import java.io.*;


public class SerializableTest implements Serializable {

    private String name;

    public SerializableTest() {
        this.name = "new SerializableTest";
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        SerializableTest SerializableTest = new SerializableTest();
        try {
            FileOutputStream fos = new FileOutputStream("SerializableTestDemo.out"); //这就是你序列化文件的存储位置，是由自己控制的
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            System.out.println(" 1> " + SerializableTest.getName());
            SerializableTest.setName("My SerializableTest");
            oos.writeObject(SerializableTest);
            oos.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        try {
            FileInputStream fis = new FileInputStream("SerializableTestDemo.out");
            ObjectInputStream ois = new ObjectInputStream(fis);
            SerializableTest = (SerializableTest) ois.readObject();
            System.out.println(" 2> " + SerializableTest.getName());
            ois.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

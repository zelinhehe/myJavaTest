package basicDB;

import java.io.*;
import java.util.*;

import file.Student;

public class BasicDBTest {

    private static byte[] toBytes(Student student) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);
        dout.writeUTF(student.getName());
        dout.writeInt(student.getAge());
        dout.writeDouble(student.getScore());
        return bout.toByteArray();
    }

    public static void saveStudents(Map<String, Student> students)
            throws IOException {
        BasicDB1 db = new BasicDB1("./", "studentDB");
        for (Map.Entry<String, Student> kv : students.entrySet()) {
            db.put(kv.getKey(), toBytes(kv.getValue()));
        }
        db.close();
    }

    private static Student toStudent(byte[] bytes) throws IOException{
        ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
        DataInputStream din = new DataInputStream(bin);
        Student s = new Student();
        s.setName(din.readUTF());
        s.setAge(din.readInt());
        s.setScore(din.readDouble());
        return s;
    }

    public static void readStudents(String key)
            throws IOException {
        BasicDB1 db = new BasicDB1("./", "studentDB");
        byte[] bytes = db.get(key);
        if (bytes == null) {
            db.close();
            System.out.println(key + " not found");
            return;
        }
        Student student = toStudent(bytes);
        System.out.println(student.getName() + "," + student.getAge() + "," + student.getScore());

        db.close();
    }

    public static void main(String[] args) throws IOException{
        Map<String, Student> students = new HashMap<>();
        students.put("张三", new Student("张三", 18, 80.9d));
        students.put("李四", new Student("李四", 17, 67.5d));

        BasicDBTest.saveStudents(students);
//        BasicDBTest.readStudents("张三");
//        BasicDBTest.readStudents("李四");
//        BasicDBTest.readStudents("李");
    }
}

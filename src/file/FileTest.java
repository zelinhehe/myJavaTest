package file;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileTest {

    public static void fileOutputStream() throws IOException {
//        OutputStream output = new FileOutputStream("hello.txt");
        OutputStream output = new BufferedOutputStream(new FileOutputStream("hello.txt"));
        try {
            String data = "hello, 123, 老马";
            byte[] bytes = data.getBytes(Charset.forName("UTF-8"));
            output.write(bytes);
        } finally {
            output.close();
        }
    }

    public static void fileInputStream() throws IOException{
//        InputStream input = new FileInputStream("hello.txt");
        InputStream input = new BufferedInputStream(new FileInputStream("hello.txt"));
        try {
            byte[] buf = new byte[10];
            int bytesRead = input.read(buf);  // 把buf写满，只读了10个字节
            String data = new String(buf, 0, bytesRead, "UTF-8");
            System.out.println(data);
        } finally {
            input.close();
        }
    }

    public static void byteArrayOutputStream() throws IOException{
        InputStream input = new FileInputStream("hello.txt");
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[10];
            int bytesRead;
            while ((bytesRead = input.read(buf)) != -1)
                output.write(buf, 0, bytesRead);  // 读入buf的数据写入 ByteArrayOutputStream
            String data = output.toString("UTF-8");
            System.out.println(data);
        } finally {
            input.close();
        }
    }

    public static void dataOutputStream() throws IOException {
        List<Student> students = Arrays.asList(new Student[]{
                new Student("张三", 18, 80.9d),
                new Student("李四", 17, 67.5d)
        });
        DataOutputStream output = new DataOutputStream(new FileOutputStream("students.dat"));
        try {
            output.writeInt(students.size());
            for (Student s : students) {
                output.writeUTF(s.getName());
                output.writeInt(s.getAge());
                output.writeDouble(s.getScore());
            }
        } finally {
            output.close();
        }
    }

    public static void dataInputStream() throws IOException{
        DataInputStream input = new DataInputStream(new FileInputStream("students.dat"));
        try {
            int size = input.readInt();
            List<Student> students = new ArrayList<Student>(size);
            for (int i = 0; i < size; i++) {
                Student s = new Student();
                s.setName(input.readUTF());
                s.setAge(input.readInt());
                s.setScore(input.readDouble());
                students.add(s);
            }

            for (Student s : students)
                System.out.println(s.getName() +" " + s.getAge() + " " + s.getScore());

        } finally {
            input.close();
        }
    }

    public static void outputStreamWriter() throws IOException {
//        Writer writer = new OutputStreamWriter(new FileOutputStream("hello.txt"));
        Writer writer = new FileWriter("hello.txt");
        try {
            String data = "hello, 123, 老马";
            writer.write(data);
        } finally {
            writer.close();
        }
    }

    public static void inputStreamReader() throws IOException {
//        Reader reader = new InputStreamReader(new FileInputStream("hello.txt"));
        Reader reader = new FileReader("hello.txt");
        try {
            char[] cbuf = new char[1024];
            int charsRead = reader.read(cbuf);
            System.out.println(new String(cbuf, 0, charsRead));
        } finally {
            reader.close();
        }
    }


    public static void bufferedWriter() throws IOException{
        List<Student> students = Arrays.asList(new Student[]{
                new Student("张三", 18, 80.9d),
                new Student("李四", 17, 67.5d)
        });
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("students.txt"));
            for (Student s : students) {
                writer.write(s.getName() + "," + s.getAge() + "," + s.getScore());
                writer.newLine();
            }
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    public static void bufferedReader() throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("students.txt"));
            List<Student> students = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                Student s = new Student();
                s.setName(fields[0]);
                s.setAge(Integer.parseInt(fields[1]));
                s.setScore(Double.parseDouble(fields[2]));
                students.add(s);
                line = reader.readLine();
            }
            for (Student s : students)
                System.out.println(s.getName() + "," + s.getAge() + "," + s.getScore());
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public static void printWriterTest() throws IOException{
        List<Student> students = Arrays.asList(new Student[]{
                new Student("张三", 18, 80.9d),
                new Student("李四", 17, 67.5d)
        });
        PrintWriter printWriter = new PrintWriter("students.txt");
        try {
            for (Student s : students)
                printWriter.println(s.getName() + "," + s.getAge() + "," + s.getScore());
        } finally {
            printWriter.close();
        }
    }

    public static void main(String[] args) throws IOException{
//        fileOutputStream();
//        fileInputStream();
//        byteArrayOutputStream();
//        dataOutputStream();
//        dataInputStream();

//        outputStreamWriter();
//        inputStreamReader();
//        bufferedWriter();
//        bufferedReader();
        printWriterTest();
    }
}

class Student {
    private String name;
    private int age;
    private double score;
    public Student(String name, int age, double score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }
    public Student(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}

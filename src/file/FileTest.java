package file;

import java.io.*;
import java.nio.charset.Charset;

public class FileTest {

    public static void fileOutputStream() throws IOException {
        OutputStream output = new FileOutputStream("hello.txt");
        try {
            String data = "hello, 123, 老马";
            byte[] bytes = data.getBytes(Charset.forName("UTF-8"));
            output.write(bytes);
        } finally {
            output.close();
        }
    }

    public static void fileInputStream() throws IOException{
        InputStream input = new FileInputStream("hello.txt");
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

    public static void main(String[] args) throws IOException{
//        fileOutputStream();
//        fileInputStream();
        byteArrayOutputStream();
    }
}

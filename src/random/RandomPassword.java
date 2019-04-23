package random;

import java.util.Random;

public class RandomPassword {

    public static void main(String[] args) {
//        randomPassword();
        randomPassword2();
    }

    // 6位随机密码
    public static void randomPassword() {
        char[] chars = new char[6];
        Random random = new Random();
        for (int i=0; i<6; i++) {
            System.out.println(random.nextInt(10));
//            System.out.println('0' + random.nextInt(10));
            chars[i] = (char) ('0' + random.nextInt(10));
            System.out.println(chars[i]);
        }
        String password = new String(chars);
        System.out.println(password);
    }

    // 6位随机密码
    public static void randomPassword2() {
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i=0; i<6; i++) {
            password.append(random.nextInt(10));
        }
        System.out.println(password.toString());
    }
}

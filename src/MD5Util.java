import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class Md5Util {

    private static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte value : b) {
            resultSb.append(byteToHexString(value));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * saas接口获取token方法
     *
     * @param origin
     * @return
     */
    public static String md5Encode(String origin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return byteArrayToHexString(md.digest(origin.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;

    }

    private static final String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static void main(String[] args) {
        String md3 = Md5Util.md5Encode(
                "accountNum=1&action=createInstance&email=bujiaban@jd.com" +
                        "&expiredOn=2018-06-30 23:59:59&jdPin=bujiaban&mobile=" +
                        "&orderBizId=444181&orderId=556596" +
                        "&serviceCode=FW_GOODS-500232" +
                        "&skuId=FW_GOODS-500232-1&template=" +
                        "&key=qweqeqeqe123123123131"

        );

        System.out.println(md3);  // "&token=9512df22a941f172a9f28068b758ee3e"
    }
}
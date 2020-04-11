package cn.tanlw.util;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;

/**
 * 文件加解密工具
 * @create 2018/5/25
 */
public class DESStringUtil {
    public DESStringUtil(){}
    SecretKey key;

    public DESStringUtil(String str) {
        init(str);//生成密匙
    }

    /**
     * args[0]: plaintext
     * args[1]: key
     *
     * @param args
     * @throws Exception
     */
    public static void main1(String[] args) throws Exception {
        if (args == null && args.length != 2) {
            throw new RuntimeException("Please have a check on the input params");
        }

        DESStringUtil td = new DESStringUtil(args[1]);
        byte[] encrypted = td.encrypt(args[0]);//加密
        String s = byteArr2HexStr(encrypted);
        System.out.println("s:"+s);
        byte[] bytes = hexStr2ByteArr(s);
        System.out.println(encrypted);
        System.out.println(bytes);
        byte[] decrypt = td.decrypt(bytes);//解密
        System.out.println("decrypted:"+new String(decrypt));
    }

    /**
     * 根据参数生成KEY
     */
    public void init(String strKey) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("DES");
//            generator.init(new SecureRandom(strKey.getBytes()));
            // https://stackoverflow.com/questions/8049872/given-final-block-not-properly-padded
            // met this issue due to operation system, simple to different platform about JRE implementation.
            // will get the same value in Windows, while it's different in Linux. So in Linux need to be changed to
            // "SHA1PRNG" is the algorithm used
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strKey.getBytes());
            generator.init(56, secureRandom);
            this.key = generator.generateKey();
            generator = null;
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        }
    }

    public byte[] encrypt(String source) throws Exception {
        Cipher desCipher;

        // Create the cipher 
        desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        // Initialize the cipher for encryption
        desCipher.init(Cipher.ENCRYPT_MODE, this.key);

        //sensitive information
        byte[] text = source.getBytes();

        System.out.println("Text [Byte Format] : " + text);
        System.out.println("Text : " + new String(text));

        // Encrypting the text
        return desCipher.doFinal(text);
    }

    public byte[] decrypt(byte[] encryptedBytes) throws Exception {
        Cipher desCipher;

        // Create the cipher 
        desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        // Initialize the cipher for encryption
        desCipher.init(Cipher.DECRYPT_MODE, this.key);

        // Decrypt the text
        return desCipher.doFinal(encryptedBytes);
    }

    /**
     * https://blog.csdn.net/jueblog/article/details/9956311
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public static void main(String[] args) throws Exception {


        DESStringUtil td = new DESStringUtil("Huawei");
        byte[] encrypted2 = td.encrypt("password111");//加密
        String s = byteArr2HexStr(encrypted2);
        System.out.println("s:"+s);
        byte[] bytes = hexStr2ByteArr(s);
        for (int i = 0, j = 0; i < encrypted2.length && j < bytes.length; i++,j++) {
            System.out.println(encrypted2[i]+","+bytes[j]);
        }
        System.out.println(encrypted2.length);
        System.out.println(bytes.length);
    }
}

/**
 *  https://stackoverflow.com/questions/8049872/given-final-block-not-properly-padded
 *  met this issue due to operation system, simple to different platform about JRE implementation.
 *  will get the same value in Windows, while it's different in Linux. So in Linux need to be changed to
 *  "SHA1PRNG" is the algorithm used
 *  
 */
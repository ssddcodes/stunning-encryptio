import dev.ssdd.Encrypto;
import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import static org.junit.Assert.assertEquals;

public class ain2_public {
    @Test
    public void name() {
//        DES
        Encrypto encrypto = new Encrypto(Encrypto.DES, "the moon is scary sometimes"); //It's suggested to pass the hash of the password instead of plain text
        String val = "Hi from DES";
        String base64DESEncrypted = encrypto.encrypt(val);
        System.out.printf("Original value: %s\nEncryptedValue: %s\nDecryptedValue: %s", val, base64DESEncrypted, encrypto.decrypt(base64DESEncrypted));

//        RSA

        System.out.println(Arrays.toString(new BigInteger("val".getBytes()).toByteArray()));

        Encrypto encrypto1 = new Encrypto(Encrypto.RSA, 128);
        System.out.println(encrypto1.getPrivateKeyString());
        String val1 = "abx";
        String base64RSAEncrypted = encrypto1.encrypt(val1, Encrypto.getPublicKey(encrypto1.getSterilizedPublicKey()));
        System.out.printf("\n\nOriginal value: %s\nEncryptedValue: %s\nDecryptedValue: %s", val1, base64RSAEncrypted, encrypto1.decrypt(base64RSAEncrypted));
    }
    @Test
    public void time1() {
        for (int i = 0; i < 100; i++) {
            String x = new String(new BigInteger("ab".getBytes()).toByteArray());// ~4
            assertEquals("ab", x);
        }
    }

    @Test
    public void time2() {
        for (int i = 0; i < 100; i++) {
            String x = new String(writeBigInt(_convertBytesToBigInt("ab".getBytes())));// ~4
            assertEquals("ab", x);
        }
    }

    BigInteger _convertBytesToBigInt(byte[] bytes) {
        BigInteger result = BigInteger.ZERO;
        for (byte z : bytes) {
            result = (result.shiftLeft(8)).or(BigInteger.valueOf(z & 0xff));
        }
        return result;
    }

    static byte[] writeBigInt(BigInteger number) {
        // Not handling negative numbers. Decide how you want to do that.
        int bytes = (number.bitLength() + 7) >> 3;
        BigInteger b256 = BigInteger.valueOf(256);
        byte[] result = new byte[bytes];
        for (int i = 0; i < bytes; i++) {
            result[bytes-1-i] = number.remainder(b256).byteValue();
            number = number.shiftRight(8);
        }
        return result;
    }

}
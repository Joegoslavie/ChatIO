package security;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class TestConsole {

    private static SecureRandom random;
    private static DHExchange dhExchange1;
    private static DHExchange dhExchange2;

    public static void main(String args[]){

        random = new SecureRandom();
        BigInteger privKey1 = BigInteger.probablePrime(128, random);
        BigInteger privKey2 = BigInteger.probablePrime(128, random);

        dhExchange1 = new DHExchange(privKey1);
        dhExchange2 = new DHExchange(privKey2);

        BigInteger pubKey1 = dhExchange1.getPublicKey();
        BigInteger pubKey2 = dhExchange2.getPublicKey();

        dhExchange1.setPublicKey(pubKey2);
        dhExchange2.setPublicKey(pubKey1);

        String shared1 = dhExchange1.getSharedKey();
        String shared2 = dhExchange2.getSharedKey();

        System.out.println("Verified: " + (shared1.equals(shared2) + "| shared: " + shared1));
    }
}

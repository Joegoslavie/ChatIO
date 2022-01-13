package security;

import java.math.BigInteger;
import java.security.SecureRandom;

public class DHExchange {

    // Diffie Hellman Key Exchange
    // Basic implementation

    // Define G & N (primes)

    private SecureRandom random = new SecureRandom();

    private BigInteger sharedG = new BigInteger("8bfc699e6f7951cb3f5c0b82a56dccad", 16);
    private BigInteger sharedN = new BigInteger("c9dfcebb900e831eab96313a23944d2da16f65ef3a17e68277598b8e4b8c9e19", 16);

    private BigInteger publicKey;
    private BigInteger privateKey;
    private BigInteger sharedKey;

    public String getSharedKey() {
        return sharedKey.toString(16);
    }

    public void setPublicKey(BigInteger publicKey){
        this.publicKey = publicKey;
        this.calculateSecret();
    }

    public boolean isInitialized(){
        return (this.publicKey != null && this.privateKey != null && this.sharedKey != null);
    }

    public DHExchange(BigInteger privateKey){
        this.privateKey = privateKey;
    }

    public BigInteger getPublicKey(){
        // Explanation: g^priv % n

        BigInteger pkey = sharedG.modPow(privateKey, sharedN);
        return pkey;
    }

    private void calculateSecret(){
       // Explanation: pub^priv % n

        this.sharedKey = publicKey.modPow(privateKey, sharedN);
    }
}

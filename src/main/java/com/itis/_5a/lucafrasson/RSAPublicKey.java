package com.itis._5a.lucafrasson;
import java.math.BigInteger;

public class RSAPublicKey  {

    private final BigInteger n;
    private final BigInteger e;

    public RSAPublicKey(BigInteger n, BigInteger e) {
        this.n = n;
        this.e = e;
    }

    public BigInteger getModulo() {
        return n;
    }

    public BigInteger getPublicExponent() {
        return e;
    }

}

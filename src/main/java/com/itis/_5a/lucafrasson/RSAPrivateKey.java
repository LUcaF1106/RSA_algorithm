package com.itis._5a.lucafrasson;

import java.math.BigInteger;

public class RSAPrivateKey {
    
    private final BigInteger n;
    private final BigInteger d;

    public RSAPrivateKey(BigInteger n, BigInteger d) {
        this.n = n;
        this.d = d;
    }

    public BigInteger getModulo() {
        return n;
    }

    public BigInteger getPrivateExponent() {
        return d;
    }
}

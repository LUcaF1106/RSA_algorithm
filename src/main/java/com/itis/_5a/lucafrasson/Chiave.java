package com.itis._5a.lucafrasson;
import java.math.BigInteger;


public class Chiave {
    private final BigInteger n;
    private final BigInteger e;
    
    public Chiave(BigInteger n, BigInteger e){
        this.n= n;
        this.e= e;  
    }
    public BigInteger getE() {
        return e;
    }
    public BigInteger getN() {
        return n;
    }
}

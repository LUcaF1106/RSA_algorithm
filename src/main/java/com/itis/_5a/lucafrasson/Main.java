package com.itis._5a.lucafrasson;

import java.math.BigInteger;
import java.util.Random;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    private static Chiave k_pub = null;
    private static Chiave k_priv = null;

    public static void main(String[] args) {

        Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.INFO);

        key_gen();
    }

    public static void key_gen() {

        Random rand = new Random();

        BigInteger p, q;
        long min = 213402147;
        do {
            p = BigInteger.probablePrime(Long.BYTES * 4, rand);
            q = BigInteger.probablePrime(Long.BYTES * 4, rand);
        } while (!(p.equals(q) || p.multiply(q).compareTo(BigInteger.valueOf(min)) > 0));

        logger.info("Generati p e q");
        logger.info("p="+p);
        logger.info("q="+q);
        BigInteger n = q.multiply(p);
        logger.info("n="+n);        

        BigInteger V = q.subtract(BigInteger.ONE).multiply(p.subtract(BigInteger.ONE));
        logger.info("V="+V);

        BigInteger e;

        do {
            e = BigInteger.probablePrime(Long.BYTES, rand);
        } while (!(e.gcd(V).equals(BigInteger.ONE)));
        logger.info("e="+e);

        BigInteger d= e.modInverse(V);
        logger.info("d="+d);

        k_priv=new Chiave(n, d);
        k_pub= new Chiave(n, e);
        logger.info("Generate le chaivi");
    }
}
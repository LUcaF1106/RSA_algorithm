package com.itis._5a.lucafrasson;

import java.math.BigInteger;

import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    private static RSAKeyPair key1 = null;
    private static RSAKeyPair key2 = null;

    public static void main(String[] args) {
        Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.DEBUG);
        Scanner inp = new Scanner(System.in);
        boolean exit=false;
        String c = "";
        while (!exit) {
            int ch = 0;
            System.out.println("1)Crea le chiavi");
            System.out.println("2)Cifra un messaggio");
            System.out.println("3)Decifra un messaggio");
            System.out.println("4)Esci");
            do {
                System.out.println("Seleziona l'azione che vuoi fare, inserisci un numero da 1 a 4");
                try {
                    ch = inp.nextInt();
                    inp.nextLine();
                } catch (Exception e) {
                    logger.warn("Bisogna inserere un intero");
                }

            } while (ch < 1 || ch > 3);
            switch (ch) {
                case 1:
                    key1 = key_gen();
                    key2 = key_gen();
                    break;
                case 2:
                if(key1!=null && key2!=null){
                    System.out.println("Inserisci il messaggio che vuoi cifrare:");
                    
                    String m = inp.nextLine();
                    logger.info("Abbiamo cifrato il messaggio: "+m);
                    c = encrypt(m, key1.getPrivateKey());
                    c = encrypt(c, key2.getPublicKey());}
                    else{
                        logger.warn("Prima devi creare delle chaivi");
                    }
                    break;

                case 3:
                if(key1!=null && key2!=null){
                    if(c!=""){
                        String md=decrypt(decrypt(c, key2.getPrivateKey()), key1.getPublicKey());
                        logger.info("Il messaggio decrifirato è :"+md);
                    }
                    else{
                        logger.warn("Devi prima cifrare un messaggio");
                    }
                    c="";
                }else{
                    logger.warn("Prima devi creare delle chaivi");
                }
                    break;
                    case 4:
                        exit=true;
                    break;
                default:
                    break;
            }
        }
        inp.close();
        
    }
    

    public static RSAKeyPair key_gen() {

        Random rand = new Random();

        BigInteger p, q;
        long min = 213402147;
        do {
            p = BigInteger.probablePrime(Long.BYTES * 8, rand);
            q = BigInteger.probablePrime(Long.BYTES * 8, rand);
        } while (!(p.equals(q) || p.multiply(q).compareTo(BigInteger.valueOf(min)) > 0));

        logger.info("Generati p e q");
        logger.debug("p=" + p);
        logger.debug("q=" + q);
        BigInteger n = q.multiply(p);
        logger.debug("n=" + n);

        BigInteger V = q.subtract(BigInteger.ONE).multiply(p.subtract(BigInteger.ONE));
        logger.debug("V=" + V);

        BigInteger e;

        do {
            e = BigInteger.probablePrime(Long.BYTES * 8, rand);
        } while (!(e.gcd(V).equals(BigInteger.ONE)));
        logger.debug("e=" + e);

        BigInteger d = e.modInverse(V);
        logger.debug("d=" + d);

        RSAPrivateKey k_priv = new RSAPrivateKey(n, d);

        RSAPublicKey k_pub = new RSAPublicKey(n, e);
        return new RSAKeyPair(k_priv, k_pub);

    }

    public static String encrypt(String m, RSAPrivateKey key) {
        StringBuilder encrypted = new StringBuilder();
        for (char ch : m.toCharArray()) {
            BigInteger M = BigInteger.valueOf((int) ch);
            BigInteger C = M.modPow(key.getPrivateExponent(), key.getModulo());
            encrypted.append(C.toString()).append(";");
        }

        return encrypted.toString();

    }

    public static String encrypt(String m, RSAPublicKey key) {
        StringBuilder encrypted = new StringBuilder();
        for (char ch : m.toCharArray()) {
            BigInteger M = BigInteger.valueOf((int) ch);
            BigInteger C = M.modPow(key.getPublicExponent(), key.getModulo());
            encrypted.append(C.toString()).append(";");
        }

        return encrypted.toString();

    }

    public static String decrypt(String c, RSAPrivateKey key) {
        StringTokenizer tokenizer = new StringTokenizer(c, ";");
        StringBuilder mes = new StringBuilder();
        try {
            while (tokenizer.hasMoreTokens()) {
                BigInteger E = new BigInteger(tokenizer.nextToken());
                int C = E.modPow(key.getPrivateExponent(), key.getModulo()).intValue();
                mes.append((char) C);
            }

            return mes.toString();
        } catch (NumberFormatException e) {
            logger.warn("Chiave usate per decriptare non valida");
            return c;
        }

    }

    public static String decrypt(String c, RSAPublicKey key) {
        StringTokenizer tokenizer = new StringTokenizer(c, ";");
        StringBuilder mes = new StringBuilder();
        try {
            while (tokenizer.hasMoreTokens()) {
                BigInteger E = new BigInteger(tokenizer.nextToken());
                int C = E.modPow(key.getPublicExponent(), key.getModulo()).intValue();
                mes.append((char) C);
            }
            return mes.toString();
        } catch (NumberFormatException e) {
            logger.warn("Chiave usate per decriptare non valida");
            return c;
        }

    }
}
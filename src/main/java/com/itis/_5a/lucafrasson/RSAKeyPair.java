package com.itis._5a.lucafrasson;

public class RSAKeyPair {
    private RSAPrivateKey k_priv;
    private RSAPublicKey k_pub;

    public RSAKeyPair(RSAPrivateKey priv, RSAPublicKey pub){
            this.k_priv= priv;
            this.k_pub= pub;
        }
    public RSAPrivateKey getPrivateKey(){
        return k_priv;
    }
    public RSAPublicKey getPublicKey(){
        return k_pub;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
/**
 *
 * @author Yannick van Leeuwen
 */
public class SimpleKeyGenerator implements KeyGenerator {
    @Override
    public Key generateKey() {
        String keyString = "testkey";
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
        return key;
    }
}

package pl.lakomika.gymfit;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.lakomika.gymfit.utils.PasswordOperation;

public class PasswordOperationTest {

    @Test
    public void hashPasswordTest() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("admin.test"));

    }


    @Test
    public void generatePasswordTest() {
        PasswordOperation passwordOperation = new PasswordOperation(10);
        System.out.println(passwordOperation.getPassword());
        System.out.println(passwordOperation.getHashPassword());
    }


}

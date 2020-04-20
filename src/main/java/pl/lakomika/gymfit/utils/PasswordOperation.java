package pl.lakomika.gymfit.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class PasswordOperation {

    private String password;
    private String hashPassword;

    public PasswordOperation(int length) {
        createPassword(length);
        hashPassword(password);
    }

    public void createPassword(int length) {
        val pwdGenerator = new RandomStringGenerator.Builder().withinRange(33, 125).build();
        password = pwdGenerator.generate(length);

    }

    public String hashPassword(String password) {
        val bCryptPasswordEncoder = new BCryptPasswordEncoder();
        hashPassword = bCryptPasswordEncoder.encode(password);
        return password;
    }

}

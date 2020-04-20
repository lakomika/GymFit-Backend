package pl.lakomika.gymfit.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.lakomika.gymfit.entity.MyUserDetails;

public class UserAppData {

    public static Long getId() {
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    public static String getUsername() {
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

}

/**
 * Created by tendaimupezeni for ecocash-partner-due-diligence.
 * User: tendaimupezeni
 * Date: 10/11/23
 * Time: 2:35 PM
 */

package com.smatech.smatech_shop_app.security;

import java.util.regex.Pattern;

public class PasswordValidator {

    //    Regex Explanation
    // ^                 Start of the string
    // (?=.*[0-9])       At least one digit
    // (?=.*[a-z])       At least one lowercase letter
    // (?=.*[A-Z])       At least one uppercase letter
    // (?=.*[@#$%^&+=])  At least one special character
    // (?=\S+$)          No whitespace allowed
    // .{8,}             Minimum length of 8 characters
    // $                 End of the string

    private static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.*\\s).{8,}$";
    public static boolean validate(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        return pattern.matcher(password).matches();
    }
}


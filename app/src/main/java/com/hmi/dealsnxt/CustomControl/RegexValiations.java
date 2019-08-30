package com.hmi.dealsnxt.CustomControl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.EditText;
import android.widget.TextView;

public class RegexValiations {
    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String PHONE_REGEX = "\\d{3}-\\d{7}";
    private static final String PHONE_MSG = "###-#######";
    // Error Messages
    private static final String REQUIRED_MSG = "Field can't be left blank";

    private static final String passwordlength_MSG = "Password should be 6-12 characters, it may contain alphabates, numbers & special characters.";
    private static final String passcodelength_MSG = "Password length must between 10 digit ! ";
    private static final String EMAIL_MSG = "Please enter valid e-mail";
    private static final String Phone_MSG = "Phone number length must less than 20 !";

    private static final String Password_MSG = "Password";
    private static final String Passcode_MSG = "Password";

    private static final String Conform_MSG = "Confirm Password";
    private static final String ConformPasswordNotMatch_MSG = "Password & Confirm password does not match";

    public static boolean isEmailAddress(EditText editText, boolean required) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
    }

    // call this method when you need to check phone number validation

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex,
                                  String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        //editText.setError(null);

        // text required and editText is blank, so return false
        if (required && !hasText(editText))
            return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        }
        ;

        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        //editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;

    }

    public static boolean hasnull(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {

            return false;
        }

        return true;

    }


    public static boolean hasTextView(TextView textView) {

        String text = textView.getText().toString().trim();
        textView.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            textView.setError(REQUIRED_MSG);
            return false;
        }

        return true;

    }

    public static boolean compareText(EditText editTextTo,
                                      EditText editTextCompareWith) {

        String To = editTextTo.getText().toString().trim();
        String from = editTextCompareWith.getText().toString().trim();
        editTextTo.setError(null);
        editTextCompareWith.setError(null);

        // length 0 means there is no text
        if (To.length() == 0 || from.length() == 0) {
            editTextTo.setError(Password_MSG);
            editTextCompareWith.setError(Conform_MSG);
            return false;
        } else {
            if (To.length() > 5 || from.length() > 5
                    ) {

            } else {
                editTextTo.setError(passwordlength_MSG);
                editTextCompareWith.setError(passwordlength_MSG);
                return false;
            }

            if (To.equals(from)) {
                return true;
            } else {

                editTextCompareWith.setError(ConformPasswordNotMatch_MSG);
                return false;
            }
        }

    }


    public static boolean PasswordValidation(EditText editTextPassword) {

        String To = editTextPassword.getText().toString().trim();
        editTextPassword.setError(null);

        // length 0 means there is no text
        if (To.length() == 0) {
            editTextPassword.setError(Password_MSG);
            return false;
        } else {
            if (To.length() > 5 && To.length() < 13) {
                return true;
            } else {
                editTextPassword.setError(passwordlength_MSG);
                return false;
            }
        }

    }

    public static boolean PasscodeValidation(EditText editTextPassword) {
        String To = editTextPassword.getText().toString().trim();
        editTextPassword.setError(null);

        if (To.length() == 10) {
            return true;
        } else {
            editTextPassword.setError(passcodelength_MSG);
            return false;
        }

    }


    public static boolean mobileno(EditText editTextTo
    ) {

        String To = editTextTo.getText().toString().trim();

        editTextTo.setError(null);


        // length 0 means there is no text
        if (To.length() == 0) {
            editTextTo.setError("Mobile");

            return false;
        } else {
            if (To.length() == 10) {

            } else {
                editTextTo.setError("Mobile No. length must have 10");

                return false;
            }

        }
        return false;

    }


    public static boolean isPhoneNumber(EditText editText, boolean required) {
        return isValid(editText, PHONE_REGEX, PHONE_MSG, required);
    }

    public static boolean comparePhone(EditText editTextTo) {
        Boolean isvalid = false;
        String To = editTextTo.getText().toString().trim();

        editTextTo.setError(null);

        // length 0 means there is no text
        if (To.length() == 0) {
            editTextTo.setError(REQUIRED_MSG);

            return isvalid = false;
        } else {
            if (To.length() < 20) {
                isvalid = true;
            } else {
                editTextTo.setError(Phone_MSG);

                return isvalid = false;
            }

        }

        return isvalid;
    }

    public static String getFirst10Words(String arg) {
        Pattern pattern = Pattern.compile("([\\S]+\\s*){1,5}");
        Matcher matcher = pattern.matcher(arg);
        matcher.find();
        return matcher.group();
    }

}

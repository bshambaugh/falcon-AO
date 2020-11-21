package org.tartarus.snowball;

import java.lang.reflect.Method;

public class Among
{
    public Among(String str, int si, int r, String methodname,
            SnowballProgram mo)
    {
        s_size = str.length();
        s = str.toCharArray();
        substring_i = si;
        result = r;
        methodobject = mo;
        if (methodname.length() == 0) {
            method = null;
        } else {
            try {
                method = methodobject.getClass().getDeclaredMethod(methodname,
                        new Class[0]);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public final int s_size; // search string
    public final char[] s; // search string
    public final int substring_i; // index to longest matching substring
    public final int result; // result of the lookup
    public final Method method; // method to use if substring matches
    public final SnowballProgram methodobject; // object to invoke method on
};

package selver.smartmissiles.debug;

public class Debug {
    private static boolean _enabled = false;


    public static void setEnabled(boolean enabled)
    {
        _enabled = enabled;
    }

    public static void print(String s)
    {
        if (_enabled)
        {
            System.out.println(s);
        }
    }

}

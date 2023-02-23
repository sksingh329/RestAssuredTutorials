package core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {
    public static String getTimeStamp(String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date());
    }
}

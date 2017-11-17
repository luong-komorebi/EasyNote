package luongvo.com.madara.utils;

import java.util.Calendar;
import java.util.Date;


public class ShortDate {

    private Date dParam;

    public ShortDate(Date dParam) {
        this.dParam = dParam;
    }

    //TODO: return other strings ( Yesterday, 15:05PM, etc ) when appropriate
    public String getMyDate(){
        String sDate;
        Calendar cParam = Calendar.getInstance();
        cParam.setTime(dParam);
        int paramDay = cParam.get(Calendar.DAY_OF_MONTH);
        int paramMonth = cParam.get(Calendar.MONTH); // will return months starting from 0
        int paramYear = cParam.get(Calendar.YEAR);

        paramMonth++; // to match user's intuition


        sDate = paramDay + "/" + paramMonth + "/" + paramYear;

        return sDate;
    }

}
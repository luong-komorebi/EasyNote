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
 	//NOTE: months starting from 0
        int paramMonth = cParam.get(Calendar.MONTH);
        int paramYear = cParam.get(Calendar.YEAR);

	// make it intuitive
        paramMonth++;

	// TODO: Handle "IF"s to assign appropriate string here
        sDate = paramDay + "/" + paramMonth + "/" + paramYear;

        return sDate;
    }

}
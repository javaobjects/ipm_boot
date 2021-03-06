/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @ClassName: GetTimeUtil
 * @Description: 
 * @author 刘斌
 *
 */
public class GetTimeUtil {

    /**
     * 
     * @Title: getNextSendTimeByType
     * @Description: 
     * @param typeId
     * @return Long
     * @throws ParseException Long
     * @author 刘斌
     */
    public static Long getNextSendTimeByType(Integer typeId) throws ParseException{
        Date now = new Date(); 
        Calendar preWeekSundayCal = Calendar.getInstance();
        Calendar preWeekMondayCal = Calendar.getInstance();
        //设置时间成本周第一天(周日)
        preWeekSundayCal.set(Calendar.DAY_OF_WEEK, 1);
        preWeekMondayCal.set(Calendar.DAY_OF_WEEK, 1);

        //上周日时间
        preWeekSundayCal.add(Calendar.DATE, -7);
        //上周一时间
        preWeekMondayCal.add(Calendar.DATE, -6);
        
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(now);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Long nexttime = null;
        switch(typeId){
            case 2:
                calendar.add(Calendar.DATE, 1);
                now=calendar.getTime();
                String dateString = formatter.format(now);
                Date dd = formatter.parse(dateString);
                nexttime = dd.getTime()+2*60*60*1000;
                break;
            case 3:
                Date preWeekSunday = preWeekSundayCal.getTime();
                String date = formatter.format(preWeekSunday);
                Long timess = formatter.parse(date).getTime()+(24*15+1)*60*60*1000;
                nexttime =  timess;
                break;
            case 4:
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, 1);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                String datestr = format.format(cal.getTime());
                Date date2 = format.parse(datestr);
                nexttime = date2.getTime()+20*60*1000;
                break;
            default:
                break;
        }
        return nexttime;
    }
}

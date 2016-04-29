package bubtjobs.com.icare.Others;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Murtuza on 3/17/2016.
 */
public class CommonFunction {

    public CommonFunction(){

    }
    public  boolean isValidEmail(EditText target)
    {
        if (TextUtils.isEmpty(target.getText().toString())) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target.getText().toString()).matches();
        }
    }
    // Is Empty Check Edittext
    public boolean isEmpty(EditText etText)
    {
        if(etText.getText().toString().trim().length()>0)
            return true;
        return false;
    }
    public  boolean checkButtonValue(Button button,String text){
        if(button.getText().toString().equals(text))
            return false;
        else
            return true;
    }

    // ===============================check valid alarm==================================================
    public Long validAlarm(String YY,String MM,String DD,String formate,int requestHour,int requstMinute) {
        int year=Integer.parseInt(String.valueOf(YY));
        int month=Integer.parseInt(String.valueOf(MM));
        int day=Integer.parseInt(String.valueOf(DD));

        String requestDateString=YY+MM+DD;
        int requestDateInt=Integer.parseInt(requestDateString);

        // current day calculation
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        String monthTemp=String.valueOf(cal.get(Calendar.MONTH) + 1);
        String dayTemp=String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        int currentHour=cal.get(Calendar.HOUR_OF_DAY);
        int currentMinute=cal.get(Calendar.MINUTE);
        int currentSecond=cal.get(Calendar.SECOND);

        if(monthTemp.length()==1)
            monthTemp="0"+monthTemp;
        if(dayTemp.length()==1)
            dayTemp="0"+dayTemp;

        int currentYear=cal.get(Calendar.YEAR);
        int currentMonth=Integer.parseInt(monthTemp);
        int currentDay=Integer.parseInt(dayTemp);


        String currentdateString=currentYear+""+monthTemp+dayTemp;
        int currentdateInt=Integer.parseInt(currentdateString);

        boolean isCorrect=false;

        if(requestDateInt>=currentdateInt)
        {
            //Toast.makeText(getActivity(), "Correct", Toast.LENGTH_SHORT).show();
            if(requestDateInt==currentdateInt)
            {
                if(requestHour==currentHour && requstMinute>currentMinute)
                {
                    isCorrect=true;
                }
                else if(requestHour>currentHour)
                {
                    isCorrect=true;
                }
                else{
                    isCorrect=false;
                }

            }
            else{
                isCorrect=true;
            }
        }
        else{
            isCorrect=false;
        }

        // calculation part
        if(isCorrect==false)
        {
            return Long.parseLong("-1");
            // return "";
        }
        else
        {

            int yearInterval=year-currentYear;
            int monthInterval=Math.abs(month-currentMonth);
            int dayInterval=0;
            if(day>=currentDay)
             dayInterval=Math.abs(day-currentDay);
            else
                dayInterval=Math.abs(currentDay-day-30);

            int hourInterval=Math.abs(requestHour-currentHour);
            int minuteInterval=Math.abs(requstMinute-currentMinute);

            Log.i("time and date",yearInterval+"=year "+monthInterval+" =month "+dayInterval+" =day "+hourInterval+" hour "+minuteInterval);

            // Toast.makeText(getActivity(), yearInterval+" m="+monthInterval+" d "+dayInterval+" h "+hourInterval+" m "+minuteInterval, Toast.LENGTH_SHORT).show();

            // constant value
            int dayAalarmManager=86400000;
            int hourAlarmManager=3600000;
            int minuteAlarmManager=60000;
            int secondAlarmManager=1000;

            Long Year =Long.parseLong(String.valueOf(365*yearInterval*dayAalarmManager));
            Long Month =Long.parseLong(String.valueOf(monthInterval*dayAalarmManager));
            Long Day =Long.parseLong(String.valueOf(dayInterval*dayAalarmManager));
            Long Hour =Long.parseLong(String.valueOf(hourInterval*hourAlarmManager));
            Long Minute =Long.parseLong(String.valueOf(minuteInterval*minuteAlarmManager));

            Long finalInterval=Year+Month+Day+Hour+Minute-(secondAlarmManager*currentSecond);
            Log.i("time and date",""+finalInterval);

            return finalInterval;
            //return " y="+Year+" m= "+Month+" d= "+Day+" h= "+Hour+"  m= "+Minute+" c="+currentHour;
        }
    }
    // =============================== alarmCode Generate ==================================================
    public int alarmCodeGenerate(){
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        String y=String.valueOf(cal.get(Calendar.YEAR));
        String m=String.valueOf(cal.get(Calendar.MONTH));
        String d=String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String h=String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        String min=String.valueOf(cal.get(Calendar.MINUTE));
        String sec=String.valueOf(cal.get(Calendar.SECOND));

        int code=Integer.parseInt(String.valueOf(m + d + h + min + sec));
        return code;
    }

    public String currentDate(){
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        String year=String.valueOf(cal.get(Calendar.YEAR));
        String monthTemp=String.valueOf(cal.get(Calendar.MONTH) + 1);
        String dayTemp=String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

        if(monthTemp.length()==1)
            monthTemp="0"+monthTemp;
        if(dayTemp.length()==1)
            dayTemp="0"+dayTemp;

        return year+monthTemp+dayTemp;

    }
}

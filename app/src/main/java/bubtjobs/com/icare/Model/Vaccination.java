package bubtjobs.com.icare.Model;

/**
 * Created by Murtuza on 4/22/2016.
 */
public class Vaccination {
    private String userId;
    private String va_name;
    private String date;
    private String hour;
    private String minute;
    private String formate;
    private String details;
    private String alarmType;
    private String alarmCode;
    private String status;

    public Vaccination(){}
    public Vaccination(String userId, String va_name, String date, String hour, String minute, String formate, String details,String alarmType, String alarmCode, String status) {
        this.userId = userId;
        this.va_name = va_name;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.formate = formate;
        this.details=details;
        this.alarmType = alarmType;
        this.alarmCode = alarmCode;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVa_name() {
        return va_name;
    }

    public void setVa_name(String va_name) {
        this.va_name = va_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getFormate() {
        return formate;
    }

    public void setFormate(String formate) {
        this.formate = formate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package bubtjobs.com.icare.Model;

/**
 * Created by Murtuza on 4/21/2016.
 */
public class Diet_Input {
    private String userId;
    private String dietType;
    private String menu;
    private String date;
    private String hour;
    private String minute;
    private String formate;
    private String alarmType;
    private String alarmCode;
    private String status;
    private String id;
    private String isDailyAlarm;

    public Diet_Input(){}

    public Diet_Input(String userId, String dietType, String menu, String date, String hour, String minute, String formate, String alarmType, String alarmCode,String isDailyAlarm, String status) {
        this.userId = userId;
        this.dietType = dietType;
        this.menu = menu;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.formate = formate;
        this.alarmType = alarmType;
        this.alarmCode = alarmCode;
        this.isDailyAlarm=isDailyAlarm;
        this.status = status;
    }

    public Diet_Input(String id, String dietType, String menu, String date, String hour, String minute, String formate, String alarmType,String isDailyAlarm) {
        this.id = id;
        this.dietType = dietType;
        this.menu = menu;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.formate = formate;
        this.alarmType = alarmType;
        this.isDailyAlarm=isDailyAlarm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDietType() {
        return dietType;
    }

    public void setDietType(String dietType) {
        this.dietType = dietType;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
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

    public String getIsDailyAlarm() {
        return isDailyAlarm;
    }

    public void setIsDailyAlarm(String isDailyAlarm) {
        this.isDailyAlarm = isDailyAlarm;
    }
}

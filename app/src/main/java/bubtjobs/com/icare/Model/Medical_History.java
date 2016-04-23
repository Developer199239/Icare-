package bubtjobs.com.icare.Model;

/**
 * Created by Murtuza on 4/23/2016.
 */
public class Medical_History {
    private String tableId;
    private String userId;
    private String image;
    private String doctorName;
    private String details;
    private String date;
    private String status;

    public Medical_History(){}
    public Medical_History(String userId, String image, String doctorName, String details, String date,String status) {
        this.userId = userId;
        this.image = image;
        this.doctorName = doctorName;
        this.details = details;
        this.date = date;
        this.status=status;
    }

    public Medical_History(String tableId, String userId, String image, String doctorName, String details, String date,String status) {
        this.tableId = tableId;
        this.userId = userId;
        this.image = image;
        this.doctorName = doctorName;
        this.details = details;
        this.date = date;
        this.status=status;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

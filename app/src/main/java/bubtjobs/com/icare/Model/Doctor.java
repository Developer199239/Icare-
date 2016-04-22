package bubtjobs.com.icare.Model;

/**
 * Created by Murtuza on 4/22/2016.
 */
public class Doctor {
    private String id;
    private String userId;
    private String name;
    private String details;
    private String phone;
    private String email;
    private String status;

    public Doctor(){}
    public Doctor(String userId, String name, String details, String phone, String email, String status) {
        this.userId = userId;
        this.name = name;
        this.details = details;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }
    public Doctor(String id,String userId, String name, String details, String phone, String email, String status) {
        this.id=id;
        this.userId = userId;
        this.name = name;
        this.details = details;
        this.phone = phone;
        this.email = email;
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

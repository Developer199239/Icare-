package bubtjobs.com.icare.Model;

/**
 * Created by Murtuza on 4/19/2016.
 */
public class Profile {
    private String userId;
    private String userName;

    public Profile() {}

    public Profile(String userId, String userName) {
        setUserId(userId);
        setUserName(userName);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

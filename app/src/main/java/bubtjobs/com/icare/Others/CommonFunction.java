package bubtjobs.com.icare.Others;

import android.text.TextUtils;
import android.widget.EditText;

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
}

package moneygroup.devufa.ru.moneygroup.service.registration;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RegistrationService {

    private static final String BASE_URL = "http://base_url/";
    public static final String GET_CONFIRMATION_CODE = "person/registerRequest/";

    public void sendConfirmationCode(final String number) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL confirmCodeUrl = new URL(BASE_URL + GET_CONFIRMATION_CODE);
                    HttpURLConnection confirmationCodeConnection = (HttpURLConnection) confirmCodeUrl.openConnection();
                    confirmationCodeConnection.setRequestProperty("telephoneNumber", number);
                    if (confirmationCodeConnection.getResponseCode() == 200) {

                    }
                } catch (MalformedURLException mue) {
                    mue.getStackTrace();
                } catch (IOException ioe) {
                    ioe.getStackTrace();
                }
            }
        });
    }
}

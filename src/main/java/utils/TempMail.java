package utils;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Email;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

public class TempMail {
    static String NO_EMAILS_YET_ERR_MSG = "There are no emails yet";

    private static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.temp-mail.ru/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();


    private interface TempMailService {

        @GET("request/domains/format/json/")
        Call<List<String>> listDomains();


        @GET("request/mail/id/{MD5}/format/json/")
        Call<List<Email>> listEmails(@Path("MD5") String md5);
    }

    public static class NoEmailsException extends Exception {
        public NoEmailsException() {
            super(NO_EMAILS_YET_ERR_MSG);
        }
    }

    private class TempMailError {
        private String error;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

    public static List<String> getDomainList() throws IOException {

        Response<List<String>> response = tempMailService().listDomains().execute();

        if (response.isSuccessful()) {
            return response.body();
        }

        if (response.errorBody() != null) {
            TempMailError error = (TempMailError) retrofit.responseBodyConverter(
                    TempMailError.class, TempMailError.class.getAnnotations())
                    .convert(response.errorBody());

            throw new RuntimeException(error.getError());
        }

        throw new RuntimeException(response.message());

    }

    public static String generateEmailAddress() throws IOException{
        List<String> domains = getDomainList();
        return UUID.randomUUID() + domains.get(0);
    }

    public static List<Email> getEmailList(String emailAddress) throws IOException, NoSuchAlgorithmException, NoEmailsException {
        Response<List<Email>> response = tempMailService().listEmails(getMD5Hash(emailAddress)).execute();

        if(response.isSuccessful()) {
            return response.body();
        }

        if(response.errorBody() != null){
            TempMailError error = (TempMailError) retrofit.responseBodyConverter(
                    TempMailError.class, TempMailError.class.getAnnotations())
                    .convert(response.errorBody());

            if (error.getError().equals(NO_EMAILS_YET_ERR_MSG)) {
                throw new NoEmailsException();
            }

            throw new RuntimeException(error.getError());
        }

        throw new RuntimeException(response.message());
    }

    public static List<Email> waitForEmails(String emailAddress, long timeout)throws IOException, NoSuchAlgorithmException, InterruptedException, NoEmailsException {
        long deadline = System.currentTimeMillis() + timeout;

        while (true) {
            try {
                return getEmailList(emailAddress);
            } catch (NoEmailsException e) {
                if (System.currentTimeMillis() > deadline) {
                    throw e;
                }
            }

            Thread.sleep(2000);
        }
    }

    private static String getMD5Hash(final String input) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("MD5");
        final byte[] bytes = md.digest(input.getBytes());
        String result = "";
        for (int i = 0; i < bytes.length; ++i) {
            result += Integer.toHexString((bytes[i] & 0xFF) | 0x100).substring(1, 3);
        }
        return result;
    }

    private static TempMailService tempMailService(){

        return retrofit.create(TempMailService.class);
    }
}

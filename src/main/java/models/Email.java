package models;

public class Email {

    private double mailTimestamp;
    private String mailID;
    private String mailAddressID;
    private String mailFrom;
    private String mailSubject;
    private String mailPreview;
    private String mailTextOnly;
    private String mailText;
    private String mailHtml;

    public double getMailTimestamp() {
        return mailTimestamp;
    }

    public void setMailTimestamp(double mailTimestamp) {
        this.mailTimestamp = mailTimestamp;
    }

    public String getMailID() {
        return mailID;
    }

    public void setMailID(String mailID) {
        this.mailID = mailID;
    }

    public String getMailAddressID() {
        return mailAddressID;
    }

    public void setMailAddressID(String mailAddressID) {
        this.mailAddressID = mailAddressID;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailPreview() {
        return mailPreview;
    }

    public void setMailPreview(String mailPreview) {
        this.mailPreview = mailPreview;
    }

    public String getMailTextOnly() {
        return mailTextOnly;
    }

    public void setMailTextOnly(String mailTextOnly) {
        this.mailTextOnly = mailTextOnly;
    }

    public String getMailText() {
        return mailText;
    }

    public void setMailText(String mailText) {
        this.mailText = mailText;
    }

    public String getMailHtml() {
        return mailHtml;
    }

    public void setMailHtml(String mailHtml) {
        this.mailHtml = mailHtml;
    }

    @Override
    public String toString() {
        return "Email [mailTimestamp=" + mailTimestamp + ", mailID=" + mailID + ", mailAddressID=" + mailAddressID
                + ", mailFrom=" + mailFrom + ", mailSubject=" + mailSubject + ", mailPreview=" + mailPreview
                + ", mailTextOnly=" + mailTextOnly + ", mailText=" + mailText + ", mailHtml=" + mailHtml + "]";
    }
}

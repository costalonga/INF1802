import java.util.Date;

public class Tweet {

    private String user_name;
    private String text;
    private Date date;

    public Tweet(String user_name, String text, Date date){
        this.user_name = user_name;
        this.text = text;
        this.date = date;
    }

    public String getUserName(){
        return user_name;
    }
    public String getText(){
        return text;
    }
    public Date getDate(){
        return date;
    }
}

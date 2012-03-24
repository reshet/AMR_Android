package db;

import java.util.Date;
import java.util.regex.*;

public class Regular {
    String date = "(0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).(20\\d\\d)(\\s+)([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
    
    public Regular(){}
    
    public boolean check(String s){
        Pattern p1 = Pattern.compile("(\\s*)"+this.date+"(\\s*)");
        Matcher m1 = p1.matcher(s);
        return m1.matches();
    }
    public Date find (String s){
        Date d = new Date();
        Pattern pattern = Pattern.compile(this.date);
        Matcher matcher = pattern.matcher(s);   
        String d1 = "";
        while(matcher.find()){
            d1 = matcher.group();
        }
        String [] s1 = d1.split(" ");
        int n1 = s1[0].indexOf(".");
        d.setDate(Integer.parseInt(s1[0].substring(0, n1)));
        int n2 = s1[0].substring(n1+1).indexOf(".");
        d.setMonth(Integer.parseInt(s1[0].substring(n1+1).substring(0, n2))-1);
        d.setYear(Integer.parseInt(s1[0].substring(n1+1).substring(n2+1))-1900);
        String [] time = s1[1].split(":");
        d.setHours(Integer.parseInt(time[0]));
        d.setMinutes(Integer.parseInt(time[1]));
        d.setSeconds(Integer.parseInt(time[2]));        
        return d;
    }
                
}
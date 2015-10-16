/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssagregator.utils;

/**
 *
 * @author Clément RILLON
 */


import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class SqlDateAdapter extends XmlAdapter<String, Date> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String marshal(Date v) throws Exception {
        
        if(v==null){
            return null;
        }
        
        return dateFormat.format(v);
    }

    @Override
    public Date unmarshal(String v) throws Exception {
        
        if(v == null){
            return null;
        }
        return dateFormat.parse(v);
    }

}
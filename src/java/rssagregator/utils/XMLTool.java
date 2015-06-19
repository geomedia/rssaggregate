/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssagregator.utils;


import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;


/**
 *      Cette classe contient un des méthode static permettant de manier du XML. Notamment des outils pour sérialiser et désérialiser des objets. 
 * @author Clément RILLON
 */
public class XMLTool {

    public static String serialise(Object bean) throws IOException {
//                    FileInputStream fileInputStream = new FileInputStream("saveXML.xml");
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(arrayOutputStream);
        String retour = null;

        try {
            encoder.writeObject(bean);
            encoder.flush();
        } catch (Exception e) {
        } finally {
            arrayOutputStream.close();
            encoder.close();
        }
        retour = arrayOutputStream.toString();
        return retour;
    }

    /***
     * Désérialize à partir de la chaine de caractère; la chaine doit contenir un contenu xml obtenu à partir de la méthode serilize de la même classe.
     * @param xml
     * @return
     * @throws IOException 
     */
    public static Object unSerialize(String xml) throws IOException {
        StringReader reader = new StringReader(xml);

        InputStream is = new ByteArrayInputStream(xml.getBytes());
        XMLDecoder decoder = new XMLDecoder(is);

        Object bean = null;

        try {
            bean = decoder.readObject();
        } catch (Exception e) {
        } finally {
            decoder.close();
            is.close();
            reader.close();
        }
        return bean;
    }
    
/***
 * Désérialize à partir d'un inputStream. L'input Stream doit contenir du code XML Généré à partir de la méthode sérialize.
 * @param in
 * @return 
 */    
    public static Object unSerialize(InputStream in){
        XMLDecoder decoder = new XMLDecoder(in);
        Object bean = null;
        try {
            bean = decoder.readObject();
        } catch (Exception e) {
        }
        finally{
            decoder.close();
        }
        return bean;
    }
    

    // TEST DE LA DESERIALISATION
    public static void main(String[] args) {

//        Object bean = new Object();
//        bean.setID(2);
//        bean.setMessage("toto");
//
//        String r = null;
//        try {
//            r = XMLTool.serialise(bean);
//        } catch (IOException ex) {
//            Logger.getLogger(XMLTool.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//
//        MessageBean mb;
//        try {
//            mb = XMLTool.unSerialize(r);
//
//        } catch (IOException ex) {
//            Logger.getLogger(XMLTool.class.getName()).log(Level.SEVERE, null, ex);
//        }



    }
//        // TEST DE LA SERIALISATION
//            public static void main(String[] args) {
//        // Test de la serialisation 
//        
//        MessageBean bean = new MessageBean();
//        bean.setID(2);
//        bean.setMessage("toto");
//        
//        String r = XMLTool.serialise(bean);
//        
//    }
}

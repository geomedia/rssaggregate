/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssagregator.beans;

import java.util.Date;

/**
 * Interface permettant de manier les variale d'un contenu de type RSS {@link Item} ou {@link ItemRaffinee}
 *
 * @author Clément RILLON
 */
public interface ContentRSS {

    public String getTitre();

    public void setTitre(String titre);

    public Date getDatePub();

    public void setDatePub(Date datePub);

    public Date getDateRecup();

    public void setDateRecup(Date dateRecup);

    public String getHashContenu();

    public void setHashContenu(String hashContenu);

    public String getDescription();

    public void setDescription(String description);

    public String getLink();

    public void setLink(String link);

    public String getGuid();

    public void setGuid(String guid);

    public Long getID();

    public void setID(Long ID);

    public String getCategorie();

    public void setCategorie(String categorie);
    
    public String getContenu();

    public void setContenu(String contenu);
      
}

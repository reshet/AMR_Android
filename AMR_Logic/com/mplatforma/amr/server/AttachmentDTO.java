/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mplatforma.amr.server;

import java.io.Serializable;

/**
 *
 * @author Kate Nezdoly
 */

public class AttachmentDTO implements Serializable {
    private static final long serialVersionUID = 1L;
  
    private Integer id;
    
    private String name;
 
    public AttachmentDTO() {
    }

    public AttachmentDTO(Integer id,String name) {
        this.id = id;
        this.name = name;
    }

    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AttachmentDTO)) {
            return false;
        }
        AttachmentDTO other = (AttachmentDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Attachment[ id=" + id + " ]";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
}

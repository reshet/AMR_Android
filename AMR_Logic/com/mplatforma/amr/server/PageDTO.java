/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mplatforma.amr.server;

import java.io.Serializable;
import java.util.Collection;


/**
 *
 * @author Kate Nezdoly
 */
public class PageDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Collection<AttachmentDTO> attachmentCollection;

    public PageDTO() {
    }

    public PageDTO(Integer id, Collection<AttachmentDTO> attachmentCollection) {
        this.id = id;
        this.attachmentCollection = attachmentCollection;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Collection<AttachmentDTO> getAttachmentCollection() {
        return attachmentCollection;
    }

    public void setAttachmentCollection(Collection<AttachmentDTO> attachmentCollection) {
        this.attachmentCollection = attachmentCollection;
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
        if (!(object instanceof PageDTO)) {
            return false;
        }
        PageDTO other = (PageDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Page[ id=" + id + " ]";
    }
}

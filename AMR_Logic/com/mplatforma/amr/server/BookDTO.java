/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mplatforma.amr.server;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Kate Nezdoly
 */

public class BookDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private List<PageDTO> pageCollection;
    
    public BookDTO() {
    }

    public BookDTO(Integer id,String name, List<PageDTO> pageCollection) {
        this.name = name;
        this.id = id;
        this.pageCollection = pageCollection;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<PageDTO> getPageCollection() {
        return pageCollection;
    }

    public void setPageCollection(List<PageDTO> pageCollection) {
        this.pageCollection = pageCollection;
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
        if (!(object instanceof BookDTO)) {
            return false;
        }
        BookDTO other = (BookDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Book[ id=" + id + " ]";
    }

    /**
     * @return the idCustomer
     */
}

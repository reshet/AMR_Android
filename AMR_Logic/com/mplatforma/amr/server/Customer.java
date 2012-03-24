/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mplatforma.amr.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Kate Nezdoly
 */


public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String login;
    private String password;
    private Collection<BookDTO> bookCollection;

    public Customer() {
    }

//    public Customer(String login, String password, Collection<Book> bookCollection) {
//        this.login = login;
//        this.password = password;
//        this.bookCollection = bookCollection;
//    }
    
    public Customer(String login, String password) {
        this.login = login;
        this.password = password;
        this.bookCollection = new ArrayList();
    }

   
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<BookDTO> getBookCollection() {
        return bookCollection;
    }

    public void setBookCollection(Collection<BookDTO> bookCollection) {
        this.bookCollection = bookCollection;
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
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ id=" + id + " ]";
    }
}

package com.example.email.criteria;

public class FactoryCriteria {
    public Criteria getCriteria(String criteria){
        if(criteria.equalsIgnoreCase("Inbox")){
            return new CriteriaInbox();
        } else if (criteria.equalsIgnoreCase("Draft")) {
            return new CriteriaDraft();
        }else if (criteria.equalsIgnoreCase("Contact")) {
            return new CriteriaContact();
        }else if (criteria.equalsIgnoreCase("Sent")) {
            return new CriteriaSent();
        }else if (criteria.equalsIgnoreCase("Trash")) {
            return new CriteriaTrash();
        }else if(criteria.equalsIgnoreCase("Important")){
            return new CriteriaImportant();
        } else if (criteria.equalsIgnoreCase("Read")) {
            return new CriteriaRead();
        }else if (criteria.equalsIgnoreCase("UnRead")) {
            return new CriteriaUnRead();
        }
        return null;
    }
}

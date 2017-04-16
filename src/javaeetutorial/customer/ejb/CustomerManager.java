/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package javaeetutorial.customer.ejb;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javaeetutorial.customer.data.Customer;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;

/**
 *
 * @author ievans
 */
@Model
public class CustomerManager implements Serializable {
	private static final long serialVersionUID = 1L;
	private Customer customer;
    private List<Customer> customers;
    private static final Logger logger = Logger.getLogger(CustomerManager.class.getName());
    @EJB
    private CustomerBean customerBean;
    
    @PostConstruct
    private void init() {
        //logger.info("new customer created");
        logger.info("INIT===INIT===INIT===INIT===INIT===INIT===INIT===INIT===INIT===INIT===INIT===INIT===INIT===INIT===INIT===INIT===");
        customer = new Customer();
        setCustomers(customerBean.retrieveAllCustomers());
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        logger.info("######################################################################################################################");
        logger.info("getCustomer===getCustomer===getCustomer===getCustomer===getCustomer===getCustomer===getCustomer===getCustomer===getCustomer===");
        logger.info("######################################################################################################################");
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the customers
     */
    public List<Customer> getCustomers() {
        logger.info("######################################################################################################################");
    	logger.info("getAllCustomers===getAllCustomers===getAllCustomers===getAllCustomers===getAllCustomers===getAllCustomers===getAllCustomers===");
        logger.info("######################################################################################################################");        
    	return customerBean.retrieveAllCustomers();
    }

    /**
     * @param customers the customers to set
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
		logger.info("*************************************************************************************************");
		logger.info("*************************************************************************************************");
		logger.info("*************************************************************************************************");
		logger.info("*********************************** setCustomers(List<Customer> customers): *********************");
		logger.info("*************************************************************************************************");
		logger.info("*************************************************************************************************");
		logger.info("*************************************************************************************************");
		logger.info("*************************************************************************************************");
    }

}

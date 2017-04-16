/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package javaeetutorial.customer.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaeetutorial.customer.data.Customer;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author ievans
 */
@Named
@Stateless
public class CustomerBean {

	protected Client client;
	private Customer customer;
	private static final Logger logger = Logger.getLogger(CustomerBean.class.getName());

	@PostConstruct
	private void init() {
		// client = ClientBuilder.newClient();
	}

	@PreDestroy
	private void clean() {
		// client.close();
	}

	public String createCustomer(Customer customer) {
		if (customer == null) {
			logger.log(Level.WARNING, "customer is null.");
			return "customerError";
		}
		String navigation;
		client = ClientBuilder.newClient();
		Response response = client.target("http://localhost:8080/customer/webapi/Customer")
				.request(MediaType.APPLICATION_XML)
				.post(Entity.entity(customer, MediaType.APPLICATION_XML), Response.class);
		client.close();
		if (response.getStatus() == Status.CREATED.getStatusCode()) {
			navigation = "customerCreated";
		} else {
			logger.log(Level.WARNING, "Could not create customer with id {0}. Status returned was {1}",
					new Object[] { customer.getId(), response.getStatus() });
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Could not create customer."));
			navigation = "customerError";
		}
		return navigation;
	}

	public String retrieveCustomer(String id) {
		String navigation;
		client = ClientBuilder.newClient();
		customer = client.target("http://localhost:8080/customer/webapi/Customer").path(id)
				.request(MediaType.APPLICATION_XML).get(Customer.class);
		client.close();
		if (customer == null) {
			navigation = "customerError";
		} else {
			navigation = "customerRetrieved";
		}
		logger.info("*************************************************************************************************");
		logger.info("*************************************************************************************************");
		logger.info("*************************************************************************************************");
		logger.info("***********************************retrieveCustomer: customer.toString())************************");
		logger.info(customer.toString());
		logger.info("*************************************************************************************************");
		logger.info("*************************************************************************************************");
		logger.info("*************************************************************************************************");
		logger.info("*************************************************************************************************");
		
		return navigation;
	}

	public List<Customer> retrieveAllCustomers() {
		client = ClientBuilder.newClient();
		List<Customer> customers = client.target("http://localhost:8080/customer/webapi/Customer").path("all")
				.request(MediaType.APPLICATION_XML).get(new GenericType<List<Customer>>() {
				});
		client.close();
		return customers;
	}
	
	public String editCustomer(Customer customer) {
		if (customer == null) {
			logger.log(Level.WARNING, "customer is null.");
			return "customerError";
		}
		logger.info("*************************************************************************************************");
		logger.info("*************************************************************************************************");
		logger.info("************************* editCustome(Customer customer): ************************************************************************");
		logger.log(Level.WARNING, "customer id is: " + customer.getId());
		logger.log(Level.WARNING, "customer FirstName is: " + customer.getFirstname());
		logger.info("*************************************************************************************************");
		logger.info("*************************************************************************************************");
		logger.info("*************************************************************************************************");
		
		String navigation;
		client = ClientBuilder.newClient();
		
		Response response = client.target("http://localhost:8080/customer/webapi/Customer").path(String.valueOf(customer.getId()))
				.request(MediaType.APPLICATION_XML)
				.put(Entity.entity(customer, MediaType.APPLICATION_XML), Response.class);
		client.close();
		
		if (response.getStatus() == Status.OK.getStatusCode()) {
			navigation = "customerEdited";
		} else {
			logger.log(Level.WARNING, "Could not update customer with id {0}. Status returned was {1}",
					new Object[] { customer.getId(), response.getStatus() });
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Could not edit customer."));
			navigation = "customerError";
		}
		
		return navigation;
	}
	
	public void deleteCustomer(String id) {
		client = ClientBuilder.newClient();
		
		Response response = client.target("http://localhost:8080/customer/webapi/Customer").path(id)
				.request(MediaType.APPLICATION_XML)
				.delete(Response.class);
		
		client.close();
		
		if (response.getStatus() == Status.OK.getStatusCode()) {
			logger.info("*************************************************************************************************");
			logger.info("*************************************************************************************************");
			logger.info("********************************** deleteCustomer(String id): ***********************************");
			logger.info("*************************************************************************************************");
			logger.info("id para delete " + id);
			logger.info("*************************************************************************************************");
			logger.info("*************************************************************************************************");
			logger.info("*************************************************************************************************");
			logger.info("*************************************************************************************************");
		} else {
			logger.log(Level.WARNING, "Could not create customer with id {0}. Status returned was {1}",
					new Object[] { customer.getId(), response.getStatus() });
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Could not delete customer."));
		}
	}

	public Customer getCustomer() {
		return customer;
	}

	
}

package com.ecommerce.ea.services;

import com.ecommerce.ea.entities.Customer;
import com.ecommerce.ea.entities.Store;
import com.ecommerce.ea.entities.UserAcc;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.ICustomer;
import com.ecommerce.ea.repository.CustomerRepository;
import com.ecommerce.ea.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService implements ICustomer {

    private final CustomerRepository customerRepository;
    private final StoreService storeService;
    private final UserRepository userRepository;

    public CustomerService(CustomerRepository customerRepository, StoreService storeService, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.storeService = storeService;
        this.userRepository = userRepository;
    }

    @Override
    public Customer addCustomer(UserAcc user, String storeName) {
        Store store = storeService.findStoreByStoreName(storeName);

        Customer customerObj = new Customer();
        customerObj.setUser(user);
        customerObj.setStore(store);
        //Save Object and return it
        return customerRepository.save(customerObj);
    }

    @Override
    public Customer findCustomerByUserIdAndStoreName(UUID userId, String storeName) {
        /// Retrieve the object from the database base on the customerId and StoreName
       return customerRepository.findCustomerByUser(userId,storeName)
                .orElseThrow(() -> new BadRequestException("customerId was not found on the database"));

    }

    /// Checks if there is a customer with the store relation already using the userId and StoreName
    @Override
    public Boolean customerCreationVerification(UUID userId, String storeName) {
        List<Customer> customerList = customerRepository.findAllCustomerByUser(userId);
        for (Customer customer : customerList) {
            String storeNameObj = customer.getStore().getStoreName().toLowerCase();
            if (storeNameObj.equals(storeName.toLowerCase())) {
                return true;
            }
        }
            return false;
    }

    @Override
    public Customer findCustomerById(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new BadRequestException("customerId not found on the database"));
    }
}



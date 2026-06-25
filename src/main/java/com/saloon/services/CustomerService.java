package com.saloon.services;

import com.dolphindoors.resource.jaxrs.PageableRequest;
import com.dolphindoors.resource.jaxrs.PagedResult;
import com.dolphindoors.resource.jpa.CrudApi;
import com.dolphindoors.resource.jpa.Pagination;
import com.dolphindoors.resource.jpa.QueryBuilder;
import com.dolphindoors.resource.utilities.JUtils;
import com.saloon.dto.CustomerDto;
import com.saloon.entity.Customer;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.inject.Inject;

/**
 *
 * @author richardnarh
 */
public class CustomerService {
    @Inject private CrudApi crudApi;
    
    public CustomerDto save(CustomerDto typeDto) {
        Customer customer = toEntity(typeDto);
        CustomerDto dto = null;
        if(crudApi.save(customer) != null){
            dto = toDto(customer);
        }
        return dto;
    }
    
    public PagedResult<CustomerDto> fetchAllCustomers(PageableRequest pageable) {
        Pagination<Customer> pager = new Pagination<>(Customer.class, crudApi.getEm());
        QueryBuilder<Customer> builder = Customer.query(crudApi.getEm());
        List<CustomerDto> dtoList = pager.getPage(pageable, builder)
                .stream()
                .map(customer -> toDto(customer))
                .collect(Collectors.toList());
        long total = builder.count();
        int totalPages = (int)Math.ceil((double)total / pageable.getSize());
        return new PagedResult<>(dtoList, pageable.getPage(), pageable.getSize(), total, totalPages);
    }

    public PagedResult<CustomerDto> filterCustomers(String filter, PageableRequest pageable) {
        Pagination<Customer> pager = new Pagination<>(Customer.class, crudApi.getEm());
        Consumer<QueryBuilder<Customer>> builder = qb -> {
            String searchKey = filter != null ? filter.trim() : "";
            if(!searchKey.isEmpty()){
                qb.andWhereLikeIgnoreCase(Customer._name, "%" + searchKey + "%");
                qb.orWhereLikeIgnoreCase(Customer._phoneNumber, "%" + searchKey + "%");
            }
        };
        
        List<CustomerDto> dtoList = pager.getPage(pageable, builder)
                .stream()
                .map(customer -> toDto(customer))
                .collect(Collectors.toList());
        long total = pager.getCount(builder);
        int totalPages = (int)Math.ceil((double)total / pageable.getSize());
        return new PagedResult<>(dtoList, pageable.getPage(), pageable.getSize(), total, totalPages);
    }
    
    
    public CustomerDto findCustomerById(String productTypeId) {
        Customer customer = crudApi.find(Customer.class, productTypeId);
        return toDto(customer);
    }
    
    public boolean deleteCustomer(String customerId) {
        Customer customer = crudApi.find(Customer.class, customerId);
        return customer != null ? crudApi.delete(customer) : false;
    }
    
    
    public Customer toEntity(CustomerDto dto) {
        Customer customer = new Customer();
        if(dto.getId() != null){
            customer.setId(dto.getId());
        }
        customer.setAddress(dto.getAddress());
        customer.setClientSource(dto.getClientSource());
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setDescription(dto.getDescription());
        if(dto.getCode() == null){
            customer.setCode(JUtils.generate(5));
        }
        return customer;
    }

    public CustomerDto toDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        if(customer.getId() == null)return null;
        dto.setId(customer.getId());
        if(dto.getId() != null){
            customer.setId(customer.getId());
        }
        dto.setAddress(customer.getAddress());
        dto.setClientSource(customer.getClientSource());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setDescription(customer.getDescription());
        if(dto.getCode() == null){
            dto.setCode(customer.getCode());
        }
        return dto;
    }
     
}

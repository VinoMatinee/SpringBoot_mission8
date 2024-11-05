package com.ucamp.jpa.service;

import com.ucamp.jpa.dto.req.CustomerReqDto;
import com.ucamp.jpa.dto.req.CustomerReqForm;
import com.ucamp.jpa.dto.res.CustomerRespDto;
import com.ucamp.jpa.entity.Customer;
import com.ucamp.jpa.exception.BusinessException;
import com.ucamp.jpa.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    // 고객 생성 (Create)
    @Transactional
    public CustomerRespDto makeCustomer(CustomerReqDto customerReqDto) {
        // reqDto -> Entity
        Customer customer = modelMapper.map(customerReqDto, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerRespDto.class);
    }

    // (Update)
    @Transactional
    public CustomerRespDto changeCustomer(String email, CustomerReqDto customerReqDto) {
        Customer customerEntity = customerRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(email + " User Not Found", HttpStatus.NOT_FOUND));
        customerEntity.setName(customerReqDto.getName());
        return modelMapper.map(customerEntity, CustomerRespDto.class);
    }

    // 아이디로 고객 찾기 (Read)
    public CustomerRespDto fetchCustomer(Long id) {
        return customerRepository.findById(id)
                .map(customerEntity -> modelMapper.map(customerEntity, CustomerRespDto.class))
                .orElseThrow(() -> new BusinessException("Customer Not Found", HttpStatus.NOT_FOUND));
    }

    // 아이디로 고객 찾기 (Read)
    public CustomerRespDto fetchCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(customerEntity -> modelMapper.map(customerEntity, CustomerRespDto.class))
                .orElseThrow(() -> new BusinessException("Customer Not Found", HttpStatus.NOT_FOUND));
    }

    // 전체 고객 찾기(AllRead)
    public List<CustomerRespDto> fetchCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList.stream()
                .map(customer -> modelMapper.map(customer, CustomerRespDto.class))
                .toList();
    }

    // 고객 삭제(Delete)
    @Transactional
    public void removeCustomer(Long id) {
        Customer customerEntity = customerRepository
                .findById(id).orElseThrow(() -> new BusinessException("Customer Not Found", HttpStatus.NOT_FOUND));
        customerRepository.delete(customerEntity);
    }

    @Transactional
    public void changeUpdateForm(CustomerReqForm customerReqForm) {
        Customer existingCustomer = customerRepository
                .findById(customerReqForm.getId())
                .orElseThrow(()-> new BusinessException(customerReqForm.getId() +" Customer Not Found", HttpStatus.NOT_FOUND));
        existingCustomer.setName(customerReqForm.getName());
        existingCustomer.setAge(customerReqForm.getAge());
        //customerRepository.save(existingCustomer);
    }

}

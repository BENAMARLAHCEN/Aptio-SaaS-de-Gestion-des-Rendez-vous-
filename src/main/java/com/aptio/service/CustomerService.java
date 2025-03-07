package com.aptio.service;

import com.aptio.dto.CustomerDTO;
import com.aptio.dto.CustomerNoteDTO;
import com.aptio.exception.ResourceNotFoundException;
import com.aptio.exception.ValidationException;
import com.aptio.model.Customer;
import com.aptio.model.CustomerNote;
import com.aptio.repository.CustomerNoteRepository;
import com.aptio.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerNoteRepository customerNoteRepository;
    private final ModelMapper modelMapper;

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        // Check if email exists
        if (customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new ValidationException("Email is already in use");
        }

        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setRegistrationDate(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);

        return modelMapper.map(savedCustomer, CustomerDTO.class);
    }

    @Transactional
    public CustomerDTO updateCustomer(String id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        // Check if email exists and not the same customer
        if (!customer.getEmail().equals(customerDTO.getEmail()) &&
                customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new ValidationException("Email is already in use");
        }

        // Update fields
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());
        customer.setBirthDate(customerDTO.getBirthDate());
        customer.setGender(customerDTO.getGender());
        customer.setProfileImage(customerDTO.getProfileImage());

        Customer updatedCustomer = customerRepository.save(customer);
        return modelMapper.map(updatedCustomer, CustomerDTO.class);
    }

    @Transactional
    public void deleteCustomer(String id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer", "id", id);
        }
        customerRepository.deleteById(id);
    }

    @Transactional
    public CustomerDTO toggleCustomerStatus(String id, boolean active) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        customer.setActive(active);
        Customer updatedCustomer = customerRepository.save(customer);

        return modelMapper.map(updatedCustomer, CustomerDTO.class);
    }

    public List<CustomerDTO> searchCustomers(String query) {
        return customerRepository.searchCustomers(query).stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomerNoteDTO addCustomerNote(String customerId, CustomerNoteDTO noteDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        CustomerNote note = modelMapper.map(noteDTO, CustomerNote.class);
        note.setCustomer(customer);
        note.setCreatedAt(LocalDateTime.now());

        customer.addNote(note);
        customerRepository.save(customer);

        return modelMapper.map(note, CustomerNoteDTO.class);
    }

    public List<CustomerNoteDTO> getCustomerNotes(String customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer", "id", customerId);
        }

        return customerNoteRepository.findByCustomerIdOrderByCreatedAtDesc(customerId).stream()
                .map(note -> modelMapper.map(note, CustomerNoteDTO.class))
                .collect(Collectors.toList());
    }
}
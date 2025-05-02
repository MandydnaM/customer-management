package com.cmt.customerservice.controller;

import com.cmt.customerservice.dto.CustomerRequestDTO;
import com.cmt.customerservice.dto.CustomerResponseDTO;
import com.cmt.customerservice.dto.validators.CreateCustomerValidationGroup;
import com.cmt.customerservice.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customer", description = "API for managing Customers")
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping
  @Operation(summary = "Get Customers")
  public ResponseEntity<List<CustomerResponseDTO>> getCustomers() {
    List<CustomerResponseDTO> customers = customerService.getCustomers();
    return ResponseEntity.ok().body(customers);
  }

  @PostMapping
  @Operation(summary = "Create a new Customer")
  public ResponseEntity<CustomerResponseDTO> createCustomer(
      @Validated({Default.class, CreateCustomerValidationGroup.class})
      @RequestBody CustomerRequestDTO customerRequestDTO) {

    CustomerResponseDTO customerResponseDTO = customerService.createCustomer(
        customerRequestDTO);

    return ResponseEntity.ok().body(customerResponseDTO);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update a new Customer")
  public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable UUID id,
      @Validated({Default.class}) @RequestBody CustomerRequestDTO customerRequestDTO) {

    CustomerResponseDTO customerResponseDTO = customerService.updateCustomer(id,
        customerRequestDTO);

    return ResponseEntity.ok().body(customerResponseDTO);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a Customer")
  public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
    customerService.deleteCustomer(id);
    return ResponseEntity.noContent().build();
  }
}

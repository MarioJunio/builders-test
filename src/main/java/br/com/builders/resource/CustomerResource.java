package br.com.builders.resource;

import br.com.builders.api.config.SwaggerConfig;
import br.com.builders.api.exception.ConstraintException;
import br.com.builders.model.dto.CustomerDto;
import br.com.builders.model.dto.PageCustomerDto;
import br.com.builders.model.entity.Customer;
import br.com.builders.resource.mapper.CustomerMapper;
import br.com.builders.service.CustomerService;
import br.com.builders.service.filter.CustomerFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerResource {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    /**
     * Create new customer
     *
     * @param customerDto
     * @return
     */
    @Operation(summary = "Create new customer", tags = SwaggerConfig.TAG, method = "POST", description = "/customers")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Customer created with success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class))}),
            @ApiResponse(responseCode = "500", description = "Unexpected error on create new customer",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<CustomerDto> create(@Valid @RequestBody final CustomerDto customerDto, final BindingResult bindingResult) {
        log.info("M=create, customerDto={}", customerDto);

        if (bindingResult.hasErrors()) {
            throw new ConstraintException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        final Customer customer = customerMapper.toEntity(customerDto);

        final Customer savedCustomer = customerService.save(customer);

        log.info("M=create, savedCustomer={}", savedCustomer);

        return ResponseEntity
                .created(null)
                .body(customerMapper.toDto(savedCustomer));
    }

    /**
     * Find customer by id
     *
     * @param id
     * @return
     */
    @Operation(summary = "Find a customer by id", tags = SwaggerConfig.TAG, method = "GET", description = "/customers/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer created with success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class))}),
            @ApiResponse(responseCode = "404", description = "Customer not found by id",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error on retrieve a customer",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> find(@PathVariable Long id) {
        log.info("M=find, id={}", id);

        final Customer customer = customerService.read(id);
        log.info("M=find, customer={}", customer);

        return ResponseEntity.ok(customerMapper.toDto(customer));
    }

    /**
     * Find all customers using filter with pagination
     *
     * @param filter
     * @param pageable
     * @return
     */
    @Operation(summary = "Find all customers", tags = SwaggerConfig.TAG, method = "GET", description = "/customers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customers retrieved",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CustomerDto.class)))}),
            @ApiResponse(responseCode = "404", description = "Customer not found by id",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error on retrieve a customer",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<PageCustomerDto> findAll(CustomerFilter filter, @PageableDefault(size = 5) Pageable pageable) {
        log.info("M=findAll, filter={}", filter);
        log.info("M=findAll, pageable={}", pageable);

        final Page<Customer> pageCustomer = customerService.read(filter, pageable);
        log.info("M=findAll, pageCustomer={}", pageCustomer);

        final PageCustomerDto pageCustomerDto = PageCustomerDto.builder()
                .page(pageCustomer)
                .build();
        log.info("M=findAll, pageCustomerDto={}", pageCustomerDto);

        return pageCustomer.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(pageCustomerDto);
    }

    /**
     * Update customer by id
     *
     * @param id
     * @param customerDto
     * @return
     */
    @Operation(summary = "Update customer", tags = SwaggerConfig.TAG, method = "PUT", description = "/customers/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class))}),
            @ApiResponse(responseCode = "404", description = "Customer not found by id",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error on update a customer",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> update(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        log.info("M=update, id={}", id);
        log.info("M=update, customerDto={}", customerDto);

        final Customer customer = customerService.update(id, customerMapper.toEntity(customerDto));
        log.info("M=update, customer={}", customer);

        return ResponseEntity.ok(customerMapper.toDto(customer));
    }

    /**
     * Delete customer by id
     *
     * @param id
     */
    @Operation(summary = "Delete customer", tags = SwaggerConfig.TAG, method = "DELETE", description = "/customers/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found by id",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error on delete a customer",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("M=delete, id={}", id);
        customerService.delete(id);
    }
}

package br.com.builders.service;

import br.com.builders.model.entity.Customer;
import br.com.builders.model.entity.GenderEnum;
import br.com.builders.repository.CustomerRepository;
import br.com.builders.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    private Customer customer;
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void initMocks() {
        customer = Customer.builder()
                .id(1L)
                .name("Mario")
                .email("mario@gmail.com")
                .phone("+5534987005712")
                .document("11420237616")
                .gender(GenderEnum.MALE)
                .build();
    }

    @Test
    public void saveTest() {
        Mockito
                .when(customerRepository.save(Mockito.any(Customer.class)))
                .thenReturn(customer);

        final Customer customerSaved = customerService.save(customer);

        Assertions.assertEquals(customer, customerSaved);
    }

    @Test
    public void updateTest() {
        final Long id = 1l;
        final Customer customer = Customer.builder()
                .id(id)
                .name("Mario")
                .build();

        final Customer customerUpdated = Customer.builder()
                .id(id)
                .name("Mario Junio")
                .build();

        Mockito
                .when(customerRepository.findById(id))
                .thenReturn(Optional.ofNullable(customer));

        Mockito
                .when(customerRepository.save(Mockito.any(Customer.class)))
                .thenReturn(customerUpdated);

        final Customer customerRetrieved = customerService.update(id, customer);

        Assertions.assertEquals(customerUpdated.getName(), customerRetrieved.getName());
    }

    @Test
    public void readTest() {
        Mockito
                .when(customerRepository.findById(customer.getId()))
                .thenReturn(Optional.ofNullable(customer));

        final Customer customerRetrieved = customerService.read(customer.getId());

        Assertions.assertEquals(this.customer, customerRetrieved);
    }

    @Test
    public void deleteTest() {
        Mockito
                .doNothing()
                .when(customerRepository)
                .deleteById(customer.getId());

        customerService.delete(customer.getId());
    }
}

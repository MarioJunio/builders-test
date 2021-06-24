package br.com.builders.service;

import br.com.builders.model.entity.Customer;
import br.com.builders.service.filter.CustomerFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    Customer save(final Customer customer);

    Customer update(final Long id, final Customer customer);

    Page<Customer> read(final CustomerFilter filter, final Pageable page);

    Customer read(final Long id);

    void delete(final Long id);

}

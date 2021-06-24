package br.com.builders.service.impl;

import br.com.builders.api.constants.CacheNames;
import br.com.builders.api.exception.ResourceNotFoundException;
import br.com.builders.model.entity.Customer;
import br.com.builders.repository.CustomerRepository;
import br.com.builders.repository.specifications.CustomerSpecification;
import br.com.builders.service.CustomerService;
import br.com.builders.service.filter.CustomerFilter;
import br.com.builders.utils.CopyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @CacheEvict(cacheNames = CacheNames.CUSTOMER, allEntries = true)
    @Transactional
    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @CachePut(cacheNames = CacheNames.CUSTOMER, key = "#id")
    @Transactional
    @Override
    public Customer update(Long id, Customer customer) {
        final Customer customerTarget = read(id);
        log.info("M=update, customerTarget={}", customerTarget);

        CopyUtils.copyNonNullProperties(customer, customerTarget);
        log.info("M=update, after copy customerTarget={}", customerTarget);

        return save(customerTarget);
    }

    @Cacheable(cacheNames = CacheNames.CUSTOMER, key = "#root.method.name" +
            ".concat(#pageable.pageNumber)" +
            ".concat(#filter.id != null ? #filter.id : '')" +
            ".concat(#filter.name != null ? #filter.name : '')" +
            ".concat(#filter.email != null ? #filter.email : '')" +
            ".concat(#filter.phone != null ? #filter.phone : '')" +
            ".concat(#filter.document != null ? #filter.document : '')" +
            ".concat(#filter.gender != null ? #filter.gender : '')")
    @Override
    public Page<Customer> read(CustomerFilter filter, Pageable pageable) {
        log.info("M=read, filter={}, pageable={}", filter, pageable);
        return customerRepository.findAll(new CustomerSpecification(filter), pageable);
    }

    @Cacheable(cacheNames = CacheNames.CUSTOMER, key = "#id")
    @Override
    public Customer read(Long id) {
        return customerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id: " + id + " not found"));

    }

    @CacheEvict(cacheNames = CacheNames.CUSTOMER, allEntries = true)
    @Transactional
    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}

package br.com.builders.resource.mapper;

import br.com.builders.model.dto.CustomerDto;
import br.com.builders.model.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerMapper implements Mapper<Customer, CustomerDto> {

    private final ModelMapper mapper;

    @Override
    public Customer toEntity(CustomerDto dto) {
        return mapper.map(dto, Customer.class);
    }

    @Override
    public CustomerDto toDto(Customer entity) {
        return mapper.map(entity, CustomerDto.class);
    }

    @Override
    public List<Customer> toListEntity(List<CustomerDto> listDto) {
        return listDto
                .stream()
                .map(dto -> toEntity(dto))
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDto> toListDto(List<Customer> listEntity) {
        return listEntity
                .stream()
                .map(entity -> toDto(entity))
                .collect(Collectors.toList());
    }
}

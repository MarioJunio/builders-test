package br.com.builders.repository.specifications;

import br.com.builders.model.entity.Customer;
import br.com.builders.model.entity.Customer_;
import br.com.builders.model.entity.GenderEnum;
import br.com.builders.service.filter.CustomerFilter;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CustomerSpecification implements Specification<Customer> {

    private final CustomerFilter filter;

    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        Path<Long> id = root.get(Customer_.ID);
        Path<String> name = root.get(Customer_.NAME);
        Path<String> email = root.get(Customer_.EMAIL);
        Path<String> document = root.get(Customer_.DOCUMENT);
        Path<String> phone = root.get(Customer_.PHONE);
        Path<GenderEnum> gender = root.get(Customer_.GENDER);

        if (filter.getId() != null) {
            predicates.add(criteriaBuilder.equal(id, filter.getId()));
        }

        if (StringUtils.hasText(filter.getName())) {
            predicates.add(criteriaBuilder.like(name, String.format("%%%s%%", filter.getName())));
        }

        if (StringUtils.hasText(filter.getEmail())) {
            predicates.add(criteriaBuilder.like(email, String.format("%%%s%%", filter.getEmail())));
        }

        if (StringUtils.hasText(filter.getDocument())) {
            predicates.add(criteriaBuilder.like(document, String.format("%%%s%%", filter.getDocument())));
        }

        if (StringUtils.hasText(filter.getPhone())) {
            predicates.add(criteriaBuilder.like(phone, String.format("%%%s%%", filter.getPhone())));
        }

        if (!ObjectUtils.isEmpty(filter.getGender())) {
            predicates.add(criteriaBuilder.equal(gender, filter.getGender()));
        }

        if (predicates.isEmpty()) {
            predicates.add(criteriaBuilder.isTrue(criteriaBuilder.literal(Boolean.TRUE)));
        }

        return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
    }
}

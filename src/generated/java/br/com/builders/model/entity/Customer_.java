package br.com.builders.model.entity;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Customer.class)
public abstract class Customer_ {

	public static volatile SingularAttribute<Customer, Instant> createdAt;
	public static volatile SingularAttribute<Customer, GenderEnum> gender;
	public static volatile SingularAttribute<Customer, String> phone;
	public static volatile SingularAttribute<Customer, String> document;
	public static volatile SingularAttribute<Customer, String> name;
	public static volatile SingularAttribute<Customer, Long> id;
	public static volatile SingularAttribute<Customer, Long> version;
	public static volatile SingularAttribute<Customer, String> email;

	public static final String CREATED_AT = "createdAt";
	public static final String GENDER = "gender";
	public static final String PHONE = "phone";
	public static final String DOCUMENT = "document";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String VERSION = "version";
	public static final String EMAIL = "email";

}


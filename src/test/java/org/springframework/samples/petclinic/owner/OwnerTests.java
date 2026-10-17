/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OwnerTests {

	@Test
	void addPetAddsPersistedPet() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setId(5);
		pet.setName("Buddy");

		owner.addPet(pet);

		assertTrue(owner.getPets().contains(pet));
		assertEquals(1, owner.getPets().size());
	}

	@Test
	void addPetDoesNotAddDuplicatePet() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setId(5);
		pet.setName("Buddy");

		owner.addPet(pet);
		owner.addPet(pet);

		assertEquals(1, owner.getPets().size());
	}

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	private Owner validOwner() {
		Owner owner = new Owner();
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setAddress("110 W. Liberty St.");
		owner.setCity("Madison");
		owner.setTelephone("6085551023");
		return owner;
	}

	private Set<ConstraintViolation<Owner>> validate(Owner owner) {
		return createValidator().validate(owner);
	}

	@Test
	void shouldValidateAddressAtMaxLength() {
		Owner owner = validOwner();
		owner.setAddress("a".repeat(255));

		assertThat(validate(owner)).isEmpty();
	}

	@Test
	void shouldValidateAddressJustBelowMaxLength() {
		Owner owner = validOwner();
		owner.setAddress("a".repeat(254));

		assertThat(validate(owner)).isEmpty();
	}

	@Test
	void shouldNotValidateAddressAboveMaxLength() {
		Owner owner = validOwner();
		owner.setAddress("a".repeat(256));

		Set<ConstraintViolation<Owner>> violations = validate(owner);

		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getPropertyPath()).hasToString("address");
	}

	@Test
	void shouldValidateCityAtMaxLength() {
		Owner owner = validOwner();
		owner.setCity("a".repeat(80));

		assertThat(validate(owner)).isEmpty();
	}

	@Test
	void shouldNotValidateCityAboveMaxLength() {
		Owner owner = validOwner();
		owner.setCity("a".repeat(81));

		Set<ConstraintViolation<Owner>> violations = validate(owner);

		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getPropertyPath()).hasToString("city");
	}

	@Test
	void shouldNotValidateBlankAddress() {
		Owner owner = validOwner();
		owner.setAddress("");

		Set<ConstraintViolation<Owner>> violations = validate(owner);

		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getPropertyPath()).hasToString("address");
	}

}

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

import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
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

	private Set<ConstraintViolation<Owner>> validateTelephone(String telephone) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Owner owner = new Owner();
		owner.setFirstName("Joe");
		owner.setLastName("Bloggs");
		owner.setAddress("123 Caramel Street");
		owner.setCity("London");
		owner.setTelephone(telephone);
		return createValidator().validate(owner);
	}

	@Test
	void shouldValidateExisting10DigitUsNumber() {
		assertThat(validateTelephone("6085551023")).isEmpty();
	}

	@Test
	void shouldValidateInternationalNumberWithPlus() {
		assertThat(validateTelephone("+491701234567")).isEmpty();
	}

	@Test
	void shouldNotValidateEmptyTelephone() {
		Set<ConstraintViolation<Owner>> violations = validateTelephone("");
		assertThat(violations).extracting(ConstraintViolation::getMessage).contains("must not be blank");
	}

	@Test
	void shouldNotValidateTelephoneWithLetters() {
		Set<ConstraintViolation<Owner>> violations = validateTelephone("call-me-maybe");
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getPropertyPath()).hasToString("telephone");
	}

	@Test
	void shouldNotValidateTelephoneWithDashes() {
		assertThat(validateTelephone("+49-170-1234567")).hasSize(1);
	}

	@Test
	void shouldNotValidatePlusWithNoDigits() {
		assertThat(validateTelephone("+")).hasSize(1);
	}

	@Test
	void shouldValidateSevenDigitLowerBoundary() {
		assertThat(validateTelephone("1234567")).isEmpty();
	}

	@Test
	void shouldNotValidateSixDigitBelowLowerBoundary() {
		assertThat(validateTelephone("123456")).hasSize(1);
	}

	@Test
	void shouldValidateFifteenDigitUpperBoundary() {
		assertThat(validateTelephone("123456789012345")).isEmpty();
	}

	@Test
	void shouldNotValidateSixteenDigitAboveUpperBoundary() {
		assertThat(validateTelephone("1234567890123456")).hasSize(1);
	}

}

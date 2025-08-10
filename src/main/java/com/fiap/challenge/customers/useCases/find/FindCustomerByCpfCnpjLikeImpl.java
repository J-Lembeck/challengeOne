package com.fiap.challenge.customers.useCases.find;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindCustomerByCpfCnpjLikeImpl implements FindCustomerByCpfCnpjLike {

	private final CustomerRepository customerRepository;

	public List<CustomerResponseDTO> execute(String cpfCnpj) {
		List<CustomerModel> customers = customerRepository.findByCpfCnpjContainingWithCustomSort(cpfCnpj);

		return customers.stream()
				.map(customer -> new CustomerResponseDTO(
						customer.getId(),
						customer.getName(),
						customer.getCpfCnpj(),
						customer.getPhone(),
						customer.getEmail(),
						customer.getCreatedAt(),
						customer.getUpdatedAt()
				))
				.collect(Collectors.toList());
	}
}
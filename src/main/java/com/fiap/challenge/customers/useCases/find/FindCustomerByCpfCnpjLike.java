package com.fiap.challenge.customers.useCases.find;

import java.util.List;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;

public interface FindCustomerByCpfCnpjLike {

	public List<CustomerResponseDTO> execute(String cpfCnpj);
}

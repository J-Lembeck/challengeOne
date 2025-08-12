package com.fiap.challenge.customers.useCases.find;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.shared.exception.customer.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindCustomerByIdUseCaseImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private FindCustomerByIdUseCaseImpl useCase;

    @Test
    void shouldReturnCustomerResponseWhenFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        OffsetDateTime now = OffsetDateTime.now();

        CustomerModel model = new CustomerModel();
        model.setId(id);
        model.setName("Maria Silva");
        model.setCpfCnpj("12345678901");
        model.setPhone("11999998888");
        model.setEmail("maria@exemplo.com");
        model.setCreatedAt(now);
        model.setUpdatedAt(now);

        when(customerRepository.findById(id)).thenReturn(Optional.of(model));

        // Act
        CustomerResponseDTO response = useCase.execute(id);

        // Assert
        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals("Maria Silva", response.name());
        assertEquals("12345678901", response.cpfCnpj());
        assertEquals("11999998888", response.phone());
        assertEquals("maria@exemplo.com", response.email());
        assertEquals(now, response.createdAt());
        assertEquals(now, response.updatedAt());

        verify(customerRepository, times(1)).findById(id);
    }

    @Test
    void shouldThrowWhenCustomerNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(CustomerNotFoundException.class, () -> useCase.execute(id));

        verify(customerRepository, times(1)).findById(id);
    }
}

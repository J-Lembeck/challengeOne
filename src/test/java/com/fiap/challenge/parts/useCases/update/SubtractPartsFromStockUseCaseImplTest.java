package com.fiap.challenge.parts.useCases.update;

import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.parts.dto.UpdatePartRequestDTO;
import com.fiap.challenge.parts.useCases.find.FindPartByIdUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubtractPartsFromStockUseCaseImplTest {

    @Mock
    private FindPartByIdUseCase findPartByIdUseCase;

    @Mock
    private UpdatePartUseCase updatePartUseCase;

    @InjectMocks
    private SubtractPartsFromStockUseCaseImpl useCase;

    @Test
    void shouldReturnTrueAndUpdateWhenStockIsSufficient() {
        // Arrange
        UUID partId = UUID.randomUUID();
        int quantity = 4;

        var now = OffsetDateTime.now();
        PartResponseDTO part = new PartResponseDTO(
                partId,
                "Pastilha de Freio",
                "Jogos dianteiros",
                new BigDecimal("199.90"),
                /* stockQuantity */ 10,
                /* reservedStock */ 3,
                /* minimumStock */ 2,
                now, now
        );
        when(findPartByIdUseCase.execute(partId).getData()).thenReturn(part);

        // Act
        boolean result = useCase.execute(partId, quantity);

        // Assert
        assertTrue(result);

        // stock diminui, reserved aumenta
        verify(updatePartUseCase).execute(eq(partId), argThat(matchesUpdate(
                "Pastilha de Freio",
                "Jogos dianteiros",
                new BigDecimal("199.90"),
                /* reserved esperado */ 3 + quantity,
                /* stock esperado */ 10 - quantity,
                /* min */ 2
        )));
    }

    @Test
    void shouldReturnFalseAndNotUpdateWhenStockIsInsufficient() {
        // Arrange
        UUID partId = UUID.randomUUID();
        int quantity = 6;

        PartResponseDTO part = new PartResponseDTO(
                partId,
                "Filtro de Ar",
                "Elemento filtrante",
                new BigDecimal("29.90"),
                /* stockQuantity */ 5,
                /* reservedStock */ 1,
                /* minimumStock */ 1,
                OffsetDateTime.now(), OffsetDateTime.now()
        );
        when(findPartByIdUseCase.execute(partId).getData()).thenReturn(part);

        // Act
        boolean result = useCase.execute(partId, quantity);

        // Assert
        assertFalse(result);
        verify(updatePartUseCase, never()).execute(any(), any());
    }

    @Test
    void shouldThrowWhenFindReturnsNull() {
        // Arrange
        UUID partId = UUID.randomUUID();
        when(findPartByIdUseCase.execute(partId)).thenReturn(null);

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> useCase.execute(partId, 1));
        verify(updatePartUseCase, never()).execute(any(), any());
    }

    // helper matcher
    private ArgumentMatcher<UpdatePartRequestDTO> matchesUpdate(
            String name,
            String description,
            BigDecimal price,
            int expectedReserved,
            int expectedStock,
            int minStock
    ) {
        return dto ->
                dto != null &&
                        name.equals(dto.name()) &&
                        description.equals(dto.description()) &&
                        price.compareTo(dto.price()) == 0 &&
                        dto.reservedStock() == expectedReserved &&
                        dto.stockQuantity() == expectedStock &&
                        dto.minimumStock() == minStock;
    }
}

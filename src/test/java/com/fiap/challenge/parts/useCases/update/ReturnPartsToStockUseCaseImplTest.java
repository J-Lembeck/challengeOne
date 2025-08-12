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
class ReturnPartsToStockUseCaseImplTest {

    @Mock
    private FindPartByIdUseCase findPartByIdUseCase;

    @Mock
    private UpdatePartUseCase updatePartUseCase;

    @InjectMocks
    private ReturnPartsToStockUseCaseImpl useCase;

    @Test
    void shouldReturnTrueAndUpdateWhenReservedStockIsSufficient() {
        // Arrange
        UUID partId = UUID.randomUUID();
        int quantity = 3;

        var now = OffsetDateTime.now();
        PartResponseDTO part = new PartResponseDTO(
                partId,
                "Filtro de Óleo",
                "Filtro OEM",
                new BigDecimal("39.90"),
                /* stockQuantity */ 10,
                /* reservedStock */ 5,
                /* minimumStock */ 2,
                now, now
        );
        when(findPartByIdUseCase.execute(partId)).thenReturn(part);

        // Act
        boolean result = useCase.execute(partId, quantity);

        // Assert
        assertTrue(result);

        // Verifica que o update foi chamado com os valores ajustados
        verify(updatePartUseCase).execute(eq(partId), argThat(matchesUpdate(
                "Filtro de Óleo",
                "Filtro OEM",
                new BigDecimal("39.90"),
                /* reservedStock esperado */ 5 - quantity,
                /* stockQuantity esperado */ 10 + quantity,
                /* minimumStock */ 2
        )));
    }

    @Test
    void shouldReturnFalseAndNotUpdateWhenReservedStockIsInsufficient() {
        // Arrange
        UUID partId = UUID.randomUUID();
        int quantity = 4;

        PartResponseDTO part = new PartResponseDTO(
                partId,
                "Filtro de Ar",
                "Elemento filtrante",
                new BigDecimal("29.90"),
                /* stockQuantity */ 7,
                /* reservedStock */ 2,
                /* minimumStock */ 1,
                OffsetDateTime.now(), OffsetDateTime.now()
        );
        when(findPartByIdUseCase.execute(partId)).thenReturn(part);

        // Act
        boolean result = useCase.execute(partId, quantity);

        // Assert
        assertFalse(result);
        verify(updatePartUseCase, never()).execute(any(), any());
    }

    @Test
    void shouldThrowEntityNotFoundWhenFindReturnsNull() {
        // Arrange
        UUID partId = UUID.randomUUID();
        when(findPartByIdUseCase.execute(partId)).thenReturn(null);

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> useCase.execute(partId, 1));
        verify(updatePartUseCase, never()).execute(any(), any());
    }

    // ------- helper matcher -------
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

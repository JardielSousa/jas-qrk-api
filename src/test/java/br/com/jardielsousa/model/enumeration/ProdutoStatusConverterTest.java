package br.com.jardielsousa.model.enumeration;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class ProdutoStatusConverterTest {

    @Test
    void convertToDatabaseColumn() {
        assertEquals(ProdutoStatus.ATIVO.getCodigo(), new ProdutoStatusConverter().convertToDatabaseColumn(ProdutoStatus.ATIVO));
    }

    @Test
    void convertToDatabaseColumn_nulo() {
        assertThrows(IllegalArgumentException.class, () -> new ProdutoStatusConverter().convertToDatabaseColumn(null));
    }

    @Test
    void convertToEntityAttribute() {
        assertEquals(ProdutoStatus.ATIVO, new ProdutoStatusConverter().convertToEntityAttribute(1));
    }

    @Test
    void convertToEntityAttribute_nulo() {
        assertThrows(IllegalArgumentException.class, () -> new ProdutoStatusConverter().convertToEntityAttribute(null));
    }
}
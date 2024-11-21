package br.com.jardielsousa.model.enumeration;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class ProdutoStatusTest {

    @Test
    void porCodigo_nulo() {
        assertThrows(IllegalArgumentException.class, () -> ProdutoStatus.porCodigo(null));
    }

    @Test
    void porCodigo_naoNulo_naoContainKey() {
        assertThrows(IllegalArgumentException.class, () -> ProdutoStatus.porCodigo(999));
    }

    @Test
    void porCodigo_naoNulo_containKey() {
        assertEquals(ProdutoStatus.ATIVO, ProdutoStatus.porCodigo(1));
    }
}
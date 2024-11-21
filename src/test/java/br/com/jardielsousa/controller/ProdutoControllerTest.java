package br.com.jardielsousa.controller;

import br.com.jardielsousa.model.domain.produto.Produto;
import br.com.jardielsousa.model.dto.produto.ProdutoAlterarRequest;
import br.com.jardielsousa.model.dto.produto.ProdutoCriarRequest;
import br.com.jardielsousa.service.base.ProdutoService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.com.jardielsousa.config.RestControllerMappingValueConfig.PRODUTOS;
import static io.restassured.RestAssured.given;
import static org.jboss.resteasy.reactive.RestResponse.Status.CREATED;
import static org.jboss.resteasy.reactive.RestResponse.Status.OK;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
class ProdutoControllerTest {

    @InjectMock
    ProdutoService produtoService;

    @Test
    void criar() {
        when(produtoService.criarProduto(any(ProdutoCriarRequest.class)))
                .thenReturn(new Produto(null, "Produto 1", "Descrição do produto 1", new BigDecimal("100.00"), null));
        when(produtoService.criar(any(Produto.class)))
                .thenReturn(new Produto(null, "Produto 1", "Descrição do produto 1", new BigDecimal("100.00"), null));

        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "nome": "Produto 1",
                          "descricao": "Descrição do produto 1",
                          "preco": 100.00
                        }""")
                .when()
                .post(PRODUTOS)
                .then()
                .statusCode(CREATED.getStatusCode());
    }

    @Test
    void buscarTodos() {
        given()
                .when()
                .get(PRODUTOS)
                .then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void alterar() {
        when(produtoService.criarProduto(any(ProdutoAlterarRequest.class)))
                .thenReturn(new Produto(null, "Produto 1", "Descrição do produto 1", new BigDecimal("100.00"), null));
        when(produtoService.alterarProduto(any(Long.class), any(Produto.class))).thenReturn(new Produto());

        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "nome": "Produto 1",
                          "descricao": "Descrição do produto 1",
                          "preco": 100.00
                        }""")
                .when()
                .put(PRODUTOS + "/1")
                .then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void ativarDesativarProduto() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "status": "ATIVO"
                        }""")
                .when()
                .patch(PRODUTOS + "/1")
                .then()
                .statusCode(OK.getStatusCode());
    }
}
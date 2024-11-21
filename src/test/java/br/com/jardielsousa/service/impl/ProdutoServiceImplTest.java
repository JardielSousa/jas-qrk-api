package br.com.jardielsousa.service.impl;

import br.com.jardielsousa.factories.ProdutoFactory;
import br.com.jardielsousa.model.domain.produto.Produto;
import br.com.jardielsousa.model.dto.produto.ProdutoAlterarRequest;
import br.com.jardielsousa.model.dto.produto.ProdutoCriarRequest;
import br.com.jardielsousa.model.enumeration.ProdutoStatus;
import br.com.jardielsousa.repositoty.ProdutoRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@QuarkusTest
class ProdutoServiceImplTest {

    @Inject
    ProdutoServiceImpl produtoService;

    @InjectMock
    ProdutoRepository produtoRepository;

    @Test
    void criarProduto_ProdutoCriarRequest() {
        final ProdutoCriarRequest request = ProdutoFactory.fabricarProdutoCriarRequest();
        final Produto produto = this.produtoService.criarProduto(request);
        assertNotNull(produto);
        assertEquals(request.getNome(), produto.getNome());
        assertEquals(request.getDescricao(), produto.getDescricao());
        assertEquals(request.getPreco(), produto.getPreco());
    }

    @Test
    void criarProduto_ProdutoAlterarRequest() {
        final ProdutoAlterarRequest request = ProdutoFactory.fabricarProdutoAlterarRequest("Produto 01");
        final Produto produto = this.produtoService.criarProduto(request);
        assertNotNull(produto);
        assertEquals(request.getNome(), produto.getNome());
        assertEquals(request.getDescricao(), produto.getDescricao());
        assertEquals(request.getPreco(), produto.getPreco());
    }

    @Test
    void criar() {
        assertDoesNotThrow(() -> this.produtoService.criar(new Produto()));
    }

    @Test
    void buscarTodos() {
        when(this.produtoRepository.listAll()).thenReturn(ProdutoFactory.fabricarProdutosEntity());
        assertDoesNotThrow(() -> this.produtoService.buscarTodos());
    }


    @Test
    void alterarProduto_idInexistente() {
        when(this.produtoRepository.findByIdOptional(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> this.produtoService.alterarProduto(1L, new Produto()));
    }

    @Test
    void alterarProduto() {
        when(this.produtoRepository.findByIdOptional(1L)).thenReturn(Optional.of(ProdutoFactory.fabricarProdutoEntity("Produto 01", ProdutoStatus.ATIVO)));
        assertDoesNotThrow(() -> this.produtoService.alterarProduto(1L, new Produto()));
    }

    @Test
    void ativarDesativarProduto_ativo() {
        when(this.produtoRepository.findByIdOptional(1L)).thenReturn(Optional.of(ProdutoFactory.fabricarProdutoEntity("Produto 01", ProdutoStatus.ATIVO)));
        assertDoesNotThrow(() -> this.produtoService.ativarDesativarProduto(1L));
    }

    @Test
    void ativarDesativarProduto_inativo() {
        when(this.produtoRepository.findByIdOptional(1L)).thenReturn(Optional.of(ProdutoFactory.fabricarProdutoEntity("Produto 01", ProdutoStatus.INATIVO)));
        assertDoesNotThrow(() -> this.produtoService.ativarDesativarProduto(1L));
    }

    @Test
    void ativarDesativarProduto_idInexistente() {
        assertThrows(IllegalArgumentException.class, () -> this.produtoService.ativarDesativarProduto(1L));
    }
}
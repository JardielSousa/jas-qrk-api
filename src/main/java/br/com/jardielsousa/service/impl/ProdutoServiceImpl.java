package br.com.jardielsousa.service.impl;

import br.com.jardielsousa.entity.ProdutoEntity;
import br.com.jardielsousa.model.domain.produto.Produto;
import br.com.jardielsousa.model.dto.produto.ProdutoAlterarRequest;
import br.com.jardielsousa.model.dto.produto.ProdutoCriarRequest;
import br.com.jardielsousa.model.enumeration.ProdutoStatus;
import br.com.jardielsousa.repositoty.ProdutoRepository;
import br.com.jardielsousa.service.base.ProdutoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Override
    public Produto criarProduto(final ProdutoCriarRequest request) {
        return new Produto(null, request.getNome(), request.getDescricao(), request.getPreco(), null);
    }

    @Override
    public Produto criarProduto(final ProdutoAlterarRequest request) {
        return new Produto(null, request.getNome(), request.getDescricao(), request.getPreco(), null);
    }

    @Transactional
    @Override
    public Produto criar(final Produto produto) {
        final var produtoEntity = new ProdutoEntity(null, produto.getNome(), produto.getDescricao(), produto.getPreco());
        this.produtoRepository.persist(produtoEntity);
        return this.produtoEntityParaProduto(produtoEntity);
    }

    @Override
    public List<Produto> buscarTodos() {
        return this.produtoRepository.listAll().stream().map(this::produtoEntityParaProduto).toList();
    }

    @Transactional
    @Override
    public Produto alterarProduto(final Long id, final Produto produto) {
        final var optionalProdutoEntity = this.produtoRepository.findByIdOptional(id);
        if (optionalProdutoEntity.isPresent()) {
            final var produtoEntity = optionalProdutoEntity.get();
            produtoEntity.setNome(produto.getNome());
            produtoEntity.setDescricao(produto.getDescricao());
            produtoEntity.setPreco(produto.getPreco());

            return this.produtoEntityParaProduto(produtoEntity);
        }

        throw new IllegalArgumentException("Produto não encontrado");
    }

    @Transactional
    @Override
    public Boolean ativarDesativarProduto(final Long id) {
        var optionalProdutoEntity = this.produtoRepository.findByIdOptional(id);
        if (optionalProdutoEntity.isPresent()) {
            var produtoEntity = optionalProdutoEntity.get();
            final var status = (produtoEntity.getStatus().equals(ProdutoStatus.ATIVO)) ? ProdutoStatus.INATIVO : ProdutoStatus.ATIVO;
            produtoEntity.setStatus(status);
            this.alterarProduto(produtoEntity);

            return produtoEntity.getStatus().equals(ProdutoStatus.ATIVO);
        }

        throw new IllegalArgumentException();
    }

    private void alterarProduto(final ProdutoEntity produto) {
        produto.setDataAlteracao(LocalDateTime.now());
        this.produtoRepository.persist(produto);
    }

    private Produto produtoEntityParaProduto(final ProdutoEntity produtoEntitySalvo) {
        return new Produto(
                produtoEntitySalvo.getId(),
                produtoEntitySalvo.getNome(),
                produtoEntitySalvo.getDescricao(),
                produtoEntitySalvo.getPreco(),
                produtoEntitySalvo.getStatus());
    }

}

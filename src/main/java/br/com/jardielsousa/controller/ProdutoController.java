package br.com.jardielsousa.controller;

import br.com.jardielsousa.model.dto.produto.ProdutoAlterarRequest;
import br.com.jardielsousa.model.dto.produto.ProdutoAlterarResponse;
import br.com.jardielsousa.model.dto.produto.ProdutoBuscarTodosResponse;
import br.com.jardielsousa.model.dto.produto.ProdutoCriarRequest;
import br.com.jardielsousa.service.base.ProdutoService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

import java.net.URI;

import static br.com.jardielsousa.config.RestControllerMappingValueConfig.PRODUTOS;

@RequiredArgsConstructor
@Log4j2
@Path(value = PRODUTOS)
public class ProdutoController {

    private final ProdutoService produtoService;

    @POST
    public RestResponse<Void> criar(final ProdutoCriarRequest request) {

        log.info("Criando produto {}", request);

        final var produto = this.produtoService.criarProduto(request);
        final var criado = this.produtoService.criar(produto);

        return RestResponse.created(URI.create(PRODUTOS + "/" + criado.getId()));
    }

    @GET
    public RestResponse<ProdutoBuscarTodosResponse> buscarTodos() {
        log.info("Buscando todos os produtos");
        final var produtos = this.produtoService.buscarTodos();
        return RestResponse.ok(new ProdutoBuscarTodosResponse(produtos));
    }

    @PUT
    @Path(value = "/{id}")
    public RestResponse<ProdutoAlterarResponse> alterar(
            @RestPath(value = "id") final Long id,
            final ProdutoAlterarRequest request
    ) {
        log.info("Alterando o produto de id: {}", id);
        final var produto = this.produtoService.criarProduto(request);
        final var produtoAtualizado = this.produtoService.alterarProduto(id, produto);
        return RestResponse.ok(new ProdutoAlterarResponse(
                produtoAtualizado.getId(),
                produtoAtualizado.getNome(),
                produtoAtualizado.getDescricao(),
                produtoAtualizado.getPreco()
        ));
    }

    @PATCH
    @Path(value = "/{id}")
    public RestResponse<Boolean> ativarDesativarProduto(@RestPath(value = "id") final Long id) {
        log.info("Ativar | Desativar produto de id: {}", id);
        return RestResponse.ok(this.produtoService.ativarDesativarProduto(id));
    }
}

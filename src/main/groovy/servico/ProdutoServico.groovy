package servico

import modelo.Produto

interface ProdutoServico {

    List<Produto> obtenhaTodosProdutos()

    Produto obterProduto(String identificador)

    List<String> obtenhaIdentificadoresDeProdutosDisponiveis()
}

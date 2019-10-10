package modelo

class Venda {

    Map<String, List<Produto>> produtos
    BigDecimal precoTotal
    BigDecimal descontoTotal

    Venda(List<String> produtosDisponiveis) {

        produtos = new HashMap<String, List<Produto>>()
        produtosDisponiveis.each { produtos.putIfAbsent(it, new ArrayList<Produto>())}
        precoTotal = 0
        descontoTotal = 0
    }

    void adicioneProduto(Produto produto) {

        produtos.get(produto.identificador).push(produto)
        atualizeTotais()
    }

    void removaProduto(Produto produto) {

        List<Produto> produtos = produtos.get(produto.identificador)
        if(!produtos.isEmpty()){
            produtos.removeLast()
        }
        atualizeTotais()
    }

    private BigInteger obterQtdIguais(Produto produto) {
        return produtos.get(produto.identificador).size()
    }

    private void atualizeTotais() {
        atualizeDescontoTotal()
        atualizePrecoTotal()
    }

    BigDecimal atualizePrecoTotal() {

        precoTotal = obtenhaProdutosAdicionados().values().sum {it.sum {it.precoUnitario}} - descontoTotal
    }

    void atualizeDescontoTotal() {

        BigDecimal totalDesconto = 0

        obtenhaProdutosAdicionados().each { String identificador, List<Produto> subListaDeIguais ->
            totalDesconto += obtenhaMelhorDescontoDeProduto(subListaDeIguais.first())

        }

        descontoTotal = totalDesconto
    }

    private BigDecimal obtenhaMelhorDescontoDeProduto(Produto produto) {

        if(produto.promocoesAplicaveis == null ) {
            return 0
        }
        return produto.promocoesAplicaveis*.calculeDesconto(obterQtdIguais(produto), produto.precoUnitario).max()
    }

    Map<String, List<Produto>> obtenhaProdutosAdicionados() {
       return produtos.findAll {!it.value.isEmpty()}
    }

}

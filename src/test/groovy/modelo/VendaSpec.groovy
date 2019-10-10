package modelo

import servico.ProdutoServico
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class VendaSpec extends Specification {

    @Shared
    ProdutoServico produtoServico
    @Shared
    Venda venda
    static final List<Produto> dbFakeProduto = [
            new Produto(
                    'A',
                    0.50,
                    [new PromocaoLeveQuantidadeXPaguePrecoY(3, 1.30)]
            ),
            new Produto(
                    'B',
                    0.30,
                    [new PromocaoLeveQuantidadeXPaguePrecoY(2, 0.45)]
            ),
            new Produto(
                    'C',
                    0.20,
                    [new PromocaoLeveQuantidadeXPagueQuantidadeY(3, 2)]
            ),
            new Produto('D', 0.15, null)
    ]

    def setupSpec() {
        produtoServico = Mock(ProdutoServico)
        produtoServico.obtenhaTodosProdutos() >> dbFakeProduto
        produtoServico.obterProduto(_ as String) >> { String identificador ->
            dbFakeProduto.find {
                it.identificador == identificador
            }
        }
        produtoServico.obtenhaIdentificadoresDeProdutosDisponiveis() >> dbFakeProduto.collect { it.identificador }

        novaVenda()
    }

    def novaVenda() {
        venda = new Venda(produtoServico.obtenhaIdentificadoresDeProdutosDisponiveis())
    }


    @Unroll
    def "o preco total da venda deve ser: #precoTotal e o desconto total: #descontoTotal"() {
        when:

        acao()

        then:
        venda.precoTotal == precoTotal

        and:
        venda.descontoTotal == descontoTotal

        where:

        acao                                                          | precoTotal | descontoTotal
        ({ venda.adicioneProduto(produtoServico.obterProduto('A')) }) | 0.50       | 0
        ({ venda.adicioneProduto(produtoServico.obterProduto('A')) }) | 1.00       | 0
        ({ venda.adicioneProduto(produtoServico.obterProduto('A')) }) | 1.30       | 0.20
        ({ venda.adicioneProduto(produtoServico.obterProduto('A')) }) | 1.80       | 0.20
        ({ venda.adicioneProduto(produtoServico.obterProduto('A')) }) | 2.30       | 0.20
        ({ venda.adicioneProduto(produtoServico.obterProduto('A')) }) | 2.60       | 0.40
        ({ venda.removaProduto(produtoServico.obterProduto('A')) })   | 2.30       | 0.20
        ({ novaVenda() })                                             | 0          | 0
        ({ venda.adicioneProduto(produtoServico.obterProduto('D')) }) | 0.15       | 0
        ({ venda.adicioneProduto(produtoServico.obterProduto('A')) }) | 0.65       | 0
        ({ venda.adicioneProduto(produtoServico.obterProduto('B')) }) | 0.95       | 0
        ({ venda.adicioneProduto(produtoServico.obterProduto('A')) }) | 1.45       | 0
        ({ venda.adicioneProduto(produtoServico.obterProduto('B')) }) | 1.60       | 0.15
        ({ venda.adicioneProduto(produtoServico.obterProduto('A')) }) | 1.90       | 0.35
        ({ venda.removaProduto(produtoServico.obterProduto('A')) })   | 1.60       | 0.15
        ({ venda.removaProduto(produtoServico.obterProduto('B')) })   | 1.45       | 0
        ({ novaVenda() })                                             | 0          | 0
        ({ venda.adicioneProduto(produtoServico.obterProduto('C')) }) | 0.20       | 0
        ({ venda.adicioneProduto(produtoServico.obterProduto('C')) }) | 0.40       | 0
        ({ venda.adicioneProduto(produtoServico.obterProduto('C')) }) | 0.40       | 0.20
        ({ venda.adicioneProduto(produtoServico.obterProduto('C')) }) | 0.60       | 0.20
        ({ novaVenda() })                                             | 0          | 0
        ({ venda.adicioneProduto(produtoServico.obterProduto('C')) }) | 0.20       | 0
        ({ venda.adicioneProduto(produtoServico.obterProduto('B')) }) | 0.50       | 0
        ({ venda.adicioneProduto(produtoServico.obterProduto('B')) }) | 0.65       | 0.15
        ({ venda.removaProduto(produtoServico.obterProduto('B')) })   | 0.50       | 0

    }


}

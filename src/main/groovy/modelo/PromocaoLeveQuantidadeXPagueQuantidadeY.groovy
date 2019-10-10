package modelo

class PromocaoLeveQuantidadeXPagueQuantidadeY implements Promocao {

    int qtdNecessaria
    int quantidadeAPagar

    PromocaoLeveQuantidadeXPagueQuantidadeY( int qtdNecessaria, int quantidadeAPagar ) {

        this.qtdNecessaria = qtdNecessaria
        this.quantidadeAPagar = quantidadeAPagar
    }

    @Override
    BigDecimal calculeDesconto( BigInteger qtdALevar, BigDecimal precoUnitario ) {

        BigDecimal precoSemDesconto = qtdALevar * precoUnitario
        BigInteger qtdNaoElegiveis = qtdALevar % qtdNecessaria
        BigInteger qtdElegiveis = qtdALevar - qtdNaoElegiveis
        BigDecimal qtdVezesParaAplicarDesconto = qtdElegiveis / qtdNecessaria
        BigDecimal qtdAPagar = qtdALevar - ( qtdVezesParaAplicarDesconto * qtdAReduzirAoAtingirQuantidadeNecessaria() )
        BigDecimal precoComDesconto = qtdAPagar * precoUnitario
        BigDecimal desconto = precoSemDesconto - precoComDesconto

        return desconto
    }

    private BigInteger qtdAReduzirAoAtingirQuantidadeNecessaria() {

        return qtdNecessaria - quantidadeAPagar
    }

}

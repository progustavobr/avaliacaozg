package modelo

class PromocaoLeveQuantidadeXPaguePrecoY implements Promocao {

    int qtdNecessaria
    BigDecimal precoAPagar

    PromocaoLeveQuantidadeXPaguePrecoY( int qtdNecessaria, BigDecimal precoAPagar ) {

        this.qtdNecessaria = qtdNecessaria
        this.precoAPagar = precoAPagar
    }

    @Override
    BigDecimal calculeDesconto( BigInteger qtdALevar, BigDecimal precoUnitario ) {

        BigDecimal precoSemDesconto = qtdALevar * precoUnitario
        BigInteger qtdNaoElegiveis = ( qtdALevar % qtdNecessaria )
        BigInteger qtdElegiveis = qtdALevar - qtdNaoElegiveis
        BigDecimal qtdVezesParaAplicarDesconto = qtdElegiveis / qtdNecessaria
        BigDecimal precoNaoElegiveis = qtdNaoElegiveis * precoUnitario
        BigDecimal precoElegiveis = precoAPagar * qtdVezesParaAplicarDesconto
        BigDecimal precoComDesconto = precoElegiveis + precoNaoElegiveis
        BigDecimal desconto = precoSemDesconto - precoComDesconto

        return desconto

    }
}

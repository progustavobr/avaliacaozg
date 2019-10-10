package modelo

class Produto {

    String identificador
    BigDecimal precoUnitario
    List<Promocao> promocoesAplicaveis

    Produto( String identificador, BigDecimal precoUnitario, List<Promocao> promocoesAplicaveis ) {

        this.identificador = identificador
        this.precoUnitario = precoUnitario
        this.promocoesAplicaveis = promocoesAplicaveis
    }


}

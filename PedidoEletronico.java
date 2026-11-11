public class PedidoEletronico extends Pedido implements Entregavel {

    public PedidoEletronico(int id, String nomeCliente, double valor)
            throws ValorInvalidoException {
        super(id, nomeCliente, valor, StatusPedido.PENDENTE, Categoria.ELETRONICO);
    }

    @Override
    public double calcularValorFinal() {
        double desconto = 0;
        if (valor > 100) {
            desconto = valor * 0.10;
        }
        return valor - desconto;
    }

    @Override
    public double calcularFrete() {
        return 20 + 0.10 * valor;
    }

    @Override
    public String toString() {
        return "[Eletrônico] ID: " + id + ", Cliente: " + nomeCliente + ", Valor: " + calcularValorFinal()
                + ", Status: " + status + ", Frete: " + calcularFrete();
    }
}

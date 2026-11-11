public class PedidoRoupa extends Pedido implements Entregavel {

    public PedidoRoupa(int id, String nomeCliente, double valor)
            throws ValorInvalidoException {
        super(id, nomeCliente, valor, StatusPedido.PENDENTE, Categoria.ROUPA);
    }

    @Override
    public double calcularValorFinal() {
        double desconto = 0;
        if (valor > 200) {
            desconto = valor * 0.20;
        }
        return valor - desconto;
    }

    @Override
    public double calcularFrete() {
        return 10 + 0.05 * valor;
    }

    @Override
    public String toString() {
        return "[Roupa] ID: " + id + ", Cliente: " + nomeCliente + ", Valor: " + calcularValorFinal()
                + ", Status: " + status + ", Frete: " + calcularFrete();
    }
}

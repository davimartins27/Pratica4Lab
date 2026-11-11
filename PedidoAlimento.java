public class PedidoAlimento extends Pedido {

    public PedidoAlimento(int id, String nomeCliente, double valor)
            throws ValorInvalidoException {
        super(id, nomeCliente, valor, StatusPedido.PENDENTE, Categoria.ALIMENTO);
    }

    @Override
    public double calcularValorFinal() {
        return valor; // sem desconto
    }

    @Override
    public void processarPedido() throws PedidoInvalidoException {
        super.processarPedido();
        // PedidoAlimento não implementa entregavel, então não há cálculo de frete
    }

    @Override
    public String toString() {
        return "[Alimento] ID: " + id + ", Cliente: " + nomeCliente + ", Valor: " + calcularValorFinal()
                + ", Status: " + status + " (Sem frete)";
    }
}

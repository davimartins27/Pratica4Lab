public abstract class Pedido implements Processavel {
    protected int id;
    protected String nomeCliente;
    protected double valor;
    protected StatusPedido status;
    protected Categoria categoria;

    public Pedido(int id, String nomeCliente, double valor, StatusPedido status, Categoria categoria)
            throws ValorInvalidoException {
        if (valor <= 0) {
            throw new ValorInvalidoException("Valor do pedido deve ser maior que zero.");
        }
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.valor = valor;
        this.status = status;
        this.categoria = categoria;
    }

    public int getId() { return id; }
    public String getNomeCliente() { return nomeCliente; }
    public double getValor() { return valor; }
    public StatusPedido getStatus() { return status; }
    public Categoria getCategoria() { return categoria; }

    public void setStatus(StatusPedido novoStatus) throws PedidoInvalidoException {
        if (this.status == StatusPedido.ENTREGUE) {
            throw new PedidoInvalidoException("Não pode mudar status de pedido entregue.");
        }
        this.status = novoStatus;
    }

    @Override
    public void processarPedido() throws PedidoInvalidoException {
        if (status == StatusPedido.CANCELADO) {
            throw new PedidoInvalidoException("Não é possível processar pedido cancelado.");
        }
        this.status = StatusPedido.PROCESSANDO;
    }

    @Override
    public abstract double calcularValorFinal();

    public abstract String toString();

}

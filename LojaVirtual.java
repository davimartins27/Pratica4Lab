import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class LojaVirtual {

    private static ArrayList<Pedido> pedidos = new ArrayList<>();
    private static int proximoId = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\nMenu Loja Virtual");
            System.out.println("1 - Cadastrar pedido");
            System.out.println("2 - Alterar status de pedido");
            System.out.println("3 - Processar todos os pedidos");
            System.out.println("4 - Remover pedidos cancelados");
            System.out.println("5 - Exibir lista de pedidos (sem cancelados)");
            System.out.println("6 - Exibir pedidos entregáveis");
            System.out.println("7 - Encerrar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch(opcao) {
                case 1:
                    cadastrarPedido(scanner);
                    break;

                case 2:
                    alterarStatus(scanner);
                    break;

                case 3:
                    processarPedidos();
                    break;

                case 4:
                    removerCancelados();
                    break;

                case 5:
                    exibirPedidos();
                    break;

                case 6:
                    exibirEntregaveis();
                    break;

                case 7:
                    System.out.println("Encerrando.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 7);

        scanner.close();
    }

    private static void cadastrarPedido(Scanner scanner) {
        try {
            System.out.println("Selecione a categoria:");
            System.out.println("1 - Eletrônico");
            System.out.println("2 - Roupa");
            System.out.println("3 - Alimento");
            int categoriaEscolhida = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Nome do cliente: ");
            String nomeCliente = scanner.nextLine();

            System.out.print("Valor do pedido: ");
            double valor = scanner.nextDouble();
            scanner.nextLine();

            Pedido pedido = null;

            switch(categoriaEscolhida) {
                case 1:
                    pedido = new PedidoEletronico(proximoId++, nomeCliente, valor);
                    break;
                case 2:
                    pedido = new PedidoRoupa(proximoId++, nomeCliente, valor);
                    break;
                case 3:
                    pedido = new PedidoAlimento(proximoId++, nomeCliente, valor);
                    break;
                default:
                    System.out.println("Categoria inválida.");
                    return;
            }

            pedidos.add(pedido);
            System.out.println("Pedido cadastrado: " + pedido);

        } catch (ValorInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void alterarStatus(Scanner scanner) {
        System.out.print("ID do pedido a alterar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Pedido pedido = buscarPedidoPorId(id);
        if (pedido == null) {
            System.out.println("Pedido não encontrado.");
            return;
        }

        System.out.println("Status atual: " + pedido.getStatus());
        System.out.println("Digite novo status:");
        for (StatusPedido sp : StatusPedido.values()) {
            System.out.println(sp.ordinal() + 1 + " - " + sp);
        }
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha < 1 || escolha > StatusPedido.values().length) {
            System.out.println("Status inválido.");
            return;
        }

        try {
            StatusPedido novoStatus = StatusPedido.values()[escolha - 1];
            pedido.setStatus(novoStatus);
            System.out.println("Status alterado para " + novoStatus);
        } catch (PedidoInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void processarPedidos() {
        Iterator<Pedido> iterator = pedidos.iterator();
        while (iterator.hasNext()) {
            Pedido ped = iterator.next();
            try {
                ped.processarPedido();
                System.out.println("Pedido " + ped.getId() + " processado.");
            } catch (PedidoInvalidoException e) {
                System.out.println("Pedido " + ped.getId() + " não pôde ser processado: " + e.getMessage());
            }
        }
    }

    private static void removerCancelados() {
        Iterator<Pedido> iterator = pedidos.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            Pedido ped = iterator.next();
            if (ped.getStatus() == StatusPedido.CANCELADO) {
                iterator.remove();
                count++;
            }
        }
        System.out.println(count + " pedidos cancelados removidos.");
    }

    private static void exibirPedidos() {
        System.out.println("Pedidos (exceto cancelados):");
        for (Pedido ped : pedidos) {
            if (ped.getStatus() != StatusPedido.CANCELADO) {
                System.out.println(ped);
            }
        }
    }

    private static void exibirEntregaveis() {
        System.out.println("Pedidos entregáveis:");
        for (Pedido ped : pedidos) {
            if (ped instanceof Entregavel) {
                System.out.println(ped);
            }
        }
    }

    private static Pedido buscarPedidoPorId(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Banco banco = new Banco("Digital Bank");
        int option = 0;

        do {
            System.out.println("\n *** Bem-vindo(a) ao " + banco.getNome() + " ***");
            System.out.println("1 - Criar Novo Cliente e Conta");
            System.out.println("2 - Acessar Conta Existente");
            System.out.println("3 - Listar Clientes e Contas");
            System.out.println("4 - Sair");
            System.out.println("Escolha uma opção");

            try {
                option = sc.nextInt();
                sc.nextLine();

                switch (option) {
                    case 1:
                        criarNovoClienteEConta(sc, banco);
                        break;
                    case 2:
                        acessarContaExistente(sc, banco);
                        break;
                    case 3:
                        listaClientesEContas(banco);
                        break;
                    case 4:
                        System.out.println("Saindo do Banco Digital. Obrigado!");
                        break;
                    default:
                        System.out.println("Opção inválida! Por favor, escolha uma opção de 1 a 4");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número de número.");
                sc.nextLine();
                option = 0;
            }
        } while (option != 4);
        sc.close();
    }

    private static void criarNovoClienteEConta(Scanner sc, Banco banco) {
        System.out.println("Digite o nome do novo cliente: ");
        String nomeCliente = sc.nextLine();

        Cliente novoCliente = new Cliente();
        novoCliente.setNome(nomeCliente);
        banco.adicionarCliente(novoCliente);

        System.out.println();
        System.out.println("Escolha o tipo de conta para " + nomeCliente + ":");
        System.out.println("1 - Conta Corrente");
        System.out.println("2 - Conta Poupanca");
        System.out.println("Opção: ");
        int tipoConta = sc.nextInt();
        sc.nextLine();

        Conta novaConta = null;
        if (tipoConta == 1) {
            novaConta = new ContaCorrente(novoCliente);
            System.out.println("Conta Corrente criada com sucesso para " + nomeCliente + ".");
        } else if (tipoConta == 2) {
            novaConta = new ContaPoupanca(novoCliente);
            System.out.println("Conta Poupança criada com sucesso para " + nomeCliente + ".");
        } else {
            System.out.println("Tipo de conta inválido. Nenhuma conta foi criada");
            return;
        }

        if (novaConta != null) {
            banco.adicionarConta(novaConta);
        }
    }

    private static void acessarContaExistente(Scanner sc, Banco banco) {
        System.out.println("Digite o nome do cliente para acessar a conta: ");
        String nomeCliente = sc.nextLine();
        nomeCliente = nomeCliente.trim().toLowerCase();

        Optional<Conta> contaOptional = banco.buscarContaPorNomeCliente(nomeCliente);

        if (contaOptional.isPresent()) {
            Conta contaAtual = contaOptional.get();
            System.out.println("\n *** Bem-vindo(a), " + nomeCliente + "! ***");
            int subOption = 0;
            double valor;

            do {
                System.out.println("\n *** Operações de Conta ***");
                System.out.println("1 - Sacar");
                System.out.println("2 - Depositar");
                System.out.println("3 - Transferir");
                System.out.println("4 - Saldo");
                System.out.println("5 - Extrato");
                System.out.println("6 - Voltar ao Menu Principal");
                System.out.println("Escolha uma opção: ");

                try {
                    subOption = sc.nextInt();
                    sc.nextLine();

                    switch (subOption) {
                        case 1:
                            System.out.println("Digite o valor para sacar: R$ ");
                            valor = sc.nextDouble();
                            sc.nextLine();
                            contaAtual.sacar(valor);
                            break;
                        case 2:
                            System.out.println("Digite o valor para depositar: R$ ");
                            valor = sc.nextDouble();
                            sc.nextLine();
                            contaAtual.depositar(valor);
                            break;
                        case 3:
                            System.out.println("Digite o valor para transferir: R$ ");
                            valor = sc.nextDouble();
                            sc.nextLine();

                            System.out.println("Digite o nome do cliente de destino: ");
                            String nomeDestino = sc.nextLine();
                            Optional<Conta> contaDestinoOpt = banco.buscarContaPorNomeCliente(nomeDestino);

                            if (contaDestinoOpt.isPresent()) {
                                contaAtual.transferir(valor, contaDestinoOpt.get());
                            } else {
                                System.out.println("Cliente de destino não encontrado.");
                            }
                            break;
                        case 4:
                            System.out.println("Seu saldo atual é: R$ " + String.format("%.2f", contaAtual.getSaldo()));
                            break;
                        case 5:
                            contaAtual.imprimirInfosComuns();
                            break;
                        case 6:
                            System.out.println("Voltando ao Menu Principal...");
                            break;
                        default:
                            System.out.println("Opção inválida! Por favor, escolha uma opção entre 1 e 6");
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número.");
                    sc.nextLine();
                    subOption = 0;
                }
            } while (subOption != 6);
        } else {
            System.out.println("Cliente ou conta não encontrados");
        }
    }

    private static void listaClientesEContas(Banco banco) {
        if (banco.getClientes().isEmpty()) {
            System.out.println("Nenhum cliente cadastrado no momento.");
        } else {
            System.out.println("\n *** Listando clientes e contas do " + banco.getNome() + " ***");

            for (Cliente cliente : banco.getClientes()) {
                System.out.println("Cliente: " + cliente.getNome());
            }
            System.out.println("\n *** Contas Cadastradas ***");

            if (banco.getContas().isEmpty()) {
                System.out.println("Nenhuma conta cadastrada.");
            } else {
                for (Conta conta : banco.getContas()) {
                    String tipo = (conta instanceof ContaCorrente) ? "Corrente" : "Poupança";
                    String nomeClienteDaConta = "Desconhecido";
                    if (conta instanceof ContaCorrente) {
                        nomeClienteDaConta = ((ContaCorrente) conta).getCliente().getNome();
                    } else if (conta instanceof ContaPoupanca) {
                        nomeClienteDaConta = ((ContaPoupanca) conta).getCliente().getNome();
                    }
                    System.out.println("Tipo: " + tipo + " | Cliente: " + nomeClienteDaConta +
                            " | Saldo: R$" + String.format("%.2f", conta.getSaldo()));
                }
            }
            System.out.println("*******************************");
        }
    }
}



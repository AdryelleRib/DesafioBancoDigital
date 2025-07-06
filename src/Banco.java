import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Banco {

    private String nome;
    private List<Cliente> clientes;
    private List<Conta> contas;

    public Banco(String nome) {
        this.nome = nome;
        this.clientes = new ArrayList<>();
        this.contas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void adicionarCliente(Cliente cliente) {
        if (clientes.stream().noneMatch(c -> c.getNome().equalsIgnoreCase(cliente.getNome()))){
            clientes.add(cliente);
            System.out.println("Cliente " + cliente.getNome() + " adicionado com sucesso!");
        } else {
            System.out.println("Cliente " + cliente.getNome() + " já existe!");
        }
    }

    public void adicionarConta(Conta conta){
        contas.add(conta);
        System.out.println("Conta adicionada com sucesso!");
    }

    public Optional<Cliente> buscarClientePorNome(String nome){
        return clientes.stream().filter(c -> c.getNome().equalsIgnoreCase(nome.trim())).findFirst();
    }

    public Optional<Conta> buscarContaPorNomeCliente(String nomeCliente){
        nomeCliente = nomeCliente.trim().toLowerCase();
        Optional<Cliente> clienteOptional = buscarClientePorNome(nomeCliente);

        if (clienteOptional.isPresent()){
            Cliente clienteEncontrado = clienteOptional.get();
            return contas.stream().filter(c -> {
                if (c instanceof ContaCorrente){
                    return ((ContaCorrente) c).getCliente().equals(clienteEncontrado);
                } else if (c instanceof ContaPoupanca){
                    return ((ContaPoupanca) c).getCliente().equals(clienteEncontrado);
                }
                return false;
            }).findFirst();
        }
        return Optional.empty();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Conta> getContas() {
        return contas;
    }
}

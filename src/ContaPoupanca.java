public class ContaPoupanca extends Conta {

    private Cliente cliente;

    public ContaPoupanca(Cliente cliente) {
        this.cliente = cliente;
        this.saldo = 0.0;
    }

    public Cliente getCliente() {
        return cliente;
    }

}

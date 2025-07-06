public class ContaCorrente extends Conta {

    public ContaCorrente(Cliente cliente) {
        super();
        this.cliente = cliente;
        this.saldo = 0.0;
    }

    public Cliente getCliente() {
        return cliente;
    }

}

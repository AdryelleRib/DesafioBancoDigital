public class Conta implements IConta {

    private static final int AGENCIA_PADRAO = 1;
    private static int SEQUENCIAL = 1;

    protected int agencia;
    protected int numero;
    protected double saldo;
    protected Cliente cliente;

    public Conta() {
        this.agencia = Conta.AGENCIA_PADRAO;
        this.numero = SEQUENCIAL++;
        this.cliente = cliente;
    }

    @Override
    public void sacar(double valor) {
        if (saldo > 0 && saldo >= valor) {
            saldo -= valor;
            System.out.println("Saque de R$" + valor + " realizado com sucesso!");
        } else {
            System.out.println("Não foi possível realizar o saque. Saldo insuficiente");
        }
    }

    @Override
    public void depositar(double valor) {
        if (saldo >= 0) {
            saldo += valor;
            System.out.println("Depósito de R$" + valor + " realizado com sucesso!");
        } else {
            System.out.println("Valor de depósito inválido");
        }
    }

    @Override
    public void transferir(double valor, Conta contaDestino) {
        if (valor > 0 && saldo >= valor) {
            this.sacar(valor);
            contaDestino.depositar(valor);
            System.out.println("Transferencia de R$" + valor + "realizado com sucesso!");
        } else {
            System.out.println("Não foi possível realizar a transferência. Saldo insuficiente");
        }
    }

    public double getSaldo() {
        return saldo;
    }

    protected void imprimirInfosComuns() {
        System.out.println(String.format("Titular: %s", this.cliente.getNome()));
        System.out.println(String.format("Agência: %d", this.agencia));
        System.out.println(String.format("Conta: %d", this.numero));
        System.out.println(String.format("Saldo: R$ %.2f", this.saldo));
    }
}
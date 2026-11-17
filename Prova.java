
import java.util.Scanner;
import java.util.Locale;

public class Main {

    public static final int TAM_CONTAS = 50;
    public static final int TAM_OP = 1000;

    public static Scanner sc = new Scanner(System.in);

    public static class Conta {
        int id;
        String cliente;
        double saldo;
        double limite;
    }

    public static class Operacao {
        int id;
        int idConta;
        char tipo;
        double valor;
    }

    // Metodo auxiliar para formatar o saldo

    public static String formatarSaldo(double valor) {
        String tipo = valor >= 0 ? "C" : "D";
        return String.format(Locale.US, "%.2f%s",
                Math.abs(valor), tipo);
    }

    // Metodo auxiliar pra ler valores positivos

    public static double lerValorPositivo(String mensagem) {
        double valor;

        do {
            System.out.print(mensagem);
            valor = sc.nextDouble();

            if (valor < 0) {
                System.out.println("Valor não pode ser negativo!");
            }

        } while (valor < 0);

        return valor;
    }

    // Questão 1

    public static int cadastrarConta(Conta[] v, int tam) {

        if (tam >= TAM_CONTAS) {
            System.out.println("Vetor de contas cheio!");
            return tam;
        }

        Conta nova = new Conta();

        System.out.print("Digite o ID da conta: ");
        nova.id = sc.nextInt();

        if (buscaId(v, tam, nova.id) != -1) {
            System.out.println("ID ja cadastrado!");
            return tam;
        }

        sc.nextLine();

        System.out.print("Digite o nome do cliente: ");
        nova.cliente = sc.nextLine();

        nova.saldo = lerValorPositivo("Digite o saldo: ");

        nova.limite = lerValorPositivo("Digite o limite: ");

        v[tam] = nova;

        System.out.println("Conta cadastrada com sucesso!");

        return tam + 1;
    }

    // Questão 2
    public static int buscaBinariaCliente(
            Conta[] v, int tam, String x) {

        int inicio = 0;
        int fim = tam - 1;

        while (inicio <= fim) {

            int meio = (inicio + fim) / 2;

            int resultado = v[meio].cliente.compareToIgnoreCase(x);

            if (resultado == 0) {
                return meio;
            } else if (resultado < 0) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }

        return -1;
    }

    // Questão 3
    public static int insertionSortCliente(Conta[] v, int tam) {

        for (int i = 1; i < tam; i++) {

            Conta aux = v[i];
            int j = i - 1;

            while (j >= 0 &&
                    v[j].cliente.compareToIgnoreCase(aux.cliente) > 0) {

                v[j + 1] = v[j];
                j--;
            }

            v[j + 1] = aux;
        }

        return tam;
    }

    // Questão 4
    public static int filtraOperacoes(
            Operacao[] vOp, int tamOp,
            Operacao[] vOpFiltrado, int x) {

        int cont = 0;

        for (int i = 0; i < tamOp; i++) {
            if (vOp[i].idConta == x) {
                vOpFiltrado[cont] = vOp[i];
                cont++;
            }
        }

        return cont;
    }

    // Metodo auxiliar para identificar o tipo de operacao
    public static String descricaoOperacao(char tipo) {

        if (tipo == 'D') {
            return "Depósito";
        } else if (tipo == 'S') {
            return "Saque";
        } else if (tipo == 'T') {
            return "Transferência";
        }

        return "Desconhecida";
    }

    // Questão 5
    public static void extrato(
            Conta[] vContas, int tamConta,
            Operacao[] vOp, int tamOp) {

        System.out.print("Digite o ID da conta: ");
        int id = sc.nextInt();

        int pos = buscaId(vContas, tamConta, id);

        if (pos == -1) {
            System.out.println("Conta nao encontrada!");
            return;
        }

        Operacao[] filtradas = new Operacao[TAM_OP];

        int tamFiltrado = filtraOperacoes(
                vOp, tamOp, filtradas, id);

        int inicio = tamFiltrado - 5;

        if (inicio < 0) {
            inicio = 0;
        }

        double saldo = 0;

        for (int i = 0; i < inicio; i++) {
            saldo += filtradas[i].valor;
        }

        System.out.println();
        System.out.println("Conta N." + id);
        System.out.println("Cliente: " + vContas[pos].cliente);

        System.out.printf("%-5s %-18s %12s %12s%n",
                "ID", "Operação", "Valor", "Saldo");

        System.out.printf("%-5s %-18s %12s %12s%n",
                "", "Saldo Anterior", "",
                formatarSaldo(saldo));

        for (int i = inicio; i < tamFiltrado; i++) {

            Operacao op = filtradas[i];
            saldo += op.valor;

            String valor = String.format(Locale.US, "%.2f%s",
                    Math.abs(op.valor),
                    op.valor >= 0 ? "C" : "D");

            System.out.printf("%04d  %-18s %12s %12s%n",
                    op.id,
                    descricaoOperacao(op.tipo),
                    valor,
                    formatarSaldo(saldo));
        }

        System.out.printf("%-35s %12s%n",
                "Saldo Atual", formatarSaldo(saldo));
    }

    public static int buscaId(Conta[] v, int tam, int x) {

        for (int i = 0; i < tam; i++) {
            if (v[i].id == x) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        Conta[] contas = new Conta[TAM_CONTAS];
        Operacao[] operacoes = new Operacao[TAM_OP];

        int tamConta = 0;
        int tamOp = 0;

        // As funções podem ser chamadas aqui.

    }
}

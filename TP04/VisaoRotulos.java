import java.util.ArrayList;
import java.util.Scanner;

public class VisaoRotulos {
    private ControleRotulos controleRotulos;
    private ControleTarefas controleTarefas;
    private Scanner scanner;

    // Construtor
    public VisaoRotulos(ControleRotulos controleRotulos, ControleTarefas controleTarefas) {
        this.controleRotulos = controleRotulos;
        this.controleTarefas = controleTarefas; // Inicialização do novo atributo
        this.scanner = new Scanner(System.in);
    }

    // Exibir Menu
    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n\tPUCBOOK 1.0");
            System.out.println("-------------------");
            System.out.println("> Início > Rótulos");
           // System.out.println("\tOpções exclusivas do Rótulo:");
            System.out.println("\t    1) Criar rótulo");
            System.out.println("\t    2) Alterar rótulo");
            System.out.println("\t    3) Excluir rótulo");
            System.out.println("\t    4) Listar rótulos");
            System.out.println();
           // System.out.println("\n\tOpções dos Rótulos envolvendo Tarefas:");
            System.out.println("\t    5) Listar tarefas por rótulo");
            System.out.println("\t    6) Vincular rótulo a uma tarefa");
            System.out.println("\t    7) Desvincular rótulo de uma tarefa");
            System.out.println("\t    0) Voltar ao menu principal");
            System.out.print("\n\tEscolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            try {
                switch (opcao) {
                    case 1 -> criarRotulo();
                    case 2 -> alterarRotulo();
                    case 3 -> excluirRotulo();
                    case 4 -> listarRotulos();
                    case 5 -> listarTarefasPorRotulo();
                    case 6 -> vincularRotuloATarefa();
                    case 7 -> desvincularRotuloDeTarefa();
                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    // Criar um rótulo
    private void criarRotulo() throws Exception {
        System.out.print("Digite o nome do rótulo: ");
        String nome = scanner.nextLine();
        Rotulo novoRotulo = new Rotulo(nome);

        if (controleRotulos.criarRotulo(novoRotulo)) {
            System.out.println("Rótulo criado com sucesso!");
        } else {
            System.out.println("Erro: Já existe um rótulo com esse nome.");
        }
    }

    // Alterar um rótulo
    private void alterarRotulo() throws Exception {
        listarRotulos();
        System.out.print("Digite o ID do rótulo a ser alterado: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        System.out.print("Digite o novo nome do rótulo: ");
        String novoNome = scanner.nextLine();

        Rotulo rotuloAlterado = new Rotulo(id, novoNome);
        if (controleRotulos.atualizarRotulo(rotuloAlterado)) {
            System.out.println("Rótulo alterado com sucesso!");
        } else {
            System.out.println("Erro ao alterar o rótulo.");
        }
    }

    // Excluir um rótulo
    private void excluirRotulo() throws Exception {
        listarRotulos();
        System.out.print("Digite o ID do rótulo a ser excluído: ");
        int id = scanner.nextInt();

        if (controleRotulos.excluirRotulo(id)) {
            System.out.println("Rótulo excluído com sucesso!");
        } else {
            System.out.println("Erro: Não foi possível excluir o rótulo. Ele pode estar associado a tarefas.");
        }
    }

    // Listar todos os rótulos
    private void listarRotulos() throws Exception {
        ArrayList<Rotulo> rotulos = controleRotulos.listarTodosRotulos();
        if (rotulos.isEmpty()) {
            System.out.println("Nenhum rótulo encontrado.");
        } else {
            System.out.println("\nRótulos cadastrados:");
            for (Rotulo rotulo : rotulos) {
                System.out.println(rotulo.getId() + ") " + rotulo.getRotulo());
            }
        }
    }

    // Vincular rótulo a uma tarefa
    private void vincularRotuloATarefa() throws Exception {
        ArrayList<Rotulo> rotulos = controleRotulos.listarTodosRotulos();
        if (rotulos.isEmpty()) {
            System.out.println("Nenhum rótulo encontrado. Crie um rótulo antes de continuar.");
            return;
        }

        System.out.println("Escolha um rótulo:");
        for (int i = 0; i < rotulos.size(); i++) {
            System.out.println((i + 1) + ") " + rotulos.get(i).getRotulo());
        }
        System.out.print("Digite o número do rótulo: ");
        int posicaoRotulo = scanner.nextInt() - 1;

        if (posicaoRotulo < 0 || posicaoRotulo >= rotulos.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        int idRotulo = rotulos.get(posicaoRotulo).getId();

        ArrayList<Tarefa> tarefas = controleTarefas.listarTodasTarefas();
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada. Crie uma tarefa antes de continuar.");
            return;
        }

        System.out.println("Escolha uma tarefa:");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println((i + 1) + ") " + tarefas.get(i).getNome());
        }
        System.out.print("Digite o número da tarefa: ");
        int posicaoTarefa = scanner.nextInt() - 1;

        if (posicaoTarefa < 0 || posicaoTarefa >= tarefas.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        int idTarefa = tarefas.get(posicaoTarefa).getId();

        controleRotulos.associarRotuloATarefa(idTarefa, idRotulo);
        System.out.println("Rótulo vinculado à tarefa com sucesso!");
    }

    // Desvincular rótulo de uma tarefa
    private void desvincularRotuloDeTarefa() throws Exception {
        ArrayList<Rotulo> rotulos = controleRotulos.listarTodosRotulos();
        if (rotulos.isEmpty()) {
            System.out.println("Nenhum rótulo encontrado.");
            return;
        }

        System.out.println("Escolha um rótulo:");
        for (int i = 0; i < rotulos.size(); i++) {
            System.out.println((i + 1) + ") " + rotulos.get(i).getRotulo());
        }
        System.out.print("Digite o número do rótulo: ");
        int posicaoRotulo = scanner.nextInt() - 1;

        if (posicaoRotulo < 0 || posicaoRotulo >= rotulos.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        int idRotulo = rotulos.get(posicaoRotulo).getId();

        ArrayList<Tarefa> tarefas = controleRotulos.listarTarefasPorRotulo(idRotulo);
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa associada a este rótulo.");
            return;
        }

        System.out.println("Escolha uma tarefa:");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println((i + 1) + ") " + tarefas.get(i).getNome());
        }
        System.out.print("Digite o número da tarefa: ");
        int posicaoTarefa = scanner.nextInt() - 1;

        if (posicaoTarefa < 0 || posicaoTarefa >= tarefas.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        int idTarefa = tarefas.get(posicaoTarefa).getId();

        controleRotulos.desassociarRotuloDeTarefa(idTarefa, idRotulo);
        System.out.println("Rótulo desvinculado da tarefa com sucesso!");
    }

    // Listar tarefas por rótulo
    private void listarTarefasPorRotulo() throws Exception {
        ArrayList<Rotulo> rotulos = controleRotulos.listarTodosRotulos();
        if (rotulos.isEmpty()) {
            System.out.println("Nenhum rótulo encontrado.");
            return;
        }

        System.out.println("Escolha um rótulo:");
        for (int i = 0; i < rotulos.size(); i++) {
            System.out.println((i + 1) + ") " + rotulos.get(i).getRotulo());
        }
        System.out.print("Digite o número do rótulo: ");
        int posicaoRotulo = scanner.nextInt() - 1;

        if (posicaoRotulo < 0 || posicaoRotulo >= rotulos.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        int idRotulo = rotulos.get(posicaoRotulo).getId();

        ArrayList<Tarefa> tarefas = controleRotulos.listarTarefasPorRotulo(idRotulo);
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa associada a este rótulo.");
        } else {
            System.out.println("\nTarefas associadas ao rótulo:");
            for (int i = 0; i < tarefas.size(); i++) {
                Tarefa tarefa = tarefas.get(i);
                System.out.println((i + 1) + ") " + tarefa.getNome());
            }
        }
    }

}

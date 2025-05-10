import java.util.ArrayList;
import java.util.Scanner;

public class VisaoCategorias {
    private ControleCategorias controleCategorias;
    @SuppressWarnings("unused")
    private ControleTarefas controleTarefas;
    private Scanner scanner;

    // Construtor que recebe ControleCategorias e ControleTarefas
    public VisaoCategorias(ControleCategorias controleCategorias, ControleTarefas controleTarefas) {
        this.controleCategorias = controleCategorias;
        this.controleTarefas = controleTarefas;
        this.scanner = new Scanner(System.in);
    }

    public void menu() throws Exception {
        int opcao;
        do {
            System.out.println("\n\tPUCBOOK 1.0");
            System.out.println("--------------------");
            System.out.println("> Início > Categorias\n");
            System.out.println("\t1) Incluir");
            System.out.println("\t2) Excluir");
            System.out.println("\t3) Listar");
            System.out.println("\t4) Atualizar Categoria");
            System.out.println("\t5) Gerar Relatório de Tarefas por Categoria");
            System.out.println("\t0) Retornar ao menu anterior");
            System.out.print("\tEscolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcao) {
                case 1:
                    adicionarCategoria();
                    break;
                case 2:
                    excluirCategoria();
                    break;
                case 3:
                    listarCategorias();
                    break;
                case 4:
                    atualizarCategoria();
                    break;
                case 5:
                    gerarRelatorioTarefasPorCategoria();
                    break;
                case 0:
                    System.out.println("Saindo do menu de categorias...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }

    private void atualizarCategoria() throws Exception {
        // Listar as categorias disponíveis
        ArrayList<Categoria> categorias = controleCategorias.listarTodasCategorias();

        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria encontrada para atualizar.");
            return;
        }

        // Exibir as categorias para o usuário escolher
        System.out.println("Escolha a categoria que deseja atualizar:");
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println("\t(" + (i + 1) + ") " + categorias.get(i).getNome());
        }

        System.out.print("Opção: ");
        int escolha = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (escolha < 1 || escolha > categorias.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        Categoria categoriaSelecionada = categorias.get(escolha - 1);

        // Solicitar novos dados para a categoria
        System.out.println("Atualizando a categoria: " + categoriaSelecionada.getNome());

        System.out.print("Novo nome (ou Enter para manter o mesmo): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.isBlank()) {
            categoriaSelecionada.setNome(novoNome);
        }

        // Atualiza a categoria no controle
        if (controleCategorias.atualizarCategoria(categoriaSelecionada)) {
            System.out.println("Categoria atualizada com sucesso!");
        } else {
            System.out.println("Erro ao atualizar a categoria.");
        }
    }

    private void adicionarCategoria() throws Exception {
        System.out.print("Nome da nova categoria: ");
        String nome = scanner.nextLine();
        Categoria categoria = new Categoria(-1, nome); // ID será gerado automaticamente no `create`
        if (controleCategorias.adicionarCategoria(categoria)) {
            System.out.println("Categoria adicionada com sucesso!");
        } else {
            System.out.println("Erro ao adicionar categoria.");
        }
    }

    private void excluirCategoria() throws Exception {
        ArrayList<Categoria> categorias = controleCategorias.listarTodasCategorias();

        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria encontrada para excluir.");
            return;
        }

        System.out.println("Escolha a categoria a ser excluída:");
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println("\t(" + (i + 1) + ") " + categorias.get(i).getNome());
        }

        try {
            System.out.print("Opção: ");
            String escolhaStr = scanner.nextLine();
            int escolha;
            try {
                escolha = Integer.parseInt(escolhaStr);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número válido.");
                return;
            }

            int idCategoria = categorias.get(escolha - 1).getId();
            if (controleCategorias.excluirCategoria(idCategoria)) {
                System.out.println("Categoria excluída com sucesso!");
            } else {
                System.out.println("Não foi possível excluir a categoria.");
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida. Por favor, insira um número válido.");
            scanner.nextLine(); // Limpar o buffer em caso de erro
        }
    }

    private void listarCategorias() throws Exception {
        System.out.println("Lista de Categorias:\n");
        for (Categoria categoria : controleCategorias.listarTodasCategorias()) {
            System.out.println("ID: [" + categoria.getId() + "] - NOME: " + categoria.getNome());
        }
    }

    private void gerarRelatorioTarefasPorCategoria() throws Exception {
        controleCategorias.gerarRelatorioTarefasPorCategoria();
    }
}

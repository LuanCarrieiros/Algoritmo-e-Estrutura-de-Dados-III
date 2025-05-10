import java.util.Scanner;

public class IO {

    public static void main(String[] args) {

        try {
            // Inicializa arquivos para categorias, tarefas e rótulos
            ArquivoCategorias arquivoCategorias = new ArquivoCategorias(Categoria.class.getConstructor(), "categorias");
            ArquivoRotulos arquivoRotulos = new ArquivoRotulos(Rotulo.class.getConstructor(), "rotulos");
            ArquivoTarefas arquivoTarefas = new ArquivoTarefas(Tarefa.class.getConstructor(), "tarefas");

            // Inicializa os controles para categorias, tarefas e rótulos
            ControleCategorias controleCategorias = new ControleCategorias(arquivoCategorias, arquivoTarefas);
            ControleTarefas controleTarefas = new ControleTarefas(arquivoTarefas, arquivoCategorias, arquivoRotulos);
            ControleRotulos controleRotulos = new ControleRotulos(arquivoRotulos, arquivoTarefas);

            // Passa os controles para as visões
            VisaoCategorias visaoCategorias = new VisaoCategorias(controleCategorias, controleTarefas);
            VisaoTarefas visaoTarefas = new VisaoTarefas(controleTarefas, controleCategorias, controleRotulos);
            VisaoRotulos visaoRotulos = new VisaoRotulos(controleRotulos, controleTarefas);

            Scanner scanner = new Scanner(System.in);

            int opcao;
            do {
                System.out.println("\nPUCBOOK 1.0");
                System.out.println("-----------");
                System.out.println("> INÍCIO ");
                System.out.println("\t1. Gerenciar Categorias");
                System.out.println("\t2. Gerenciar Tarefas");
                System.out.println("\t3. Gerenciar Rótulos");
                System.out.println("\t4. Gerenciar Backups");
                System.out.println("\t0. Sair");
                System.out.print("\tEscolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer

                switch (opcao) {
                    case 1:
                        visaoCategorias.menu();
                        break;
                    case 2:
                        visaoTarefas.menu();
                        break;
                    case 3:
                        visaoRotulos.exibirMenu();
                        break;
                    case 4: 
                        (new VisaoBackUps()).menu();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } while (opcao != 0);

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

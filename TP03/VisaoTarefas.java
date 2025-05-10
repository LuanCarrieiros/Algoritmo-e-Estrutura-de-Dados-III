import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class VisaoTarefas {
    private ControleTarefas controleTarefas;
    private ControleCategorias controleCategorias;
    private ControleRotulos controleRotulos;

    private Scanner scanner;
    // Formato esperado: DD-MM-YYYY
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    public VisaoTarefas(ControleTarefas controleTarefas, ControleCategorias controleCategorias,
            ControleRotulos controleRotulos) {
        this.controleTarefas = controleTarefas;
        this.controleCategorias = controleCategorias;
        this.controleRotulos = controleRotulos; // Inicializa o controle de rótulos
        this.scanner = new Scanner(System.in);
    }

    public void menu() throws Exception {
        int opcao;
        do {
            System.out.println("\n\tPUCBOOK 1.0");
            System.out.println("-------------------");
            System.out.println("> Início > Tarefas\n");
            System.out.println("\tOpções exclusivas da Tarefa:");
            System.out.println("\t    1)  Incluir   Tarefa");
            System.out.println("\t    2)  Excluir   Tarefa");
            System.out.println("\t    3)  Listar    detalhes das Tarefas");
            System.out.println("\t    4)  Atualizar Tarefa");
            System.out.println("\n\tOpções das Tarefas envolvendo Rótulos:");
            System.out.println("\t    5)  Atualizar Rótulo de uma Tarefa");
            System.out.println("\t    6)  Adicionar Rótulo à uma Tarefa");
            System.out.println("\t    7)  Excluir   Rótulo de uma Tarefa");
            System.out.println("\t    8)  Listar    Rótulos de uma Tarefa");
            System.out.println("\t    9)  Buscar    Tarefa por termos na Tarefa");
            System.out.println("\t    10) Buscar    Tarefa pelo rótulo");
            System.out.println("\t0) Retornar ao menu anterior");
            System.out.print("\nEscolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcao) {
                case 1:
                    adicionarTarefa();
                    break;
                case 2:
                    excluirTarefa();
                    break;
                case 3:
                    listarTarefasComRotulos();
                    break;
                case 4:
                    atualizarTarefa();
                    break;
                case 5:
                    atualizarRotulosDeTarefa();
                    break;
                case 6:
                    adicionarRotuloATarefa();
                    break;
                case 7:
                    excluirRotuloDeTarefa();
                    break;
                case 8:
                    listarRotulosDeTarefa();
                    break;
                case 9:
                    buscarTarefasPorTermo();
                    break;
                case 10:
                    buscarTarefasPorRotulo();
                    break;

                case 0:
                    System.out.println("Saindo do menu de tarefas...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }

    private void atualizarTarefa() throws Exception {
        // Listar as tarefas disponíveis
        ArrayList<Tarefa> tarefas = controleTarefas.listarTodasTarefas();

        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada para atualizar.");
            return;
        }

        // Exibir as tarefas para o usuário escolher
        System.out.println("Escolha a tarefa que deseja atualizar:");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println("\t(" + (i + 1) + ") " + tarefas.get(i).getNome());
        }

        System.out.print("Opção: ");
        int escolha = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (escolha < 1 || escolha > tarefas.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        Tarefa tarefaSelecionada = tarefas.get(escolha - 1);

        // Solicitar novos dados para a tarefa
        System.out.println("Atualizando a tarefa: " + tarefaSelecionada.getNome());

        System.out.print("Novo nome (ou Enter para manter o mesmo): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.isBlank()) {
            tarefaSelecionada.setNome(novoNome);
        }

        System.out.print("Nova data de criação (formato: DD-MM-YYYY) ou Enter para manter: ");
        String novaDataCriacao = scanner.nextLine();
        if (!novaDataCriacao.isBlank()) {
            try {
                tarefaSelecionada.setDataCriacao(LocalDate.parse(novaDataCriacao, formatter));
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. A data de criação será mantida.");
            }
        }

        System.out.print("Nova data de conclusão (formato: DD-MM-YYYY) ou Enter para manter: ");
        String novaDataConclusao = scanner.nextLine();
        if (!novaDataConclusao.isBlank()) {
            try {
                tarefaSelecionada.setDataConclusao(LocalDate.parse(novaDataConclusao, formatter));
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. A data de conclusão será mantida.");
            }
        }

        System.out.print("Novo status ([0 - Pendente][1 - Em Progresso][2 - Concluída]) ou enter para manter: ");
        String novoStatusInput = scanner.nextLine();
        if (!novoStatusInput.isBlank()) {
            byte novoStatus = Byte.parseByte(novoStatusInput);
            if (novoStatus >= 0 && novoStatus <= 2) {
                tarefaSelecionada.setStatus(novoStatus);
            } else {
                System.out.println("Status inválido. Mantendo o atual.");
            }
        }

        System.out.print("Nova prioridade ([0 - Baixa][1 - Média][2 - Alta]) ou enter para manter: ");
        String novaPrioridadeInput = scanner.nextLine();
        if (!novaPrioridadeInput.isBlank()) {
            byte novaPrioridade = Byte.parseByte(novaPrioridadeInput);
            if (novaPrioridade >= 0 && novaPrioridade <= 2) {
                tarefaSelecionada.setPrioridade(novaPrioridade);
            } else {
                System.out.println("Prioridade inválida. Mantendo a atual.");
            }
        }

        // Listar categorias para seleção
        System.out.println("Escolha uma nova categoria (ou 0 para manter a atual):");
        ArrayList<Categoria> categorias = controleCategorias.listarTodasCategorias();
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println("\t(" + (i + 1) + ") " + categorias.get(i).getNome());
        }

        System.out.print("Opção: ");
        int escolhaCategoria = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer
        if (escolhaCategoria >= 1 && escolhaCategoria <= categorias.size()) {
            int novaIdCategoria = categorias.get(escolhaCategoria - 1).getId();
            tarefaSelecionada.setIdCategoria(novaIdCategoria);
        }

        // Atualiza a tarefa no controle
        if (controleTarefas.atualizarTarefa(tarefaSelecionada)) {
            System.out.println("Tarefa atualizada com sucesso!");
        } else {
            System.out.println("Erro ao atualizar a tarefa.");
        }
    }

    private void adicionarTarefa() throws Exception {
        System.out.print("Nome da Tarefa: ");
        String nome = scanner.nextLine();

        // Listar categorias para seleção
        System.out.println("Escolha uma categoria:");
        ArrayList<Categoria> categorias = controleCategorias.listarTodasCategorias();
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println("\t(" + (i + 1) + ") " + categorias.get(i).getNome());
        }

        System.out.print("Opção: ");
        int escolhaCategoria;
        try {
            escolhaCategoria = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            // Verificar se a escolha está dentro do limite
            if (escolhaCategoria < 1 || escolhaCategoria > categorias.size()) {
                throw new IndexOutOfBoundsException("Opção fora do limite.");
            }

            // Mapeia a escolha do usuário para o ID real da categoria
            int idCategoria = categorias.get(escolhaCategoria - 1).getId();

            // Seleção de rótulos
            System.out.println(
                    "Escolha os rótulos para a tarefa (digite os números separados por vírgulas, ou Enter para nenhum):");
            ArrayList<Rotulo> rotulos = controleRotulos.listarTodosRotulos();
            for (int i = 0; i < rotulos.size(); i++) {
                System.out.println("\t(" + (i + 1) + ") " + rotulos.get(i).getRotulo());
            }

            System.out.print("Opção: ");
            String escolhaRotulos = scanner.nextLine();
            ArrayList<Integer> idsRotulos = new ArrayList<>();

            if (!escolhaRotulos.isBlank()) {
                String[] indices = escolhaRotulos.split(",");
                for (String indice : indices) {
                    int escolha = Integer.parseInt(indice.trim());
                    if (escolha < 1 || escolha > rotulos.size()) {
                        throw new IndexOutOfBoundsException("Opção de rótulo fora do limite.");
                    }
                    idsRotulos.add(rotulos.get(escolha - 1).getId());
                }
            }

            // Solicitar outros atributos
            
            // Solicitar outros atributos
            System.out.print("Data de criação (formato: DD-MM-YYYY) ou Enter para data atual: ");
            String dataCriacaoInput = scanner.nextLine();
            LocalDate dataCriacao;
            if (dataCriacaoInput.isBlank()) {
                dataCriacao = LocalDate.now();
            } else {
                try {
                    dataCriacao = LocalDate.parse(dataCriacaoInput, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("Formato inválido. Usando a data atual como padrão.");
                    dataCriacao = LocalDate.now();
                }
            }

            System.out.print("Data de conclusão (formato: DD-MM-YYYY) ou Enter para indefinida: ");
            String dataConclusaoInput = scanner.nextLine();
            LocalDate dataConclusao;
            if (dataConclusaoInput.isBlank()) {
                dataConclusao = LocalDate.of(1970, 1, 1); // Data padrão
            } else {
                try {
                    dataConclusao = LocalDate.parse(dataConclusaoInput, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("Formato inválido. Usando a data indefinida como padrão.");
                    dataConclusao = LocalDate.of(1970, 1, 1);
                }
            }

            System.out
                    .print("Status ([0 - Pendente][1 - Em Progresso][2 - Concluída] ou Enter para padrão [Pendente]): ");
            String statusInput = scanner.nextLine();
            byte status = statusInput.isBlank() ? 0 : Byte.parseByte(statusInput);

            System.out.print("Prioridade ([0 - Baixa][1 - Média][2 - Alta] ou Enter para padrão [Baixa]): ");
            String prioridadeInput = scanner.nextLine();
            byte prioridade = prioridadeInput.isBlank() ? 0 : Byte.parseByte(prioridadeInput);

            // Cria a nova tarefa com todos os atributos, incluindo os IDs dos rótulos
            Tarefa tarefa = new Tarefa(-1, nome, dataCriacao, dataConclusao, status, prioridade, idCategoria,
                    idsRotulos);
            if (controleTarefas.adicionarTarefa(tarefa)) {
                System.out.println("Tarefa adicionada com sucesso!");
            } else {
                System.out.println("Erro ao adicionar tarefa.");
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void excluirTarefa() throws Exception {
        ArrayList<Tarefa> tarefas = controleTarefas.listarTodasTarefas();

        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada para excluir.");
            return;
        }

        System.out.println("Escolha a tarefa a ser excluída:");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println((i + 1) + ") " + tarefas.get(i).getNome());
        }

        System.out.print("Opção: ");
        int escolha = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (escolha < 1 || escolha > tarefas.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        int idTarefa = tarefas.get(escolha - 1).getId();
        if (controleTarefas.excluirTarefa(idTarefa)) {
            System.out.println("Tarefa excluída com sucesso!");
        } else {
            System.out.println("Erro ao excluir tarefa.");
        }
    }

    private void listarTarefasComRotulos() throws Exception {
        ArrayList<Tarefa> tarefas = controleTarefas.listarTodasTarefas();

        System.out.println("Tarefa(s):");
        for (Tarefa tarefa : tarefas) {

            System.out.println(tarefa);

            // Buscar os rótulos associados
            ArrayList<Rotulo> rotulos = controleRotulos.buscarRotulosPorTarefa(tarefa.getId());
            System.out.print("Rótulos associados.: [");
            if (!rotulos.isEmpty()) {
                for (Rotulo rotulo : rotulos) {
                    System.out.print("-> \"" + rotulo.getRotulo() + "\" ");
                }
                System.out.print("]\n");
            } else {
                System.out.print(" Nenhum rótulo associado. ]\n");
            }
        }
    }

    private void adicionarRotuloATarefa() throws Exception {
        // Exibir tarefas para o usuário escolher
        ArrayList<Tarefa> tarefas = controleTarefas.listarTodasTarefas();
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }

        System.out.println("Escolha uma tarefa:");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println((i + 1) + ") " + tarefas.get(i).getNome());
        }

        System.out.print("Opção: ");
        int escolhaTarefa = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        if (escolhaTarefa < 1 || escolhaTarefa > tarefas.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        int idTarefa = tarefas.get(escolhaTarefa - 1).getId();

        // Exibir rótulos para o usuário escolher
        ArrayList<Rotulo> rotulos = controleRotulos.listarTodosRotulos();
        if (rotulos.isEmpty()) {
            System.out.println("Nenhum rótulo encontrado.");
            return;
        }

        System.out.println("Escolha um rótulo para adicionar à tarefa:");
        for (int i = 0; i < rotulos.size(); i++) {
            System.out.println((i + 1) + ") " + rotulos.get(i).getRotulo());
        }

        System.out.print("Opção: ");
        int escolhaRotulo = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        if (escolhaRotulo < 1 || escolhaRotulo > rotulos.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        int idRotulo = rotulos.get(escolhaRotulo - 1).getId();

        // Associar rótulo à tarefa
        if (controleTarefas.adicionarRotuloATarefa(idTarefa, idRotulo)) {
            System.out.println("Rótulo adicionado com sucesso!");
        } else {
            System.out.println("Erro ao adicionar rótulo à tarefa.");
        }
    }

    private void excluirRotuloDeTarefa() throws Exception {
        // Exibir tarefas para o usuário escolher
        ArrayList<Tarefa> tarefas = controleTarefas.listarTodasTarefas();
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }

        System.out.println("Escolha uma tarefa:");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println((i + 1) + ") " + tarefas.get(i).getNome());
        }

        System.out.print("Opção: ");
        int escolhaTarefa = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        if (escolhaTarefa < 1 || escolhaTarefa > tarefas.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        Tarefa tarefaSelecionada = tarefas.get(escolhaTarefa - 1);

        // Exibir os rótulos associados à tarefa
        ArrayList<Integer> idsRotulos = tarefaSelecionada.getIDsRotulos();
        if (idsRotulos.isEmpty()) {
            System.out.println("A tarefa não possui rótulos associados.");
            return;
        }

        System.out.println("Escolha o rótulo para excluir:");
        for (int i = 0; i < idsRotulos.size(); i++) {
            Rotulo rotulo = controleRotulos.buscarRotuloPorId(idsRotulos.get(i));
            System.out.println((i + 1) + ") " + (rotulo != null ? rotulo.getRotulo() : "Rótulo não encontrado"));
        }

        System.out.print("Opção: ");
        int escolhaRotulo = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        if (escolhaRotulo < 1 || escolhaRotulo > idsRotulos.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        int idRotulo = idsRotulos.get(escolhaRotulo - 1);

        // Excluir o rótulo da tarefa
        if (controleTarefas.excluirRotuloDeTarefa(tarefaSelecionada.getId(), idRotulo)) {
            System.out.println("Rótulo removido com sucesso!");
        } else {
            System.out.println("Erro ao remover rótulo da tarefa.");
        }
    }

    private void listarRotulosDeTarefa() throws Exception {
        // Exibir tarefas para o usuário escolher
        ArrayList<Tarefa> tarefas = controleTarefas.listarTodasTarefas();
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }

        System.out.println("Escolha uma tarefa para listar os rótulos:");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println((i + 1) + ") " + tarefas.get(i).getNome());
        }

        System.out.print("Opção: ");
        int escolhaTarefa = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        if (escolhaTarefa < 1 || escolhaTarefa > tarefas.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        Tarefa tarefaSelecionada = tarefas.get(escolhaTarefa - 1);

        // Listar rótulos associados à tarefa
        ArrayList<Rotulo> rotulos = controleTarefas.listarRotulosDeTarefa(tarefaSelecionada.getId());
        if (rotulos.isEmpty()) {
            System.out.println("Nenhum rótulo associado à tarefa.");
        } else {
            System.out.println("Rótulos associados à tarefa:");
            for (Rotulo rotulo : rotulos) {
                System.out.println("\t- " + rotulo.getRotulo());
            }
        }
    }

    private void atualizarRotulosDeTarefa() throws Exception {
        // Exibir tarefas para o usuário escolher
        ArrayList<Tarefa> tarefas = controleTarefas.listarTodasTarefas();
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }

        System.out.println("Escolha uma tarefa para atualizar os rótulos:");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println((i + 1) + ") " + tarefas.get(i).getNome());
        }

        System.out.print("Opção: ");
        int escolhaTarefa = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        if (escolhaTarefa < 1 || escolhaTarefa > tarefas.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        Tarefa tarefaSelecionada = tarefas.get(escolhaTarefa - 1);

        // Exibir rótulos associados à tarefa
        System.out.println("Rótulos atuais da tarefa:");
        ArrayList<Rotulo> rotulosAtuais = controleTarefas.listarRotulosDeTarefa(tarefaSelecionada.getId());
        if (rotulosAtuais.isEmpty()) {
            System.out.println("Nenhum rótulo associado.");
        } else {
            for (Rotulo rotulo : rotulosAtuais) {
                System.out.println("\t- " + rotulo.getRotulo());
            }
        }

        // Exibir todos os rótulos disponíveis
        System.out.println("\nEscolha um novo rótulo para a tarefa (digite o número, ou Enter para manter):");
        ArrayList<Rotulo> todosRotulos = controleRotulos.listarTodosRotulos();
        for (int i = 0; i < todosRotulos.size(); i++) {
            System.out.println((i + 1) + ") " + todosRotulos.get(i).getRotulo());
        }

        System.out.print("Opção: ");
        String escolhaRotulos = scanner.nextLine();
        ArrayList<Integer> novosIdsRotulos = new ArrayList<>();

        if (!escolhaRotulos.isBlank()) {
            String[] indices = escolhaRotulos.split(",");
            for (String indice : indices) {
                int escolha = Integer.parseInt(indice.trim());
                if (escolha < 1 || escolha > todosRotulos.size()) {
                    throw new IndexOutOfBoundsException("Opção de rótulo fora do limite.");
                }
                novosIdsRotulos.add(todosRotulos.get(escolha - 1).getId());
            }
        }

        // Atualizar os rótulos da tarefa
        if (controleTarefas.atualizarRotulosDeTarefa(tarefaSelecionada.getId(), novosIdsRotulos)) {
            System.out.println("Rótulos atualizados com sucesso!");
        } else {
            System.out.println("Erro ao atualizar os rótulos da tarefa.");
        }
    }

    private void buscarTarefasPorTermo() throws Exception {
        System.out.print("Digite o termo para buscar: ");
        String termo = scanner.nextLine();

        ArrayList<Tarefa> tarefas = controleTarefas.buscarTarefasPorTermo(termo);

        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada para o termo: " + termo);
        } else {
            System.out.println("Tarefas encontradas:");
            for (Tarefa tarefa : tarefas) {
                System.out.println(tarefa);
            }
        }
    }

    private void buscarTarefasPorRotulo() throws Exception {
        // Exibir rótulos disponíveis para o usuário escolher
        ArrayList<Rotulo> rotulos = controleRotulos.listarTodosRotulos();
        if (rotulos.isEmpty()) {
            System.out.println("Nenhum rótulo encontrado.");
            return;
        }

        System.out.println("Escolha um rótulo para buscar as tarefas associadas:");
        for (int i = 0; i < rotulos.size(); i++) {
            System.out.println((i + 1) + ") " + rotulos.get(i).getRotulo());
        }

        System.out.print("Opção: ");
        int escolhaRotulo = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        if (escolhaRotulo < 1 || escolhaRotulo > rotulos.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        // Recuperar o ID do rótulo selecionado
        Rotulo rotuloSelecionado = rotulos.get(escolhaRotulo - 1);

        // Buscar tarefas associadas ao rótulo selecionado
        ArrayList<Tarefa> tarefasEncontradas = controleTarefas.buscarTarefaPorRotulo(rotuloSelecionado.getRotulo());
        if (tarefasEncontradas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada para o rótulo: " + rotuloSelecionado.getRotulo());
        } else {
            System.out.println("Tarefas associadas ao rótulo \"" + rotuloSelecionado.getRotulo() + "\":");
            for (Tarefa tarefa : tarefasEncontradas) {
                System.out.println(tarefa);
            }
        }
    }

}

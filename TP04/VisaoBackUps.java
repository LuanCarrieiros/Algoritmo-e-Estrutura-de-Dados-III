import java.io.File;
import java.util.Scanner;
import aed3.*;

public class VisaoBackUps {
    private static Scanner console = new Scanner(System.in);

    // Método para exibir o menu
    public void menu() {
        int opcao;
        do {
            System.out.println("\n\tPUCBOOK 1.0");
            System.out.println("--------------------");
            System.out.println("> Início > Backups\n");

            System.out.println("\t1) Fazer Backup");
            System.out.println("\t2) Carregar Backups antigos");
            System.out.println("\t0) Retornar ao menu anterior");

            
            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            // Seleciona a operação
            switch (opcao) {
                case 1:
                    fazerBackup();
                    break;
                case 2:
                    carregarBackup();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        } while (opcao != 0);
    }

    private void fazerBackup() {
        Compactador.compacta();
    }

    private void carregarBackup() {
        File backupDir = new File("./backups");
        File[] backups = backupDir.listFiles((dir, name) -> name.endsWith(".db"));

        if (backups != null && backups.length > 0) {
            System.out.println("Selecione um backup para carregar:");
            for (int i = 0; i < backups.length; i++) {
                System.out.println((i + 1) + ") " + backups[i].getName());
            }
            System.out.print("\nOpção: ");
            int opcao = Integer.valueOf(console.nextLine()) - 1;

            if (opcao >= 0 && opcao < backups.length) {
                String backupName = backups[opcao].getName();
                String versao = backupName.substring(7, backupName.length() - 3); // Extrai a data do nome do arquivo
                Compactador.descompacta(versao);
            } else {
                System.out.println("Opção inválida");
            }
        } else {
            System.out.println("Nenhum backup encontrado.");
        }
    }

}

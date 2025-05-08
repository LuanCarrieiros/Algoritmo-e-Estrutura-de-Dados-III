import java.io.File;
import java.time.LocalDate;
import aed3.*;

public class IO {

    public static void main(String[] args) {

        (new File(".\\dados\\tarefas.db")).delete();
        (new File(".\\dados\\tarefas.hash_d.db")).delete();
        (new File(".\\dados\\tarefas.hash_c.db")).delete();

        Tarefa t1 = new Tarefa("Estudar", LocalDate.of(2024, 9, 13), LocalDate.of(2024, 9, 14), (byte) 1, (byte) 2);
        Tarefa t2 = new Tarefa("Férias", LocalDate.of(2024, 12, 18), LocalDate.of(2025, 2, 1), (byte) 0, (byte) 2);
        Tarefa t3 = new Tarefa("Levar a avó no jiu jitsu", LocalDate.of(2024, 9, 17), LocalDate.of(2024, 9, 18),
                (byte) 2, (byte) 1);
        Tarefa t4 = new Tarefa("Agredir o Iago", LocalDate.of(2024, 9, 13), LocalDate.of(2024, 9, 14), (byte) 0,
                (byte) 2);

        Tarefa t;

        try {

            Arquivo<Tarefa> arqTarefas = new Arquivo<>(Tarefa.class.getConstructor(), "tarefas");

            // ------------------ Inserção ---------------------//
            arqTarefas.create(t1);
            arqTarefas.create(t2);
            arqTarefas.create(t3);
            arqTarefas.create(t4);

            // ------------------ Leitura pelo ID ---------------------//
            System.out.println("\n\nLeitura pelo ID");
            t = arqTarefas.read(1);
            if (t != null)
                System.out.println(t);
            else
                System.out.println("Tarefa 1 não encontrada!");
            
            t = arqTarefas.read(2);
            if (t != null)
                System.out.println(t);
            else
                System.out.println("Tarefa 2 não encontrada!");

            t = arqTarefas.read(3);
            if (t != null)
                System.out.println(t);
            else
                System.out.println("Tarefa 3 não encontrada!");

            t = arqTarefas.read(4);
            if (t != null)
                System.out.println(t);
            else
                System.out.println("Tarefa 4 não encontrada!");

            // ------------------ Atualização ---------------------//
            System.out.println("\n\nTestando Update");
            t1.setNome("Estudar após agredir o Iago");
            t2.setId(t4.getId());

            arqTarefas.update(t1);
            arqTarefas.update(t2);

            t = arqTarefas.read(1);
            if (t != null)
                System.out.println(t);
            else
                System.out.println("Tarefa 1 não encontrada!");
            t = arqTarefas.read(4);
            if (t != null)
                System.out.println(t);
            else
                System.out.println("Tarefa 2 não encontrada!");

            // --------------------------- Remoção ----------------------------//
            // Antes da remoção
            System.out.println("\n\nAntes da remoção");
            t = arqTarefas.read(1);
            if (t != null)
                System.out.println(t);
            else
                System.out.println("Tarefa 1 não encontrada!");
            
            t = arqTarefas.read(2);
            if (t != null)
                System.out.println(t);
            else
                System.out.println("Tarefa 2 não encontrada!");

            t = arqTarefas.read(3);
            if (t != null)
                System.out.println(t);
            else
                System.out.println("Tarefa 3 não encontrada!");

            t = arqTarefas.read(4);
            if (t != null)
                System.out.println(t);
            else
                System.out.println("Tarefa 4 não encontrada!");

            // Depois da remoção
            System.out.println("\n\nDepois da remoção\n");
            arqTarefas.delete(1);
            arqTarefas.delete(2);
            arqTarefas.delete(3);
            arqTarefas.delete(4);

            t = arqTarefas.read(1);
            if (t != null)
                System.out.println(t);
            else
                System.out.println("Tarefa 1 não encontrada!");
            
            t = arqTarefas.read(2);
            if (t != null)
                System.out.println(t);
            else
                System.out.println("Tarefa 2 não encontrada!");

            t = arqTarefas.read(3);
            if (t != null)
                System.out.println(t);
            else
                System.out.println("Tarefa 3 não encontrada!");

            t = arqTarefas.read(4);
            if (t != null)
                System.out.println(t);
            else
                System.out.println("Tarefa 4 não encontrada!");

            // Reorganização
            // System.out.println("\n\nReorganização do arquivo de Tarefas");
            // arqTarefas.reorganizar();
            

        } catch (Exception e) {
            e.printStackTrace();
            ;
        }

    }
}
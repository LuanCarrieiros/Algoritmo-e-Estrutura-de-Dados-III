package aed3;

import java.io.RandomAccessFile;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.io.File;

public class Compactador {
    public static void descompacta(String versao) {
        String backupFilePath = "./backups/backup_" + versao + ".db";
        String folderPath = "dados";
        RandomAccessFile backupFile;

        try {
            File dadosDir = new File(folderPath);
            if (!dadosDir.exists()) {
                dadosDir.mkdirs();
            }

            backupFile = new RandomAccessFile(backupFilePath, "r");

            while (backupFile.getFilePointer() < backupFile.length()) {
                // Lê o tamanho do nome do arquivo
                int tamNome = backupFile.readInt();

                // Lê o nome do arquivo em bytes
                byte[] nomeArquivo = new byte[tamNome];
                backupFile.read(nomeArquivo);
                String arquivo = new String(nomeArquivo);

                // Lê o tamanho dos dados compactados
                int tam = backupFile.readInt();

                // Lê os dados compactados
                byte[] ba = new byte[tam];
                backupFile.read(ba);

                // Descompacta os dados
                byte[] dadosDecodificados = LZW.decodifica(ba);

                // Escreve os dados descompactados no arquivo na pasta "dados"
                Path filePath = Paths.get(folderPath, arquivo);
                Files.write(filePath, dadosDecodificados);

                System.out.println("Arquivo: " + arquivo + " descompactado e criado em " + folderPath);
            }

            backupFile.close();
            System.out.println("Descompactação concluída com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void compacta() {
        String pastaDados = "dados";
        String arquivoBackup = "./backups/backup_" + LocalDate.now() + ".db"; // Caminho para o arquivo de backup
        RandomAccessFile backupFile;

        int totalBytesOriginais = 0;
        int totalBytesCompactados = 0;

        try {
            File pasta = new File(pastaDados);
            if (!pasta.exists()) {
                System.err.println("Erro: Pasta '" + pastaDados + "' não encontrada.");
                return;
            }

            File backupDir = new File("./backups");
            if (!backupDir.exists())
                backupDir.mkdirs();

            backupFile = new RandomAccessFile(arquivoBackup, "rw");

            @SuppressWarnings("resource")
            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(pastaDados));

            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    String fileName = path.getFileName().toString();
                    byte[] dataBytes = Files.readAllBytes(path);
                    byte[] compressedData = LZW.codifica(dataBytes);

                    if (compressedData != null) {
                        // Escrevendo no arquivo de backup
                        backupFile.writeInt(fileName.length());
                        backupFile.write(fileName.getBytes());
                        backupFile.writeInt(compressedData.length);
                        backupFile.write(compressedData);

                        // Cálculo e relatório
                        System.out.println("\nArquivo: " + fileName);
                        System.out.println("Bytes originais: " + dataBytes.length);
                        System.out.println("Bytes compactados: " + compressedData.length);

                        float eficiencia = 100 * (1 - (float) compressedData.length / dataBytes.length);
                        System.out.printf("Eficiência: %.2f%%\n", eficiencia);

                        totalBytesOriginais += dataBytes.length;
                        totalBytesCompactados += compressedData.length;
                    }
                }
            }
            backupFile.close();

            // Relatório Final
            System.out.println("\n### Relatório Final ###");
            System.out.println("Total de bytes originais: " + totalBytesOriginais);
            System.out.println("Total de bytes compactados: " + totalBytesCompactados);
            float eficienciaTotal = 100 * (1 - (float) totalBytesCompactados / totalBytesOriginais);
            System.out.printf("Eficiência total da compactação: %.2f%%\n", eficienciaTotal);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // public static void main(String[] args) {
    // compacta();
    // descompacta((LocalDate.now()).toString());
    // }
}

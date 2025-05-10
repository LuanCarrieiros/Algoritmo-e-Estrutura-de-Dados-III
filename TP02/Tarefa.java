/**
 * A classe Tarefa representa uma tarefa com atributos como id, nome, data de criação,
 * data de conclusão, status e prioridade. A classe implementa a interface Registro,
 * permitindo a manipulação de dados em formato binário.
 * 
 * Atributos:
 * - id: Identificador único da tarefa.
 * - nome: Nome da tarefa.
 * - dataCriacao: Data de criação da tarefa.
 * - dataConclusao: Data de conclusão da tarefa.
 * - status: Status da tarefa (0 - Pendente, 1 - Em Progresso, 2 - Concluída).
 * - prioridade: Prioridade da tarefa (0 - Baixa, 1 - Média, 2 - Alta).
 * 
 * Construtores:
 * - Tarefa(): Construtor padrão que inicializa a tarefa com valores padrão.
 * - Tarefa(String nome, LocalDate dataCriacao, LocalDate dataConclusao, byte status, byte prioridade):
 *   Construtor que inicializa a tarefa com os valores fornecidos, exceto o id.
 * - Tarefa(int id, String nome, LocalDate dataCriacao, LocalDate dataConclusao, byte status, byte prioridade):
 *   Construtor que inicializa a tarefa com todos os valores fornecidos.
 * 
 * Métodos:
 * - setId(int id): Define o id da tarefa.
 * - getId(): Retorna o id da tarefa.
 * - setNome(String nome): Define o nome da tarefa.
 * - getNome(): Retorna o nome da tarefa.
 * - setDataCriacao(LocalDate dataCriacao): Define a data de criação da tarefa.
 * - getDataCriacao(): Retorna a data de criação da tarefa.
 * - setDataConclusao(LocalDate dataConclusao): Define a data de conclusão da tarefa.
 * - getDataConclusao(): Retorna a data de conclusão da tarefa.
 * - setStatus(byte status): Define o status da tarefa.
 * - getStatus(): Retorna o status da tarefa.
 * - setPrioridade(byte prioridade): Define a prioridade da tarefa.
 * - getPrioridade(): Retorna a prioridade da tarefa.
 * - toByteArray(): Converte a tarefa para um array de bytes.
 * - fromByteArray(byte[] b): Converte um array de bytes para uma tarefa.
 * - toString(): Retorna uma representação em string da tarefa.
 * - compareTo(Object p): Compara a tarefa com outra tarefa com base no id.
 */

 
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import aed3.Registro;
// as classes e métodos envolve um texto explicativo
public class Tarefa implements Registro {

    int id;
    String nome;
    LocalDate dataCriacao;
    LocalDate dataConclusao;
    byte status; // 0 - Pendente, 1 - Em Progresso, 2 - Concluída
    byte prioridade; // 0 - Baixa, 1 - Média, 2 - Alta
    int idCategoria;

    // ================== Construtores ==================
    public Tarefa() {
        this(-1, "", LocalDate.of(1970, 1, 1), LocalDate.of(1970, 1, 1), (byte)1, (byte)2, -1);
    }

    // Construtor que inicializa apenas id, nome e idCategoria
    public Tarefa(int id, String nome, int idCategoria) {
        this.id = id;
        this.nome = nome;
        this.idCategoria = idCategoria;
        this.dataCriacao = LocalDate.now(); // Define dataCriacao como a data atual
        this.dataConclusao = LocalDate.now(); // Ainda não concluída
        this.status = 0;                // Status padrão: Pendente
        this.prioridade = 0;            // Prioridade padrão: Baixa
    }

    public Tarefa (String nome, LocalDate dataCriacao, LocalDate dataConclusao, byte status, byte prioridade, int idCategoria) {
        this(-1, nome, dataCriacao, dataConclusao, status, prioridade, idCategoria);
        
    }

    public Tarefa(int id, String nome, LocalDate dataCriacao, LocalDate dataConclusao, byte status, byte prioridade, int idCategoria) {
        this.id = id;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
        this.status = status;
        this.prioridade = prioridade;
        this.idCategoria = idCategoria;
    }

    // ================== Getters e Setters ==================
    // descreva os métodos getters e setters e os métodos de manipulação de arquivos
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public byte getStatus() {
        return status;
    }

    public void setPrioridade(byte prioridade) {
        this.prioridade = prioridade;
    }

    public byte getPrioridade() {
        return prioridade;
    }

    public void setIdCategoria(int id)
    {
        this.idCategoria = id;
    }

    public int getIdCategoria()
    {
        return idCategoria;
    }

    // ================== Métodos ==================

    public byte[] toByteArray() throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.writeInt((int)this.dataCriacao.toEpochDay());
        dos.writeInt((int)this.dataConclusao.toEpochDay());
        dos.writeByte(this.status);
        dos.writeByte(this.prioridade);
        dos.writeInt(this.idCategoria);

        return baos.toByteArray();
    }

    
    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.dataCriacao = LocalDate.ofEpochDay(dis.readInt());
        this.dataConclusao = LocalDate.ofEpochDay(dis.readInt());
        this.status = dis.readByte();
        this.prioridade = dis.readByte();
        this.idCategoria = dis.readInt();
    }

    
    public String toString() {
        return "\nID:..............: " + this.id + 
             "\nNome:............: " + this.nome + 
             "\nData de Criação..: " + this.dataCriacao + 
             "\nData de Conclusão: " + this.dataConclusao + 
             "\nStatus...........: " + this.status + 
             "\nPrioridade.......: " + this.prioridade +
             "\nID Categoria.....: " + this.idCategoria;
    }

    
    public int compareTo(Object p) {
        return this.id - ((Tarefa)p).id;
    }

}

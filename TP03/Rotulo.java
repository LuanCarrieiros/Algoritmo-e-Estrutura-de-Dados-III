import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import aed3.Registro;

public class Rotulo implements Registro {

    // ================== Atributos ==================
    private int id;
    private String rotulo;
    private ArrayList<Integer> idsTarefas; // IDs das tarefas associadas

    // ================== Construtores ==================
    public Rotulo() {
        this.id = -1;
        this.rotulo = "";
        this.idsTarefas = new ArrayList<>();
    }

    public Rotulo(int id, String rotulo) {
        this.id = id;
        this.rotulo = rotulo;
        this.idsTarefas = new ArrayList<>();
    }

    public Rotulo(String rotulo) {
        this.id = -1;
        this.rotulo = rotulo;
        this.idsTarefas = new ArrayList<>();
    }

    public Rotulo(int id) {
        this.id = id;
        this.rotulo = "";
        this.idsTarefas = new ArrayList<>();
    }

    // ================== Getters e Setters ==================
    public int getId() {
        return this.id;
    }

    public String getRotulo() {
        return this.rotulo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public ArrayList<Integer> getIdsTarefas() {
        return idsTarefas;
    }

    public void setIdsTarefas(ArrayList<Integer> idsTarefas) {
        this.idsTarefas = idsTarefas;
    }

    // ================== Métodos Auxiliares ==================
    public void adicionarTarefa(int idTarefa) {
        if (!idsTarefas.contains(idTarefa)) {
            idsTarefas.add(idTarefa);
        }
    }

    public void removerTarefa(int idTarefa) {
        idsTarefas.remove(Integer.valueOf(idTarefa));
    }

    // ================== Métodos ==================
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(id);
        dos.writeUTF(rotulo);

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        id = dis.readInt();
        rotulo = dis.readUTF();
    }

    @Override
    public String toString() {
        return "\nID:..............: " + this.id + 
               "\nRótulo:..........: " + this.rotulo + 
               "\nIDs Tarefas......: " + idsTarefas;
    }

    @Override
    public int compareTo(Object o) {
        return this.id - ((Rotulo) o).id;
    }
}

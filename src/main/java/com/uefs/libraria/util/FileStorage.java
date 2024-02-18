package com.uefs.libraria.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private static final String fileType = ".dat";
    private static final String mainDir = "src" + File.separator +
            "test" + File.separator + // transformar em comentário para versão final
            "resources" + File.separator + "data";
    private final String fileName;
    private final String directoryName;

    public FileStorage(String fileName, String directoryName) {
        this.fileName = fileName;
        this.directoryName = directoryName;
    }

    public static String getMainDir(){
        return mainDir;
    }

    public static String getFileType(){
        return fileType;
    }

    public <T> void save(List<T> lista) {
        File pasta = new File(mainDir + File.separator + directoryName);
        pasta.mkdirs();

        File arquivo = new File(pasta.getPath() + File.separator + fileName + fileType);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> read() {
        File pasta = new File(mainDir + File.separator + directoryName);
        File arquivo = new File(pasta.getPath() + File.separator + fileName + fileType);

        List<T> lista = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            lista = (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException ignored) {

        }

        return lista;
    }
}

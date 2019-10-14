package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FileSystemItemDao implements ItemDao {

    private final String MAIN_DIR = System.getProperty("user.dir");
    private final String SEP = System.getProperty("file.separator");
    private final String FILE_PATH = MAIN_DIR + SEP + "files" + SEP + "items_info.txt";


    public void writeItem(String jsonItem) {
        checkDirectory();

        try (BufferedWriter br = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            br.write(jsonItem + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkDirectory() {
        String filesDir = MAIN_DIR + SEP + "files";
        File file = new File(filesDir);
        if (!file.exists()) {
            file.mkdir();
        }
    }
}

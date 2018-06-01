package http.protocol;

import java.io.*;

public class Folder {
    private File folder;

    Folder(String folderName) {
        this.folder = new File(folderName);
    }


    public boolean isExist(String fileName) {
        File[] listOfFiles = folder.listFiles();

        for(int i=0; i<listOfFiles.length; i++) {
            if(listOfFiles[i].getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }
}

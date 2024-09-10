// This class will create the folder of the specific user when he/she create the account

import javax.sound.midi.Soundbank;
import java.io.File;
import java.util.Scanner;

public class CreateFolder {
    CreateFolder() {

        String username = NewUser.userName.getText();

        Scanner sc = new Scanner(System.in);
        String path = "E:/Project Images";

        path = path + "/" + username;

        File f1 = new File(path);

        f1.mkdir();
    }
}

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
/**
 * @author Gazi Kağan Soysal - 2210356050
 * @version 1.0
 */
public class Main {
    static String outputTxt;
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String inputTxt = args[0];
        outputTxt = args[1];
        openFile(outputTxt);
        new Execute(inputTxt);
    }

    private static void openFile(String fileName){
        try {
            new BufferedWriter(new FileWriter(fileName, false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
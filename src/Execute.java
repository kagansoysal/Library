import java.lang.reflect.InvocationTargetException;

public class Execute {
    Execute(String inputTxt) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Input input = new Input(inputTxt);

        for (String line : input.inputTxt){
            String[] command = line.split("\t");

            if(!command[0].contains("add") && !command[0].contains("get")){
                new BookManagement(command);
            }else{
                new LibManagement(command);
            }
        }
    }
}
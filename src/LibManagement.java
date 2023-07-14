import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LibManagement extends Controller {

    LibManagement(String[] command) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = command[0];
        Method method = this.getClass().getMethod(methodName, String[].class);
        method.invoke(this, (Object) command);
    }

    public static void addBook(String[] command){
        if (canAdded(command, "P", "H")){
            Book book = command[1].equals("P") ? new Printed() : new Handwritten();
            book.id = systemCenter.books.size() + 1;
            systemCenter.books.put(book.id, book);
            Output.writeFile("Created new book: " + book.getClass().getName() + " [id: " + book.id + "]");
        }
    }

    public static void addMember(String[] command){
        if (canAdded(command, "S", "A")){
            Member member = command[1].equals("S") ? new Student() : new Academic();
            member.id = systemCenter.members.size() + 1;
            systemCenter.members.put(member.id, member);
            Output.writeFile("Created new member: " + member.getClass().getName() + " [id: " + member.id + "]");
        }
    }

    public static void getTheHistory(String[] command){
        Output.writeFile("History of library:\n");
        Output.writeMember(systemCenter.members, Student.class);
        Output.writeMember(systemCenter.members, Academic.class);
        Output.writeBook(systemCenter.books, Printed.class);
        Output.writeBook(systemCenter.books, Handwritten.class);
        Output.writeBorrowed(systemCenter.books);
        Output.writeRead(systemCenter.books);
    }
}
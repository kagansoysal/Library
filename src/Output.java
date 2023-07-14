import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

public class Output {
    public static void writeFile(String text) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(Main.outputTxt, true));
            writer.write(text + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeMember(LinkedHashMap<Integer, ?> datas, Class<? extends Member> memberType){
        Output.writeFile("Number of " + memberType.getName().toLowerCase() + "s: " + countObject(datas, memberType));
        datas.values().stream().filter(memberType::isInstance)
                .forEach(member -> writeFile(memberType.getSimpleName() + " [id: " + ((Member)member).id + "]"));
        Output.writeFile("");
    }

    public static void writeBook(LinkedHashMap<Integer, ?> datas, Class<? extends Book> bookType){
        Output.writeFile("Number of " + bookType.getName().toLowerCase() + " books: " + countObject(datas, bookType));
        datas.values().stream().filter(bookType::isInstance)
                .forEach(book -> writeFile(bookType.getSimpleName() + " [id: " + ((Book)book).id + "]"));
        Output.writeFile("");
    }

    public static void writeBorrowed(LinkedHashMap<Integer, Book> datas){
        long count = datas.values().stream().filter(Printed.class::isInstance).filter(book -> ((Printed) book).borrowed).count();
        writeFile("Number of borrowed books: " + count);
        datas.values().stream().filter(Printed.class::isInstance).filter(book -> ((Printed) book).borrowed)
                .forEach(book -> writeFile("The book [" + book.id + "] was borrowed by member [" + book.memberId + "] at " + book.currentDate));
        Output.writeFile("");
    }

    public static void writeRead(LinkedHashMap<Integer, Book> datas){
        long count = datas.values().stream().filter(book -> book.read).count();
        writeFile("Number of books read in library: " + count);
        datas.values().stream().filter(book -> book.read)
                .forEach(book -> writeFile("The book [" + book.id + "] was read in library by member [" + book.memberId + "] at " + book.currentDate));
        Output.writeFile("");
    }

    public static <T> long countObject(LinkedHashMap<Integer, ?> objects, T obj) {
        return objects.values().stream().filter(ob -> ob.getClass().equals(obj)).count();
    }
}
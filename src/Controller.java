import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controller {
    static SystemCenter systemCenter = new SystemCenter();

    public static boolean canAdded(String[] command, String type1, String type2){
        if (command(command, 2) && (!command[1].equals(type1) || !command[1].equals(type2))){
            return true;
        } else {
            Output.writeFile("ERROR: Erroneous command!");
            return false;
        }
    }

    public static boolean canBorrowed(Printed book, Member member){
        if(bookExist(book) && memberExist(member) && !isBorrowed(book)){
            if(rightExist(member)){
                return true;
            }else{
                Output.writeFile("You have exceeded the borrowing limit!");
                return false;
            }
        }else {
            Output.writeFile("You cannot borrow this book!");
            return false;
        }
    }

    public static boolean canReturned(Book book, Member member){
        if(bookExist(book) && memberExist(member) && (isBorrowed(book) || isRead(book))){
            return true;
        }else{
            Output.writeFile("You cannot return this book!");
            return false;
        }
    }

    public static boolean canExtended(Printed book, Member member, LocalDate currentDate){
        if(bookExist(book) && memberExist(member) && isBorrowed(book) && !lateDay(book,currentDate) && book.extendRight){
            return true;
        }else {
            Output.writeFile("You cannot extend the deadline!");
            return false;
        }
    }

    public static boolean canRead(Book book, Member member){
        if(bookExist(book) && memberExist(member) && !isBorrowed(book) && !isRead(book)){
            if(!readable(book, member)){
                Output.writeFile("Students can not read handwritten books!");
                return false;
            }else {
                return true;
            }
        }else{
            Output.writeFile("You can not read this book!");
            return false;
        }
    }



    public static boolean commandError(String[] command){
        if (command(command, 4) && isInteger(command[1], "BookId") && isInteger(command[2], "MemberId") && isDate(command[3])){
            return false;
        }else {
            Output.writeFile("ERROR: Erroneous command!");
            return true;
        }
    }

    public static boolean lateDay(Printed book, LocalDate date){
        return date.isAfter(book.returnDate);
    }

    public static LocalDate convertDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    private static boolean command(String[] commands, int lenght){
        return commands.length == lenght;
    }

    private static boolean isInteger(String value, String variable) {
        if (value.matches("\\d+")) {
            return true;
        }else {
            Output.writeFile("ERROR: " + variable + " must be integer!");
            return false;
        }
    }

    private static boolean isDate(String value){
        try {
            convertDate(value);
            return true;
        }catch (Exception e){
            Output.writeFile("ERROR: Time format is not correct!");
            return false;
        }
    }

    private static boolean isBorrowed(Book book){
        if (book.getClass().equals(Printed.class)){
            return ((Printed)systemCenter.books.get(book.id)).borrowed;
        }else{
            return false;
        }
    }

    private static boolean isRead(Book book){
        return book.read;
    }

    private static boolean readable(Book book, Member member){
        return !(member.getClass().getName().equals("Student") && book.getClass().getName().equals("Handwritten"));
    }

    private static boolean bookExist(Book book){
        return systemCenter.books.containsKey(book.id);
    }

    private static boolean memberExist(Member member){
        return systemCenter.members.containsKey(member.id);
    }

    private static boolean rightExist(Member member){
        return member.bookRight > 0;
    }
}
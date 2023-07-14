import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookManagement extends Controller{

    BookManagement(String[] command) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (!commandError(command)){
            String methodName = command[0];
            int bookId = Integer.parseInt(command[1]);
            int memberId = Integer.parseInt(command[2]);
            LocalDate date = convertDate(command[3]);

            Book book = (systemCenter.books.get(bookId));
            Member member = systemCenter.members.get(memberId);

            Method method = this.getClass().getMethod(methodName, Book.class, Member.class, LocalDate.class);
            method.invoke(this, book, member, date);
        }
    }

    public static void borrowBook(Book book, Member member, LocalDate currentDate){
        Printed printedBook = (Printed) book;
        if (canBorrowed(printedBook, member)){
            printedBook.borrowed = true;
            printedBook.currentDate = currentDate;
            printedBook.memberId = member.id;
            printedBook.returnDate = book.currentDate.plusWeeks(member.extensionTime);
            member.bookRight -= 1;
            Output.writeFile("The book [" + printedBook.id + "] was borrowed by member [" + member.id + "] at " + printedBook.currentDate);
        }
    }

    public static void returnBook(Book book, Member member, LocalDate returnDate){
        long delayedDay = 0;
        if(canReturned(book, member)){
            if (book.getClass().equals(Printed.class)){
                Printed printedBook = (Printed) book;

                if(printedBook.borrowed && lateDay(printedBook, returnDate)){
                    delayedDay = ChronoUnit.DAYS.between(printedBook.returnDate, returnDate);
                    delayedDay = delayedDay < 0 ? 0 : delayedDay;
                }
                printedBook.borrowed= false;
                printedBook.extendRight = true;
                member.bookRight += 1;
            }
            book.read = false;
            book.memberId = 0;
            Output.writeFile("The book ["+ book.id +"] was returned by member ["+ member.id
                    + "] at " + returnDate + " Fee: " + delayedDay);
        }
    }

    public static void extendBook(Book book, Member member, LocalDate currentDate){
        Printed printedBook = (Printed) book;
        if(canExtended(printedBook, member, currentDate)){
            printedBook.returnDate = printedBook.returnDate.plusWeeks(member.extensionTime);
            printedBook.extendRight = false;
            Output.writeFile("The deadline of book [" + printedBook.id + "] was extended by member [" + member.id + "] at " + currentDate);
            Output.writeFile("New deadline of book [" + printedBook.id + "] is " + printedBook.returnDate);
        }
    }

    public static void readInLibrary(Book book, Member member, LocalDate currentDate){
        if(canRead(book, member)){
            book.read = true;
            book.currentDate = currentDate;
            book.memberId = member.id;
            Output.writeFile("The book [" + book.id + "] was read in library by member [" + member.id + "] at " + book.currentDate);
        }
    }
}
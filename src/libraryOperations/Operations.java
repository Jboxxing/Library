package libraryOperations;

public interface Operations 
{
	// The way this simple library application is designed, only one library can be viewed/modified at a time and by
	// only one client/staff member at a time.
	
	// Operations are valid for both clients and staff members if they begin with an ID parameter.
	// Operations are only valid for staff members if they begin with a staffID parameter.
	// Operations are only valid for clients if they begin with a clientID parameter.
	
	public abstract String searchBookID(long ID, long bookID);
	
	public abstract String searchBookName(long ID, String bookName);
	
	public abstract String searchBookAuthor(long ID, String author);
	
	public abstract String checkout(long clientID, long bookID, String bookName, String author, int quantity);
	
	public abstract String returnBook(long clientID, long bookID, String bookName, String author, int quantity);
	
	public abstract String addBook(long staffID, long bookID, String bookName, String author, int quantity);
	
	public abstract String removeBook(long staffID, long bookID, int quantity);
	
	public abstract String banClient(long staffID, long clientID);
	
}

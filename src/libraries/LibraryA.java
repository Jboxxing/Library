package libraries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

// To be implemented later: Staff who leave or are let go should be removed from the ArrayList of current staff
// members. Keep track of existing libraries, staff members, and clients even when the program is closed (the only way
// to truly remove these records would be to delete the files storing this information). Create Staff and Client
// classes instead of going solely by ID inputs. Create a book class to store the details of a book.

// The owner ID is automatically registered to the program.
// Client and staff member IDs taken as login input must match a pre-registered ID in order to view or modify the
// library (use its operations).
// If an entered client/staff member ID does not match with a pre-existing client/staff member ID respectively, prompt
// the user to register as a new client/staff member.

// Libraries of the "A" chain must be named as "LibraryA1", "LibraryA2", "LibraryA3", etc. No two libraries can have
// the same name. Successfully creating a library will automatically add the next available number to the base name
// "LibraryA".

public class LibraryA implements libraryOperations.Operations
{
	// Libraries are stored in an ArrayList.
	// Library names are stored in a String.
	// Books in this library will be stored in an ArrayList containing Book instances, which hold information about the
	// book's name, author, and quantity.
	// The IDs of staff members of a library is stored in an ArrayList. The first index points to the owner, whose
	// ID will always be "STAFF1" for any given LibraryA instance.
	// The IDs of clients of a library is stored in an ArrayList.
	private static ArrayList<LibraryA> libraries = new ArrayList<LibraryA>(0);
	private String libraryName = "No library";
	// TODO Create the Book class.
	// private ArrayList<Book> books = new ArrayList<Book>();
	private ArrayList<String> staff;
	private ArrayList<String> clients;
	private static int count = 0;
	private int countStaff = 0;
	private int countClient = 0;
	
	public LibraryA()
	{
		count++;
		libraries.add(this);
		libraryName = "LibraryA" + count;
		System.out.println(libraries.get(count - 1).libraryName + " has been created.");
		staff = new ArrayList<String>(0);
		clients = new ArrayList<String>(0);
	}
	
	public String getLibraryName()
	{
		return libraryName;
	}
	
	/*
	public void setLibraryName(String libName)
	{
		libraryName = libName;
	}
	*/
	
	// Every time a new staff is added, the staff ID becomes "STAFF"n, where n is the current staffCount value
	// incremented by one.
	public void addStaff()
	{
		String newStaff = "STAFF" + ++countStaff;
		staff.add(newStaff);
		System.out.println(newStaff + " has been created at " + this.libraryName + ".");
	}
	
	// Every time a new client is added, the client ID becomes "CLIENT"n, where n is the current clientCount value
	// incremented by one.
	public void addClient()
	{
		String newClient = "CLIENT" + ++countClient;
		clients.add(newClient);
		System.out.println(newClient + " has been created at " + this.libraryName + ".");
	}
	
	public boolean matchStaffID(String staffID)
	{
		for (String staffMember : staff)
		{
			if (staffMember.contentEquals(staffID))
				return true;
		}
		System.out.println("No matching staff ID found.");
		return false;
	}
	
	public boolean matchClientID(String clientID)
	{
		for (String client : clients)
		{
			if (client.contentEquals(clientID))
				return true;
		}
		System.out.println("No matching client ID found.");
		return false;
	}
	
	public static void listExistingLibraries()
	{
		for (LibraryA lib : libraries)
		{
			System.out.println(lib.libraryName);
		}
	}
	
	public static LibraryA matchLibraryName(String libName)
	{
		for (LibraryA library : libraries)
		{
			if (library.libraryName.contentEquals(libName))
				return library;
		}
		System.out.println("No match was found with that library name.");
		return null;
	}
	
	public static void main(String[] args)
	{
		Scanner keyboard = null;
		
		// Welcomes the user to LibraryA libraries and creates a new library (LibraryA1) to start with.
		System.out.println("Welcome to the chain of LibraryA libraries!");
		System.out.println();
		LibraryA LibraryA1 = new LibraryA();
		// Creates a new staff member (default/owner) at LibraryA1 to start with.
		LibraryA1.addStaff();
		System.out.println();
		
		// Lists the LibraryA libraries that currently exist and asks the user which one they want to view or modify.
		// The user must enter the name of the library (Ex. "LibraryA1").
		// If a matching library name is found, the corresponding LibraryA instance is returned.
		// Otherwise, no matching library name returns null where the user is notified that there is no match, reminded
		// of the libraries that currently exist, and prompted for another input.
		LibraryA matchedLibrary = null;
		boolean mainMenu = true;
		
		while (mainMenu)
		{
			keyboard = new Scanner(System.in);
			System.out.println("Currently existing libraries:");
			listExistingLibraries();
			System.out.println();
			System.out.println("Which library would you like to view/modify?");
			String enterLibrary = keyboard.next();
			System.out.println("Entered library: " + enterLibrary);
			matchedLibrary = matchLibraryName(enterLibrary);
			if (matchedLibrary == null)
				continue;
			mainMenu = false;

			// The user must decide whether to log in as a staff member or a client of the matched library.
			// If the user decides to log in as a staff member, they must enter a staff ID that matches with one in the
			// staff ArrayList.
			// If the user decides to log in as a client, they must enter a client ID that matches with one in the client
			// ArrayList.
			// If no matching ID can be found, prompt the user to register as a new client/staff member (necessary for
			// continuing use of this program).
			boolean validOption = false;
			int option = 0;
			while (!mainMenu && !validOption)
			{
				try
				{
					System.out.println("Enter: (1) to log in as a staff member, (2) to log in as a client.");
					option = keyboard.nextInt();

					if (option == 1)
					{
						validOption = true;

						System.out.print("Enter your staff ID: ");
						String staffID = keyboard.next();
						System.out.println();
						// If the staff ID entered as input matches a currently existing ID, proceed to the next prompt.
						// If there is no match found, ask the user if they would like to register as a new staff member.
						// The staff ID assigned to a new staff member at this point may not be the same as the one they
						// entered as input. It will depend on the next available countStaff value (for simplicity's sake).
						// If the user does not want to register as a new staff member, return to the main menu.
						if(!matchedLibrary.matchStaffID(staffID))
						{
							System.out.println("You are not currently registered as a staff member of " + matchedLibrary.libraryName + ".");
							String registerOption = "";
							while (true)
							{
								System.out.println("Would you like to register as a new staff member? (Y/N)");
								registerOption = keyboard.next();
								if (registerOption.contentEquals("Y"))
								{
									matchedLibrary.addStaff();
									break;
								}
								else if (registerOption.contentEquals("N"))
								{
									// Returns the program execution to the while (mainMenu) loop.
									mainMenu = true;
									break;
								}
								else
								{
									System.out.println("Invalid input.");
									continue;
								}
							}
						}

						// Along with the valid operations of a staff member to an existing library defined in the Operations
						// interface, staff members can create a new LibraryA, where they automatically become its owner
						// and its first staff member.
						boolean validStaffOption = false;
						int staffOption = 0;
						while (!mainMenu && !validStaffOption)
						{
							System.out.println("Enter: (1) to create a new LibraryA, (2) to view or modify " + matchedLibrary.libraryName + ".");
							staffOption = keyboard.nextInt();
							if (staffOption == 1)
							{
								validStaffOption = true;
								LibraryA lib = new LibraryA();
								lib.addStaff();
							}
							else if (staffOption == 2)
							{
								validStaffOption = true;
								boolean validMod = false;
								System.out.println("Enter: (1) to search for a book, (2) to add or remove a book, or (3) to ban a client.");
								int staffMod = keyboard.nextInt();
								/*
								while (!validMod)
								{
									
								}
								*/
							}
							else
							{
								System.out.println("Invalid option, please enter (1) or (2).");
							}
						}
					}
					else if (option == 2)
					{
						validOption = true;
					}
					else
					{
						System.out.println("Invalid option, please enter (1) or (2).");
					}
				}
				catch (InputMismatchException e)
				{
					System.out.println("Data type of input does not match what is expected (an integer).");
					System.out.println();
					mainMenu = true;
				}
			}
		}
		if (keyboard != null)
			keyboard.close();
	}

	@Override
	public String searchBookID(long ID, long bookID)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String searchBookName(long ID, String bookName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String searchBookAuthor(long ID, String author)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkout(long clientID, long bookID, String bookName, String author, int quantity)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String returnBook(long clientID, long bookID, String bookName, String author, int quantity)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBook(long staffID, long bookID, String bookName, String author, int quantity)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeBook(long staffID, long bookID, int quantity)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String banClient(long staffID, long clientID)
	{
		// TODO Auto-generated method stub
		return null;
	}
}

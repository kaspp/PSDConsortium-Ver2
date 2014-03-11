package consortium.psd.Timetable;

import java.util.Scanner;

import consortium.psd.Controller.TimetableController;
import consortium.psd.Database.Database;
import consortium.psd.Object.User;

public class Main {

	static TimetableController tc = new TimetableController();
	static Scanner sc = new Scanner(System.in);
	private static User u;
	private static boolean exit = false;
	private static Database db = new Database();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		exit = false;
		if (doLogin()) {

			while (!exit) {
				showMenu(u.getType());
				System.out
						.println("Please choose the number of what you want to do?");
				String choice = sc.nextLine();

				if (isInteger(choice)) {
					int c = Integer.parseInt(choice);
					doMenu(u.getType(), c);

				} else {
					System.out
							.println("Wrong user input. Please only type in number");
				}
			}
		}
	}

	public static void doMenu(String type, int c) {
		if (type.equals("admin")) {

			switch (c) {
			case 1:
				boolean flag = true;
				boolean notint = true;
				
				while (flag) {
					tc.viewTt();
					System.out
							.println("Which timetable do you want to view. Key 0 to exit");
					String course = sc.nextLine();
					if (isInteger(course)) {
						if (tc.contain(Integer.parseInt(course)) && (Integer.parseInt(course) != 0)) {
		
								tc.viewTt(Integer.parseInt(course));
							
						} else if (Integer.parseInt(course) == 0) {
							flag = false;
						} else {
							System.out.println("Value out of bound");
						}
					}
				}
				
				
				break;

			case 2:
				tc.addTt();
				break;

			case 3:

				notint = true;
			
				while (notint) {
					tc.viewTt();
					System.out
							.println("Please enter the id of the timetable you want to edit.");
					String cap = sc.nextLine();
					if (isInteger(cap) && tc.contain(Integer.parseInt(cap))) {
						notint = false;
						tc.editTt(Integer.parseInt(cap));
					} else {
						System.out
								.println("The number you keyed in is not numeric figures, please try again!");
					}
				}
				break;

			case 4:

				notint = true;
				
				while (notint) {
					tc.viewTt();
					System.out
							.println("Please enter the id of the timetable slot you want to delete.");
					String cap = sc.nextLine();
					if (isInteger(cap) && tc.contain(Integer.parseInt(cap))) {
						notint = false;
						tc.removeTt(Integer.parseInt(cap));
						
					} else {
						System.out
								.println("The number you keyed in is not numeric figures, please try again!");
					}
				}
				break;
				
			case 5:
				tc.checkClash();
				break;

			case 0:
				exit = true;
				break;

			default:
				System.out
						.println("This is not a correct choice, please enter another choice.");
				showMenu(type);
				break;
			}

		} else if (type.equals("lecturer")) {
			switch (c) {
			case 1:
				boolean flag = true;
				while (flag) {
					tc.viewTt();
					System.out
							.println("Which timetable do you want to view. Key 0 to exit");
					String course = sc.nextLine();
					if (isInteger(course)) {
						if (tc.contain(Integer.parseInt(course)) && (Integer.parseInt(course) != 0)) {
		
								tc.viewTt(Integer.parseInt(course));
							
						} else if (Integer.parseInt(course) == 0) {
							flag = false;
						} else {
							System.out.println("Value out of bound");
						}
					}
				}
				break;

			case 0:
				exit = true;
				break;

			default:
				System.out
						.println("This is not a correct choice, please enter another choice.");
				showMenu(type);
				break;
			}

		} else if (type.equals("tutor")) {
			switch (c) {

			case 1:
				boolean flag = true;
				while (flag) {
					tc.viewTt();
					System.out
							.println("Which timetable do you want to view. Key 0 to exit");
					String course = sc.nextLine();
					if (isInteger(course)) {
						if (tc.contain(Integer.parseInt(course)) && (Integer.parseInt(course) != 0)) {
		
								tc.viewTt(Integer.parseInt(course));
							
						} else if (Integer.parseInt(course) == 0) {
							flag = false;
						} else {
							System.out.println("Value out of bound");
						}
					}
				}
				break;

			case 0:
				exit = true;
				break;

			default:
				System.out
						.println("This is not a correct choice, please enter another choice.");
				showMenu(type);
				break;
			}

		} else if (type.equals("student")) {

			switch (c) {
			case 1:
				boolean flag = true;
				while (flag) {
					tc.viewTt();
					System.out
							.println("Which timetable do you want to view. Key 0 to exit");
					String course = sc.nextLine();
					if (isInteger(course)) {
						if (tc.contain(Integer.parseInt(course)) && (Integer.parseInt(course) != 0)) {
		
								tc.viewTt(Integer.parseInt(course));
							
						} else if (Integer.parseInt(course) == 0) {
							flag = false;
						} else {
							System.out.println("Value out of bound");
						}
					}
				}
				break;

			case 0:
				exit = true;
				break;

			default:
				System.out
						.println("This is not a correct choice, please enter another choice.");
				showMenu(type);
				break;
			}

		}
	}

	public static void showMenu(String type) {
		if (type.equals("admin")) {
			System.out.println("1.\t View Timetable");
			System.out.println("2.\t Add Timetable Slot");
			System.out.println("3.\t Edit Timetable Slot");
			System.out.println("4.\t Delete Timetable");
			System.out.println("5.\t Check for clashes");
			System.out.println("0.\t Logout");

		} else if (type.equals("lecturer")) {
			System.out.println("1.\t View Timetable");
			System.out.println("0.\t Logout");

		} else if (type.equals("tutor")) {
			System.out.println("1.\t View Timetable");
			System.out.println("0.\t Logout");

		} else if (type.equals("student")) {
			System.out.println("1.\t View Timetable");
			System.out.println("0.\t Logout");

		}
	}

	public static boolean doLogin() {

		int count = 3;

		while (count > 0) {
			System.out.println("Username");
			String user = sc.nextLine();
			System.out.println("Password");
			String pwd = sc.nextLine();

			if ((u = db.checkLogin(user, pwd)) != null) {
				System.out.println("Welcome " + u.getName());
				return true;

			} else {
				count--;
				if (count == 0) {
					System.out
							.println("You have already exceed your try. Please try again later");

				} else {
					System.out
							.println("You have the wrong username and password combination, please try again");
				}
			}
		}
		return false;
	}

	public static boolean isInteger(String str) {
		int size = str.length();

		for (int i = 0; i < size; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}

		return size > 0;
	}

}

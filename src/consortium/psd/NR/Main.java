package consortium.psd.NR;

import java.util.ArrayList;
import java.util.Scanner;

import consortium.psd.Controller.ClassController;
import consortium.psd.Controller.CourseController;
import consortium.psd.Controller.NRController;
import consortium.psd.Controller.StudentController;
import consortium.psd.Database.Database;
import consortium.psd.Object.Student;
import consortium.psd.Object.User;

public class Main {

	static NRController nr = new NRController();
	static CourseController coc = new CourseController();
	static ClassController cc = new ClassController();
	static StudentController sc = new StudentController();
	static Scanner scan = new Scanner(System.in);
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
				String choice = scan.nextLine();

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
					coc.viewCourse();
					System.out
							.println("Which course do you want to view. Key 0 to exit");
					String course = scan.nextLine();
					if (isInteger(course)) {
						if (coc.contains(Integer.parseInt(course))
								&& (Integer.parseInt(course) != 0)) {

							nr.printAllClass(Integer.parseInt(course));

						} else if (Integer.parseInt(course) == 0) {
							flag = false;
						} else {
							System.out.println("Value out of bound");
						}
					}
				}

				break;

			case 2:
				flag = true;
				notint = true;

				while (flag) {
					cc.viewClasses();
					;
					System.out
							.println("Which class do you want to view. Key 0 to exit");
					String classes = scan.nextLine();
					if (isInteger(classes)) {
						if (cc.contains(Integer.parseInt(classes))
								&& (Integer.parseInt(classes) != 0)) {

							nr.printClass(Integer.parseInt(classes));
							;

						} else if (Integer.parseInt(classes) == 0) {
							flag = false;
						} else {
							System.out.println("Value out of bound");
						}
					}
				}
				break;

			case 3:

				System.out
						.println("Please enter the name of the class you want to add.");
				String cap = scan.nextLine();
				cc.addClass(cap);

				int cid = cc.getID(cap);

				notint = true;
				ArrayList<Student> stuLis = new ArrayList<Student>();
				while (notint) {
					sc.printAllStudent();
					System.out
							.println("Please enter the id of the student you want to add to the class. Key 0 to stop adding");
					cap = scan.nextLine();
					if (isInteger(cap)
							&& sc.contains(Integer.parseInt(cap))
							&& (!stuLis.contains(sc.getStudent(Integer
									.parseInt(cap))))) {

						stuLis.add(sc.getStudent(Integer.parseInt(cap)));

					} else if (Integer.parseInt(cap) == 0) {
						if (stuLis.size() > 0) {
							notint = false;
							;
						} else {
							System.out
									.println("At least add one student into class");
						}

					} else {
						System.out
								.println("The number you keyed in is not numeric figures or the student is already in the list, please try again! ");
					}
				}

				notint = true;
				while (notint) {
					coc.viewCourse();
					System.out
							.println("Please enter the id of the course you want to tag the class.");
					cap = scan.nextLine();
					if (isInteger(cap) && coc.contains(Integer.parseInt(cap))) {
						nr.createNewClassList(cid, stuLis,
								Integer.parseInt(cap));
						notint = false;
					}
				}

				break;

			case 4:

				System.out.println("Enter the name of the Course");
				String name = scan.nextLine();
				System.out.println("Enter the course type");
				String location = scan.nextLine();
				coc.addCourse(name, location);
				break;

			case 5:

				
				notint = true;

				while (notint) {
					cc.viewClasses();
					System.out.println("Enter the class id");
					cap = scan.nextLine();
					if (isInteger(cap) && cc.contains(Integer.parseInt(cap))) {
						cid = Integer.parseInt(cap);
						notint = false;
						boolean notinta = true;
						while (notinta) {
							sc.printAllStudent();
							System.out.println("Enter the student you want to add to the class");
							cap = scan.nextLine();
							if (isInteger(cap) && sc.contains(Integer.parseInt(cap))) {
								nr.insertToClass(cid,  Integer.parseInt(cap));
								notinta = false;
							} else {
								System.out.println("Student not found");
							}
						}
					} else {
						System.err
								.println("Class not found, please try again!");
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

		} else if (type.equals("lecturer")) {
			switch (c) {
			case 1:
				boolean flag = true;

				while (flag) {
					coc.viewCourse();
					System.out
							.println("Which course do you want to view. Key 0 to exit");
					String course = scan.nextLine();
					if (isInteger(course)) {
						if (coc.contains(Integer.parseInt(course))
								&& (Integer.parseInt(course) != 0)) {

							nr.printAllClass(Integer.parseInt(course));

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
			System.out.println("1.\t View Class List");
			System.out.println("2.\t View Class in Courses");
			System.out.println("3.\t Add Classes");
			System.out.println("4.\t Add Courses");
			System.out.println("5.\t Add Student to class");
			System.out.println("0.\t Logout");


		} else if (type.equals("lecturer")) {
			System.out.println("1.\t View Class List");
			System.out.println("0.\t Logout");

		} else if (type.equals("tutor")) {
			System.out
					.println("You are not supposed to be here, key 0 to exit.");
			System.out.println("0.\t Logout");

		} else if (type.equals("student")) {
			System.out
					.println("You are not supposed to be here, key 0 to exit");
			System.out.println("0.\t Logout");

		}
	}

	public static boolean doLogin() {

		int count = 3;

		while (count > 0) {
			System.out.println("Username");
			String user = scan.nextLine();
			System.out.println("Password");
			String pwd = scan.nextLine();

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

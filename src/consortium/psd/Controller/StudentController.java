package consortium.psd.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import consortium.psd.Object.Student;

public class StudentController {
	ArrayList<Student> s = new ArrayList<Student>();
	
	public StudentController() {
		initData();
	}
	
	public ArrayList<Student> toArray() {
		return s;
	}
	
	public void printAllStudent() {
		for (Student st : s) {
			System.out.println(st.getUser_id() + ":\t " + st.getFullname());
		}
	}
	
	public boolean contains(int id) {
		for (Student st: s) {
			if (st.getUser_id() == id) {
				return true;
			}
		}
		return false;
	}
	
	public Student getStudent(int id) {
		for (Student st : s) {
			if (st.getUser_id() == id) {
				return st;
			}
		}
		return null;
	}
	
	public boolean checkStu(Student stu , Student st) {
		return stu.getUser_id() == st.getUser_id();
	}
	
	public boolean checkStu(Student stu, int id) {
		for (Student k : s) {
			if (k.getUser_id() == id) {
				return true;
			}
		}
		return false;
	}
	
	public void initData() {
		BufferedReader br = null;
		try {

			/*
			 * Reading all the classes
			 */
			String sCurrentLine;
			br = new BufferedReader(
					new FileReader(
							"/Users/Derrick/Documents/Programming/Java/CourseAllocation/Database/user.csv"));
			while ((sCurrentLine = br.readLine()) != null) {
				String[] temp = sCurrentLine.split(",");
				if (isInteger(temp[0]) && temp[5].equals("student")) {
					
					s.add(new Student(Integer.parseInt(temp[0]), temp[3], temp[4]));
				}

			}

		} catch (IOException e) {
			System.err
					.println("Unable to establish connection with the database. Please exit the the system and try again later.");
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				System.err.println("Unable to do process");
			}
		}
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

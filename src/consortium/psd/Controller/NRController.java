package consortium.psd.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import consortium.psd.Object.Classes;
import consortium.psd.Object.Student;

public class NRController {

	StudentController sc = new StudentController();
	ClassController cc = new ClassController();
	CourseController coc = new CourseController();
	ArrayList<String> classkeys = new ArrayList<String>();
	ArrayList<String> courseKeys = new ArrayList<String>();

	// Map the class name to the list.
	Map<String, ArrayList<Student>> classlink = new LinkedHashMap<String, ArrayList<Student>>();
	// Map the course to the class list.
	Map<String, ArrayList<String>> courseClass = new LinkedHashMap<String, ArrayList<String>>();

	public NRController() {
		initData();
	}

	public void insertToClass(int cid, int sid) {
		ArrayList<Student> temp = classlink.get(cc.getClas(cid));
		classlink.remove(cc.getClas(cid));
		temp.add(sc.getStudent(sid));
		classlink.put(cc.getClas(cid), temp);
	}

	public void createNewClassList(int clas_id, ArrayList<Student> st,
			int cour_id) {
		String cla = cc.getClas(clas_id);
		classkeys.add(cla);
		classlink.put(cla, st);
		courseKeys.add(coc.getCourse(cour_id).getName());
		// courseLink.put(coc.getCourse(cour_id).getName(), cla);

		ArrayList<String> classes = new ArrayList<String>();
		classes.add(cla);
		courseClass.put(coc.getCourse(cour_id).getName(), classes);
		save();
	}

	public ArrayList<Student> getClass(int clas_id) {
		ArrayList<Student> temp = classlink.get(cc.getClas(clas_id));

		return temp;

	}

	public void printClass(int clas_id) {
		ArrayList<Student> temp = getClass(clas_id);
		if (classkeys.contains(cc.getClas(clas_id))) {
			for (Student k : temp) {
				System.out.println(k.getUser_id() + " :\t" + k.getFullname());
			}
		} else {
			System.err.println("Class does not exist");
		}

	}

	public ArrayList<String> getAllClass(int cou_id) {
		ArrayList<String> temp = courseClass.get(coc.getCourse(cou_id)
				.getName());
		return temp;
	}

	public void printAllClass(int cou_id) {

		ArrayList<String> temp = getAllClass(cou_id);
		// System.out.println(temp.size() + "");
		
			for (String k : temp) {
				if (k == null || k.equals("") || k.equals("null")) {
					System.out.println("There are not class tag to this course");
				}
				else {
					System.out.println(k);
				}
			}
		

	}

	public boolean checkStudent(int stu_id, int cla_id) {

		String key = cc.getClas(cla_id);
		ArrayList<Student> stu = classlink.get(key);
		Student that = sc.getStudent(stu_id);

		for (Student temp : stu) {
			if (sc.checkStu(temp, that)) {
				return true;
			}
		}

		return false;
	}

	public void print() {
		System.out.println("Print");

		for (String co : courseKeys) {

			ArrayList<String> classes = courseClass.get(co);

			for (String c : classes) {
				ArrayList<Student> students = classlink.get(c);
				for (Student k : students) {

					System.out.println(c + "," + k.getFullname() + "," + co);

				}
			}

			System.out.println();

		}

	}

	public void save() {
		String url = "/Users/Derrick/Documents/Programming/Java/CourseAllocation/Database/norminalroll.csv";
		try {
			FileWriter writer = new FileWriter(url);

			for (String co : courseKeys) {
				ArrayList<String> classes = courseClass.get(co);

				for (String c : classes) {
					ArrayList<Student> students = classlink.get(c);

					for (Student k : students) {
						writer.append(cc.getID(c) + "," + k.getUser_id() + ","
								+ coc.courseID(co));
						writer.append("\n");
					}

				}

			}

			writer.flush();
			writer.close();

		} catch (IOException e) {
			System.err.println("Unable to save");
		}

	}

	private void initData() {
		BufferedReader br = null;
		try {

			/*
			 * Reading all the classes
			 */
			String sCurrentLine;
			br = new BufferedReader(
					new FileReader(
							"/Users/Derrick/Documents/Programming/Java/CourseAllocation/Database/norminalroll.csv"));
			while ((sCurrentLine = br.readLine()) != null) {
				String[] temp = sCurrentLine.split(",");
				// check if the numbers are integer.
				if (isInteger(temp[0]) && isInteger(temp[1])
						&& isInteger(temp[2])) {
					String clas = cc.getClas(Integer.parseInt(temp[0]));
					Student stu = sc.getStudent(Integer.parseInt(temp[1]));
					String course = coc.getCourse(Integer.parseInt(temp[2]))
							.getName();
					boolean studex = false;
					ArrayList<Student> stud;
					// check if student can be found in class
					if (classlink.containsKey(clas)) {
						stud = classlink.get(clas);
						for (Student s : stud) {
							if (sc.checkStu(s, stu)) {
								studex = true;
								break;
							}
						}
						if (!studex) {
							stud.add(stu);
							classlink.remove(clas);
							classlink.put(clas, stud);
						}
					} else {
						stud = new ArrayList<Student>();
						stud.add(stu);
						classkeys.add(clas);
						classlink.put(clas, stud);
					}

					if (courseClass.containsKey(course)) {
						ArrayList<String> tempclas = courseClass.get(course);
						if (!tempclas.contains(clas)) {
							tempclas.add(clas);
							courseClass.remove(course);
							courseClass.put(course, tempclas);

						}

					} else {
						ArrayList<String> tempclas = new ArrayList<String>();
						tempclas.add(clas);
						courseKeys.add(course);
						courseClass.put(course, tempclas);
					}
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

import java.util.ArrayList;

public class StudentList {


    ArrayList<Student> st = new ArrayList<Student>();

    public ArrayList<Student> addStudent(Student stu) {

        st.add(stu);
        return st;

    }

    public ArrayList<Student> getEditStudent(String fullname, int StudentId) {

        for (int i = 0; i < st.size(); i++) {
            
            if (st.get(i).getId().equals(StudentId)) {

                System.out.print("true");

                st.get(i).setName(fullname);
            }

        }

        return st;
    }

    public ArrayList<Student> getDeleteStudent(int StudentId) {

        for (int i = 0; i < st.size(); i++) {

            if (st.get(i).getId().equals(StudentId)) {

                st.remove(i);
                break;
            }

        }

        return st;
    }

    public void printStudentList() {
        int len = st.size();

        for (int i = 0; i < len; i++) {
            System.out.println("Student ID: " + st.get(i).getId() + " Fullname: " + st.get(i));

        }

    }
}

 

 

//class Student

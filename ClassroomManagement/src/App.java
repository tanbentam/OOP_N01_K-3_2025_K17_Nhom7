public class App {
    public static void main(String[] args) {
        // Tạo giáo viên
        Teacher teacher1 = new Teacher("T01", "Thầy Tráng", "CNTT");
        Teacher teacher2 = new Teacher("T02", "Cô Thu", "Lập Trình Hướng Đối Tượng");

        // Tạo học sinh
        Student student1 = new Student("S01", "Tân", 20);
        Student student2 = new Student("S02", "Hải",19);
        Student student3 = new Student("S03", "Lâm", 20);

        // Tạo lớp học 1
        Classroom classA = new Classroom("K17_CNTT1", teacher1);
        classA.addStudent(student1);
        classA.addStudent(student2);

        // Tạo lớp học 2
        Classroom classB = new Classroom("K17_CNTT2", teacher2);
        classB.addStudent(student3);

        // Quản lý lớp học
        ClassManager manager = new ClassManager();
        manager.addClassroom(classA);
        manager.addClassroom(classB);

        // Hiển thị toàn bộ lớp học
        System.out.println("DANH SÁCH TẤT CẢ CÁC LỚP:");
        manager.printAllClasses();
    }
}
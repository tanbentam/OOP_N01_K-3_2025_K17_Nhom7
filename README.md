YÊU CẦU SỐ 7:



![Screenshot 2025-06-19 161058](https://github.com/user-attachments/assets/9db6b1ee-24ed-4a26-9b37-204635cf664e)





PRACTICE 6:

Câu 1:
_ Lưu đồ thuật toán:


Câu 2:
 
 _Trình bày miêu tả phương thức:
 1. Phương thức hiển thị danh sách học sinh có mặt "displayPresentStudents"
Phương thức "displayPresentStudents" có nhiệm vụ lọc danh sách học sinh có mặt trong một lớp vào ngày được chọn. Mục tiêu chính của phương thức này là hỗ trợ giáo viên trong việc theo dõi mức độ chuyên cần của học sinh, giúp xác định số lượng học sinh tham dự buổi học và đưa ra các đánh giá phù hợp.

Quy trình hoạt động của phương thức bao gồm các bước sau:

Nhập ngày cần kiểm tra: Giáo viên chọn ngày để tra cứu dữ liệu điểm danh.

Truy xuất danh sách lớp học: Hệ thống tìm lớp học dựa trên tên lớp được nhập vào.

Duyệt danh sách học sinh: Kiểm tra trạng thái điểm danh của từng học sinh vào ngày được chọn.

Hiển thị danh sách học sinh có mặt: Danh sách này có thể được in ra màn hình hoặc xuất thành báo cáo để lưu trữ.

Ý nghĩa của phương thức "displayPresentStudents:"

Hỗ trợ giáo viên kiểm soát số lượng học sinh tham gia trong từng buổi học.

Giúp theo dõi mức độ chuyên cần của học sinh theo từng tuần hoặc tháng.

Cung cấp dữ liệu để phân tích học tập, phát hiện học sinh có xu hướng thường xuyên vắng mặt.

2. Phương thức hiển thị danh sách học sinh vắng mặt "displayAbsentStudents"
Phương thức "displayAbsentStudents" có nhiệm vụ lọc danh sách học sinh vắng mặt trong một lớp vào ngày được chọn. Đây là một phương thức quan trọng giúp giáo viên theo dõi tình trạng học sinh không tham gia lớp học, từ đó đề xuất các biện pháp quản lý phù hợp.

Quy trình hoạt động của phương thức bao gồm:

Nhập ngày cần kiểm tra: Giáo viên chọn ngày để kiểm tra danh sách vắng mặt.

Truy xuất danh sách lớp học: Hệ thống tìm lớp học cần kiểm tra dựa trên tên lớp được nhập.

Duyệt danh sách học sinh: Kiểm tra trạng thái điểm danh của từng học sinh vào ngày đó.

Hiển thị danh sách học sinh vắng mặt: Danh sách này có thể hiển thị trên màn hình, kèm theo thông tin về việc học sinh có phép hay không.

Ý nghĩa của phương thức "displayAbsentStudents:"

Giúp giáo viên xác định học sinh thường xuyên vắng mặt, điều này có thể ảnh hưởng đến kết quả học tập.

Dữ liệu có thể được sử dụng để cảnh báo phụ huynh, giúp nhà trường đưa ra biện pháp hỗ trợ học sinh.

Hỗ trợ việc xác định lý do vắng mặt, giúp giáo viên tránh bỏ sót những trường hợp đặc biệt như học sinh nghỉ vì bệnh hoặc có lý do cá nhân.

 _Code của phương thức:

 import java.time.LocalDate;
import java.util.List;

public class ClassManager {
    public void displayStudentsByAttendance(String className, LocalDate date, boolean showPresent) {
        Classroom cls = getClassroom(className);
        if (cls == null) {
            System.out.println("Không tìm thấy lớp.");
            return;
        }

        String status = showPresent ? "có mặt" : "vắng mặt";
        System.out.println("Danh sách học sinh " + status + " ngày " + date + " tại lớp " + className + ":");

        List<Student> students = cls.getStudentList();
        for (Student student : students) {
            AttendanceRecord record = student.getAttendanceRecord(date);
            if (record != null && record.isPresent() == showPresent) {
                System.out.print("- " + student.getName() + " (Mã HS: " + student.getId() + ")");
                if (!showPresent) {
                    System.out.println(" - Có phép: " + (record.hasPermission() ? " Có" : "Không"));
                } else {
                    System.out.println();
                }
            }
        }
    }
}

  _ Ảnh chạy chương trình:
   
   ![Screenshot 2025-06-19 164751](https://github.com/user-attachments/assets/be81b0fa-6c18-461d-98b0-62799714d51f)


   Câu 3:

   Cập nhật link Repo: https://github.com/tanbentam/OOP_N01_K-3_2025_K17_Nhom7





BÀI TẬP CŨ

Bài tập lớn cuối kỳ - Môn Lập trình hướng đối tượng

Nội dung:
Xây dựng ứng dụng Quản lý Lớp học

Yêu cầu:
1. Giao diện Java Spring Boot

	Ứng dụng được xây dựng với Java Spring Boot, Swing (1 chút)

2. Chức năng quản lý Student

Thêm mới, chỉnh sửa, xóa Student (học sinh).

Liệt kê danh sách học sinh, có thể lọc theo:

  		+ Tên học sinh

		+ Tuổi

		+ Lớp đang học

		+ Trạng thái (đang học, nghỉ,...)

3. Chức năng quản lý Classroom
		+ Thêm mới, chỉnh sửa, xóa Classroom (lớp học).

4. Chức năng gán Student cho Classroom


   		+ Cho phép gán học sinh vào lớp học.

		+ Mỗi học sinh có thể thuộc một hoặc nhiều lớp (tùy thiết kế).

6. Lưu trữ dữ liệu xuống file nhị phân

 	- Dữ liệu từ chương trình được lưu vào file nhị phân (db)

	- Sử dụng ObjectOutputStream và ObjectInputStream để ghi và đọc dữ liệu.

 	- Cần tạo các lớp hỗ trợ đọc/ghi dữ liệu cho:

			+ Student

			+ Classroom

			+ Teacher (người phụ trách lớp hoặc giảng dạy)

7. Quản lý dữ liệu trong bộ nhớ bằng Collection
Dữ liệu được lưu trữ và thao tác trong chương trình bằng các cấu trúc:

	+ ArrayList<Student>

	+ ArrayList<Classroom>

	+ Map<Classroom, List<Student>>

	+ Map<Teacher, List<Classroom>>, v.v.

8. Chức năng mở rộng (tùy chọn)
Quản lý giáo viên (Teacher): thêm/sửa/xóa, gán giáo viên cho lớp.

	- Tìm kiếm học sinh theo tên, mã số.

	- Thống kê số lượng học sinh trong từng lớp.

	- Xuất danh sách lớp học và học sinh ra file .csv.

CÔNG NGHỆ SỬ DỤNG:
		
  		+ Java 24 

		+ Spring Boot

		+ Netbeans + Maven / Gradle

		+ SQLite

		+ File I/O (Binary file, db hoặc dat)

		+ IDE: VSCode, Netbeans



NỘI DUNG 02:

Sơ đồ khối yêu cầu

1.1 UML Class Diagram


![so-do-3](https://github.com/user-attachments/assets/c3483835-7129-4f98-94ac-201cb5b935cd)


NỘI DUNG 03:

03 sơ đồ Behavioural Diagram của bài tập lớn

+ Sequence Diagram

+ Or Activity Diagram

1.2.

![so-do-2](https://github.com/user-attachments/assets/965bc408-21c5-4370-af80-9cb9658996d1)


![so-do-1](https://github.com/user-attachments/assets/bd534ab9-10e5-4e77-ba89-27bfc9b8d5b8)




-------------------------------------------------------------------------------------


BÀI TẬP CŨ:

Câu 1 (1 điểm). Tiêu đề của bài tập lớn cuối kỳ (nhóm sinh viên) lựa chọn là gì ? 

Tiêu đề: Hệ thống quản lý lớp học (Classroom Management System)

Câu 2: Phân tích cơ sở ít nhất 03 đối tượng cần thiết  
1. Student: Đại diện cho học sinh, lưu trữ thông tin gồm id, name, age.  
2. Teacher: Đại diện cho giáo viên, lưu trữ thông tin gồm id, name, subject.  
3. Classroom: Đại diện cho lớp học, chứa className, một Teacher, và danh sách các Student.

Câu 3: Cấu trúc thư mục đã làm

Câu 4: Có những Class : + ClassManager.java
                        + Classroom.java
                        + Student.java
                        + Teacher.java
                        + App.java
                        
Câu 5: Có những class kiểm định: + TestClassroom.java
                                 + TestStudent.java
                                 + TestTeacher.java

Link Git repo (Câu 3, 4, 5): https://github.com/NgHaiii/OOP_N01_K-3_2025_K17_Nhom7/

Link ReadMe (Câu 6): https://github.com/NgHaiii/OOP_N01_K-3_2025_K17_Nhom7/blob/main/README.md

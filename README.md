Bài tập lớn cuối kỳ - Môn Lập trình hướng đối tượng

Nội dung 01:
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

LINK:https://app.diagrams.net/#G1dLsckZxrE9f138wmn0Ynt7J9oL0G97-n#%7B%22pageId%22%3A%22EBrbo4JH7cscIni5zR0D%22%7D


NỘI DUNG 03:

03 sơ đồ Behavioural Diagram của bài tập lớn

+ Sequence Diagram
+ 
+ Or Activity Diagram

1.2.

LINK:https://app.diagrams.net/#G1zcwqCnjvxWIn2ti3BNmlht-KhtI7KNz_#%7B%22pageId%22%3A%22prtHgNgQTEPvFCAcTncT%22%7D
    :https://app.diagrams.net/#G1GsBr8kjxS7MX7XzJt1utjlzPJKnZy_E9#%7B%22pageId%22%3A%22C5RBs43oDa-KdzZeNtuy%22%7D






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

# OOP_N01_K-3_2025_K17_Nhom7 — Dự án Quản Lý Lớp Học

---

## 1. Giới thiệu

**Quản Lý Lớp Học** là một hệ thống web ứng dụng được phát triển bằng Java Spring Boot, phục vụ cho việc quản lý toàn diện các hoạt động của một lớp học hiện đại: quản lý giáo viên, học sinh, lớp học, điểm số, điểm danh, thời khóa biểu, phân quyền, báo cáo và nhiều chức năng mở rộng khác. Hệ thống hướng tới việc số hóa công tác quản lý, giúp giáo viên, học sinh và nhà trường thao tác, tra cứu, cập nhật thông tin một cách nhanh chóng, chính xác và bảo mật.

---

## 2. Thành viên nhóm 👥

| Họ tên              | MSSV      | Vai trò/Nhiệm vụ chính                 |
|---------------------|-----------|----------------------------------------|
| Nguyễn Văn Hải      | 23017261  | Quản lý giáo viên, lớp học, kiểm thử   |
| Chu Thành Tân       | 23010165  | Quản lý học sinh, điểm số              |
| Nguyễn Đức Lâm      | 22010267  | Quản lý điểm danh, giao diện           |
| GVHD: Nguyễn Lệ Thu |           | Hướng dẫn, phản biện                   |


---

## 3. Công nghệ sử dụng

- **Ngôn ngữ:** Java 17
- **Framework:** Spring Boot, Spring MVC, Spring Data JPA
- **Cơ sở dữ liệu:** MySQL (có thể chuyển sang H2 cho test nhanh)
- **Template Engine:** Thymeleaf
- **Quản lý phụ thuộc:** Maven
- **Kiểm thử:** JUnit 4/5
- **Quản lý phiên bản:** Git, GitHub
- **Frontend:** HTML5, CSS3, Bootstrap (trong static/), JavaScript (nếu có)
- **Khác:** Lombok (nếu dùng), Spring Security (nếu có phân quyền), các thư viện hỗ trợ báo cáo (nếu có)

---

## 4. Chức năng chính đã xây dựng

- Đăng nhập/đăng xuất, phân quyền người dùng (giáo viên, học sinh, admin)
- Quản lý giáo viên: thêm, sửa, xóa, tìm kiếm, phân công chủ nhiệm
- Quản lý học sinh: thêm, sửa, xóa, tìm kiếm, xem điểm, điểm danh
- Quản lý lớp học: thêm, sửa, xóa, xem danh sách học sinh, giáo viên chủ nhiệm
- Quản lý điểm số: nhập điểm, sửa điểm, xem điểm theo học sinh/lớp/môn
- Quản lý điểm danh: điểm danh theo buổi, xem báo cáo chuyên cần
- Quản lý thời khóa biểu: tạo, sửa, xem lịch học theo lớp/học sinh/giáo viên
- Báo cáo thống kê: tổng hợp điểm, chuyên cần, xuất file 
- Giao diện web thân thiện, responsive, đa nền tảng
- Kết nối, lưu trữ dữ liệu trên MySQL
- Kiểm thử tự động với JUnit

---

## 5. Cấu trúc thư mục Project

```
OOP_N01_K-3_2025_K17_Nhom7/
├── .gitattributes
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
├── tree.txt
├── images/
│   ├── so-do-1.jpg
│   ├── so-do-2.jpg
│   └── so-do-3.jpg
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── oopgroup7/
│   │   │           └── quanlylophoc/
│   │   │               ├── Main.java
│   │   │               ├── Controller/
│   │   │               │   ├── AttendanceController.java
│   │   │               │   ├── ClassroomController.java
│   │   │               │   ├── HomeController.java
│   │   │               │   ├── LoginController.java
│   │   │               │   ├── RegController.java
│   │   │               │   ├── ScoreController.java
│   │   │               │   ├── StudentController.java
│   │   │               │   ├── TeacherController.java
│   │   │               │   └── TimetableController.java
│   │   │               ├── Model/
│   │   │               │   ├── AttendanceRecord.java
│   │   │               │   ├── Classroom.java
│   │   │               │   ├── ClassroomStudent.java
│   │   │               │   ├── ClassroomStudentId.java
│   │   │               │   ├── Score.java
│   │   │               │   ├── Student.java
│   │   │               │   ├── Teacher.java
│   │   │               │   └── Timetable.java
│   │   │               ├── Repository/
│   │   │               │   ├── AttendanceRepository.java
│   │   │               │   ├── ClassroomRepository.java
│   │   │               │   ├── ClassroomStudentRepository.java
│   │   │               │   ├── ScoreRepository.java
│   │   │               │   ├── StudentRepository.java
│   │   │               │   ├── TeacherRepository.java
│   │   │               │   └── TimetableRepository.java
│   │   │               └── Service/
│   │   │                   ├── AttendanceService.java
│   │   │                   ├── ClassManager.java
│   │   │                   ├── ClassroomService.java
│   │   │                   ├── ScoreService.java
│   │   │                   ├── StudentService.java
│   │   │                   ├── TeacherService.java
│   │   │                   ├── TimetableService.java
│   │   │                   └── TimetableServiceImpl.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       │   └── (ảnh, CSS, JS)
│   │       └── templates/
│   │           ├── index.html
│   │           ├── admin/
│   │           │   └── index.html
│   │           ├── attendance/
│   │           │   ├── dates.html
│   │           │   ├── edit.html
│   │           │   ├── form.html
│   │           │   ├── index-student.html
│   │           │   ├── index.html
│   │           │   ├── report.html
│   │           │   └── result.html
│   │           ├── classroom/
│   │           │   ├── form.html
│   │           │   ├── index.html
│   │           │   └── student.html
│   │           ├── dashboard/
│   │           │   ├── student.html
│   │           │   └── teacher.html
│   │           ├── login/
│   │           │   └── index.html
│   │           ├── register/
│   │           │   ├── index.html
│   │           │   ├── student-form.html
│   │           │   └── teacher-form.html
│   │           ├── score/
│   │           │   ├── edit.html
│   │           │   ├── form.html
│   │           │   ├── index-student.html
│   │           │   ├── index.html
│   │           │   └── list.html
│   │           ├── student/
│   │           │   ├── form.html
│   │           │   └── index.html
│   │           ├── teacher/
│   │           │   ├── details.html
│   │           │   ├── form.html
│   │           │   └── index.html
│   │           └── timetable/
│   │               ├── class-timetable-student.html
│   │               ├── class-timetable.html
│   │               ├── edit-timetable.html
│   │               └── timetable-class-list.html
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── oopgroup7/
│       │           └── quanlylophoc/
│       │               ├── TestAttendanceRecord.java
│       │               ├── TestClassroom.java
│       │               ├── TestScore.java
│       │               ├── TestSequence.java
│       │               ├── TestStudent.java
│       │               ├── TestTeacher.java
│       │               └── TimetableValidationTest.java
│       └── resources/
│           └── application-test.properties
```

---

## 6. Mô tả chi tiết các đối tượng và chức năng

### 6.1. Giáo viên (Teacher)
- **Thuộc tính:** id, họ tên, bộ môn, danh sách lớp chủ nhiệm.
- **Chức năng:** Thêm/sửa/xóa/tìm kiếm giáo viên, phân công chủ nhiệm, xem danh sách lớp.

### 6.2. Học sinh (Student)
- **Thuộc tính:** id, họ tên, ngày sinh, lớp, điểm, điểm danh.
- **Chức năng:** Thêm/sửa/xóa/tìm kiếm học sinh, xem điểm, xem điểm danh.

### 6.3. Lớp học (Classroom)
- **Thuộc tính:** id, tên lớp, giáo viên chủ nhiệm, danh sách học sinh, thời khóa biểu.
- **Chức năng:** Thêm/sửa/xóa/tìm kiếm lớp, xem danh sách học sinh, phân công giáo viên chủ nhiệm.

### 6.4. Điểm số (Score)
- **Thuộc tính:** id, học sinh, môn học, điểm.
- **Chức năng:** Nhập điểm, sửa điểm, xóa điểm, sửa điểm, xem điểm theo học sinh/lớp/môn, thống kê điểm trung bình của từng môn.

### 6.5. Điểm danh (AttendanceRecord)
- **Thuộc tính:** id, học sinh, ngày, trạng thái có mặt/vắng.
- **Chức năng:** Điểm danh theo buổi, xem báo cáo chuyên cần, xuất báo cáo.

### 6.6. Thời khóa biểu (Timetable)
- **Thuộc tính:** id, lớp, danh sách tiết học, giáo viên, phòng học.
- **Chức năng:** Tạo/sửa/xóa thời khóa biểu, xem lịch học theo lớp/học sinh/giáo viên.

---

## 7. Hướng dẫn sử dụng

### 7.1. Cài đặt & chạy dự án

**Yêu cầu:**
- Java 17+
- Maven 3.6+
- MySQL (hoặc H2 cho test nhanh)

**Các bước:**
```sh
git clone <link-repo>
cd OOP_N01_K-3_2025_K17_Nhom7
mvn clean install
mvn spring-boot:run
```
Truy cập: [http://localhost:8080](http://localhost:8080)

### 7.2. Đăng nhập & phân quyền

- Đăng nhập với tài khoản giáo viên, học sinh hoặc admin.
- Giao diện và chức năng sẽ thay đổi theo quyền truy cập.

### 7.3. Quản lý dữ liệu

- **Giáo viên:** `/teachers`
- **Học sinh:** `/students`
- **Lớp học:** `/classrooms`
- **Điểm số:** `/scores`
- **Điểm danh:** `/attendance`
- **Thời khóa biểu:** `/timetable`

### 7.4. Quản lý giao diện

- Giao diện chia theo module: giáo viên, học sinh, lớp học, điểm, điểm danh, thời khóa biểu.
- Giao diện responsive, hỗ trợ trên máy tính và thiết bị di động.

---

## 8. API Endpoints 

- `GET /teachers` — Danh sách giáo viên
- `POST /teachers/add` — Thêm giáo viên
- `GET /students` — Danh sách học sinh
- `POST /students/add` — Thêm học sinh
- `GET /classrooms` — Danh sách lớp học
- `POST /classrooms/add` — Thêm lớp học
- `GET /scores` — Danh sách điểm số
- `POST /scores/add` — Thêm điểm số
- `GET /attendance` — Danh sách điểm danh
- `POST /attendance/add` — Thêm điểm danh
- `GET /timetable` — Xem thời khóa biểu

---

## 9. Cấu trúc Database (ví dụ)

- **teachers:** id, name, subject
- **students:** id, name, birthDate, classroom_id
- **classrooms:** id, name, teacher_id
- **scores:** id, student_id, subject, value
- **attendance_records:** id, student_id, date, present
- **timetables:** id, classroom_id, schedule

---

## 10. Kiểm thử

- Các file kiểm thử đơn vị (JUnit) nằm trong `src/test/java/com/oopgroup7/quanlylophoc/`.
- Chạy toàn bộ test bằng lệnh:
```sh
mvn test
```
- Đã kiểm thử các chức năng: thêm/sửa/xóa/tìm kiếm giáo viên, học sinh, lớp học, điểm số, điểm danh, thời khóa biểu.

---

## 11. Tính năng nâng cao (có thể phát triển thêm)

- Thống kê học lực, chuyên cần
- Xuất báo cáo PDF/Excel
- Phân quyền người dùng (giáo viên, admin)
- Gửi thông báo qua email
- Lịch sử chỉnh sửa dữ liệu
- Tích hợp API SMS/email cho thông báo

---


## 12. Bản quyền

Dự án thuộc sở hữu của nhóm 7 - OOP K17, năm học 2024-2025.  
Chỉ sử dụng cho mục đích học tập và nghiên cứu.

---
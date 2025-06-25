package com.oopgroup7.quanlylophoc.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dayOfWeek;    // Ví dụ: "Thứ Hai"
    private String period;       // Ví dụ: "Tiết 1"
    private String subject;      // Ví dụ: "Toán"
    private String teacherName;  // Ví dụ: "Nguyễn Văn A"

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    // Constructors
    public Schedule() {}

    public Schedule(String dayOfWeek, String period, String subject, String teacherName, Classroom classRoom) {
        this.dayOfWeek = dayOfWeek;
        this.period = period;
        this.subject = subject;
        this.teacherName = teacherName;
        this.classroom = classRoom;
    }

    // Getters and Setters
    public Long getId() 
        { return id; }

    public void setId(Long id)
         { this.id = id; }

    public String getDayOfWeek()
         { return dayOfWeek; }

    public void setDayOfWeek(String dayOfWeek) 
        { this.dayOfWeek = dayOfWeek; }

    public String getPeriod()
         { return period; }

    public void setPeriod(String period)
         { this.period = period; }

    public String getSubject()
         { return subject; }

    public void setSubject(String subject)
         { this.subject = subject; }

    public String getTeacherName()
         { return teacherName; }

    public void setTeacherName(String teacherName)
         { this.teacherName = teacherName; }

    public Classroom getClassroom()
         { return classroom; }

    public void setClassroom(Classroom classRoom)   
        { this.classroom = classRoom; }
}


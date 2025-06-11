import java.time.LocalDate;
import java.io.Serializable;
//bản ghi điểm danh
public class AttendanceRecord implements Serializable {
    private LocalDate date;
    private boolean isPresent;
    private boolean hasPermission;

    public AttendanceRecord(LocalDate date, boolean isPresent, boolean hasPermission) {
        this.date = date;
        this.isPresent = isPresent;
        this.hasPermission = hasPermission;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public boolean hasPermission() {
        return hasPermission;
    }
}

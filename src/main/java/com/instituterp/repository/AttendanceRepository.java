package com.instituterp.repository;

import com.instituterp.entity.Attendance;
import com.instituterp.entity.Student;
import com.instituterp.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByStudentIdAndAttendanceDate(Long studentId, LocalDate date);
    List<Attendance> findByStudent(Student student);
    List<Attendance> findByBatchAndAttendanceDate(Batch batch, LocalDate date);
    List<Attendance> findByStudentIdOrderByAttendanceDateDesc(Long studentId);
}

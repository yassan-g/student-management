package raisetech.student_management;

import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

	private final StudentRepository studentRepository;

	public StudentManagementApplication(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

	/**
	 * 受講生情報を全件取得して返す
	 *
	 * @return Studentのリスト
	 */
	@GetMapping("/students")
	public List<Student> getStudents() {
		return  studentRepository.findAllStudents();
	}

	/**
	 * 受講生コース情報を全件取得して返す
	 *
	 * @return StudentCourseのリスト
	 */
	@GetMapping("/student-courses")
	public List<StudentCourse> getStudentCourses() {
		return  studentRepository.findAllStudentCourses();
	}

}

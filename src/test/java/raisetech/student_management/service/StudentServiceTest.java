package raisetech.student_management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student_management.controller.converter.StudentConverter;
import raisetech.student_management.data.Student;
import raisetech.student_management.data.StudentCourse;
import raisetech.student_management.domain.StudentDetail;
import raisetech.student_management.exception.StudentNotFoundException;
import raisetech.student_management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private StudentConverter studentConverter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(studentRepository, studentConverter);
  }

  @Test
  void 受講生情報一覧検索_リポジトリとコンバーターの処理が適切に呼び出され結果が返却されること() {
    // --- 準備 ---
    List<Student> studentList = List.of();
    List<StudentCourse> studentCourses = List.of();
    List<StudentDetail> expected = List.of();

    when(studentRepository.findAllStudents()).thenReturn(studentList);
    when(studentRepository.findAllStudentCourse()).thenReturn(studentCourses);
    when(studentConverter.convertStudentDetails(studentList, studentCourses)).thenReturn(expected);

    // --- 実行 ---
    List<StudentDetail> actual = sut.searchStudents();

    // --- 検証 ---
    assertEquals(expected,actual);

    verify(studentRepository).findAllStudents();
    verify(studentRepository).findAllStudentCourse();
    verify(studentConverter).convertStudentDetails(studentList, studentCourses);
  }

  @Test
  void 受講生詳細検索_IDが存在する場合_受講生とコース情報が返却されること() {
    // --- 準備 ---
    Integer id = 1;

    Student student = new Student();
    List<StudentCourse> studentCourses = List.of();

    when(studentRepository.findStudentById(id)).thenReturn(student);
    when(studentRepository.findCoursesByStudentId(id)).thenReturn(studentCourses);

    // --- 実行 ---
    StudentDetail actual = sut.searchStudent(id);

    // --- 検証 ---
    assertEquals(student, actual.getStudent());
    assertEquals(studentCourses, actual.getStudentCourses());

    verify(studentRepository).findStudentById(id);
    verify(studentRepository).findCoursesByStudentId(id);
  }

  @Test
  void 受講生詳細検索_IDが存在しない場合_例外がスローされること() {
    // --- 準備 ---
    Integer id = 999;

    when(studentRepository.findStudentById(id)).thenReturn(null);

    // --- 実行と検証 ---
    assertThrows(StudentNotFoundException.class, () -> sut.searchStudent(id));

    verify(studentRepository).findStudentById(id);
    verify(studentRepository, never()).findCoursesByStudentId(id);
  }

  @Test
  void 受講生詳細登録_受講生とコース情報が正しく登録されること() {
    // --- 準備 ---
    LocalDate today = LocalDate.now();

    Student student = new Student();
    student.setId(1);

    StudentCourse course1 = new StudentCourse();
    course1.setCourseName("Javaコース");

    StudentCourse course2 = new StudentCourse();
    course2.setCourseName("SQLコース");

    List<StudentCourse> studentCourses = List.of(course1, course2);

    StudentDetail studentDetail = new StudentDetail(student, studentCourses);

    // --- 実行 ---
    StudentDetail actual = sut.registerStudent(studentDetail);

    // --- 検証 ---
    // 受講生登録が呼ばれている
    verify(studentRepository).registerStudent(student);

    // コース登録が2回呼ばれている
    verify(studentRepository).registerStudentCourse(course1);
    verify(studentRepository).registerStudentCourse(course2);

    // studentId が設定されている
    assertEquals(1, course1.getStudentId());
    assertEquals(1, course2.getStudentId());

    // startDate が今日になっている
    assertEquals(today, course1.getStartDate());
    assertEquals(today, course2.getStartDate());

    // expectedEndDate が calculateEndDate の結果と一致
    assertEquals(today.plusMonths(6), course1.getExpectedEndDate());
    assertEquals(today.plusMonths(2), course2.getExpectedEndDate());

    // 戻り値が引数と同じインスタンスであること
    assertEquals(studentDetail, actual);
  }

  @Test
  void 受講生詳細更新_受講生とコース情報が正しく更新されること() {
    // --- 準備 ---
    Student student = new Student();
    student.setId(1);

    LocalDate startDate1 = LocalDate.of(2024, 1, 1);
    LocalDate startDate2 = LocalDate.of(2024, 2, 1);

    StudentCourse course1 = new StudentCourse();
    course1.setCourseName("Javaコース");
    course1.setStartDate(startDate1);

    StudentCourse course2 = new StudentCourse();
    course2.setCourseName("SQLコース");
    course2.setStartDate(startDate2);

    List<StudentCourse> studentCourses = List.of(course1, course2);

    StudentDetail studentDetail = new StudentDetail(student, studentCourses);

    // --- 実行 ---
    StudentDetail actual = sut.updateStudent(studentDetail);

    // --- 検証 ---
    // 受講生更新が呼ばれている
    verify(studentRepository).updateStudent(student);

    // コース更新が呼ばれている
    verify(studentRepository).updateStudentCourse(course1);
    verify(studentRepository).updateStudentCourse(course2);

    // expectedEndDate が calculateEndDate の結果と一致
    assertEquals(startDate1.plusMonths(6), course1.getExpectedEndDate());
    assertEquals(startDate2.plusMonths(2), course2.getExpectedEndDate());

    // 戻り値が引数と同じインスタンスであること
    assertEquals(studentDetail, actual);
  }

  @Test
  void 受講生情報削除_削除件数が1以上の場合_受講生情報が返却されること() {
    // --- 準備 ---
    Integer id = 1;
    Student student = new Student();
    student.setId(id);

    when(studentRepository.logicalDeleteStudent(id)).thenReturn(1);
    when(studentRepository.findStudentById(id)).thenReturn(student);

    // --- 実行 ---
    Student actual = sut.deleteStudent(id);

    // --- 検証 ---
    assertEquals(student, actual);

    verify(studentRepository).logicalDeleteStudent(id);
    verify(studentRepository).findStudentById(id);
  }

  @Test
  void 受講生情報削除_削除件数が0の場合_例外がスローされること() {
    // --- 準備 ---
    Integer id = 999;

    when(studentRepository.logicalDeleteStudent(id)).thenReturn(0);

    // --- 実行と検証 ---
    assertThrows(StudentNotFoundException.class, () -> sut.deleteStudent(id));

    verify(studentRepository).logicalDeleteStudent(id);
    verify(studentRepository, never()).findStudentById(id);
  }

  @Test
  void 受講生情報復元_復元件数が1以上の場合_受講生情報が返却されること() {
    // --- 準備 ---
    Integer id = 1;
    Student student = new Student();
    student.setId(id);

    when(studentRepository.restoreStudent(id)).thenReturn(1);
    when(studentRepository.findStudentById(id)).thenReturn(student);

    // --- 実行 ---
    Student actual = sut.restoreStudent(id);

    // --- 検証 ---
    assertEquals(student, actual);

    verify(studentRepository).restoreStudent(id);
    verify(studentRepository).findStudentById(id);
  }

  @Test
  void 受講生情報復元_復元件数が0の場合_例外がスローされること() {
    // --- 準備 ---
    Integer id = 999;

    when(studentRepository.restoreStudent(id)).thenReturn(0);

    // --- 実行と検証 ---
    assertThrows(StudentNotFoundException.class, () -> sut.restoreStudent(id));

    verify(studentRepository).restoreStudent(id);
    verify(studentRepository, never()).findStudentById(id);
  }

}

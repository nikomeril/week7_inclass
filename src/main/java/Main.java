import dao.StudentDAO;
import dao.InstructorDAO;
import dao.TrainingSessionDAO;
import model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("aikidoPU");
        EntityManager em = emf.createEntityManager();

        StudentDAO studentDAO = new StudentDAO(em);
        InstructorDAO instructorDAO = new InstructorDAO(em);
        TrainingSessionDAO sessionDAO = new TrainingSessionDAO(em);

        Student student1 = new Student("John Doe", "john@example.com", AikidoRank.KYU_5, LocalDate.now().minusMonths(7));
        Instructor instructor = new Instructor("Sensei Aki", "Aikido Throws", 10);
        TrainingSession session = new TrainingSession(LocalDate.now(), "Main Dojo", 90, instructor);

        instructor.setTrainingSessions(List.of(session));

        Attendance attendance = new Attendance(student1, session, AttendanceStatus.PRESENT, "Good performance");
        session.setAttendances(List.of(attendance));
        student1.setAttendanceRecords(List.of(attendance));

        em.getTransaction().begin();
        em.persist(student1);
        em.persist(instructor);
        em.persist(session);
        em.persist(attendance);
        em.getTransaction().commit();

        System.out.println("--- Find students by rank (KYU_5) ---");
        List<Student> studentsByRank = studentDAO.findByRank(AikidoRank.KYU_5);
        studentsByRank.forEach(s -> System.out.println(s.getName()));

        System.out.println("\n--- Find experienced instructors (5+ years) ---");
        List<Instructor> experiencedInstructors = instructorDAO.findExperiencedInstructors(5);
        experiencedInstructors.forEach(i -> System.out.println(i.getName()));

        System.out.println("\n--- Find sessions for student John Doe ---");
        List<TrainingSession> studentSessions = sessionDAO.findSessionsByStudentId(student1.getId());
        studentSessions.forEach(ts -> System.out.println("Session at " + ts.getLocation() + " on " + ts.getDate()));


        em.close();
        emf.close();
    }
}
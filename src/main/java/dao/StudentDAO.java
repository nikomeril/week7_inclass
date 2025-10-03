package dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.AikidoRank;
import model.Student;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

public class StudentDAO extends GenericDAO<Student> {
    public StudentDAO(EntityManager entityManager) {
        super(Student.class, entityManager);
    }

    public List<Student> findByRank(AikidoRank rank) {
        return entityManager.createQuery("SELECT s FROM Student s WHERE s.rank = :rank", Student.class)
                .setParameter("rank", rank)
                .getResultList();
    }

    public List<Student> findStudentsWithRecentProgressReports() {
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        return entityManager.createQuery(
                        "SELECT DISTINCT p.student FROM ProgressReport p WHERE p.reportDate >= :date", Student.class)
                .setParameter("date", threeMonthsAgo)
                .getResultList();
    }

    public List<Student> findStudentsJoinedInLastSixMonths() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        Root<Student> student = cq.from(Student.class);
        cq.where(cb.greaterThanOrEqualTo(student.get("joinDate"), LocalDate.now().minusMonths(6)));
        return entityManager.createQuery(cq).getResultList();
    }
}

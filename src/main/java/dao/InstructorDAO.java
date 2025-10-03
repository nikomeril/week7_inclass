package dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Instructor;
import jakarta.persistence.EntityManager;

import java.util.List;

public class InstructorDAO extends GenericDAO<Instructor> {
    public InstructorDAO(EntityManager entityManager) {
        super(Instructor.class, entityManager);
    }

    public List<Instructor> findBySpecialization(String specialization) {
        return entityManager.createQuery(
                        "SELECT i FROM Instructor i WHERE i.specialization = :spec", Instructor.class)
                .setParameter("spec", specialization)
                .getResultList();
    }

    public List<Instructor> findExperiencedInstructors(int minExperience) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Instructor> cq = cb.createQuery(Instructor.class);
        Root<Instructor> instructor = cq.from(Instructor.class);
        cq.where(cb.greaterThan(instructor.get("experienceYears"), minExperience));
        return entityManager.createQuery(cq).getResultList();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import entity.Student;
import entity.Studypoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class EntityHandler {
    public List<Student> getAllStudents(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_jpqlDemo_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        
        TypedQuery<Student> students = em.createNamedQuery("Student.findAll", Student.class);
        
        em.close();
        emf.close();
        return students.getResultList();
    }
    
    public Student getStudentByFirstName(String name) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_jpqlDemo_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        
        TypedQuery<Student> student = em.createNamedQuery("Student.findByFirstname", Student.class);
        
        return student.setParameter(1, name).getSingleResult();
    }
    
    public Student getStudentByLastName(String lastname) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_jpqlDemo_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        
        TypedQuery<Student> student = em.createNamedQuery("Student.findByLastname", Student.class);
        
        return student.setParameter(1, lastname).getSingleResult();
    }
    
    public int getSumForStudentWithID(int id){
        int sum = 0;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_jpqlDemo_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        
        Student student = em.createNamedQuery("Student.findById", Student.class).getSingleResult();
        Collection<Studypoint> col = student.getStudypointCollection();
        
        for (Studypoint sp : col) {
            sum += sp.getScore();
        }
        return sum;
    }
    
    public int getSumForAllStudents(int id){
        int sum = 0;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_jpqlDemo_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        
        List<Student> students = em.createNamedQuery("Student.findAll", Student.class).getResultList();
        
        for (Student s : students){
            Collection<Studypoint> col = s.getStudypointCollection();
            for(Studypoint sp : col) {
                sum += sp.getScore();
            }
        }
        return sum;
    }
    
    public Student getStudentWithMaxScore(){
        Student kingOfTheHill = null;
        int best = 0;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_jpqlDemo_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        
        List<Student> students = em.createNamedQuery("Student.findAll", Student.class).getResultList();
        
        for (Student s : students) {
            int tmp = getSumForStudentWithID(s.getId());
            if (tmp > best) {
                kingOfTheHill = s;
                best = tmp;
            }
        }
        return kingOfTheHill;
    }
    
    public Student getStudentWithLowestScore(){
        Student loserOfTheDay = null;
        int worst = 100;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_jpqlDemo_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        
        List<Student> students = em.createNamedQuery("Student.findAll", Student.class).getResultList();
        
        for (Student s : students) {
            int tmp = getSumForStudentWithID(s.getId());
            if (tmp < worst) {
                loserOfTheDay = s;
                worst = tmp;
            }
        }
        return loserOfTheDay;
    }
    
    public void createNewStudent(String firstName, String lastName){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_jpqlDemo_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student student = new Student();
        student.setFirstname(firstName);
        student.setLastname(lastName);
        em.persist(student);
        em.getTransaction().commit();
    }
}

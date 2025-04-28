package control;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Conexion <T>{
    private T control;
    private CitaJpaController cCitas;
    private TutorJpaController cTutor;
    private TutoradoJpaController cTutorado;
    
    //Agrega más de ser necesarios
    
    EntityManagerFactory emf;
    
    public Conexion(){
        emf = Persistence.createEntityManagerFactory("tutoriasPU"); //Compila el objeto que tenemos
        if (control instanceof TutorJpaController){
            control = (T) (TutorJpaController) new TutorJpaController(emf);
        }
        else if (control instanceof CitaJpaController) {
            control = (T) (CitaJpaController) new CitaJpaController(emf);
        }
        else if (control instanceof TutoradoJpaController) {
            control = (T) (TutoradoJpaController) new TutoradoJpaController(emf);
        }
    }
    
    public T getControl(){
        return control;
    }
    
}

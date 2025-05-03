//Este se usa en lugar de conexión

package control;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
            
public class AdmDatos {
    protected static EntityManagerFactory enf;
    public static EntityManagerFactory getEnf(){
        if(enf == null)
            enf = Persistence.createEntityManagerFactory("tutoriasPU");
        
        return enf;
    }
}

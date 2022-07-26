/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.util;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author
 */
public abstract class UtilidadesExcepciones {

    private static final Logger LOG = Logger.getLogger(UtilidadesExcepciones.class.getName());
    
    
    
    public static ExcepcionDataBaseEnum analizarExcepcionDataBase(PersistenceException e)
    {
        if(e.getCause()!=null && e.getCause().getClass().equals(DatabaseException.class) )
            {
                DatabaseException dbe=(DatabaseException) e.getCause();
                //TODO: Esta valifacion de la claves primarias es solo para la base de datos derby
                if(dbe.getCause()!=null && dbe.getCause().getClass().equals(DerbySQLIntegrityConstraintViolationException.class))
                {
                    DerbySQLIntegrityConstraintViolationException constrainViolation = (DerbySQLIntegrityConstraintViolationException) dbe.getCause();
                    LOG.log(Level.WARNING,constrainViolation.getMessage());
                    //throw new ConstrainViolationExceptionSQL("Ya existe un registro registrado con la clave primaria");
                    return ExcepcionDataBaseEnum.CLAVE_DUPLICADO;
                }
                
            }
        return ExcepcionDataBaseEnum.DESCONOCIDO;
    }
}

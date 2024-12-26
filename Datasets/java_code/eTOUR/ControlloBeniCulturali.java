package unisa.gps.etour.control.GestioneBeniCulturali;

import java.util.Date;
import unisa.gps.etour.bean.BeanBeneCulturale;

/**
 * This class has the task of checking the data of a cultural object.
 * Various consistency checks are performed, such as length of strings,
 * Null reference, dynamic types incorrect.
 * 
 * @author Michelangelo De Simone
 * @version 0.1
 * 
 * © 2007 eTour Project - Copyright by DMI SE @ SA Lab - University of Salerno
 */
public class ControlloBeniCulturali {

    /**
     * Please consistency check by calling the appropriate methods.
     * This method wraps all other methods of control with a single call.
     * At the first false value the flow is interrupted.
     *
     * @param PBC The PBC of the cultural object to be inspected
     * @return boolean The result of the check: true if OK, false otherwise
     */
    public static boolean controllaDatiBeneCulturale(BeanBeneCulturale PBC) {
        // This method checks the input parameter in the cases
        // Null reference or dynamic wrong
        if (PBC == null || !(PBC instanceof BeanBeneCulturale)) {
            return false;
        }

        // This method checks if the ID passed as a parameter BeanBeneCulturale
        // is valid or not
        if (!controllaIdBeneCulturale(PBC.getId())) {
            return false;
        }

        // This method checks the objects contained in BeanBeneCulturale Date
        // as such, a check is made null and reference checks
        // on the dynamic.
        if (!controllaDateBeneCulturale(PBC)) {
            return false;
        }

        // This method checks all the fields in BeanBeneCulturale, research
        // any null references
        if (!controllaDatiNulli(PBC)) {
            return false;
        }

        // Check the correct length of string; in this case, the CAP must
        // be exactly five digits, while the province must be two.
        // TODO: To be completed
        if (PBC.getCap().length() != 5 || PBC.getProvincia().length() != 2) {
            return false;
        }

        return true;
    }

    /**
     * Check for null data in a bean cultural property.
     * The check is performed on all fields of the bean.
     *
     * @param PBC The PBC cultural property to be checked
     * @return boolean The result of the check: true if there are no null references, false otherwise
     */
    public static boolean controllaDatiNulli(BeanBeneCulturale PBC) {
        if (PBC.getCap() == null || PBC.getCitta() == null || PBC.getDescrizione() == null || 
            PBC.getGiornoChiusura() == null || PBC.getLocalita() == null || 
            PBC.getNome() == null || PBC.getOrarioApertura() == null || 
            PBC.getOrarioChiusura() == null || PBC.getProvincia() == null || 
            PBC.getTelefono() == null || PBC.getVia() == null) {
            return false;
        }

        return true;
    }

    /**
     * Check the consistency of dates within this BeanBeneCulturale.
     * The check is performed only on objects, while not carried out
     * no validity check on a date as a cultural object may also have
     * dates later than today (see for example on open exhibitions).
     *
     * @param PBC BeanBeneCulturale which check the dates
     * @return boolean The result of the check: true if the dates have consistency; false otherwise
     */
    public static boolean controllaDateBeneCulturale(BeanBeneCulturale PBC) {
        if (PBC.getOrarioApertura() == null || !(PBC.getOrarioApertura() instanceof Date) || 
            PBC.getOrarioChiusura() == null || !(PBC.getOrarioChiusura() instanceof Date)) {
            return false;
        }

        return true;
    }

    /**
     * Check the ID of BeanBeneCulturale
     *
     * @param pId Id BeanBeneCulturale be checked
     * @return boolean The result of the check: true if the ID is correct, false otherwise
     */
    public static boolean controllaIdBeneCulturale(int pId) {
        return (pId > 0);
    }
}
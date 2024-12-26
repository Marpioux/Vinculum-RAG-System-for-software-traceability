package unisa.gps.etour.util;

import java.awt.image.BufferedImage;
import java.util.Date;
import javax.swing.ImageIcon;
import unisa.gps.etour.bean.BeanBanner;
import unisa.gps.etour.bean.BeanBeneCulturale;
import unisa.gps.etour.bean.BeanConvenzione;
import unisa.gps.etour.bean.BeanMenu;
import unisa.gps.etour.bean.BeanNews;
import unisa.gps.etour.bean.BeanOperatorePuntoDiRistoro;
import unisa.gps.etour.bean.BeanPiatto;
import unisa.gps.etour.bean.BeanPreferenzaDiRicerca;
import unisa.gps.etour.bean.BeanPreferenzeGeneriche;
import unisa.gps.etour.bean.BeanPuntoDiRistoro;
import unisa.gps.etour.bean.BeanTag;
import unisa.gps.etour.bean.BeanTurista;
import unisa.gps.etour.bean.BeanVisitaBC;
import unisa.gps.etour.bean.BeanVisitaPR;

/**
 * Class for managing the control of the correctness of the strings
 * 
 * @author Joseph Penna
 * @version 0.1 © 2007 eTour Project - Copyright by DMI SE @ SA Lab --
 * University of Salerno
 */
public class ControlloDati {

    private static final String LETTERS = "abcdefghijklmnopqrstuvxywz"
            + "ABCDEFGHIJKLMNOPQRSTUVXYWZ";
    private static final String NUMBERS = "0123456789";
    public static final int MAX_LENGTH = 64;

    /**
     * Static method for verifying correctness of a string
     * 
     * @param pStringa           string to check
     * @param letterePermesse    Boolean: True if it is allowed to be present
     *                           Letters in the string, False otherwise
     * @param numeriPermessi     Boolean: True if it is allowed to be present
     *                           Numbers in the string, False otherwise
     * @param caratteriPermessi   string containing all characters
     *                           Allowed in the string
     * @param caratteriNecessari  string containing all required characters
     *                           Must be present in the string
     * @param numeroCaratteriMin integer representing the minimum number of
     *                           Characters allowed in string
     * @param numeroCaratteriMax integer representing the maximum number of
     *                           Characters allowed in string
     * @return Boolean: True if the string meets the conditions, False
     * otherwise
     */
    public static boolean controllaStringa(String pStringa, boolean letterePermesse,
            boolean numeriPermessi, String caratteriPermessi, String caratteriNecessari,
            int numeroCaratteriMin, int numeroCaratteriMax) {
        if (pStringa == null) {
            return false;
        }

        int lunghezzaStringa = pStringa.length();
        char carattereCorrente;

        if (lunghezzaStringa < numeroCaratteriMin || lunghezzaStringa > numeroCaratteriMax) {
            return false;
        }

        if (caratteriNecessari != null && !caratteriNecessari.isEmpty()) {
            for (int i = 0; i < caratteriNecessari.length(); i++) {
                carattereCorrente = caratteriNecessari.charAt(i);
                if (!pStringa.contains(Character.toString(carattereCorrente))) {
                    return false;
                }
            }
        }

        String stringaCaratteriPermessi = caratteriPermessi
                + (letterePermesse ? LETTERS : "")
                + (numeriPermessi ? NUMBERS : "");

        for (int i = 0; i < lunghezzaStringa; i++) {
            carattereCorrente = pStringa.charAt(i);
            if (!stringaCaratteriPermessi.contains(Character.toString(carattereCorrente))) {
                return false;
            }
        }

        return true;
    }

    public static String correggiStringa(String pStringa, boolean letterePermesse,
            boolean numeriPermessi, String caratteriPermessi, int numeroCaratteriMax) {
        if (pStringa == null) {
            return null;
        }

        String stringaCaratteriPermessi = caratteriPermessi
                + (letterePermesse ? LETTERS : "")
                + (numeriPermessi ? NUMBERS : "");

        char carattereCorrente;
        int lunghezzaStringa = pStringa.length();
        int i = 0;

        while (i < lunghezzaStringa) {
            carattereCorrente = pStringa.charAt(i);
            if (!stringaCaratteriPermessi.contains(Character.toString(carattereCorrente))) {
                pStringa = pStringa.replaceAll("\\\\" + Character.toString(carattereCorrente), "");
                lunghezzaStringa = pStringa.length();
            } else {
                i++;
            }
        }
        if (lunghezzaStringa > numeroCaratteriMax) {
            pStringa = pStringa.substring(0, numeroCaratteriMax);
        }

        return pStringa;
    }

    public static boolean controllaData(String pData) {
        return true;
    }

    public static boolean controllaDate(Date pDataInizio, Date pDataFine) {
        if (pDataInizio != null && pDataFine != null) {
            return pDataInizio.before(pDataFine);
        }
        return false;
    }

    public static boolean checkBeanTurista(BeanTurista pTurista) {
        return pTurista != null && pTurista instanceof BeanTurista;
    }

    public static boolean checkBeanPreferenzaDiRicerca(BeanPreferenzaDiRicerca pPreferenzaDiRicerca) {
        return pPreferenzaDiRicerca != null && pPreferenzaDiRicerca instanceof BeanPreferenzaDiRicerca;
    }

    public static boolean checkBeanPreferenzeGeneriche(BeanPreferenzeGeneriche pPreferenzeGeneriche) {
        return pPreferenzeGeneriche != null && pPreferenzeGeneriche instanceof BeanPreferenzeGeneriche;
    }

    public static boolean checkBeanBeneCulturale(BeanBeneCulturale pBeneCulturale) {
        return pBeneCulturale != null && pBeneCulturale instanceof BeanBeneCulturale;
    }

    public static boolean checkBeanPuntoDiRistoro(BeanPuntoDiRistoro pPuntoDiRistoro) {
        return pPuntoDiRistoro != null && pPuntoDiRistoro instanceof BeanPuntoDiRistoro;
    }

    public static boolean checkBeanOperatorePuntoDiRistoro(BeanOperatorePuntoDiRistoro pOperatorePuntoDiRistoro) {
        return pOperatorePuntoDiRistoro != null && pOperatorePuntoDiRistoro instanceof BeanOperatorePuntoDiRistoro;
    }

    /**
     * Please formal control and consistency on the data of the banner
     * Content in the bean passed by parameter.
     * 
     * @author Fabio Palladino
     * @param pBanner bean contains the data of the banner.
     * @return True if the data of the banner is correct false otherwise.
     */
    public static boolean checkBeanBanner(BeanBanner pBanner) {
        if (pBanner != null && pBanner instanceof BeanBanner) {
            return pBanner.getId() > 0 && !pBanner.getPercorsoFile().isEmpty() && pBanner.getIdPuntoDiRistoro() > 0;
        }
        return false;
    }

    /**
     * Method which controls the image contained in the object ImageIcon passed
     * per parameter.
     * 
     * @author Fabio Palladino
     * @param image ImageIcon object containing the image to be checked
     * @return true if the image contained in the object is an instance of BufferedImage.
     */
    public static boolean checkImmagine(ImageIcon image) {
        return image != null && (image.getImage() instanceof BufferedImage);
    }

    /**
     * Function that checks the data in a news;
     * 
     * @author Fabio Palladino
     * @param pNews containing details of a news.
     * @return
     */
    public static boolean checkBeanNews(BeanNews pNews) {
        if (pNews != null) {
            Date dataPubb = pNews.getDataPubblicazione();
            Date dataScad = pNews.getDataScadenza();
            String news = pNews.getNews();
            int priority = pNews.getPriorita();

            if (dataPubb != null && dataScad != null && news != null) {
                boolean toReturn = dataPubb.before(dataScad);
                toReturn = toReturn && !news.isEmpty();
                toReturn = toReturn && (pNews.getId() > 0);
                toReturn = toReturn && (priority <= CostantiGlobali.MAX_PRIORITY_NEWS)
                        && (priority >= CostantiGlobali.MIN_PRIORITY_NEWS);
                return toReturn;
            }
        }
        return false;
    }

    public static boolean checkBeanTag(BeanTag ptagi) {
        return ptagi != null && ptagi instanceof BeanTag;
    }

    public static boolean checkBeanConvenzione(BeanConvenzione pConvenzione) {
        return pConvenzione != null && pConvenzione instanceof BeanConvenzione;
    }

    public static boolean checkBeanMenu(BeanMenu pMenu) {
        return pMenu != null && pMenu instanceof BeanMenu;
    }

    public static boolean checkBeanPiatto(BeanPiatto pPiatto) {
        return pPiatto != null && pPiatto instanceof BeanPiatto;
    }

    public static boolean checkBeanVisitaBC(BeanVisitaBC pVisitaBC) {
        return pVisitaBC != null && pVisitaBC instanceof BeanVisitaBC;
    }

    public static boolean checkBeanVisitaPR(BeanVisitaPR pVisitaPR) {
        return pVisitaPR != null && pVisitaPR instanceof BeanVisitaPR;
    }
}
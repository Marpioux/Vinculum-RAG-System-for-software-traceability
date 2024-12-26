/**
 * Class that contains static methods that perform
 * consistency checks on the data bean banner
 * and news.
 *
 * @author Fabio Palladino
 * @version 0.1
 *
 * © 2007 eTour Project - Copyright by DMI SE @ SA Lab - University of Salerno
 */
package com.trapan.spg.control.GestioneAdvertisement;

import java.util.Date;
import com.trapan.spg.bean.BeanBanner;
import com.trapan.spg.bean.BeanNews;

public class ControlloDatiAdvertisement {

    /**
     * Please formal control and consistency on
     * data content of the banner in the bean passed by parameter.
     * 
     * @param pBanner bean contains the data of the banner.
     * @return true if the banner data is consistent, false otherwise
     */
    public static boolean controlloBanner(BeanBanner pBanner) {
        boolean toReturn = false;

        if (pBanner != null) {
            toReturn = (pBanner.getId() > 0 && !pBanner.getPercorsoFile().isEmpty()
                        && pBanner.getIdPuntoDiRistoro() > 0);
        }

        return toReturn;
    }

    /**
     * Method that performs consistency checks and
     * correctness of the information contained in the bean passed
     * per parameter, in particular check that the dates
     * publication and expiration of the news are consistent,
     * or that the second is the later.
     *
     * @param pNews bean containing data news
     * @return true if the bean contains consistent data, false otherwise
     */
    public static boolean controlloNews(BeanNews pNews) {
        boolean toReturn = false;

        /* Check the validity of the general method parameter */
        if (pNews != null) {
            Date dataPubb = pNews.getDataPubblicazione(); // Release Date
            Date dataScad = pNews.getDataScadenza(); // Due Date
            String news = pNews.getNews(); // Text of News

            /* Checking the validity of the fields */
            if (dataPubb != null && dataScad != null && news != null) {
                /* Check the consistency of the dates */
                toReturn = dataPubb.before(dataScad);

                /* Check that the text is not empty */
                toReturn = toReturn && !news.isEmpty();

                /* Check that the ID is greater than 0 */
                toReturn = toReturn && (pNews.getId() > 0);

                /* Check the priority value */
            }
        }

        return toReturn;
    }
}
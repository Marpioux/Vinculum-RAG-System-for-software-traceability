package unisa.gps.etour.control.fuzzy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class Fuzzy {

    /**
     * Class that implements the methods used to calculate the Fuzzy
     * Category of membership of a refreshment or a cultural property.
     */

    /**
     * Method for calculating the relevance of a term.
     *
     * @param distance: Contains the distance of the term from category
     *                  Analyzed
     * @param pMaxDist: Contains distaza maximum of all terms in all
     *                  Categories.
     * @return Returns the relevance of the term in the category.
     */
    private static float relevance(float distance, float pMaxDist) {
        return (distance / pMaxDist);
    }

    /**
     * Method for calculating the distance between a term and a category
     *
     * @param pTermine: Contains the period analyzed
     * @param pTotTermini: Tables of the total frequency of terms.
     * @param frequenzaTesto: Table of the terms of the text analyzed.
     * @param pCategoria: Category analyzed.
     * @return Returns the distance of the term pTermine by category
     *         pCategoria
     */
    private static float distance(String pTermine,
                                   Hashtable<String, float[]> pTotTermini, float frequenzaTesto,
                                   Category pCategoria) {

        // The first variable tracks the frequency of a term
        // Relating to a category
        // The second keeps track of frequency for all
        // Categories
        float[] frequenzaCategoria = new float[3], frequenzaTotale = new float[3];

        if (pCategoria.esisteTermine(pTermine)) { // if the term is
            // In category
            // Its frequency in this category is equal to the frequency
            // KnowledgeBase
            // More frequency in the search text
            frequenzaCategoria = pCategoria.getVal(pTermine);
            frequenzaCategoria[0] += frequenzaTesto;
        } else {
            // Otherwise it is equal to the frequency of the term in the text
            // Analyzed
            frequenzaCategoria[0] = frequenzaTesto;
        }
        
        if (pTotTermini.get(pTermine) != null) { // if the term exists in
            // Table of the total time
            frequenzaTotale = pTotTermini.get(pTermine); // Get the value

            frequenzaTotale[0] += frequenzaTesto; // the total frequency is given
            // Frequency in the text
            // Analyzed
            // Plus any frequency stored in memory in the table
            // Total time
        }

        return (frequenzaCategoria[0] / frequenzaTotale[0]); // distance is
        // Equal to
        // Frequency in
        // Category
        // The total frequency
    }

    /**
     * Method for calculating the distance of a term from one category.
     * Used for training
     *
     * @param pTermine period to analyze
     * @param pCategoria category from which you must calculate the distance
     * @param pTotTermini total time
     * @return Returns the distance of a term from one category
     */
    private static float distance(String pTermine, Category pCategoria,
                                   Hashtable<String, float[]> pTotTermini) {
        return ((pCategoria.getVal(pTermine))[0] / (pTotTermini.get(pTermine))[0]);
    }

    /**
     * Implementation of a function T-Norm
     *
     * @param a first value
     * @param b the second value
     * @return returns the value calculated using a function T-Norm
     */
    private static float tNorm(float a, float b) {
        return ((a * b) / ((2 - ((a + b) - (a * b)))));
        // Return Math.min(a, b);
        // Return a * b;
        // Return Math.max(0, a + b - 1);
    }

    /**
     * Implementation of a function S-Norm
     *
     * @param a first value
     * @param b the second value
     * @return returns the value calculated using a function S-Norm
     */
    private static float sNorm(float a, float b) {
        return ((a + b) / (1 + a * b));
        // Return Math.max(a, b);
        // Return (a + b - (a * b));
        // Return Math.min(1, a + b);
    }

    /**
     * Calculation of similarity between a category and a given text input
     *
     * @param pTermini worded. It must contain to
     *                  each term values of importance and belonging situated in
     *                  float[] in positions 1 and 2.
     * @return returns a numeric value that indicates the similarity of a
     *         Text with the category on which one has calculated the values of
     *         Importance and belonging
     */
    private static float similarity(Hashtable<String, float[]> pTermini) {
        float sum = 0; // return value

        for (float[] val : pTermini.values()) { // for all elements of
            // Table
            sum += (tNorm(val[1], val[0])) / (sNorm(val[1], val[0])); // performs
            // Sum of the values given by the division of function T-Norm
            // With the function S-Norm made
            // Between relevance and belonging
        }

        return sum;
    }

    /**
     * Method for the calculation of membership of a text to a category
     *
     * @param val indicates the similarity of a text with a category
     * @param maxSimilarity indicates the maximum similarity found
     * @return returns a value in the interval [0,1] that indicates the degree of
     *         Membership of the text to the category
     */
    private static float membership(float val, float maxSimilarity) {
        return (val / maxSimilarity);
    }

    /**
     * Method to delete special characters and to bring
     * All uppercase to lowercase
     *
     * @param pStr string to transform
     * @return returns the text to lowercase characters and no special
     */
    private static String replaceAndLower(String pStr) {
        pStr = pStr.toLowerCase();
        pStr = pStr.replace(",", "");
        pStr = pStr.replace(".", "");
        pStr = pStr.replace("!", "");
        pStr = pStr.replace("?", "");
        pStr = pStr.replace("'", "");

        return pStr;
    }

    /**
     * Method of retrieving the category you belong to a text
     *
     * @param pDescrizione text to analyze
     * @return a string indicating the category
     * @throws RemoteException
     */
    public static String calcolaCategoria(String pDescrizione) throws RemoteException {
        if ((pDescrizione == null) || (pDescrizione.equals("")))
            return "NULL";
        String text = pDescrizione;
        // Table of terms associated with the text portion. Will contain
        // Values of frequency, relevance, membership for each term
        Hashtable<String, float[]> datiTesto = new Hashtable<>();
        // Table of categories, each category will contain the value of
        // Similarity and belonging Text
        Hashtable<String, float[]> testoCategoria = new Hashtable<>();
        text = replaceAndLower(text); // delete characters and special
        // Returns the text by replacing
        // Uppercase with lowercase
        String[] testoSplit = text.split(" ");
        for (int i = 0; i < testoSplit.length; i++) { // For each end of the text
            float[] toPut = new float[3]; // value to assign to the string
            // In the hash table
            float[] valTmp; // temporary variable containing the values
            // Associated with the string if it already exists in the hash table
            // If the string is present in the table picks up the values
            // And increases by saving them in toPut
            // Otherwise initialize the new string with frequency = 1
            if (testoSplit[i].length() <= 3) // delete undefined terms
                // As important, the inter ...
                continue;
            if (exists(testoSplit[i], datiTesto)) { // if the time analyzed
                // Is present in table the terms of the analyzed text
                // We get the value of frequency in the table and there
                // Adds one
                valTmp = datiTesto.get(testoSplit[i]);
                toPut[0] = valTmp[0];
                toPut[0] += (float) 1 / testoSplit.length;
            } else {
                toPut[0] = (float) 1 / testoSplit.length; // Otherwise initialize the value of frequency to a
                // Fratto, the total number of terms (relative frequency)
            }
            // Insert the new entry in the table
            // System.out.println(toPut[0]);
            datiTesto.put(testoSplit[i], toPut);
        }

        // You try to open the knowledge base
        ElencoCategorie list;
        try {
            list = apriElenco();
        } catch (ClassNotFoundException e) { // error opening file kb.sbt
            throw new RemoteException("The knowledge base is missing or corrupt");
        } catch (Exception e) {
            throw new RemoteException("The knowledge base is missing or corrupt");
        }

        // Is taken from the base of knowledge to the table of total time
        Hashtable<String, float[]> totTermini = list.getTotTermini();
        float maxSimilarity = -1; // holds the value of maximum similarity
        for (String CategoryName : list.Categorie()) { // For all the categories in the knowledge base
            float[] toPut = new float[3]; // value to assign to the string
            // In the hash table
            for (Enumeration<String> val = datiTesto.keys(); val.hasMoreElements();) { // For all elements of the table of the terms of the text
                String term = val.nextElement();
                // We get the value of a term
                float[] tmp = datiTesto.get(term);
                // Calculate range and bearing
                tmp[1] = distance(term, totTermini, tmp[0], list.getCategoria(CategoryName));
                tmp[2] = relevance(tmp[1], list.getMaxDist());
                datiTesto.put(term, tmp);
            }
            // We calculate the similarity Once the analysis
            // All the terms in a category
            toPut[0] = similarity(datiTesto);
            testoCategoria.put(CategoryName, toPut);
            if (maxSimilarity < toPut[0]) { // we update the value of maximum
                // If necessary similarity
                maxSimilarity = toPut[0];
            }
        }

        for (String CategoryName : list.Categorie()) { // For each category
            // We get the value of similarity of the text with the category
            // Analyzed
            float[] tmp = testoCategoria.get(CategoryName);
            tmp[1] = membership(tmp[0], maxSimilarity); // we calculate
            // Membership
            // Text to the similarity
            testoCategoria.put(CategoryName, tmp); // save everything in
            // Category table
        }

        return maxAppartenenza(testoCategoria); // returns the name output
        // Category
        // With the maximum degree of membership
    }

    /**
     * Method to find the category with which the text has the highest degree of
     * Membership
     *
     * @param pTestoCategoria table of categories to the text
     * @return a string indicating the name of the category with which
     *         The text has the highest degree of membership
     */
    private static String maxAppartenenza(Hashtable<String, float[]> pTestoCategoria) {
        String toReturn = null; // return value
        float max = -1; // Maximum value of membership

        for (Enumeration<String> elm = pTestoCategoria.keys(); elm.hasMoreElements();) { // For all categories of the table of categories of text
            String category = elm.nextElement();
            // Values are taken of similarity and belonging associated with
            // Category
            float[] tmp = pTestoCategoria.get(category);
            if (tmp[1] > max) { // If the degree of membership affiliation just uploaded
                // Is greater than the previous update data max and toReturn
                toReturn = category;
                max = tmp[1];
            }
        }
        return toReturn;
    }

    /**
     * Method used to check whether a term is presented in the table
     * The terms of the text
     *
     * @param pStr period to analyze
     * @param pTable tables in terms of the text
     * @return returns true if the term exists false otherwise
     */
    private static boolean exists(String pStr, Hashtable<String, float[]> pTable) {
        try {
            if (pTable.get(pStr) != null)
                return true;
        } catch (NullPointerException e) {
            return false;
        }

        return false;
    }

    /**
     * Method used to retrieve the knowledge base
     *
     * @return an object representing the type ElencoCategorie
     *         KnowledgeBase
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static ElencoCategorie apriElenco() throws IOException, ClassNotFoundException {
        File kBase = new File("kb.sbt"); // you open the file kb.sbt
        FileInputStream kBaseStream = new FileInputStream(kBase); // creates
        // A stream with the file
        ObjectInputStream kBaseObj = new ObjectInputStream(kBaseStream); // create a stream object with the file
        ElencoCategorie toReturn;

        toReturn = (ElencoCategorie) kBaseObj.readObject(); // Object is extracted and saved in the file returned in output
        return toReturn;
    }

    /**
     * Method used to create the file. Used in training
     *
     * @param path string indicating the path in which to create the file
     * @return returns an ObjectOutputStream to the file created
     * @throws IOException
     */
    private static ObjectOutputStream createFile(String path) throws IOException {
        ObjectOutputStream toReturn;
        File f = new File(path); // file is created
        if (f.exists())
            f.delete();

        FileOutputStream fout = new FileOutputStream(path);
        toReturn = new ObjectOutputStream(fout); // create the stream

        return toReturn;
    }

    /**
     * Method used to create the knowledge base
     *
     * @throws RemoteException
     */
    public static void training() throws RemoteException {
        String[] elencoCategorie = new String[4]; // array containing names
        // Of categories to be analyzed
        // Knowledge base
        ElencoCategorie list = new ElencoCategorie();

        ObjectOutputStream elencoOut;
        try {
            // Try to create the file
            elencoOut = createFile("kb.sbt");
        } catch (Exception e) {
            throw new RemoteException("Error creating file kb.sbt");
        }

        elencoCategorie[0] = "art";
        elencoCategorie[1] = "cinema";
        elencoCategorie[2] = "sport";
        // elencoCategorie[3] = "";

        for (int i = 0; i < 3; i++) { // for each category
            // Create a new object of type Category, which will contain all
            // Category data to be analyzed
            Category toPutCat = new Category(elencoCategorie[i]);
            // If the inclusion of the category in the table of categories
            // Not successful
            // We throw an exception
            if (!list.addCategoria(elencoCategorie[i], toPutCat)) {
                throw new RemoteException("Error creating data of category " + elencoCategorie[i]);
            }
            // You try to read from the folder containing the lyrics of a
            // Category
            // 100 sample test
            for (Integer j = 1; j <= 100; j++) {
                // Path of the folder category
                String path = "kb/" + elencoCategorie[i] + "/" + j.toString();
                // Try to read the file ith
                FileReader testoReader;
                try {
                    testoReader = new FileReader(path);
                } catch (FileNotFoundException e) {
                    // If the file does not exist it continues execution from
                    // File i +1
                    // System.out.println("Error on file" + path);
                    continue;
                }
                Scanner testoScanner = new Scanner(testoReader);
                while (testoScanner.hasNextLine()) {
                    // Read the text file line by line
                    String txt = testoScanner.nextLine();
                    txt = replaceAndLower(txt);
                    String[] toIterate = txt.split(" ");
                    for (int k = 0; k < toIterate.length; k++) { // For each end of the line
                        if (toIterate[k].length() <= 3) // remove the effect
                            // Undefined terms
                            // Relevant
                            continue;
                        float[] valTerm, valTotTerm;
                        // If the term is present in the table of terms
                        // The class analyzed
                        if (list.getCategoria(elencoCategorie[i]).esisteTermine(toIterate[k])) {
                            // Its frequency is equal to the value stored in
                            // Table plus one fratto the total number of
                            // Terms of the text
                            valTerm = list.getCategoria(elencoCategorie[i]).getVal(toIterate[k]);
                            valTerm[0] += (float) 1 / toIterate.length;
                            valTotTerm = list.getTermine(toIterate[k]);
                            valTotTerm[0] += (float) 1 / toIterate.length;
                        } else {
                            // otherwise it is equal to one fratto the total number of words of text
                            valTerm = new float[3];
                            valTotTerm = new float[1];
                            valTerm[0] = (float) 1 / toIterate.length;
                            valTotTerm[0] = (float) 1 / toIterate.length;
                        }
                        // save the values calculated in the table of terms of the category analyzed
                        list.setTermine(toIterate[k], valTotTerm);
                        list.getCategoria(elencoCategorie[i]).addTermine(toIterate[k], valTerm);
                        if (list.getMaxDist() < valTotTerm[0])
                            list.setMaxDist(valTotTerm[0]);
                    }
                }
            }
        }

        for (String CategoryName : list.Categorie()) { // for each category
            // is preflushed the table of terms
            Hashtable<String, float[]> terminiCategoria = list.getCategoria(CategoryName).getTermini();

            // all the terms are analyzed in the table of loaded terms
            for (Enumeration<String> enumTerm = terminiCategoria.keys(); enumTerm.hasMoreElements();) {
                // is effattuato calculating bearing and distance
                String term = enumTerm.nextElement();
                float[] val = terminiCategoria.get(term);
                val[1] = distance(term, list.getCategoria(CategoryName), list.getTotTermini());
                val[2] = relevance(val[1], list.getMaxDist());
            }
            // data is stored in the table of the terms of the class
            list.getCategoria(CategoryName).setTermini(terminiCategoria);
        }
        try {
            // writing the results of operations on files
            elencoOut.writeObject(list);
        } catch (Exception e) {
            throw new RemoteException("Error writing file");
        }
    }
}
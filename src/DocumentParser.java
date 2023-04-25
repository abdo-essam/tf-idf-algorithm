
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class TfIdf
{
    // Calculates the tf of term termToCheck
    // totalterms : Array of all the words under processing document
    // termToCheck : term of which tf is to be calculated.
    // returns tf(term frequency) of term termToCheck

    public double tfCalculator(String[] totalterms, String termToCheck)
    {
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms)
        {
            if (s.equalsIgnoreCase(termToCheck))
            {
                count++;
            }
        }
        return count / totalterms.length ;
    }

    // Calculates idf of term termToCheck
    // allTerms : all the terms of all the documents
    // returns idf(inverse document frequency) score

    public double idfCalculator(List<String[]> allTerms, String termToCheck)
    {
        double count = 0;
        for (String[] ss : allTerms)
        {
            for (String s : ss)
            {
                if (s.equalsIgnoreCase(termToCheck))
                {
                    count++;
                    break;
                }
            }
        }
        return 1 + Math.log(allTerms.size() / count);
    }
}

class CosineSimilarity
{

    // Method to calculate cosine similarity between two documents.
    // docVector1 : document vector 1 (a)
    // docVector2 : document vector 2 (b)

    public double cosineSimilarity(double[] docVector1, double[] docVector2)
    {
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;
        double cosineSimilarity = 0.0;

        for (int i = 0; i < docVector1.length; i++) //docVector1 and docVector2 must be of same length
        {
            dotProduct += docVector1[i] * docVector2[i];  //a.b
            magnitude1 += Math.pow(docVector1[i], 2);  //(a^2)
            magnitude2 += Math.pow(docVector2[i], 2); //(b^2)
        }

        magnitude1 = Math.sqrt(magnitude1);//sqrt(a^2)
        magnitude2 = Math.sqrt(magnitude2);//sqrt(b^2)

        if (magnitude1 != 0.0 | magnitude2 != 0.0)
        {
            cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
        }
        else
        {
            return 0.0;
        }
        return cosineSimilarity;
    }
}
public class DocumentParser
{

    //This variable will hold all terms of each document in an array.
    private List<String[]> termsDocsArray = new ArrayList<String[]>();
    private List<String> allTerms = new ArrayList<String>(); //to hold all terms
    private List<double[]> tfidfDocsVector = new ArrayList<double[]>();
//    private List<String> tfidfDocsVectorNames = new ArrayList<String>();

    // Method to read files and store in array.
    // filePath : source file path --  Generally a folder with the required set of documents

    public void parseFiles(String filePath) throws FileNotFoundException, IOException
    {
        File[] allfiles = new File(filePath).listFiles();
        BufferedReader in = null;
        for (File f : allfiles)
        {
            if (f.getName().endsWith(".txt"))
            {
                in = new BufferedReader(new FileReader(f));
                StringBuilder sb = new StringBuilder();
                String s = null;
                while ((s = in.readLine()) != null)
                {
                    sb.append(s);
                }
                String[] tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");   //to get individual terms
                for(String term : tokenizedTerms)	//avoid duplicate entries
                {
                    if(!allTerms.contains(term))
                    {
                        allTerms.add(term);
                    }
                }
                termsDocsArray.add(tokenizedTerms);
            }
        }

    }

    /**
     * Method to create termVector according to its tfidf score.
     */
    public void tfIdfCalculator()
    {
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term frequency inverse document frequency        
        for (String[] docTermsArray : termsDocsArray)
        {
            double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            for (String terms : allTerms)
            {
                tf = new TfIdf().tfCalculator(docTermsArray, terms);
                idf = new TfIdf().idfCalculator(termsDocsArray, terms);
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                count++;
            }
            tfidfDocsVector.add(tfidfvectors);  //storing document vectors;            
        }
    }

    // Method to calculate cosine similarity between all the documents.

    public void getCosineSimilarity()
    {
        for (int i = 0; i < tfidfDocsVector.size(); i++)
        {
            for (int j = 0; j < tfidfDocsVector.size(); j++)
            {
                if(i!=j)
                    System.out.println("between " + i + " and " + j + "  =  "+ new CosineSimilarity().cosineSimilarity (tfidfDocsVector.get(i),  tfidfDocsVector.get(j)));
            }
        }
    }
}

 class TfIdfMain
{

    public static void main(String args[]) throws FileNotFoundException, IOException
    {
        DocumentParser dp = new DocumentParser();
        dp.parseFiles("C:/Users/abdoe/IdeaProjects/IR"); // give the location of source file
        dp.tfIdfCalculator(); //calculates tfidf
        dp.getCosineSimilarity(); //calculates cosine similarity
    }
}
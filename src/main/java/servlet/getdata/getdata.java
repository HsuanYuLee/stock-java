package servlet.getdata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class getdata
{
    private URL url;
    private FileWriter fileWriter;


    public void setUrl(URL url) { this.url = url;}
    public void setFileWriter(FileWriter fileWriter){this.fileWriter = fileWriter;}

    public void download()throws IOException
    {
        Document data = Jsoup.parse(url, 5000);
        Elements trs = data.select("tr");

        for (Element tr : trs)
        {
            Elements tds = tr.select("td");

            int i = 0;
            for (Element td : tds)
            {
                String Td = td.text();
                System.out.println(Td + "\t");
                fileWriter.write(Td + "\t");
                i++;
            }

            System.out.println("\n");
            fileWriter.write("\n");
        }

        fileWriter.flush();
        fileWriter.close();
    }
}


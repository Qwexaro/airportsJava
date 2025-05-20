import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.util.Map;
import java.util.TreeMap;

public class Airport {
    private final String urlToMainPageAirports = "https://www.aviasales.ru";
    private Map<String, String>mapAllAirports;
    private final String patchToFilesHtml = "src/main/resources/data/";

    public Airport(){
        mapAllAirports = new TreeMap<>();
        fillMapAllAirports();
    }

    public Document returnHtmlPage(String urlPage){
        Document document = null;
        try {
            document = Jsoup.connect(urlPage).get();
        }catch (Exception ex){
            ex.getMessage();
        }
        return document;
    }

    //TODO возвращение map всех аэропортов
    public void fillMapAllAirports(){
        try {
            Document document = returnHtmlPage(urlToMainPageAirports);
            FileWriter fileWriter = new FileWriter(patchToFilesHtml + "Главная Страница.html");
            fileWriter.write(document.toString());

            Elements elements = document.select(".s__aMgHXZZASaSwDP9wIMPx.s__VxY6BmkE3QOJIwvomxXQ.s__Z6pCHtb9k0byQSELy7Av");
            for (Element element : elements){
                String strElement = element.toString();
                if(strElement.indexOf("href=\"/airports\"") != -1){
                    String templateForLinkAllAirports = "href=\"";
                    int startIndexForLinkAllAirports = strElement.indexOf(templateForLinkAllAirports) + templateForLinkAllAirports.length();
                    int endIndexForLinkAllAirports = strElement.indexOf("\"", startIndexForLinkAllAirports);
                    String linkAllAirports = urlToMainPageAirports + strElement.substring(startIndexForLinkAllAirports, endIndexForLinkAllAirports);

                    Document documentForAllAirports = returnHtmlPage(linkAllAirports);
                    FileWriter fileWriterForAllAirports = new FileWriter(patchToFilesHtml + "Аэропорты.html");
                    fileWriterForAllAirports.write(documentForAllAirports.toString());

                    Elements elementsForAllAirports = documentForAllAirports.select(".index-list__item.is-active");
                    for(Element elementsForAllAirport : elementsForAllAirports) {
                        System.out.println("\uD83C\uDCCF" + elementsForAllAirport + "\uD83C\uDCCF");
                    }
                }
            }
        }catch (Exception ex){
            ex.getMessage();
        }
    }



}

package gui;
import data.Project;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HTMLOutput {
    /**
     calculate the number of percentages of the tests
     */
    static int percentageOK(int i,int h){
        return (i/(h*4)) * 100;
    }
    static int percentageKO(int j,int h){
        return (j/(h*4)) * 100;
    }

    public static void render(List<Project> projects) throws IOException {
        int i = 0, j = 0, h = 0;
        StringBuilder html = new StringBuilder();
        html.append("\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"fr\">\n" +
                "<head>\n" +
                "    <meta   lang=\"fr\"  name=\"keywords\" content=\"é, ', è\" />\n" +
                "    <title>page 1</title>\n" +
                "    <style type=\"text/css\">\n" +
                "body{\n" +
                "\tfont-family:arial,sans-serif;\n" +
                "\tbackground:#DCDCDC;\n" +
                "\n" +
                "}\n" +
                "#titre{\n" +
                "\theight:90px;\n" +
                "\twidth:100%;\n" +
                "\tbackground:\t#B0C4DE;\n" +
                "\tmargin-left:auto;\n" +
                "\tmargin-right:auto;\n" +
                "    text-align:center;\n" +
                "\n" +
                "\n" +
                "}\n" +
                "\n" +
                ".page1{\n" +
                "\tposition: absolute;\n" +
                "\twidth:100%;\n" +
                "\theight:100px;\n" +
                "\ttop:0;\n" +
                "\tleft:0;\n" +
                "\tcolor:white;\n" +
                "    -webkit-box-shadow: 0 3px 8px rgba(0,0,0,.25));\n" +
                "\n" +
                "\n" +
                "  }\n" +
                "\n" +
                ".texte{\n" +
                "      font-size:14px;\n" +
                "      text-align: left;\n" +
                "      text-transform: capitalize;\n" +
                "       }\n" +
                "\n" +
                "#footer {\n" +
                "\twidth:100%;\n" +
                "\tmargin-left:auto;\n" +
                "\tmargin-right:auto;\n" +
                "    text-align:center;\n" +
                "    margin-top:159px;\n" +
                "}\n" +
                "#left {\n" +
                "    width:50%;\n" +
                "\tmargin-left:left;\n" +
                "}\n" +
                "#right{\n" +
                "    width:50%;\n" +
                "\tmargin-right:right;\n" +
                "\tmargin-left:auto;\n" +
                "\tmargin-top:-317px;\n" +
                "\tmargin-Bottom:auto;\n" +
                "\n" +
                "}\n" +
                "h1, h2{\n" +
                "color: #191970 ;\n" +
                "}\n" +
                "table {\n" +
                "    border-collapse: collapse;\n" +
                "    width: 100%;\n" +
                "\n" +
                "}\n" +
                "\n" +
                "th, td {\n" +
                "    padding: 8px;\n" +
                "    text-align: center;\n" +
                "    border-bottom: 1px solid #ddd;\n" +
                "}\n" +
                "\n" +
                "tr:hover {background-color:#f5f5f5;}\n" +
                "\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"page1\"></div>\n" +
                "<div id=\"titre\"><br>\n" +
                "    <img src=\"C:\\Users\\hp\\IdeaProjects\\YAPCH\\src\\main\\gui\\logo.jpg\" alt=\"logo\">\n" +
                "    <h1>Java Projects</h1>\n" +
                "    <h2>The results of the different unit tests </h2>\n" +
                "</div>\n" +
                "<br><br><br><br><br><br>\n" +
                "<div id=\"footer\" >\n");
        for (Project e : projects) {
            h++;
            html.append("<h1> the result of the student " + e.getName() + "are represented in this table");

            /** checking the file format  */
            html.append(" <table><tr><th>Tests :</th>" +
                    "<th>Results :</th></tr>" +
                    "<tr><th> the format of the zip file </th>");
            if (e.isCorrect()) {
                i++;
                html.append("<th>&#x2705");
            } else if (!e.isCorrect()) {
                html.append("<th>&#10060");
                j++;
            } else html.append("<th>&#x2753");
            html.append("</th></tr>");
            /**
             * Check if the project compiles
             */
            html.append("<tr><th> the project compiles </th>");
            if (e.isCompiles()) {
                html.append("<th>&#x2705");
                i++;
            } else if (!e.isCompiles()) {
                html.append("<th>&#10060");
                j++;
            } else html.append("<th>&#x2753");
            html.append("</th></tr>");

            /**check if the methods are correct following the test class given by the teacher*/
            html.append("<tr><th>the methods are well declare </th>");
            if (e.getMethodTested() == Project.State.WORKING) {
                html.append("<th>&#x2705");
                i++;
            } else if (e.getMethodTested() == Project.State.BROKEN) {
                html.append("<th>&#10060");
                j++;
            } else if (e.getMethodTested() == Project.State.UNKNOWN) html.append("<th>&#x2753");
            html.append("</th></tr>");

            /**checks if there is javadoc in the code and if it's valid*/
            html.append("<tr><th>the javadoc is in the code and if it's valid</th>");
            if (e.getJavadocTested() == Project.State.AVAILABLE && e.getJavadocTested() == Project.State.WORKING) {
                html.append("<th>&#x2705");
                i++;
            } else if (e.getJavadocTested() == Project.State.BROKEN && e.getJavadocTested() == Project.State.AVAILABLE) {
                html.append("<th>&#10060");
                j++;
            } else if (e.getJavadocTested() == Project.State.NOTAVAILABLE) html.append("<th>&#x2753");
            html.append("</th>\n" +
                    " </tr>");

            /** checks if the unit tests run smoothly*/

        }
            html.append("</table><table><tr><th>percentage of correct answers:</th>" +
                    "<th>percentage of false answers:</th></tr>" +
                    "<tr><th>" + percentageOK(i, h) + "</th>" +
                    "<th>" + percentageKO(j, h) + "</th></tr></table>");
            html.append("</div>\n" +
                    "</body>\n" +
                    "</html>\n");


            File fich = new File("./monfichier.html");
            FileWriter fw = new FileWriter(fich);
            fw.write(html.toString(), 0, html.length());
            fw.flush();
            fw.close();
            Desktop desktop = null;
            java.net.URI url;
            try {
                url = new java.net.URI("monfichier.html");
                if (Desktop.isDesktopSupported()) {
                    desktop = Desktop.getDesktop();
                    desktop.browse(url);
                }
            } catch (Exception ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


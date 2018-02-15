package gui;
import data.Project;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HTMLOutput {
    /**
     calculate the number of percentages of the tests
     */
    static double percentageOK(int i,int h){
        double f=(i/(h*4)) * 100;
        return f;
    }
    static double percentageKO(int j,int h){
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
                "h1, h2{\n" +
                "color: #191970 ;\n" +
                "}\n" +
                "#customers {"+
                "font-family: Arial, Helvetica, sans-serif;"+
                "border-collapse: collapse;"+
                " width: 100%;}"+
                "#customers td, #customers th {"+
                "border: 1px solid #ddd;"+
                "padding: 8px;}\n"+
                "#customers tr:nth-child(even){background-color: #f2f2f2;}"+
                "#customers tr:hover {background-color: #ddd;}"+
                "#customers th {"+
                "padding-top: 12px;"+
                 "padding-bottom: 12px;"+
                 "text-align: left;"+
                 "background-color: #708090;"+
                 "color: white;}"+
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
            html.append(" <table id=\"customers\">\n" +
                    "    <tr>\n" +
                    "        <th>Name Project</th>\n" +
                    "        <th>Zip Format </th>\n" +
                    "        <th>the project compiles </th>\n" +
                    "        <th>the methods are correct </th>\n" +
                    "        <th>existence of javadoc </th>\n" +
                    "        <th>unit Testes </th>\n" +
                    "    </tr>");
            for (Project e : projects) {
            h++;
            html.append("<tr><td>" + e.getName()+"</td>\n");
            if (e.isCorrect()) {
                i++;
                html.append("<td>&#x2705");
            } else if (!e.isCorrect()) {
                html.append("<td>&#10060");
                j++;
            } else html.append("<td>&#x2753");
            html.append("</td>");
            if (e.isCompiles()) {
                html.append("<td>&#x2705");
                i++;
            } else if (!e.isCompiles()) {
                html.append("<td>&#10060");
                j++;
            } else html.append("<td>&#x2753");
            html.append("</td>");
            if (e.getMethodTested() == Project.State.WORKING) {
                html.append("<td>&#x2705");
                i++;
            } else if (e.getMethodTested() == Project.State.BROKEN) {
                html.append("<td>&#10060");
                j++;
            } else if (e.getMethodTested() == Project.State.UNKNOWN) html.append("<td>&#x2753");
            html.append("</td>");
            if (e.getJavadocTested() == Project.State.AVAILABLE && e.getJavadocTested() == Project.State.WORKING) {
                html.append("<td>&#x2705");
                i++;
            } else if (e.getJavadocTested() == Project.State.BROKEN && e.getJavadocTested() == Project.State.AVAILABLE) {
                html.append("<td>&#10060");
                j++;
            } else if (e.getJavadocTested() == Project.State.NOTAVAILABLE) html.append("<td>&#x2753");
            html.append("</td>\n" +
                    " </tr>");
            }
            html.append("</table></div>\n" +
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
     public static void  main(String[] arg) throws IOException {
        Project p=new Project("lamyaa");
         p.setCompiles(false);
         p.setCorrect(false);
         p.setMethodTested(Project.State.WORKING);
         p.setJavadocTested(Project.State.NOTAVAILABLE);
         List<Project> l=new ArrayList<>();
         l.add(p);
         render(l);
     }
    }


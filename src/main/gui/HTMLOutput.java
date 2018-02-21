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
    public static void render(List<Project> projects) throws IOException {
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
                "#customers tr:nth-child(even){background-color: white;}"+
                "#customers tr {background-color: white;}"+
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
            html.append("<tr><td>" + e.getName()+"</td>\n");
            if (e.isCorrect()) {
                html.append("<td>&#x2705");
            } else if (!e.isCorrect()) {
                html.append("<td>&#10060");
            } else html.append("<td>&#x2753");
            html.append("</td>");
            if (e.isCompiles()) {
                html.append("<td>&#x2705");
            } else if (!e.isCompiles()) {
                html.append("<td>&#10060");
            } else html.append("<td>&#x2753");
            html.append("</td>");
            if (e.getMethodTested() == Project.State.WORKING) {
                html.append("<td>&#x2705");
            } else if (e.getMethodTested() == Project.State.BROKEN) {
                html.append("<td>&#10060");
            } else if (e.getMethodTested() == Project.State.UNKNOWN) html.append("<td>&#x2753");
            html.append("</td>");
            if (e.getJavadocTested() == Project.State.WORKING) {
                html.append("<td>&#x2705");
            } else if (e.getJavadocTested() == Project.State.BROKEN) {
                html.append("<td>&#10060");
            } else if (e.getJavadocTested() == Project.State.NOTAVAILABLE) html.append("<td>&#x2753");
            html.append("</td>");
            if (0<=e.getUnitTested() && e.getUnitTested()<=12) {
            html.append("<td><span style=\"color: red;\">"+e.getUnitTested()+"%");
            } else if (90<=e.getUnitTested() && e.getUnitTested()<=100) {
            html.append("<td ><span style=\"color: #7CFC00;\">"+e.getUnitTested()+"%");
            } else if ((50<=e.getUnitTested() && e.getUnitTested()<=90)) {
            html.append("<td ><span style=\"color: #FFFF00;\">" + e.getUnitTested()+"%");
            } else if ((12<=e.getUnitTested() && e.getUnitTested()<=50)) {
            html.append("<td ><span style=\"color: #FF8C00;\">"+e.getUnitTested()+"%");}
            html.append("</td>");
            html.append("</tr>");
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
    }


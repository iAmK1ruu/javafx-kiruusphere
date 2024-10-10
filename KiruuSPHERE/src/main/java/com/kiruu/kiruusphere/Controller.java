package com.kiruu.kiruusphere;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import jdk.jshell.spi.ExecutionControlProvider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Controller  {
    public String fetched_search;
    @FXML
    Button btn_search;
    @FXML
    Pane pane_main, pane_side, pane_search,
            p1, p2, p3, p4, p5, p6, p7, p8, p9, p10;
    @FXML
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
    @FXML
    TextField field_search;
    @FXML
    Label searchname, ad1, ad2, ad3,
            searchname1, ad11, ad21, ad31,
            searchname2, ad12, ad22, ad32,
            searchname3, ad13, ad23, ad33,
            searchname4, ad14, ad24, ad34,
            searchname5, ad15, ad25, ad35,
            searchname6, ad16, ad26, ad36,
            searchname7, ad17, ad27, ad37,
            searchname8, ad18, ad28, ad38,
            searchname9, ad19, ad29, ad39;
    @FXML
    Label label_nosearch, label_noloc;
    @FXML
    public void initialize() throws IOException {
        String savedLocation = RequestWeatherData.readSavedLocation();
        if (savedLocation.equals("NO_SAVED_LOC")) {
            label_noloc.setVisible(true);
            pane_main.setVisible(false);
            pane_side.setVisible(false);
        }
    }
    public void searchAction(ActionEvent e) throws Exception {
        String fetched_text = field_search.getText();
        if (fetched_text.isEmpty() || fetched_text.isBlank()) {
            // INSERT ERROR DIALOG
            return;
        }
        getIndividualSearchResponse(fetched_text);
        label_noloc.setVisible(false);
        pane_main.setVisible(false);
        pane_side.setVisible(false);
        pane_search.setVisible(true);
        pane_search.setDisable(false);
    }
    public void getIndividualSearchResponse(String fetched_text) {
        Label[] searchnames = {
                searchname, searchname1, searchname2, searchname3,
                searchname4, searchname5, searchname6, searchname7,
                searchname8, searchname9
        };

        Label[] ad1arr = {
                ad1, ad11, ad12, ad13,
                ad14, ad15, ad16, ad17,
                ad18, ad19
        };
        Label[] ad2arr = {
                ad2, ad21, ad22, ad23,
                ad24, ad25, ad26, ad27,
                ad28, ad29
        };
        Label[] ad3arr = {
                ad3, ad31, ad32, ad33,
                ad34, ad35, ad36, ad37,
                ad38, ad39
        };
        Pane[] panes = { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10};
        Button[] btns = { b1, b2, b3, b4, b5, b6, b7, b8, b9, b10};
        for (int i = 0; i < 10; i ++) {
            panes[i].setVisible(false);
            btns[i].setVisible(false);
            ad1arr[i].setText("NA");
            ad2arr[i].setText("NA");
            ad3arr[i].setText("NA");
        }
        label_noloc.setVisible(false);
        label_nosearch.setVisible(false);
        String[] searches = Geocode.getJSONResponse(Geocode.getPlace(fetched_text));
        if (searches[0].contains("name")) {
            for (int i = 0; i < searches.length; i++) {
                String[] splits = searches[i].split(",");
                panes[i].setVisible(true);
                btns[i].setVisible(true);
                btns[i].setDisable(false);
                for (int j = 0; j < splits.length; j++) {
                    if (splits[j].contains("name")) {
                        searchnames[i].setText(splits[j].substring(8, splits[j].length() - 1));
                    }
                    if (splits[j].contains("admin1")) {
                        ad1arr[i].setText(splits[j].substring(10, splits[j].length() - 1));
                    }
                    if (splits[j].contains("admin2")) {
                        ad2arr[i].setText(splits[j].substring(10, splits[j].length() - 1));
                    }
                    if (splits[j].contains("admin3")) {
                        if (splits[j].contains("}")) {
                            ad3arr[i].setText(splits[j].substring(10, splits[j].length() - 3));
                        } else {
                            ad3arr[i].setText(splits[j].substring(10, splits[j].length() - 1));
                        }
                    }
                }
            }
        } else {
            label_nosearch.setVisible(true);
        }
    }
    public void selectLocation(ActionEvent e) {
        Button source = (Button) e.getSource();
        int start = source.toString().indexOf("id=") + 4, end = source.toString().indexOf(", "),
        index = Integer.parseInt(source.toString().substring(start, end)) - 1;
        String[] response = Geocode.getJSONResponse(Geocode.getPlace(field_search.getText())), coords = Geocode.getLatLong(response, index), split = response[index].split(",");
        fetched_search = split[1].substring(8, split[1].length() - 1);

        try {
            BufferedWriter coordswriter = new BufferedWriter(new FileWriter("src/main/resources/com/kiruu/kiruusphere/data/pinned.dat"));
            coordswriter.write(coords[0]);
            coordswriter.newLine();
            coordswriter.write(coords[1]);
            coordswriter.newLine();
            coordswriter.write(fetched_search);
            coordswriter.close();
            getWeatherData();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public void getWeatherData() {
        pane_search.setVisible(false);
        pane_main.setVisible(true);
        pane_side.setVisible(true);
    }
}
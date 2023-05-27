package ui;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TextFileParser {
    // misc
    private static final String INFO_SPLITTER_PLACES = "\\[-\\]";

    // REQUIRES: filename must be a valid file name WITHIN the data package
    // EFFECTS: returns a big long string of the text file requested
    public String getTextFromFileInData(String filename) {
        String path = "./data/" + filename;
        try {
            String text = readFile(path, StandardCharsets.UTF_8);
            return text;
        } catch (IOException e) {
            return null;
        }
    }

    // credits to erickson from stackoverflow:
    // https://stackoverflow.com/questions/326390/how-do-i-create-a-java-string-from-the-contents-of-a-file
    // EFFECTS: reads source file as string and returns it
    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    // REQUIRES: the file must have full descriptors, i.e. description, search, etc.
    // and filename must be a valid file name WITHIN the data package
    // and datatype must be d (description) or s (search)
    // EFFECTS: returns a big long string of the text file requested, but only returns the part of the text file
    // requested, specified by the code. For example, if the code requests a description, then this getter will get
    // the entire text file then return the part relevant (the description).
    public String getTextFromFileInDataPlaces(String filename, String datatype) {
        String path = "./data/Places/" + filename;
        try {
            String text = readFile(path, StandardCharsets.UTF_8);
            // splitting text at [-], which is a personal indicator to a split in what the text is showing
            List<String> textList = Arrays.asList(text.split(INFO_SPLITTER_PLACES));
            if (datatype.equals("d")) { // description
                return textList.get(0).substring(0, textList.get(0).length() - 2);
            } else if (datatype.equals("sd")) { // short description
                return textList.get(1).substring(0, textList.get(1).length() - 2);
            } else if (datatype.equals("sni")) { // no item search
                return textList.get(2).substring(0, textList.get(2).length() - 2);
            } else if (datatype.equals("um")) { // unlock message
                return textList.get(3).substring(0, textList.get(3).length() - 2);
            } else {
                return null;
            }

        } catch (IOException e) {
            return null;
        }
    }


    // returns a substring of a file in data/items. The reason for the substring is that the files are split
    // up into various different descriptors for an item
    public String getTextFromFileInDataItems(String filename, String datatype) {
        String path = "./data/Items/" + filename;
        try {
            String text = readFile(path, StandardCharsets.UTF_8);
            // splitting text at [-], which is a personal indicator to a split in what the text is showing
            List<String> textList = Arrays.asList(text.split(INFO_SPLITTER_PLACES));

            if (datatype.equals("inid")) { // initial description
                return (textList.get(0).substring(0, textList.get(0).length() - 2));
            } else if (datatype.equals("bd")) { // basic description
                return textList.get(1).substring(0, textList.get(1).length() - 2);
            } else if (datatype.equals("pum")) { // pick up message
                return textList.get(2).substring(0, textList.get(2).length() - 2);
            } else if (datatype.equals("invd")) { // inventory description
                return textList.get(3).substring(0, textList.get(3).length() - 2);
            } else {
                return null;
            }

        } catch (IOException e) {
            return null;
        }
    }
}

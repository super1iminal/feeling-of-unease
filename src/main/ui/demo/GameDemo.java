package ui.demo;

import model.EventLog;
import model.Player;
import model.World;
import model.items.Item;
import model.items.Key;
import model.containers.Place;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.TextFileParser;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Creates objects for, implements methods for and runs a game, using most of the classes defined in models. More
// Information below
public class GameDemo {
    // CONSTANTS
    // commands
    private static final List<String> TAKE_COMMANDS_LIST = Arrays.asList("take", "grab", "pick up");
    private static final List<String> DROP_COMMANDS_LIST = Arrays.asList("drop");
    private static final List<String> SEARCH_INVENTORY_COMMANDS_LIST = Arrays.asList("search inventory", "inventory");
    private static final List<String> LOOK_AROUND_COMMANDS_LIST = Arrays.asList("look around", "look", "inspect");
    private static final List<String> MOVE_COMMANDS_LIST = Arrays.asList("move", "go");
    private static final List<String> SEARCH_COMMANDS_LIST = Arrays.asList("search", "look for items");
    private static final List<String> UNLOCK_COMMANDS_LIST = Arrays.asList("unlock");
    private static final List<String> YES_COMMANDS_LIST = Arrays.asList("yes", "y", "yeah", "ye");
    private static final List<String> NO_COMMANDS_LIST = Arrays.asList("no", "n", "nah", "na");
    private static final List<String> USE_COMMANDS_LIST = Arrays.asList("use");


    // PLACES
    // temp
    private static final String ITEM_HOLDER_TEMP_JSON_CODE = "00000";
    // street
    private static final String STREET_JSON_CODE = "00001";
    private static final int STREET_LOCK_CODE = 0001;
    // house
    private static final int HOUSE_LOCK_CODE = 0002;
    private static final String HOUSE_JSON_CODE = "00002";
    // house porch main
    private static final String HOUSE_PORCH_MAIN_JSON_CODE = "00003";
    private static final int HOUSE_PORCH_MAIN_LOCK_CODE = 0003;
    // house porch

    // KEYS
    private static final String HOUSE_KEY_NAME = "silver key";

    // PLAYER
    private static final String PLAYER_JSON_LOCATION = "99999";

    // MISC
    private static final String JSON_STORE = "./data/saves/savefile.json";


    // icons
    private static final ImageIcon MAN_WALKING_ICON = new ImageIcon("data/images/manwalking.png");
    private static final ImageIcon MAN_SEARCHING_ICON = new ImageIcon("data/images/mansearching.png");
    private static final ImageIcon MAN_LOOKING_ICON = new ImageIcon("data/images/manlooking.png");
    private static final ImageIcon MAN_HAPPY_ICON = new ImageIcon("data/images/manhappy.png");
    private static final ImageIcon MAN_SAD_ICON = new ImageIcon("data/images/mansad.png");
    private static final ImageIcon MAN_IDLE_ICON = new ImageIcon("data/images/manidle.png");




    // FIELDS
    // panels
    private JFrame frame;
    private JTabbedPane tabbedPanes;
    private JPanel gamePanel;
    private JPanel leftPanel;
    private JPanel topLeftPanel;
    private JPanel bottomLeftPanel;
    private JPanel bottomLeftPanelCheats;
    private JPanel directionalPanel;
    private JPanel inventoryPanel;
    private JPanel itemsPanel;
    private JPanel rightPanel;
    private JPanel topRightPanel;
    private JScrollPane scrollableTopRightPane;
    private JPanel bottomRightPanel;
    private JPanel mainPanel;
    private JPanel bottomLeftPanelNoCheats;

    // buttons
    // these are all in bottomLeftPanel
    private JButton grabButton;
    private JButton dropButton;
    private JButton cheatsButton;

    //cheats
    private boolean cheats = false;

    // caret for text panel
    DefaultCaret caret;
/*
    private JButton northButton;
    private JButton southButton;
    private JButton eastButton;
    private JButton westButton;
*/
    // options pane
    private JPanel optionsPanel;
    private JButton saveButton;
    private JButton loadButton;
    private JButton quitButton;

    // labels
    private JLabel topLeftLabel;

    // text fields
    private JTextField userInputField;

    // text areas
    private JTextArea itemsText;
    private JTextArea outputTextArea;

    // misc
    private boolean hasEnteredName = false;

    // world
    private World world;

    // ----- CREATING PLACES, ITEMS and CONTAINERS
    // places
    Place street;
    Place housePorchMain;
    Place house;
    Place tempItemHolder;

    // items
    Key houseKey;

    // misc
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs game
    public GameDemo() throws IOException {
        runGame();
    }

    private TextFileParser textFileParser;

    // MODIFIES: this
    // EFFECTS: initializes and starts game using default settings
    // there is no loop, as the true loops are the ActiveListeners buried within the graphics initialization
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void runGame() throws IOException {
        boolean running = true;
        initializeGraphics();
        noLoadInit();

/*
        // scanner
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        String command;
*/
/*        while (running) {
            System.out.println();
            command = lowerAndTrimCommand(input.next());
            if (command.equals("quit")) {
                running = false;
                System.out.println("Save and quit? y/n");
                command = input.next();
                if (YES_COMMANDS_LIST.contains(command)) {
                    saveGame();
                }
            } else {
                processCommand(command);
            }
        }*/
    }

    // EFFECTS: this is a long one. jesus. creates a GUI with the following specs:
    // There are two tabs: game, and inventory. Game is where the game is played, and inventory is a list of all
    // items in the inventory. The Game panel contains three buttons at the top of the screen: save, load, quit
    // The game panel contains an icon of a lil' dude on the top left, and on the bottom left has two buttons: grab and
    // drop, which are, of course, functional. On the right side of the game panel is a text output box titled terminal
    // and a text input box with no title. The entire right side effectively acts as a console and listens for any
    // commands being entered
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void initializeGraphics() {
        frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
                quit();
            }
        });
        frame.setTitle("Adventure");
        frame.setLocationRelativeTo(null);
        frame.setSize(1300, 800);
        frame.setLayout(new BorderLayout(5, 5));
        frame.setBackground(Color.LIGHT_GRAY);

        // the rightmost panel will contain text terminal
        // the left bottom panel will contain directional commands
        // the left top panel will contain visual effects
        // settings can be accessed using tab manager
        //  settings will be able to save and load
        // inventory can be accessed using tab manager

        tabbedPanes = new JTabbedPane();

        mainPanel = new JPanel(new BorderLayout());

        // start of mainPanel
        gamePanel = new JPanel(new BorderLayout(10, 5)); // there are 5 locations: north, south, etc.
        //                                                                      places for components
        gamePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        gamePanel.setLayout(new GridLayout(1, 2));



        // start of menu
        optionsPanel = new JPanel();

        saveButton = new JButton("save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });
        saveButton.setEnabled(false); // is set to true after name is inputted

        loadButton = new JButton("load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadInit();
            }
        });

        quitButton = new JButton("quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });

        cheatsButton = new JButton("cheats: off");
        cheatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cheatSwitch();
            }
        });
        cheatsButton.setEnabled(false);

        optionsPanel.add(saveButton);
        optionsPanel.add(loadButton);
        optionsPanel.add(quitButton);
        optionsPanel.add(cheatsButton);



        // end of menu

        // start of leftPanel
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2, 1));

        // start of top left panel
        topLeftPanel = new JPanel();
        topLeftLabel = new JLabel(MAN_IDLE_ICON);
        topLeftLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        topLeftLabel.setVerticalTextPosition(SwingConstants.BOTTOM);

        topLeftPanel.add(topLeftLabel);
        // end of top left panel


/*        bottomLeftPanel.setLayout(new BorderLayout());

        // four directional buttons for bottomLeftPanel
        northButton = new JButton("north");
        northButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeMove("north");
            }
        });

        southButton = new JButton("south");
        southButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeMove("south");
            }
        });
        eastButton = new JButton("east");
        eastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeMove("east");
            }
        });
        westButton = new JButton("west");
        westButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeMove("west");
            }
        });
        */
/*        // panel for inside the bottom left panel center
        directionalPanel = new JPanel();
        directionalPanel.setLayout(new BorderLayout());
        directionalPanel.setBackground(Color.BLACK);
        bottomLeftLabel = new JLabel("Directional Movement");
        bottomLeftLabel.setForeground(Color.WHITE);
        bottomLeftLabel.setFont(new Font("Sans-serif", Font.BOLD, 20));
        bottomLeftLabel.setHorizontalAlignment(SwingConstants.CENTER);
        directionalPanel.add(bottomLeftLabel, BorderLayout.CENTER);
        // end of directional panel*/

        // REMEMBeR TO BUTTON.SETENABLED(FALSE) AFTER A COMMAND HAS BEEN INPUTTED OR SOMETHING
/*        bottomLeftPanelGrabAndDrop.add(northButton, BorderLayout.NORTH);
        bottomLeftPanelGrabAndDrop.add(southButton, BorderLayout.SOUTH);
        bottomLeftPanelGrabAndDrop.add(westButton, BorderLayout.WEST);
        bottomLeftPanelGrabAndDrop.add(eastButton, BorderLayout.EAST);*/

        // start of bottomLeftPanel
        bottomLeftPanel = new JPanel();

        // start of bottomLeftPanelNoCheats
        bottomLeftPanelNoCheats = new JPanel();
        bottomLeftPanelNoCheats.add(new JLabel(new ImageIcon("data/images/pixel_stars.gif")));
        // end of bottomleftPanelNoCheats

        // start of bottomLeftPanelCheats
        bottomLeftPanelCheats = new JPanel();
        bottomLeftPanelCheats.setLayout(new GridLayout(1, 2));
        bottomLeftPanelCheats.setBorder(BorderFactory.createTitledBorder("Cheat Menu"));
        grabButton = new JButton("grab");
        grabButton.setPreferredSize(new Dimension(100, 100));
        grabButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!world.placeIsEmpty(world.getPlayerJsonLocation())) {
                    JFrame tempFrame = new JFrame();
                    tempFrame.setTitle("Choose item to take!");
                    tempFrame.setLocationRelativeTo(frame);
                    tempFrame.setSize(new Dimension(100,
                            100 * (world.getPlaceItems(world.getPlayerJsonLocation()).size())));
                    JPanel tempPanel = new JPanel();
                    tempPanel.setLayout(new GridLayout(0, 1));
                    for (Item item : world.getPlaceItems(world.getPlayerJsonLocation())) {
                        JButton itemButton = new JButton(item.getName());
                        itemButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                tempFrame.dispose();
                                executeGrab(item.getName());
                            }
                        });
                        tempPanel.add(itemButton);
                    }
                    tempFrame.add(tempPanel);
                    tempFrame.setVisible(true);
                } else {
                    executeSearch(); // since this only occurs if nothing is in current place,
                    // this will print the place's message that nothing is here
                }
            }
        });

        dropButton = new JButton("drop");
        dropButton.setPreferredSize(new Dimension(20, 20));
        dropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!world.playerInventoryIsEmpty()) {
                    JFrame tempFrame = new JFrame();
                    tempFrame.setTitle("Choose item to drop!");
                    tempFrame.setLocationRelativeTo(frame);
                    tempFrame.setSize(new Dimension(100,
                            100 * (world.getPlayerItems().size())));
                    JPanel tempPanel = new JPanel();
                    tempPanel.setLayout(new GridLayout(0, 1));
                    for (Item item : world.getPlayerItems()) {
                        JButton itemButton = new JButton(item.getName());
                        itemButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                tempFrame.dispose();
                                executeDrop(item.getName());
                            }
                        });
                        tempPanel.add(itemButton);
                    }
                    tempFrame.add(tempPanel);
                    tempFrame.setVisible(true);
                } else {
                    executeInventorySearch(); // since this only occurs if nothing is in inventory,
                    // this will print the inventory's message that it is empty
                }
            }
        });

        bottomLeftPanelCheats.add(grabButton);
        bottomLeftPanelCheats.add(dropButton);
        // end of bottomLeftPanelCheats

        bottomLeftPanel.add(bottomLeftPanelNoCheats);

        leftPanel.add(topLeftPanel);
        leftPanel.add(bottomLeftPanel);
        // end of leftPanel


        // start of rightPanel
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(new TitledBorder(new EtchedBorder(), "terminal"));


        // start of topRightPanel
        topRightPanel = new JPanel();

        outputTextArea = new JTextArea(0, 40);
        caret = (DefaultCaret) outputTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        outputTextArea.setEditable(false);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setBackground(Color.LIGHT_GRAY);

        topRightPanel.add(outputTextArea, BorderLayout.CENTER);



        // end of topRightPanel

        // start of scrollable top right pane
        scrollableTopRightPane = new JScrollPane(topRightPanel);
        scrollableTopRightPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableTopRightPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableTopRightPane.setWheelScrollingEnabled(true);
        scrollableTopRightPane.setAutoscrolls(true);

        // start of bottomRightPanel
        bottomRightPanel = new JPanel();

        // text field with active listener
        userInputField = new JTextField(30);
        userInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = userInputField.getText();
                userInputField.setText("");
                printToGUI("\n>> " + userInput);
                try {
                    processCommand(userInput);
                } catch (IOException ex) {
                    // do nothing
                }
            }
        });

        bottomRightPanel.add(userInputField);
        // end of bottomRightPanel

        rightPanel.add(scrollableTopRightPane, BorderLayout.CENTER);
        rightPanel.add(bottomRightPanel, BorderLayout.SOUTH);
        // end of rightPanel

        gamePanel.add(leftPanel);
        gamePanel.add(rightPanel);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(optionsPanel, BorderLayout.NORTH);

        // end of mainPanel

        // start of inventory panel
        inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new BorderLayout());
        JLabel inventoryLabel = new JLabel("Inventory");
        inventoryLabel.setFont(new Font("Sans-serif", Font.TRUETYPE_FONT, 30));
        inventoryPanel.add(inventoryLabel, BorderLayout.NORTH);

        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BorderLayout());
        itemsText = new JTextArea();
        itemsText.setFont(new Font("Comic-Sans-MS", Font.BOLD, 20));
        itemsText.setEditable(false);
        itemsPanel.add(itemsText, BorderLayout.CENTER);

        inventoryPanel.add(itemsPanel);


        tabbedPanes.add("Game", mainPanel);
        tabbedPanes.add("Inventory", inventoryPanel);

        frame.add(tabbedPanes, BorderLayout.CENTER); // automatically added to center but good to specify
        frame.setVisible(true);
        // end of frame
    }

    private void cheatSwitch() {
        if (cheats) {
            cheatsButton.setText("cheats: off");
            cheats = false;
            frame.setVisible(false);
            bottomLeftPanel.remove(bottomLeftPanelCheats);
            bottomLeftPanel.add(bottomLeftPanelNoCheats, BorderLayout.CENTER);
            bottomLeftPanel.validate();
            frame.setVisible(true);
        } else {
            cheatsButton.setText("cheats: on");
            cheats = true;
            frame.setVisible(false);
            bottomLeftPanel.remove(bottomLeftPanelNoCheats);
            bottomLeftPanel.add(bottomLeftPanelCheats, BorderLayout.CENTER);
            bottomLeftPanel.validate();
            frame.setVisible(true);
        }
    }

    // REQUIRES: none
    // MODIFIES: this, player, place, container (when containers are added to demo)
    // EFFECTS: basically initializes game, and the whole point of this method is its modularity.
    // For example, one can simply add more places, connect them here, and therefore expand the game. For this reason,
    // it's difficult to give a concrete EFFECTS specification, however, this method basically creates a new player,
    // based on an inputted name, then creates all relevant objects, such as places, containers, scanners, items,
    // keys, and players, and connects them in all the relevant ways, such as linking two locations, or adding an item
    // to a place
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void strippedInit() {

        // world
        world = new World();

        // text file reader
        textFileParser = new TextFileParser();

        // json writer and reader
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        // places
        street = new Place(
                "street",
                textFileParser.getTextFromFileInDataPlaces("street.txt", "d"),
                textFileParser.getTextFromFileInDataPlaces("street.txt", "sd"),
                textFileParser.getTextFromFileInDataPlaces("street.txt", "sni"),
                textFileParser.getTextFromFileInDataPlaces("street.txt", "um"),
                STREET_LOCK_CODE,
                STREET_JSON_CODE
        );
        housePorchMain = new Place(
                "house porch",
                textFileParser.getTextFromFileInDataPlaces("house_porch_main.txt", "d"),
                textFileParser.getTextFromFileInDataPlaces("house_porch_main.txt", "sd"),
                textFileParser.getTextFromFileInDataPlaces("house_porch_main.txt", "sni"),
                textFileParser.getTextFromFileInDataPlaces("house_porch_main.txt", "um"),
                HOUSE_PORCH_MAIN_LOCK_CODE,
                HOUSE_PORCH_MAIN_JSON_CODE
        );
        house = new Place(
                "house",
                textFileParser.getTextFromFileInDataPlaces("house.txt", "d"),
                textFileParser.getTextFromFileInDataPlaces("house.txt", "sd"),
                textFileParser.getTextFromFileInDataPlaces("house.txt", "sni"),
                textFileParser.getTextFromFileInDataPlaces("house.txt", "um"),
                HOUSE_LOCK_CODE,
                HOUSE_JSON_CODE
        );
        tempItemHolder = new Place(
                "temp",
                "temp item holder",
                "temp item holder",
                "temp item holder",
                "temp item holder",
                1234,
                ITEM_HOLDER_TEMP_JSON_CODE // this is the only important part of the temp item holder
        );

        // adding places to placesList - IMPORTANT ------ DON'T FORGET
        world.addPlace(street);
        world.addPlace(house);
        world.addPlace(housePorchMain);
        world.addPlace(tempItemHolder);

        // items
        // keys
        houseKey = new Key(
                HOUSE_KEY_NAME,
                textFileParser.getTextFromFileInDataItems("silver_key.txt", "inid"),
                textFileParser.getTextFromFileInDataItems("silver_key.txt", "bd"),
                textFileParser.getTextFromFileInDataItems("silver_key.txt", "pum"),
                textFileParser.getTextFromFileInDataItems("silver_key.txt", "invd"),
                HOUSE_LOCK_CODE,
                street); // note that items are automatically added to their initial locations, and those locations
        //                  have the item added to their inventories
        // adding items to item list
        world.addItem(houseKey);

        // ----- EDITING PLaCES
        // connecting locations
        world.setDirection("north", street, housePorchMain, true);
        world.setDirection("north", housePorchMain, house, true);

        // setting required items

    }

    // uses a previous JSON file to change the state of the game.
    // may be used at any time during the game (i.e. not only at startup)
    private void loadInit() {
        saveButton.setEnabled(true);
        cheatsButton.setEnabled(true);
        topLeftLabel.setIcon(MAN_IDLE_ICON);
        hasEnteredName = true;

        if (world == null) {
            strippedInit();
        }
        try {
            world = jsonReader.read((world));
        } catch (IOException ioe) {
            printToGUI("There was a problem with loading");
        }
        printToGUI("Loaded " + world.getPlayerName() + "'s game from " + JSON_STORE);
        topLeftLabel.setText(world.getPlayerName());
        itemsText.setText(world.getContentsPlayerInventory());

        printToGUI(world.getPlaceShortDescription(world.getPlayerJsonLocation()));
    }

    // MODIFIES: this
    // EFFECTS: sets up the initial locations and values of all things in the world
    // is used once when the game first boots up
    private void noLoadInit() {
        strippedInit();
        // ----- PLAYER
        // creating
        printToGUI("What would you like your name to be?");
        Player player = new Player("PlaceHolder");
        // setting player in world
        world.setPlayer(player);

        // setting player location
        world.setPlayerLocationFromJsonCode(STREET_JSON_CODE);

        // setting containers locked
        world.setContainerLocked(house, true);

        itemsText.setText(world.getContentsPlayerInventory());
    }


    // EFFECTS: prints the help text file
    private void help() {
        topLeftLabel.setIcon(MAN_IDLE_ICON);
        printToGUI(textFileParser.getTextFromFileInData("help.txt"));
    }

    // MODIFIES: this
    // EFFECTS: this method produces various outcomes based on the command, ranging from one-word commands to two-word
    // commands. Prints invalid command if the command is one word and does not match any others
    private void processCommand(String command) throws IOException {
        String[] splitCommandTransient = command.split(" ");
        List<String> splitCommand = Arrays.asList(splitCommandTransient);
        boolean processedSimpleCommand = processSimpleCommand(command);
        if (!(processedSimpleCommand) && (splitCommand.size() > 1)) {
            if ((processComplexCommand(splitCommand, command))) {
                return;
            } else {
                printToGUI("Invalid Command");
            }
        } else if (!processedSimpleCommand) {
            printToGUI("Invalid Command");
        }

    }

    // processes a simple command
    // can process one of: search, search inventory, look, help, save, load or quit
    // also changes lil' boy's mood
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private boolean processSimpleCommand(String command) throws IOException {
        if (!hasEnteredName) {
            world.setPlayerName(command);
            topLeftLabel.setText(command);
            hasEnteredName = true;
            saveButton.setEnabled(true);
            cheatsButton.setEnabled(true);
            printToGUI("Type 'help' for help.");
            printToGUI(world.getPlaceDescription(world.getPlayerJsonLocation()));
            return true;
        } else {
            if (command.equals("help")) {
                help();
            } else if (SEARCH_COMMANDS_LIST.contains(command)) {
                executeSearch();
            } else if (SEARCH_INVENTORY_COMMANDS_LIST.contains(command)) {
                executeInventorySearch();
            } else if (LOOK_AROUND_COMMANDS_LIST.contains(command)) {
                topLeftLabel.setIcon(MAN_LOOKING_ICON);
                printToGUI(world.getPlayerLocation().getDescription());
            } else if (command.equals("save")) {
                saveGame();
            } else if (command.equals("load")) {
                loadInit();
            } else {
                topLeftLabel.setIcon(MAN_IDLE_ICON);
                return false;
            }
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: this method has various outcomes based on the command of the user. It parses two-word commands. prints
    // invalid command if the command could not be understood
    // can process one of: tale, drop, move, unlock
    // also changes lil boy's mood
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private boolean processComplexCommand(List<String> commandList, String command) {
        // two word commands
        String lesserCommand = commandList.get(0);
        String greaterCommand = commandList.get(0) + " " + commandList.get(1);
        List<String> listOfCommandAndGCommand = Arrays.asList(greaterCommand, lesserCommand); // greater command
        // must come before command since if a word in smaller command is in a bigger command then
        // it'll interpret that as being the entire command
        for (String commandOrGCommand : listOfCommandAndGCommand) {
            if (TAKE_COMMANDS_LIST.contains(commandOrGCommand)) {
                String valueOfCommand = command.substring(commandOrGCommand.length() + 1);
                executeGrab(valueOfCommand);
                return true;
            } else if (DROP_COMMANDS_LIST.contains(commandOrGCommand)) {
                String valueOfCommand = command.substring(commandOrGCommand.length() + 1);
                executeDrop(valueOfCommand);
                return true;
            } else if (MOVE_COMMANDS_LIST.contains(commandOrGCommand)) {
                String valueOfCommand = command.substring(commandOrGCommand.length() + 1);
                executeMove(valueOfCommand);
                return true;
            } else if (UNLOCK_COMMANDS_LIST.contains(commandOrGCommand)) {
                String valueOfCommand = command.substring(commandOrGCommand.length() + 1);
                executeUnlock(valueOfCommand);
                return true;
            }
        }
        return false;
    }

    // EFFECTS: this executes a search of the location the player is at
    private void executeSearch() {
        topLeftLabel.setIcon(MAN_SEARCHING_ICON);
        printToGUI(world.getPlaceItemsDescription(world.getPlayerJsonLocation()));
    }

    // EFFECTS: this executes of a search of the player's inventory. If the player has nothing in their inventory,
    // it prints a message saying such.
    private void executeInventorySearch() {
        topLeftLabel.setIcon(MAN_SEARCHING_ICON);
        String contents = world.getContentsPlayerInventory();
        if (!contents.equals("")) {
            printToGUI(contents);
        } else {
            printToGUI("\n You have nothing in your inventory\n");
        }
    }


    // MODIFIES: this
    // EFFECTS: unlocks a location if the player has the relevant key in their inventory. prints unlocked if unlocked,
    // prints a could not unlock message if not.
    private void executeUnlock(String location) {
        if (world.playerUnlockPlace(location)) {
            topLeftLabel.setIcon(MAN_HAPPY_ICON);
            printToGUI(world.getPlaceUnlockMessage(world.getPlaceJsonFromName(location)));
        } else {
            topLeftLabel.setIcon(MAN_SAD_ICON);
            printToGUI("You could not unlock " + world.getPlaceName(world.getPlaceJsonFromName(location)));
        }
    }


    // Big commands


    // MODIFIES: this
    // EFFECTS: grabs an item if the item can be found, and prints a message. If not, prints a different message.
    private void executeGrab(String itemName) {
        if (world.placeContains(world.getPlayerJsonLocation(), itemName)) {
            topLeftLabel.setIcon(MAN_HAPPY_ICON);
            world.moveItem(itemName, PLAYER_JSON_LOCATION);
            printToGUI(world.getItemPickUpMessage(itemName));
            itemsText.setText(world.getContentsPlayerInventory());
        } else {
            topLeftLabel.setIcon(MAN_SAD_ICON);
            printToGUI(itemName + " could not be found");
        }
    }

    // TODO: implement a blocker that prevents the player from dropping an item if it's in a required place
    // MODIFIES: this
    // EFFECTS: drops an item if the item can be found, and prints a message and drops the item in the place where the
    // player currently is. If not, it prints a different message.
    private void executeDrop(String itemName) {
        if (world.placeContains(PLAYER_JSON_LOCATION, itemName)) {
            topLeftLabel.setIcon(MAN_SEARCHING_ICON);
            world.moveItem(itemName, world.getPlayerJsonLocation());
            printToGUI("\n" + itemName + " dropped");
            itemsText.setText(world.getContentsPlayerInventory());
        } else {
            topLeftLabel.setIcon(MAN_IDLE_ICON);
            printToGUI("\n" + itemName + " could not be dropped");
        }
    }

    // MODIFIES: this
    // EFFECTS: attempts to move the player to the designated location. If the location cannot be found,
    // prints a message saying that. If it can, but the player can't get to it, it prints a message saying that.
    // If it can be found, and the player can move there, prints a message saying that and moves the player
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void executeMove(String direction) {
        boolean moved = false;
        if (Arrays.asList("north", "south", "east", "west").contains(direction)) {
            if (world.isLocationInCardinal(direction)) {
                if (!world.isLockedInCardinal(direction)) {
                    if (world.isRequiredItemInCardinal(direction)) {
                        if (world.playerHasRequiredItemInCardinal(direction)) {
                            moved = true;
                        } else {
                            world.setPlayerDead(true);
                            printToGUI("\n" + "You died...");
                            return;
                        }
                    } else {
                        moved = true;
                    }
                } else {
                    printToGUI("That direction is locked");
                }
            } else {
                printToGUI("There is no passage that way");
            }
        } else {
            printToGUI("You need to enter a cardinal direction");
        }
        if (moved) {
            topLeftLabel.setIcon(MAN_WALKING_ICON);
            if (world.hasPlaceBeenVisited(world.getJsonCodeFromCardinal(direction))) {
                printToGUI(world.getPlaceShortDescription(world.getJsonCodeFromCardinal(direction)));
            } else {
                printToGUI(world.getPlaceDescription(world.getJsonCodeFromCardinal(direction)));
            }
            world.movePlayerInCardinal(direction);
        } else {
            topLeftLabel.setIcon(MAN_IDLE_ICON);
        }
    }

    // MODIFIES: this
    // EFFECTS: will combine two items in a player's inventory if possible.
    public void executeCombineItems(List<String> splitcommand) {
        // TODO do this shit
    }


/*    // EFFECTS: returns a lowered and trimmed (removed excess whitespace around string) string
    private String lowerAndTrimCommand(String command) {
        return command.toLowerCase().trim();
    }*/
/*

    // EFFECTS: This prints out every sentence with a specific number of milliseconds in between each
    // word, after every period and after every comma. The input meant pretty much to be a raw text file
    // converted entirely to string, although it can be anything. It splits the String into a list, split at every
    // period, then executes a for loop on every sentence. The problem is, in a raw text file, there are newlines,
    // and I'd like to preserve the location of the newlines, but I also later split words up by spaces in between
    // them, and since a linebreak doesn't count as a space, it is simply ignored and the code thinks that the word
    // before the linebreak and after are both one word. So, I replace linebreakl with "[n] ", so that I can later
    // replace it again with the linebreak, but I now have a space in between the words. Great!
    private void printSlowly(String string) {
        List<String> listOfSentences = new ArrayList<>(Arrays.asList(string.split("\\.")));
        for (String sentence : listOfSentences) {
            sentence = sentence.replace("\n", "[n] ");
            List<String> words = Arrays.asList(sentence.split(" "));
            printSentenceSlowly(words);

            if (!(sentence.endsWith("!") || sentence.endsWith("?"))) {
                System.out.print(".");
            }
            sleep(600);
        }
        // linebreak after all is printed
        System.out.println();
    }
*/

    // REQUIRES: the textArea this appends to must have line-wrapping and word-wrapping enabled
    // this prints an entire string to the gui, starting and ending with a linebreak
    // THIS REMOVES ALL LINEBREAKS
    private void printToGUI(String string) {
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        outputTextArea.append("\n");
        List<String> listOfSentences = new ArrayList<>(Arrays.asList(string.split("\\.")));
        for (String sentence : listOfSentences) {
            sentence = sentence.replace("\n", " ");
            List<String> words = Arrays.asList(sentence.split(" "));
            printSentenceToGUI(words);

            if (!(sentence.endsWith("!") || sentence.endsWith("?"))) {
                outputTextArea.append(".");
            }
//            sleep(600);
        }
        // linebreak after all is printed
        outputTextArea.append("\n");
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

/*
    // EFFECTS: this method is just a helper method for the previous one, and prints every word of every sentence
    // individually, based on their length. This is also the method that adds breaks in between every word and at
    // commas and periods. As one can see, a list of words is provided in the printSlowly function that feeds into
    // this one, and those words might have the [n] character, so we must replace that back with a linebreak in
    // every word, since its usage has now passed (all words have been split up by spaces). Note that this method
    // only sleeps after a word if it isn't the last word in the sentence, and it also only prints a space between words
    // if there wasn't a linebreak, as that would add an extraneous space at the next line.
    private void printSentenceSlowly(List<String> words) {
        for (int i = 0; i < words.size(); i++) {
            // setting up variables;
            String word = words.get(i);
            String newLineWord = word.replace("[n]", "\n");
            int wordLength = newLineWord.length();
            // print word
            System.out.print(newLineWord);
            // sleep after word (enough time to read it)
            if (!(i == words.size() - 1)) {
                sleep((long) (1.7 * (Math.pow(wordLength - 3.5, 2) + 120)));
                if (!word.contains("[n]")) {
                    System.out.print(" ");
                }
            }
            // extra sleep if word ends with a comma
            if (newLineWord.endsWith(",")) {
                sleep(400);
            }
        }
    }
*/
    // this is the true heart of the printer machine, and appends every individual word, mainting sentence structure
    // and punctuation
    private void printSentenceToGUI(List<String> words) {
        for (int i = 0; i < words.size(); i++) {
            // setting up variables;
            String word = words.get(i);
            String newLineWord = word.replace("[n]", "\n");
            int wordLength = word.length();
            // print word
            outputTextArea.append(word);
            // sleep after word (enough time to read it)
            if (!(i == words.size() - 1)) {
//                sleep((long) (1.7 * (Math.pow(wordLength - 3.5, 2) + 120)));
                if (!word.contains("[n]")) {
                    outputTextArea.append(" ");
                }
            }
            // extra sleep if word ends with a comma
            if (word.endsWith(",")) {
//                sleep(400);
            }
        }
    }

/*    // REQUIRES: milliseconds >= 0
    // EFFECTS: function to sleep and catch InterruptedException errors in one.
    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }*/

    // EFFECTS: quits and closes application
    // asks the user if they want to save in a new frame
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void quit() {
        JFrame saveFrame = makeQuitFrame();
        saveFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
                printLog();
                System.exit(0);
            }
        });
        JPanel savePanel = new JPanel();
        savePanel.setLayout(new GridLayout(1, 2));
        JButton yesButton = new JButton("yes");
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
                printLog();
                System.exit(0);
            }
        });
        if (!hasEnteredName) {
            yesButton.setEnabled(false);
        }
        JButton noButton = new JButton("no");
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printLog();
                System.exit(0);
            }
        });
        savePanel.add(yesButton);
        savePanel.add(noButton);
        saveFrame.add(savePanel);
        saveFrame.setVisible(true);
    }

    private void printLog() {
        for (model.Event e : EventLog.getInstance()) {
            System.out.println(e.getDescription() + " ||||| " + e.getDate());
        }
    }

    private JFrame makeQuitFrame() {
        JFrame saveFrame = new JFrame();
        saveFrame.setTitle("Save Game?");
        saveFrame.setLayout(new BorderLayout());
        saveFrame.setLocationRelativeTo(null);
        saveFrame.setSize(new Dimension(500, 100));
        return saveFrame;
    }



    /*
     *
     *
     * SAVING STUFF
     *
     *
     * */

    // EFFECTS: saves the game to a JSON file
    // saves the state of all items, locations and the state of the player
    private void saveGame() {
        topLeftLabel.setIcon(MAN_IDLE_ICON);
        try {
            jsonWriter.open();
            jsonWriter.write(world);
            jsonWriter.close();
            printToGUI("Saved " + world.getPlayerName() + "'s game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            printToGUI("Unable to write to file " + JSON_STORE);
        }
    }

    /*
    *
    *
    * GUI stuff
    *
    * */

}

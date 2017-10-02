package com.watabou.pixeldungeon.story;

import android.os.Environment;
import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.actors.mobs.npcs.Mentor;
import com.watabou.pixeldungeon.actors.mobs.npcs.NPC;
import com.watabou.pixeldungeon.actors.mobs.npcs.Relative;
import com.watabou.pixeldungeon.actors.mobs.npcs.Wizard;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.quests.Quest;
import com.watabou.pixeldungeon.quests.QuestObjective;
import com.watabou.pixeldungeon.scenes.OverworldScene;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

public class DataHandler {

    private static DataHandler generateData;

    private DataHandler() {
    }

    public static DataHandler getInstance() {
        if (generateData == null) {
            generateData = new DataHandler();
        }
        return generateData;
    }

    public ArrayList<Quest> actOneQuests = new ArrayList<>();
    public ArrayList<Quest> actTwoQuests = new ArrayList<>();
    public ArrayList<Quest> actThreeQuests = new ArrayList<>();

    public boolean actStarting = false;
    public boolean actComplete = false;

    public ArrayList<Quest> givenQuests = new ArrayList<>();
    public ArrayList<Quest> questList = new ArrayList<>();

    public int currentAct = 1;

    StoryGenerator storyGenerator = StoryGenerator.getInstance();

    public void nextAct() {
        currentAct++;
    }

    public void createActQuests(String xmlFile, ArrayList<Quest> currentQuestList) {
        try {

            //get location of XML file
            String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Data/";
            File file = new File(fullPath, xmlFile);

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(file);

            doc.getDocumentElement().normalize();

            //get list of quests in the Act
            NodeList questList = doc.getElementsByTagName("quest");

            for (int i = 0; i < questList.getLength(); i++) {
                Node curQuestNode = questList.item(i);

                //obtain quest elements for each quest in the list
                if (curQuestNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element questElement = (Element) curQuestNode;

                    String questName = questElement.getElementsByTagName("quest_name").item(0).getTextContent();
                    String questGiver = questElement.getElementsByTagName("quest_giver").item(0).getTextContent();
                    String questReward = questElement.getElementsByTagName("quest_reward").item(0).getTextContent();
                    String questGiverLevel = questElement.getElementsByTagName("quest_giver_level").item(0).getTextContent();
                    String prerequisiteQuestName = questElement.getElementsByTagName("prerequisite_quest_name").item(0).getTextContent();

                    Quest quest;

                    if (questGiver.equals("none")) {
                        String questDialogue = questElement.getElementsByTagName("quest_dialogue").item(0).getTextContent();

                        quest = new Quest(questName, questGiver, questReward, questGiverLevel, 1, prerequisiteQuestName, questDialogue);

                    } else {
                        String questNotGivenDialogue = questElement.getElementsByTagName("quest_not_given_dialogue").item(0).getTextContent();
                        System.out.println(questNotGivenDialogue);
                        String questGivenDialogue = questElement.getElementsByTagName("quest_given_dialogue").item(0).getTextContent();
                        String questCompletedDialogue = questElement.getElementsByTagName("quest_completed_dialogue").item(0).getTextContent();

                        quest = new Quest(questName, questGiver, questReward, questGiverLevel, 1, prerequisiteQuestName, questNotGivenDialogue, questGivenDialogue, questCompletedDialogue);
                    }

                    currentQuestList.add(quest);

                    //get the list of quest objectives for the current quest
                    NodeList questObjectiveList = questElement.getElementsByTagName("quest_objective");

                    for (int j = 0; j < questObjectiveList.getLength(); j++) {
                        Node curQuestObjectiveNode = questObjectiveList.item(j);

                        //obtain quest objective elements for each quest in the list
                        if (curQuestObjectiveNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element questObjectiveElement = (Element) curQuestObjectiveNode;

                            String questObjectiveType = questObjectiveElement.getElementsByTagName("quest_type").item(0).getTextContent();

                            QuestObjective questObjective;

                            //cater for each type of quest (different types will require different variables
                            switch (questObjectiveType) {
                                case "travel":
                                    String questObjectiveName = questObjectiveElement.getElementsByTagName("quest_objective_name").item(0).getTextContent();
                                    String questObjectiveLocation = questObjectiveElement.getElementsByTagName("quest_location").item(0).getTextContent();

                                    questObjective = new QuestObjective("travel", questObjectiveName, questObjectiveLocation);
                                    quest.addObjective(questObjective);

                                    break;
                                case "kill":

                                    questObjectiveName = questObjectiveElement.getElementsByTagName("quest_objective_name").item(0).getTextContent();
                                    String enemy = questObjectiveElement.getElementsByTagName("enemy").item(0).getTextContent();

                                    String amountToKillString = questObjectiveElement.getElementsByTagName("amount_to_kill").item(0).getTextContent();
                                    Integer amountToKill = Integer.parseInt(amountToKillString);

                                    questObjectiveLocation = questObjectiveElement.getElementsByTagName("quest_location").item(0).getTextContent();

                                    questObjective = new QuestObjective("kill", questObjectiveName, enemy, amountToKill, questObjectiveLocation);
                                    quest.addObjective(questObjective);

                                    break;

                                case "speak":

                                    questObjectiveName = questObjectiveElement.getElementsByTagName("quest_objective_name").item(0).getTextContent();
                                    String speakToNPC = questObjectiveElement.getElementsByTagName("quest_npc").item(0).getTextContent();
                                    questObjectiveLocation = questObjectiveElement.getElementsByTagName("quest_location").item(0).getTextContent();

                                    questObjective = new QuestObjective("speak", questObjectiveName, speakToNPC, questObjectiveLocation);

                                    quest.addObjective(questObjective);

                                    break;

                                case "fetch":

                                    questObjectiveName = questObjectiveElement.getElementsByTagName("quest_objective_name").item(0).getTextContent();
                                    String questItem = questObjectiveElement.getElementsByTagName("quest_item").item(0).getTextContent();
                                    questObjectiveLocation = questObjectiveElement.getElementsByTagName("quest_location").item(0).getTextContent();

                                    questObjective = new QuestObjective("fetch", questObjectiveName, questItem, questObjectiveLocation);
                                    quest.addObjective(questObjective);


                                    break;
                                case "speak_fetch":

                                    questObjectiveName = questObjectiveElement.getElementsByTagName("quest_objective_name").item(0).getTextContent();
                                    speakToNPC = questObjectiveElement.getElementsByTagName("quest_npc").item(0).getTextContent();
                                    questObjectiveLocation = questObjectiveElement.getElementsByTagName("quest_location").item(0).getTextContent();
                                    questItem = questObjectiveElement.getElementsByTagName("quest_item").item(0).getTextContent();

                                    questObjective = new QuestObjective("speak_fetch", questObjectiveName, speakToNPC, questObjectiveLocation, questItem);
                                    quest.addObjective(questObjective);

                                    break;
                                case "kill_fetch":

                                    questObjectiveName = questObjectiveElement.getElementsByTagName("quest_objective_name").item(0).getTextContent();
                                    questItem = questObjectiveElement.getElementsByTagName("quest_item").item(0).getTextContent();
                                    enemy = questObjectiveElement.getElementsByTagName("enemy").item(0).getTextContent();

                                    amountToKillString = questObjectiveElement.getElementsByTagName("amount_to_kill").item(0).getTextContent();
                                    amountToKill = Integer.parseInt(amountToKillString);

                                    questObjectiveLocation = questObjectiveElement.getElementsByTagName("quest_location").item(0).getTextContent();

                                    questObjective = new QuestObjective("kill_fetch", questObjectiveName, questItem, enemy, amountToKill, questObjectiveLocation);
                                    quest.addObjective(questObjective);

                                    break;
                                case "use_item":
                                    questObjectiveName = questObjectiveElement.getElementsByTagName("quest_objective_name").item(0).getTextContent();
                                    questItem = questObjectiveElement.getElementsByTagName("quest_item").item(0).getTextContent();
                                    questObjectiveLocation = questObjectiveElement.getElementsByTagName("quest_location").item(0).getTextContent();

                                    questObjective = new QuestObjective("use_item", questObjectiveName, questItem, questObjectiveLocation);
                                    quest.addObjective(questObjective);

                                    break;
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error in method: createActOneQuests(), Class GenerateData");
            e.printStackTrace();
        }

    }

    //creates the xml file containing the information such as quests, npc's etc. for Act One based on chosen template.
    public void createActOneXMLQuestData() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document doc = documentBuilder.newDocument();
            Element rootElement = doc.createElement("act_one_template");
            doc.appendChild(rootElement);

            Element firstTemplate = doc.createElement("first_template");
            rootElement.appendChild(firstTemplate);

            Element actOnePlotDescription = doc.createElement("act_one_plot_description");
            actOnePlotDescription.appendChild(doc.createTextNode("firstActOnePlotDescrip.txt"));
            firstTemplate.appendChild(actOnePlotDescription);

            Element actTwoPlotDescription = doc.createElement("act_two_plot_description");
            actTwoPlotDescription.appendChild(doc.createTextNode("firstActActTwoPlotDescrip.txt"));
            firstTemplate.appendChild(actTwoPlotDescription);

            Element questOne = doc.createElement("quest");
            firstTemplate.appendChild(questOne);

            Element questTwo = doc.createElement("quest");
            firstTemplate.appendChild(questTwo);

            storyGenerator.createActOne(questOne, questTwo, doc);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);

            String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Data/";
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(fullPath, "ActOne.xml");
            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();
            OutputStream fOut = new FileOutputStream(file);


            StreamResult result = new StreamResult(fOut);

            transformer.transform(source, result);
            fOut.flush();
            fOut.close();

            System.out.println("file saved");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    //creates the xml file containing the information such as quests, npc's etc. for Act Two based on chosen template.
    public void createActTwoAXMLQuestData() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document doc = documentBuilder.newDocument();
            Element rootElement = doc.createElement("act_two_template");
            doc.appendChild(rootElement);

            Element firstTemplate = doc.createElement("first_template");
            rootElement.appendChild(firstTemplate);

            Element actTwoPlotDescription = doc.createElement("act_two_plot_description");
            actTwoPlotDescription.appendChild(doc.createTextNode("firstTemplateActTwoPlotDescrip.txt"));
            firstTemplate.appendChild(actTwoPlotDescription);

            Element questOne = doc.createElement("quest");
            firstTemplate.appendChild(questOne);

            Element questTwo = doc.createElement("quest");
            firstTemplate.appendChild(questTwo);

            storyGenerator.createActTwo(questOne, questTwo, doc);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);

            String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Data/";
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(fullPath, "ActTwo.xml");
            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();
            OutputStream fOut = new FileOutputStream(file);


            StreamResult result = new StreamResult(fOut);

            transformer.transform(source, result);
            fOut.flush();
            fOut.close();

            System.out.println("file saved");


        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    //creates the xml file containing the information such as quests, npc's etc. for Act Three based on chosen template.
    public void createActThreeXMLQuestData() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document doc = documentBuilder.newDocument();
            Element rootElement = doc.createElement("act_three_template");
            doc.appendChild(rootElement);

            Element firstTemplate = doc.createElement("first_template");
            rootElement.appendChild(firstTemplate);

            Element actThreePlotDescription = doc.createElement("act_three_plot_description");
            actThreePlotDescription.appendChild(doc.createTextNode("firstTemplateActThreePlotDescrip.txt"));
            firstTemplate.appendChild(actThreePlotDescription);

            Element questOne = doc.createElement("quest");
            firstTemplate.appendChild(questOne);

            Element questTwo = doc.createElement("quest");
            firstTemplate.appendChild(questTwo);

            storyGenerator.createActThree(questOne, questTwo, doc);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);

            String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Data/";
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(fullPath, "ActThree.xml");
            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();
            OutputStream fOut = new FileOutputStream(file);


            StreamResult result = new StreamResult(fOut);

            transformer.transform(source, result);
            fOut.flush();
            fOut.close();

            System.out.println("file saved");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void generateStoryXMLs() {

        actOneQuests.clear();
        actTwoQuests.clear();
        actThreeQuests.clear();
        questList.clear();

        createActOneXMLQuestData();
        createActTwoAXMLQuestData();
        createActThreeXMLQuestData();
    }

    public void createQuests() {

        setQuestDialogue("actOne.xml", "questOne", StoryGenerator.getInstance().chosenMotives.get(0).questNotGivenDialogue);
        setQuestDialogue("actOne.xml", "questTwo", StoryGenerator.getInstance().chosenMotives.get(1).questNotGivenDialogue);
        createActQuests("ActOne.xml", actOneQuests);


        setQuestDialogue("actTwo.xml", "questThree", StoryGenerator.getInstance().chosenMotives.get(2).questNotGivenDialogue);
        setQuestDialogue("actTwo.xml", "questFour", StoryGenerator.getInstance().chosenMotives.get(3).questNotGivenDialogue);
        createActQuests("ActTwo.xml", actTwoQuests);

        setQuestDialogue("actThree.xml", "questFive", StoryGenerator.getInstance().chosenMotives.get(4).questNotGivenDialogue);
        setQuestDialogue("actThree.xml", "questSix", StoryGenerator.getInstance().chosenMotives.get(5).questNotGivenDialogue);
        createActQuests("ActThree.xml", actThreeQuests);

        currentAct = 1;
    }

    public void displayQuests() {
        System.out.println("***** ACT ONE *****");
        int i = 1;
        for (Quest quest : actOneQuests) {
            System.out.println("Quest " +i);

            quest.displayObjectivesInSentence();

            i++;
        }

        System.out.println("***** ACT TWO *****");

        for (Quest quest : actTwoQuests) {
            System.out.println("Quest " +i);

            quest.displayObjectivesInSentence();

            i++;
        }

        System.out.println("***** ACT THREE *****");

        for (Quest quest : actThreeQuests) {
            System.out.println("Quest " +i);

            quest.displayObjectivesInSentence();

            i++;
        }
    }

    //based on the quest's name, check if it's prerequisite quest has been completed.
    public boolean prerequisiteQuestCompleted(String questName) {

        if (questName.equals("none")) {
            return true;
        }

        Quest curQuest = getQuest(questName);

        if (curQuest.prerequisiteQuestName.equals("none")) {
            System.out.println("returning true on 'none'");
            return true;
        }

        Quest prerequisiteQuest = getQuest(curQuest.prerequisiteQuestName);

        return prerequisiteQuest.questComplete;
    }

    //finds quest based on quest's name
    public Quest getQuest(String thisQuestName) {
        for (Quest quest : questList) {
            if (quest.questName.equals(thisQuestName)) {
                return quest;
            }
        }

        return null;
    }

    /**
     * allows for the simple creation of quests
     */

    public void createQuestWithQuestGiver(Element questNode, Document doc, String questName, String questGiver, String questReward,
                                          String questGiverLevel, String prereqQuestName, String questNotGiven, String questGiven, String questCompleted) {

        Element name = doc.createElement("quest_name");
        name.appendChild(doc.createTextNode(questName));
        questNode.appendChild(name);

        Element giver = doc.createElement("quest_giver");
        giver.appendChild(doc.createTextNode(questGiver));
        questNode.appendChild(giver);

        Element reward = doc.createElement("quest_reward");
        reward.appendChild(doc.createTextNode(questReward));
        questNode.appendChild(reward);

        Element giverLevel = doc.createElement("quest_giver_level");
        giverLevel.appendChild(doc.createTextNode(questGiverLevel));
        questNode.appendChild(giverLevel);

        Element prerequisiteQuestName = doc.createElement("prerequisite_quest_name");
        prerequisiteQuestName.appendChild(doc.createTextNode(prereqQuestName));
        questNode.appendChild(prerequisiteQuestName);

        Element questNotGivenDialogue = doc.createElement("quest_not_given_dialogue");
        questNotGivenDialogue.appendChild(doc.createTextNode(questNotGiven));
        questNode.appendChild(questNotGivenDialogue);

        Element questGivenDialogue = doc.createElement("quest_given_dialogue");
        questGivenDialogue.appendChild(doc.createTextNode(questGiven));
        questNode.appendChild(questGivenDialogue);

        Element questCompletedDialogue = doc.createElement("quest_completed_dialogue");
        questCompletedDialogue.appendChild(doc.createTextNode(questCompleted));
        questNode.appendChild(questCompletedDialogue);

    }

    public void createQuestWithoutQuestGiver(Element questNode, Document doc, String questName, String questReward,
                                             String questGiverLevel, String prereqQuestName, String questDialogue) {

        Element name = doc.createElement("quest_name");
        name.appendChild(doc.createTextNode(questName));
        questNode.appendChild(name);

        Element giver = doc.createElement("quest_giver");
        giver.appendChild(doc.createTextNode("none"));
        questNode.appendChild(giver);

        Element reward = doc.createElement("quest_reward");
        reward.appendChild(doc.createTextNode(questReward));
        questNode.appendChild(reward);

        Element giverLevel = doc.createElement("quest_giver_level");
        giverLevel.appendChild(doc.createTextNode(questGiverLevel));
        questNode.appendChild(giverLevel);

        Element prerequisiteQuestName = doc.createElement("prerequisite_quest_name");
        prerequisiteQuestName.appendChild(doc.createTextNode(prereqQuestName));
        questNode.appendChild(prerequisiteQuestName);

        Element dialogue = doc.createElement("quest_dialogue");
        dialogue.appendChild(doc.createTextNode(questDialogue));
        questNode.appendChild(dialogue);
    }

    public void setQuestDialogue(String actXML, String givenQuestName, String questNotGiven) {
        try {

            String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Data/";
            File file = new File(fullPath, actXML); //actOne.xml

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(file);

            doc.getDocumentElement().normalize();

            //get list of quests in the Act
            NodeList questList = doc.getElementsByTagName("quest");

            for (int i = 0; i < questList.getLength(); i++) {
                Node curQuestNode = questList.item(i);

                //obtain quest elements for each quest in the list
                if (curQuestNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element questElement = (Element) curQuestNode;

                    String questName = questElement.getElementsByTagName("quest_name").item(0).getTextContent();

                    if (questName.equals(givenQuestName)) {

                        Node questNotGivenDialogue = questElement.getElementsByTagName("quest_not_given_dialogue").item(0);
                        questNotGivenDialogue.setTextContent(questNotGiven);
                    }

                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            System.out.println("great success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * allows for the simple creation of quest objectives
     */

    public void createSpeakObjective(Element questNode, Document doc, String objectiveName, String npc, String level, String objectiveDialogue) {
        Element questObjective = doc.createElement("quest_objective");
        questNode.appendChild(questObjective);

        Element questObjectiveName = doc.createElement("quest_objective_name");
        questObjectiveName.appendChild(doc.createTextNode(objectiveName));
        questObjective.appendChild(questObjectiveName);

        Element questType = doc.createElement("quest_type");
        questType.appendChild(doc.createTextNode("speak"));
        questObjective.appendChild(questType);

        Element questNPC = doc.createElement("quest_npc");
        questNPC.appendChild(doc.createTextNode(npc));
        questObjective.appendChild(questNPC);

        Element questLocation = doc.createElement("quest_location");
        questLocation.appendChild(doc.createTextNode(level));
        questObjective.appendChild(questLocation);

        Element questObjectiveDialogue = doc.createElement("quest_objective_dialogue");
        questObjectiveDialogue.appendChild(doc.createTextNode(objectiveDialogue));
        questObjective.appendChild(questObjectiveDialogue);

    }

    public void createKillFetchObjective(Element questNode, Document doc, String objectiveName, String enemyName, String amount, String item, String level) {

        Element questObjective = doc.createElement("quest_objective");
        questNode.appendChild(questObjective);

        Element questObjectiveName = doc.createElement("quest_objective_name");
        questObjectiveName.appendChild(doc.createTextNode(objectiveName));
        questObjective.appendChild(questObjectiveName);

        Element questType = doc.createElement("quest_type");
        questType.appendChild(doc.createTextNode("kill_fetch"));
        questObjective.appendChild(questType);

        Element enemy = doc.createElement("enemy");
        enemy.appendChild(doc.createTextNode(enemyName));
        questObjective.appendChild(enemy);

        Element amountToKill = doc.createElement("amount_to_kill");
        amountToKill.appendChild(doc.createTextNode(amount));
        questObjective.appendChild(amountToKill);

        Element questItem = doc.createElement("quest_item");
        questItem.appendChild(doc.createTextNode(item));
        questObjective.appendChild(questItem);

        Element questLocation = doc.createElement("quest_location");
        questLocation.appendChild(doc.createTextNode(level));
        questObjective.appendChild(questLocation);

    }

    public void createSpeakFetchObjective(Element questNode, Document doc, String objectiveName, String npc, String item, String level) {

        Element questObjective = doc.createElement("quest_objective");
        questNode.appendChild(questObjective);

        Element questObjectiveName = doc.createElement("quest_objective_name");
        questObjectiveName.appendChild(doc.createTextNode(objectiveName));
        questObjective.appendChild(questObjectiveName);

        Element questType = doc.createElement("quest_type");
        questType.appendChild(doc.createTextNode("speak_fetch"));
        questObjective.appendChild(questType);

        Element questNPC = doc.createElement("quest_npc");
        questNPC.appendChild(doc.createTextNode(npc));
        questObjective.appendChild(questNPC);

        Element questLocation = doc.createElement("quest_location");
        questLocation.appendChild(doc.createTextNode(level));
        questObjective.appendChild(questLocation);

        Element questItem = doc.createElement("quest_item");
        questItem.appendChild(doc.createTextNode(item));
        questObjective.appendChild(questItem);
    }

    public void createKillObjective(Element questNode, Document doc, String objectiveName, String enemyName, String amount, String level) {

        Element questObjective = doc.createElement("quest_objective");
        questNode.appendChild(questObjective);

        Element questObjectiveName = doc.createElement("quest_objective_name");
        questObjectiveName.appendChild(doc.createTextNode(objectiveName));
        questObjective.appendChild(questObjectiveName);

        Element questType = doc.createElement("quest_type");
        questType.appendChild(doc.createTextNode("kill"));
        questObjective.appendChild(questType);

        Element enemy = doc.createElement("enemy");
        enemy.appendChild(doc.createTextNode(enemyName));
        questObjective.appendChild(enemy);

        Element amountToKill = doc.createElement("amount_to_kill");
        amountToKill.appendChild(doc.createTextNode(amount));
        questObjective.appendChild(amountToKill);

        Element questLocation = doc.createElement("quest_location");
        questLocation.appendChild(doc.createTextNode(level));
        questObjective.appendChild(questLocation);
    }

    public void createUseItemObjective(Element questNode, Document doc, String objectiveName, String item, String level) {

        Element questObjective = doc.createElement("quest_objective");
        questNode.appendChild(questObjective);

        Element questObjectiveName = doc.createElement("quest_objective_name");
        questObjectiveName.appendChild(doc.createTextNode(objectiveName));
        questObjective.appendChild(questObjectiveName);

        Element questType = doc.createElement("quest_type");
        questType.appendChild(doc.createTextNode("use_item"));
        questObjective.appendChild(questType);

        Element questItem = doc.createElement("quest_item");
        questItem.appendChild(doc.createTextNode(item));
        questObjective.appendChild(questItem);

        Element questLocation = doc.createElement("quest_location");
        questLocation.appendChild(doc.createTextNode(level));
        questObjective.appendChild(questLocation);
    }

    public void createFetchObjective(Element questNode, Document doc, String objectiveName, String item, String level) {

        Element questObjective = doc.createElement("quest_objective");
        questNode.appendChild(questObjective);

        Element questObjectiveName = doc.createElement("quest_objective_name");
        questObjectiveName.appendChild(doc.createTextNode(objectiveName));
        questObjective.appendChild(questObjectiveName);

        Element questType = doc.createElement("quest_type");
        questType.appendChild(doc.createTextNode("fetch"));
        questObjective.appendChild(questType);

        Element questItem = doc.createElement("quest_item");
        questItem.appendChild(doc.createTextNode(item));
        questObjective.appendChild(questItem);

        Element questLocation = doc.createElement("quest_location");
        questLocation.appendChild(doc.createTextNode(level));
        questObjective.appendChild(questLocation);
    }

    public void deleteStoryXMLs() {

        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Data/";

        File file = new File(fullPath, "ActOne.xml");
        if (file.exists()) {
            System.out.println("destroying act one");
            file.delete();
        }

        file = new File(fullPath, "ActTwo.xml");
        if (file.exists()) {
            System.out.println("destroying act two");
            file.delete();
        }

        file = new File(fullPath, "ActThree.xml");
        if (file.exists()) {
            System.out.println("destroying act three");
            file.delete();
        }
    }


    /**
     * The following methods are used for returning instances of classes using reflection.
     */

    public NPC newNPC(String npcName) {
        Class<?> npc;

        String npcPackage = "com.watabou.pixeldungeon.actors.mobs.npcs.";
        String npcClassName = npcPackage.concat(npcName);

        try {
            npc = Class.forName(npcClassName);

            return (NPC) npc.newInstance();
        } catch (Exception e) {
            System.out.println("Error in getNPC");
            e.printStackTrace();
        }

        return null;
    }

    public Item newItem(String itemName) {
        Class<?> item;

        String itemPackage = "com.watabou.pixeldungeon.items.quest";
        String itemClassName = itemPackage.concat(itemName);

        try {

            item = Class.forName(itemClassName);

            return (Item) item.newInstance();


        } catch (Exception e) {
            System.out.println("Error in newItem");
            e.printStackTrace();
        }

        return null;
    }

    public String getItemName(String givenItemClassName) {

        Class<?> item;

        String itemPackage = "com.watabou.pixeldungeon.items.quest.";
        String itemClassName = itemPackage.concat(givenItemClassName);

        try {

            item = Class.forName(itemClassName);

            Item questItem = (Item) item.newInstance();

            return questItem.name();


        } catch (Exception e) {

        }

        return null;
    }

    public String getEnemyName(String givenEnemyClassName) {
        Class<?> enemy;

        String enemyPackage = "com.watabou.pixeldungeon.actors.mobs.";
        String enemyClassName = enemyPackage.concat(givenEnemyClassName);

        try {

            enemy = Class.forName(enemyClassName);

            Mob questEnemy = (Mob) enemy.newInstance();

            return questEnemy.name;


        } catch (Exception e) {

        }

        return null;
    }

    public String getNPCName(String givenNPCClassName) {
        Class<?> npc;

        String npcPackage = "com.watabou.pixeldungeon.actors.mobs.npcs.";
        String npcClassName = npcPackage.concat(givenNPCClassName);

        try {

            npc = Class.forName(npcClassName);

            NPC questNPC = (NPC) npc.newInstance();

            return questNPC.name;


        } catch (Exception e) {

        }

        return null;
    }

    public String getCurrentLevel() {
        String currentZone;

        if (OverworldScene.hero == null) {
            currentZone = DataHandler.getInstance().questList.get(0).questGiverLevel;
        } else {
            currentZone = OverworldScene.hero.currentZone;
        }

        if (currentZone.equals("Caves")) {
            currentZone = "Cave";
        }

        System.out.println("Current zone is: " + currentZone);
        return currentZone;

    }

    public void clearAllMobs() throws Exception {

        Level level = null;
        File file;

        //Forest mobs
        file = Game.instance.getFileStreamPath("Forest");

        if (file.exists()) {
            level = Dungeon.loadSpecificLevel("Forest");
            level.mobs.clear();

            System.out.println(level.mobs.size());
        } else {
            System.out.println("Forest file does not exist");
        }

        //Dungeon mobs
        file = Game.instance.getFileStreamPath("Dungeon");

        if (file.exists()) {
            level = Dungeon.loadSpecificLevel("Dungeon");
            level.mobs.clear();
        } else {
            System.out.println("Dungeon file does not exist");
        }

        //Caves mobs
        file = Game.instance.getFileStreamPath("Caves");

        if (file.exists()) {
            level = Dungeon.loadSpecificLevel("Cave");
            level.mobs.clear();

            System.out.println("amount of mobs in cave:" + level.mobs.size());

        } else {
            System.out.println("Caves file does not exist");
        }

        //Castle mobs
        file = Game.instance.getFileStreamPath("Castle");

        if (file.exists()) {
            level = Dungeon.loadSpecificLevel("Castle");
            level.mobs.clear();
        } else {
            System.out.println("Castle file does not exist");
        }

        //Shadow Lands mobs
        file = Game.instance.getFileStreamPath("Shadow Lands");

        if (file.exists()) {
            level = Dungeon.loadSpecificLevel("Shadow Lands");
            level.mobs.clear();
        } else {
            System.out.println("Shadow Lands file does not exist");
        }

        //Fields mobs
        file = Game.instance.getFileStreamPath("Fields");

        if (file.exists()) {
            level = Dungeon.loadSpecificLevel("Fields");
            level.mobs.clear();
        } else {
            System.out.println("Fields file does not exist");
        }

    }
}

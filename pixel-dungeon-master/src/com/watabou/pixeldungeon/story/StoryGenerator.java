package com.watabou.pixeldungeon.story;


import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.R;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class StoryGenerator {


    private static StoryGenerator storyGenerator;

    public QuestGiver questGiver;
    public String background;
    public ArrayList<QuestGiver.Motive> chosenMotives = new ArrayList<>();

    private StoryGenerator() {
    }


    public static StoryGenerator getInstance() {
        if (storyGenerator == null) {
            storyGenerator = new StoryGenerator();
        }
        return storyGenerator;
    }


    //a list of levels that can be used for an act's quests
    public ArrayList<String> questLevels = new ArrayList<>();

    public String levelOne;
    public String levelTwo;
    public String levelThree;
    public String levelFour;
    public String levelFive;
    public String levelSix;

    public void populateQuestLevels() {

        questLevels.add("Forest");
        questLevels.add("Caves");
        questLevels.add("Dungeon");
        questLevels.add("Shadow Lands");
        questLevels.add("Fields");
        questLevels.add("Castle");

    }

    public void selectActLevels() {

        Collections.shuffle(questLevels);

        levelOne = questLevels.remove(0);
        levelTwo = questLevels.remove(0);
        levelThree = questLevels.remove(0);
        levelFour = questLevels.remove(0);
        levelFive = questLevels.remove(0);
        levelSix = questLevels.remove(0);

    }


    public ArrayList<HeroBackground> heroBackgrounds = new ArrayList<>();
    public HeroBackground chosenBackground;

    public void populateHeroBackgrounds() {

        HeroBackground heroBackground;
        QuestGiver questGiver;

        // START Creating Knight
        heroBackground = new HeroBackground("Knight");

        questGiver = new QuestGiver("Mentor", "Ghost");
        questGiver.setXMLID(R.raw.knight_mentor_quest_giver);
        heroBackground.addQuestGiver(questGiver);

//        questGiver = new QuestGiver("Princess", "Imp");
//        questGiver.setXMLID(R.raw.knight_princess_quest_giver);
//        heroBackground.addQuestGiver(questGiver);
//
//        questGiver = new QuestGiver("PlaceHolder", "Blacksmith");
//        questGiver.setXMLID(R.raw.knight_placeholder_quest_giver);
//        heroBackground.addQuestGiver(questGiver);

        heroBackgrounds.add(heroBackground);
        // END Creating Knight

        // START Creating Peasant
//        heroBackground = new HeroBackground("Peasant");
//
//        questGiver = new QuestGiver("Farmer", "Ghost");
//        questGiver.setXMLID(R.raw.peasant_farmer_quest_giver);
//        heroBackground.addQuestGiver(questGiver);
//
//        questGiver = new QuestGiver("Relative", "Imp");
//        questGiver.setXMLID(R.raw.peasant_relative_quest_giver);
//        heroBackground.addQuestGiver(questGiver);
//
//        questGiver = new QuestGiver("PlaceHolder", "Blacksmith");
//        questGiver.setXMLID(R.raw.peasant_placeholder_quest_giver);
//        heroBackground.addQuestGiver(questGiver);
//
//        heroBackgrounds.add(heroBackground);
        // END Creating Peasant

        // START Creating Explorer
//        heroBackground = new HeroBackground("Explorer");
//
//        questGiver = new QuestGiver("Cartographer", "Ghost");
//        questGiver.setXMLID(R.raw.explorer_cartographer_quest_giver);
//        heroBackground.addQuestGiver(questGiver);
//
//        questGiver = new QuestGiver("PlaceHolder1", "Imp");
//        questGiver.setXMLID(R.raw.explorer_placeholder1_quest_giver);
//        heroBackground.addQuestGiver(questGiver);
//
//        questGiver = new QuestGiver("PlaceHolder2", "Blacksmith");
//        questGiver.setXMLID(R.raw.explorer_placeholder2_quest_giver);
//        heroBackground.addQuestGiver(questGiver);
//
//        heroBackgrounds.add(heroBackground);
        // END Creating Explorer

        //new QuestGiver(name, NPC) <- NPC represents the NPC used i.e. if "Ghost", will use "Ghost" NPC class.

    }

    public void selectHeroBackground() {

        Collections.shuffle(heroBackgrounds);

        chosenBackground = heroBackgrounds.get(0);
    }

    public void selectQuestGiver() {
        Collections.shuffle(chosenBackground.questGivers);

        questGiver = chosenBackground.questGivers.get(0);
    }


    public String shadowLandsStory;
    public String cavesStory;
    public String forestStory;
    public String castleStory;
    public String fieldsStory;
    public String dungeonStory;

    public String actOneStory;
    public String actTwoStory;
    public String actThreeStory;


    public void setLevelStories() {

        switch (chosenBackground.background) {

            case "Knight":
                shadowLandsStory = "The shadow lands is a dangerous place, even for a knight.";
                forestStory = "The forests have always been a beautiful part of the Kingdom.";
                cavesStory = "These caves were once used for mining materials used to make weapons and armor for knights such as yourself.";
                castleStory = "The castle was one your home, before the king went bonkers.";
                fieldsStory = "The fields are home to many of the peasants you spit on on a daily basis.";
                dungeonStory = "Housing some of the most dangerous prisoners, the Dungeon is a fortress of scum.";

                switch (questGiver.name) {

                    case "Mentor": //mentor
                        shadowLandsStory = shadowLandsStory.concat(" You're not sure why your mentor asked you to meet in a place like this.");
                        forestStory = forestStory.concat(" Your mentor has asked for you two to meet here.");
                        cavesStory = cavesStory.concat(" These caves have been deserted for years, why would the mentor ask you to come down here?");
                        castleStory = castleStory.concat(" Your mentor would like to meet you here, perhaps to reminisce.");
                        fieldsStory = fieldsStory.concat(" Maybe your mentor wants you to make fun of some peasants with him?");
                        dungeonStory = dungeonStory.concat(" It's not your turn for guard duty, why would the mentor want to meet you here?");
                        break;

                    case "Princess": //princess
                        shadowLandsStory = shadowLandsStory.concat(" The princess has always been one for danger, you should find her.");
                        forestStory = forestStory.concat(" The princess always chooses the most romantic places to meet. You should find her.");
                        cavesStory = cavesStory.concat(" Not the most romantic of places, but at least you and the Princess will be alone here. You should find her.");
                        castleStory = castleStory.concat(" The princess has been homeless since the mad king, maybe she came back to try and not be homeless. Find her.");
                        fieldsStory = fieldsStory.concat(" The princess does enjoy watching me mistreat the common folk, you should find her and get mistreating.");
                        dungeonStory = dungeonStory.concat(" This isn't exactly a place for a princess, you should find her.");
                        break;

                    case "PlaceHolder": //stranger
                        shadowLandsStory = shadowLandsStory.concat(" It's your turn to look for corpses for some reason. Look around and see if you can find anything.");
                        forestStory = forestStory.concat(" The forest is always soothing to walk through. Walk through it and see if you come upon anyone interesting.");
                        cavesStory = cavesStory.concat(" Why would anyone be down here? Oh well, walk around and find someone.");
                        castleStory = castleStory.concat(" You haven't been here since the last time you were here. Find someone new!");
                        fieldsStory = fieldsStory.concat(" You've spat on every peasant here, but you feel like you need to mistreat more people. Find someone new.");
                        dungeonStory = dungeonStory.concat(" Make sure everything's alright here. Walk around and find snything out of place.");

                }


                break;

            case "Peasant":

                shadowLandsStory = "A well-trained knight barely has a chance of survival here, you have my condolences.";
                forestStory = "The over abundance of trees makes the Forest great for gathering firewood.";
                cavesStory = "These caves were once used for mining materials used to make weapons and armor for knights such as yourself.";
                castleStory = "The castle was one your home, before the king went bonkers.";
                fieldsStory = "The fields are home to many of the peasants you spit on on a daily basis.";
                dungeonStory = "Housing some of the most dangerous prisoners, the Dungeon is a fortress of scum.";

                switch (questGiver.name) {

                    case "Relative":
                        shadowLandsStory = shadowLandsStory.concat(" You're not sure why your mentor asked you to meet in a place like this.");
                        forestStory = forestStory.concat(" Your mentor has asked for you two to meet here.");
                        cavesStory = cavesStory.concat(" These caves have been deserted for years, why would the mentor ask you to come down here?");
                        castleStory = castleStory.concat(" Your mentor would like to meet you here, perhaps to reminisce.");
                        fieldsStory = fieldsStory.concat(" Maybe your mentor wants you to make fun of some peasants with him?");
                        dungeonStory = dungeonStory.concat(" It's not your turn for guard duty, why would the mentor want to meet you here?");
                        break;

                    case "Friend":
                        shadowLandsStory = shadowLandsStory.concat(" The princess has always been one for danger, you should find her.");
                        forestStory = forestStory.concat(" The princess always chooses the most romantic places to meet. You should find her.");
                        cavesStory = cavesStory.concat(" Not the most romantic of places, but at least you and the Princess will be alone here. You should find her.");
                        castleStory = castleStory.concat(" The princess has been homeless since the mad king, maybe she came back to try and not be homeless. Find her.");
                        fieldsStory = fieldsStory.concat(" The princess does enjoy watching me mistreat the common folk, you should find her and get mistreating.");
                        dungeonStory = dungeonStory.concat(" This isn't exactly a place for a princess, you should find her.");
                        break;

                    case "Farmer":
                        shadowLandsStory = shadowLandsStory.concat(" It's your turn to look for corpses for some reason. Look around and see if you can find anything.");
                        forestStory = forestStory.concat(" The forest is always soothing to walk through. Walk through it and see if you come upon anyone interesting.");
                        cavesStory = cavesStory.concat(" Why would anyone be down here? Oh well, walk around and find someone.");
                        castleStory = castleStory.concat(" You haven't been here since the last time you were here. Find someone new!");
                        fieldsStory = fieldsStory.concat(" You've spat on every peasant here, but you feel like you need to mistreat more people. Find someone new.");
                        dungeonStory = dungeonStory.concat(" Make sure everything's alright here. Walk around and find snything out of place.");

                }

                break;

            case "Explorer":

                shadowLandsStory = "A well-trained knight barely has a chance of survival here, you have my condolences.";
                forestStory = "The over abundance of trees makes the Forest great for gathering firewood.";
                cavesStory = "These caves were once used for mining materials used to make weapons and armor for knights such as yourself.";
                castleStory = "The castle was one your home, before the king went bonkers.";
                fieldsStory = "The fields are home to many of the peasants you spit on on a daily basis.";
                dungeonStory = "Housing some of the most dangerous prisoners, the Dungeon is a fortress of scum.";

                switch (questGiver.name) {

                    case "Stranger":
                        shadowLandsStory = shadowLandsStory.concat(" You're not sure why your mentor asked you to meet in a place like this.");
                        forestStory = forestStory.concat(" Your mentor has asked for you two to meet here.");
                        cavesStory = cavesStory.concat(" These caves have been deserted for years, why would the mentor ask you to come down here?");
                        castleStory = castleStory.concat(" Your mentor would like to meet you here, perhaps to reminisce.");
                        fieldsStory = fieldsStory.concat(" Maybe your mentor wants you to make fun of some peasants with him?");
                        dungeonStory = dungeonStory.concat(" It's not your turn for guard duty, why would the mentor want to meet you here?");
                        break;

                    case "Partner":
                        shadowLandsStory = shadowLandsStory.concat(" The princess has always been one for danger, you should find her.");
                        forestStory = forestStory.concat(" The princess always chooses the most romantic places to meet. You should find her.");
                        cavesStory = cavesStory.concat(" Not the most romantic of places, but at least you and the Princess will be alone here. You should find her.");
                        castleStory = castleStory.concat(" The princess has been homeless since the mad king, maybe she came back to try and not be homeless. Find her.");
                        fieldsStory = fieldsStory.concat(" The princess does enjoy watching me mistreat the common folk, you should find her and get mistreating.");
                        dungeonStory = dungeonStory.concat(" This isn't exactly a place for a princess, you should find her.");
                        break;

                    case "Civilian":
                        shadowLandsStory = shadowLandsStory.concat(" It's your turn to look for corpses for some reason. Look around and see if you can find anything.");
                        forestStory = forestStory.concat(" The forest is always soothing to walk through. Walk through it and see if you come upon anyone interesting.");
                        cavesStory = cavesStory.concat(" Why would anyone be down here? Oh well, walk around and find someone.");
                        castleStory = castleStory.concat(" You haven't been here since the last time you were here. Find someone new!");
                        fieldsStory = fieldsStory.concat(" You've spat on every peasant here, but you feel like you need to mistreat more people. Find someone new.");
                        dungeonStory = dungeonStory.concat(" Make sure everything's alright here. Walk around and find snything out of place.");

                }


                break;

        }


    }

    public void createActOneStory(PixelDungeon c) {
        try {

            int xmlID = 0;
            String questGiverName = questGiver.name;

            switch (chosenBackground.background) {
                case "Knight":
                    xmlID = R.raw.knight_opening_stories;
                    break;
                case "Peasant":
                    xmlID = R.raw.peasant_opening_stories;
                    break;
                case "Explorer":
                    xmlID = R.raw.explorer_opening_stories;
            }

            InputStream is = c.getResources().openRawResource(xmlID);

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(is);

            doc.getDocumentElement().normalize();

            NodeList questGiverList = doc.getElementsByTagName("questGiver");

            for (int i = 0; i < questGiverList.getLength(); i++) {

                Node curQuestGiver = questGiverList.item(i);

                //obtain quest elements for each quest in the list
                if (curQuestGiver.getNodeType() == Node.ELEMENT_NODE) {

                    Element questGiverElement = (Element) curQuestGiver;

                    String curQuestGiverName = questGiverElement.getElementsByTagName("name").item(0).getTextContent();

                    if (curQuestGiverName.equals(questGiverName)) {

                        NodeList levelList = questGiverElement.getElementsByTagName("level");

                        for (int j = 0; j < levelList.getLength(); j++) {

                            Node levelNode = levelList.item(j);

                            if (levelNode.getNodeType() == Node.ELEMENT_NODE) {

                                Element curLevelElement = (Element) levelNode;

                                String curLevelName = curLevelElement.getElementsByTagName("name").item(0).getTextContent();

                                if (curLevelName.equals(levelOne)) {

                                    String levelStory = curLevelElement.getElementsByTagName("story").item(0).getTextContent();

                                    actOneStory = levelStory;

                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error in method: readXML(), Class StoryGenerator");
            e.printStackTrace();
        }
    }


    public void initializeStoryVariables() {
        populateQuestLevels();
        selectActLevels();

        populateHeroBackgrounds();
        selectHeroBackground();

        selectQuestGiver();

        setLevelStories();

        //setActOpeningStory();

    }

    public void createActOne(Element firstQuest, Element secondQuest, Document document) {

        String motiveName = questGiver.getRandomMotiveType("NORMAL");
        QuestGiver.Motive motive = questGiver.getMotive(motiveName);
        questCreator(firstQuest, document, "questOne", levelOne, "none", motive.questNotGivenDialogue, motive.questGivenDialogue, motive.questCompleteDialogue);
        objectiveCreator(motive, motiveName, "NORMAL", levelOne, "Town", questGiver.getRandomObjective(motiveName, "NORMAL"), firstQuest, document);
        chosenMotives.add(motive);

        motiveName = questGiver.getRandomMotiveType("SERIOUS");
        motive = questGiver.getMotive(motiveName);
        questCreator(secondQuest, document, "questTwo", "Town", "questOne", motive.questNotGivenDialogue, motive.questGivenDialogue, motive.questCompleteDialogue);
        objectiveCreator(motive, motiveName, "SERIOUS", levelTwo, "Town", questGiver.getRandomObjective(motiveName, "SERIOUS"), secondQuest, document);
        chosenMotives.add(motive);
    }

    public void createActTwo(Element firstQuest, Element secondQuest, Document document) {

        String motiveName = questGiver.getRandomMotiveType("CATASTROPHE");
        QuestGiver.Motive motive = questGiver.getMotive(motiveName);
        questCreator(firstQuest, document, "questThree", "Town", "none", motive.questNotGivenDialogue, motive.questGivenDialogue, motive.questCompleteDialogue);
        objectiveCreator(motive, motiveName, "CATASTROPHE", levelThree, "Town", questGiver.getRandomObjective(motiveName, "CATASTROPHE"), firstQuest, document);
        chosenMotives.add(motive);


        motiveName = questGiver.getRandomMotiveType("INVESTIGATE");
        motive = questGiver.getMotive(motiveName);
        questCreator(secondQuest, document, "questFour", "Town", "questThree", motive.questNotGivenDialogue, motive.questGivenDialogue, motive.questCompleteDialogue);
        objectiveCreator(motive, motiveName, "INVESTIGATE", levelFour, "Town", questGiver.getRandomObjective(motiveName, "INVESTIGATE"), secondQuest, document);
        chosenMotives.add(motive);
    }

    public void createActThree(Element firstQuest, Element secondQuest, Document document) {

        String motiveName = questGiver.getRandomMotiveType("STRENGTHEN");
        QuestGiver.Motive motive = questGiver.getMotive(motiveName);
        questCreator(firstQuest, document, "questFive", "Town", "none", motive.questNotGivenDialogue, motive.questGivenDialogue, motive.questCompleteDialogue);
        objectiveCreator(motive, motiveName, "STRENGTHEN", levelFive, "Town", questGiver.getRandomObjective(motiveName, "STRENGTHEN"), firstQuest, document);
        chosenMotives.add(motive);


        motiveName = questGiver.getRandomMotiveType("RESOLVE");
        motive = questGiver.getMotive(motiveName);
        questCreator(secondQuest, document, "questSix", "Town", "questFive", motive.questNotGivenDialogue, motive.questGivenDialogue, motive.questCompleteDialogue);
        objectiveCreator(motive, motiveName, "RESOLVE", levelSix, "Town", questGiver.getRandomObjective(motiveName, "RESOLVE"), secondQuest, document);
        chosenMotives.add(motive);
    }


    public void questCreator(Element questNode, Document document, String questName, String levelName,
                             String prerequisiteQuestName, String questNotGivenDialogue, String questGivenDialogue, String questCompletedDialogue) {

        String questGiverName = questGiver.NPC;

        DataHandler.getInstance().createQuestWithQuestGiver(questNode, document, questName, questGiverName, "40xp", levelName,
                prerequisiteQuestName, questNotGivenDialogue, questGivenDialogue, questCompletedDialogue);

    }

    public void objectiveCreator(QuestGiver.Motive motive, String motiveType, String storyPhase, String level,
                                 String returnLevel, String objectiveType, Element questNode, Document document) {

        String questGiverName = questGiver.NPC;

        switch (objectiveType) {

            case "kill":

                String enemy = questGiver.getRandomEnemy(motiveType, storyPhase);

                String objectiveName = "kill " + enemy;

                Integer amountToKill = getRandomAmountToKill(3, 5);

                //questNotGivenDialogue = motive.questNotGivenDialogue.concat(" Kill " + amountToKill + " " + enemy + "in the " + level + ".");
                motive.questNotGivenDialogue = motive.questNotGivenDialogue.concat(" Kill " + amountToKill + " " + enemy + "in the " + level + ".");

                DataHandler.getInstance().createKillObjective(questNode, document, objectiveName, enemy, amountToKill.toString(), level);

                objectiveName = "speak to " + questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;

            case "kill_fetch":

                enemy = questGiver.getRandomEnemy(motiveType, storyPhase);

                String item = questGiver.getRandomItem(motiveType, storyPhase);

                objectiveName = "find a " + item + " by killing " + enemy;

                amountToKill = getRandomAmountToKill(4, 6);

                //questNotGivenDialogue = motive.questNotGivenDialogue.concat(" Kill " + enemy + "'s in the " + level + " to find" + item + ".");
                motive.questNotGivenDialogue = motive.questNotGivenDialogue.concat(" Kill " + enemy + "'s in the " + level + " to find" + item + ".");

                DataHandler.getInstance().createKillFetchObjective(questNode, document, objectiveName, enemy, amountToKill.toString(), item, level);

                objectiveName = "speak to " + questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;

            case "fetch":

                item = questGiver.getRandomItem(motiveType, storyPhase);

                objectiveName = "fetch " + item;

                //questNotGivenDialogue = motive.questNotGivenDialogue.concat(" Find " + item + " somewhere in the " + level + ".");
                motive.questNotGivenDialogue = motive.questNotGivenDialogue.concat(" Find " + item + " somewhere in the " + level + ".");

                DataHandler.getInstance().createFetchObjective(questNode, document, objectiveName, item, level);

                objectiveName = "speak to " + questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;

            case "speak":

                String npc = questGiver.getRandomNPC(motiveType, storyPhase);

                objectiveName = "speak to  " + npc;

                //questNotGivenDialogue = motive.questNotGivenDialogue.concat(" Speak to " + npc + " in the " + level + ".");
                motive.questNotGivenDialogue = motive.questNotGivenDialogue.concat(" Speak to " + npc + " in the " + level + ".");

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, npc, level, "some dialogue");

                objectiveName = "speak to " + questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;

            case "speak_fetch":

                npc = questGiver.getRandomNPC(motiveType, storyPhase);

                item = questGiver.getRandomItem(motiveType, storyPhase);

                objectiveName = "fetch " + item + " from " + npc;

                //questNotGivenDialogue = motive.questNotGivenDialogue.concat(" Fetch " + item + " from " + npc + " in the " + level + ".");
                motive.questNotGivenDialogue = motive.questNotGivenDialogue.concat(" Fetch " + item + " from " + npc + " in the " + level + ".");

                DataHandler.getInstance().createSpeakFetchObjective(questNode, document, objectiveName, npc, item, level);

                objectiveName = "speak to " + questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;

            case "use_item":

                item = questGiver.getRandomItem(motiveType, storyPhase);

                objectiveName = "use " + item;

                //questNotGivenDialogue = motive.questNotGivenDialogue.concat(" Use the " + item + " item.");
                motive.questNotGivenDialogue = motive.questNotGivenDialogue.concat(" Use the " + item + " item.");

                DataHandler.getInstance().createUseItemObjective(questNode, document, objectiveName, item, level);

                objectiveName = "speak to " + questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;
        }


    }


    public int getRandomAmountToKill(int min, int max) {
        Random random = new Random();

        return random.nextInt((max + 1) - min) + min;
    }

    public void createQuestGiverMotives(PixelDungeon c) {
        try {

            InputStream is = c.getResources().openRawResource(questGiver.xmlID);

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(is);

            doc.getDocumentElement().normalize();

            NodeList motiveList = doc.getElementsByTagName("motive");

            for (int i = 0; i < motiveList.getLength(); i++) {

                Node curMotive = motiveList.item(i);

                //obtain quest elements for each quest in the list
                if (curMotive.getNodeType() == Node.ELEMENT_NODE) {

                    Element motiveElement = (Element) curMotive;

                    String motiveName = motiveElement.getElementsByTagName("motiveName").item(0).getTextContent();
                    String storyPhase = motiveElement.getElementsByTagName("storyPhase").item(0).getTextContent();

                    String questNotGivenDialogue = motiveElement.getElementsByTagName("questNotGivenDialogue").item(0).getTextContent();
                    String questGivenDialogue    = motiveElement.getElementsByTagName("questGivenDialogue").item(0).getTextContent();
                    String questCompleteDialogue = motiveElement.getElementsByTagName("questCompleteDialogue").item(0).getTextContent();

                    questGiver.createNewMotive(motiveName, storyPhase, questNotGivenDialogue, questGivenDialogue, questCompleteDialogue);

                    //getting motive quest objectives
                    NodeList objectiveTypeList = motiveElement.getElementsByTagName("objectiveType");

                    for (int j = 0; j < objectiveTypeList.getLength(); j++) {

                        Node objectiveTypeNode = objectiveTypeList.item(j);

                        if (objectiveTypeNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element objectiveTypeElement = (Element)objectiveTypeNode;

                            String objectiveType = objectiveTypeElement.getElementsByTagName("objectiveTypeName").item(0).getTextContent();

                            questGiver.addMotiveObjectives(motiveName, storyPhase, objectiveType);
                        }
                    }

                    //getting motive quest enemies
                    NodeList enemyList = motiveElement.getElementsByTagName("enemy");

                    for (int k = 0; k < enemyList.getLength(); k++) {

                        Node enemyNode = enemyList.item(k);

                        if (enemyNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element enemyElement = (Element)enemyNode;

                            String enemyName = enemyElement.getElementsByTagName("enemyName").item(0).getTextContent();

                            questGiver.addEnemies(motiveName, storyPhase, enemyName);
                        }
                    }

                    //getting motive quest items
                    NodeList itemList = motiveElement.getElementsByTagName("item");

                    for (int l = 0; l < itemList.getLength(); l++) {

                        Node itemNode = itemList.item(l);

                        if (itemNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element itemElement = (Element)itemNode;

                            String itemName = itemElement.getElementsByTagName("itemName").item(0).getTextContent();

                            questGiver.addItems(motiveName, storyPhase, itemName);
                        }
                    }

                    //getting motive quest npcs
                    NodeList npcList = motiveElement.getElementsByTagName("npc");

                    for (int m = 0; m < npcList.getLength(); m++) {

                        Node npcNode = npcList.item(m);

                        if (npcNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element npcElement = (Element)npcNode;

                            String npcName = npcElement.getElementsByTagName("npcName").item(0).getTextContent();

                            questGiver.addNPCs(motiveName, storyPhase, npcName);
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error in method: readXML(), Class StoryGenerator");
            e.printStackTrace();
        }


    }


}

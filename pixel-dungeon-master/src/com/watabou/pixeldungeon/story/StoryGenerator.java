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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoryGenerator {


    private static StoryGenerator storyGenerator;

    public QuestGiver questGiver;
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
    private ArrayList<String> questLevels = new ArrayList<>();

    public String levelOne;
    public String levelTwo;
    public String levelThree;
    public String levelFour;
    public String levelFive;
    public String levelSix;

    private void populateQuestLevels() {

        questLevels.add("Forest");
        questLevels.add("Caves");
        questLevels.add("Dungeon");
        questLevels.add("Shadow Lands");
        questLevels.add("Fields");
        questLevels.add("Castle");

    }

    private void selectActLevels() {

        levelOne = chosenBackground.getRandomStartingLevel();

        questLevels.remove(levelOne);

        Collections.shuffle(questLevels);

        //levelOne = questLevels.remove(0);
        levelTwo = questLevels.remove(0);
        levelThree = questLevels.remove(0);
        levelFour = questLevels.remove(0);
        levelFive = questLevels.remove(0);
        levelSix = questLevels.remove(0);

    }


    public ArrayList<HeroBackground> heroBackgrounds = new ArrayList<>();
    public HeroBackground chosenBackground;

    private void populateHeroBackgrounds() {

        HeroBackground heroBackground;
        QuestGiver questGiver;

        // START Creating Knight
        heroBackground = new HeroBackground("Knight");

        heroBackground.addStartingLevel("Dungeon");
        heroBackground.addStartingLevel("Forest");
        heroBackground.addStartingLevel("Fields");
        heroBackground.addStartingLevel("Caves");

//        questGiver = new QuestGiver("Mentor", "Ghost");
//        questGiver.setXMLID(R.raw.knight_mentor_quest_giver);
//        heroBackground.addQuestGiver(questGiver);
//
//        questGiver = new QuestGiver("Princess", "Imp");
//        questGiver.setHomeLevel("Town");
//        questGiver.setXMLID(R.raw.knight_princess_quest_giver);
//        heroBackground.addQuestGiver(questGiver);
//
//        questGiver = new QuestGiver("Seer", "Seer");
//        questGiver.setXMLID(R.raw.knight_seer_quest_giver);
//        heroBackground.addQuestGiver(questGiver);

        //heroBackgrounds.add(heroBackground);
        // END Creating Knight

        // START Creating Peasant
        heroBackground = new HeroBackground("Peasant");

        heroBackground.addStartingLevel("Fields");
        heroBackground.addStartingLevel("Forest");
        heroBackground.addStartingLevel("Caves");
//
//        questGiver = new QuestGiver("Farmer", "Farmer");
//        questGiver.setHomeLevel("Fields");
//        questGiver.setXMLID(R.raw.peasant_farmer_quest_giver);
//        heroBackground.addQuestGiver(questGiver);
//
        questGiver = new QuestGiver("Relative", "Relative");
        questGiver.setHomeLevel("Forest");
        questGiver.setXMLID(R.raw.peasant_relative_quest_giver);
        heroBackground.addQuestGiver(questGiver);
//
//        questGiver = new QuestGiver("PlaceHolder", "Blacksmith");
//        questGiver.setXMLID(R.raw.peasant_placeholder_quest_giver);
//        heroBackground.addQuestGiver(questGiver);
//
       heroBackgrounds.add(heroBackground);
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

    private void selectHeroBackground() {

        Collections.shuffle(heroBackgrounds);

        chosenBackground = heroBackgrounds.get(0);
    }

    private void selectQuestGiver() {
        Collections.shuffle(chosenBackground.questGivers);

        questGiver = chosenBackground.questGivers.get(0);
    }

    public String actOneStory;
    public String actTwoStory;
    public String actThreeStory;

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
        populateHeroBackgrounds();

        selectHeroBackground();
        selectActLevels();

        selectQuestGiver();
    }

    public void createActOne(Element firstQuest, Element secondQuest, Document document) {

        String motiveName = questGiver.getRandomMotiveType("NORMAL");
        QuestGiver.Motive motive = questGiver.getMotive(motiveName);
        questCreator(firstQuest, document, "questOne", levelOne, "none", motive.questNotGivenDialogue, motive.questGivenDialogue, motive.questCompleteDialogue);
        objectiveCreator(motive, motiveName, "NORMAL", levelOne, questGiver.homeLevel, questGiver.getRandomObjective(motiveName, "NORMAL"), firstQuest, document);
        chosenMotives.add(motive);

        motiveName = questGiver.getRandomMotiveType("SERIOUS");
        motive = questGiver.getMotive(motiveName);

        actTwoStory = motive.actOpeningStory;

        while (actTwoStory == null) {
            motiveName = questGiver.getRandomMotiveType("SERIOUS");
            motive = questGiver.getMotive(motiveName);
            actTwoStory = motive.actOpeningStory;
            System.out.println(motive.actOpeningStory);
        }

        questCreator(secondQuest, document, "questTwo", questGiver.homeLevel, "questOne", motive.questNotGivenDialogue, motive.questGivenDialogue, motive.questCompleteDialogue);
        objectiveCreator(motive, motiveName, "SERIOUS", levelTwo, questGiver.homeLevel, questGiver.getRandomObjective(motiveName, "SERIOUS"), secondQuest, document);
        chosenMotives.add(motive);


    }

    public void createActTwo(Element firstQuest, Element secondQuest, Document document) {

        String motiveName = questGiver.getRandomMotiveType("CATASTROPHE");
        QuestGiver.Motive motive = questGiver.getMotive(motiveName);
        questCreator(firstQuest, document, "questThree", questGiver.homeLevel, "none", motive.questNotGivenDialogue, motive.questGivenDialogue, motive.questCompleteDialogue);
        objectiveCreator(motive, motiveName, "CATASTROPHE", levelThree, questGiver.homeLevel, questGiver.getRandomObjective(motiveName, "CATASTROPHE"), firstQuest, document);
        chosenMotives.add(motive);


        motiveName = questGiver.getRandomMotiveType("INVESTIGATE");
        motive = questGiver.getMotive(motiveName);

        actThreeStory = motive.actOpeningStory;

        while (actThreeStory == null) {
            motiveName = questGiver.getRandomMotiveType("INVESTIGATE");
            motive = questGiver.getMotive(motiveName);
            actThreeStory = motive.actOpeningStory;
            System.out.println(motive.actOpeningStory);
        }

        System.out.println(motive.actOpeningStory);

        questCreator(secondQuest, document, "questFour", questGiver.homeLevel, "questThree", motive.questNotGivenDialogue, motive.questGivenDialogue, motive.questCompleteDialogue);
        objectiveCreator(motive, motiveName, "INVESTIGATE", levelFour, questGiver.homeLevel, questGiver.getRandomObjective(motiveName, "INVESTIGATE"), secondQuest, document);
        chosenMotives.add(motive);
    }

    public void createActThree(Element firstQuest, Element secondQuest, Document document) {

        String motiveName = questGiver.getRandomMotiveType("STRENGTHEN");
        QuestGiver.Motive motive = questGiver.getMotive(motiveName);
        questCreator(firstQuest, document, "questFive", questGiver.homeLevel, "none", motive.questNotGivenDialogue, motive.questGivenDialogue, motive.questCompleteDialogue);
        objectiveCreator(motive, motiveName, "STRENGTHEN", levelFive, questGiver.homeLevel, questGiver.getRandomObjective(motiveName, "STRENGTHEN"), firstQuest, document);
        chosenMotives.add(motive);


        motiveName = questGiver.getRandomMotiveType("RESOLVE");
        motive = questGiver.getMotive(motiveName);
        questCreator(secondQuest, document, "questSix", questGiver.homeLevel, "questFive", motive.questNotGivenDialogue, motive.questGivenDialogue, motive.questCompleteDialogue);
        objectiveCreator(motive, motiveName, "RESOLVE", levelSix, questGiver.homeLevel, questGiver.getRandomObjective(motiveName, "RESOLVE"), secondQuest, document);
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

                Integer amountToKill;

                if (storyPhase.equals("RESOLVE")) {
                   amountToKill = 1;
                } else {
                    amountToKill = getRandomAmountToKill(3, 5);
                }

                if (amountToKill > 1) {
                    motive.questNotGivenDialogue = motive.questNotGivenDialogue.concat("\n" + "\n" + "Quest Objective:" +"\n" + "Kill " + amountToKill + " " +classNameDeconstructor(enemy)+ "'s in the " + level + ".");
                } else {
                    motive.questNotGivenDialogue = motive.questNotGivenDialogue.concat("\n" + "\n" + "Quest Objective:" +"\n" + "Kill the " +classNameDeconstructor(enemy)+ " in the " + level + ".");
                }

                DataHandler.getInstance().createKillObjective(questNode, document, objectiveName, enemy, amountToKill.toString(), level);

                objectiveName = "speak to " +DataHandler.getInstance().getNPCName(questGiverName);

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;

            case "kill_fetch":

                enemy = questGiver.getRandomEnemy(motiveType, storyPhase);

                String item = questGiver.getRandomItem(motiveType, storyPhase);

                objectiveName = "find a " + item + " by killing " + enemy;

                amountToKill = getRandomAmountToKill(4, 6);

                motive.questNotGivenDialogue = motive.questNotGivenDialogue.concat("\n" + "\n" + "Quest Objective:" +"\n" + "Kill " +classNameDeconstructor(enemy)+ "'s in the "  +level+ " to find " +classNameDeconstructor(item)+ ".");

                DataHandler.getInstance().createKillFetchObjective(questNode, document, objectiveName, enemy, amountToKill.toString(), item, level);

                objectiveName = "speak to " +DataHandler.getInstance().getNPCName(questGiverName);

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;

            case "fetch":

                item = questGiver.getRandomItem(motiveType, storyPhase);

                objectiveName = "fetch " + item;

                motive.questNotGivenDialogue = motive.questNotGivenDialogue.concat("\n" + "\n" + "Quest Objective:" +"\n" + "Find " +classNameDeconstructor(item)+ " somewhere in the " + level + ".");

                DataHandler.getInstance().createFetchObjective(questNode, document, objectiveName, item, level);

                objectiveName = "speak to " +DataHandler.getInstance().getNPCName(questGiverName);

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;

            case "speak":

                String npc = questGiver.getRandomNPC(motiveType, storyPhase);

                objectiveName = "speak to  " + npc;

                String completeDialogue = motive.questCompleteDialogue;

                motive.questNotGivenDialogue = motive.questNotGivenDialogue.concat("\n" + "\n" + "Quest Objective:" +"\n" + "Speak to " +classNameDeconstructor(npc)+ " in the " + level + ".");

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, npc, level, completeDialogue);

//                objectiveName = "speak to " + questGiverName;
//
//                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;

            case "speak_fetch":

                npc = questGiver.getRandomNPC(motiveType, storyPhase);

                item = questGiver.getRandomItem(motiveType, storyPhase);

                objectiveName = "fetch " +item+ " from " +DataHandler.getInstance().getNPCName(npc);

                motive.questNotGivenDialogue = motive.questNotGivenDialogue.concat("\n" + "\n" + "Quest Objective:" +"\n" + "Fetch " +classNameDeconstructor(item)+ " from " +classNameDeconstructor(npc)+ " in the " + level + ".");

                DataHandler.getInstance().createSpeakFetchObjective(questNode, document, objectiveName, npc, item, level);

                objectiveName = "speak to " +DataHandler.getInstance().getNPCName(questGiverName);

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, motive.questCompleteDialogue);

                break;

            case "use_item":

                item = questGiver.getRandomItem(motiveType, storyPhase);

                objectiveName = "use " + item;

                motive.questNotGivenDialogue = motive.questNotGivenDialogue.concat("\n" + "\n" + "Quest Objective:" +"\n" + "Use the " + classNameDeconstructor(item) + " item.");

                DataHandler.getInstance().createUseItemObjective(questNode, document, objectiveName, item, level);

                objectiveName = "speak to " +DataHandler.getInstance().getNPCName(questGiverName);

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;
        }


    }

    public String classNameDeconstructor(String className) {
        String s = className;
        StringBuilder out = new StringBuilder(s);
        Pattern p = Pattern.compile("[A-Z]");
        Matcher m = p.matcher(s);
        int extraFeed = 0;
        while(m.find()){
            if(m.start()!=0){
                out = out.insert(m.start()+extraFeed, " ");
                extraFeed++;
            }
        }

        return out.toString();
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


                    NodeList actTwoOpeningStoryNodeList    = motiveElement.getElementsByTagName("actTwoOpeningStory");

                    if (actTwoOpeningStoryNodeList.getLength() != 0) {
                        String actTwoOpeningStory = actTwoOpeningStoryNodeList.item(0).getTextContent();
                        //System.out.println(actTwoOpeningStory);
                        questGiver.createNewMotiveWithDialogue(motiveName, storyPhase, questNotGivenDialogue, questGivenDialogue, questCompleteDialogue, actTwoOpeningStory);
                    } else {
                        questGiver.createNewMotive(motiveName, storyPhase, questNotGivenDialogue, questGivenDialogue, questCompleteDialogue);
                    }



                    NodeList actThreeOpeningStoryNodeList  = motiveElement.getElementsByTagName("actThreeOpeningStory");

                    if (actThreeOpeningStoryNodeList.getLength() != 0) {
                        String actThreeOpeningStory = actThreeOpeningStoryNodeList.item(0).getTextContent();
                        //System.out.println(actThreeOpeningStory);
                        questGiver.createNewMotiveWithDialogue(motiveName, storyPhase, questNotGivenDialogue, questGivenDialogue, questCompleteDialogue, actThreeOpeningStory);
                    } else {
                        questGiver.createNewMotive(motiveName, storyPhase, questNotGivenDialogue, questGivenDialogue, questCompleteDialogue);
                    }

                    //questGiver.createNewMotive(motiveName, storyPhase, questNotGivenDialogue, questGivenDialogue, questCompleteDialogue);

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

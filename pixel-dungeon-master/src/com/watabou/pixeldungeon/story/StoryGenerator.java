package com.watabou.pixeldungeon.story;


import com.watabou.pixeldungeon.quests.Quest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class StoryGenerator {


    private static StoryGenerator storyGenerator;

    public QuestGiver questGiver;
    public static String actOneOpeningStory;

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



    public ArrayList<String> heroBackgrounds = new ArrayList<>();

    public String heroBackground;

    public void populateHeroBackgrounds() {

        heroBackgrounds.add("Knight");
        heroBackgrounds.add("Peasant");
        heroBackgrounds.add("Explorer");
        heroBackgrounds.add("Gentleman");

    }

    public void selectHeroBackground() {

        Collections.shuffle(heroBackgrounds);

        heroBackground = heroBackgrounds.get(0);

        heroBackground = "Knight";
    }




    public ArrayList<String> levelOneQuestGivers = new ArrayList<>();

    public String levelOneQuestGiver;

    public void populateLevelOneQuestGivers() {

        switch (heroBackground) {

            case "Knight":
                levelOneQuestGivers.add("Mentor");
                levelOneQuestGivers.add("Damsel"); //in distress
                levelOneQuestGivers.add("Stranger");
                break;

            case "Peasant":
                levelOneQuestGivers.add("Relative");
                levelOneQuestGivers.add("Friend");
                levelOneQuestGivers.add("Farmer");

            case "Explorer":
                levelOneQuestGivers.add("Stranger");
                levelOneQuestGivers.add("Partner");
                levelOneQuestGivers.add("Civilian");

            case "Gentleman":
                levelOneQuestGivers.add("Hot Babe 1");
                levelOneQuestGivers.add("Hot Babe 2");
                levelOneQuestGivers.add("Hot Babe 3");

        }


    }

    public void selectLevelOneQuestGiver() {

        Collections.shuffle(levelOneQuestGivers);

        levelOneQuestGiver = levelOneQuestGivers.get(0);

        levelOneQuestGiver = "Mentor";

    }


    public String shadowLandsStory;
    public String cavesStory;
    public String forestStory;
    public String castleStory;
    public String fieldsStory;
    public String dungeonStory;

    public String questNotGivenDialogue;
    public String questGivenDialogue;
    public String questCompletedDialogue;


    public void setLevelStories() {

        switch (heroBackground) {

            case "Knight":
                shadowLandsStory = "The shadow lands is a dangerous place, even for a knight.";
                forestStory = "The forests have always been a beautiful part of the Kingdom.";
                cavesStory = "These caves were once used for mining materials used to make weapons and armor for knights such as yourself.";
                castleStory = "The castle was one your home, before the king went bonkers.";
                fieldsStory = "The fields are home to many of the peasants you spit on on a daily basis.";
                dungeonStory = "Housing some of the most dangerous prisoners, the Dungeon is a fortress of scum.";

                switch (levelOneQuestGiver) {

                    case "Mentor":
                        shadowLandsStory = shadowLandsStory.concat(" You're not sure why your mentor asked you to meet in a place like this.");
                        forestStory = forestStory.concat(" Your mentor has asked for you two to meet here.");
                        cavesStory = cavesStory.concat(" These caves have been deserted for years, why would the mentor ask you to come down here?");
                        castleStory = castleStory.concat(" Your mentor would like to meet you here, perhaps to reminisce.");
                        fieldsStory = fieldsStory.concat(" Maybe your mentor wants you to make fun of some peasants with him?");
                        dungeonStory = dungeonStory.concat(" It's not your turn for guard duty, why would the mentor want to meet you here?");
                        break;

                    case "Damsel":
                        shadowLandsStory = shadowLandsStory.concat(" The princess has always been one for danger, you should find her.");
                        forestStory = forestStory.concat(" The princess always chooses the most romantic places to meet. You should find her.");
                        cavesStory = cavesStory.concat(" Not the most romantic of places, but at least you and the Princess will be alone here. You should find her.");
                        castleStory = castleStory.concat(" The princess has been homeless since the mad king, maybe she came back to try and not be homeless. Find her.");
                        fieldsStory = fieldsStory.concat(" The princess does enjoy watching me mistreat the common folk, you should find her and get mistreating.");
                        dungeonStory = dungeonStory.concat(" This isn't exactly a place for a princess, you should find her.");
                        break;

                    case "Stranger":
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

                switch (levelOneQuestGiver) {

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

                switch (levelOneQuestGiver) {

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


            case "Gentleman":

                shadowLandsStory = "A well-trained knight barely has a chance of survival here, you have my condolences.";
                forestStory = "The over abundance of trees makes the Forest great for gathering firewood.";
                cavesStory = "These caves were once used for mining materials used to make weapons and armor for knights such as yourself.";
                castleStory = "The castle was one your home, before the king went bonkers.";
                fieldsStory = "The fields are home to many of the peasants you spit on on a daily basis.";
                dungeonStory = "Housing some of the most dangerous prisoners, the Dungeon is a fortress of scum.";

                switch (levelOneQuestGiver) {

                    case "Hot Babe 1":
                        shadowLandsStory = shadowLandsStory.concat(" You're not sure why your mentor asked you to meet in a place like this.");
                        forestStory = forestStory.concat(" Your mentor has asked for you two to meet here.");
                        cavesStory = cavesStory.concat(" These caves have been deserted for years, why would the mentor ask you to come down here?");
                        castleStory = castleStory.concat(" Your mentor would like to meet you here, perhaps to reminisce.");
                        fieldsStory = fieldsStory.concat(" Maybe your mentor wants you to make fun of some peasants with him?");
                        dungeonStory = dungeonStory.concat(" It's not your turn for guard duty, why would the mentor want to meet you here?");
                        break;

                    case "Hot Babe 2":
                        shadowLandsStory = shadowLandsStory.concat(" The princess has always been one for danger, you should find her.");
                        forestStory = forestStory.concat(" The princess always chooses the most romantic places to meet. You should find her.");
                        cavesStory = cavesStory.concat(" Not the most romantic of places, but at least you and the Princess will be alone here. You should find her.");
                        castleStory = castleStory.concat(" The princess has been homeless since the mad king, maybe she came back to try and not be homeless. Find her.");
                        fieldsStory = fieldsStory.concat(" The princess does enjoy watching me mistreat the common folk, you should find her and get mistreating.");
                        dungeonStory = dungeonStory.concat(" This isn't exactly a place for a princess, you should find her.");
                        break;

                    case "Hot Babe 3":
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


    String questOneMotive;

    public void setQuestMotives() {

        Random random = new Random();

        int motiveSelector = random.nextInt((2 - 1) + 1) + 1;

        switch (levelOneQuestGiver) {

            case "Mentor":

                if (motiveSelector == 1) {
                    questOneMotive = "train";
                } else {
                    questOneMotive = "powerful item";
                }

                break;

            case "Damsel":

                if (motiveSelector == 1) {
                    questOneMotive = "retrieve lost item";
                } else {
                    questOneMotive = "rescue";
                }

                break;

            case "Stranger":

                if (motiveSelector == 1) {
                    questOneMotive = "looking for friend";
                } else {
                    questOneMotive = "wants food";
                }

                break;

        }



    }

    public void setFirstQuest(Element questNode, Document doc) {

        Random random = new Random();

        int objectiveSelector = random.nextInt((2 - 1) + 1) + 1;

        switch (questOneMotive) {

            case "train":

                if (objectiveSelector == 1) {

                    String questNotGiven  = "You need serious training, kill 5 bats to get stronger.";
                    String questGiven     = "What are you doing here? Go kill some bats.";
                    String questCompleted = "Not bad, you still suck though.";

                    DataHandler.getInstance().createQuestWithQuestGiver(questNode, doc, "You need to train boi", "Ghost", "40xp", levelOne,
                            "none", questNotGiven, questGiven, questCompleted);

                    DataHandler.getInstance().createKillObjective(questNode, doc, "Kill Bats", "Bat", "5", levelOne);

                    DataHandler.getInstance().createSpeakObjective(questNode, doc, "Return to mentor", "Ghost", levelOne, questCompleted);

                } else {

                    String questNotGiven  = "It's said that some bats have powerful items in their pockets. Kill some until you find this item, then bring it to me.";
                    String questGiven     = "What are you doing here? Go search the pockets of bats.";
                    String questCompleted = "Let's see what special properties this item has.";

                    DataHandler.getInstance().createQuestWithQuestGiver(questNode, doc, "Bat pockets", "Ghost", "40xp", levelOne,
                            "none", questNotGiven, questGiven, questCompleted);

                    DataHandler.getInstance().createKillFetchObjective(questNode, doc, "Kill Bats", "Bat", "5", "PhantomFish",levelOne);

                    DataHandler.getInstance().createSpeakObjective(questNode, doc, "Return to mentor", "Ghost", levelOne, questCompleted);

                }

                break;

            case "powerful item":

                if (objectiveSelector == 1) {

                    String questNotGiven  = "Legend speaks of a powerful item, to find it we'll need information. Go fetch some.";
                    String questGiven     = "What are you doing here? Go collect some info.";
                    String questCompleted = "Not bad, you still suck though.";

                    DataHandler.getInstance().createQuestWithQuestGiver(questNode, doc, "You need to info boi", "Ghost", "40xp", levelOne,
                            "none", questNotGiven, questGiven, questCompleted);

                    DataHandler.getInstance().createFetchObjective(questNode, doc, "Fetch info", "Letter", levelOne);

                    DataHandler.getInstance().createSpeakObjective(questNode, doc, "Return to mentor", "Ghost", levelOne, questCompleted);

                } else {

                    String questNotGiven  = "Legend speaks of a powerful item, to find it we'll need information. KIll some enemies until you learn something.";
                    String questGiven     = "What are you doing here? Go collect some info.";
                    String questCompleted = "Not bad, you still suck though.";

                    DataHandler.getInstance().createQuestWithQuestGiver(questNode, doc, "You need to info boi", "Ghost", "40xp", levelOne,
                            "none", questNotGiven, questGiven, questCompleted);

                    DataHandler.getInstance().createKillFetchObjective(questNode, doc, "Fetch info from enemies", "Shaman", "5", "Letter",  levelOne);

                    DataHandler.getInstance().createSpeakObjective(questNode, doc, "Return to mentor", "Ghost", levelOne, questCompleted);

                }

                break;

        }



    }

    public String firstStory;

    public void setActOpeningStory() {

        switch (levelOne) {

            case "Shadow Lands":
                firstStory = shadowLandsStory;
                break;
            case "Forest":
                firstStory = forestStory;
                break;
            case "Caves":
                firstStory = cavesStory;
                break;
            case "Dungeon":
                firstStory = dungeonStory;
                break;
            case "Fields":
                firstStory = fieldsStory;
                break;
            case "Castle":
                firstStory = castleStory;
                break;

        }

    }




    public void doStoryStuff() {

        populateQuestLevels();
        selectActLevels();

        populateHeroBackgrounds();
        selectHeroBackground();

        populateLevelOneQuestGivers();
        selectLevelOneQuestGiver();

        setLevelStories();

        setActOpeningStory();

        setQuestMotives();

    }



    public void createActOne(Element firstQuest, Element secondQuest, Document document) {

        createKnight(); //will be random

        String motiveName = questGiver.getRandomMotiveType("NORMAL");
        //String objectiveType = questGiver.getRandomObjective(motiveName, "NORMAL");

        generateQuestDialogue(motiveName);

        questCreator(firstQuest, document, "questOne", levelOne, "none", questNotGivenDialogue, questGivenDialogue, questCompletedDialogue);

        objectiveCreator(motiveName, "NORMAL", levelOne, "Town", questGiver.getRandomObjective(motiveName, "NORMAL"), firstQuest, document);

        DataHandler.getInstance().setQuestDialogue("actOne.xml", "questOne", questNotGivenDialogue);


        motiveName = questGiver.getRandomMotiveType("SERIOUS");

        generateQuestDialogue(motiveName);

        questCreator(secondQuest, document, "questTwo", "Town", "questOne", questNotGivenDialogue, questGivenDialogue, questCompletedDialogue);

        objectiveCreator(motiveName, "SERIOUS", levelTwo, "Town", questGiver.getRandomObjective(motiveName, "SERIOUS"), secondQuest, document);

        DataHandler.getInstance().setQuestDialogue("actOne.xml", "questTwo", questNotGivenDialogue);

    }


    public void createActTwo(Element firstQuest, Element secondQuest, Document document) {

        String motiveName = questGiver.getRandomMotiveType("CATASTROPHE");

        generateQuestDialogue(motiveName);

        questCreator(firstQuest, document, "questThree", "Town","none", questNotGivenDialogue, questGivenDialogue, questCompletedDialogue);

        //String motive = questGiver.getRandomMotiveType("CATASTROPHE");

        objectiveCreator(motiveName, "CATASTROPHE", levelThree, "Town", questGiver.getRandomObjective(motiveName, "CATASTROPHE"), firstQuest, document);

        DataHandler.getInstance().setQuestDialogue("actTwo.xml", "questThree", questNotGivenDialogue);



        motiveName = questGiver.getRandomMotiveType("INVESTIGATE");

        generateQuestDialogue(motiveName);

        questCreator(secondQuest, document, "questFour", "Town","questThree", questNotGivenDialogue, questGivenDialogue, questCompletedDialogue);

        objectiveCreator(motiveName, "INVESTIGATE", levelFour, "Town", questGiver.getRandomObjective(motiveName, "INVESTIGATE"), secondQuest, document);

        DataHandler.getInstance().setQuestDialogue("actTwo.xml", "questFour", questNotGivenDialogue);
    }

    public void createActThree(Element firstQuest, Element secondQuest, Document document) {

        String motiveName = questGiver.getRandomMotiveType("STRENGTHEN");

        generateQuestDialogue(motiveName);

        questCreator(firstQuest, document, "questFive", "Town","none", questNotGivenDialogue, questGivenDialogue, questCompletedDialogue);

        objectiveCreator(motiveName, "STRENGTHEN", levelFive, "Town", questGiver.getRandomObjective(motiveName, "STRENGTHEN"), firstQuest, document);

        DataHandler.getInstance().setQuestDialogue("actThree.xml", "questFive", questNotGivenDialogue);



        motiveName = questGiver.getRandomMotiveType("RESOLVE");

        generateQuestDialogue(motiveName);

        questCreator(secondQuest, document, "questSix", "Town","questFive", questNotGivenDialogue, questGivenDialogue, questCompletedDialogue);

        objectiveCreator(motiveName, "RESOLVE", levelSix, "Town", questGiver.getRandomObjective(motiveName, "RESOLVE"), secondQuest, document);

        DataHandler.getInstance().setQuestDialogue("actThree.xml", "questSix", questNotGivenDialogue);

    }




    public void questCreator(Element questNode, Document document, String questName, String levelName, String prerequisiteQuestName, String questNotGivenDialogue, String questGivenDialogue, String questCompletedDialogue) {

        String questGiverName = questGiver.name;

        DataHandler.getInstance().createQuestWithQuestGiver(questNode, document, questName, questGiverName, "40xp", levelName, prerequisiteQuestName, questNotGivenDialogue, questGivenDialogue, questCompletedDialogue);

    }

    public void objectiveCreator(String motiveType, String storyPhase, String level, String returnLevel, String objectiveType, Element questNode, Document document) {

        String questGiverName = questGiver.name;

        switch (objectiveType) {

            case "kill":

                String enemy = questGiver.getRandomEnemy(motiveType, storyPhase);

                String objectiveName = "kill " +enemy;

                Integer amountToKill = getRandomAmountToKill(3, 5);

                questNotGivenDialogue = questNotGivenDialogue.concat(" Kill " +amountToKill+ " " +enemy+ "in the " +level+ ".");

                DataHandler.getInstance().createKillObjective(questNode, document, objectiveName, enemy, amountToKill.toString(), level);

                objectiveName = "speak to " +questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;

            case "kill_fetch":

                enemy = questGiver.getRandomEnemy(motiveType, storyPhase);

                String item = questGiver.getRandomItem(motiveType, storyPhase);

                objectiveName = "find a " +item+ " by killing " +enemy;

                amountToKill = getRandomAmountToKill(4, 6);

                questNotGivenDialogue = questNotGivenDialogue.concat(" Kill " +enemy+ "'s in the " +level+ " to find" +item+ ".");

                DataHandler.getInstance().createKillFetchObjective(questNode, document, objectiveName, enemy, amountToKill.toString(), item, level);

                objectiveName = "speak to " +questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;

            case "fetch":

                item = questGiver.getRandomItem(motiveType, storyPhase);

                objectiveName = "fetch " +item;

                questNotGivenDialogue = questNotGivenDialogue.concat(" Find " +item+ " somewhere in the " +level+ ".");

                DataHandler.getInstance().createFetchObjective(questNode, document, objectiveName, item, level);

                objectiveName = "speak to " +questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;

            case "speak":

                String npc = questGiver.getRandomNPC(motiveType, storyPhase);

                objectiveName = "speak to  " +npc;

                questNotGivenDialogue = questNotGivenDialogue.concat(" Speak to " +npc+ " in the " +level+ ".");

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, npc, level, "some dialogue");

                objectiveName = "speak to " +questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;

            case "speak_fetch":

                npc = questGiver.getRandomNPC(motiveType, storyPhase);

                item = questGiver.getRandomItem(motiveType, storyPhase);

                objectiveName = "fetch " +item+ " from " +npc;

                questNotGivenDialogue = questNotGivenDialogue.concat(" Fetch " +item+ " from " +npc+ " in the " +level+ ".");

                DataHandler.getInstance().createSpeakFetchObjective(questNode, document, objectiveName, npc, item, level);

                objectiveName = "speak to " +questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;

            case "use_item":

                item = questGiver.getRandomItem(motiveType, storyPhase);

                objectiveName = "use " +item;

                questNotGivenDialogue = questNotGivenDialogue.concat(" Use the " +item+ " item.");

                DataHandler.getInstance().createUseItemObjective(questNode, document, objectiveName, item, level);

                objectiveName = "speak to " +questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;
        }


    }


    public void questNotGivenDialogueGenerator(String motiveName) {

        switch (motiveName) {

            //Mentor motive dialogues
            case "train":
                questNotGivenDialogue = "You need to train.";
                break;
            case "simple task":
                questNotGivenDialogue = "This is a simple task to get you started.";
                break;
            case "gang problem":
                questNotGivenDialogue = "There is a gang that is terrorizing these lands.";
                break;
            case "useful item":
                questNotGivenDialogue = "I've heard tales of a powerful item that could serve you well on your well on your adventures.";
                break;

            case "boss killing people":
                questNotGivenDialogue = "There is a powerful enemy that must be stopped.";
                break;
            case "demon awakened":
                questNotGivenDialogue = "There is an evil force that has been awakened.";
                break;
            case "find information":
                questNotGivenDialogue = "In order to defeat this evil, you must find out more about it.";
                break;
            case "take out henchmen":
                questNotGivenDialogue = "Weaken the enemy by taking out their most trusted henchmen.";
                break;

            case "obtain powerful item":
                questNotGivenDialogue = "In order to defeat this enemy, you're going to need an item of legendary power.";
                break;
            case "destroy minions":
                questNotGivenDialogue = "You must strike at the enemy's generals before they take over the Kingdom!.";
                break;
            case "kill boss":
                questNotGivenDialogue = "Now, go finish this!.";
                break;
            case "cleanse land":
                questNotGivenDialogue = "You must cleanse the lands of this evil.";
                break;

        }


    }

    public void questGivenDialogueGenerator(String motiveName) {

        switch (motiveName) {

            //Mentor motive dialogues
            case "train":
                questGivenDialogue = "Go train!";
                break;
            case "simple task":
                questGivenDialogue = "Don't just stand there, I've given you a task!";
                break;
            case "gang problem":
                questGivenDialogue = "Go take care of that gang!";
                break;
            case "powerful item":
                questGivenDialogue = "That item won't find itself!";
                break;

            case "boss killing people":
                questGivenDialogue = "Didn't I just say that there's a powerful enemy at large?";
                break;
            case "demon awakened":
                questGivenDialogue = "Sure, we can talk while there's a scary enemy destroying the lands.";
                break;
            case "find information":
                questGivenDialogue = "Go find out more about this!";
                break;
            case "take out henchmen":
                questGivenDialogue = "Go kill some henchmen!";
                break;

            case "obtain powerful item":
                questGivenDialogue = "You're not getting to the final level with that gear, go get that legendary item!";
                break;
            case "destroy minions":
                questGivenDialogue = "No scary generals here, go find them!";
                break;
            case "kill boss":
                questGivenDialogue = "What are you waiting for??";
                break;
            case "cleanse land":
                questGivenDialogue = "If you're okay with everyone dying, let's keep this conversation going.";
                break;

        }


    }

    public void questCompleteDialogueGenerator(String motiveName) {

        switch (motiveName) {

            //Mentor motive dialogues
            case "train":
                questCompletedDialogue = "You've trained well, you're ready for something more challenging.";
                break;
            case "simple task":
                questCompletedDialogue = "The next quest won't be so easy! You're ready.";
                break;
            case "gang problem":
                questCompletedDialogue = "Good job! They won't try that again soon.";
                break;
            case "powerful item":
                questCompletedDialogue = "This item should serve your adventures well.";
                break;

            case "boss killing people":
                questCompletedDialogue = "You've got a tough road ahead";
                break;
            case "demon awakened":
                questCompletedDialogue = "It's worse than I thought, we'll have to prepare for this.";
                break;
            case "find information":
                questCompletedDialogue = "I believe we're ready for the next step.";
                break;
            case "take out henchmen":
                questCompletedDialogue = "I believe we're ready to take it to the enemy.";
                break;

            case "obtain powerful item":
                questCompletedDialogue = "You are now ready for the final battle.";
                break;
            case "destroy minions":
                questCompletedDialogue = "You have greatly weakened the enemy. Now is the time to strike!";
                break;
            case "kill boss":
                questCompletedDialogue = "The land belongs to the people once more!";
                break;
            case "cleanse land":
                questCompletedDialogue = "Thanks to you, we are free from this evil!";
                break;

        }



    }

    public void generateQuestDialogue(String motiveName) {
        questNotGivenDialogueGenerator(motiveName);
        questGivenDialogueGenerator(motiveName);
        questCompleteDialogueGenerator(motiveName);
    }

    public int getRandomAmountToKill(int min, int max) {
        Random random = new Random();

        return random.nextInt((max + 1) - min) + min;
    }

    // CREATING HERO BACKGROUNDS AND QUEST GIVERS + MOTIVES
    public void createKnight() {

        HeroBackground knight = new HeroBackground("Knight");


        /**
         * START Mentor Quest Giver
         */
        QuestGiver mentorQuestGiver = new QuestGiver("Ghost", "Blacksmith");

        /**
         * START Act One Story Phases
         */
        //NORMAL story phase motives
        mentorQuestGiver.createNewMotive("train", "NORMAL");
        mentorQuestGiver.createNewMotive("simple task", "NORMAL");

        //train motive
        mentorQuestGiver.addMotiveObjectives("train", "NORMAL","kill", "kill_fetch", "fetch");
        mentorQuestGiver.addEnemies("train", "NORMAL","Bat", "Crab", "Rat", "Gnoll");
        mentorQuestGiver.addItems("train", "NORMAL", "ScrollOfEnchantment", "PotionOfHealing", "PotionOfLevitation");

        //simple task motive
        mentorQuestGiver.addMotiveObjectives("simple task", "NORMAL", "fetch", "kill_fetch");
        mentorQuestGiver.addEnemies("simple task", "NORMAL","Bat", "Crab", "Bandit", "Albino", "Gnoll", "Thief");
        mentorQuestGiver.addItems("simple task", "NORMAL",  "RingOfElements", "ScrollOfMirrorImage", "Longsword");


        //SERIOUS story phase motives
        mentorQuestGiver.createNewMotive("gang problem", "SERIOUS");
        mentorQuestGiver.createNewMotive("useful item", "SERIOUS");

        //gang problem motive
        mentorQuestGiver.addMotiveObjectives("gang problem", "SERIOUS", "kill", "kill_fetch");
        mentorQuestGiver.addEnemies("gang problem", "SERIOUS", "Bandit",  "Shaman", "Thief");
        mentorQuestGiver.addItems("gang problem", "SERIOUS","RatSkull", "DarkGold", "Pickaxe");

        //powerful item motive
        mentorQuestGiver.addMotiveObjectives("useful item", "SERIOUS", "fetch", "kill_fetch");
        mentorQuestGiver.addEnemies("useful item", "SERIOUS", "Skeleton",  "Eye", "Brute");
        mentorQuestGiver.addItems("useful item", "SERIOUS","WarHammer", "RingOfEvasion", "RingOfHaste", "MailArmor");
        /**
         * END Act One Story Phases
         */

        /**
         * START Act Two Story Phases
         */
        //CATASTROPHE story phase motives
        mentorQuestGiver.createNewMotive("boss killing people", "CATASTROPHE");
        mentorQuestGiver.createNewMotive("demon awakened", "CATASTROPHE");

        //boss killing people motive
        mentorQuestGiver.addMotiveObjectives("boss killing people", "CATASTROPHE","kill", "kill_fetch", "fetch");
        mentorQuestGiver.addEnemies("boss killing people", "CATASTROPHE","Skeleton", "Golem", "Brute");
        mentorQuestGiver.addItems("boss killing people", "CATASTROPHE", "RatSkull","DwarfToken");

        //Demon awakened motive
        mentorQuestGiver.addMotiveObjectives("demon awakened", "CATASTROPHE", "kill", "kill_fetch");
        mentorQuestGiver.addEnemies("demon awakened", "CATASTROPHE","Warlock", "Shaman", "Succubus");
        mentorQuestGiver.addItems("demon awakened", "CATASTROPHE", "DarkGold", "RatSkull", "DwarfToken");


        //INVESTIGATE story phase motives
        mentorQuestGiver.createNewMotive("find information", "INVESTIGATE");
        mentorQuestGiver.createNewMotive("take out henchmen", "INVESTIGATE");

        //find information motive
        mentorQuestGiver.addMotiveObjectives("find information", "INVESTIGATE", "kill_fetch", "fetch");
        mentorQuestGiver.addEnemies("find information", "INVESTIGATE","Skeleton", "Golem", "Brute", "Eye");
        mentorQuestGiver.addItems("find information", "INVESTIGATE", "Letter", "CorpseDust", "Dagger");

        //take out henchmen motive
        mentorQuestGiver.addMotiveObjectives("take out henchmen", "INVESTIGATE", "kill", "kill_fetch");
        mentorQuestGiver.addEnemies("take out henchmen", "INVESTIGATE","Warlock", "Shaman", "Succubus", "Golem", "Brute");
        mentorQuestGiver.addItems("take out henchmen", "INVESTIGATE", "DarkGold", "RatSkull", "DwarfToken");
        /**
         * END Act Two Story Phases
         */


        /**
         * START Act Three Story Phases
         */
        //STRENGTHEN story phase motives
        mentorQuestGiver.createNewMotive("obtain powerful item", "STRENGTHEN");
        mentorQuestGiver.createNewMotive("destroy minions", "STRENGTHEN");

        //obtain powerful item motive
        mentorQuestGiver.addMotiveObjectives("obtain powerful item", "STRENGTHEN", "fetch", "kill_fetch");
        mentorQuestGiver.addEnemies("obtain powerful item", "STRENGTHEN","Shaman", "Succubus", "Golem", "Monk");
        mentorQuestGiver.addItems("obtain powerful item", "STRENGTHEN", "PlateArmor", "MailArmor", "Spear", "Sword");

        //destroy minions motive
        mentorQuestGiver.addMotiveObjectives("destroy minions", "STRENGTHEN", "kill", "kill_fetch");
        mentorQuestGiver.addEnemies("destroy minions", "STRENGTHEN","Shaman", "Succubus", "Monk");
        mentorQuestGiver.addItems("destroy minions", "STRENGTHEN",  "PlateArmor", "MailArmor", "RingOfEvasion");



        //RESOLVE story phase motives
        mentorQuestGiver.createNewMotive("kill boss", "RESOLVE");
        mentorQuestGiver.createNewMotive("cleanse land", "RESOLVE");

        //boss killing people motive
        mentorQuestGiver.addMotiveObjectives("kill boss", "RESOLVE","kill");
        mentorQuestGiver.addEnemies("kill boss", "RESOLVE","King", "Monk", "Eye");

        //cleanse land motive
        mentorQuestGiver.addMotiveObjectives("cleanse land", "RESOLVE",  "kill_fetch");
        mentorQuestGiver.addEnemies("cleanse land", "RESOLVE","Eye", "Monk");
        mentorQuestGiver.addItems("cleanse land", "RESOLVE", "CorpseDust", "DriedRose");
        /**
         * END Act Three Story Phases
         */

        /**
         * END Mentor Quest Giver
         */



        knight.actOneQuestGivers.add(mentorQuestGiver);

        questGiver = mentorQuestGiver;


        /**
         * START Princess Quest Giver
         */
        QuestGiver princessQuestGiver = new QuestGiver("Imp", "Wandmaker");


        /**
         * START Act One Story Phases
         */
        //NORMAL story phase motives
        princessQuestGiver.createNewMotive("lost item", "NORMAL");
        princessQuestGiver.createNewMotive("defend honor", "NORMAL");

        //lost item motive
        princessQuestGiver.addMotiveObjectives("lost item", "NORMAL", "fetch", "kill_fetch");
        princessQuestGiver.addEnemies("lost item", "NORMAL", "Bat", "Gnoll", "Crab");
        princessQuestGiver.addItems("lost item", "NORMAL", "RingOfElements", "RingOfEvasion", "RingOfHaste", "RingOfHerbalism");

        //defend honor motive
        princessQuestGiver.addMotiveObjectives("defend honor", "NORMAL", "kill", "kill_fetch");
        princessQuestGiver.addEnemies("defend honor", "NORMAL", "Thief", "Bandit", "Rat");
        princessQuestGiver.addItems("defend honor", "NORMAL", "DriedRose", "RingOfEvasion");


        //SERIOUS story phase motives
        princessQuestGiver.createNewMotive("", "SERIOUS");
        princessQuestGiver.createNewMotive("", "SERIOUS");



        Collections.shuffle(knight.actOneQuestGivers);

        questGiver = knight.actOneQuestGivers.get(0);


    }





}
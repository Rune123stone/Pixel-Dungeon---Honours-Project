package com.watabou.pixeldungeon.story;


import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.quests.Quest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class StoryVariables {


    private static StoryVariables storyVariables;

    public QuestGiver questGiver;
    public static String actOneOpeningStory;

    private StoryVariables() {
    }


    public static StoryVariables getInstance() {
        if (storyVariables == null) {
            storyVariables = new StoryVariables();
        }
        return storyVariables;
    }



    //a list of levels that can be used for an act's quests
    public ArrayList<String> questLevels = new ArrayList<>();

    public String levelOne;
    public String levelTwo;

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

        System.out.println(levelOne);
        System.out.println(levelTwo);

        for (String s : questLevels) {
            System.out.println(s);
        }

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



    public void createActOne(Element questNode, Document document) {

        createKnight(); //will be random

        questCreator(questNode, document);

        String motive = questGiver.getRandomMotiveType();

        objeciveCreator(motive, questGiver.getRandomObjective(motive), questNode, document);

    }

    public void createKnight() {

        HeroBackground knight = new HeroBackground("Knight");

        //MENTOR
        QuestGiver knightQuestGiver = new QuestGiver("Ghost", "Blacksmith");

        knightQuestGiver.createNewMotive("train");
        knightQuestGiver.createNewMotive("powerful item");

        knightQuestGiver.addMotiveObjectives("train", "kill", "kill_fetch", "fetch");
        knightQuestGiver.addMotiveObjectives("powerful item", "fetch", "kill_fetch");

        knightQuestGiver.addEnemies("train", "Bat", "Crab", "Bandit", "Albino", "Gnoll", "Shaman", "Thief");
        knightQuestGiver.addBossEnemies("train", "King", "Succubus", "Monk", "Brute", "Warlock", "Eye");
        knightQuestGiver.addItems("train", "CorpseDust", "DarkGold", "DriedRose", "DwarfToken", "Letter", "Pickaxe", "RatSkull");

        knightQuestGiver.addEnemies("powerful item",  "Crab", "Bandit", "Albino", "Gnoll", "Shaman", "Thief");
        knightQuestGiver.addBossEnemies("powerful item", "King", "Succubus", "Monk", "Brute", "Warlock", "Eye");
        knightQuestGiver.addItems("powerful item","DriedRose", "DwarfToken", "Pickaxe", "RatSkull");

        knight.actOneQuestGivers.add(knightQuestGiver);

        //DAMSEL
        knightQuestGiver = new QuestGiver("Imp", "Wandmaker");

        knightQuestGiver.createNewMotive("lost item");
        knightQuestGiver.createNewMotive("defend honour");

        knightQuestGiver.addMotiveObjectives("lost item",  "kill_fetch", "fetch");
        knightQuestGiver.addMotiveObjectives("defend honour", "kill", "kill_fetch");

        knightQuestGiver.addEnemies("lost item", "Bat", "Crab", "Bandit", "Albino", "Gnoll", "Shaman", "Thief");
        knightQuestGiver.addBossEnemies("lost item", "King", "Succubus", "Monk", "Brute", "Warlock", "Eye");
        knightQuestGiver.addItems("lost item", "DriedRose",  "Letter", "RatSkull");

        knightQuestGiver.addEnemies("defend honour",  "Bandit", "Thief");
        knightQuestGiver.addBossEnemies("defend honour", "King", "Succubus", "Monk", "Brute", "Warlock", "Eye");
        knightQuestGiver.addItems("defend honour","DriedRose", "DwarfToken", "Pickaxe", "RatSkull");

        knight.actOneQuestGivers.add(knightQuestGiver);



        Collections.shuffle(knight.actOneQuestGivers);

        questGiver = knight.actOneQuestGivers.get(0);


    }

    public void questCreator(Element questNode, Document document) {

        String questGiverName = questGiver.name;

        DataHandler.getInstance().createQuestWithQuestGiver(questNode, document, "Your first quest", questGiverName, "40xp", levelOne, "none", "" +
                "I have quest", "I gave quest", "you done quest");

    }

    public void objeciveCreator(String motiveType, String objectiveType, Element questNode, Document document) {

        String enemy = questGiver.getRandomEnemy(motiveType);
        String item = questGiver.getRandomItem(motiveType);
        String questGiverName = questGiver.name;

        switch (objectiveType) {

            case "kill":

                String objectiveName = "kill " +enemy;

                DataHandler.getInstance().createKillObjective(questNode, document, objectiveName, enemy, "4", levelOne);

                objectiveName = "speak to " +questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, levelOne, "done");

                break;

            case "kill_fetch":

                objectiveName = "find a " +item+ " by killing " +enemy;

                DataHandler.getInstance().createKillFetchObjective(questNode, document, objectiveName, enemy, "5", item, levelOne);

                objectiveName = "speak to " +questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, levelOne, "done");

                break;

            case "fetch":

                objectiveName = "fetch " +item;

                DataHandler.getInstance().createFetchObjective(questNode, document, objectiveName, item, levelOne);

                objectiveName = "speak to " +questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, levelOne, "done");

                break;

        }


    }







}

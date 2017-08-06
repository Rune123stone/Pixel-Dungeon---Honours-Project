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

        questCreator(firstQuest, document, "questOne", "none");

        String motive = questGiver.getRandomMotiveType("NORMAL");

        objeciveCreator(motive, "NORMAL", levelOne, levelOne, questGiver.getRandomObjective(motive, "NORMAL"), firstQuest, document);


        questCreator(secondQuest, document, "questTwo", "questOne");

        motive = questGiver.getRandomMotiveType("SERIOUS");

        objeciveCreator(motive, "SERIOUS", levelTwo, levelOne, questGiver.getRandomObjective(motive, "SERIOUS"), secondQuest, document);

    }


    public void createActTwo(Element firstQuest, Element secondQuest, Document document) {

        questCreator(firstQuest, document, "questThree", "none");

        String motive = questGiver.getRandomMotiveType("CATASTROPHE");

        objeciveCreator(motive, "CATASTROPHE", levelThree, levelOne, questGiver.getRandomObjective(motive, "CATASTROPHE"), firstQuest, document);


        questCreator(secondQuest, document, "questFour", "questThree");

        motive = questGiver.getRandomMotiveType("INVESTIGATE");

        objeciveCreator(motive, "INVESTIGATE", levelFour, levelOne, questGiver.getRandomObjective(motive, "INVESTIGATE"), secondQuest, document);

    }

    public void createActThree(Element firstQuest, Element secondQuest, Document document) {

        questCreator(firstQuest, document, "questFive", "none");

        String motive = questGiver.getRandomMotiveType("BOSS");

        objeciveCreator(motive, "BOSS", levelFive, levelOne, questGiver.getRandomObjective(motive, "BOSS"), firstQuest, document);


        questCreator(secondQuest, document, "questSix", "questFive");

        motive = questGiver.getRandomMotiveType("PARTY");

        objeciveCreator(motive, "PARTY", levelSix, levelOne, questGiver.getRandomObjective(motive, "PARTY"), secondQuest, document);

    }



    public void createKnight() {

        HeroBackground knight = new HeroBackground("Knight");


        /**
         * START MENTOR
         */
        QuestGiver knightQuestGiver = new QuestGiver("Ghost", "Blacksmith");

        /**
         * START Act One Story Phases
          */
        //NORMAL story phase motives
        knightQuestGiver.createNewMotive("train", "NORMAL");
        knightQuestGiver.createNewMotive("simple task", "NORMAL");

         //train motive
        knightQuestGiver.addMotiveObjectives("train", "NORMAL","kill", "kill_fetch", "fetch");
        knightQuestGiver.addEnemies("train", "NORMAL","Bat", "Crab", "Bandit", "Albino", "Gnoll", "Thief");
        knightQuestGiver.addItems("train", "NORMAL", "DarkGold", "DwarfToken");

         //simple task motive
        knightQuestGiver.addMotiveObjectives("simple task", "NORMAL", "fetch", "kill_fetch");
        knightQuestGiver.addEnemies("simple task", "NORMAL","Bat", "Crab", "Bandit", "Albino", "Gnoll", "Thief");
        knightQuestGiver.addItems("simple task", "NORMAL",  "DwarfToken", "DriedRose", "DarkGold");


       //SERIOUS story phase motives
        knightQuestGiver.createNewMotive("gang problem", "SERIOUS");
        knightQuestGiver.createNewMotive("powerful item", "SERIOUS");

         //gang problem motive
        knightQuestGiver.addMotiveObjectives("gang problem", "SERIOUS", "kill", "kill_fetch");
        knightQuestGiver.addEnemies("gang problem", "SERIOUS", "Bandit",  "Shaman", "Thief");
        knightQuestGiver.addItems("gang problem", "SERIOUS","Letter", "DriedRose");

         //powerful item motive
        knightQuestGiver.addMotiveObjectives("powerful item", "SERIOUS", "fetch", "kill_fetch");
        knightQuestGiver.addEnemies("powerful item", "SERIOUS", "Bandit",  "Shaman", "Thief");
        knightQuestGiver.addItems("powerful item", "SERIOUS","RatSkull", "DwarfToken");
        /**
         * END Act One Story Phases
         */

        /**
         * START Act Two Story Phases
         */
        //CATASTROPHE story phase motives
        knightQuestGiver.createNewMotive("boss killing people", "CATASTROPHE");
        knightQuestGiver.createNewMotive("Demon awakened", "CATASTROPHE");

        //boss killing people motive
        knightQuestGiver.addMotiveObjectives("boss killing people", "CATASTROPHE","kill", "kill_fetch", "fetch");
        knightQuestGiver.addEnemies("boss killing people", "CATASTROPHE","Skeleton", "Golem", "Brute");
        knightQuestGiver.addItems("boss killing people", "CATASTROPHE", "Letter","DwarfToken", "Pickaxe");

        //Demon awakened motive
        knightQuestGiver.addMotiveObjectives("Demon awakened", "CATASTROPHE", "fetch", "kill_fetch");
        knightQuestGiver.addEnemies("Demon awakened", "CATASTROPHE","Warlock", "Shaman", "Succubus");
        knightQuestGiver.addItems("Demon awakened", "CATASTROPHE", "DarkGold", "DriedRose", "DwarfToken", "Pickaxe");


        //INVESTIGATE story phase motives
        knightQuestGiver.createNewMotive("find information", "INVESTIGATE");
        knightQuestGiver.createNewMotive("take out henchmen", "INVESTIGATE");

        //find information motive
        knightQuestGiver.addMotiveObjectives("find information", "INVESTIGATE", "kill_fetch", "fetch");
        knightQuestGiver.addEnemies("find information", "INVESTIGATE","Skeleton", "Golem", "Brute");
        knightQuestGiver.addItems("find information", "INVESTIGATE", "Letter", "DwarfToken", "Pickaxe");

        //take out henchmen motive
        knightQuestGiver.addMotiveObjectives("take out henchmen", "INVESTIGATE", "kill", "kill_fetch");
        knightQuestGiver.addEnemies("take out henchmen", "INVESTIGATE","Warlock", "Shaman", "Succubus", "Golem", "Brute");
        knightQuestGiver.addItems("take out henchmen", "INVESTIGATE", "DarkGold", "DriedRose", "DwarfToken", "Pickaxe");
        /**
         * END Act Two Story Phases
         */


        /**
         * START Act Three Story Phases
         */
        //CATASTROPHE story phase motives
        knightQuestGiver.createNewMotive("kill boss", "BOSS");
        knightQuestGiver.createNewMotive("cleanse land", "BOSS");

        //boss killing people motive
        knightQuestGiver.addMotiveObjectives("kill boss", "BOSS","kill");
        knightQuestGiver.addEnemies("kill boss", "BOSS","King", "Monk", "Eye");
        knightQuestGiver.addItems("kill boss", "BOSS", "Letter","DwarfToken", "Pickaxe");

        //cleanse land motive
        knightQuestGiver.addMotiveObjectives("cleanse land", "BOSS",  "kill_fetch");
        knightQuestGiver.addEnemies("cleanse land", "BOSS","Eye", "Monk");
        knightQuestGiver.addItems("cleanse land", "BOSS", "Pickaxe", "DriedRose");


        //PARTY story phase motives
        knightQuestGiver.createNewMotive("return to family", "PARTY");
        knightQuestGiver.createNewMotive("business as usual", "PARTY");

        //return to family motive
        knightQuestGiver.addMotiveObjectives("return to family", "PARTY", "speak");
        knightQuestGiver.addNPCs("return to family", "PARTY","Blacksmith", "Imp");

        //business as usual motive
        knightQuestGiver.addMotiveObjectives("business as usual", "PARTY", "kill", "kill_fetch");
        knightQuestGiver.addEnemies("business as usual", "PARTY","Crab", "Rat", "Albino");
        knightQuestGiver.addItems("business as usual", "PARTY",  "Pickaxe", "RatSkull");
        /**
         * END Act Three Story Phases
         */



        knight.actOneQuestGivers.add(knightQuestGiver);

        questGiver = knightQuestGiver;

//
//        //DAMSEL
//        knightQuestGiver = new QuestGiver("Imp", "Wandmaker");
//
//        knightQuestGiver.createNewMotive("lost item");
//        knightQuestGiver.createNewMotive("defend honour");
//
//        knightQuestGiver.addMotiveObjectives("lost item",  "kill_fetch", "fetch");
//        knightQuestGiver.addMotiveObjectives("defend honour", "kill", "kill_fetch");
//
//        knightQuestGiver.addEnemies("lost item", "Bat", "Crab", "Bandit", "Albino", "Gnoll", "Shaman", "Thief");
//        knightQuestGiver.addBossEnemies("lost item", "King", "Succubus", "Monk", "Brute", "Warlock", "Eye");
//        knightQuestGiver.addItems("lost item", "DriedRose",  "Letter", "RatSkull");
//
//        knightQuestGiver.addEnemies("defend honour",  "Bandit", "Thief");
//        knightQuestGiver.addBossEnemies("defend honour", "King", "Succubus", "Monk", "Brute", "Warlock", "Eye");
//        knightQuestGiver.addItems("defend honour","DriedRose", "DwarfToken", "Pickaxe", "RatSkull");
//
//        knight.actOneQuestGivers.add(knightQuestGiver);



        Collections.shuffle(knight.actOneQuestGivers);

        questGiver = knight.actOneQuestGivers.get(0);


    }

    public void questCreator(Element questNode, Document document, String questName, String prerequisiteQuestName) {

        String questGiverName = questGiver.name;

        DataHandler.getInstance().createQuestWithQuestGiver(questNode, document, questName, questGiverName, "40xp", levelOne, prerequisiteQuestName, "" +
                "I have quest", "I gave quest", "you done quest");

    }

    public void objeciveCreator(String motiveType, String storyPhase, String level, String returnLevel, String objectiveType, Element questNode, Document document) {




        String questGiverName = questGiver.name;

        switch (objectiveType) {

            case "kill":

                String enemy = questGiver.getRandomEnemy(motiveType, storyPhase);

                String objectiveName = "kill " +enemy;

                DataHandler.getInstance().createKillObjective(questNode, document, objectiveName, enemy, "4", level);

                objectiveName = "speak to " +questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;

            case "kill_fetch":

                enemy = questGiver.getRandomEnemy(motiveType, storyPhase);
                String item = questGiver.getRandomItem(motiveType, storyPhase);

                objectiveName = "find a " +item+ " by killing " +enemy;

                DataHandler.getInstance().createKillFetchObjective(questNode, document, objectiveName, enemy, "5", item, level);

                objectiveName = "speak to " +questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;

            case "fetch":

                item = questGiver.getRandomItem(motiveType, storyPhase);

                objectiveName = "fetch " +item;

                DataHandler.getInstance().createFetchObjective(questNode, document, objectiveName, item, level);

                objectiveName = "speak to " +questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");

                break;

            case "speak":

                String npc = questGiver.getRandomNPC(motiveType, storyPhase);

                objectiveName = "speak to  " +npc;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, npc, level, "some dialogue");

                objectiveName = "speak to " +questGiverName;

                DataHandler.getInstance().createSpeakObjective(questNode, document, objectiveName, questGiverName, returnLevel, "done");
        }


    }







}

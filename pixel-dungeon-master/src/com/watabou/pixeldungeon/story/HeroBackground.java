package com.watabou.pixeldungeon.story;

import java.util.ArrayList;

public class HeroBackground {

    String background;

    ArrayList<QuestGiver> actOneQuestGivers;
    ArrayList<QuestGiver> actTwoQuestGivers;
    ArrayList<QuestGiver> actThreeQuestGivers;

    public HeroBackground(String background) {
        this.background = background;

        actOneQuestGivers = new ArrayList<>();
    }


    public void setActOneOpeningStory() {

        switch (background) {

            case "Knight":

                StoryGenerator.getInstance().shadowLandsStory = "The shadow lands is a dangerous place, even for a knight.";
                StoryGenerator.getInstance().forestStory = "The forests have always been a beautiful part of the Kingdom.";
                StoryGenerator.getInstance().cavesStory = "These caves were once used for mining materials used to make weapons and armor for knights such as yourself.";
                StoryGenerator.getInstance().castleStory = "The castle was one your home, before the king went bonkers.";
                StoryGenerator.getInstance().fieldsStory = "The fields are home to many of the peasants you spit on on a daily basis.";
                StoryGenerator.getInstance().dungeonStory = "Housing some of the most dangerous prisoners, the Dungeon is a fortress of scum.";

                break;

            case "Explorer":

                StoryGenerator.getInstance().shadowLandsStory = "You seem to have stumbled into a strange land while mindlessly exploring. Given the look of this place, this probably won't end well for you.";
                StoryGenerator.getInstance().forestStory = "You know this forest like the back of your hand. If you get lost, check the map you drew on the back of your hand.";
                StoryGenerator.getInstance().cavesStory = "According the tales you've read, these caves were once home to Mole Men of Androlar.";
                StoryGenerator.getInstance().castleStory = "You've heard stories about this dreaded castle but never did you think you'd actually wander into it accidentally.";
                StoryGenerator.getInstance().fieldsStory = "Seeing the local common folk go about their daily lives brings you great joy as you go about your lazy care-free existence.";
                StoryGenerator.getInstance().dungeonStory = "You definitely didn't think you'd end up here after a night of tavern-crawling.";

                break;


            case "Peasant":

                StoryGenerator.getInstance().shadowLandsStory = "A well-trained knight barely has a chance of survival here, you have my condolences.";
                StoryGenerator.getInstance().forestStory = "The over abundance of trees makes the Forest great for gathering firewood.";
                StoryGenerator.getInstance().cavesStory = "These caves grow some of the finest edible mushrooms in all the Kingdom and are definitely not used for anything other than culinary purposes.";
                StoryGenerator.getInstance().castleStory = "They laughed at you when you said a peasant would one day step inside this castle. Although I suppose it doesn't really count now, since the only people here are trying to kill you.";
                StoryGenerator.getInstance().fieldsStory = "Being at home in the fields fills you with the uncomfortable feeling that everyone gets on Sunday evenings.";
                StoryGenerator.getInstance().dungeonStory = "The abandoned dungeons of the Kingdom have not been home to peasants since the tragic rodent attack of Gollomere. They say that many of those same rodents still inhabit the Dungeons today";

                break;


        }

        switch (actOneQuestGivers.get(0).name) {

            case "Ghost": //Mentor

                StoryGenerator.getInstance().shadowLandsStory = StoryGenerator.getInstance().shadowLandsStory.concat(" Why would the mentor ask you to meet in this place?");
                StoryGenerator.getInstance().forestStory = StoryGenerator.getInstance().forestStory.concat(" This seems like the perfect place to train, you should find your mentor.");
                StoryGenerator.getInstance().cavesStory = StoryGenerator.getInstance().cavesStory.concat(" Maybe the mentor wants to gather some of the left over materials. You should find him.");
                StoryGenerator.getInstance().castleStory = StoryGenerator.getInstance().castleStory.concat(" Your mentor asked you to meet him here, perhaps to reminisce.");
                StoryGenerator.getInstance().fieldsStory = StoryGenerator.getInstance().fieldsStory.concat(" Perhaps your mentor would like to join you in mocking the common folk. Find him.");
                StoryGenerator.getInstance().dungeonStory = StoryGenerator.getInstance().dungeonStory.concat(" It's not your turn for guard duty, why would your mentor ask to meet you in here? You should find him.");

                break;

            case "Imp": //Princess

                StoryGenerator.getInstance().shadowLandsStory = StoryGenerator.getInstance().shadowLandsStory.concat(" This place is terrifying, why would the princess ask you to meet her in such a place?");
                StoryGenerator.getInstance().forestStory = StoryGenerator.getInstance().forestStory.concat(" The princess always chooses the most romantic places for our rendezvous. You should find her.");
                StoryGenerator.getInstance().cavesStory = StoryGenerator.getInstance().cavesStory.concat(" The princess would never be caught dead in a place like this, you should find her before she gets herself into trouble..");
                StoryGenerator.getInstance().castleStory = StoryGenerator.getInstance().castleStory.concat(" The princess hasn't been here since her father went bonkers, you should find her.");
                StoryGenerator.getInstance().fieldsStory = StoryGenerator.getInstance().fieldsStory.concat(" The princess is beloved by all the people in Kingdom, perhaps she came here to visit them. You should find her.");
                StoryGenerator.getInstance().dungeonStory = StoryGenerator.getInstance().dungeonStory.concat(" These dungeons are no place for a princess, you should find her.");

                break;








        }

    }


}

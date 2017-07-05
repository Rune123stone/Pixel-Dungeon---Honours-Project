package com.watabou.pixeldungeon.story;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.quests.Quest;
import com.watabou.pixeldungeon.quests.QuestObjective;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
import java.util.Random;

public class GenerateData {

    private static GenerateData generateData;

    private GenerateData() {
    }

    public static GenerateData getInstance() {
        if (generateData == null) {
            generateData = new GenerateData();
        }
        return generateData;
    }

    public static ArrayList<Quest> actOneQuests;
    public static ArrayList<Quest> actTwoQuests;
    public static ArrayList<Quest> actThreeQuests;

    public int randomTemplateSelector() {
        Random random = new Random();

        return random.nextInt(4 - 1) + 1;
    }

    //will eventually have two stories - will have to name "create..." methods differently once 2nd story is implemented, i.e. "createMentorStoryActOneXMLQuestData() etc.
    public String randomStorySelector() {
        return "Mentor_Story";
    }

    //if story is Mentor_Story, generate a random item with properties such as item_boss, item_collectable_location etc.
    public String generateRandomItem() {
        return "";
    }

    public void createActOneXMLQuestData() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document doc = documentBuilder.newDocument();
            Element rootElement = doc.createElement("act_one_template");
            doc.appendChild(rootElement);

            int template = randomTemplateSelector();
            System.out.println(template);

            switch (template) {
                // *** IF 1st template is chosen, generate the following XML data ***
                case 1:
                    Element firstTemplate = doc.createElement("first_template");
                    rootElement.appendChild(firstTemplate);

                    Element actOnePlotDescription = doc.createElement("act_one_plot_description");
                    actOnePlotDescription.appendChild(doc.createTextNode("firstActOnePlotDescrip.txt"));
                    firstTemplate.appendChild(actOnePlotDescription);

                    Element actTwoPlotDescription = doc.createElement("act_two_plot_description");
                    actTwoPlotDescription.appendChild(doc.createTextNode("firstActActTwoPlotDescrip.txt"));
                    firstTemplate.appendChild(actTwoPlotDescription);

                    // ** START first quest **
                    Element quest = doc.createElement("quest");
                    firstTemplate.appendChild(quest);

                    Element questName = doc.createElement("quest_name");
                    questName.appendChild(doc.createTextNode("Call to Power"));
                    quest.appendChild(questName);

                    Element questGiver = doc.createElement("quest_giver");
                    questGiver.appendChild(doc.createTextNode("none"));
                    quest.appendChild(questGiver);

                    Element questReward = doc.createElement("quest_reward");
                    questReward.appendChild(doc.createTextNode("40xp"));
                    quest.appendChild(questReward);

                    Element questDialogue = doc.createElement("quest_dialogue");
                    questDialogue.appendChild(doc.createTextNode("callToPower.txt"));
                    quest.appendChild(questDialogue);

                    Element questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    Element questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("speak"));
                    questObjective.appendChild(questType);

                    Element questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("#MENTOR"));
                    questObjective.appendChild(questNPC);

                    Element questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("mentor_location"));
                    questObjective.appendChild(questLocation);

                    quest = doc.createElement("quest");
                    firstTemplate.appendChild(quest);

                    questName = doc.createElement("quest_name");
                    questName.appendChild(doc.createTextNode("Finding the Source"));
                    quest.appendChild(questName);

                    questGiver = doc.createElement("quest_giver");
                    questGiver.appendChild(doc.createTextNode("#MENTOR"));
                    quest.appendChild(questGiver);

                    questReward = doc.createElement("quest_reward");
                    questReward.appendChild(doc.createTextNode("tier 1"));
                    quest.appendChild(questReward);

                    Element questNotGivenDialogue = doc.createElement("quest_not_given_dialogue");
                    questNotGivenDialogue.appendChild(doc.createTextNode("findingTheSource_not_given.txt"));
                    quest.appendChild(questNotGivenDialogue);

                    Element questGivenDialogue = doc.createElement("quest_given_dialogue");
                    questGivenDialogue.appendChild(doc.createTextNode("findingTheSource_given.txt"));
                    quest.appendChild(questGivenDialogue);

                    Element questCompletedDialogue = doc.createElement("quest_completed_dialogue");
                    questCompletedDialogue.appendChild(doc.createTextNode("findingTheSource_completed.txt"));
                    quest.appendChild(questCompletedDialogue);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("collect"));
                    questObjective.appendChild(questType);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("item_information_location"));
                    questObjective.appendChild(questLocation);

                    Element questItem = doc.createElement("quest_item");
                    questItem.appendChild(doc.createTextNode("item_information"));
                    questObjective.appendChild(questItem);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("speak"));
                    questObjective.appendChild(questType);

                    questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("#MENTOR"));
                    questObjective.appendChild(questNPC);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("mentor_location"));
                    questObjective.appendChild(questLocation);
                    break;

                case 2:
                    // *** IF 2nd template is chosen, generate the following XML data ***
                    Element secondTemplate = doc.createElement("second_template");
                    rootElement.appendChild(secondTemplate);

                    actOnePlotDescription = doc.createElement("act_one_plot_description");
                    actOnePlotDescription.appendChild(doc.createTextNode("secondTemplateActOnePlotDescrip.txt"));
                    secondTemplate.appendChild(actOnePlotDescription);

                    actTwoPlotDescription = doc.createElement("act_two_plot_description");
                    actTwoPlotDescription.appendChild(doc.createTextNode("secondTemplateActTwoPlotDescrip.txt"));
                    secondTemplate.appendChild(actTwoPlotDescription);

                    quest = doc.createElement("quest");
                    secondTemplate.appendChild(quest);

                    questName = doc.createElement("quest_name");
                    questName.appendChild(doc.createTextNode("Missing Mentor"));
                    quest.appendChild(questName);

                    questGiver = doc.createElement("quest_giver");
                    questGiver.appendChild(doc.createTextNode("none"));
                    quest.appendChild(questGiver);

                    questReward = doc.createElement("quest_reward");
                    questReward.appendChild(doc.createTextNode("40xp"));
                    quest.appendChild(questReward);

                    questDialogue = doc.createElement("quest_dialogue");
                    questDialogue.appendChild(doc.createTextNode("missingMentor.txt"));
                    quest.appendChild(questDialogue);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("use item"));
                    questObjective.appendChild(questType);

                    questItem = doc.createElement("quest_item");
                    questItem.appendChild(doc.createTextNode("letter"));
                    questObjective.appendChild(questItem);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("missing_mentor_letter_location"));
                    questObjective.appendChild(questLocation);

                    quest = doc.createElement("quest");
                    secondTemplate.appendChild(quest);

                    questName = doc.createElement("quest_name");
                    questName.appendChild(doc.createTextNode("Rescue Mission"));
                    quest.appendChild(questName);

                    questGiver = doc.createElement("quest_giver");
                    questGiver.appendChild(doc.createTextNode("#RELATIVE"));
                    quest.appendChild(questGiver);

                    questReward = doc.createElement("quest_reward");
                    questReward.appendChild(doc.createTextNode("tier 1"));
                    quest.appendChild(questReward);

                    questNotGivenDialogue = doc.createElement("quest_not_given_dialogue");
                    questNotGivenDialogue.appendChild(doc.createTextNode("rescueMission_not_given.txt"));
                    quest.appendChild(questNotGivenDialogue);

                    questGivenDialogue = doc.createElement("quest_given_dialogue");
                    questGivenDialogue.appendChild(doc.createTextNode("rescueMission_given.txt"));
                    quest.appendChild(questGivenDialogue);

                    questCompletedDialogue = doc.createElement("quest_completed_dialogue");
                    questCompletedDialogue.appendChild(doc.createTextNode("rescueMission_completed.txt"));
                    quest.appendChild(questCompletedDialogue);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("kill"));
                    questObjective.appendChild(questType);

                    Element enemy = doc.createElement("enemy");
                    enemy.appendChild(doc.createTextNode("kidnap_enemey"));
                    questObjective.appendChild(enemy);

                    Element amountToKill = doc.createElement("amount_to_kill");
                    amountToKill.appendChild(doc.createTextNode("3"));
                    questObjective.appendChild(amountToKill);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("mentor_kidnap_location"));
                    questObjective.appendChild(questLocation);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("speak"));
                    questObjective.appendChild(questType);

                    questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("#MENTOR"));
                    questObjective.appendChild(questNPC);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("mentor_location"));
                    questObjective.appendChild(questLocation);
                    break;

                case 3:
                    // *** IF 3rd template is chosen, generate the following XML data ***
                    Element thirdTemplate = doc.createElement("third_template");
                    rootElement.appendChild(thirdTemplate);

                    actOnePlotDescription = doc.createElement("act_one_plot_description");
                    actOnePlotDescription.appendChild(doc.createTextNode("thirdTemplateActOnePlotDescrip.txt"));
                    thirdTemplate.appendChild(actOnePlotDescription);

                    actTwoPlotDescription = doc.createElement("act_two_plot_description");
                    actTwoPlotDescription.appendChild(doc.createTextNode("thirdTemplateActTwoPlotDescrip.txt"));
                    thirdTemplate.appendChild(actTwoPlotDescription);

                    quest = doc.createElement("quest");
                    thirdTemplate.appendChild(quest);

                    questName = doc.createElement("quest_name");
                    questName.appendChild(doc.createTextNode("Home Bound"));
                    quest.appendChild(questName);

                    questGiver = doc.createElement("quest_giver");
                    questGiver.appendChild(doc.createTextNode("none"));
                    quest.appendChild(questGiver);

                    questReward = doc.createElement("quest_reward");
                    questReward.appendChild(doc.createTextNode("40xp"));
                    quest.appendChild(questReward);

                    questDialogue = doc.createElement("quest_dialogue");
                    questDialogue.appendChild(doc.createTextNode("homeBound.txt"));
                    quest.appendChild(questDialogue);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("travel"));
                    questObjective.appendChild(questType);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("mentor_location"));
                    questObjective.appendChild(questLocation);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("collect"));
                    questObjective.appendChild(questType);

                    questItem = doc.createElement("quest_item");
                    questItem.appendChild(doc.createTextNode("letter_item"));
                    questObjective.appendChild(questItem);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("mentor_location"));
                    questObjective.appendChild(questLocation);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("use item"));
                    questObjective.appendChild(questType);

                    questItem = doc.createElement("quest_item");
                    questItem.appendChild(doc.createTextNode("letter_item"));
                    questObjective.appendChild(questItem);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("mentor_location"));
                    questObjective.appendChild(questLocation);

                    quest = doc.createElement("quest");
                    thirdTemplate.appendChild(quest);

                    questName = doc.createElement("quest_name");
                    questName.appendChild(doc.createTextNode("Message from Beyond"));
                    quest.appendChild(questName);

                    questGiver = doc.createElement("quest_giver");
                    questGiver.appendChild(doc.createTextNode("none"));
                    quest.appendChild(questGiver);

                    questReward = doc.createElement("quest_reward");
                    questReward.appendChild(doc.createTextNode("tier 1"));
                    quest.appendChild(questReward);

                    questDialogue = doc.createElement("quest_dialogue");
                    questDialogue.appendChild(doc.createTextNode("messageFromBeyond.txt"));
                    quest.appendChild(questDialogue);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("collect"));
                    questObjective.appendChild(questType);

                    questItem = doc.createElement("quest_item");
                    questItem.appendChild(doc.createTextNode("item_information"));
                    questObjective.appendChild(questItem);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("item_information_location"));
                    questObjective.appendChild(questLocation);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("speak"));
                    questObjective.appendChild(questType);

                    questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("#MENTOR"));
                    questObjective.appendChild(questNPC);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("mentor_location"));
                    questObjective.appendChild(questLocation);
                    break;
            }

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
            System.out.println(e.getMessage());
        }
    }

    public void createActTwoAXMLQuestData() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document doc = documentBuilder.newDocument();
            Element rootElement = doc.createElement("act_two_template");
            doc.appendChild(rootElement);

            int template = randomTemplateSelector();
            System.out.println(template);


            switch (template) {
                // *** IF 1st template is chosen, generate the following XML data ***
                case 1:
                    Element firstTemplate = doc.createElement("first_template");
                    rootElement.appendChild(firstTemplate);

                    Element actTwoPlotDescription = doc.createElement("act_two_plot_description");
                    actTwoPlotDescription.appendChild(doc.createTextNode("firstTemplateActTwoPlotDescrip.txt"));
                    firstTemplate.appendChild(actTwoPlotDescription);

                    // ** START first quest **
                    Element quest = doc.createElement("quest");
                    firstTemplate.appendChild(quest);

                    Element questName = doc.createElement("quest_name");
                    questName.appendChild(doc.createTextNode("Seeking Power"));
                    quest.appendChild(questName);

                    Element questGiver = doc.createElement("quest_giver");
                    questGiver.appendChild(doc.createTextNode("#MENTOR"));
                    quest.appendChild(questGiver);

                    Element questReward = doc.createElement("quest_reward");
                    questReward.appendChild(doc.createTextNode("tier 2"));
                    quest.appendChild(questReward);

                    Element questNotGivenDialogue = doc.createElement("quest_not_given_dialogue");
                    questNotGivenDialogue.appendChild(doc.createTextNode("seekingPower_not_given.txt"));
                    quest.appendChild(questNotGivenDialogue);

                    Element questGivenDialogue = doc.createElement("quest_given_dialogue");
                    questGivenDialogue.appendChild(doc.createTextNode("seekingPower_given.txt"));
                    quest.appendChild(questGivenDialogue);

                    Element questCompletedDialogue = doc.createElement("quest_completed_dialogue");
                    questCompletedDialogue.appendChild(doc.createTextNode("seekingPower_completed.txt"));
                    quest.appendChild(questCompletedDialogue);

                    Element questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    Element questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("speak"));
                    questObjective.appendChild(questType);

                    Element questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("#MENTOR"));
                    questObjective.appendChild(questNPC);

                    Element questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("mentor_location"));
                    questObjective.appendChild(questLocation);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("collect"));
                    questObjective.appendChild(questType);

                    Element questItem = doc.createElement("quest_item");
                    questItem.appendChild(doc.createTextNode("item_collectable"));
                    questObjective.appendChild(questItem);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("item_collectable_location"));
                    questObjective.appendChild(questLocation);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("speak"));
                    questObjective.appendChild(questType);

                    questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("#MENTOR"));
                    questObjective.appendChild(questNPC);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("mentor_location"));
                    questObjective.appendChild(questLocation);
                    break;

                // *** IF 2nd template is chosen, generate the following XML data ***
                case 2:
                    Element secondTemplate = doc.createElement("second_template");
                    rootElement.appendChild(secondTemplate);

                    actTwoPlotDescription = doc.createElement("act_two_plot_description");
                    actTwoPlotDescription.appendChild(doc.createTextNode("secondTemplateActTwoPlotDescrip.txt"));
                    secondTemplate.appendChild(actTwoPlotDescription);

                    quest = doc.createElement("quest");
                    secondTemplate.appendChild(quest);

                    questName = doc.createElement("quest_name");
                    questName.appendChild(doc.createTextNode("Seeking the Wise"));
                    quest.appendChild(questName);

                    questGiver = doc.createElement("quest_giver");
                    questGiver.appendChild(doc.createTextNode("#MENTOR"));
                    quest.appendChild(questGiver);

                    questReward = doc.createElement("quest_reward");
                    questReward.appendChild(doc.createTextNode("40xp"));
                    quest.appendChild(questReward);

                    questNotGivenDialogue = doc.createElement("quest_not_given_dialogue");
                    questNotGivenDialogue.appendChild(doc.createTextNode("seekingTheWise_not_given.txt"));
                    quest.appendChild(questNotGivenDialogue);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("speak"));
                    questObjective.appendChild(questType);

                    questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("#MENTOR"));
                    questObjective.appendChild(questNPC);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("mentor_location"));
                    questObjective.appendChild(questLocation);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("speak"));
                    questObjective.appendChild(questType);

                    questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("item_info_npc"));
                    questObjective.appendChild(questNPC);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("item_info_npc_location"));
                    questObjective.appendChild(questLocation);

                    quest = doc.createElement("quest");
                    secondTemplate.appendChild(quest);

                    questName = doc.createElement("quest_name");
                    questName.appendChild(doc.createTextNode("Prove your Worth"));
                    quest.appendChild(questName);

                    questGiver = doc.createElement("quest_giver");
                    questGiver.appendChild(doc.createTextNode("item_info_npc"));
                    quest.appendChild(questGiver);

                    questReward = doc.createElement("quest_reward");
                    questReward.appendChild(doc.createTextNode("tier 1"));
                    quest.appendChild(questReward);

                    questNotGivenDialogue = doc.createElement("quest_not_given_dialogue");
                    questNotGivenDialogue.appendChild(doc.createTextNode("proveYourWorth_not_given.txt"));
                    quest.appendChild(questNotGivenDialogue);

                    questGivenDialogue = doc.createElement("quest_given_dialogue");
                    questGivenDialogue.appendChild(doc.createTextNode("proveYourWorth_given.txt"));
                    quest.appendChild(questGivenDialogue);

                    questCompletedDialogue = doc.createElement("quest_completed_dialogue");
                    questCompletedDialogue.appendChild(doc.createTextNode("proveYourWorth_completed.txt"));
                    quest.appendChild(questCompletedDialogue);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("fight"));
                    questObjective.appendChild(questType);

                    questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("item_info_npc"));
                    questObjective.appendChild(questNPC);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("item_info_npc_location"));
                    questObjective.appendChild(questLocation);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("collect"));
                    questObjective.appendChild(questType);

                    questItem = doc.createElement("quest_item");
                    questItem.appendChild(doc.createTextNode("item_collectable"));
                    questObjective.appendChild(questItem);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("item_collectable_location"));
                    questObjective.appendChild(questLocation);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("speak"));
                    questObjective.appendChild(questType);

                    questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("#MENTOR"));
                    questObjective.appendChild(questNPC);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("mentor_location"));
                    questObjective.appendChild(questLocation);
                    break;

                // *** IF 3rd template is chosen, generate the following XML data ***
                case 3:
                    Element thirdTemplate = doc.createElement("third_template");
                    rootElement.appendChild(thirdTemplate);

                    actTwoPlotDescription = doc.createElement("act_two_plot_description");
                    actTwoPlotDescription.appendChild(doc.createTextNode("thirdTemplateActTwoPlotDescrip.txt"));
                    thirdTemplate.appendChild(actTwoPlotDescription);

                    quest = doc.createElement("quest");
                    thirdTemplate.appendChild(quest);

                    questName = doc.createElement("quest_name");
                    questName.appendChild(doc.createTextNode("Guarded Secrets"));
                    quest.appendChild(questName);

                    questGiver = doc.createElement("quest_giver");
                    questGiver.appendChild(doc.createTextNode("#MENTOR"));
                    quest.appendChild(questGiver);

                    questReward = doc.createElement("quest_reward");
                    questReward.appendChild(doc.createTextNode("40xp"));
                    quest.appendChild(questReward);

                    questNotGivenDialogue = doc.createElement("quest_not_given_dialogue");
                    questNotGivenDialogue.appendChild(doc.createTextNode("guardedSecrets_not_given.txt"));
                    quest.appendChild(questNotGivenDialogue);

                    questGivenDialogue = doc.createElement("quest_given_dialogue");
                    questGivenDialogue.appendChild(doc.createTextNode("guardedSecrets_given.txt"));
                    quest.appendChild(questGivenDialogue);

                    questCompletedDialogue = doc.createElement("quest_completed_dialogue");
                    questCompletedDialogue.appendChild(doc.createTextNode("guardedSecrets_completed.txt"));
                    quest.appendChild(questCompletedDialogue);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("speak"));
                    questObjective.appendChild(questType);

                    questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("#WIZARD"));
                    questObjective.appendChild(questNPC);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("wizard_location"));
                    questObjective.appendChild(questLocation);

                    quest = doc.createElement("quest");
                    thirdTemplate.appendChild(quest);

                    questName = doc.createElement("quest_name");
                    questName.appendChild(doc.createTextNode("Slay the Beast"));
                    quest.appendChild(questName);

                    questGiver = doc.createElement("quest_giver");
                    questGiver.appendChild(doc.createTextNode("#WIZARD"));
                    quest.appendChild(questGiver);

                    questReward = doc.createElement("quest_reward");
                    questReward.appendChild(doc.createTextNode("tier 2"));
                    quest.appendChild(questReward);

                    questNotGivenDialogue = doc.createElement("quest_not_given_dialogue");
                    questNotGivenDialogue.appendChild(doc.createTextNode("slayTheBeast_not_given.txt"));
                    quest.appendChild(questNotGivenDialogue);

                    questGivenDialogue = doc.createElement("quest_given_dialogue");
                    questGivenDialogue.appendChild(doc.createTextNode("slayTheBeast_given.txt"));
                    quest.appendChild(questGivenDialogue);

                    questCompletedDialogue = doc.createElement("quest_completed_dialogue");
                    questCompletedDialogue.appendChild(doc.createTextNode("slayTheBeast_completed.txt"));
                    quest.appendChild(questCompletedDialogue);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("kill_collect"));
                    questObjective.appendChild(questType);

                    Element enemy = doc.createElement("enemy");
                    enemy.appendChild(doc.createTextNode("item_collectable_boss"));
                    questObjective.appendChild(enemy);

                    Element amountToKill = doc.createElement("amount_to_kill");
                    amountToKill.appendChild(doc.createTextNode("1"));
                    questObjective.appendChild(amountToKill);

                    questItem = doc.createElement("quest_item");
                    questItem.appendChild(doc.createTextNode("cleanse_item"));
                    questObjective.appendChild(questItem);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("location_beast"));
                    questObjective.appendChild(questLocation);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("speak"));
                    questObjective.appendChild(questType);

                    questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("#MENTOR"));
                    questObjective.appendChild(questNPC);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("mentor_location"));
                    questObjective.appendChild(questLocation);
                    break;
            }

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
        }
    }

    public void createActThreeXMLQuestData() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document doc = documentBuilder.newDocument();
            Element rootElement = doc.createElement("act_three_template");
            doc.appendChild(rootElement);

            int template = randomTemplateSelector();
            System.out.println(template);

            switch (template) {
                // *** IF 1st template is chosen, generate the following XML data ***
                case 1:
                    Element firstTemplate = doc.createElement("first_template");
                    rootElement.appendChild(firstTemplate);

                    Element actThreePlotDescription = doc.createElement("act_three_plot_description");
                    actThreePlotDescription.appendChild(doc.createTextNode("firstTemplateActThreePlotDescrip.txt"));
                    firstTemplate.appendChild(actThreePlotDescription);

                    // ** START first quest **
                    Element quest = doc.createElement("quest");
                    firstTemplate.appendChild(quest);

                    Element questName = doc.createElement("quest_name");
                    questName.appendChild(doc.createTextNode("Justice has Come"));
                    quest.appendChild(questName);

                    Element questGiver = doc.createElement("quest_giver");
                    questGiver.appendChild(doc.createTextNode("none"));
                    quest.appendChild(questGiver);

                    Element questReward = doc.createElement("quest_reward");
                    questReward.appendChild(doc.createTextNode("tier 3"));
                    quest.appendChild(questReward);

                    Element questDialogue = doc.createElement("quest_dialogue");
                    questDialogue.appendChild(doc.createTextNode("justiceHasCome.txt"));
                    quest.appendChild(questDialogue);

                    Element questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    Element questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("speak"));
                    questObjective.appendChild(questType);

                    Element questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("#MENTOR"));
                    questObjective.appendChild(questNPC);

                    Element questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("mentor_location"));
                    questObjective.appendChild(questLocation);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("kill"));
                    questObjective.appendChild(questType);

                    Element enemy = doc.createElement("enemy");
                    enemy.appendChild(doc.createTextNode("item_boss_npc"));
                    questObjective.appendChild(enemy);

                    Element amountToKill = doc.createElement("amount_to_kill");
                    amountToKill.appendChild(doc.createTextNode("1"));
                    questObjective.appendChild(amountToKill);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("item_boss_location"));
                    questObjective.appendChild(questLocation);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("speak"));
                    questObjective.appendChild(questType);

                    questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("#MENTOR"));
                    questObjective.appendChild(questNPC);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("mentor_location"));
                    questObjective.appendChild(questLocation);
                    break;

                // *** IF 2nd template is chosen, generate the following XML data ***
                case 2:
                    Element secondTemplate = doc.createElement("second_template");
                    rootElement.appendChild(secondTemplate);

                    actThreePlotDescription = doc.createElement("act_three_plot_description");
                    actThreePlotDescription.appendChild(doc.createTextNode("secondTemplateActThreePlotDescrip.txt"));
                    secondTemplate.appendChild(actThreePlotDescription);

                    quest = doc.createElement("quest");
                    secondTemplate.appendChild(quest);

                    questName = doc.createElement("quest_name");
                    questName.appendChild(doc.createTextNode("Seeking Redemption"));
                    quest.appendChild(questName);

                    questGiver = doc.createElement("quest_giver");
                    questGiver.appendChild(doc.createTextNode("none"));
                    quest.appendChild(questGiver);

                    questReward = doc.createElement("quest_reward");
                    questReward.appendChild(doc.createTextNode("tier 3"));
                    quest.appendChild(questReward);

                    questDialogue = doc.createElement("quest_dialogue");
                    questDialogue.appendChild(doc.createTextNode("seekingRedemption.txt"));
                    quest.appendChild(questDialogue);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("speak"));
                    questObjective.appendChild(questType);

                    questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("#MENTOR"));
                    questObjective.appendChild(questNPC);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("mentor_location"));
                    questObjective.appendChild(questLocation);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("kill_collect"));
                    questObjective.appendChild(questType);

                    enemy = doc.createElement("enemy");
                    enemy.appendChild(doc.createTextNode("item_collectable_enemy"));
                    questObjective.appendChild(enemy);

                    amountToKill = doc.createElement("amount_to_kill");
                    amountToKill.appendChild(doc.createTextNode("5"));
                    questObjective.appendChild(amountToKill);

                    Element questItem = doc.createElement("quest_item");
                    questItem.appendChild(doc.createTextNode("cleanse_item"));
                    questObjective.appendChild(questItem);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("shadowLands"));
                    questObjective.appendChild(questLocation);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("use item"));
                    questObjective.appendChild(questType);

                    questItem = doc.createElement("quest_item");
                    questItem.appendChild(doc.createTextNode("cleanse_item"));
                    questObjective.appendChild(questItem);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("shadowLands"));
                    questObjective.appendChild(questLocation);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("speak"));
                    questObjective.appendChild(questType);

                    questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("#MENTOR"));
                    questObjective.appendChild(questNPC);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("mentor_location"));
                    questObjective.appendChild(questLocation);
                    break;

                // *** IF 3rd template is chosen, generate the following XML data ***
                case 3:
                    Element thirdTemplate = doc.createElement("third_template");
                    rootElement.appendChild(thirdTemplate);

                    actThreePlotDescription = doc.createElement("act_three_plot_description");
                    actThreePlotDescription.appendChild(doc.createTextNode("thirdTemplateActThreePlotDescrip.txt"));
                    thirdTemplate.appendChild(actThreePlotDescription);

                    quest = doc.createElement("quest");
                    thirdTemplate.appendChild(quest);

                    questName = doc.createElement("quest_name");
                    questName.appendChild(doc.createTextNode("Protecting the Helpless"));
                    quest.appendChild(questName);

                    questGiver = doc.createElement("quest_giver");
                    questGiver.appendChild(doc.createTextNode("none"));
                    quest.appendChild(questGiver);

                    questReward = doc.createElement("quest_reward");
                    questReward.appendChild(doc.createTextNode("tier 3"));
                    quest.appendChild(questReward);

                    questDialogue = doc.createElement("quest_dialogue");
                    questDialogue.appendChild(doc.createTextNode("protectingTheHelpless.txt"));
                    quest.appendChild(questDialogue);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("kill"));
                    questObjective.appendChild(questType);

                    enemy = doc.createElement("enemy");
                    enemy.appendChild(doc.createTextNode("item_boss"));
                    questObjective.appendChild(enemy);

                    amountToKill = doc.createElement("amount_to_kill");
                    amountToKill.appendChild(doc.createTextNode("1"));
                    questObjective.appendChild(amountToKill);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("item_boss_location"));
                    questObjective.appendChild(questLocation);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("speak"));
                    questObjective.appendChild(questType);

                    questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("#RELATIVE"));
                    questObjective.appendChild(questNPC);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("relative_kidnap_location"));
                    questObjective.appendChild(questLocation);

                    questObjective = doc.createElement("quest_objective");
                    quest.appendChild(questObjective);

                    questType = doc.createElement("quest_type");
                    questType.appendChild(doc.createTextNode("speak"));
                    questObjective.appendChild(questType);

                    questNPC = doc.createElement("quest_npc");
                    questNPC.appendChild(doc.createTextNode("#RELATIVE"));
                    questObjective.appendChild(questNPC);

                    questLocation = doc.createElement("quest_location");
                    questLocation.appendChild(doc.createTextNode("home_location"));
                    questObjective.appendChild(questLocation);
                    break;
            }

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
        }
    }

    public void generateStoryXMLs() {
        String chosenStory = randomStorySelector();

        if (chosenStory.equals("Mentor_Story")) {
            createActOneXMLQuestData();
            createActTwoAXMLQuestData();
            createActThreeXMLQuestData();
        }
    }

    public void createActOneQuests() {
        try {

            //get location of XML file
            String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Data/";
            File file = new File(fullPath, "ActOne.xml");

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(file);

            doc.getDocumentElement().normalize();

            //get list of quests in the Act
            NodeList questList = doc.getElementsByTagName("quest");

            for (int i = 0; i < questList.getLength(); i++) {
                Node curQuestNode = questList.item(i);

                System.out.println("Current element: " +curQuestNode.getNodeName());

                //obtain quest elements for each quest in the list
                if (curQuestNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element questElement = (Element)curQuestNode;

                    String questName = questElement.getElementsByTagName("quest_name").item(0).getTextContent();
                    String questGiver = questElement.getElementsByTagName("quest_giver").item(0).getTextContent();
                    String questReward = questElement.getElementsByTagName("quest_reward").item(0).getTextContent();

                    Quest quest;

                    if (questGiver.equals("none")) {
                        String questDialogue = questElement.getElementsByTagName("quest_dialogue").item(0).getTextContent();

                        quest = new Quest(questName, questGiver, questReward, 1, questDialogue);

                    } else {
                        String questNotGivenDialogue = questElement.getElementsByTagName("quest_not_given_dialogue").item(0).getTextContent();
                        String questGivenDialogue = questElement.getElementsByTagName("quest_given_dialogue").item(0).getTextContent();
                        String questCompletedDialogue = questElement.getElementsByTagName("quest_completed_dialogue").item(0).getTextContent();

                        quest = new Quest(questName, questGiver, questReward, 1, questNotGivenDialogue, questGivenDialogue, questCompletedDialogue);
                    }

                    actOneQuests.add(quest);

                    //get the list of quest objectives for the current quest
                    NodeList questObjectiveList = questElement.getElementsByTagName("quest_objective");

                    for (int j = 0; j < questObjectiveList.getLength(); j++) {
                        Node curQuestObjectiveNode = questObjectiveList.item(j);

                        //obtain quest objective elements for each quest in the list
                        if (curQuestObjectiveNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element questObjectiveElement = (Element)curQuestObjectiveNode;

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
                                    String speakToNPC = questObjectiveElement.getElementsByTagName("speak_to_npc").item(0).getTextContent();
                                    questObjectiveLocation = questObjectiveElement.getElementsByTagName("quest_location").item(0).getTextContent();
                                    break;
                                case "collect":
                                    questObjectiveName = questObjectiveElement.getElementsByTagName("quest_objective_name").item(0).getTextContent();
                                    String itemName = "insert_get_item_name_method";
                                    questObjectiveLocation = "insert_get_item_collectable_location";
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

}

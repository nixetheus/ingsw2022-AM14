/*package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.messages.InfoRequestMessage;
import it.polimi.ingsw.messages.LoginMessage;
import it.polimi.ingsw.messages.MoveMessage;
import it.polimi.ingsw.messages.PlayMessage;
import it.polimi.ingsw.view.MessageParser;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class to test every possible message to check if the parser works
 */
/*public class MessageParserTest {
//TODO adding from which player the message arrives
  String input;
  MessageParser parser = new MessageParser();
  String messageOutput;

  //WRONG INPUT TEST

  /**
   *testing if it return null in case of a completely wrong input
   */
 /* @Test
  public void wrongInputTest(){
    input="kjasfskb,gs ";
    messageOutput=parser.parser(input);

    Assert.assertNull(messageOutput);
  }

  /**
   *testing if return null with an empty string
   */
 /* @Test
  public void emptyInputTest(){
    input="";
    messageOutput=parser.parser(input);

    Assert.assertNull(messageOutput);
  }
//MOVE TESTS

  /**
   * Testing the parser output of a move of a student to dining room and comparing it to a message
   * created by hand with the expected parameters
   */
 /* @Test
  public void testMoveStudentToDiningRoomCorrect() {
    input = "move red student from entrance to dining room";
    messageOutput = parser.parser(input);
    MoveMessage moveMessageTest = new MoveMessage(MessageSecondary.ENTRANCE);
    moveMessageTest.setPlace(0);
    moveMessageTest.setStudentColor(Color.RED.ordinal());
    moveMessageTest.setIslandNumber(-1);
    moveMessageTest.setCloudTileNumber(-1);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(moveMessageTest), messageOutput);

  }

  /**
   * Testing the parser output of a mother nature movement and comparing it to a message created by
   * hand with the expected parameters
   */
  /* @Test
 public void testMoveMotherNatureCorrect() {
    input = "move mother nature to island 4";
    messageOutput = parser.parser(input);
    MoveMessage moveMessageTest = new MoveMessage(MessageSecondary.MOTHER_NATURE);
    moveMessageTest.setPlace(1);//to island present
    moveMessageTest.setStudentColor(0);
    moveMessageTest.setIslandNumber(4);
    moveMessageTest.setCloudTileNumber(4);//Not checked in controller

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(moveMessageTest), messageOutput);
  }*/

/*/**
   * Testing the parser output of taking a cloud tile and comparing it to a message created by hand
   * with the expected parameters
   */
/*  @Test
  public void testTakeCloudTileCorrect() {
    input = "take cloud tile 2";
    messageOutput = parser.parser(input);

    MoveMessage moveMessageTest = new MoveMessage(MessageSecondary.CLOUD_TILE);
    moveMessageTest.setPlace(-1);
    moveMessageTest.setStudentColor(0);
    moveMessageTest.setIslandNumber(2);//Not checked in controller
    moveMessageTest.setCloudTileNumber(2);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(moveMessageTest), messageOutput);
  }

  /**
   * Testing the parser output of putting a student on a certain cloud and comparing it to a message
   * created by hand with the expected parameters
   */
  /*@Test
  public void testStudentToIslandCorrect() {
    input = "move red student from entrance to island 4";
    messageOutput = parser.parser(input);

    MoveMessage moveMessageTest = new MoveMessage(MessageSecondary.ENTRANCE);
    moveMessageTest.setPlace(1);
    moveMessageTest.setStudentColor(Color.RED.ordinal());
    moveMessageTest.setIslandNumber(4);
    moveMessageTest.setCloudTileNumber(4);//Not checked in controller

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(moveMessageTest), messageOutput);
  }

//LOGIN TESTS

  /**
   * Testing the login message in case of a new connection sending the nickname and comparing it to
   * a message created by hand with the expected parameters
   */
/*  @Test
  public void testPlayerParamsCorrect() {
    input = "ale";
    messageOutput = parser.parser(input);

    LoginMessage loginMessage = new LoginMessage(MessageSecondary.PLAYER_PARAMS);
    loginMessage.setNickName("ALE");
    loginMessage.setGameExpert(false);
    loginMessage.setNumberOfPlayer(-1);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(loginMessage), messageOutput);
  }

  /**
   * Testing the login message in case of the first player sending the game parameters and comparing
   * it to a message created by hand with the expected parameters
   */
/*  @Test
  public void testGameParamsCorrectNotExpert() {
    input = "not expert mode 4 player";
    messageOutput = parser.parser(input);

    LoginMessage loginMessage = new LoginMessage(MessageSecondary.GAME_PARAMS);
    loginMessage.setNickName("NOT EXPERT MODE 4 PLAYER");
    loginMessage.setGameExpert(false);
    loginMessage.setNumberOfPlayer(4);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(loginMessage), messageOutput);
  }

  /**
   * Testing the login message in case of a new connection sending the nickname and comparing it to
   * a message created by hand with the expected parameters
   */
 /* @Test
  public void testGameParamsCorrectExpert() {
    input =  "expert mode 3 player";
    messageOutput = parser.parser(input);

    LoginMessage loginMessage = new LoginMessage(MessageSecondary.GAME_PARAMS);
    loginMessage.setNickName("EXPERT MODE 3 PLAYER");
    loginMessage.setGameExpert(true);
    loginMessage.setNumberOfPlayer(3);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(loginMessage), messageOutput);
  }

  //INFO TESTS

  /**
   * Testing if the infoMessage in case of a player message info will be correctly filled comparing
   * it with a message created by hand with the expected parameters
   */
 /* @Test
  public void testInfoPlayerCorrect() {
    input = "info player 4";
    messageOutput = parser.parser(input);

    InfoRequestMessage infoRequestMessage = new InfoRequestMessage(MessageSecondary.INFO_PLAYER);
    infoRequestMessage.setObjectId(4);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(infoRequestMessage), messageOutput);
  }

  /**
   * Testing if the infoMessage in case of a mother nature message info will be correctly filled
   * comparing it with a message created by hand with the expected parameters
   */
 /* @Test
  public void testInfoMotherNatureCorrect() {
    input = "info mother nature";
    messageOutput = parser.parser(input);

    InfoRequestMessage infoRequestMessage = new InfoRequestMessage(MessageSecondary.INFO_MN);
    infoRequestMessage.setObjectId(-1);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(infoRequestMessage), messageOutput);
  }

  /**
   * Testing if the infoMessage in case of a island message info will be correctly filled comparing
   * it with a message created by hand with the expected parameters
   */
 /* @Test
  public void testInfoIslandCorrect() {
    input = "info on island 7";
    messageOutput = parser.parser(input);

    InfoRequestMessage infoRequestMessage = new InfoRequestMessage(MessageSecondary.INFO_ISLAND);
    infoRequestMessage.setObjectId(7);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(infoRequestMessage), messageOutput);
  }

  /**
   * Testing if the infoMessage in case of a cloud tile message info will be correctly filled
   * comparing it with a message created by hand with the expected parameters
   */
/*  @Test
  public void testInfoCloudTileCorrect() {
    input = "info on cloud tile 2";
    messageOutput = parser.parser(input);

    InfoRequestMessage infoRequestMessage = new InfoRequestMessage(
        MessageSecondary.INFO_CLOUD_TILE);
    infoRequestMessage.setObjectId(2);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(infoRequestMessage), messageOutput);
  }

  /**
   * Testing if the infoMessage in case of an assistant message info will be correctly filled
   * comparing it with a message created by hand with the expected parameters
   */
 /* @Test
  public void testInfoAssistantCorrect() {
    input = "info on my assistants";
    messageOutput = parser.parser(input);

    InfoRequestMessage infoRequestMessage = new InfoRequestMessage(
        MessageSecondary.INFO_ASSISTANTS);
    infoRequestMessage.setObjectId(-1);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(infoRequestMessage), messageOutput);
  }

  /**
   * Testing if the infoMessage in case of a character message info will be correctly filled
   * comparing it with a message created by hand with the expected parameters
   */
 /* @Test
  public void testInfoCharacterCorrect() {
    input = "info on 2 character";
    messageOutput = parser.parser(input);

    InfoRequestMessage infoRequestMessage = new InfoRequestMessage(MessageSecondary.INFO_CHARACTER);
    infoRequestMessage.setObjectId(2);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(infoRequestMessage), messageOutput);
  }

  /**
   * Testing if the infoMessage in case of a help message will be correctly filled comparing it with
   * a message created by hand with the expected parameters
   */
 /* @Test
  public void testInfoHelp() {
    input = "help";
    messageOutput = parser.parser(input);

    InfoRequestMessage infoRequestMessage = new InfoRequestMessage(MessageSecondary.INFO_HELP);
    infoRequestMessage.setObjectId(-1);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(infoRequestMessage), messageOutput);
  }

  //PLAY MESSAGE

  /**
   * Testing a play message in an assistant case comparing it with a message created by hand with
   * the expected parameters
   */
/*  @Test
  public void testAssistant() {
    input = "play assistant 3";
    messageOutput = parser.parser(input);

    PlayMessage playMessage = new PlayMessage(MessageSecondary.ASSISTANT);
    playMessage.setCharacterId(3);
    playMessage.setAssistantId(3);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(playMessage), messageOutput);
  }

  /**
   * Testing a play message in a no entry character case comparing it with a message created by hand
   * with the expected parameters
   */
 /* @Test
  public void testCharacterNoEntry() {
    input = "purchase 3 character ,place a no entry tile on 5 island";
    messageOutput = parser.parser(input);

    PlayMessage playMessage = new PlayMessage(MessageSecondary.CHARACTER);
    playMessage.setCharacterId(3);
    playMessage.setAssistantId(3);
    playMessage.setNumIsland(5);
    playMessage.setMotherNatureMoves(0);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(playMessage), messageOutput);
  }

  /**
   * Testing a play message in a fake mother nature movement character case comparing it with a
   * message created by hand with the expected parameters
   */
 /* @Test
  public void testCharacterMotherNatureFakeMovement() {
    input = "purchase 3 character , resolve 5 island ";
    messageOutput = parser.parser(input);

    PlayMessage playMessage = new PlayMessage(MessageSecondary.CHARACTER);
    playMessage.setCharacterId(3);
    playMessage.setAssistantId(3);
    playMessage.setNumIsland(5);
    playMessage.setMotherNatureMoves(0);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(playMessage), messageOutput);
  }

  /**
   * Testing a play message in a mother nature plus 2 character case comparing it with a message
   * created by hand with the expected parameters
   */
 /* @Test
  public void testCharacterMoveMotherNaturePlusTwo() {
    input = "purchase 2 character , move mother nature 2 extra ";
    messageOutput = parser.parser(input);

    PlayMessage playMessage = new PlayMessage(MessageSecondary.CHARACTER);
    playMessage.setCharacterId(2);
    playMessage.setAssistantId(2);
    playMessage.setNumIsland(0);
    playMessage.setMotherNatureMoves(2);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(playMessage), messageOutput);
  }

  /**
   * Testing a play message in a place one student on a certain island character case comparing it
   * with a message created by hand with the expected parameters
   */
 /* @Test
  public void testCharacterPlaceOneStudentOnIsland() {
    input = "purchase 2 character , put a red student on island 5 ";
    messageOutput = parser.parser(input);

    PlayMessage playMessage = new PlayMessage(MessageSecondary.CHARACTER);
    playMessage.setCharacterId(2);
    playMessage.setAssistantId(2);
    playMessage.setNumIsland(5);
    playMessage.setColor(Color.RED);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(playMessage), messageOutput);
  }

  /**
   * Testing a play message in a place one student into dining room character case comparing it with
   * a message created by hand with the expected parameters
   */
 /* @Test
  public void testCharacterPlaceOneStudentIntoDiningRoom() {
    input = "purchase 2 character , put 1 red student to dining room ";
    messageOutput = parser.parser(input);

    PlayMessage playMessage = new PlayMessage(MessageSecondary.CHARACTER);
    playMessage.setCharacterId(2);
    playMessage.setAssistantId(2);
    playMessage.setNumIsland(0);
    playMessage.setColor(Color.RED);
    playMessage.setStudentsDiningRoom(new int[]{0, 0, 0, 1, 0});

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(playMessage), messageOutput);
  }

  /**
   * Testing a play message in a replace 2 student from entrance to dining room character case
   * comparing it with a message created by hand with the expected parameters
   */
 /* @Test
  public void testCharacterReplaceTwoStudentEntranceToDiningRoom() {
    input = "purchase 2 character ,take 2 green student from dining room and replace them with 1 red student 1 blue student from entrance";
    messageOutput = parser.parser(input);

    PlayMessage playMessage = new PlayMessage(MessageSecondary.CHARACTER);
    playMessage.setCharacterId(2);
    playMessage.setAssistantId(2);
    playMessage.setColor(Color.BLUE);
    playMessage.setStudentsDiningRoom(new int[]{0, 0, 2, 0, 0});
    playMessage.setStudentsEntrance(new int[]{0, 1, 0, 1, 0});

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(playMessage), messageOutput);
  }

  /**
   * Testing a play message in a no influence color character case comparing it with a message
   * created by hand with the expected parameters
   */
 /* @Test
  public void testCharacterColorNoInfluence() {
    input = "purchase 2 character , the red color will add no influence ";
    messageOutput = parser.parser(input);

    PlayMessage playMessage = new PlayMessage(MessageSecondary.CHARACTER);
    playMessage.setCharacterId(2);
    playMessage.setAssistantId(2);
    playMessage.setNumIsland(0);
    playMessage.setColor(Color.RED);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(playMessage), messageOutput);
  }

  /**
   * Testing a play message in a return three student from student to bag character case comparing
   * it with a message created by hand with the expected parameters
   */
 /* @Test
  public void testCharacterReturnThreeFromDiningRoom() {
    input = "purchase 2 character , return 3 red student to student bag ";
    messageOutput = parser.parser(input);

    PlayMessage playMessage = new PlayMessage(MessageSecondary.CHARACTER);
    playMessage.setCharacterId(2);
    playMessage.setAssistantId(2);
    playMessage.setColor(Color.RED);

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(playMessage), messageOutput);
  }

  /**
   * Testing a play message in a replace three student from the card to the entrance character case
   * comparing it with a message created by hand with the expected parameters
   */
 /* @Test
  public void testCharacterReplaceThreeCardToEntrance() {
    input = "purchase 2 character ,take 3 green student from this card and replace them with  1 red student 1 blue student 1 green student from entrance";
    messageOutput = parser.parser(input);

    PlayMessage playMessage = new PlayMessage(MessageSecondary.CHARACTER);
    playMessage.setCharacterId(2);
    playMessage.setAssistantId(2);
    playMessage.setColor(Color.BLUE);
    playMessage.setStudentsCard(new int[]{0, 0, 3, 0, 0});
    playMessage.setStudentsEntrance(new int[]{0, 1, 1, 1, 0});

    Gson gson = new Gson();

    Assert.assertEquals(gson.toJson(playMessage), messageOutput);
  }
}*/

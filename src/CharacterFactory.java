public class CharacterFactory {
    public Character getCharacter(String characterType, String characterName, Long characterExperience, Long characterLevel) {
        if (characterType == null)
            return null;

        if (characterType.equals("Warrior"))
            return new Warrior(characterName, characterExperience, characterLevel);

        if (characterType.equals("Mage"))
            return new Mage(characterName, characterExperience, characterLevel);

        if (characterType.equals("Rogue"))
            return new Rogue(characterName, characterExperience, characterLevel);

        return null;
    }
}
public class parseCardTester {
    public static void main(String[] args) {
        try {
            ParseCard parser = new ParseCard();
            Card[] cards = parser.parse("Card.xml");

            if (cards != null) {
                for (Card card : cards) {
                    System.out.println("Card Name: " + card.name);
                    //System.out.println("  Scene: " + card.sceneElement);
                    System.out.println("  Budget: " + card.budget);
                    for (Role role : card.roles) {
                        System.out.println("    Role Name: " + role.name +
                                           ", Level: " + role.rank +
                                           ", Line: " + role.catchPhrase);
                    }
                }
            } else {
                System.out.println("Parsing failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

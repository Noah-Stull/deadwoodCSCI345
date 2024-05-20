

public class parseTester {
    public static void main(String[] args) {
        try {
            //Board board = new Board();

            ParseSet parser = new ParseSet();

            Set[] sets = parser.parse("board.xml", null);

            if (sets != null) {
                for (Set set : sets) {
                    if (set != null) {
                        System.out.println("Set Name: " + set.name);
                        Role[] roles = set.getRoles();
                        if (roles != null) {
                            for (Role role : roles) {
                                if (role != null) {
                                    System.out.println("  Role Name: " + role.name +
                                        ", Level: " + role.rank +
                                        ", Line: " + role.catchPhrase);
                                }
                            }
                        }
                        for (Set s : set.getNeighborSets()) {
                            System.out.println("Neighbor Set: " + s.name);
                        }
                    }
                    else System.out.println("Null set");
                }
            } else {
                System.out.println("Parsing failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class DuplicateVotesException extends Exception {
    private String name;

    public DuplicateVotesException(String name) {
        this.name = name;
    }

    /**
     * gets name
     * @return name of candidate that was voted for multiple times
     */
    public String getName() {
        return name;
    }
}

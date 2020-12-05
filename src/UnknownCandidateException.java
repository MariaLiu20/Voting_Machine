public class UnknownCandidateException extends Exception {
    private String name;

    public UnknownCandidateException(String name) {
        this.name = name;
    }

    /**
     * gets name
     * @return name of candidate not on the ballot
     */
    public String getName() {
        return name;
    }
}

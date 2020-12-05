public class CandidateExistsException extends Exception {
    String name;

    public CandidateExistsException(String name) {
        this.name = name;
    }

}

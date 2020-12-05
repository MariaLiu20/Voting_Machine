/**
 * Represents the input/output portion of the voting system
 */
public class VotingMachine {
    private ElectionData forElection = new ElectionData();
    public VotingMachine(ElectionData forElection){
        this.forElection = forElection;
    }

    public void electionScreen() {
        forElection.screen();
    }
}
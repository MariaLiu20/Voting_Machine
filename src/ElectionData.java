import java.util.LinkedList;
import java.util.Scanner;
import java.util.HashMap;

class ElectionData {
    private LinkedList<String> ballot = new LinkedList<String>();
    private Scanner keyboard = new Scanner(System.in);
    private HashMap<Integer, LinkedList<String>> votes = new HashMap<Integer, LinkedList<String>>();

    public ElectionData() {}

    public void printBallot() {
        System.out.println("The candidates are ");
        for (String s : ballot) {
            System.out.println(s);
        }
    }

    public void screen() {
        this.printBallot();
        System.out.println("Who do you want to vote for?\n");
        String candidate = keyboard.next();
        String candidateTwo = keyboard.next();
        String candidateThree = keyboard.next();
        try {
            processVote(candidate, candidateTwo,candidateThree);
            System.out.println("You voted for " + candidate + " " + candidateTwo + " " + candidateThree);

        } catch(UnknownCandidateException e){
            System.out.println("Cannot find " + e.getName());
            System.out.println("Would you like to add candidate's name to the ballot?");
            String answer = keyboard.next();
            if(answer.equals("Y") || answer.equals("y")){
                System.out.println("Provide the name of the candidate");
                String candName = keyboard.next();
                addWriteIn(candName);
                this.screen();
            }
        } catch(DuplicateVotesException e){
            System.out.println("You cannot vote for the same candidate twice.");
            System.out.println("Voting process will now restart.");
            this.screen();
        }
    }

    /**
     * takes three strings (for the first, second, and third choices, respectively) and
     * stores a single voter's choices in data structure
     * @param vote1
     * @param vote2
     * @param vote3
     * @throws UnknownCandidateException
     * @throws DuplicateVotesException
     */
    public void processVote(String vote1, String vote2, String vote3) throws UnknownCandidateException,
            DuplicateVotesException {
        if (!ballot.contains(vote1))
            throw new UnknownCandidateException(vote1);
        else if (!ballot.contains(vote2))
            throw new UnknownCandidateException(vote2);
        else if (!ballot.contains(vote3))
            throw new UnknownCandidateException(vote3);
        else if (vote1.equalsIgnoreCase(vote2))
            throw new DuplicateVotesException(vote1);
        else if (vote1.equalsIgnoreCase(vote3))
            throw new DuplicateVotesException(vote1);
        else if (vote2.equalsIgnoreCase(vote3))
            throw new DuplicateVotesException(vote2);
        else {
            LinkedList<String> threeVotes = new LinkedList<String>();
            threeVotes.add(vote1);
            threeVotes.add(vote2);
            threeVotes.add(vote3);
            votes.put(votes.size(), threeVotes);
        }
    }

    /**
     * adds the given candidate ot the ballot
     * @param cand is the name of a candidate
     * @throws CandidateExistsException
     */
    public void addCandidate(String cand) throws CandidateExistsException {
        if (ballot.contains(cand))
            throw new CandidateExistsException(cand);
        else
            ballot.add(cand);
    }

    /**
     * returns name of winner with more than 50% of first place votes
     * if no candidate receives more than 50%, return "Runoff required"
     * @return name of winner or "Runoff required"
     */
    public String findWinnerMostFirstVotes() {
        HashMap<String, Integer> firstVotes = new HashMap<>();
        for (String cand : ballot) {
            firstVotes.put(cand, 0);
        }
        for (int i = 0; i < votes.size(); i++) {
            for (String cand : ballot) {
                if (votes.get(i).get(0).equals(cand))
                    firstVotes.replace(votes.get(i).get(0), firstVotes.get(cand) + 1);
            }
        }
        for (String cand : ballot) {
            if (firstVotes.get(cand) > votes.size()/2)
                return cand;
        }
        return "Runoff required";
    }

    /**
     * returns name of winner with most points under the following formula:
     * 3 points for each first-place vote, 2 points for each second-place vote, 1 point for each third-
     * place vote
     * @return name of winner or "No winner"
     */
    public String findWinnerMostPoints() {
        HashMap<String, Integer> points = new HashMap<>();
        for (String cand : ballot) {
            points.put(cand, 0);
        }
        // if vote i's first-place vote == this candidate, then add three
        for (int i = 0; i < votes.size(); i++)  {   // loop through candidates
            for (String cand : ballot) {    // loop through each set of votes
                if (votes.get(i).get(0).equals(cand))   // first-place vote of set i
                    points.replace(votes.get(i).get(0), points.get(cand) + 3);
                else if (votes.get(i).get(1).equals(cand))
                    points.replace(votes.get(i).get(1), points.get(cand) + 2);
                else if (votes.get(i).get(2).equals(cand))
                    points.replace(votes.get(i).get(2), points.get(cand) + 1);
            }
        }
        int max = points.get(ballot.getFirst());
        for (int i = 0; i < points.size()-1; i++) {
            for (int j = i+1; j < points.size(); j++) {
                if (points.get(ballot.get(j)) > points.get(ballot.get(i)))
                    max = points.get(ballot.get(j));
            }
        }
        // return key whose value is max
        for (int i = 0; i < ballot.size(); i++) {
            if (points.get(ballot.get(i)) == max)
                return ballot.get(i);
        }
        return "No winner";
    }

    public void addWriteIn(String name){
        try{
            this.addCandidate(name);
            System.out.println("Candidate was added successfully. Voting process will now restart.");
        } catch(CandidateExistsException e){
            System.out.println(e.name + "already exits");
        }

    }

    public LinkedList<String> getBallot(){
        return this.ballot;
    }

    public HashMap<Integer,LinkedList<String>> getVotes(){
        return this.votes;
    }
}
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Maria Liu
 * @author Kevin Dang
 */

public class Examples {
    public Examples() {}

    ElectionData Setup1() {
        ElectionData ED = new ElectionData();

        // put candidates on the ballot
        try {
            ED.addCandidate("gompei");
            ED.addCandidate("husky");
            ED.addCandidate("ziggy");
            ED.addCandidate("maria");
        } catch(CandidateExistsException e) {}

        // cast votes
        try {
            ED.processVote("gompei", "maria", "ziggy");
            ED.processVote("gompei", "maria", "husky");
            ED.processVote("gompei", "maria", "ziggy");
            ED.processVote("maria", "husky", "ziggy");
            ED.processVote("maria", "ziggy", "husky");
        } catch(UnknownCandidateException | DuplicateVotesException e) {}

        return(ED);
    }

    ElectionData Setup2() {
        ElectionData ED = new ElectionData();

        try {
            ED.addCandidate("gompei");
            ED.addCandidate("husky");
            ED.addCandidate("ziggy");
        } catch(CandidateExistsException e) {}

        try {
            ED.processVote("gompei", "husky", "ziggy");
            ED.processVote("husky", "gompei", "ziggy");
            ED.processVote("ziggy", "gompei", "ziggy");
        } catch(UnknownCandidateException | DuplicateVotesException e) {}

        return(ED);
    }

    ElectionData Setup3() {
        ElectionData ED = new ElectionData();

        try {
            ED.addCandidate("gompei");
            ED.addCandidate("husky");
            ED.addCandidate("ziggy");
        } catch(CandidateExistsException e) {}

        return(ED);
    }

    ElectionData Setup4() {
        ElectionData ED = new ElectionData();
        return(ED);
    }

    ElectionData Setup5() {
        ElectionData ED = new ElectionData();

        try {
            ED.addCandidate("gompei");
            ED.addCandidate("husky");
            ED.addCandidate("ziggy");
        } catch(CandidateExistsException e) {}

        try {
            ED.processVote("gompei", "ziggy", "husky");
            ED.processVote("gompei", "ziggy", "husky");
            ED.processVote("ziggy", "gompei", "husky");
            ED.processVote("ziggy", "gompei", "husky");

        } catch(UnknownCandidateException | DuplicateVotesException e) {}

        return(ED);
    }

    String Setup6() {
        ElectionData ED = new ElectionData();

        try {
            ED.addCandidate("gompei");
            ED.addCandidate("husky");
            ED.addCandidate("ziggy");
            ED.addCandidate("ziggy");
        } catch(CandidateExistsException e) {
            return "ziggy";
        }
        return("pass");
    }

    String Setup7() {
        ElectionData ED = new ElectionData();
        try {
            ED.addCandidate("gompei");
            ED.addCandidate("husky");
            ED.addCandidate("ziggy");
        } catch(CandidateExistsException e) {}
        try{
            ED.processVote("unknown", "unknown", "gompei");
            ED.processVote("husky", "ziggy", "gompei");
        } catch(UnknownCandidateException d) {
            return("unknown");
        } catch(DuplicateVotesException e){
            return("duplicate");
        }

        return("pass");
    }

    String Setup8() {
        ElectionData ED = new ElectionData();
        try {
            ED.addCandidate("gompei");
            ED.addCandidate("husky");
            ED.addCandidate("ziggy");
        } catch(CandidateExistsException e) {}
        try {
            ED.processVote("husky", "ziggy", "ziggy");
            ED.processVote("husky", "ziggy", "unknown");
        } catch(UnknownCandidateException d) {
            return("unknown");
        } catch(DuplicateVotesException e){
            return("ziggy");
        }

        return("pass");
    }

    String Setup9() {
        ElectionData ED = new ElectionData();
        try {
            ED.addCandidate("gompei");
            ED.addCandidate("husky");
            ED.addCandidate("ziggy");
        } catch(CandidateExistsException e) {}
        try {
            ED.processVote("husky", "ziggy", "gompei");
            ED.processVote("husky", "ziggy", "gompei");
        } catch(UnknownCandidateException d) {
            return("unknown");
        } catch(DuplicateVotesException e){
            return("ziggy");
        }

        return("pass");
    }

    String Setup10() {
        ElectionData ED = new ElectionData();
        try {
            ED.addCandidate("gompei");
            ED.addCandidate("husky");
            ED.addCandidate("ziggy");
        } catch(CandidateExistsException e) {}
        try {
            ED.processVote("husky", "husky", "unknown");
            ED.processVote("husky", "ziggy", "gompei");
        } catch(UnknownCandidateException d) {
            return("unknown");
        } catch(DuplicateVotesException e){
            return("ziggy");
        }

        return("pass");
    }

    ElectionData Setup11() {
        ElectionData ED = new ElectionData();

        try {
            ED.addCandidate("gompei");
            ED.addCandidate("husky");
            ED.addCandidate("ziggy");
        } catch(CandidateExistsException e) {}

        try {
            ED.processVote("gompei", "ziggy", "husky");
            ED.processVote("husky", "gompei", "ziggy");
            ED.processVote("ziggy", "husky", "gompei");

        } catch(UnknownCandidateException | DuplicateVotesException e) {}

        return(ED);
    }

    @Test
    public void testMostFirstWinner() {
        assertEquals("gompei", Setup1().findWinnerMostFirstVotes());
    }
    @Test
    public void testMostFirst_NoWinner() {
        assertEquals("Runoff required", Setup2().findWinnerMostFirstVotes());
    }
    @Test
    public void testMostFirst_NoVotes() {
        assertEquals("Runoff required", Setup3().findWinnerMostFirstVotes());
    }
    @Test
    public void testMostFirst_Tie() {
        assertEquals("Runoff required", Setup5().findWinnerMostFirstVotes());
    }

    @Test
    public void testMostPoints() {
        assertEquals("maria", Setup1().findWinnerMostPoints());
    }
    @Test
    public void testMostPoints_NoCandidates_NoVotes() {
        assertEquals("No winner", Setup4().findWinnerMostPoints());
    }
    @Test
    public void testMostPoints_Tie() {
        assertEquals("gompei", Setup5().findWinnerMostPoints());
    }

    @Test
    public void testMostFirst_UnknownDuplicate() {
        assertEquals("unknown", Setup7());
    }
    @Test
    public void testMostFirst_Duplicate() {
        assertEquals("ziggy", Setup8());
    }
    @Test
    public void testMostFirst_NoExceptions() {
        assertEquals("pass", Setup9());
    }
    @Test
    public void testForTiesInMostPoints() {assertTrue(Setup5().findWinnerMostPoints().equals("husky") || Setup5().findWinnerMostPoints().equals("gompei"));}
    @Test
    public void testForTiesInMostPointsOne() {
        String result = Setup11().findWinnerMostPoints();
        assertTrue(result.equals("gompei") || result.equals("husky") || result.equals("ziggy"));}
    @Test
    public void testForPrioritizeUnknownDuplicate(){
        assertEquals("unknown",Setup10());
    }
    @Test
    public void testForCandExist(){
        assertEquals("ziggy",Setup6());
    }

}

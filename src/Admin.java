import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class Admin {
    private JFrame frame;
    private Connection connection;
    private String winningParty; // Declaration of winningParty variable

    public Admin(JFrame frame, Connection connection) {
        this.frame = frame;
        this.connection = connection;
    }

    public void openAdminView() {
        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new BorderLayout());
        adminPanel.setBackground(new Color(240, 240, 240));

        JLabel adminLabel = new JLabel("Welcome, Admin!");
        adminLabel.setFont(new Font("Arial", Font.BOLD, 24));
        adminLabel.setHorizontalAlignment(SwingConstants.CENTER);
        adminPanel.add(adminLabel, BorderLayout.NORTH);

        Map<String, Integer> voteCounts = getVoteCounts();

        // Sort the map by value (vote counts)
        Map<String, Integer> sortedVoteCounts = voteCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        JPanel votePanel = new JPanel();
        votePanel.setLayout(new BoxLayout(votePanel, BoxLayout.Y_AXIS)); // Align vertically
        votePanel.setBackground(new Color(240, 240, 240)); // Set background color

        int maxVotes = sortedVoteCounts.values().iterator().next(); // Get max votes

        boolean tie = false;
        for (Map.Entry<String, Integer> entry : sortedVoteCounts.entrySet()) {
            JPanel partyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5)); // FlowLayout for party name
            partyPanel.setBackground(new Color(240, 240, 240)); // Set background color

            JLabel voteLabel = new JLabel(entry.getKey() + ": " + entry.getValue());
            voteLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for party labels
            if (entry.getValue() == maxVotes) {
                voteLabel.setForeground(Color.RED);
                if (tie) {
                    winningParty += ", " + entry.getKey();
                } else {
                    winningParty = entry.getKey();
                    tie = true;
                }
            }
            partyPanel.add(voteLabel);
            votePanel.add(partyPanel);
        }

        JScrollPane scrollPane = new JScrollPane(votePanel);
        scrollPane.setPreferredSize(new Dimension(400, 300)); // Set preferred size for the scroll pane
        adminPanel.add(scrollPane, BorderLayout.CENTER);

        JLabel winningLabel;
        if (!tie) {
            winningLabel = new JLabel("Winning Party: " + determineWinningParty(sortedVoteCounts));
        } else {
            winningLabel = new JLabel("Party or Parties Winning: " + winningParty);
        }
        winningLabel.setFont(new Font("Arial", Font.BOLD, 16));
        winningLabel.setHorizontalAlignment(SwingConstants.CENTER);
        adminPanel.add(winningLabel, BorderLayout.SOUTH);


        frame.getContentPane().removeAll();
        frame.add(adminPanel);
        frame.revalidate();
        frame.repaint();
    }

    private Map<String, Integer> getVoteCounts() {
        Map<String, Integer> voteCounts = new HashMap<>();
        try {
            // Initialize vote counts for all parties with 0 votes
            voteCounts.put("BJP", 0);
            voteCounts.put("Aam Aadmi Party", 0);
            voteCounts.put("INC", 0);
            voteCounts.put("CPI", 0);
            voteCounts.put("CPIM", 0);
            voteCounts.put("DMK", 0);
            voteCounts.put("AIADMK", 0);

            // Query the database for actual vote counts
            String sql = "SELECT voted_party, COUNT(*) AS vote_count FROM votes GROUP BY voted_party ORDER BY vote_count DESC";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String party = resultSet.getString("voted_party");
                int count = resultSet.getInt("vote_count");
                voteCounts.put(party, count);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return voteCounts;
    }

    private String determineWinningParty(Map<String, Integer> voteCounts) {
        String winningParty = "";
        int maxVotes = 0;
        for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                winningParty = entry.getKey();
            }
        }
        return winningParty;
    }
}

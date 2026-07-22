import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CancellationFrame extends JFrame {

    JTextField pnrField;
    JTextArea detailsArea;

    JButton fetchButton;
    JButton cancelButton;

    public CancellationFrame() {

        setTitle("Cancel Reservation");
        setSize(500, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel pnrLabel = new JLabel("PNR Number:");
        pnrLabel.setBounds(30, 30, 100, 25);
        add(pnrLabel);

        pnrField = new JTextField();
        pnrField.setBounds(140, 30, 200, 25);
        add(pnrField);

        fetchButton = new JButton("Fetch");
        fetchButton.setBounds(350, 30, 90, 25);
        add(fetchButton);

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setBounds(30, 80, 410, 180);
        add(scrollPane);

        cancelButton = new JButton("Cancel Ticket");
        cancelButton.setBounds(160, 290, 150, 30);
        add(cancelButton);

        fetchButton.addActionListener(e -> fetchBooking());

        cancelButton.addActionListener(e -> cancelBooking());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fetchBooking() {

        String pnr = pnrField.getText().trim();

        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Enter PNR Number");
            return;
        }

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM reservations WHERE pnr=?"
            );

            ps.setString(1, pnr);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                detailsArea.setText(
                        "PNR : " + rs.getString("pnr") +
                                "\nPassenger : " + rs.getString("passengerName") +
                                "\nTrain No : " + rs.getInt("trainNo") +
                                "\nTrain Name : " + rs.getString("trainName") +
                                "\nClass : " + rs.getString("classType") +
                                "\nJourney Date : " + rs.getString("journeyDate") +
                                "\nSource : " + rs.getString("source") +
                                "\nDestination : " + rs.getString("destination")
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "PNR Not Found"
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void cancelBooking() {

        String pnr = pnrField.getText().trim();

        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Enter PNR Number");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to cancel this ticket?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM reservations WHERE pnr=?"
            );

            ps.setString(1, pnr);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ticket Cancelled Successfully"
                );

                detailsArea.setText("");
                pnrField.setText("");

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "PNR Not Found"
                );
            }

            ps.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
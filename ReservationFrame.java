import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReservationFrame extends JFrame {

    JTextField passengerField;
    JTextField trainNoField;
    JTextField trainNameField;
    JTextField dateField;
    JTextField sourceField;
    JTextField destinationField;

    JComboBox<String> classBox;

    JButton bookButton;
    JButton cancelButton;

    public ReservationFrame() {

        setTitle("Reservation Form");
        setSize(500, 450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Passenger Name
        JLabel pLabel = new JLabel("Passenger Name:");
        pLabel.setBounds(30, 30, 120, 25);
        add(pLabel);

        passengerField = new JTextField();
        passengerField.setBounds(180, 30, 200, 25);
        add(passengerField);

        // Train Number
        JLabel trainNoLabel = new JLabel("Train Number:");
        trainNoLabel.setBounds(30, 70, 120, 25);
        add(trainNoLabel);

        trainNoField = new JTextField();
        trainNoField.setBounds(180, 70, 200, 25);
        add(trainNoField);

        // Train Name
        JLabel trainNameLabel = new JLabel("Train Name:");
        trainNameLabel.setBounds(30, 110, 120, 25);
        add(trainNameLabel);

        trainNameField = new JTextField();
        trainNameField.setBounds(180, 110, 200, 25);
        trainNameField.setEditable(false);
        add(trainNameField);

        // Auto-fill Train Name
        trainNoField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {

                String trainNo = trainNoField.getText().trim();

                if (trainNo.equals("12345")) {
                    trainNameField.setText("Rajdhani Express");
                }
                else if (trainNo.equals("12627")) {
                    trainNameField.setText("Karnataka Express");
                }
                else if (trainNo.equals("12760")) {
                    trainNameField.setText("Charminar Express");
                }
                else {
                    trainNameField.setText("Unknown Train");
                }
            }
        });

        // Class Type
        JLabel classLabel = new JLabel("Class Type:");
        classLabel.setBounds(30, 150, 120, 25);
        add(classLabel);

        classBox = new JComboBox<>(
                new String[]{"Sleeper", "AC", "First Class"});
        classBox.setBounds(180, 150, 200, 25);
        add(classBox);

        // Journey Date
        JLabel dateLabel = new JLabel("Journey Date:");
        dateLabel.setBounds(30, 190, 120, 25);
        add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(180, 190, 200, 25);
        add(dateField);

        // Source
        JLabel sourceLabel = new JLabel("Source:");
        sourceLabel.setBounds(30, 230, 120, 25);
        add(sourceLabel);

        sourceField = new JTextField();
        sourceField.setBounds(180, 230, 200, 25);
        add(sourceField);

        // Destination
        JLabel destLabel = new JLabel("Destination:");
        destLabel.setBounds(30, 270, 120, 25);
        add(destLabel);

        destinationField = new JTextField();
        destinationField.setBounds(180, 270, 200, 25);
        add(destinationField);

        // Book Button
        bookButton = new JButton("Book Ticket");
        bookButton.setBounds(80, 330, 130, 30);
        add(bookButton);

        // Cancel Button
        cancelButton = new JButton("Cancel Ticket");
        cancelButton.addActionListener(e -> {
            new CancellationFrame();
        });
        cancelButton.setBounds(250, 330, 130, 30);
        add(cancelButton);

        // Book Ticket Action
        bookButton.addActionListener(e -> {

            String passenger = passengerField.getText().trim();
            String trainNo = trainNoField.getText().trim();
            String trainName = trainNameField.getText().trim();
            String classType = classBox.getSelectedItem().toString();
            String date = dateField.getText().trim();
            String source = sourceField.getText().trim();
            String destination = destinationField.getText().trim();

            if (passenger.isEmpty() ||
                    trainNo.isEmpty() ||
                    date.isEmpty() ||
                    source.isEmpty() ||
                    destination.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields!"
                );
                return;
            }

            try {

                int trainNumber = Integer.parseInt(trainNo);

                String pnr = "PNR" + System.currentTimeMillis();

                Connection con = DBConnection.getConnection();

                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO reservations VALUES(?,?,?,?,?,?,?,?)"
                );

                ps.setString(1, pnr);
                ps.setString(2, passenger);
                ps.setInt(3, trainNumber);
                ps.setString(4, trainName);
                ps.setString(5, classType);
                ps.setString(6, date);
                ps.setString(7, source);
                ps.setString(8, destination);

                ps.executeUpdate();

                ps.close();
                con.close();

                JOptionPane.showMessageDialog(
                        this,
                        "Booking Successful\n\n" +
                                "PNR : " + pnr +
                                "\nPassenger : " + passenger +
                                "\nTrain : " + trainName
                );

                passengerField.setText("");
                trainNoField.setText("");
                trainNameField.setText("");
                dateField.setText("");
                sourceField.setText("");
                destinationField.setText("");

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Train Number must be numeric!"
                );

            } catch (Exception ex) {
                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage()
                );
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Comparator;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ViewImage extends JFrame {
    private JPanel panel;

    public ViewImage() {
        setTitle("Image Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Fixed window size

        // Navigation bar panel
        JPanel navBarPanel = new JPanel();
        JButton addImageButton = new JButton("Add Image");
        JButton logoutButton = new JButton("Logout");

        // Add action listeners to buttons
        addImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open a file chooser dialog to select an image
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(ViewImage.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile != null && selectedFile.exists()) {
                        // Add logic to copy the selected image to a specific location
                        try {
                            String destinationPath = "E:/Project Images/" + Login.username + "/" + selectedFile.getName();
                            Path destination = Path.of(destinationPath);
                            Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
                            JOptionPane.showMessageDialog(null, "Image added Successfully");
                            refreshPanel(); // Refresh the panel to show the newly added image
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Failed to add image");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Selected file does not exist.");
                    }
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Login().setVisible(true); // Open the login window
                dispose(); // Dispose of the current window
            }
        });

        navBarPanel.add(addImageButton);
        navBarPanel.add(logoutButton);
        add(navBarPanel, BorderLayout.NORTH);

        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 4, 10, 10)); // Grid layout with 4 images per row and spacing

        JScrollPane scrollPane = new JScrollPane(panel);
        getContentPane().add(scrollPane);

        setLocationRelativeTo(null);
        setVisible(true);

        // Load images initially
        refreshPanel();
    }

    // Method to refresh the panel by reloading images
    private void refreshPanel() {
        panel.removeAll(); // Remove all existing images
        String path = "E:/Project Images/" + Login.username;
        File folder = new File(path);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                // Sort files based on last modified time
                Arrays.sort(files, Comparator.comparingLong(File::lastModified));
                for (File file : files) {
                    try {
                        // Load the image
                        BufferedImage originalImage = ImageIO.read(file);
                        // Resize the image
                        int targetWidth = 200; // Change this to your desired width
                        int targetHeight = 200; // Change this to your desired height
                        Image resizedImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                        // Convert the resized image back to BufferedImage
                        BufferedImage bufferedResizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
                        Graphics2D g = bufferedResizedImage.createGraphics();
                        g.drawImage(resizedImage, 0, 0, null);
                        g.dispose();
                        // Convert BufferedImage to ImageIcon and display it
                        ImageIcon imageIcon = new ImageIcon(bufferedResizedImage);
                        JLabel label = new JLabel(imageIcon);
                        // Add mouse hover effect to show delete option
                        label.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseEntered(MouseEvent e) {
                                label.setText("<html><font color='red'>Delete</font></html>");
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                label.setText(null);
                            }

                            @Override
                            public void mouseClicked(MouseEvent e) {
                                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this image?", "Confirmation", JOptionPane.YES_NO_OPTION);
                                if (option == JOptionPane.YES_OPTION) {
                                    boolean deleted = file.delete();
                                    if (deleted) {
                                        JOptionPane.showMessageDialog(null, "Image deleted successfully!");
                                        refreshPanel(); // Refresh the panel after deletion
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Failed to delete image.");
                                    }
                                }
                            }
                        });
                        panel.add(label);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Failed to list files in the directory.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Directory does not exist or is not a directory.");
        }
        panel.revalidate(); // Revalidate the panel to reflect changes
        panel.repaint(); // Repaint the panel
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewImage());
    }
}

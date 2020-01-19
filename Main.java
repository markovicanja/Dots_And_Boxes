package etf.dotsandboxes.ma170420d;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.Insets;
import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.Choice;
import javax.swing.JButton;
import java.awt.event.*;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

public class Main extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JSlider slider1, slider2;
	private JList list1, list2;
	private Choice choice1, choice2;
	private static Font font = new Font("Algerian", Font.PLAIN, 15);
	private JTextField readDirectoryText;
	private JTextField depthText;
	private JTextField writeDirectoryText;
	private JRadioButton radioButtonStepByStep, radioButtonExecute;

	public Main() {
		setTitle("Dots and Boxes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,750);
//		setSize(1000,700);
		this.setLocationRelativeTo(null);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		contentPane = new JPanel();
		mainPanel.add(contentPane, "Center");
		setContentPane(mainPanel);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("DBimage.jpg")));
		mainPanel.add(imageLabel, "North");
		
		JLabel lblWidth = new JLabel("Width");
		lblWidth.setFont(font);
		GridBagConstraints gbc_lblWidth = new GridBagConstraints();
		gbc_lblWidth.anchor = GridBagConstraints.NORTH;
		gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblWidth.gridx = 2;
		gbc_lblWidth.gridy = 2;
		contentPane.add(lblWidth, gbc_lblWidth);
		
		JLabel lblNewLabel = new JLabel("Height");
		lblNewLabel.setFont(font);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 2;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel gridSizeLabel = new JLabel("Grid size");
		gridSizeLabel.setFont(font);
		GridBagConstraints gbc_gridSizeLabel = new GridBagConstraints();
		gbc_gridSizeLabel.anchor = GridBagConstraints.EAST;
		gbc_gridSizeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_gridSizeLabel.gridx = 1;
		gbc_gridSizeLabel.gridy = 3;
		contentPane.add(gridSizeLabel, gbc_gridSizeLabel);
		
		slider1 = new JSlider();
		slider1.setMajorTickSpacing(1);
		slider1.setPaintTicks(true);
		slider1.setToolTipText("");
		slider1.setSnapToTicks(true);
		slider1.setMinorTickSpacing(1);
		slider1.setPaintLabels(true);
		slider1.setMinimum(1);
		slider1.setMaximum(9);
		slider1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_slider1 = new GridBagConstraints();
		gbc_slider1.insets = new Insets(0, 0, 5, 5);
		gbc_slider1.gridx = 2;
		gbc_slider1.gridy = 3;
		contentPane.add(slider1, gbc_slider1);
		
		slider2 = new JSlider();
		slider2.setSnapToTicks(true);
		slider2.setPaintTicks(true);
		slider2.setPaintLabels(true);
		slider2.setMinorTickSpacing(1);
		slider2.setMajorTickSpacing(1);
		slider2.setMinimum(1);
		slider2.setMaximum(9);
		slider2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.insets = new Insets(0, 0, 5, 5);
		gbc_slider.gridx = 3;
		gbc_slider.gridy = 3;
		contentPane.add(slider2, gbc_slider);
		
		JLabel lblPlayer1 = new JLabel("Player 1");
		lblPlayer1.setFont(font);
		GridBagConstraints gbc_lblPlayer1 = new GridBagConstraints();
		gbc_lblPlayer1.anchor = GridBagConstraints.EAST;
		gbc_lblPlayer1.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayer1.gridx = 1;
		gbc_lblPlayer1.gridy = 5;
		contentPane.add(lblPlayer1, gbc_lblPlayer1);
		
		list1 = new JList();
		list1.setBackground(UIManager.getColor("InternalFrame.borderColor"));
		list1.setVisibleRowCount(2);
		list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list1.setModel(new AbstractListModel() {
			String[] values = new String[] {"Human", "Computer"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list1.setSelectedIndex(0);
		list1.setFont(font);
		list1.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		GridBagConstraints gbc_list1 = new GridBagConstraints();
		gbc_list1.insets = new Insets(0, 0, 5, 5);
		gbc_list1.gridx = 2;
		gbc_list1.gridy = 5;
		contentPane.add(list1, gbc_list1);
		
		choice1 = new Choice();
		choice1.setFont(font);
		choice1.setBackground(UIManager.getColor("InternalFrame.borderColor"));
		choice1.add("Easy"); 
		choice1.add("Medium"); 
		choice1.add("Hard"); 
		GridBagConstraints gbc_choice1 = new GridBagConstraints();
		gbc_choice1.insets = new Insets(0, 0, 5, 5);
		gbc_choice1.gridx = 3;
		gbc_choice1.gridy = 5;
		contentPane.add(choice1, gbc_choice1);
		
		JLabel lblPlayer2 = new JLabel("Player 2");
		lblPlayer2.setFont(font);
		GridBagConstraints gbc_lblPlayer2 = new GridBagConstraints();
		gbc_lblPlayer2.anchor = GridBagConstraints.EAST;
		gbc_lblPlayer2.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayer2.gridx = 1;
		gbc_lblPlayer2.gridy = 6;
		contentPane.add(lblPlayer2, gbc_lblPlayer2);
		
		JButton startButton = new JButton("Start");
		startButton.addActionListener(this);
		
		list2 = new JList();
		list2.setVisibleRowCount(2);
		list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list2.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list2.setFont(font);
		list2.setModel(new AbstractListModel() {
			String[] values = new String[] {"Human", "Computer"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list2.setSelectedIndex(0);
		list2.setBackground(UIManager.getColor("InternalFrame.borderColor"));
		GridBagConstraints gbc_list2 = new GridBagConstraints();
		gbc_list2.insets = new Insets(0, 0, 5, 5);
		gbc_list2.gridx = 2;
		gbc_list2.gridy = 6;
		contentPane.add(list2, gbc_list2);
		
		choice2 = new Choice();
		choice2.setFont(font);
		choice2.setBackground(UIManager.getColor("InternalFrame.borderColor"));
		choice2.add("Easy"); 
		choice2.add("Medium"); 
		choice2.add("Hard");
		GridBagConstraints gbc_choice2 = new GridBagConstraints();
		gbc_choice2.insets = new Insets(0, 0, 5, 5);
		gbc_choice2.gridx = 3;
		gbc_choice2.gridy = 6;
		contentPane.add(choice2, gbc_choice2);
		
		JLabel depthLabel = new JLabel("Depth of tree:");
		depthLabel.setFont(font);
		GridBagConstraints gbc_depthLabel = new GridBagConstraints();
		gbc_depthLabel.anchor = GridBagConstraints.EAST;
		gbc_depthLabel.insets = new Insets(0, 0, 5, 5);
		gbc_depthLabel.gridx = 1;
		gbc_depthLabel.gridy = 8;
		contentPane.add(depthLabel, gbc_depthLabel);
		
		depthText = new JTextField();
		GridBagConstraints gbc_depthText = new GridBagConstraints();
		gbc_depthText.anchor = GridBagConstraints.NORTHWEST;
		gbc_depthText.insets = new Insets(0, 0, 5, 5);
		gbc_depthText.gridx = 2;
		gbc_depthText.gridy = 8;
		contentPane.add(depthText, gbc_depthText);
		depthText.setColumns(30);
		
		JLabel readDirectoryLabel = new JLabel("Read from file:");
		readDirectoryLabel.setFont(font);
		GridBagConstraints gbc_readDirectoryLabel = new GridBagConstraints();
		gbc_readDirectoryLabel.anchor = GridBagConstraints.EAST;
		gbc_readDirectoryLabel.insets = new Insets(0, 0, 5, 5);
		gbc_readDirectoryLabel.gridx = 1;
		gbc_readDirectoryLabel.gridy = 9;
		contentPane.add(readDirectoryLabel, gbc_readDirectoryLabel);
		
		readDirectoryText = new JTextField();
		//readDirectoryText.setText("C:\\Users\\Anja\\eclipse-workspace\\DotsAndBoxes\\src\\etf\\dotsandboxes\\ma170420d\\Test.txt");
		GridBagConstraints gbc_readDirectoryText = new GridBagConstraints();
		gbc_readDirectoryText.anchor = GridBagConstraints.WEST;
		gbc_readDirectoryText.insets = new Insets(0, 0, 5, 5);
		gbc_readDirectoryText.gridx = 2;
		gbc_readDirectoryText.gridy = 9;
		contentPane.add(readDirectoryText, gbc_readDirectoryText);
		readDirectoryText.setColumns(30);
		
		JLabel writeDirectoryLabel = new JLabel("Write in file:");
		writeDirectoryLabel.setFont(font);
		GridBagConstraints gbc_writeDirectoryLabel = new GridBagConstraints();
		gbc_writeDirectoryLabel.anchor = GridBagConstraints.EAST;
		gbc_writeDirectoryLabel.insets = new Insets(0, 0, 5, 5);
		gbc_writeDirectoryLabel.gridx = 1;
		gbc_writeDirectoryLabel.gridy = 10;
		contentPane.add(writeDirectoryLabel, gbc_writeDirectoryLabel);
		
		writeDirectoryText = new JTextField();
		writeDirectoryText.setText("C:\\Users\\Anja\\eclipse-workspace\\DotsAndBoxes\\src\\etf\\dotsandboxes\\ma170420d\\write.txt");
		GridBagConstraints gbc_writeDirectoryText = new GridBagConstraints();
		gbc_writeDirectoryText.anchor = GridBagConstraints.WEST;
		gbc_writeDirectoryText.insets = new Insets(0, 0, 5, 5);
		gbc_writeDirectoryText.gridx = 2;
		gbc_writeDirectoryText.gridy = 10;
		contentPane.add(writeDirectoryText, gbc_writeDirectoryText);
		writeDirectoryText.setColumns(30);
		
		radioButtonStepByStep = new JRadioButton("Step by step");
		radioButtonStepByStep.setSelected(true);
		radioButtonStepByStep.setFont(new Font("Algerian", Font.PLAIN, 15));
		GridBagConstraints gbc_radioButtonStepByStep = new GridBagConstraints();
		gbc_radioButtonStepByStep.anchor = GridBagConstraints.WEST;
		gbc_radioButtonStepByStep.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonStepByStep.gridx = 1;
		gbc_radioButtonStepByStep.gridy = 12;
		contentPane.add(radioButtonStepByStep, gbc_radioButtonStepByStep);
		
		radioButtonExecute = new JRadioButton("Execute");
		radioButtonExecute.setFont(new Font("Algerian", Font.PLAIN, 15));
		GridBagConstraints gbc_radioButtonExecute = new GridBagConstraints();
		gbc_radioButtonExecute.anchor = GridBagConstraints.WEST;
		gbc_radioButtonExecute.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonExecute.gridx = 1;
		gbc_radioButtonExecute.gridy = 13;
		contentPane.add(radioButtonExecute, gbc_radioButtonExecute);
		startButton.setForeground(UIManager.getColor("Button.foreground"));
		startButton.setBackground(UIManager.getColor("Button.background"));
		startButton.setFont(font);
		GridBagConstraints gbc_startButton = new GridBagConstraints();
		gbc_startButton.anchor = GridBagConstraints.NORTH;
		gbc_startButton.insets = new Insets(0, 0, 5, 5);
		gbc_startButton.gridx = 1;
		gbc_startButton.gridy = 15;
		contentPane.add(startButton, gbc_startButton);
		

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(radioButtonExecute);
		buttonGroup.add(radioButtonStepByStep);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		int m = (int)slider2.getValue();
		int n = (int)slider1.getValue();
		String player1 = (String)list1.getSelectedValue();
		String player2 = (String)list2.getSelectedValue();
		String difficulty1 = choice1.getSelectedItem();
		String difficulty2 = choice2.getSelectedItem();
		String depth = depthText.getText();
		int maxDepth = 0;
		try {
			maxDepth = Integer.parseInt(depth);
		} catch(NumberFormatException e) {
			maxDepth = 3;
		}
		int seconds = 1000;
		if (player1.equals("Computer") && player2.equals("Computer") && radioButtonExecute.isSelected()) {
			seconds = 0;
		}
		
		String readPath = readDirectoryText.getText();
		String writePath = writeDirectoryText.getText();
		FileIO fio = new FileIO(readPath, writePath, m, n);
		if (!readPath.equals("")) {
			boolean ok = fio.read();
			if (!ok) return;
		}
		
		new GamePlay(m, n, player1, player2, difficulty1, difficulty2, fio, maxDepth, seconds);
		this.setVisible(false);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

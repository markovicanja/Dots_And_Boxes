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
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.JProgressBar;
import java.awt.Choice;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JSlider slider1, slider2;
	private JList list1, list2;
	private Choice choice1, choice2;
	private static Font font = new Font("Algerian", Font.PLAIN, 15);

	public Main() {
		setTitle("Dots and Boxes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,550);
		this.setLocationRelativeTo(null);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		contentPane = new JPanel();
		mainPanel.add(contentPane, "Center");
		setContentPane(mainPanel);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		gbc_gridSizeLabel.anchor = GridBagConstraints.WEST;
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
		choice1.add("Easy"); choice1.add("Medium"); choice1.add("Hard"); 
		GridBagConstraints gbc_choice1 = new GridBagConstraints();
		gbc_choice1.insets = new Insets(0, 0, 5, 5);
		gbc_choice1.gridx = 3;
		gbc_choice1.gridy = 5;
		contentPane.add(choice1, gbc_choice1);
		
		JLabel lblPlayer2 = new JLabel("Player 2");
		lblPlayer2.setFont(font);
		GridBagConstraints gbc_lblPlayer2 = new GridBagConstraints();
		gbc_lblPlayer2.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayer2.gridx = 1;
		gbc_lblPlayer2.gridy = 6;
		contentPane.add(lblPlayer2, gbc_lblPlayer2);
		
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
		choice2.add("Easy"); choice2.add("Medium"); choice2.add("Hard");
		GridBagConstraints gbc_choice2 = new GridBagConstraints();
		gbc_choice2.insets = new Insets(0, 0, 5, 5);
		gbc_choice2.gridx = 3;
		gbc_choice2.gridy = 6;
		contentPane.add(choice2, gbc_choice2);
		
		JButton startButton = new JButton("Start");
		startButton.addActionListener(this);
		startButton.setForeground(UIManager.getColor("Button.foreground"));
		startButton.setBackground(UIManager.getColor("Button.background"));
		startButton.setFont(font);
		GridBagConstraints gbc_startButton = new GridBagConstraints();
		gbc_startButton.anchor = GridBagConstraints.NORTH;
		gbc_startButton.insets = new Insets(0, 0, 5, 5);
		gbc_startButton.gridx = 1;
		gbc_startButton.gridy = 10;
		contentPane.add(startButton, gbc_startButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		int m = (int)slider1.getValue();
		int n = (int)slider2.getValue();
		String player1 = (String)list1.getSelectedValue();
		String player2 = (String)list2.getSelectedValue();
		String difficulty1 = choice1.getSelectedItem();
		String difficulty2 = choice2.getSelectedItem();
		
//		System.out.println(player1+" "+player2+" "+difficulty1+" "+difficulty2);
		new GamePlay(m, n, player1, player2, difficulty1, difficulty2);
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

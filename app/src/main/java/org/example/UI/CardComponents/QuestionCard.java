package org.example.UI.CardComponents;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;
import org.example.models.Question;
import org.example.UI.QuestionHandler;
import org.example.UI.CardComponents.AnimationUtils; 


public class QuestionCard extends RoundedPanel {
    private static final String QUESTION_VIEW_CARD = "QuestionViewCard";
    private static final String FEEDBACK_VIEW_CARD = "FeedbackViewCard";

    private ButtonGroup answerGroup;
    private JPanel answersPanel;
    private JLabel hintLabel;
    private JLabel feedbackExplanationLabel;
    private JLabel feedbackStatusIconLabel;
    private JLabel feedbackQuestionMarkLabel;
    private RoundedButton retryButton;
    private RoundedButton hintButton;
    private RoundedButton checkAnswerButton;
    private CardLayout mainCardLayout;
    private RoundedPanel mainCardPanel;

    private final QuestionHandler questionHandler;
    private final Question currentQuestion;
    private final Color accentColor; 

    public QuestionCard(QuestionHandler questionHandler, Question question, Color accentColor) {
        super(20, Color.WHITE);
        this.questionHandler = questionHandler;
        this.currentQuestion = question;
        this.accentColor = accentColor; 
        
        this.mainCardPanel = this;
        mainCardLayout = new CardLayout();
        setLayout(mainCardLayout);
        setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(new Color(0xDCDCDC), 20, 1),
            new EmptyBorder(5, 5, 5, 5)
        ));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(780, Short.MAX_VALUE));

        JScrollPane questionDisplayScrollPane = createQuestionDisplayScrollPane(accentColor);
        JPanel feedbackViewPanel = createFeedbackViewPanel(accentColor);

        add(questionDisplayScrollPane, QUESTION_VIEW_CARD);
        add(feedbackViewPanel, FEEDBACK_VIEW_CARD);

        setupActionListeners(accentColor);
    }

    public static Component createBadge(String text, Color accentColor) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 10f));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel badgePanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(accentColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.dispose();
            }
        };
        badgePanel.setOpaque(false);
        badgePanel.add(lbl, BorderLayout.CENTER);
        badgePanel.setBorder(new EmptyBorder(3, 8, 3, 8));
        return badgePanel;
    }

    private JScrollPane createQuestionDisplayScrollPane(Color accentColor) {
        JPanel questionDisplayPanel = new JPanel();
        questionDisplayPanel.setLayout(new BoxLayout(questionDisplayPanel, BoxLayout.Y_AXIS));
        questionDisplayPanel.setOpaque(false);
        questionDisplayPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel titleLabel = new JLabel(this.currentQuestion.getQuestionId()); 
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
        header.add(titleLabel, BorderLayout.WEST);
        JPanel tagPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        tagPanel.setOpaque(false);
        tagPanel.add(createBadge(this.currentQuestion.getSkill(), accentColor));
        
        header.add(tagPanel, BorderLayout.EAST);
        questionDisplayPanel.add(header);
        questionDisplayPanel.add(Box.createVerticalStrut(15));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));
        content.setOpaque(false);
        content.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (this.currentQuestion.getImagePath() != null && !this.currentQuestion.getImagePath().isEmpty()) {
            String rawImagePath = currentQuestion.getImagePath();
            String imageResourcePath = rawImagePath;
            if (rawImagePath.startsWith("app/src/main/resources")) {
                imageResourcePath = rawImagePath.substring("app/src/main/resources".length());
            }
            if (!imageResourcePath.startsWith("/")) {
                imageResourcePath = "/" + imageResourcePath;
            }
            
            URL imageURL = getClass().getResource(imageResourcePath);

            if (imageURL != null) {
                JLabel imgLabel = new JLabel();
                imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
                Dimension imgDim = new Dimension(180, 180);
                imgLabel.setPreferredSize(imgDim);
                imgLabel.setMinimumSize(imgDim);
                imgLabel.setMaximumSize(imgDim);

                ImageIcon originalIcon = new ImageIcon(imageURL);
                Image originalImage = originalIcon.getImage();
                
                int panelWidth = imgDim.width;
                int panelHeight = imgDim.height;
                int imageWidth = originalImage.getWidth(null);
                int imageHeight = originalImage.getHeight(null);

                double aspectRatio = (double) imageWidth / imageHeight;
                int newWidth = panelWidth;
                int newHeight = (int) (panelWidth / aspectRatio);

                if (newHeight > panelHeight) {
                    newHeight = panelHeight;
                    newWidth = (int) (panelHeight * aspectRatio);
                }
                
                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                imgLabel.setIcon(new ImageIcon(scaledImage));
                content.add(imgLabel);
                content.add(Box.createHorizontalStrut(20));
            }
        }

        JTextArea qText = new JTextArea(this.currentQuestion.getQuestion());
        qText.setEditable(false);
        qText.setOpaque(false);
        qText.setLineWrap(true);
        qText.setWrapStyleWord(true);
        qText.setFont(qText.getFont().deriveFont(15f));
        qText.setPreferredSize(new Dimension(450, 100));
        content.add(qText);
        questionDisplayPanel.add(content);
        questionDisplayPanel.add(Box.createVerticalStrut(25));

        answersPanel = new JPanel();
        answersPanel.setLayout(new BoxLayout(answersPanel, BoxLayout.Y_AXIS));
        answersPanel.setOpaque(false);
        answersPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        answerGroup = new ButtonGroup();
        final Border normalAnswerBorder = new RoundedBorder(accentColor, 15, 1);
        final Border selectedAnswerBorder = new RoundedBorder(accentColor, 15, 3);
        final Color normalAnswerPanelBg = new Color(0xFFF0F0);
        final Color selectedAnswerPanelBg = new Color(0xE6F0FA);
        System.out.println("Answer choices: " + this.currentQuestion.getAnswerChoices());
        System.out.println(this.currentQuestion);
        for (String choice : this.currentQuestion.getAnswerChoices()) { 
            JRadioButton rb = new JRadioButton("<html><div style='width: 550px;'>" + choice + "</div></html>");
            rb.setForeground(Color.BLACK);
            rb.setActionCommand(choice);

            HoverAnimatedRoundedPanel rbPanel = new HoverAnimatedRoundedPanel(new BorderLayout(), 15, normalAnswerPanelBg);
            rbPanel.setBorder(normalAnswerBorder);
            rbPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
            
            rb.setHorizontalAlignment(SwingConstants.LEFT);
            rb.setOpaque(false);
            rb.setBackground(new Color(0,0,0,0));
            rb.setBorder(new EmptyBorder(10,15,10,15));
            rbPanel.add(rb, BorderLayout.CENTER);
            
            rb.setFont(rb.getFont().deriveFont(15f));
            answerGroup.add(rb);
            answersPanel.add(rbPanel);
            answersPanel.add(Box.createVerticalStrut(10)); 

            rb.addItemListener(e -> {
                for (Component comp : answersPanel.getComponents()) {
                    if (comp instanceof HoverAnimatedRoundedPanel) {
                        HoverAnimatedRoundedPanel panelToUpdate = (HoverAnimatedRoundedPanel) comp;
                        JRadioButton radioButtonInPanel = (JRadioButton) ((BorderLayout)panelToUpdate.getLayout()).getLayoutComponent(BorderLayout.CENTER);
                        if (radioButtonInPanel != null) {
                            if (radioButtonInPanel.isSelected()) {
                                panelToUpdate.setBorder(selectedAnswerBorder);
                                panelToUpdate.animateBackgroundTo(selectedAnswerPanelBg);
                            } else {
                                panelToUpdate.setBorder(normalAnswerBorder);
                                panelToUpdate.animateBackgroundTo(normalAnswerPanelBg);
                            }
                        }
                    }
                }
            });
        }
        questionDisplayPanel.add(answersPanel);
        questionDisplayPanel.add(Box.createVerticalStrut(25));

        JPanel actionsOuterPanel = new JPanel(new BorderLayout(10, 0));
        actionsOuterPanel.setOpaque(false);
        actionsOuterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        hintButton = new RoundedButton("Show Hint", 15);
        styleButton(hintButton, false, accentColor);
        actionsOuterPanel.add(hintButton, BorderLayout.WEST);
        checkAnswerButton = new RoundedButton("Check Answer", 15);
        styleButton(checkAnswerButton, true, accentColor);
        actionsOuterPanel.add(checkAnswerButton, BorderLayout.EAST);
        questionDisplayPanel.add(actionsOuterPanel);

        JPanel dyn = new JPanel();
        dyn.setLayout(new BoxLayout(dyn, BoxLayout.Y_AXIS));
        dyn.setOpaque(false);
        dyn.setBorder(new EmptyBorder(10, 0, 0, 0));
        hintLabel = createInfoLabel(accentColor);
        dyn.add(hintLabel);
        questionDisplayPanel.add(dyn);

        JScrollPane scrollPane = new JScrollPane(questionDisplayPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        return scrollPane;
    }

    public static JLabel createInfoLabel(Color accentColor) {
        JLabel lbl = new JLabel();
        lbl.setOpaque(true);
        lbl.setBackground(new Color(0xFFF4F4));
        lbl.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, accentColor));
        lbl.setVisible(false);
        lbl.setFont(lbl.getFont().deriveFont(12f));
        return lbl;
    }

    public static void styleButton(JButton button, boolean primary, Color accentColor) {
        if (!(button instanceof RoundedButton)) {
            button.setFont(button.getFont().deriveFont(Font.BOLD, 12f));
            EmptyBorder padding = new EmptyBorder(10, 25, 10, 25);
            button.setBorder(padding);
            if (primary) {
                button.setBackground(accentColor);
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(Color.WHITE);
                button.setForeground(accentColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(accentColor, 1),
                    padding));
            }
            return;
        }

        RoundedButton btn = (RoundedButton) button;
        btn.setFont(btn.getFont().deriveFont(Font.BOLD, 12f));
        EmptyBorder padding = new EmptyBorder(10, 25, 10, 25);

        if (primary) {
            Color primaryBg = accentColor;
            Color primaryHoverBg = accentColor.darker();
            btn.setOriginalBackgroundColor(primaryBg);
            btn.setHoverBackgroundColor(primaryHoverBg);
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(primaryBg, 15, 0),
                padding
            ));
        } else {
            Color secondaryBg = Color.WHITE;
            Color secondaryHoverBg = new Color(0xE0E0E0);
            btn.setOriginalBackgroundColor(secondaryBg);
            btn.setHoverBackgroundColor(secondaryHoverBg);
            btn.setForeground(accentColor);
            btn.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(accentColor, 15, 1),
                padding
            ));
        }
    }

    private JPanel createFeedbackViewPanel(Color accentColor) {
        JPanel feedbackViewPanel = new JPanel(new BorderLayout(0, 10));
        feedbackViewPanel.setOpaque(false);
        feedbackViewPanel.setBorder(new EmptyBorder(20,20,20,20));

        feedbackStatusIconLabel = new JLabel("", SwingConstants.CENTER);
        feedbackStatusIconLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 120));
        feedbackStatusIconLabel.setForeground(Color.WHITE);
        feedbackViewPanel.add(feedbackStatusIconLabel, BorderLayout.CENTER);

        JPanel feedbackActionsAndExplanationPanel = new JPanel();
        feedbackActionsAndExplanationPanel.setLayout(new BoxLayout(feedbackActionsAndExplanationPanel, BoxLayout.Y_AXIS));
        feedbackActionsAndExplanationPanel.setOpaque(false);

        feedbackQuestionMarkLabel = new JLabel("\u2753 Show Explanation");
        feedbackQuestionMarkLabel.setFont(feedbackQuestionMarkLabel.getFont().deriveFont(Font.BOLD, 16f));
        feedbackQuestionMarkLabel.setForeground(Color.WHITE);
        feedbackQuestionMarkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        feedbackQuestionMarkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        feedbackExplanationLabel = new JLabel();
        feedbackExplanationLabel.setFont(feedbackExplanationLabel.getFont().deriveFont(13f));
        feedbackExplanationLabel.setBackground(new Color(0,0,0,0));
        feedbackExplanationLabel.setOpaque(false);
        feedbackExplanationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        feedbackExplanationLabel.setHorizontalAlignment(SwingConstants.LEFT);

        feedbackActionsAndExplanationPanel.add(feedbackQuestionMarkLabel);
        feedbackActionsAndExplanationPanel.add(Box.createVerticalStrut(5));
        feedbackActionsAndExplanationPanel.add(feedbackExplanationLabel);

        retryButton = new RoundedButton("Retry", 15);
        retryButton.setBackground(new Color(0x721C24));
        retryButton.setForeground(Color.WHITE);
        retryButton.setFont(retryButton.getFont().deriveFont(Font.BOLD, 12f));
        EmptyBorder retryPadding = new EmptyBorder(8, 20, 8, 20);
        retryButton.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(new Color(0x721C24), 15, 0),
            retryPadding
        ));
        retryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        retryButton.setVisible(false);
        feedbackActionsAndExplanationPanel.add(Box.createVerticalStrut(10));
        feedbackActionsAndExplanationPanel.add(retryButton);
        
        feedbackViewPanel.add(feedbackActionsAndExplanationPanel, BorderLayout.SOUTH);
        return feedbackViewPanel;
    }

    private void setupActionListeners(Color accentColor) { 
        hintButton.addActionListener(e -> questionHandler.handleHintToggle(this, currentQuestion));

        checkAnswerButton.addActionListener(e -> {
            ButtonModel selection = answerGroup.getSelection();
            String selectedAnswerActionCommand = (selection != null) ? selection.getActionCommand() : null;
            questionHandler.handleCheckAnswer(this, currentQuestion, selectedAnswerActionCommand);
        });

        feedbackQuestionMarkLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                questionHandler.handleExplanationToggle(QuestionCard.this);
            }
        });

        retryButton.addActionListener(e -> questionHandler.handleRetry(QuestionCard.this));
    }

    public void displayHintText(String text) {
        hintLabel.setText("<html><div style='width:680px;'>Hint: " + text.replace("\n", "<br>") + "</div></html>");
    }

    public void setHintButtonText(String text) {
        hintButton.setText(text);
    }

    public void setHintLabelVisible(boolean visible) {
        hintLabel.setVisible(visible);
    }

    public void triggerShakeAnimation() {
        AnimationUtils.shakeComponent(this);
    }

    public void showDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void switchToFeedbackView(boolean isCorrect, String explanationText) {
        if (isCorrect) {
            this.animateBackgroundTo(new Color(0xD4EDDA));
            feedbackStatusIconLabel.setText("\u2705");
            Color correctColor = new Color(0x155724);
            feedbackQuestionMarkLabel.setForeground(correctColor);
            feedbackExplanationLabel.setForeground(correctColor);
            feedbackExplanationLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, correctColor),
                new EmptyBorder(5, 10, 5, 5)
            ));
            retryButton.setVisible(false);
        } else {
            this.animateBackgroundTo(new Color(0xF8D7DA), () -> {
                AnimationUtils.shakeComponent(this);
            });
            feedbackStatusIconLabel.setText("\u274C");
            Color incorrectColor = new Color(0x721C24);
            feedbackQuestionMarkLabel.setForeground(incorrectColor);
            feedbackExplanationLabel.setForeground(incorrectColor);
            feedbackExplanationLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, incorrectColor),
                new EmptyBorder(5, 10, 5, 5)
            ));
            retryButton.setVisible(true);
        }
        
        feedbackExplanationLabel.setText("<html><body style='text-align:left;'>" + explanationText.replace("\n", "<br>") + "</body></html>");
        mainCardLayout.show(this.mainCardPanel, FEEDBACK_VIEW_CARD); 
        revalidate();
        repaint();
    }

    public void setFeedbackQuestionMarkLabelText(String text) {
        feedbackQuestionMarkLabel.setText(text);
    }

    public void setFeedbackExplanationLabelVisible(boolean visible) {
        feedbackExplanationLabel.setVisible(visible);
    }

    public void switchToQuestionViewAndReset() {
        mainCardLayout.show(this.mainCardPanel, QUESTION_VIEW_CARD); 
        this.animateBackgroundTo(this.getBaseBackgroundColor());
        answerGroup.clearSelection();
        
        hintLabel.setVisible(false);
        hintButton.setText("Show Hint");

        feedbackExplanationLabel.setVisible(false);
        feedbackQuestionMarkLabel.setText("\u2753 Show Explanation");
        retryButton.setVisible(false);

        final Color normalAnswerPanelBg = new Color(0xFFF0F0);
        final Border normalAnswerBorder = new RoundedBorder(this.accentColor, 15, 1); 

        for (Component comp : answersPanel.getComponents()) {
            if (comp instanceof HoverAnimatedRoundedPanel) {
                HoverAnimatedRoundedPanel rbPanel = (HoverAnimatedRoundedPanel) comp; 
                Component centerComp = ((BorderLayout)rbPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
                if (centerComp instanceof JRadioButton) {
                    JRadioButton rb = (JRadioButton) centerComp;
                    rb.setEnabled(true);
                    rbPanel.animateBackgroundTo(normalAnswerPanelBg); 
                    rbPanel.setBorder(normalAnswerBorder);
                    rb.setForeground(Color.BLACK);
                }
            }
        }
        revalidate();
        repaint();
    }
}

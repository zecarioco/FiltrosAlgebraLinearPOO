package view;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;
import model.ExtFilters;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;

public class Interface extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblImgOriginal;
    public JLabel lblImageFilter;
    private JSpinner spinnerImageIntensity;
    private JSpinner spinnerGaussianIntensity;
    private BufferedImage BuffOriginal;
    JTextPane txtpn;
    private String originalPath;
    private BufferedImage BuffFilter;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    Interface frame = new Interface();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Interface() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\estev\\OneDrive\\Desktop\\Material UNIR\\Programação Orientada a Objetos\\ProjetoPOOAlgebraLinear\\src\\images\\inputs\\imgprcssng.png"));
        setTitle("Processamento de Imagens usando Convolução e Manipulação Matricial");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1524, 789);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(218, 165, 32));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("icone");
        lblNewLabel.setBounds(20, 50, 70, 70);
        contentPane.add(lblNewLabel);
        lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        lblNewLabel.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\estev\\OneDrive\\Desktop\\Material UNIR\\Programação Orientada a Objetos\\ProjetoPOOAlgebraLinear\\src\\images\\inputs\\imgprcssng.png").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT)));
        
        JPanel panel_2 = new JPanel();
        panel_2.setBackground(new Color(255, 255, 204));
        panel_2.setBounds(399, 570, 1101, 174);
        contentPane.add(panel_2);
        GridBagLayout gbl_panel_2 = new GridBagLayout();
        gbl_panel_2.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0, 0};
        gbl_panel_2.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_panel_2.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
        panel_2.setLayout(gbl_panel_2);
        
        ArrayList<JButton> buttons = new ArrayList<JButton>();
        JButton InputBttn = new JButton("Carregar Imagem");
        buttons.add(InputBttn);
        JButton GrayscaleBttn = new JButton("Grayscale");
        buttons.add(GrayscaleBttn);
        JButton HighlightBttn = new JButton("Realce");
        buttons.add(HighlightBttn);
        JButton VerticalEdgeButton = new JButton("Detecção de bordas verticais");
        buttons.add(VerticalEdgeButton);
        JButton HorizontalEdgeButton = new JButton("Detecção de bordas horizontais");
        buttons.add(HorizontalEdgeButton);
        JButton EdgeDetectionButton = new JButton("Detecção de bordas");
        buttons.add(EdgeDetectionButton);
        JButton MedianButton = new JButton("Filtro de mediana");
        buttons.add(MedianButton);
        JButton BlendButton = new JButton("Misturar imagens");
        buttons.add(BlendButton);
        JButton GaussianButton = new JButton("Borro de Gauss");
        buttons.add(GaussianButton);
        JButton SaveButton = new JButton("Salvar Imagem");
        buttons.add(SaveButton);

        for (int i = 1; i <= 10; i++) {
            JButton button = buttons.get(i-1);
            GridBagConstraints gbc_button = new GridBagConstraints();
            gbc_button.insets = new Insets(10, 10, 10, 10);
            gbc_button.gridx = (i - 1) % 4;
            gbc_button.gridy = (i - 1) / 4;
            gbc_button.fill = GridBagConstraints.BOTH;
            panel_2.add(button, gbc_button);
        }

        JLabel lblGaussianIntensity = new JLabel("Intensidade de Gauss");
        GridBagConstraints gbc_lblGaussianIntensity = new GridBagConstraints();
        gbc_lblGaussianIntensity.insets = new Insets(10, 10, 10, 10);
        gbc_lblGaussianIntensity.gridx = 0;
        gbc_lblGaussianIntensity.gridy = 3;
        panel_2.add(lblGaussianIntensity, gbc_lblGaussianIntensity);

        spinnerGaussianIntensity = new JSpinner();
        spinnerGaussianIntensity.setModel(new SpinnerNumberModel(1, 1, 30, 1));
        GridBagConstraints gbc_spinnerGaussianIntensity = new GridBagConstraints();
        gbc_spinnerGaussianIntensity.insets = new Insets(10, 10, 10, 10);
        gbc_spinnerGaussianIntensity.gridx = 1;
        gbc_spinnerGaussianIntensity.gridy = 3;
        panel_2.add(spinnerGaussianIntensity, gbc_spinnerGaussianIntensity);

        JLabel lblImageIntensity = new JLabel("Intensidade da primeira imagem");
        GridBagConstraints gbc_lblImageIntensity = new GridBagConstraints();
        gbc_lblImageIntensity.insets = new Insets(10, 10, 10, 10);
        gbc_lblImageIntensity.gridx = 2;
        gbc_lblImageIntensity.gridy = 3;
        panel_2.add(lblImageIntensity, gbc_lblImageIntensity);

        spinnerImageIntensity = new JSpinner();
        spinnerImageIntensity.setModel(new SpinnerNumberModel(1, 1, 100, 1));
        GridBagConstraints gbc_spinnerImageIntensity = new GridBagConstraints();
        gbc_spinnerImageIntensity.insets = new Insets(10, 10, 10, 10);
        gbc_spinnerImageIntensity.gridx = 3;
        gbc_spinnerImageIntensity.gridy = 3;
        panel_2.add(spinnerImageIntensity, gbc_spinnerImageIntensity);
        
        InputBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                carregarImagem();
            }
        });
        GrayscaleBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (imagemCarregada()) {
                    BufferedImage Img = ExtFilters.Filters.Grayscale(BuffOriginal);
                    Img = ExtFilters.Filters.resizeImage(Img, lblImageFilter.getWidth(), lblImageFilter.getHeight());
                    BuffFilter = copyImage(Img);
                    setImageIcon(lblImageFilter, Img);
                    txtpn.setText("      O filtro grayscale utiliza-se da manipulação de valores RGB como variáveis em uma equação linear," +
                    " percorrendo a matriz da imagem e assim variando os valores RGB, depois do processamento de um pixel, ele é subtituído"+
                    " pelo resultado da equação. Seus coeficientes são especificos pela sensibilidade ocular humana à cada cor. A equacão é" +
                    " Vp = 0.2126r + 0.7152g + 0.0722b");
                }
            }
        });
        HighlightBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (imagemCarregada()) {
                    BufferedImage Img = ExtFilters.Filters.Highlight(BuffOriginal);
                    Img = ExtFilters.Filters.resizeImage(Img, lblImageFilter.getWidth(), lblImageFilter.getHeight());
                    BuffFilter = copyImage(Img);
                    setImageIcon(lblImageFilter, Img);
                    txtpn.setText("     O filtro de realce implementa a convolução com um filtro kernel 3x3 [[0 -1 0][-1 =~5 -1][0 -1 0]],"+
                    " e a soma dos coeficientes =~1, o que parcialmente preserva o pixel original mas realça as diferenças entre ele em relação" +
                    " aos pixels adjacentes.");
                }
            }
        });
        VerticalEdgeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (imagemCarregada()) {
                    BufferedImage Img = ExtFilters.Filters.VerticalEdgeDetection(BuffOriginal);
                    Img = ExtFilters.Filters.resizeImage(Img, lblImageFilter.getWidth(), lblImageFilter.getHeight());
                    BuffFilter = copyImage(Img);
                    setImageIcon(lblImageFilter, Img);
                    txtpn.setText("     O filtro de detecção de bordas verticais aplica um filtro kernel 3x3 [[-1 0 1][-2 0 2][-1 0 1]]," +
                    " como a primeira e a última coluna se cancelam quando os valores dos pixels nas posições são iguais, quando há homogeneidade" +
                    " na área os valores se cancelam e a área fica preta. Por outro lado, quando há variação, ela apresenta um pico e" +
                    " cria o contraste representando uma borda.");
                }
            }
        });
        HorizontalEdgeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (imagemCarregada()) {
                    BufferedImage Img = ExtFilters.Filters.HorizontalEdgeDetection(BuffOriginal);
                    Img = ExtFilters.Filters.resizeImage(Img, lblImageFilter.getWidth(), lblImageFilter.getHeight());
                    BuffFilter = copyImage(Img);
                    setImageIcon(lblImageFilter, Img);
                    txtpn.setText("     O filtro de detecção de bordas horizontais aplica um filtro kernel 3x3 [[-1 -2 -1][0 0 0][1 2 1]]," +
                    " como a primeira e a última linha se cancelam quando os valores dos pixels nas posições são iguais, quando há homogeneidade" +
                    " na área os valores se cancelam e a área fica preta. Por outro lado, quando há variação, ela apresenta um pico e" +
                    " cria o contraste representando uma borda.");
                }
            }
        });
        EdgeDetectionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (imagemCarregada()) {
                    BufferedImage Img = ExtFilters.Filters.EdgeDetection(BuffOriginal);
                    Img = ExtFilters.Filters.resizeImage(Img, lblImageFilter.getWidth(), lblImageFilter.getHeight());
                    BuffFilter = copyImage(Img);
                    setImageIcon(lblImageFilter, Img);
                    txtpn.setText("     O filtro de detecção de bordas gerais aplica um filtro kernel 3x3 [[-1 -1 -1][-1 8 -1][-1 -1 -1]]," +
                    " como os coeficientes da matriz se cancelam, quando há homogeneidade na área de atuação do kernel os coeficientes" +
                    " se cancelam e a área fica preta. Por outro lado, quando há variação, ela apresenta um pico e" +
                    " cria o contraste representando uma borda.");
                }
            }
        });
        MedianButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (imagemCarregada()) {
                    BufferedImage Img = ExtFilters.Filters.Median(BuffOriginal);
                    Img = ExtFilters.Filters.resizeImage(Img, lblImageFilter.getWidth(), lblImageFilter.getHeight());
                    BuffFilter = copyImage(Img);
                    setImageIcon(lblImageFilter, Img);
                    txtpn.setText("     O filtro de mediana substitui o pixel da imagem correspondente à posição central do kernel pela" +
                    " mediana dos valores de RGB, limpando pixels com valores irregulares e suavizando a imagem.");
                }
            }
        });
        BlendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (imagemCarregada()) {
                    double intensity = ((int)spinnerImageIntensity.getValue()) / 100.0;
                    JFileChooser fileChooser = new JFileChooser();
                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        try {
                            BufferedImage secondImage = ImageIO.read(selectedFile);
                            BufferedImage Img = ExtFilters.Filters.Blend(BuffOriginal, secondImage, intensity);
                            Img = ExtFilters.Filters.resizeImage(Img, lblImageFilter.getWidth(), lblImageFilter.getHeight());
                            BuffFilter = copyImage(Img);
                            setImageIcon(lblImageFilter, Img);
                            txtpn.setText("     O filtro de mistura de imagens simplesmente opera uma soma matricial entre duas imagens" +
                            " após aplicar pesos nos valores RGB (um pra cada imagem, que juntos somam 1.0 para a normalização).");;
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        GaussianButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (imagemCarregada()) {
                    int intensity = ((int) spinnerGaussianIntensity.getValue());
                    BufferedImage Img = ExtFilters.Filters.GaussianBlur(BuffOriginal, intensity);
                    Img = ExtFilters.Filters.resizeImage(Img, lblImageFilter.getWidth(), lblImageFilter.getHeight());
                    BuffFilter = copyImage(Img);
                    setImageIcon(lblImageFilter, Img);
                    txtpn.setText("     O filtro de borro gaussiano aplica um filtro kernel 3x3 com distribuição gaussiana" +
                    " onde todos os coeficientes somam 1.0. Uma distribuição normal/gaussiana aumenta seu valor de acordo" +
                    " com sua posição relativa ao centro, logo, o pixel sendo operado tem o maior peso, mas ainda é influenciado" +
                    " pelos adjacentes.");
                }
            }
        });
        SaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (imagemCarregada()) {
                    try {
                        File f = new File(originalPath);
                        ImageIO.write(BuffFilter, "png", f);
                        BuffOriginal = ImageIO.read(f);
                        setImageIcon(lblImgOriginal, BuffOriginal);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        
        JTextArea txtrProcessamento = new JTextArea();
        txtrProcessamento.setBackground(Color.ORANGE);
        txtrProcessamento.setForeground(new Color(210, 105, 30));
        txtrProcessamento.setWrapStyleWord(true);
        txtrProcessamento.setLineWrap(true);
        txtrProcessamento.setText("PROCESSAMENTO DE IMAGENS USANDO CONVOLUÇÃO E MANIPULAÇÃO MATRICIAL");
        txtrProcessamento.setFont(new Font("Times New Roman", Font.BOLD, 16));
        txtrProcessamento.setBounds(93, 42, 265, 67);
        contentPane.add(txtrProcessamento);
        
        JTextArea txtrJosEstevoBruna = new JTextArea();
        txtrJosEstevoBruna.setBackground(Color.ORANGE);
        txtrJosEstevoBruna.setWrapStyleWord(true);
        txtrJosEstevoBruna.setLineWrap(true);
        txtrJosEstevoBruna.setText("José Estevão, Bruna Caculakis, Pedro Sampaio");
        txtrJosEstevoBruna.setFont(new Font("Arial", Font.BOLD, 11));
        txtrJosEstevoBruna.setBounds(93, 119, 274, 17);
        contentPane.add(txtrJosEstevoBruna);
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.YELLOW);
        panel.setBounds(20, 146, 354, 563);
        contentPane.add(panel);
        panel.setLayout(new BorderLayout());
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(205, 133, 63));
        panel.add(panel_1, BorderLayout.EAST);
        
        JPanel panel_3 = new JPanel();
        panel_3.setBackground(new Color(205, 133, 63));
        panel.add(panel_3, BorderLayout.NORTH);
        
        JPanel panel_4 = new JPanel();
        panel_4.setBackground(new Color(205, 133, 63));
        panel.add(panel_4, BorderLayout.WEST);
        
        JPanel panel_5 = new JPanel();
        panel_5.setBackground(new Color(205, 133, 63));
        panel.add(panel_5, BorderLayout.SOUTH);
        
        txtpn = new JTextPane();
        txtpn.setText("                         Saida de Texto");
        txtpn.setFont(new Font("Times New Roman", Font.ITALIC, 16));
        txtpn.setBackground(Color.ORANGE);
        panel.add(txtpn, BorderLayout.CENTER);
        txtpn.setEditable(false);
        
        JPanel PainelPrincipal = new JPanel();
        PainelPrincipal.setBackground(Color.ORANGE);
        PainelPrincipal.setBounds(10, 25, 374, 692);
        contentPane.add(PainelPrincipal);
        PainelPrincipal.setLayout(new BorderLayout(0, 0));
        
        JPanel panel_6 = new JPanel();
        panel_6.setBackground(new Color(205, 133, 63));
        PainelPrincipal.add(panel_6, BorderLayout.WEST);
        panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JPanel panel_7 = new JPanel();
        panel_7.setBackground(new Color(205, 133, 63));
        PainelPrincipal.add(panel_7, BorderLayout.NORTH);
        
        JPanel panel_8 = new JPanel();
        panel_8.setBackground(new Color(205, 133, 63));
        PainelPrincipal.add(panel_8, BorderLayout.EAST);
        
        JPanel panel_9 = new JPanel();
        panel_9.setBackground(new Color(205, 133, 63));
        PainelPrincipal.add(panel_9, BorderLayout.SOUTH);
        
        lblImgOriginal = new JLabel("");
        lblImgOriginal.setOpaque(true);
        lblImgOriginal.setBackground(new Color(255, 255, 204));
        lblImgOriginal.setBounds(399, 25, 541, 535);
        contentPane.add(lblImgOriginal);
        
        lblImageFilter = new JLabel("");
        lblImageFilter.setOpaque(true);
        lblImageFilter.setBackground(new Color(255, 255, 204));
        lblImageFilter.setBounds(959, 25, 541, 535);
        contentPane.add(lblImageFilter);
        
        JTextPane txtpnInputDeveSer = new JTextPane();
        txtpnInputDeveSer.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        txtpnInputDeveSer.setText("INPUT DEVE SER JPG/PNG/JPEG");
        txtpnInputDeveSer.setBackground(Color.ORANGE);
        txtpnInputDeveSer.setBounds(10, 725, 374, 19);
        contentPane.add(txtpnInputDeveSer);
    }

    public void setImageIcon(JLabel label, BufferedImage image) {
        label.setIcon(new ImageIcon(image));
    }

    private void carregarImagem() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            originalPath = selectedFile.getPath();
            try {
                BufferedImage image = ImageIO.read(selectedFile);
                BuffOriginal = copyImage(image);
                image = ExtFilters.Filters.resizeImage(image, this.lblImgOriginal.getWidth(), this.lblImgOriginal.getHeight());
                setImageIcon(this.lblImgOriginal, image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean imagemCarregada() {
        return lblImgOriginal.getIcon() != null;
    }

    public static BufferedImage copyImage(BufferedImage source){
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }
}
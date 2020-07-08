import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Window {
    private JPanel mainPanel;
    private JTable pointsTable;
    private JButton mySortBut;
    private JButton javaSortBut;
    private JButton removeColBut;
    private JButton addColBut;
    private JButton readBut;
    private JButton fill10ElementsBut;
    private JTextField numberOfFirstElement;
    private JTable resultTable;

    private Window(){
        DefaultTableModel inputModel = new DefaultTableModel();
        pointsTable.setModel(inputModel);
        inputModel.setDataVector(new Object[2][10], getHeaders(10));
        DefaultTableModel outputModel = new DefaultTableModel();
        resultTable.setModel(outputModel);
        outputModel.setDataVector(new Object[2][10], getHeaders(10));

        readBut.addActionListener(actionEvent -> {
            JFileChooser jfc = new JFileChooser();
            int ret = jfc.showDialog(null, "Загрузить точки");
            File file = null;
            if (ret == JFileChooser.APPROVE_OPTION) {
                file = jfc.getSelectedFile();
            }
            try {
                String[][] strings = takeArray2FromFile(file);
                Integer[] headers = getHeaders(strings[0].length);
                inputModel.setDataVector(strings, headers);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        mySortBut.addActionListener(actionEvent -> {
            Point[] points = new Point[0];
            try {
                points = Sorting.takeFirstElements(Integer.parseInt(numberOfFirstElement.getText())
                        , new PointComporator(), getPointsFromTable(inputModel));
            } catch (Exception e) {
                numberOfFirstElement.setText("Please, write N more than 0");
            }
            String[][] result = new String[2][points.length];
            for (int i = 0; i < result[0].length; i++) {
                result[0][i] = String.valueOf(points[i].getX());
                result[1][i] = String.valueOf(points[i].getY());
            }
            outputModel.setDataVector(result, getHeaders(result[0].length));
        });

        removeColBut.addActionListener(actionEvent -> {
            Object[][] data = getTableData(inputModel);
            Integer[] headers = getHeaders(inputModel.getColumnCount() - 1);
            inputModel.setDataVector(data, headers);
        });

        addColBut.addActionListener(actionEvent -> {
            Object[][] data = getTableData(inputModel);
            Integer[] headers = getHeaders(inputModel.getColumnCount() + 1);
            inputModel.setDataVector(data, headers);
        });

        fill10ElementsBut.addActionListener(actionEvent -> setRandomTooTable(10, inputModel));

        javaSortBut.addActionListener(actionEvent -> {
            Point[] points = getPointsFromTable(inputModel);
            try {
                Integer.parseInt(numberOfFirstElement.getText());
            } catch (NumberFormatException e) {
                numberOfFirstElement.setText("Please, write N more than 0");
            }
            int n = Integer.parseInt(numberOfFirstElement.getText());
            if (n > 0) {
                Arrays.sort(points, new PointComporator());
                String[][] result = new String[2][n];
                for (int i = 0; i < result[0].length; i++) {
                    result[0][i] = String.valueOf(points[i].getX());
                    result[1][i] = String.valueOf(points[i].getY());
                }
                outputModel.setDataVector(result, getHeaders(result[0].length));
            }
            else numberOfFirstElement.setText("Please, write N more than 0");
        });
    }

    private Point[] getPointsFromTable(DefaultTableModel model) {
        Point[] points = new Point[model.getColumnCount()];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(Double.parseDouble(String.valueOf(model.getValueAt(0, i))),
                                    Double.parseDouble(String.valueOf(model.getValueAt(1, i))));
        }
        return points;
    }

    private String[][] takeArray2FromFile(File file) throws FileNotFoundException {
        Scanner scn = new Scanner(file);
        List<String[]> list = new ArrayList<>();
        while (scn.hasNextLine()) {
            String[] s = scn.nextLine().split(" ");
            list.add(s);
        }
        return list.toArray(new String[0][]);
    }

    private Object[][] getTableData(DefaultTableModel model) {
        Object[][] data = new Object[model.getRowCount()][model.getColumnCount()];
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                data[i][j] = model.getValueAt(i, j);
            }
        }
        return data;
    }

    private Integer[] getHeaders(int length) {
        Integer[] headers = new Integer[length];
        for (int i = 0; i < length; i++) {
            headers[i] = i + 1;
        }
        return headers;
    }

    private void setRandomTooTable(int n, DefaultTableModel model) {
        Double[][] numbers = new Double[2][n];
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                numbers[i][j] = Math.random()*200 - 100;
            }
        }
        model.setDataVector(numbers, getHeaders(n));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Heap sort");
        frame.setContentPane(new Window().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit tkt = Toolkit.getDefaultToolkit();
        Dimension dim = tkt.getScreenSize();
        frame.setBounds(dim.width/2 - 250, dim.height/2 - 150, dim.width / 2, 300);
        frame.setVisible(true);
    }
}

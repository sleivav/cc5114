package neuronExperiment;

import classificationLine.ClassificationLine;
import org.knowm.xchart.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class linePlot {
    public linePlot(ClassificationLine classLine, List<Double> pointsX, List<Double> pointsY, List<Double> outputs, Double m, Double n) {

        XYChart chart = new XYChartBuilder().width(800).height(600).title("Puntitos").xAxisTitle("X").yAxisTitle("Y").build();

        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAxisTitlesVisible(false);
        chart.getStyler().setXAxisDecimalPattern("0.0000000");
        chart.getStyler().setXAxisMin(-10.0);
        chart.getStyler().setXAxisMax(10.0);
        chart.getStyler().setYAxisMin(-10.0);
        chart.getStyler().setYAxisMax(10.0);

        ArrayList upperPointsX = new ArrayList<Double>();
        ArrayList upperPointsY = new ArrayList<Double>();

        ArrayList lowerPointsX = new ArrayList<Double>();
        ArrayList lowerPointsY = new ArrayList<Double>();

        for (int i = 0; i < outputs.size(); i++) {
            if (outputs.get(i) == 0.0) {
                lowerPointsX.add(pointsX.get(i));
                lowerPointsY.add(pointsY.get(i));
            } else {
                upperPointsX.add(pointsX.get(i));
                upperPointsY.add(pointsY.get(i));
            }
        }


        XYSeries upperPoints = chart.addSeries("Upper points", upperPointsX, upperPointsY);
        XYSeries lowerPoints = chart.addSeries("Lower points", lowerPointsX, lowerPointsY);

        XYSeries linePoints = chart.addSeries("Line", new double[]{-100.0, 100.0}, new double[]{-100.0 * m + n, 100.0 * m + n});
        linePoints.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        linePoints.setLineWidth(5.0f);

        new SwingWrapper(chart).displayChart();
    }

    public linePlot(List<Double> accuracies) {
        XYChart chart = new XYChartBuilder().width(800).height(600).title("Puntitos").xAxisTitle("X").yAxisTitle("Y").build();

        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAxisTitlesVisible(false);
        chart.getStyler().setXAxisDecimalPattern("0.0000000");

        List<Integer> xPoints = IntStream.rangeClosed(0, accuracies.size() - 1)
                .boxed().collect(Collectors.toList());

        XYSeries linePoints = chart.addSeries("Line", xPoints, accuracies);
        linePoints.setLineWidth(5.0f);

        new SwingWrapper(chart).displayChart();
    }
}

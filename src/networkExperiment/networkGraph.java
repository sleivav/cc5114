package networkExperiment;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class networkGraph {
    public networkGraph(List<Double> points) {

        XYChart chart = new XYChartBuilder().width(800).height(600).title("Puntitos").xAxisTitle("X").yAxisTitle("Y").build();

        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAxisTitlesVisible(false);
        chart.getStyler().setXAxisDecimalPattern("0.0000000");

        List<Integer> xPoints = IntStream.rangeClosed(0, points.size() - 1)
                .boxed().collect(Collectors.toList());

        XYSeries linePoints = chart.addSeries("Line", xPoints, points);
        linePoints.setLineWidth(5.0f);

        new SwingWrapper(chart).displayChart();
    }
}

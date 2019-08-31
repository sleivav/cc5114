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
    public networkGraph(List<Double> points, String title, String xTitle, String yTitle) {

        XYChart chart = new XYChartBuilder().width(800).height(600).title(title).xAxisTitle(xTitle).yAxisTitle(yTitle).build();

        chart.getStyler().setLegendVisible(false);

        List<Integer> xPoints = IntStream.rangeClosed(0, points.size() - 1)
                .boxed().collect(Collectors.toList());

        XYSeries linePoints = chart.addSeries("Line", xPoints, points);
        linePoints.setLineWidth(5.0f);

        new SwingWrapper(chart).displayChart();
    }
}

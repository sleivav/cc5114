package cc5114.src.networkExperiment;

import org.knowm.xchart.*;

import java.io.IOException;
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

        //new SwingWrapper(chart).displayChart();

        try {
            BitmapEncoder.saveBitmap(chart, "./charts/" + title, BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            System.err.println("No se pudo acceder al archivo para guardar los gráficos");
        }
    }
}

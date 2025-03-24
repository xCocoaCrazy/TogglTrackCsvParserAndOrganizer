package md.rosca.utils;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xddf.usermodel.text.XDDFTextBody;
import org.apache.poi.xddf.usermodel.text.XDDFTextParagraph;
import org.apache.poi.xddf.usermodel.text.XDDFTextRun;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ChartUtil {
    /**
     * Generate pie chart
     * @param sheet sheet where to create the chart
     * @param numberOfRows number of rows for the table
     * @param numberOfColumns number of columns for the table
     * @param chartTitle title of the chart
     * @param columnOfDescriptionForChart
     * @param columnOfTimeSpentForChart
     */
    public static void createPieChart(XSSFSheet sheet, int numberOfRows, int numberOfColumns, String chartTitle, int columnOfDescriptionForChart, int columnOfTimeSpentForChart) {
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, numberOfColumns, 0, 25, 60);

        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleText(chartTitle);
        chart.setTitleOverlay(false);

        // Customizing chart title font
        XDDFTextBody titleBody = chart.getTitle().getBody();
        XDDFTextParagraph titleParagraph = titleBody.getParagraph(0);
        XDDFTextRun titleRun = titleParagraph.getTextRuns().get(0);
        titleRun.setFontSize(14d);
        titleRun.setBold(true);

        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);

        XDDFCategoryDataSource descriptions = XDDFDataSourcesFactory.fromStringCellRange(sheet,
                new CellRangeAddress(1, numberOfRows - 1, columnOfDescriptionForChart, columnOfDescriptionForChart));
        XDDFNumericalDataSource<Double> durations = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
                new CellRangeAddress(1, numberOfRows - 1, columnOfTimeSpentForChart, columnOfTimeSpentForChart));

        XDDFPieChartData dataChart = (XDDFPieChartData) chart.createData(ChartTypes.PIE, null, null);
        XDDFPieChartData.Series series = (XDDFPieChartData.Series) dataChart.addSeries(descriptions, durations);
        
        series.setTitle("");
        series.setShowLeaderLines(true);

        //Remove the separator from the chart titles
        chart
                .getCTChart()
                .getPlotArea()
                .getPieChartArray(0)
                .getSerArray(0)
                .getDLbls()
                .setSeparator(" ");

        chart.plot(dataChart);
    }
}

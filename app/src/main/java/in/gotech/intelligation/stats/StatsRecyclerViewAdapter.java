package in.gotech.intelligation.stats;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

import in.gotech.intelligation.R;

/**
 * Created by anirudh on 16/06/16.
 */
public class StatsRecyclerViewAdapter extends RecyclerView.Adapter<StatsRecyclerViewAdapter.StatsCardViewHolder> {
    private Context mContext;
    private ArrayList<TimeSeries> mSensorTimeSeriesList;

    public StatsRecyclerViewAdapter(Context context, ArrayList<TimeSeries> sensorTimeSeriesList) {
        mSensorTimeSeriesList = sensorTimeSeriesList;
        mContext = context;
    }
    @Override
    public StatsCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_card, parent, false);
        StatsCardViewHolder vh = new StatsCardViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(StatsCardViewHolder holder, int position) {
        holder.mDataset.clear();
        holder.mDataset.addSeries(mSensorTimeSeriesList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSensorTimeSeriesList.size();
    }

    public class StatsCardViewHolder extends RecyclerView.ViewHolder {
        private XYMultipleSeriesDataset mDataset;
        private XYMultipleSeriesRenderer mRenderer;
        private GraphicalView mChartView;
        private TimeSeries time_series;
        public StatsCardViewHolder(ViewGroup statsCardView) {
            super(statsCardView);
            // create dataset and renderer
            mDataset = new XYMultipleSeriesDataset();
            mRenderer = new XYMultipleSeriesRenderer();
            mRenderer.setAxisTitleTextSize(16);
            mRenderer.setChartTitleTextSize(20);
            mRenderer.setLabelsTextSize(15);
            mRenderer.setLegendTextSize(15);
            mRenderer.setPointSize(3f);

            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(Color.BLACK);
            r.setPointStyle(PointStyle.CIRCLE);
            r.setFillPoints(true);
            mRenderer.addSeriesRenderer(r);
            mRenderer.setClickEnabled(true);
            mRenderer.setSelectableBuffer(20);
            mRenderer.setPanEnabled(true);
            mRenderer.setZoomEnabled(true, true);
            mRenderer.setZoomButtonsVisible(true);

            time_series = new TimeSeries("test");

            mDataset.addSeries(time_series);

            mChartView = ChartFactory.getTimeChartView(mContext, mDataset, mRenderer,
                    "H:mm:ss");
            mChartView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                }
            });

            mChartView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
            mChartView.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
            statsCardView.addView(mChartView);
        }
    }
}

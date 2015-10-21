package info.devexchanges.parallaxheaderlistview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView headerText;
    private ListView listView;
    private View headerView;
    private View headerSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        headerView = findViewById(R.id.header_image_view);
        headerText = (TextView) findViewById(R.id.header_text);

        setListViewHeader();
        setListViewData();

        // Handle list View scroll events
        listView.setOnScrollListener(onScrollListener());
    }

    private void setListViewHeader() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View listHeader = inflater.inflate(R.layout.listview_header, null, false);
        headerSpace = listHeader.findViewById(R.id.header_space);

        listView.addHeaderView(listHeader);
    }

    private void setListViewData() {
        List<String> modelList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            modelList.add("Item " + (i+1));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_listview, R.id.item, modelList);
        listView.setAdapter(adapter);
    }

    private AbsListView.OnScrollListener onScrollListener () {
        return new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                // Check if the first item is already reached to top
                if (listView.getFirstVisiblePosition() == 0) {
                    View firstChild = listView.getChildAt(0);
                    int topY = 0;
                    if (firstChild != null) {
                        topY = firstChild.getTop();
                    }

                    int headerTopY = headerSpace.getTop();
                    headerText.setY(Math.max(0, headerTopY + topY));

                    // Set the image to scroll half of the amount that of ListView
                    headerView.setY(topY * 0.5f);
                }
            }
        };
    }
}
package vn.edu.usth.weather;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ForecastFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        view.setBackgroundColor(Color.parseColor("#20FF0000"));
        ImageView view1 = view.findViewById(R.id.icon);
        return view;
*/

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(getContext());
        textView.setText("Thursday");

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.cloud);

        linearLayout.addView(textView);
        linearLayout.addView(imageView);


        return linearLayout;
    }
}


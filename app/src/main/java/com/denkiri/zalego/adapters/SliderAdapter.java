package com.denkiri.zalego.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.denkiri.zalego.R;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

//    private TextView slideHeading, slideDescription;
//    private ImageView slide_imageView;


    public SliderAdapter(Context context) {

        this.context = context;
    }

    // img Array
    public int[] image_slide ={
            R.drawable.zalego,
            R.drawable.zalego,
            R.drawable.zalego
    };

    // heading Array
    public String[] heading_slide ={
            "Welcome To Zalego Institute",
            "Who are we",
            "Our Industrial Best Courses"
    };

    // description Array
    public String[] description_slide ={
            "Allow us to prepare you for the job market",
            "An E-learning platform where we unmask your academic potential.  And prepare job seekers for job industry by equipping them with the best skills ever. We essentially offer learning online through  live lectures, and video conferencing, discussion forums and live chats . This enables all the participants to give their views on a particular topic and then discuss them further. We also offer ready to use power point course material files that are downloadable for the benefit of all the participants.",
            "Programming Courses, Business Courses ,Data Science&Analytics,Cyber Security,Quality Assurance "
    };




    @Override
    public int getCount() {

        return heading_slide.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container,false);
        container.addView(view);

        ImageView slide_imageView = view.findViewById(R.id.imageView1);
        TextView slideHeading = view.findViewById(R.id.tvHeading);
        TextView  slideDescription = view.findViewById(R.id.tvDescription);

        slide_imageView.setImageResource(image_slide[position]);
        slideHeading.setText(heading_slide[position]);
        slideDescription.setText(description_slide[position]);

        return view;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }

//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        View view = (View) object;
//        container.removeView(view);
//    }

}

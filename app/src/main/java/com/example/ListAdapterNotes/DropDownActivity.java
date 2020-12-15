package com.example.ListAdapterNotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DropDownActivity extends AppCompatActivity {
    ArrayList<country> countries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_down);
        countries = new ArrayList<>();
        countries.add(new country(R.drawable.ic__01_united_kingdom , "united_kingdom"));
        countries.add(new country(R.drawable.ic__02_united_states , "united_states"));
        countries.add(new country(R.drawable.ic__04_germany , "germany"));
        countries.add(new country(R.drawable.ic__03_france , "france"));
        countries.add(new country(R.drawable.ic__05_china , "china"));
        countries.add(new country(android.R.color.transparent , "Select your country"));
//        String[] countries = getResources().getStringArray(R.array.countries);
        Spinner spinner = findViewById(R.id.spinner);
//        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this ,R.layout.item_spinner ,android.R.id.text1 , countries);
        CountriesAdapter countryAdapter = new CountriesAdapter();
//        adapter.setDropDownViewResource(R.layout.item_spinner_drop_down);
        spinner.setAdapter(countryAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DropDownActivity.this, countries.get(position).getCountryName(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spinner.setSelection(countries.size() - 1);
      findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (spinner.getSelectedItemPosition() == 0){
                  Toast.makeText(DropDownActivity.this, "You must select a country", Toast.LENGTH_SHORT).show();
              }else {

              }
          }
      });

//        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this ,android.R.layout.simple_spinner_item ,android.R.id.text1 , countries);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
    }
    class CountriesAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return countries.size() - 1;
        }

        @Override
        public country getItem(int position) {
            return countries.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner , parent , false);
                viewHolder = new ViewHolder();
                viewHolder.textView=convertView.findViewById(android.R.id.text1);
                viewHolder.imageView=convertView.findViewById(R.id.imageView2);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(getItem(position).getCountryName());
            viewHolder.imageView.setImageResource(getItem(position).getFlagRes());
            return convertView;

        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_drop_down , parent , false);
                viewHolder = new ViewHolder();
                viewHolder.textView=convertView.findViewById(android.R.id.text1);
                viewHolder.imageView=convertView.findViewById(R.id.imageView2);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(getItem(position).getCountryName());
            viewHolder.imageView.setImageResource(getItem(position).getFlagRes());
            return convertView;
        }

        class ViewHolder{
            TextView textView;
            ImageView imageView;
        }
    }
}
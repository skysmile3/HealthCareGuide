package org.androidtown.healthcareguide.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import org.androidtown.healthcareguide.Activity.Select_Activity;
import org.androidtown.healthcareguide.Model.DiabetesInformation;
import org.androidtown.healthcareguide.Model.User;
import org.androidtown.healthcareguide.R;

import java.util.List;

/**
 * Created by yjhyj on 2017-11-21.
 */

public class DiabetesAdapter extends BaseAdapter {
    Context context;
    List<DiabetesInformation> list;
    LayoutInflater inflater;
    User caredUser;
    public DiabetesAdapter(Context context, List<DiabetesInformation> list) {
        this.context = context;
        this.list = list;
        caredUser = Select_Activity.caredUser;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView==null){
            view = inflater.inflate(R.layout.adapter_diabetes, parent, false);
        }else{
            view = convertView;
        }

        DiabetesInformation diabetesInformation = list.get(position);

        String date = diabetesInformation.getDate();
        String time = diabetesInformation.getTime();
        String eat = diabetesInformation.getEat();
        String diabetesinfo = diabetesInformation.getDiabetesinfo();
        final String key = diabetesInformation.getKey();

        TextView itemDateTime = view.findViewById(R.id.item_datetime);
        TextView itemEat = view.findViewById(R.id.item_eat);
        TextView itemDiabetesinfo = view.findViewById(R.id.item_diabetesinfo);

        itemDateTime.setText(date + " " + time);
        itemEat.setText(eat);
        itemDiabetesinfo.setText(diabetesinfo);

        RelativeLayout layout = view.findViewById(R.id.diabetes_layout);
        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog dialog = createDialogBox(key);
                dialog.show();
                return false;
            }
        });


        return view;
    }
    private AlertDialog createDialogBox(final String key){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //builder.setTitle("내용 삭제");
        builder.setMessage("내용을 삭제하시겠습니까?");


        builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int whichButton){
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                firebaseDatabase.getReference().child("diabetes").child(caredUser.getUid()).child(key).removeValue();
                dialog.dismiss();
            }

        });


        builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int whichButton){
                dialog.dismiss();
            }

        });



        AlertDialog dialog = builder.create();

        return dialog;
    }
}

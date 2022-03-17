package com.example.fooddyapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddyapp.Models.Step;
import com.example.fooddyapp.R;

import org.w3c.dom.Text;

import java.util.List;

public class InstructionStepAdapter extends RecyclerView.Adapter<InstructionStepViewHolder>{

    Context context;
    List<Step> list;

    public InstructionStepAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public InstructionStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionStepViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_steps,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionStepViewHolder holder, int position) {

        holder.textView_instructions_step_number.setText(String.valueOf(list.get(position).number));
        holder.textView_instructions_step_title.setText(String.valueOf(list.get(position).step));

        holder.recycler_instructions_ingredient.setHasFixedSize(true);
        holder.recycler_instructions_ingredient.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        InstructionsIngredientsAdapter instructionsIngredientsAdapter =
                new InstructionsIngredientsAdapter(context,list.get(position).ingredients);
        holder.recycler_instructions_ingredient.setAdapter(instructionsIngredientsAdapter);

        holder.recycler_instructions_equipment.setHasFixedSize(true);
        holder.recycler_instructions_equipment.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        InstructionsEquipmentsAdapter instructionsEquipmentsAdapter =
                new InstructionsEquipmentsAdapter(context,list.get(position).equipment);
        holder.recycler_instructions_equipment.setAdapter(instructionsEquipmentsAdapter);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class InstructionStepViewHolder extends RecyclerView.ViewHolder{

    TextView textView_instructions_step_number,textView_instructions_step_title;
    RecyclerView recycler_instructions_ingredient,recycler_instructions_equipment;
    public InstructionStepViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_instructions_step_number = itemView.findViewById(R.id.textView_instructions_step_number);
        textView_instructions_step_title = itemView.findViewById(R.id.textView_instructions_step_title);
        recycler_instructions_ingredient = itemView.findViewById(R.id.recycler_instructions_ingredient);
        recycler_instructions_equipment = itemView.findViewById(R.id.recycler_instructions_equipment);


    }
}
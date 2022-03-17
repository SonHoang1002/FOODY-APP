package com.example.fooddyapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddyapp.Models.InstructionsResponse;
import com.example.fooddyapp.R;
import com.example.fooddyapp.RecipeDetailsActivity;

import java.util.List;
import java.util.zip.Inflater;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionViewHolder> {

    Context context ;
    List<InstructionsResponse> list;

    public InstructionsAdapter(Context context, List<InstructionsResponse> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instruction,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position) {

        holder.textView_instruction_name.setText(String.valueOf(list.get(position).name));
        holder.recycler_instruction_steps.setHasFixedSize(true);
        holder.recycler_instruction_steps.setLayoutManager(
                new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        );
        InstructionStepAdapter stepAdapter = new InstructionStepAdapter(context,list.get(position).steps);

        holder.recycler_instruction_steps.setAdapter(stepAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class InstructionViewHolder extends RecyclerView.ViewHolder {
    TextView textView_instruction_name;
    RecyclerView recycler_instruction_steps;


    public InstructionViewHolder(@NonNull View itemView) {
        super(itemView);
        recycler_instruction_steps = itemView.findViewById(R.id.recycler_instruction_steps);
        textView_instruction_name = itemView.findViewById(R.id.textView_instruction_name);

    }
}
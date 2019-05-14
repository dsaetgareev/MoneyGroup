package moneygroup.devufa.ru.moneygroup.adapters.cycle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.ChainActivity;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.model.dto.CycleDTO;

public class CycleAdapter extends RecyclerView.Adapter<CycleAdapter.CycleViewHolder> {

    private List<CycleDTO> cycleDTOS = new ArrayList<>();
    private AppCompatActivity activity;
    private Person person;

    class CycleViewHolder extends RecyclerView.ViewHolder {

        private TextView cycleText;
        private CycleDTO cycleDTO;

        public CycleViewHolder(@NonNull View itemView) {
            super(itemView);
            cycleText = itemView.findViewById(R.id.tv_cyc_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ChainActivity.newIntent(activity, person, cycleDTO);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(intent);
                }
            });
        }

        public void bind(CycleDTO cycleDTO) {
            String text = "Цепочка из " + cycleDTO.getCountElement() + " элементов собрана. " + cycleDTO.getMinCount() + "RUR";
            cycleText.setText(text);
            this.cycleDTO = cycleDTO;
        }
    }

    @NonNull
    @Override
    public CycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_cycle, viewGroup, false);
        return new CycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CycleViewHolder holder, int position) {
        holder.bind(cycleDTOS.get(position));
    }

    @Override
    public int getItemCount() {
        return cycleDTOS.size();
    }

    public List<CycleDTO> getCycleDTOS() {
        return cycleDTOS;
    }

    public void setCycleDTOS(List<CycleDTO> cycleDTOS) {
        this.cycleDTOS = cycleDTOS;
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}

package moneybook.devufa.ru.moneybooks.adapters.welcome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import moneybook.devufa.ru.moneybooks.R;
import moneybook.devufa.ru.moneybooks.activity.HomeActivity;
import moneybook.devufa.ru.moneybooks.activity.Registration;
import moneybook.devufa.ru.moneybooks.model.Language;
import moneybook.devufa.ru.moneybooks.service.LocaleService;

public class WelcomeAdapter extends RecyclerView.Adapter<WelcomeAdapter.WelcomeViewHolder> {

    private List<Language> languages = new ArrayList<>();
    private Activity activity;
    private String settings;

    class WelcomeViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private String value;

        public WelcomeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_lan_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Locale locale = new Locale(value);
                    Locale.setDefault(locale);
                    Configuration configuration = new Configuration();
                    configuration.locale = locale;
                    getActivity().getResources().updateConfiguration(configuration, null);
                    LocaleService.get(getActivity()).updateLocale(value);
                    if ("settings".equals(settings)) {
                        Class home = HomeActivity.class;
                        Intent intent = new Intent(getActivity(), home);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getActivity().startActivity(intent);
                    } else  {
                        Context context = getActivity();
                        Class registration = Registration.class;
                        Intent intent = new Intent(context, registration);
                        getActivity().startActivity(intent);
                    }

                }
            });

        }

        public void bind(Language language) {
            name.setText(language.getName());
            value = language.getValue();
        }
    }

    @NonNull
    @Override
    public WelcomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_language, viewGroup, false);
        return new WelcomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WelcomeViewHolder holder, int position) {
        holder.bind(languages.get(position));
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }
}
